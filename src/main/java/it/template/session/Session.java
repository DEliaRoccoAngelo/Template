package it.template.session;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.template.jwt.security.JwtTokenUtil;



@Component
public class Session {
	
	final static Logger logger = Logger.getLogger(Session.class);
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	private Map<Integer, SessionBean> map;
	
	private  static Session session;

	public Session() {
		map = new HashMap<Integer, SessionBean>();
	}
	
	public static Session getIstance() {
		if(Session.session == null) {
			session = new Session();
		}
		return Session.session;
	}
	
	public void put(Integer idUser, SessionBean value) {
		map.put(idUser, value);
	}
	
	public SessionBean get(Integer idUser) {
		if(map.containsKey(idUser)) {
			return map.get(idUser);
		}else {
			return null;
		}
	}
	
	public void remove(Integer idUser) {
		if(map.containsKey(idUser)) {
			SessionBean sb = map.get(idUser);
			JwtTokenUtil.invalidateToken(sb.getToken());
			map.remove(idUser);
		}
	}
	
	public boolean verifyToken(Integer idUtente, String token) {
		boolean result = false;
		
		if(map.containsKey(idUtente)) {
			if (jwtUtil.isTokenExpired(token)) {
				return false;
			} else {
				return true;
			}
		}
		return result;
	}
	
}
