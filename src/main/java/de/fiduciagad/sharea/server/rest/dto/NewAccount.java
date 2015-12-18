package de.fiduciagad.sharea.server.rest.dto;

import javax.validation.constraints.NotNull;

public class NewAccount {

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String realname;

	@NotNull
	private String deviceName;

	@NotNull
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
