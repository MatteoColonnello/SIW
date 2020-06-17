package it.uniroma3.siw.controller.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Utente;

@Component
public class UtenteValidator implements Validator {

	final Integer LUNGHEZZA_MAX = 100;
	final Integer LUNGHEZZA_MIN = 2;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Utente utente = (Utente) o;
		String nome = utente.getNome().trim();
		String cognome = utente.getCognome().trim();
		
		if (nome.trim().isEmpty())
			errors.rejectValue("nome", "necessario");
		else if (nome.length() < LUNGHEZZA_MIN || nome.length() > LUNGHEZZA_MAX)
			errors.rejectValue("nome", "dimensione");
		
		if (cognome.trim().isEmpty())
			errors.rejectValue("cognome", "necessario");
		else if (cognome.length() < LUNGHEZZA_MIN || cognome.length() > LUNGHEZZA_MAX)
			errors.rejectValue("cognome", "dimensione");
	}

}
