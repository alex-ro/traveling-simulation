package ro.endava.hackathon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.reader.DocReader;
import ro.endava.hackathon.reader.PdfReader;
import ro.endava.hackathon.reader.QRCodeReader;

public class FileParserService {

	public static void addMoreInfoFromFiles(List<Activity> activities,
			List<Person> persons, String folderPath) {
		// prepare data
		Map<String, Activity> activityMap = getActivityMap(activities);
		Map<String, Person> personMap = getPersonMap(persons);

		// QRcode parsing method
		QRCodeReader.addMoreDataFromPng(personMap, activityMap, folderPath);

		// pdf parsing method
		PdfReader.addMoreDataFromPdf(personMap, activities, folderPath);

		// doc parsing method
		DocReader.addMoreDataFromDoc(personMap, activities, folderPath);
	}

	private static Map<String, Person> getPersonMap(List<Person> persons) {
		Map<String, Person> personMap = new HashMap<String, Person>();
		for (Person p : persons) {
			personMap.put(p.getName(), p);
		}
		return personMap;
	}

	private static Map<String, Activity> getActivityMap(
			List<Activity> activities) {
		Map<String, Activity> activityMap = new HashMap<String, Activity>();
		for (Activity a : activities) {
			activityMap.put(a.getName(), a);
		}
		return activityMap;
	}

}
