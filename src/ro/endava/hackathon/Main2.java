/**
 * 
 */
package ro.endava.hackathon;

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;
import ro.endava.hackathon.service.FileParserService;
import ro.endava.hackathon.service.TravelingParserService;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

/**
 * @author gnaftanaila
 * 
 */
public class Main2 {

	/**
	 * @param args
	 * @throws XMLStreamException
	 * @throws IOException
	 * @throws WriterException
	 * @throws NotFoundException
	 */
	public static void main(String[] args) throws XMLStreamException,
			WriterException, IOException, NotFoundException {
		TravelingParserService travelingParserService = new TravelingParserService();

		Integer journey = travelingParserService
				.getJouneyDuration("D:\\dataset.xml");

		List<Activity> activityList = travelingParserService
				.getActivities("D:\\dataset.xml");

		List<Person> personList = travelingParserService.getPersons(
				"D:\\dataset.xml", activityList);

		FileParserService.addMoreInfoFromFiles(activityList, personList,
				"D:\\additional-files");
		

	}

}
