package it.template.persistent.service;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.template.persistent.dao.UtenteDao;
import it.template.persistent.entity.Utente;

@Service
public class UtenteService {
	
	final static Logger logger = Logger.getLogger(UtenteService.class);
	
	@Autowired
	private UtenteDao utenteDao;
	
	@Transactional(readOnly = true)
	public Utente detail(Integer idUtente) {
		return utenteDao.detail(idUtente);
	}
	
	
	@Transactional(readOnly = false)
	public boolean createUser(Integer idUser, String email) {
		Utente user = new Utente();
		user.setSospeso(true);
		user.setActive(false);
		user.setEmail(email);
		user.setIdUtente(idUser);
		return utenteDao.persist(user);		
	}
	
	@Transactional(readOnly = false)
	public boolean activeUser(Integer idUser) {
		Utente user = utenteDao.detail(idUser);
		if(user != null) {
			user.setActive(true);
			user.setSospeso(false);
			return utenteDao.update(user);	
		}
		return false;		
	}
	
	
	@Transactional(readOnly = false)
	public boolean save(Serializable a){
		try {
			utenteDao.persist(a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional(readOnly = false)
	public boolean delete(Serializable a){
		try {
			utenteDao.remove(a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	

	@Transactional(readOnly = false)
	public boolean update(Serializable a){
		try {
			utenteDao.update(a);
			return true;
		} catch (Exception e) {
			logger.error("ERRORE UPDATE ", e);
			return false;
		}
	}
}
