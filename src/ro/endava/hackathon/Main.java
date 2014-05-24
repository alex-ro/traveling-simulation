package ro.endava.hackathon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.util.Comparators;
import ro.endava.hackathon.util.Filter;

public class Main {
	public static void main(String[] args) {
		List<ProcessActivity> processActivities = new ArrayList<ProcessActivity>();
		List<ProcessPerson> processPersons = new ArrayList<ProcessPerson>();
		System.out.println("Asignare persoane la activitati...");
		Filter.getOpenActivitiesAndSort(processActivities, Comparators.activityCompareByTicket);
		for (ProcessActivity processActivity:processActivities) {
			List<ProcessPerson> availableProcessPersons = Filter.getAvailablePersonForActivityAndSort(processPersons, processActivity, Comparators.personCompareByRemainingBuget);
			
			if (availableProcessPersons.size()>processActivity.getActivity().getMinNoOfParticipants()) {
				System.out.println("Se adauga persoanele la activitatea " + processActivity.getActivity().getName() + "...");
				if (availableProcessPersons.size()<processActivity.getActivity().getCapacity()) {
					processActivity.getPersonsAttending().addAll(availableProcessPersons);
				} else {
					for (Iterator<ProcessPerson> iterator = availableProcessPersons.listIterator(); iterator.hasNext(); ) {
					    if (availableProcessPersons.indexOf(iterator.next())+1>processActivity.getActivity().getCapacity()) {
					    	iterator.remove();
					    }
					}
				}
				for (ProcessPerson processPerson:availableProcessPersons) {
					processPerson.setAssigned(true);
					processPerson.setAssignedToProcessActivity(processActivity);
				}
			}
			
			for () {
		}
		
	}
}
