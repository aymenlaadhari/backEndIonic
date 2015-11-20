package de.fiduciagad.sharea.server.configuration;

public class DatabaseConfiguration {
	private String database;
	private String password;
	private String username;
	private String url;

	public DatabaseConfiguration(String username, String password, String database, String url) {
		this.username = username;
		this.password = password;
		this.database = database;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDatabase() {
		return database;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
}
