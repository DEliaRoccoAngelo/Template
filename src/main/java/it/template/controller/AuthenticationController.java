package it.template.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import it.template.controller.bean.AccountRequest;
import it.template.controller.bean.Authentication;
import it.template.controller.bean.Esito;
import it.template.controller.bean.SignUpBean;
import it.template.jwt.security.JwtTokenUtil;
import it.template.persistent.service.UtenteService;
import it.template.pm.client.PmClient;
import it.template.pm.client.bean.ChangePasswordRequest;
import it.template.pm.client.bean.LoginResponse;
import it.template.pm.client.bean.RegistrazioneBean;
import it.template.pm.client.bean.RegistrazioneEsito;
import it.template.session.Session;
import it.template.session.SessionBean;
import it.template.session.TokenBlackList;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = "Authentication", value = "Authentication", description = "Controller to authenticate user")
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	final static Logger logger = Logger.getLogger(AuthenticationController.class);
	
	@Autowired
	private JwtTokenUtil jwtUtil;

	@Autowired
	private PmClient pmClient;
	
	@Autowired
	private Session session;
	
	@Autowired
	private UtenteService utenteService;
		
	@PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Authentication> login(@RequestBody AccountRequest account){
		Authentication authentication = null;
		try {
			LoginResponse loginResponse = pmClient.login(account.getUsername(),account.getPassword());
			if (loginResponse.getAttivazione() != null) {
				String ruolo  = jwtUtil.getRuolo(loginResponse.getToken());
				authentication = new Authentication();
				authentication.setUsername(loginResponse.getUsername());
				authentication.setAttivazione(loginResponse.getAttivazione()); 	
				TokenBlackList blackList = TokenBlackList.getIstance();
				if (blackList.getTokens().contains(loginResponse.getToken())) {
					blackList.getTokens().remove(loginResponse.getToken());
				}
				SessionBean sb = new SessionBean();
				sb.setToken(loginResponse.getToken());
				sb.setAttivo(true);
				sb.setUsername(loginResponse.getUsername());
				sb.setStart(new Date());
				sb.setAttivazione(loginResponse.getAttivazione());
				authentication.setToken(loginResponse.getToken());
				authentication.setRuolo(ruolo);
				authentication.setRotte(jwtUtil.getRotte(loginResponse.getToken()));
				session.put(jwtUtil.getIdUtente(loginResponse.getToken()), sb);
				return new ResponseEntity<Authentication>(authentication, HttpStatus.OK);	
			} else {
				return new ResponseEntity<Authentication>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			logger.error("ERRORE Login",e);
			return new ResponseEntity<Authentication>(HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	
	@PostMapping(value = "/logout")
	public ResponseEntity logout(){
		logger.info("ricevuta chiamata");
		String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
		Session session = Session.getIstance();
		session.remove(jwtUtil.getIdUtente(token));
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping(value = "/sign-up", produces = "application/json", consumes = "application/json")
	public ResponseEntity<RegistrazioneEsito> signUp(@RequestBody SignUpBean request){
		RegistrazioneBean bean = new RegistrazioneBean();
		bean.setFirstName(request.getFirstName());
		bean.setLastName(request.getLastName());
		bean.setUserName(request.getUserName());
		RegistrazioneEsito response = pmClient.signUp(bean);
		if(response.getEsito()) {
			utenteService.createUser(response.getId(), request.getUserName());
		}
		return new ResponseEntity<RegistrazioneEsito>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = "/activation/{codice}", produces = "application/json")
	public ResponseEntity<RegistrazioneEsito> activation(@PathVariable(name = "codice") String codice) {
		RegistrazioneEsito response = new RegistrazioneEsito();
		if(StringUtils.isNumeric(codice)) {
			response = pmClient.activation(codice);
			if(response.getEsito()) {
				utenteService.activeUser(response.getId());
				return new ResponseEntity<RegistrazioneEsito>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<RegistrazioneEsito>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			response.setEsito(false);
			response.setStatus("KO");
			response.setError("IMPOSSIBILI PROCEDERE CON L'ATTIVAZIONE");
			return new ResponseEntity<RegistrazioneEsito>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/change-password", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Esito> changePAssword(@RequestBody ChangePasswordRequest request){
		Esito response = pmClient.changePsw(request);
		return new ResponseEntity<Esito>(response, HttpStatus.OK);
	}
}
