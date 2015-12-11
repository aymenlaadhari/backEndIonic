package de.fiduciagad.sharea.server.dto;

public class Share {

	private String _id;
	private String _rev;
	private String title;
	private String content;
	private String icon;
	
	private String placeFrom;
	private String placeTo;
	
	public String timeFrom;
	public String timeUntil;
	
	private String ownerId;
	private Person[] shareWith;
	
	public int maxmember;
	
	public Category category;
	
	public String type;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPlaceFrom() {
		return placeFrom;
	}

	public void setPlaceFrom(String placeFrom) {
		this.placeFrom = placeFrom;
	}

	public String getPlaceTo() {
		return placeTo;
	}

	public void setPlaceTo(String placeTo) {
		this.placeTo = placeTo;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeUntil() {
		return timeUntil;
	}

	public void setTimeUntil(String timeUntil) {
		this.timeUntil = timeUntil;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public int getMaxmember() {
		return maxmember;
	}

	public void setMaxmember(int maxmember) {
		this.maxmember = maxmember;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Share(String _id) {
		this.set_id(_id);
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Person[] getShareWith() {
		return shareWith;
	}

	public void setShareWith(Person[] shareWith) {
		this.shareWith = shareWith;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

}
