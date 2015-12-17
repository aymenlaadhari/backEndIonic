package de.fiduciagad.sharea.server.data.repository.dto;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Event extends CouchDbDocument {

	private static final long serialVersionUID = 1L;

	private String name;

	private Location location;

	private Set<Person> subscriber;

	private String dateTime;

	@JsonProperty("type")
	@TypeDiscriminator(value = "doc.type === 'Event'")
	private final String type = "Event";

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private Event() {
	}

	public Event(String name, Location location, Set<Person> subscriber, String dateTime) {
		super();
		this.name = name;
		this.location = location;
		this.subscriber = subscriber;
		this.dateTime = dateTime;
	}

	public String getDateTime() {
		return dateTime;
	}

	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public Set<Person> getSubscriber() {
		return subscriber;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSubscriber(Set<Person> subscriber) {
		this.subscriber = subscriber;
	}

}
