package de.fiduciagad.sharea.server.configuration;

public class DatabaseConfiguration {
	private String database;
	private String password;
	private String username;

	public DatabaseConfiguration(String username, String password, String database) {
		this.username = username;
		this.password = password;
		this.database = database;
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
