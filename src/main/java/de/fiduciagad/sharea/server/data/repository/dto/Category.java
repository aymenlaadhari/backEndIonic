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
	private CategoryConfig categoryConfig;

	@JsonProperty("docType")
	@TypeDiscriminator(value = "doc.docType === 'Category'")
	private final String docType = "Category";

	// Used for deserialization of the CouchDBDocument
	@SuppressWarnings("unused")
	private Category() {
	}

	public Category(String title, String imgSrc, Set<String> icons, CategoryConfig categoryConfig) {
		super();
		this.title = title;
		this.imgSrc = imgSrc;
		this.icons = icons;
		this.categoryConfig = categoryConfig;
	}

	public CategoryConfig getCategoryConfig() {
		return categoryConfig;
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

	public void setCategoryConfig(CategoryConfig categoryConfig) {
		this.categoryConfig = categoryConfig;
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
