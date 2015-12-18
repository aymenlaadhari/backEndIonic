package de.fiduciagad.sharea.server.data.repository.dto;

import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Event extends CouchDbDocument {

	private static final long serialVersionUID = 1L;

	private String name;

	private String location;

	private Set<String> participantIds;

	private Date dateTime;

	@JsonProperty("docType")
	@TypeDiscriminator(value = "doc.docType === 'Event'")
	private final String docType = "Event";

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private Event() {
	}

	public Event(String name, String location, Set<String> participantIds, Date dateTime) {
		super();
		this.name = name;
		this.location = location;
		setParticipantIds(participantIds);
		this.dateTime = dateTime;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public Set<String> getParticipantIds() {
		return participantIds;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParticipantIds(Set<String> participantIds) {
		this.participantIds = participantIds;
	}

}
