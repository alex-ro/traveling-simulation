package ro.endava.hackathon.core;

import java.util.List;

public class Person {
	private String name;
	private Integer budget;
	private List<Activity> preferences;
	private Integer continuousSleepTime;
	private Integer maxAwakeTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public List<Activity> getPreferences() {
		return preferences;
	}

	public void setPreferences(List<Activity> preferences) {
		this.preferences = preferences;
	}

	public Integer getContinuousSleepTime() {
		return continuousSleepTime;
	}

	public void setContinuousSleepTime(Integer continuousSleepTime) {
		this.continuousSleepTime = continuousSleepTime;
	}

	public Integer getMaxAwakeTime() {
		return maxAwakeTime;
	}

	public void setMaxAwakeTime(Integer maxAwakeTime) {
		this.maxAwakeTime = maxAwakeTime;
	}
}
