package ro.endava.hackathon.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ro.endava.hackathon.Configuration;
import ro.endava.hackathon.Main;
import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.util.Comparators;
import ro.endava.hackathon.util.Filter;
import ro.endava.hackathon.util.ListUtil;

public class AssignmentOptimizedService {
	// Se asigneaza persoanele disponibile la evenimentele disponibile dupa ce se sorteaza in functie de parametrii trimisi...
	public static Long assignActivitiesForHour(List<ProcessActivity> processActivities, List<ProcessPerson> processPersons, Comparator<ProcessActivity> activitiesComparator, Comparator<ProcessPerson> personsComparator, Integer nrOfHoursToPredict) {
		List<ProcessActivity> toDecideWhatToDo = new ArrayList<ProcessActivity>();
		Long turnoverThisHour = 0l;
		List<ProcessActivity> filteredProcessActivities = Filter.getOpenActivitiesAndSort(processActivities, activitiesComparator);
		for (ProcessActivity processActivity : filteredProcessActivities) {
			List<ProcessPerson> filteredAvailableProcessPersons = Filter.getAvailablePersonForActivityAndSort(processPersons, processActivity.getActivity(), personsComparator);
			if (filteredAvailableProcessPersons.size() > processActivity.getActivity().getMinNoOfParticipants()) {
				if (filteredAvailableProcessPersons.size() < processActivity.getActivity().getCapacity()) {
					processActivity.getPersonsAttending().addAll(filteredAvailableProcessPersons);
					turnoverThisHour += processActivity.getActivity().getTicketPrice() * filteredAvailableProcessPersons.size();
				} else {
					for (Iterator<ProcessPerson> iterator = filteredAvailableProcessPersons.listIterator(); iterator.hasNext();) {
						if (filteredAvailableProcessPersons.indexOf(iterator.next()) + 1 > processActivity.getActivity().getCapacity()) {
							iterator.remove();
						}
					}
					turnoverThisHour += processActivity.getActivity().getTicketPrice() * processActivity.getActivity().getCapacity();
				}
				if (Configuration.LOG) {
					System.out.println("Se adauga " + filteredAvailableProcessPersons.size() + " persone la activitatea " + processActivity.getActivity().getName() + "...");
				}
				for (ProcessPerson filteredAvailableProcessPerson : filteredAvailableProcessPersons) {
					filteredAvailableProcessPerson.setRemainingBudget(filteredAvailableProcessPerson.getRemainingBudget() - processActivity.getActivity().getTicketPrice());
					filteredAvailableProcessPerson.setAssigned(true);
					filteredAvailableProcessPerson.setAssignedToProcessActivity(processActivity);
				}
			} else {
				toDecideWhatToDo.add(processActivity);
			}
		}
		
		for (ProcessActivity processActivity:toDecideWhatToDo) {
			List<ProcessActivity> copyOfProcessActivities = new ArrayList<ProcessActivity>();
			List<ProcessPerson> copyOfProcessPersons = new ArrayList<ProcessPerson>();
			ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
			for (ProcessActivity currentCopy:copyOfProcessActivities) {
				if (currentCopy.getActivity().equals(processActivity.getActivity())) {
					currentCopy.setRemainingHours(currentCopy.getActivity().getMaintenanceHours());
					currentCopy.setWorking(false);
				}
			}
			UpdateService.updateProcessActivityAtEndOfHour(copyOfProcessActivities);
			UpdateService.updateProcessPersonAtEndOfHour(copyOfProcessPersons);
			Long caseWithActivityClosed = Main.predictNextHours(copyOfProcessActivities, copyOfProcessPersons, nrOfHoursToPredict, false);
			copyOfProcessActivities = new ArrayList<ProcessActivity>();
			copyOfProcessPersons = new ArrayList<ProcessPerson>();
			ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
			UpdateService.updateProcessActivityAtEndOfHour(copyOfProcessActivities);
			UpdateService.updateProcessPersonAtEndOfHour(copyOfProcessPersons);
			Long caseWithActivityUnforced = Main.predictNextHours(copyOfProcessActivities, copyOfProcessPersons, nrOfHoursToPredict, false);
			
			if (caseWithActivityUnforced<caseWithActivityClosed) {
				if (processActivity.getActivity().equals(processActivity.getActivity())) {
					processActivity.setRemainingHours(processActivity.getActivity().getMaintenanceHours());
					processActivity.setWorking(false);
				}
			}
		}
		return turnoverThisHour;
	}

	// Se asigneaza persoanele disponibile la evenimentele disponibile dupa ce se sorteaza in functie de parametrii trimisi...
	public static Long assignActivitiesForHourOptimized(List<ProcessActivity> processActivities, List<ProcessPerson> processPersons, Integer nrOfHoursToPredict) {
		List<ProcessActivity> copyOfProcessActivities = new ArrayList<ProcessActivity>();
		List<ProcessPerson> copyOfProcessPersons = new ArrayList<ProcessPerson>();
		ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
		Long turnoverForTicketBudgetDescending = assignActivitiesForHour(copyOfProcessActivities, copyOfProcessPersons, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetDesceding, nrOfHoursToPredict);
		copyOfProcessActivities = new ArrayList<ProcessActivity>();
		copyOfProcessPersons = new ArrayList<ProcessPerson>();
		ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
		Long turnoverForTicketBudgetAscending = assignActivitiesForHour(copyOfProcessActivities, copyOfProcessPersons, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetAscending, nrOfHoursToPredict);
		copyOfProcessActivities = new ArrayList<ProcessActivity>();
		copyOfProcessPersons = new ArrayList<ProcessPerson>();
		ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
		Long turnoverForRemainingHoursBudgetDescending = assignActivitiesForHour(copyOfProcessActivities, copyOfProcessPersons, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetDesceding, nrOfHoursToPredict);
		copyOfProcessActivities = new ArrayList<ProcessActivity>();
		copyOfProcessPersons = new ArrayList<ProcessPerson>();
		ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
		Long turnoverForRemainingHoursBudgetAscending = assignActivitiesForHour(copyOfProcessActivities, copyOfProcessPersons, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetAscending, nrOfHoursToPredict);

		if (turnoverForTicketBudgetDescending >= turnoverForTicketBudgetAscending && turnoverForTicketBudgetDescending >= turnoverForRemainingHoursBudgetDescending && turnoverForTicketBudgetDescending >= turnoverForRemainingHoursBudgetAscending) {
			return assignActivitiesForHour(processActivities, processPersons, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetDesceding, nrOfHoursToPredict);
		} else if (turnoverForTicketBudgetAscending >= turnoverForTicketBudgetDescending && turnoverForTicketBudgetAscending >= turnoverForRemainingHoursBudgetDescending && turnoverForTicketBudgetAscending >= turnoverForRemainingHoursBudgetAscending) {
			return assignActivitiesForHour(processActivities, processPersons, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetAscending, nrOfHoursToPredict);
		} else if (turnoverForRemainingHoursBudgetDescending >= turnoverForTicketBudgetDescending && turnoverForRemainingHoursBudgetDescending >= turnoverForTicketBudgetAscending
				&& turnoverForRemainingHoursBudgetDescending >= turnoverForRemainingHoursBudgetAscending) {
			return assignActivitiesForHour(processActivities, processPersons, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetDesceding, nrOfHoursToPredict);
		} else if (turnoverForRemainingHoursBudgetAscending >= turnoverForTicketBudgetDescending && turnoverForRemainingHoursBudgetAscending >= turnoverForTicketBudgetAscending
				&& turnoverForRemainingHoursBudgetAscending >= turnoverForRemainingHoursBudgetDescending) {
			return assignActivitiesForHour(processActivities, processPersons, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetAscending, nrOfHoursToPredict);
		} else
			return 0l;
	}
}
