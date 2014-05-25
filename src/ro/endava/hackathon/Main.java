package ro.endava.hackathon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.core.Result;
import ro.endava.hackathon.service.AssignmentService;
import ro.endava.hackathon.service.FileParserService;
import ro.endava.hackathon.service.InitializationService;
import ro.endava.hackathon.service.TravelingParserService;
import ro.endava.hackathon.service.UpdateService;
import ro.endava.hackathon.util.OutputTransform;

public class Main {
	public static void main(String[] args) throws XMLStreamException, IOException {
		long startTime = System.currentTimeMillis();
		if (Configuration.LOG) {
			System.out.println("Parsare dataset pentru activitati...");
		}
		List<Activity> activities = TravelingParserService.getActivities(Configuration.DATASET_PATH);
		if (Configuration.LOG) {
			System.out.println("Parsare dataset pentru persoane...");
		}
		List<Person> persons = TravelingParserService.getPersons(Configuration.DATASET_PATH, activities);
		if (Configuration.LOG) {
			System.out.println("Parsare dataset pentru numarul de ore...");
		}
		Integer hours = TravelingParserService.getJouneyDuration(Configuration.DATASET_PATH);
		
		if (Configuration.LOG) {
			System.out.println("Parsare alte fisiere pentru informatii suplimentare...");
		}
		String folderPath = Configuration.ADDITION_FILES_PATH;
		FileParserService.addMoreInfoFromFiles(activities, persons, folderPath);

		List<ProcessActivity> processActivities = InitializationService.prepareProcessActivitiesFromActivities(activities);
		List<ProcessPerson> processPersons = InitializationService.prepareProcessPersonsFromPersons(persons);

		if (Configuration.LOG) {
			System.out.println("Calculare buget total...");
		}
		Long totalBudget = 0l;
		for (Person currentPerson : persons) {
			totalBudget += currentPerson.getBudget();
		}

		Long totalTurnover = 0l;
		List<Result> results = new ArrayList<Result>();
		for (Integer currentHour = 0; currentHour < hours; currentHour++) {
			if (Configuration.LOG) {
				System.out.println("SE ASIGNEAZA PERSOANELE PENTRU ACTIVITATILE DIN ORA " + currentHour + "!");
			}	
			// totalTurnover += AssignmentService.assignActivitiesForHour(processActivities, processPersons, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetAscending);
			totalTurnover += AssignmentService.assignActivitiesForHourOptimized(processActivities, processPersons);

			results.addAll(OutputTransform.getOutput(processPersons, currentHour));

			UpdateService.updateProcessActivityAtEndOfHour(processActivities);
			UpdateService.updateProcessPersonAtEndOfHour(processPersons);
		}

		if (Configuration.LOG) {
			System.out.println("Se scrie fisierul de iesire...");
		}
		OutputTransform.writeResult(results, totalTurnover);

		if (Configuration.LOG) {
			System.out.println("Venitul maxim posibil este: " + totalBudget);
			System.out.println("Suma totala incasata este: " + totalTurnover);
			System.out.println("Procentul de incasare: " + (Double.valueOf(totalTurnover) / Double.valueOf(totalBudget)) * 100);
		}
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		if (Configuration.TIMER) {
			System.out.println("Aplicatia a rulat in " + duration + "!");
		}
	}
}
