package de.fiduciagad.sharea.server.dto;

public class ShareInputDTO {
	
	private String placeFrom;
	private Integer limit;
	
	public ShareInputDTO(String placeFrom, Integer limit) {
		this.placeFrom = placeFrom;
		this.limit = limit;
	}
	
	

	public ShareInputDTO() {
	}



	public String getPlaceFrom() {
		return placeFrom;
	}

	public void setPlaceFrom(String placeFrom) {
		this.placeFrom = placeFrom;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	

}
