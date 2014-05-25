package ro.endava.hackathon.core;

import java.util.List;

public class ProcessActivity {
	private Activity activity;
	private Boolean working;
	private Integer remainingHours;
	private List<ProcessPerson> personsAttending;

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Boolean getWorking() {
		return working;
	}

	public void setWorking(Boolean working) {
		this.working = working;
	}

	public Integer getRemainingHours() {
		return remainingHours;
	}

	public void setRemainingHours(Integer remainingHours) {
		this.remainingHours = remainingHours;
	}

	public List<ProcessPerson> getPersonsAttending() {
		return personsAttending;
	}

	public void setPersonsAttending(List<ProcessPerson> personsAttending) {
		this.personsAttending = personsAttending;
	}
	
	public ProcessActivity clone() {
		ProcessActivity clone = new ProcessActivity();
		clone.setActivity(this.activity);
		clone.setPersonsAttending(this.personsAttending);
		clone.setRemainingHours(this.remainingHours);
		clone.setWorking(this.working);
		return clone;
	}
}
