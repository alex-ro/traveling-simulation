/**
 * 
 */
package ro.endava.hackathon.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.Person;

/**
 * @author gnaftanaila
 * 
 */
public class FileUtil {

	private static final Pattern moneyPattern = Pattern.compile("USD(\\d+)");

	public static List<String> getFilePaths(String folderPath, String extension) {
		List<String> filePaths = new ArrayList<>();

		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			String fileName = listOfFiles[i].getName();
			if (listOfFiles[i].isFile() && fileName.contains(extension)) {
				filePaths.add(folderPath + "\\" + fileName);
			}
		}
		return filePaths;
	}

	public static void updatedEntities(String text,
			Map<String, Activity> activityMap, Map<String, Person> personMap) {
		String pName = null;
		Integer moneySum = 0;
		List<String> moreActivities = new ArrayList<>();

		List<String> keySet = new ArrayList<String>(personMap.keySet());
		Collections.sort(keySet, new Comparator<String>() {
			@Override
			public int compare(String a0, String a1) {
				return a1.length() - a0.length();
			}
		});
		for (String personName : keySet) {

			/*
			 * Pattern pattern = Pattern.compile(personName); Matcher matcher =
			 * pattern.matcher(text); if (matcher.matches()) { pName =
			 * personName; break; }
			 */

			if (text.contains(personName)) {
				pName = personName;
				break;
			}

		}

		if (pName != null) {
			// Find money
			Matcher matcher = moneyPattern.matcher(text);
			while (matcher.find()) {
				Integer money = Integer.valueOf(matcher.group(1));
				moneySum += money;
			}

			// Find activities
			for (String activityName : activityMap.keySet()) {
				/*
				 * Pattern pattern = Pattern.compile(activityName); Matcher
				 * matcherActivity = pattern.matcher(text); if
				 * (matcherActivity.matches()) {
				 * moreActivities.add(activityName); }
				 */
				if (text.contains(activityName)) {
					moreActivities.add(activityName);
				}
			}

			Person p = personMap.get(pName);
			p.setBudget(p.getBudget() + moneySum);
			List<Activity> activityList = p.getPreferences();
			if (activityList == null && moreActivities.size() > 0) {
				activityList = new ArrayList<>();
			}

			for (String newActivityName : moreActivities) {
				Activity activity = activityMap.get(newActivityName);
				if (!activityList.contains(activity)) {
					activityList.add(activity);
				}

			}
		}

	}
}
