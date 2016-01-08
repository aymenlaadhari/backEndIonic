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

	private String owningPersonNickname;

	private Set<String> participantNicknames;
	private int participantLimit;

	private String categoryId;

	@JsonProperty("docType")
	@TypeDiscriminator(value = "doc.docType === 'Share'")
	private final String docType = "Share";

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private Share() {
	}

	public Share(String title, String description, String categoryId, String icon, String startLocation,
			String endLocation, Date startDate, Date endDate, String owningPersonNickname,
			Set<String> participantNicknames, int participantLimit) {
		super();
		this.title = title;
		this.description = description;
		this.categoryId = categoryId;
		this.icon = icon;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.startDate = startDate;
		this.endDate = endDate;
		this.setOwningPersonNickname(owningPersonNickname);
		this.setParticipantNicknames(participantNicknames);
		this.participantLimit = participantLimit;
	}

	public String getCategoryId() {
		return categoryId;
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

	public void setCategory(String categoryId) {
		this.categoryId = categoryId;
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

	public void setParticipantLimit(int participantLimit) {
		this.participantLimit = participantLimit;
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

	public Set<String> getParticipantNicknames() {
		return this.participantNicknames;
	}

	public void setParticipantNicknames(Set<String> participantNicknames) {
		this.participantNicknames = participantNicknames;
	}

	public String getOwningPersonNickname() {
		return this.owningPersonNickname;
	}

	public void setOwningPersonNickname(String owningPersonNickname) {
		this.owningPersonNickname = owningPersonNickname;
	}

}
