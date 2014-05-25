package ro.endava.hackathon.core;

public class Activity {
	private String name;
	private Integer capacity;
	private Integer minNoOfParticipants;
	private Integer ticketPrice;
	private Integer continuousOpenHours;
	private Integer maintenanceHours;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

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
