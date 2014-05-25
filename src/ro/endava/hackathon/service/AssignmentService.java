package ro.endava.hackathon.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ro.endava.hackathon.Configuration;
import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.util.Comparators;
import ro.endava.hackathon.util.Filter;
import ro.endava.hackathon.util.ListUtil;

public class AssignmentService {
	// Se asigneaza persoanele disponibile la evenimentele disponibile dupa ce se sorteaza in functie de parametrii trimisi...
	public static Long assignActivitiesForHour(List<ProcessActivity> processActivities, List<ProcessPerson> processPersons, Comparator<ProcessActivity> activitiesComparator, Comparator<ProcessPerson> personsComparator) {
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
			}
		}
		return turnoverThisHour;
	}

	// Se asigneaza persoanele disponibile la evenimentele disponibile dupa ce se sorteaza in functie de parametrii trimisi...
	public static Long assignActivitiesForHourOptimized(List<ProcessActivity> processActivities, List<ProcessPerson> processPersons) {
		List<ProcessActivity> copyOfProcessActivities = new ArrayList<ProcessActivity>();
		List<ProcessPerson> copyOfProcessPersons = new ArrayList<ProcessPerson>();
		ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
		Long turnoverForTicketBudgetDescending = assignActivitiesForHour(copyOfProcessActivities, copyOfProcessPersons, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetDesceding);
		copyOfProcessActivities = new ArrayList<ProcessActivity>();
		copyOfProcessPersons = new ArrayList<ProcessPerson>();
		ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
		Long turnoverForTicketBudgetAscending = assignActivitiesForHour(copyOfProcessActivities, copyOfProcessPersons, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetAscending);
		copyOfProcessActivities = new ArrayList<ProcessActivity>();
		copyOfProcessPersons = new ArrayList<ProcessPerson>();
		ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
		Long turnoverForRemainingHoursBudgetDescending = assignActivitiesForHour(copyOfProcessActivities, copyOfProcessPersons, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetDesceding);
		copyOfProcessActivities = new ArrayList<ProcessActivity>();
		copyOfProcessPersons = new ArrayList<ProcessPerson>();
		ListUtil.makeCopy(processActivities, processPersons, copyOfProcessActivities, copyOfProcessPersons);
		Long turnoverForRemainingHoursBudgetAscending = assignActivitiesForHour(copyOfProcessActivities, copyOfProcessPersons, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetAscending);

		if (turnoverForTicketBudgetDescending >= turnoverForTicketBudgetAscending && turnoverForTicketBudgetDescending >= turnoverForRemainingHoursBudgetDescending && turnoverForTicketBudgetDescending >= turnoverForRemainingHoursBudgetAscending) {
			return assignActivitiesForHour(processActivities, processPersons, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetDesceding);
		} else if (turnoverForTicketBudgetAscending >= turnoverForTicketBudgetDescending && turnoverForTicketBudgetAscending >= turnoverForRemainingHoursBudgetDescending && turnoverForTicketBudgetAscending >= turnoverForRemainingHoursBudgetAscending) {
			return assignActivitiesForHour(processActivities, processPersons, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetAscending);
		} else if (turnoverForRemainingHoursBudgetDescending >= turnoverForTicketBudgetDescending && turnoverForRemainingHoursBudgetDescending >= turnoverForTicketBudgetAscending
				&& turnoverForRemainingHoursBudgetDescending >= turnoverForRemainingHoursBudgetAscending) {
			return assignActivitiesForHour(processActivities, processPersons, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetDesceding);
		} else if (turnoverForRemainingHoursBudgetAscending >= turnoverForTicketBudgetDescending && turnoverForRemainingHoursBudgetAscending >= turnoverForTicketBudgetAscending
				&& turnoverForRemainingHoursBudgetAscending >= turnoverForRemainingHoursBudgetDescending) {
			return assignActivitiesForHour(processActivities, processPersons, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetAscending);
		} else
			return 0l;
	}
}
