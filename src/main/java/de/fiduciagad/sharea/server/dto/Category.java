package de.fiduciagad.sharea.server.dto;

public class Category {

	private String _id;
	private String _rev;
	private String title;
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String[] getIcons() {
		return icons;
	}

	public void setIcons(String[] icons) {
		this.icons = icons;
	}

	public CategoryConfig getConfig() {
		return config;
	}

	public void setConfig(CategoryConfig config) {
		this.config = config;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String imgSrc;
	private String [] icons;
	
	private CategoryConfig config;
	
	private String type;
	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}
	
}
