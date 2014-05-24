package ro.endava.hackathon.service;

import java.util.Iterator;
import java.util.List;

import ro.endava.hackathon.Configuration;
import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.util.Comparators;
import ro.endava.hackathon.util.Filter;

public class AssignmentService {
	public static Long assignActivitiesForHour(List<ProcessActivity> processActivities, List<ProcessPerson> processPersons) {
		Long turnoverThisHour = 0l;
		List<ProcessActivity> filteredProcessActivities = Filter.getOpenActivitiesAndSort(processActivities, Comparators.activityCompareByTicket);
		for (ProcessActivity processActivity : filteredProcessActivities) {
			List<ProcessPerson> filteredAvailableProcessPersons = Filter.getAvailablePersonForActivityAndSort(processPersons, processActivity.getActivity(), Comparators.personCompareByRemainingBugetDesceding);
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
}
