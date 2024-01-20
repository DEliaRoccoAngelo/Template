package it.template.persistent.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import it.template.persistent.entity.Utente;

@Component
public class UtenteDao extends DaoImpl{
	
	public Utente detail(Integer idUtente) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Utente> query = cb.createQuery(Utente.class);
		Root<Utente> root = query.from(Utente.class);
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get("idUtente"), idUtente));
		query.where(predicateList.toArray(new Predicate[predicateList.size()]));
		try {
			return em.createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
