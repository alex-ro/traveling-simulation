package ro.endava.hackathon.core;

public class ProcessPassenger {
	private Passenger passenger;
	private Integer remainingBudget;
	private Boolean awake;
	private Integer remainingHours;
	private Boolean assigned;
	private ProcessActivity processActivity;

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Integer getRemainingBudget() {
		return remainingBudget;
	}

	public void setRemainingBudget(Integer remainingBudget) {
		this.remainingBudget = remainingBudget;
	}

	public Boolean getAwake() {
		return awake;
	}

	public void setAwake(Boolean awake) {
		this.awake = awake;
	}

	public Integer getRemainingHours() {
		return remainingHours;
	}

	public void setRemainingHours(Integer remainingHours) {
		this.remainingHours = remainingHours;
	}

	public Boolean getAssigned() {
		return assigned;
	}

	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}

	public ProcessActivity getProcessActivity() {
		return processActivity;
	}

	public void setProcessActivity(ProcessActivity processActivity) {
		this.processActivity = processActivity;
	}
}
