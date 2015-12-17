package de.fiduciagad.sharea.server.data.repository.dto;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Location extends CouchDbDocument {

	private static final long serialVersionUID = 1L;

	private String location;

	@JsonProperty("type")
	@TypeDiscriminator(value = "doc.type === 'Location'")
	private final String type = "Location";

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private Location() {
	}

	public Location(String location) {
		super();
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
