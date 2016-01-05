package de.fiduciagad.sharea.server.rest.dto;

import java.util.Date;

public class NewShare {

	private String title;

	private String description;
	private String icon;
	private String startLocation;

	private String endLocation;
	private Date startDate;

	private Date endDate;
	private int participantLimit;

	private String categoryId;

	public NewShare() {
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

	public void setCategoryId(String categoryId) {
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

}
