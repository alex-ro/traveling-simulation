package ro.endava.hackathon.service;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileParserService {

	public static void addMoreInfoFromFiles(List<Activity> activities, List<Person> persons, String folderPaht) {
		//prepare data
		Map<String, Person> personMap = getPersonMap(persons);
		
		//QRcode parsing method 
		addMoreDataFromPng(personMap, activities, folderPaht);
		
		//pdf parsing method
		addMoreDataFromPdf(personMap, activities, folderPaht);
		
		//doc parsing method
		addMoreDataFromDoc(personMap, activities, folderPaht);
	}

	private static void addMoreDataFromDoc(Map<String, Person> personMap, List<Activity> activities, String folderPaht) {
		// TODO Auto-generated method stub
		
	}

	private static void addMoreDataFromPdf(Map<String, Person> personMap, List<Activity> activities, String folderPaht) {
		// TODO Auto-generated method stub
		
	}

	private static void addMoreDataFromPng(Map<String, Person> personMap, List<Activity> activities, String folderPaht) {
		// TODO Auto-generated method stub
		
	}

	private static Map<String, Person> getPersonMap(List<Person> persons) {
		Map<String, Person> personMap = new HashMap<String, Person>();
		for (Person p : persons) {
			personMap.put(p.getName(), p);
		}
		return personMap;
	}
	
	
}
