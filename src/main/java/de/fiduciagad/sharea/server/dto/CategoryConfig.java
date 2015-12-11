package de.fiduciagad.sharea.server.dto;

public class CategoryConfig {

	//TODO: Die Variablennamen sollten umbenannt werden um dem Boolean wert zu entsprechen
	//z.B 'showTitle' ist jedoch auch im Frontend nachzuziehen
	
	private boolean title;
	private boolean description;
	private boolean startTime;
	private boolean endtime;
	private boolean location;
	private boolean endlocation;
	private boolean member;
	public boolean isTitle() {
		return title;
	}
	public void setTitle(boolean title) {
		this.title = title;
	}
	public boolean isDescription() {
		return description;
	}
	public void setDescription(boolean description) {
		this.description = description;
	}
	public boolean isStartTime() {
		return startTime;
	}
	public void setStartTime(boolean startTime) {
		this.startTime = startTime;
	}
	public boolean isEndtime() {
		return endtime;
	}
	public void setEndtime(boolean endtime) {
		this.endtime = endtime;
	}
	public boolean isLocation() {
		return location;
	}
	public void setLocation(boolean location) {
		this.location = location;
	}
	public boolean isEndlocation() {
		return endlocation;
	}
	public void setEndlocation(boolean endlocation) {
		this.endlocation = endlocation;
	}
	public boolean isMember() {
		return member;
	}
	public void setMember(boolean member) {
		this.member = member;
	}

}
