package it.template.jwt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.template.session.Session;
import it.template.session.SessionBean;



@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private Session session;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Override
	public UserDetails loadUserByUsername(String token) 
			throws UsernameNotFoundException
	{
		String ErrMsg = "";
		
		if (token == null || token.length() < 2) 
		{
			ErrMsg = "Nome utente assente o non valido";
			
			logger.warn(ErrMsg);
			
	    	throw new UsernameNotFoundException(ErrMsg); 
		} 
		Integer idUtente = jwtUtil.getIdUtente(token);
		if(session.verifyToken(idUtente, token)) {
			SessionBean sessionBean = session.get(idUtente);
			if(sessionBean != null) {
				UserBuilder builder = null;
				builder = org.springframework.security.core.userdetails.User.withUsername(sessionBean.getUsername());
				builder.disabled((sessionBean.getAttivo() ? false : true));
				builder.password(token);
				builder.authorities("User");
				return builder.build();
			}
		} else {
			ErrMsg = String.format("Token %s non valido!", token);
			
			logger.warn(ErrMsg);
			
			throw new UsernameNotFoundException(ErrMsg);
		}
		return null;
		
	}
	

	
}
	