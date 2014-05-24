/**
 * 
 */
package ro.endava.hackathon.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.repository.ActivityRepository;

/**
 * @author gnaftanaila
 * 
 */
public class TravelingParserService {

	public static Integer getJouneyDuration(String path) throws FileNotFoundException,
			XMLStreamException {

		Integer jouneyDuration = null;

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory
				.createXMLStreamReader(new FileInputStream(path));

		while (reader.hasNext()) {
			int event = reader.next();

			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				if ("journey".equals(reader.getLocalName())) {
					jouneyDuration = Integer.parseInt(reader
							.getAttributeValue(0));
				}
				break;

			}

		}
		return jouneyDuration;
	}

	public static List<Activity> getActivities(String path)
			throws FileNotFoundException, XMLStreamException {

		List<Activity> activityList = null;
		Activity currentActivity = null;

		String tagContent = null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory
				.createXMLStreamReader(new FileInputStream(path));

		while (reader.hasNext()) {
			int event = reader.next();

			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				if ("activities".equals(reader.getLocalName())) {
					activityList = new ArrayList<Activity>();
				}
				if ("dance".equals(reader.getLocalName())) {
					currentActivity = new Activity();
					currentActivity.setName(reader.getLocalName());
				}
				if ("pool".equals(reader.getLocalName())) {
					currentActivity = new Activity();
					currentActivity.setName(reader.getLocalName());
				}
				if ("bowling".equals(reader.getLocalName())) {
					currentActivity = new Activity();
					currentActivity.setName(reader.getLocalName());
				}
				if ("spa".equals(reader.getLocalName())) {
					currentActivity = new Activity();
					currentActivity.setName(reader.getLocalName());
				}
				if ("volleyball".equals(reader.getLocalName())) {
					currentActivity = new Activity();
					currentActivity.setName(reader.getLocalName());
				}
				if ("library".equals(reader.getLocalName())) {
					currentActivity = new Activity();
					currentActivity.setName(reader.getLocalName());
				}
				if ("club".equals(reader.getLocalName())) {
					currentActivity = new Activity();
					currentActivity.setName(reader.getLocalName());
				}
				if ("cinema".equals(reader.getLocalName())) {
					currentActivity = new Activity();
					currentActivity.setName(reader.getLocalName());
				}
				if ("theatre".equals(reader.getLocalName())) {
					currentActivity = new Activity();
					currentActivity.setName(reader.getLocalName());
				}
				break;

			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
				break;

			case XMLStreamConstants.END_ELEMENT:
				switch (reader.getLocalName()) {
				case "dance":
					activityList.add(currentActivity);
					break;
				case "pool":
					activityList.add(currentActivity);
					break;
				case "bowling":
					activityList.add(currentActivity);
					break;
				case "spa":
					activityList.add(currentActivity);
					break;
				case "volleyball":
					activityList.add(currentActivity);
					break;
				case "library":
					activityList.add(currentActivity);
					break;
				case "club":
					activityList.add(currentActivity);
					break;
				case "cinema":
					activityList.add(currentActivity);
					break;
				case "theatre":
					activityList.add(currentActivity);
					break;

				case "capacity":
					currentActivity.setCapacity(Integer.parseInt(tagContent));
					break;
				case "minNoOfParticipants":
					currentActivity.setMinNoOfParticipants(Integer
							.parseInt(tagContent));
					break;
				case "ticketPrice":
					currentActivity
							.setTicketPrice(Integer.parseInt(tagContent));
					break;
				case "continuousOpenHours":
					currentActivity.setContinuousOpenHours(Integer
							.parseInt(tagContent));
					break;
				case "maintenanceHours":
					currentActivity.setMaintenanceHours(Integer
							.parseInt(tagContent));
					break;

				}
				break;

			case XMLStreamConstants.START_DOCUMENT:
				// activityList = new ArrayList<>();
				break;
			}

		}
		return activityList;
	}

	public static List<Person> getPersons(String path, List<Activity> activityList)
			throws FileNotFoundException, XMLStreamException {

		ActivityRepository activityRepository = new ActivityRepository();

		List<Person> personList = null;
		Person currentPerson = null;

		String tagContent = null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory
				.createXMLStreamReader(new FileInputStream(path));

		while (reader.hasNext()) {
			int event = reader.next();

			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				if ("persons".equals(reader.getLocalName())) {
					personList = new ArrayList<>();
				}
				if ("person".equals(reader.getLocalName())) {
					currentPerson = new Person();
				}

				break;

			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
				break;

			case XMLStreamConstants.END_ELEMENT:
				switch (reader.getLocalName()) {
				case "person":
					personList.add(currentPerson);
					break;

				case "name":
					currentPerson.setName(tagContent);
					break;
				case "budget":
					currentPerson.setBudget(Integer.parseInt(tagContent));
					break;
				case "preferences":
					List<String> activityNames = Arrays.asList(tagContent
							.split(" "));
					currentPerson.setPreferences(activityRepository
							.getActivityByName(activityNames, activityList));
					break;
				case "continuousSleepTime":
					currentPerson.setContinuousSleepTime(Integer
							.parseInt(tagContent));
					break;
				case "maxAwakeTime":
					currentPerson.setMaxAwakeTime(Integer.parseInt(tagContent));
					break;

				}
				break;

			case XMLStreamConstants.START_DOCUMENT:
				// activityList = new ArrayList<>();
				break;
			}

		}
		return personList;
	}
}
