package ro.endava.hackathon.core;

public class ProcessPerson {
	private Person person;
	private Integer remainingBudget;
	private Boolean awake;
	private Integer remainingHours;
	private Boolean assigned;
	private ProcessActivity assignedToProcessActivity;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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

	public ProcessActivity getAssignedToProcessActivity() {
		return assignedToProcessActivity;
	}

	public void setAssignedToProcessActivity(ProcessActivity assignedToProcessActivity) {
		this.assignedToProcessActivity = assignedToProcessActivity;
	}
	
	public ProcessPerson clone() {
		ProcessPerson clone = new ProcessPerson();
		clone.setAssigned(this.assigned);
		clone.setAssignedToProcessActivity(this.assignedToProcessActivity);
		clone.setAwake(this.awake);
		clone.setPerson(this.person);
		clone.setRemainingBudget(this.remainingBudget);
		clone.setRemainingHours(this.remainingHours);
		return clone;
	}
}
