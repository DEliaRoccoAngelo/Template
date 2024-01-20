package it.template.persistent.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Table(name="utente")
@Audited
public class Utente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_utente", nullable = false)
	private Integer idUtente;
	
	@Basic
	@Column(name="email", unique = true)
	private String email;
	

	@Basic
	@Column(name="cellulare")
	private String cellulare;
	
	@Basic
	@Column(name="sospeso", nullable = false)
	private Boolean sospeso = true;
		
	@Basic
	@Column(name="active", nullable = false)
	private Boolean active = false;

	public Integer getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public Boolean getSospeso() {
		return sospeso;
	}

	public void setSospeso(Boolean sospeso) {
		this.sospeso = sospeso;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
