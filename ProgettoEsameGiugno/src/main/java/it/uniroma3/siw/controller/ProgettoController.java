package it.uniroma3.siw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.session.SessionData;
import it.uniroma3.siw.controller.validation.ProgettoValidator;
import it.uniroma3.siw.controller.validation.TagValidator;
import it.uniroma3.siw.controller.validation.TaskValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Progetto;
import it.uniroma3.siw.model.Tag;
import it.uniroma3.siw.model.Task;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.services.ProgettoService;
import it.uniroma3.siw.services.TagService;
import it.uniroma3.siw.services.TaskService;
import it.uniroma3.siw.services.UtenteService;

@Controller
public class ProgettoController {

	@Autowired
	UtenteService utenteService;

	@Autowired
	ProgettoService progettoService;

	@Autowired
	TagService tagService;

	@Autowired
	TaskService taskService;

	@Autowired
	SessionData sessionData;

	@Autowired
	ProgettoValidator progettoValidator;

	@Autowired
	TaskValidator taskValidator;

	@Autowired
	TagValidator tagValidator;


	// Rimanda a link dei progetti creati dall'utente corrente.
	@RequestMapping (value = {"/progetti"}, method = RequestMethod.GET)
	public String mostraMieiProgetti (Model model) {

		Utente utenteLoggato = sessionData.getUtenteLoggato();
		List<Progetto> progettiCreati = progettoService.getProgettiCreatiDaUtente(utenteLoggato);

		model.addAttribute("utenteLoggato", utenteLoggato);
		model.addAttribute("progettiCreati", progettiCreati);

		return "mieiProgetti";	
	}

	//Rimanda al singolo progetto preso dalla lista di progetti creati.
	@RequestMapping (value = {"/progetti/{progettoId}"}, method = RequestMethod.GET)
	public String mostraProgetto (Model model, @PathVariable Long progettoId) {

		Progetto progetto = progettoService.getProgetto(progettoId);
		Utente utenteLoggato = sessionData.getUtenteLoggato();

		if (progetto == null)
			return "redirect:/progetti";

		List<Utente> membri = this.utenteService.getMembriProgetto(progetto);
		if (!membri.contains(utenteLoggato) && 	!progetto.getUtenteProprietario().equals(utenteLoggato))
			return "redirect:/progetti";

		model.addAttribute("utenteLoggato", utenteLoggato);
		model.addAttribute("progetto", progetto);
		model.addAttribute("membri", membri);

		return "progetto";
	}

	@RequestMapping (value = {"/creazioneProgetto"}, method = RequestMethod.GET)
	public String creaProgettoForm (Model model) {
		Utente utenteLoggato = this.sessionData.getUtenteLoggato();

		model.addAttribute("utenteLoggato", utenteLoggato);
		model.addAttribute("progettoForm", new Progetto());
		return "creaProgetto";
	}


	@RequestMapping(value = "/creazioneProgetto", method = RequestMethod.POST)
	public String creazioneProgettoAvvenuta(@Valid @ModelAttribute("progettoForm") Progetto progetto,
			BindingResult progettoBindingResult,
			Model model) {

		Utente utenteLoggato = this.sessionData.getUtenteLoggato();

		progettoValidator.validate(progetto, progettoBindingResult);
		
		if (!progettoBindingResult.hasErrors()) {
			progetto.setUtenteProprietario(utenteLoggato);
			this.progettoService.saveProgetto(progetto);
			return "redirect:/progetti/" + progetto.getId();
		}

		model.addAttribute("utenteLoggato", utenteLoggato);

		//non andata a buon fine la creazione del progetto, rimanda alla creazione
		return "creaProgetto";

	}


	@RequestMapping(value="/cancellazioneProgetto/{id}", method= RequestMethod.GET)
	public String cancellaProgetto(@PathVariable("id") Long id, Model model) {
		Utente utenteLoggato = this.sessionData.getUtenteLoggato();
		model.addAttribute("utenteLoggato", utenteLoggato);
		List<Progetto> progettiCreati = this.progettoService.getProgettiCreatiDaUtente(utenteLoggato);
		this.progettoService.deleteProgettoId(id);
		model.addAttribute("progettiCreati", progettiCreati);		
		return "redirect:/progetti";
	}



	@RequestMapping (value = {"/aggiornaProgetto/{id}"}, method = RequestMethod.GET)
	public String creaFormAggiornamento (@PathVariable Long id, Model model) {	
		Utente utenteCorrente = this.sessionData.getUtenteLoggato();
		model.addAttribute("utenteCorrente", utenteCorrente);
		model.addAttribute("progettoForm", progettoService.getProgetto(id));
		return "aggiornaProgetto"; 
	}


	@RequestMapping(value="/aggiornaProgetto/{id}", method = RequestMethod.POST)
	public String aggiornaProgetto(@PathVariable Long id, @Valid @ModelAttribute("progettoForm") 
	Progetto progettoForm, BindingResult progettoBindingResult, Model model) {

		progettoValidator.validate(progettoForm, progettoBindingResult);
		Progetto progetto = this.progettoService.getProgetto(id);

		Credentials utente = this.sessionData.getCredenzialiUtenteLoggato();

		if (progetto!=null) {
			if (!progettoBindingResult.hasErrors()) {
				if (progetto.getUtenteProprietario().getId().equals(utente.getUtente().getId())) {
					progetto.setNome(progettoForm.getNome());
					this.progettoService.saveProgetto(progetto);
				}
				return "redirect:/progetti/" + id;

			}
			model.addAttribute("utente", utente);
			return "aggiornaProgetto";
		}

		return "redirect:/progetti/" + id;


	}

	@RequestMapping (value = {"/aggiuntaTag/{id}"}, method = RequestMethod.GET)
	public String creaFormAggiuntaTag (@PathVariable Long id, Model model) {	
		Utente utenteCorrente = this.sessionData.getUtenteLoggato();
		model.addAttribute("utenteCorrente", utenteCorrente);
		model.addAttribute("progettoForm", progettoService.getProgetto(id));
		model.addAttribute("tagForm", new Tag());
		return "aggiuntaTag"; 
	}

	@RequestMapping(value="/aggiuntaTag/{id}", method = RequestMethod.POST)
	public String aggiungiTagProgetto(@PathVariable ("id") Long id,
			@Valid @ModelAttribute("tagForm") Tag tagForm, BindingResult tagBindingResult,
			@ModelAttribute ("progettoForm") Progetto progettoForm, Model model) {

		this.tagValidator.validate(tagForm, tagBindingResult);
		progettoForm = this.progettoService.getProgetto(id);
		Credentials utente = this.sessionData.getCredenzialiUtenteLoggato();

		if (progettoForm!=null) {
			if (!tagBindingResult.hasErrors()) {
				if (progettoForm.getUtenteProprietario().getId().equals(utente.getUtente().getId())) {
					this.tagService.saveTag(tagForm);
					progettoForm.aggiungiTag(tagForm);
					this.progettoService.saveProgetto(progettoForm);
				}
				return "redirect:/progetti/" + id.toString();
			}
			model.addAttribute("utente", utente);
			return "aggiuntaTag";
		}
		return "redirect:'/progetti/" + id;
	}

	
	@RequestMapping (value = {"/aggiuntaTask/{id}"}, method = RequestMethod.GET)
	public String creaFormAggiuntaTask (@PathVariable Long id, Model model) {	
		Utente utenteCorrente = this.sessionData.getUtenteLoggato();
		model.addAttribute("utenteCorrente", utenteCorrente);
		model.addAttribute("progettoForm", progettoService.getProgetto(id));
		model.addAttribute("taskForm", new Task());
		return "aggiuntaTask"; 
	}

	@RequestMapping(value="/aggiuntaTask/{id}", method = RequestMethod.POST)
	public String aggiungiTaskProgetto(@PathVariable ("id") Long id,
			@Valid @ModelAttribute("taskForm") Task taskForm, BindingResult taskBindingResult,
			@ModelAttribute ("progettoForm") Progetto progettoForm, Model model) {

		this.taskValidator.validate(taskForm, taskBindingResult);
		progettoForm = this.progettoService.getProgetto(id);
		Credentials utente = this.sessionData.getCredenzialiUtenteLoggato();

		if (progettoForm!=null) {
			if (!taskBindingResult.hasErrors()) {
				if (progettoForm.getUtenteProprietario().getId().equals(utente.getUtente().getId())) {
					this.taskService.saveTask(taskForm);
					progettoForm.aggiungiTask(taskForm);
					this.progettoService.saveProgetto(progettoForm);
				}
				return "redirect:/progetti/" + id.toString();
			}
			model.addAttribute("utente", utente);
			return "aggiuntaTask";
		}
		return "redirect:'/progetti/" + id;
	}
}




