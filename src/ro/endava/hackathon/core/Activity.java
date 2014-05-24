package ro.endava.hackathon.core;

public class Activity {
	private String name;
	private Integer capacity;
	private Integer minNoOfParticipants;
	private Integer ticketPrice;
	private Integer continuousOpenHours;
	private Integer maintenanceHours;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getMinNoOfParticipants() {
		return minNoOfParticipants;
	}

	public void setMinNoOfParticipants(Integer minNoOfParticipants) {
		this.minNoOfParticipants = minNoOfParticipants;
	}

	public Integer getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Integer getContinuousOpenHours() {
		return continuousOpenHours;
	}

	public void setContinuousOpenHours(Integer continuousOpenHours) {
		this.continuousOpenHours = continuousOpenHours;
	}

	public Integer getMaintenanceHours() {
		return maintenanceHours;
	}

	public void setMaintenanceHours(Integer maintenanceHours) {
		this.maintenanceHours = maintenanceHours;
	}
}
