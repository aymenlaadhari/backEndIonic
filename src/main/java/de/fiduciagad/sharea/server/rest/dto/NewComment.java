package de.fiduciagad.sharea.server.rest.dto;

import java.util.Date;

public class NewComment {

	private String text;
	private String shareId;
	private Date commentDate;
	private String owningPersonId;
	
	public NewComment(){
		
	}

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

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public String getOwningPersonId() {
		return owningPersonId;
	}

	public void setOwningPersonId(String owningPersonId) {
		this.owningPersonId = owningPersonId;
	}

}
