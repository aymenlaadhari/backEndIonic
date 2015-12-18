package de.fiduciagad.sharea.server.rest.dto;

public class NewAccount {

	private String email;
	private String password;
	private String realname;
	private String deviceName;
	private String deviceIdentifier;

	public NewAccount() {
	}

	public String getDeviceIdentifier() {
		return deviceIdentifier;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getRealname() {
		return realname;
	}

	public void setDeviceIdentifier(String deviceIdentifier) {
		this.deviceIdentifier = deviceIdentifier;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

}
