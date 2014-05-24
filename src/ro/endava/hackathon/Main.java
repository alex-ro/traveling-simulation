package ro.endava.hackathon;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.core.Result;
import ro.endava.hackathon.service.FileParserService;
import ro.endava.hackathon.service.TravelingParserService;
import ro.endava.hackathon.util.Comparators;
import ro.endava.hackathon.util.Filter;
import ro.endava.hackathon.util.OutputTransform;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
		TravelingParserService travelingParserService = new TravelingParserService();
		List<Activity> activities = travelingParserService.getActivities("D:\\dataset.xml");
		List<Person> persons = travelingParserService.getPersons("D:\\dataset.xml", activities);
		Integer hours = 183;
		
		String folderPaht = ""; //TODO
		FileParserService.addMoreInfoFromFiles(activities, persons, folderPaht);
		
		System.out.println("Initializare processActivities si processPersons...");
		List<ProcessActivity> processActivities = new ArrayList<ProcessActivity>();
		List<ProcessPerson> processPersons = new ArrayList<ProcessPerson>();
		for (Activity currentActivity:activities) {
			ProcessActivity processActivity = new ProcessActivity();
			processActivity.setActivity(currentActivity);
			processActivity.setWorking(true);
			processActivity.setRemainingHours(currentActivity.getContinuousOpenHours());
			processActivity.setPersonsAttending(null);
			processActivities.add(processActivity);
		}
		for (Person currentPerson:persons) {
			ProcessPerson processPerson = new ProcessPerson();
			processPerson.setPerson(currentPerson);
			processPerson.setAwake(true);
			processPerson.setRemainingBudget(currentPerson.getBudget());
			processPerson.setRemainingHours(currentPerson.getMaxAwakeTime());
			processPerson.setAssigned(false);
			processPerson.setAssignedToProcessActivity(null);
			processPersons.add(processPerson);
		}

		Long totalBudget = 0l;
		for (Person currentPerson:persons) {
			totalBudget += currentPerson.getBudget();
		}

		Long totalTurnover = 0l;
		List<Result> results = new ArrayList<Result>();
		
		for (Integer currentHour = 0; currentHour < hours; currentHour++) {
			System.out.println("*** Asignare pentru ora " + currentHour + "...");
			List<ProcessActivity> filteredProcessActivities = Filter.getOpenActivitiesAndSort(processActivities, Comparators.activityCompareByTicket);

			Integer turnoverThisHour = 0;
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
					System.out.println("Se adauga " + filteredAvailableProcessPersons.size() + " persone la activitatea " + processActivity.getActivity().getName() + "...");
					for (ProcessPerson processPerson : filteredAvailableProcessPersons) {
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
				processActivity.setPersonsAttending(new ArrayList<ProcessPerson>());
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
		
		OutputTransform.writeResult(results, totalTurnover);
		System.out.println("Maximul posibil este: " + totalBudget);
		System.out.println("Suma totala este: " + totalTurnover);
		System.out.println("Procentul de profit: " + (Double.valueOf(totalTurnover)/Double.valueOf(totalBudget))*100);
	}
}
