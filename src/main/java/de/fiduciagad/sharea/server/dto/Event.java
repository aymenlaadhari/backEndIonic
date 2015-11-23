package de.fiduciagad.sharea.server.dto;

import java.util.List;

public class Event {

	private String name;
	private Location location;
	private List<Person> subscriber;
	private String dateTime;

	public Event() {

	}

	public Event(String name, Location location, List<Person> subscriber, String dateTime) {
		super();
		this.name = name;
		this.location = location;
		this.subscriber = subscriber;
		this.dateTime = dateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Person> getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(List<Person> subscriber) {
		this.subscriber = subscriber;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}
