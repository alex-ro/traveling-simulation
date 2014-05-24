package ro.endava.hackathon.core;

import java.util.List;

public class ProcessActivity {
	private Activity activity;
	private Boolean working;
	private Integer remainingHours;
	private List<ProcessPassenger> passengerActivity;

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

	public List<ProcessPassenger> getPassengerActivity() {
		return passengerActivity;
	}

	public void setPassengerActivity(List<ProcessPassenger> passengerActivity) {
		this.passengerActivity = passengerActivity;
	}
}
