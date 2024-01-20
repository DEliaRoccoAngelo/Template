package it.template.controller.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;



public class Authentication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;
	
	
	private String username;
	
	private Boolean attivazione = false;
	
	private String ruolo = "USER";
	
	private List<String> rotte;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
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

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public List<String> getRotte() {
		return rotte;
	}

	public void setRotte(List<String> rotte) {
		this.rotte = rotte;
	}

	
}
