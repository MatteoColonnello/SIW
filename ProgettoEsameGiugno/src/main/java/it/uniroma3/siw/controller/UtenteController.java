package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.validation.UtenteValidator;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.controller.session.SessionData;

@Controller
public class UtenteController {

	@Autowired
	UtenteRepository utenteRepository;

	@Autowired
	UtenteValidator utenteValidator;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	SessionData sessionData;

	@RequestMapping(value = { "/paginaUtente" }, method = RequestMethod.GET)
	public String paginaUtente(Model model) {
		Utente utenteLoggato = sessionData.getUtenteLoggato();
		model.addAttribute("utente", utenteLoggato);
		return "paginaUtente";
	}
	
	 /**
     * This method is called when a GET request is sent by the user to URL "/users/user_id".
     * This method prepares and dispatches the User registration view.
     *
     * @param model the Request model
     * @return the name of the target view, that in this case is "register"
     */

	
	//MOSTRA PROFILO UTENTE
	@RequestMapping (value = "/profiloUtente", method = RequestMethod.GET)
    public String profilo(Model model) {
        Utente utenteLoggato = sessionData.getUtenteLoggato();
        Credentials credentials = sessionData.getCredenzialiUtenteLoggato();
        System.out.println(credentials.getPassword());
        model.addAttribute("utente", utenteLoggato);
        model.addAttribute("credentials", credentials);

        return "profiloUtente";
    }

    /**
     * This method is called when a GET request is sent by the user to URL "/users/user_id".
     * This method prepares and dispatches the User registration view.
     *
     * @param model the Request model
     * @return the name of the target view, that in this case is "register"
     */
	
	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
    public String admin(Model model) {
        Utente utenteLoggato = sessionData.getUtenteLoggato();
        model.addAttribute("utente", utenteLoggato);
        return "admin";
    }
	

	

}
