package it.template.pm.client.bean;

import java.io.Serializable;

public class RegistrazioneBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String firstName;
	
	private String lastName;
	
	private String role;
	
	private String app;
	
	private String attivazionePage;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getAttivazionePage() {
		return attivazionePage;
	}

	public void setAttivazionePage(String attivazionePage) {
		this.attivazionePage = attivazionePage;
	}
	
}
