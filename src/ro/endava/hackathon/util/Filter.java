package ro.endava.hackathon.util;

import ro.endava.hackathon.core.Activity;
import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class Filter {

	public static List<ProcessActivity> getOpenActivitiesAndSort(List<ProcessActivity> activities, Comparator<ProcessActivity> comp) {
		Collection<ProcessActivity> filter = Collections2.filter(activities, new Predicate<ProcessActivity>() {
			public boolean apply(ProcessActivity pa) {
				return pa.getWorking().equals(Boolean.TRUE);
			}
		});
		ArrayList<ProcessActivity> subList = new ArrayList<ProcessActivity>(filter);
		Collections.sort(subList, comp);
		return subList;
	}

	public static List<ProcessPerson> getAvailablePersonForActivityAndSort(List<ProcessPerson> persons,
			Activity activity, Comparator<ProcessPerson> comp) {

		List<ProcessPerson> filterPersonsByActivity = filterPersonsByActivityAndBuget(persons, activity);
		Collection<ProcessPerson> filter = Collections2.filter(filterPersonsByActivity, new Predicate<ProcessPerson>() {
			public boolean apply(ProcessPerson pp) {
				return pp.getAwake().equals(Boolean.TRUE) &&
						pp.getAssigned().equals(Boolean.FALSE);
			}
		});

		ArrayList<ProcessPerson> subList = new ArrayList<ProcessPerson>(filter);
		Collections.sort(subList, comp);
		return subList;
	}

	private static List<ProcessPerson> filterPersonsByActivityAndBuget(List<ProcessPerson> persons, Activity activity) {
		List<ProcessPerson> list = new ArrayList<ProcessPerson>();
		for (ProcessPerson pp : persons) {
			if (isPreferedActivity(pp, activity) && pp.getRemainingBudget() - activity.getTicketPrice() > 0) {
				list.add(pp);
			}
		}
		return list;
	}

	private static boolean isPreferedActivity(ProcessPerson pp, Activity activity) {
		if (pp.getPerson().getPreferences() != null) {
			for (Activity a : pp.getPerson().getPreferences()) {
				if (a.getName().equals(activity.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
