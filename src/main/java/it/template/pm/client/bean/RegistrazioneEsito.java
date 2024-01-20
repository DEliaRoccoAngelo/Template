package it.template.pm.client.bean;

public class RegistrazioneEsito {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean esito;
	
	private Integer id;
	
	private String message;
	
	private String error;
	
	private String codeError;
	
	private String status; //OK,KO
	
	private Boolean sospeso;
	
	public Boolean getSospeso() {
		return sospeso;
	}

	public void setSospeso(Boolean sospeso) {
		this.sospeso = sospeso;
	}

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCodeError() {
		return codeError;
	}

	public void setCodeError(String codeError) {
		this.codeError = codeError;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
