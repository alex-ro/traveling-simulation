package ro.endava.hackathon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.core.Result;
import ro.endava.hackathon.service.AssignmentOptimizedService;
import ro.endava.hackathon.service.AssignmentService;
import ro.endava.hackathon.service.FileParserService;
import ro.endava.hackathon.service.InitializationService;
import ro.endava.hackathon.service.TravelingParserService;
import ro.endava.hackathon.service.UpdateService;
import ro.endava.hackathon.util.Comparators;
import ro.endava.hackathon.util.OutputTransform;

public class Main {
	private static Long maxTotalTurnover = Long.MIN_VALUE;

	public static void main(String[] args) {

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

		if (Configuration.LOG) {
			System.out.println("Calculare buget total...");
		}
		Long totalBudget = 0l;
		for (Person currentPerson : persons) {
			totalBudget += currentPerson.getBudget();
		}
		System.out.println("Bugetul total este: " + totalBudget);

		System.out.println("Executare algoritm basic 1...");
		tryBasic(activities, persons, hours, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetAscending);
		System.out.println("Executare algoritm basic 2...");
		tryBasic(activities, persons, hours, Comparators.activityCompareByRemainingHours, Comparators.personCompareByRemainingBugetDesceding);
		System.out.println("Executare algoritm basic 3...");
		tryBasic(activities, persons, hours, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetAscending);
		System.out.println("Executare algoritm basic 4...");
		tryBasic(activities, persons, hours, Comparators.activityCompareByTicket, Comparators.personCompareByRemainingBugetDesceding);
		System.out.println("Executare algoritm optimized...");
		tryOptimized(activities, persons, hours);
		for (Integer i = 0; i < 20; i++) {
			System.out.println("Executare algoritm cu pseudo-predictie (" + i + ")");
			tryOptimizedWithPrediction(activities, persons, hours, i);
		}

		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		if (Configuration.TIMER) {
			System.out.println("Aplicatia a rulat in " + duration + "!");
		}
	}

	public static void tryBasic(List<Activity> activities, List<Person> persons, Integer hours, Comparator<ProcessActivity> activitiesComparator, Comparator<ProcessPerson> personsComparator) {
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
			totalTurnover += AssignmentService.assignActivitiesForHour(processActivities, processPersons, activitiesComparator, personsComparator);

			results.addAll(OutputTransform.getOutput(processPersons, currentHour));

			UpdateService.updateProcessActivityAtEndOfHour(processActivities);
			UpdateService.updateProcessPersonAtEndOfHour(processPersons);
		}

		if (Configuration.LOG) {
			System.out.println("Se scrie fisierul de iesire...");
		}
		if (maxTotalTurnover < totalTurnover) {
			maxTotalTurnover = totalTurnover;
			OutputTransform.writeResult(results, totalTurnover);
		}
		System.out.println("Suma totala incasata este: " + totalTurnover);
		System.out.println("Procentul de incasare: " + (Double.valueOf(totalTurnover) / Double.valueOf(totalBudget)) * 100);
	}

	public static void tryOptimized(List<Activity> activities, List<Person> persons, Integer hours) {
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
			totalTurnover += AssignmentService.assignActivitiesForHourOptimized(processActivities, processPersons);

			results.addAll(OutputTransform.getOutput(processPersons, currentHour));

			UpdateService.updateProcessActivityAtEndOfHour(processActivities);
			UpdateService.updateProcessPersonAtEndOfHour(processPersons);
		}

		if (Configuration.LOG) {
			System.out.println("Se scrie fisierul de iesire...");
		}
		if (maxTotalTurnover < totalTurnover) {
			maxTotalTurnover = totalTurnover;
			OutputTransform.writeResult(results, totalTurnover);
		}
		System.out.println("Suma totala incasata este: " + totalTurnover);
		System.out.println("Procentul de incasare: " + (Double.valueOf(totalTurnover) / Double.valueOf(totalBudget)) * 100);
	}

	public static void tryOptimizedWithPrediction(List<Activity> activities, List<Person> persons, Integer hours, Integer nrOfHoursToPredict) {
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
			totalTurnover += AssignmentOptimizedService.assignActivitiesForHourOptimized(processActivities, processPersons, nrOfHoursToPredict);

			results.addAll(OutputTransform.getOutput(processPersons, currentHour));

			UpdateService.updateProcessActivityAtEndOfHour(processActivities);
			UpdateService.updateProcessPersonAtEndOfHour(processPersons);
		}

		if (Configuration.LOG) {
			System.out.println("Se scrie fisierul de iesire...");
		}
		if (maxTotalTurnover < totalTurnover) {
			maxTotalTurnover = totalTurnover;
			OutputTransform.writeResult(results, totalTurnover);
		}
		System.out.println("Suma totala incasata este: " + totalTurnover);
		System.out.println("Procentul de incasare: " + (Double.valueOf(totalTurnover) / Double.valueOf(totalBudget)) * 100);
	}

	public static Long predictNextHours(List<ProcessActivity> processActivities, List<ProcessPerson> processPersons, Integer nrOfHours, Boolean showLog) {
		Long totalTurnover = 0l;
		for (Integer currentHour = 0; currentHour < nrOfHours; currentHour++) {
			if (showLog) {
				System.out.println("SE ASIGNEAZA PERSOANELE PENTRU ACTIVITATILE DIN ORA " + currentHour + "!");
			}
			;
			totalTurnover += AssignmentService.assignActivitiesForHourOptimized(processActivities, processPersons);

			UpdateService.updateProcessActivityAtEndOfHour(processActivities);
			UpdateService.updateProcessPersonAtEndOfHour(processPersons);
		}
		return totalTurnover;
	}
}
