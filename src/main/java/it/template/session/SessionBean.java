package it.template.session;

import java.util.Date;


public class SessionBean {

	private final long DELAY = 3600000; // pari a 60 minuti
	
	private String username;
	
	private Date start;
	
	private String profile;
	
	private Boolean attivo;
		
	private Integer idObject;
	
	private String ruolo;
	
	private Boolean attivazione = false;
	
	private String token;
	
	
	public boolean verifyTiming() {
		Date now = new Date();
		if((now.getTime() - start.getTime()) >DELAY) {
			return false;
		}else {
			start = now;
			return true;
		}
	}
	
	public void resumeTiming() {
		Date now = new Date();
		start = now;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public Integer getIdObject() {
		return idObject;
	}

	public void setIdObject(Integer idObject) {
		this.idObject = idObject;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Boolean getAttivazione() {
		return attivazione;
	}

	public void setAttivazione(Boolean attivazione) {
		this.attivazione = attivazione;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
