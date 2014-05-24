package ro.endava.hackathon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.core.Result;
import ro.endava.hackathon.util.Comparators;
import ro.endava.hackathon.util.Filter;
import ro.endava.hackathon.util.OutputTransform;

public class Main {
	public static void main(String[] args) {
		// De citit din XML
		Integer hours = 0;
		List<ProcessActivity> processActivities = new ArrayList<ProcessActivity>();
		List<ProcessPerson> processPersons = new ArrayList<ProcessPerson>();

		Integer totalTurnover = 0;
		List<Result> results = new ArrayList<Result>();
		
		for (Integer currentHour = 0; currentHour < hours; currentHour++) {
			System.out.println("*** Asignare pentru ora " + currentHour + "...");
			Filter.getOpenActivitiesAndSort(processActivities, Comparators.activityCompareByTicket);

			Integer turnoverThisHour = 0;
			for (ProcessActivity processActivity : processActivities) {
				List<ProcessPerson> availableProcessPersons = Filter.getAvailablePersonForActivityAndSort(processPersons, processActivity.getActivity(), Comparators.personCompareByRemainingBuget);
				if (availableProcessPersons.size() > processActivity.getActivity().getMinNoOfParticipants()) {
					System.out.println("Se adauga persoanele la activitatea " + processActivity.getActivity().getName() + "...");
					if (availableProcessPersons.size() < processActivity.getActivity().getCapacity()) {
						processActivity.getPersonsAttending().addAll(availableProcessPersons);
						turnoverThisHour += processActivity.getActivity().getTicketPrice() * availableProcessPersons.size();
					} else {
						for (Iterator<ProcessPerson> iterator = availableProcessPersons.listIterator(); iterator.hasNext();) {
							if (availableProcessPersons.indexOf(iterator.next()) + 1 > processActivity.getActivity().getCapacity()) {
								iterator.remove();
							}
						}
						turnoverThisHour += processActivity.getActivity().getTicketPrice() * processActivity.getActivity().getCapacity();
					}
					for (ProcessPerson processPerson : availableProcessPersons) {
						processPerson.setRemainingBudget(processPerson.getRemainingBudget() - processActivity.getActivity().getTicketPrice());
						processPerson.setAssigned(true);
						processPerson.setAssignedToProcessActivity(processActivity);
					}
				}
			}

			totalTurnover += turnoverThisHour;
			results.addAll(OutputTransform.getOutput(processPersons, currentHour));

			System.out.println("Se actualizeaza activitatile...");
			for (ProcessActivity processActivity : processActivities) {
				if (processActivity.getRemainingHours() > 1) {
					processActivity.setRemainingHours(processActivity.getRemainingHours() - 1);
				} else {
					processActivity.setWorking(!processActivity.getWorking());
					processActivity.setRemainingHours(processActivity.getWorking() ? processActivity.getActivity().getContinuousOpenHours() : processActivity.getActivity().getMaintenanceHours());
				}
				processActivity.setPersonsAttending(null);
			}
			System.out.println("Se actualizeaza persoanele...");
			for (ProcessPerson processPerson : processPersons) {
				if (processPerson.getRemainingHours() > 1) {
					processPerson.setRemainingHours(processPerson.getRemainingHours() - 1);
				} else {
					processPerson.setAwake(!processPerson.getAwake());
					processPerson.setRemainingHours(processPerson.getAwake() ? processPerson.getPerson().getMaxAwakeTime() : processPerson.getPerson().getContinuousSleepTime());
				}
				processPerson.setAssigned(false);
				processPerson.setAssignedToProcessActivity(null);
			}
		}
		
		System.out.print("Suma totala este: " + totalTurnover);
	}
}
