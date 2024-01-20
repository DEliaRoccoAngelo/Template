package it.template.jwt.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import it.template.controller.bean.Authentication;
import it.template.session.TokenBlackList;

@Component
public class JwtTokenUtil implements Serializable {

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_CREATED = "iat";
	private static final long serialVersionUID = -3301605591108950415L;
	private Clock clock = DefaultClock.INSTANCE;

	@Autowired
	private JwtConfig jwtConfig;
	
	/*
	private static JwtTokenUtil service;
	
	
	private JwtTokenUtil() {
		 jwtConfig = new JwtConfig();
	}
	
	public static JwtTokenUtil getInstance() {
		if(JwtTokenUtil.service == null)  {
			JwtTokenUtil.service = new JwtTokenUtil();
		}
		return JwtTokenUtil.service;
	}

	*/
	public String getUsernameFromToken(String token) 
	{
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) 
	{
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) 
	{
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) 
	{
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) 
	{
		return Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(token).getBody();
	}
	
	public String getRuolo(String token) {
		String ruolo = null;
		Claims claims = getAllClaimsFromToken(token);
		ruolo = (String)claims.get("Ruolo");
		return ruolo;
	}
	
	public Integer getIdUtente(String token) {
		Integer idUtente = null;
		Claims claims = getAllClaimsFromToken(token);
		idUtente = (Integer)claims.get("ID_UTENTE");
		return idUtente;
	}

	public List<String> getRotte(String token) {
		Claims claims = getAllClaimsFromToken(token);
		List<String> rotte = (List<String>)claims.get("Rotte");
		return rotte;
	}
	
	public Boolean isTokenExpired(String token) 
	{
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(clock.now());
	}

	private Boolean ignoreTokenExpiration(String token) 
	{
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(Authentication userDetails) 
	{
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails);
	}

	private String doGenerateToken(Map<String, Object> claims, Authentication userDetails) 
	{
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);

		return Jwts.builder()
				.setHeaderParam("typ","JWT")
				.claim("authorities", Arrays.asList("USER"))
				//.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
				.compact();
	}

	public Boolean canTokenBeRefreshed(String token) 
	{
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) 
	{
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);

		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
				.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) 
	{
		//JwtUserDetails user = (JwtUserDetails) userDetails;
		
		final String username = getUsernameFromToken(token);
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private Date calculateExpirationDate(Date createdDate) 
	{
		return new Date(createdDate.getTime() + jwtConfig.getExpiration() * 1000);
	}
	
		
	public boolean inBlackListToken(String token) {
		TokenBlackList blackList = TokenBlackList.getIstance();
		if(blackList.getTokens().contains(token)) {
			return true;
		} 
		return false;
	}
	
	public static void invalidateToken(String token) {
		TokenBlackList blackList = TokenBlackList.getIstance();
		blackList.getTokens().add(token);
	}
}
