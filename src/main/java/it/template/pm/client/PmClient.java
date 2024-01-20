package it.template.pm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import it.template.controller.bean.Esito;
import it.template.pm.client.bean.ActivationBean;
import it.template.pm.client.bean.ChangePasswordRequest;
import it.template.pm.client.bean.LoginRequest;
import it.template.pm.client.bean.LoginResponse;
import it.template.pm.client.bean.RegistrazioneBean;
import it.template.pm.client.bean.RegistrazioneEsito;


@Component
public class PmClient {

		
	@Value("${pm.url}")
	private String urlService;
	
	@Value("${app_name}")
	private  String app;
	
	@Value("${app_role}")
	private  String role;
	
	@Value("${attivation_page}")
	private  String attivationPage;
	
	public LoginResponse login(String username, String password) throws HttpClientErrorException{
		RestTemplate restTemplate = new RestTemplate();
		LoginRequest bean = new LoginRequest(username, password);
		String url = urlService+"/authentication/login";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity requestHeader = new HttpEntity(headers);
		LoginResponse response = restTemplate.postForObject(url, bean, LoginResponse.class);
		return response;
	}
	
	
	public void logout(String token) {
		RestTemplate restTemplate = new RestTemplate();
		String url = urlService+"/authentication/login";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer "+token);
		HttpEntity requestHeader = new HttpEntity(headers);
		Object response = restTemplate.postForEntity(url, requestHeader, Object.class);
		
	}
	
	
	public RegistrazioneEsito signUp( RegistrazioneBean bean) {
		bean.setApp(app);
		bean.setRole(role);
		bean.setAttivazionePage(attivationPage);
		RestTemplate restTemplate = new RestTemplate();
		String url = urlService+"/authentication/sign-up";
		return  restTemplate.postForObject(url, bean, RegistrazioneEsito.class);
	}
	
	public RegistrazioneEsito activation( String code) {
		ActivationBean bean = new ActivationBean();
		bean.setApp(app);
		bean.setIdUserApp(Integer.valueOf(code));
		RestTemplate restTemplate = new RestTemplate();
		String url = urlService+"/authentication/activation";
		return  restTemplate.postForObject(url, bean, RegistrazioneEsito.class);
	}
	
	public Esito changePsw( ChangePasswordRequest bean) {
		bean.setApp(app);
		RestTemplate restTemplate = new RestTemplate();
		String url = urlService+"/utente/cambio-pass";
		return  restTemplate.postForObject(url, bean, Esito.class);
	}
	
}
