package de.fiduciagad.sharea.server.rest.dto;

public class FindShares {

	private String startLocation;
	private int limit;

	public FindShares() {
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
