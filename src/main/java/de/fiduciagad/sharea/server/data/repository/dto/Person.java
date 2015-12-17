package de.fiduciagad.sharea.server.data.repository.dto;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Person extends CouchDbDocument {

	private static final long serialVersionUID = 1L;

	private String name;

	private String owningAccountId;

	@JsonProperty("type")
	@TypeDiscriminator(value = "doc.type === 'Person'")
	private final String type = "Person";

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private Person() {
	}

	public Person(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getOwningAccountId() {
		return owningAccountId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwningAccountId(String owningAccountId) {
		this.owningAccountId = owningAccountId;
	}

}
