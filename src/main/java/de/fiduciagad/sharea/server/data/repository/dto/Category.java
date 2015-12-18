package de.fiduciagad.sharea.server.data.repository.dto;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

public class Category extends CouchDbDocument {

	private static final long serialVersionUID = 1L;
	private String title;
	private String imgSrc;
	private Set<String> icons;
	private String categoryConfigId;

	@JsonProperty("docType")
	@TypeDiscriminator(value = "doc.docType === 'Category'")
	private final String docType = "Category";

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private Category() {
	}

	public Category(String title, String imgSrc, Set<String> icons, String categoryConfigId) {
		super();
		this.title = title;
		this.imgSrc = imgSrc;
		this.icons = icons;
		this.categoryConfigId = categoryConfigId;
	}

	public String getCategoryConfigId() {
		return categoryConfigId;
	}

	public Set<String> getIcons() {
		return icons;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public String getTitle() {
		return title;
	}

	public void setCategoryConfigId(String categoryConfigId) {
		this.categoryConfigId = categoryConfigId;
	}

	public void setIcons(Set<String> icons) {
		this.icons = icons;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
