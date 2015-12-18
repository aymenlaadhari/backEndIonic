package de.fiduciagad.sharea.server.data.repository.dto;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Comment extends CouchDbDocument {

	private static final long serialVersionUID = 1L;
	private String authorId;
	private String text;

	@JsonProperty("docType")
	@TypeDiscriminator(value = "doc.docType === 'Comment'")
	private final String docType = "Comment";

	private Comment() {
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDocType() {
		return docType;
	}
	
	

}
