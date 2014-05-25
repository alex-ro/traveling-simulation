package ro.endava.hackathon.util;

import java.util.ArrayList;
import java.util.List;

import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;

public class ListUtil {
	public static void makeCopy(List<ProcessActivity> processActivities, List<ProcessPerson> processPersons, List<ProcessActivity> copyOfProcessActivities, List<ProcessPerson> copyOfProcessPersons) {
		for (ProcessActivity processActivity : processActivities) {
			copyOfProcessActivities.add(processActivity.clone());
		}
		for (ProcessPerson processPerson : processPersons) {
			copyOfProcessPersons.add(processPerson.clone());
		}
	}
}
