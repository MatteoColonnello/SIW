package it.uniroma3.siw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.validation.CredentialsValidator;
import it.uniroma3.siw.controller.validation.UtenteValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.services.CredentialsService;

@Controller
public class AuthenticationController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UtenteValidator utenteValidator;

	@Autowired
	private CredentialsValidator credentialsValidator;

	// Mostra il form di registrazione.
	@RequestMapping(value = "/registrazioneUtente", method = RequestMethod.GET)
	public String mostraFormRegistrazione(Model model) {
		model.addAttribute("utenteForm", new Utente());
		model.addAttribute("credentialsForm", new Credentials());

		return "registrazioneUtente";
	}

	// Verifica che i dati inseriti nella form siano validi: se ciò avviene allora l'utente viene salvato
	// nel DB e viene mostrata la pagina di registrazione effettuata con successo, altrimenti
	// rimanda alla pagina di registrazione.
	@RequestMapping(value = "/registrazioneUtente", method = RequestMethod.POST)
	public String registrazioneUtente(@Valid @ModelAttribute("utenteForm") Utente utente,
			BindingResult userBindingResult,
			@Valid @ModelAttribute("credentialsForm") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {

		this.utenteValidator.validate(utente, userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);

		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			credentials.setUtente(utente);
			credentialsService.saveCredentials(credentials);
			return "registrazioneEffettuataConSuccesso";
		}
		return "registrazioneUtente";
	}


}
