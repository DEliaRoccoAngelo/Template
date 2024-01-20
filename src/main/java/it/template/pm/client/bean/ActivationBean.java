package it.template.pm.client.bean;

import java.io.Serializable;

public class ActivationBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idUserApp;
	
	private String app;

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public Integer getIdUserApp() {
		return idUserApp;
	}

	public void setIdUserApp(Integer idUserApp) {
		this.idUserApp = idUserApp;
	}
	
	
}
