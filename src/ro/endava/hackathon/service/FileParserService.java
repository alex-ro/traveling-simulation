package ro.endava.hackathon.service;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileParserService {

	public static void addMoreInfoFromFiles(List<Activity> activities, List<Person> persons, String folderPath) {
		//prepare data
		Map<String, Person> personMap = getPersonMap(persons);
		
		//QRcode parsing method 
		addMoreDataFromPng(personMap, activities, folderPath);
		
		//pdf parsing method
		addMoreDataFromPdf(personMap, activities, folderPath);
		
		//doc parsing method
		addMoreDataFromDoc(personMap, activities, folderPath);
	}

	private static void addMoreDataFromDoc(Map<String, Person> personMap, List<Activity> activities, String folderPath) {
		// TODO Auto-generated method stub
		
	}

	private static void addMoreDataFromPdf(Map<String, Person> personMap, List<Activity> activities, String folderPath) {
		// TODO Auto-generated method stub
		
	}

	private static void addMoreDataFromPng(Map<String, Person> personMap, List<Activity> activities, String folderPath) {
		// TODO Auto-generated method stub
		
	}

	private static Map<String, Person> getPersonMap(List<Person> persons) {
		Map<String, Person> personMap = new HashMap<String, Person>();
		for (Person p : persons) {
			personMap.put(p.getName(), p);
		}
		return personMap;
	}
	
	private List<String> getFilePaths(String folderPath, String extension) {
		List<String> filePaths = new ArrayList<>();
		
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	String fileName = listOfFiles[i].getName();
		    	if (listOfFiles[i].isFile() && fileName.contains(".png")) {
		    		filePaths.add(folderPath+"\\" + fileName);
			      } 
		    }
		      
		
		return filePaths;
	}
	
	
	
	
}
