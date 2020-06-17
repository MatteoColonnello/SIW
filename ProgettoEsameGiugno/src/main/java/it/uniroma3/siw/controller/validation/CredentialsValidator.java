package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.services.CredentialsService;

@Component
public class CredentialsValidator implements Validator {
	
	@Autowired 
	CredentialsService credentialsService;
	
	final Integer LUNGHEZZA_MAX_USERNAME = 20;
	final Integer LUNGHEZZA_MIN_USERNAME = 4;
	final Integer LUNGHEZZA_MAX_PASSWORD = 20;
	final Integer LUNGHEZZA_MIN_PASSWORD = 6;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Credentials.class.equals(clazz);
	}
	@Override
	public void validate(Object o, Errors errors) {
		Credentials credentials = (Credentials) o;
		String username = credentials.getUsername().trim();
		String password = credentials.getPassword().trim();
		
		if (username.trim().isEmpty())
			errors.rejectValue("username", "necessario");
		else if (username.length()<LUNGHEZZA_MIN_USERNAME || username.length() > LUNGHEZZA_MAX_USERNAME)
			errors.rejectValue("username", "dimensione");
		else if (this.credentialsService.getCredentials(username) != null)
			errors.rejectValue("username", "duplicato");
		
		if (password.trim().isEmpty())
			errors.rejectValue("password", "necessario");
		else if (password.length()<LUNGHEZZA_MIN_PASSWORD || password.length() > LUNGHEZZA_MAX_PASSWORD)
			errors.rejectValue("password", "dimensione");
	}
	
	

}
