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
				.getJouneyDuration(Configuration.DATASET_PATH);

		List<Activity> activityList = travelingParserService
				.getActivities(Configuration.DATASET_PATH);

		List<Person> personList = travelingParserService.getPersons(
				Configuration.DATASET_PATH, activityList);

		FileParserService.addMoreInfoFromFiles(activityList, personList,
				Configuration.ADDITION_FILES_PATH);

	}

}
