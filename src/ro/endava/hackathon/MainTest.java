package ro.endava.hackathon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MainTest {
	public static void main(String[] args) throws XMLStreamException,
			IOException {
		try {

			List<String> allPreferences = new ArrayList<>();

			allPreferences.add("dance");
			allPreferences.add("pool");
			allPreferences.add("bowling");
			allPreferences.add("spa");
			allPreferences.add("volleyball");
			allPreferences.add("library");
			allPreferences.add("club");
			allPreferences.add("cinema");
			allPreferences.add("theatre");

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("journey");
			Attr xmlnsAttr = doc.createAttribute("xmlns");
			xmlnsAttr.setValue("http://www.endava.com/hackathon/journey");
			rootElement.setAttributeNode(xmlnsAttr);
			Attr teamAttr = doc.createAttribute("duration");
			teamAttr.setValue("1500");
			rootElement.setAttributeNode(teamAttr);
			doc.appendChild(rootElement);

			Element activities = doc.createElement("activities");
			createActivity(doc, activities, "dance");
			createActivity(doc, activities, "pool");
			createActivity(doc, activities, "bowling");
			createActivity(doc, activities, "spa");
			createActivity(doc, activities, "volleyball");
			createActivity(doc, activities, "library");
			createActivity(doc, activities, "club");
			createActivity(doc, activities, "cinema");
			createActivity(doc, activities, "theatre");
			rootElement.appendChild(activities);

			createPersons(doc, rootElement, allPreferences);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(
					Configuration.DATASET_PATH_2));
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public static void createActivity(Document doc, Element rootElement,
			String name) {
		Element activity = doc.createElement(name);

		Element capacity = doc.createElement("capacity");
		capacity.appendChild(doc.createTextNode(get2DigitsRandomInt()
				.toString()));
		activity.appendChild(capacity);

		Element minNoOfParticipants = doc.createElement("minNoOfParticipants");
		minNoOfParticipants.appendChild(doc
				.createTextNode(get2DigitsRandomInt().toString()));
		activity.appendChild(minNoOfParticipants);

		Element ticketPrice = doc.createElement("ticketPrice");
		ticketPrice.appendChild(doc.createTextNode(get2DigitsRandomInt()
				.toString()));
		activity.appendChild(ticketPrice);

		Element continuousOpenHours = doc.createElement("continuousOpenHours");
		continuousOpenHours.appendChild(doc
				.createTextNode(get2DigitsRandomInt().toString()));
		activity.appendChild(continuousOpenHours);

		Element maintenanceHours = doc.createElement("maintenanceHours");
		maintenanceHours.appendChild(doc.createTextNode(get2DigitsRandomInt()
				.toString()));
		activity.appendChild(maintenanceHours);
		rootElement.appendChild(activity);

	}

	public static void createPersons(Document doc, Element rootElement,
			List<String> allPreferences) {
		Element persons = doc.createElement("persons");

		for (int i = 1; i < 2000; i++) {
			Element person = doc.createElement("person");

			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode("P" + i));
			person.appendChild(name);

			Element budget = doc.createElement("budget");
			budget.appendChild(doc.createTextNode(get4DigitsRandomInt()
					.toString()));
			person.appendChild(budget);

			Element preferences = doc.createElement("preferences");
			preferences.appendChild(doc
					.createTextNode(getRandomPreferences(allPreferences)));
			person.appendChild(preferences);

			Element continuousSleepTime = doc
					.createElement("continuousSleepTime");
			continuousSleepTime.appendChild(doc.createTextNode("8"));
			person.appendChild(continuousSleepTime);

			Element maxAwakeTime = doc.createElement("maxAwakeTime");
			maxAwakeTime.appendChild(doc.createTextNode("16"));
			person.appendChild(maxAwakeTime);

			persons.appendChild(person);

		}
		rootElement.appendChild(persons);

	}

	private static Integer get1DigitRandomInt() {
		return (int) (Math.random() * 10);
	}

	private static Integer get2DigitsRandomInt() {
		return (int) (Math.random() * 100);
	}

	private static Integer get4DigitsRandomInt() {
		return (int) (Math.random() * 10000);
	}

	private static String getRandomPreferences(List<String> allPreferences) {

		Collections.shuffle(allPreferences);

		return allPreferences.get(0) + " " + allPreferences.get(1) + " "
				+ allPreferences.get(2);

	}
}
