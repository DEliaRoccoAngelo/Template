package it.template.persistent.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public class DaoImpl{

Logger logger = Logger.getLogger(DaoImpl.class);
	
	@PersistenceContext
	protected EntityManager em;
	
	public boolean persist(Serializable arg) {
		try {			
			em.persist(arg);
			return true;
		}catch (Exception e) {
			logger.error("ERRORE SALVATAGGIO", e);
			return false;
		}
	}
	
	public boolean update(Serializable arg) {
		try {			
			em.merge(arg);
			return true;
		}catch (Exception e) {
			logger.error("ERRORE AGGIORNAMENTO", e);
			return false;
		}
	}
	
	public boolean remove(Serializable arg) {
		try {	
			arg  = em.merge(arg);
			em.remove(arg);
			return true;	
		}catch (Exception e) {
			logger.error("ERRORE REMOVE", e);
			return false;
		}
	}
}
