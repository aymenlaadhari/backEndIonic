package de.fiduciagad.sharea.server.data.repository.dto;

import org.ektorp.support.CouchDbDocument;

public class CategoryConfig extends CouchDbDocument {

	private static final long serialVersionUID = 1L;

	private boolean showTitle;

	private boolean showDescription;

	private boolean showStartTime;

	private boolean showEndtime;

	private boolean showLocation;

	private boolean showEndLocation;

	private boolean ShowParticipants;

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private CategoryConfig() {
	}

	public CategoryConfig(boolean showTitle, boolean showDescription, boolean showStartTime, boolean showEndtime,
			boolean showLocation, boolean showEndLocation, boolean showParticipants) {
		super();
		this.showTitle = showTitle;
		this.showDescription = showDescription;
		this.showStartTime = showStartTime;
		this.showEndtime = showEndtime;
		this.showLocation = showLocation;
		this.showEndLocation = showEndLocation;
		ShowParticipants = showParticipants;
	}

	public boolean isShowDescription() {
		return showDescription;
	}

	public boolean isShowEndLocation() {
		return showEndLocation;
	}

	public boolean isShowEndtime() {
		return showEndtime;
	}

	public boolean isShowLocation() {
		return showLocation;
	}

	public boolean isShowParticipants() {
		return ShowParticipants;
	}

	public boolean isShowStartTime() {
		return showStartTime;
	}

	public boolean isShowTitle() {
		return showTitle;
	}

	public void setShowDescription(boolean showDescription) {
		this.showDescription = showDescription;
	}

	public void setShowEndLocation(boolean showEndLocation) {
		this.showEndLocation = showEndLocation;
	}

	public void setShowEndtime(boolean showEndtime) {
		this.showEndtime = showEndtime;
	}

	public void setShowLocation(boolean showLocation) {
		this.showLocation = showLocation;
	}

	public void setShowParticipants(boolean showParticipants) {
		ShowParticipants = showParticipants;
	}

	public void setShowStartTime(boolean showStartTime) {
		this.showStartTime = showStartTime;
	}

	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}

}
