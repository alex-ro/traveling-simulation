/**
 * 
 */
package ro.endava.hackathon.repository;

import java.util.ArrayList;
import java.util.List;

import ro.endava.hackathon.core.Activity;

/**
 * @author gnaftanaila
 * 
 */
public class ActivityRepository {

	public List<Activity> getActivityByName(List<String> desiredNames,
			List<Activity> totalActivityList) {
		List<Activity> desiredActivities = new ArrayList<Activity>();
		for (String desiredName : desiredNames) {
			for (Activity activity : totalActivityList) {
				if (desiredName.equals(activity.getName())) {
					desiredActivities.add(activity);
				}
			}
		}
		return desiredActivities;
	}

}
