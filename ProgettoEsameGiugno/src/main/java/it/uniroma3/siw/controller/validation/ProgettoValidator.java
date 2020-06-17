package it.uniroma3.siw.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Progetto;
import it.uniroma3.siw.services.ProgettoService;

@Component
public class ProgettoValidator implements Validator {
	
	final Integer LUNGHEZZA_MAX = 20;
	final Integer LUNGHEZZA_MIN = 3;
	
	@Autowired
	private ProgettoService progettoService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Progetto.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Progetto progetto = (Progetto) o;
		String nome = progetto.getNome().trim();
		
		if (nome.trim().isEmpty())
			errors.rejectValue("nome", "necessario");
		else if (nome.length() < LUNGHEZZA_MIN || nome.length() > LUNGHEZZA_MAX)
			errors.rejectValue("nome", "dimensione");
		else if (this.progettoService.getProgetto(nome) != null)
			errors.rejectValue("nome", "duplicato");
		
	}

}
