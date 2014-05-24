package ro.endava.hackathon.core;

import java.util.Set;

public class ProcessActivity {
	private Activity activity;
	private Boolean working;
	private Integer remainingHours;
	private Set<ProcessPassenger> passengerActivity;

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

	public Set<ProcessPassenger> getPassengerActivity() {
		return passengerActivity;
	}

	public void setPassengerActivity(Set<ProcessPassenger> passengerActivity) {
		this.passengerActivity = passengerActivity;
	}
}
