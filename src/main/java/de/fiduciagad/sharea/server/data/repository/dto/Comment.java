package de.fiduciagad.sharea.server.data.repository.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Comment extends CouchDbDocument {

	private static final long serialVersionUID = 1L;
	private String owningPersonId;
	private String name;
	private String text;
	private String shareId;
	private Date commentDate;

	@JsonProperty("docType")
	@TypeDiscriminator(value = "doc.docType === 'Comment'")
	private final String docType = "Comment";

	public Comment(String owningPersonId, String text, String shareId, String name, Date commentDate) {
		this.owningPersonId = owningPersonId;
		this.text = text;
		this.shareId = shareId;
		this.name = name;
		this.commentDate = commentDate;
	}
	
	public Comment(){
		
	}


	public String getDocType() {
		return docType;
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


	public String getOwningPersonId() {
		return owningPersonId;
	}


	public void setOwningPersonId(String owningPersonId) {
		this.owningPersonId = owningPersonId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date timeStamp) {
		this.commentDate = timeStamp;
	}

}
