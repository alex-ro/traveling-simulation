/**
 * 
 */
package ro.endava.hackathon;

import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.service.TravelingParserService;

/**
 * @author gnaftanaila
 * 
 */
public class Main2 {

	/**
	 * @param args
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
		TravelingParserService travelingParserService = new TravelingParserService();

		List<Activity> activityList = travelingParserService
				.getActivities("D:\\dataset.xml");
		
		List<Person> personList = travelingParserService
				.getPersons("D:\\dataset.xml", activityList);

	}

}
