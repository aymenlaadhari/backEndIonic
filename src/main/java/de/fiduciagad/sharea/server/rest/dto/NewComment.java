package de.fiduciagad.sharea.server.rest.dto;

public class NewComment {

	private String text;
	private String shareId;

	public String getShareId() {
		return shareId;
	}

	public String getText() {
		return text;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
	}

	public void setText(String text) {
		this.text = text;
	}

}
