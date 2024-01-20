package it.template.pm.client.bean;

import java.io.Serializable;

public class LoginResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private Boolean attivazione;
	
	private String app;
	
	private String token;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getAttivazione() {
		return attivazione;
	}

	public void setAttivazione(Boolean attivazione) {
		this.attivazione = attivazione;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	

}
