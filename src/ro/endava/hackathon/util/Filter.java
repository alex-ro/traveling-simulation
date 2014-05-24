package ro.endava.hackathon.util;

import ro.endava.hackathon.core.ProcessActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class Filter {

	//descrescator
	public static Comparator<ProcessActivity> activityCompareByTicket = new Comparator<ProcessActivity> () {
		public int compare(ProcessActivity pa1, ProcessActivity pa2) {
			return pa2.getActivity().getTicketPrice().compareTo(pa1.getActivity().getTicketPrice());
		}
	};
	//crescator
	public static Comparator<ProcessActivity> activityCompareByRemainingHours = new Comparator<ProcessActivity> () {
		public int compare(ProcessActivity pa1, ProcessActivity pa2) {
			return pa1.getRemainingHours().compareTo(pa2.getRemainingHours());
		}
	};
	
	public static List<ProcessActivity> getOpenActivitiesAndSort(List<ProcessActivity> activities, Comparator<ProcessActivity> comp) {
		Collection<ProcessActivity> filter = Collections2.filter(activities, new Predicate<ProcessActivity> () {
			public boolean apply(ProcessActivity pa) {
				return pa.getWorking().equals(Boolean.TRUE);
			}
		});
		ArrayList<ProcessActivity> subList = new ArrayList<ProcessActivity>(filter);
		Collections.sort(subList, comp);
		return subList;
	}
}
