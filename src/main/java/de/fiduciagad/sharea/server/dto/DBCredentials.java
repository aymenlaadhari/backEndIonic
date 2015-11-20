package de.fiduciagad.sharea.server.dto;

public class DBCredentials {
	
	private final long id;
	private final String credentials;
	
	public DBCredentials(long id, String credentials){
		this.id=id;
		this.credentials=credentials;
	}

	public long getId() {
		return id;
	}

	public String getCredentials() {
		return credentials;
	}
	

}
