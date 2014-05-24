package ro.endava.hackathon.util;

import ro.endava.hackathon.core.ProcessActivity;
import ro.endava.hackathon.core.ProcessPerson;

import java.util.Comparator;

public class Comparators {

	//descrescator
	public static Comparator<ProcessActivity> activityCompareByTicket = new Comparator<ProcessActivity>() {
		public int compare(ProcessActivity pa1, ProcessActivity pa2) {
			return pa2.getActivity().getTicketPrice().compareTo(pa1.getActivity().getTicketPrice());
		}
	};
	//crescator
	public static Comparator<ProcessActivity> activityCompareByRemainingHours = new Comparator<ProcessActivity>() {
		public int compare(ProcessActivity pa1, ProcessActivity pa2) {
			return pa1.getRemainingHours().compareTo(pa2.getRemainingHours());
		}
	};
		
	//descrescator
	public static Comparator<ProcessPerson> personCompareByRemainingBugetDesceding = new Comparator<ProcessPerson>() {
		public int compare(ProcessPerson pp1, ProcessPerson pp2) {
			return pp2.getRemainingHours().compareTo(pp1.getRemainingHours());
		}
	};
	
	//crescator
	public static Comparator<ProcessPerson> personCompareByRemainingBugetAscending = new Comparator<ProcessPerson>() {
		public int compare(ProcessPerson pp1, ProcessPerson pp2) {
			return pp1.getRemainingHours().compareTo(pp2.getRemainingHours());
		}
	};
	
}
