package de.fiduciagad.sharea.server.data.repository.dto;

import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Share extends CouchDbDocument {

	private static final long serialVersionUID = 1L;

	private String title;
	private String description;
	private String icon;

	private String startLocation;
	private String endLocation;

	private Date startDate;
	private Date endDate;

	private String owningPersonId;

	private Set<String> participantIds;
	private int participantLimit;

	private Set<String> categoryIds;

	@JsonProperty("docType")
	@TypeDiscriminator(value = "doc.docType === 'Share'")
	private final String docType = "Share";

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private Share() {
	}

	public Share(String title, String description, Set<String> categoryIds, String icon, String startLocation,
			String endLocation, Date startDate, Date endDate, String owningPersonId, Set<String> participantIds,
			int participantLimit) {
		super();
		this.title = title;
		this.description = description;
		this.categoryIds = categoryIds;
		this.icon = icon;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.startDate = startDate;
		this.endDate = endDate;
		this.owningPersonId = owningPersonId;
		this.participantIds = participantIds;
		this.participantLimit = participantLimit;
	}

	public Set<String> getCategoryIds() {
		return categoryIds;
	}

	public String getDescription() {
		return description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public String getIcon() {
		return icon;
	}

	public String getOwningPersonId() {
		return owningPersonId;
	}

	public Set<String> getParticipantIds() {
		return participantIds;
	}

	public int getParticipantLimit() {
		return participantLimit;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public String getTitle() {
		return title;
	}

	public void setCategory(Set<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setOwningPersonId(String owningPersonId) {
		this.owningPersonId = owningPersonId;
	}

	public void setParticipantLimit(int participantLimit) {
		this.participantLimit = participantLimit;
	}

	public void setParticipants(Set<String> participantIds) {
		this.participantIds = participantIds;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
