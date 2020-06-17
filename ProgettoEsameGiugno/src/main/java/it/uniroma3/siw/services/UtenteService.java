package it.uniroma3.siw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Progetto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Transactional
	public Utente getUtente(long id) {
		Optional<Utente> result = this.utenteRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Utente saveUtente(Utente utente) {
		return this.utenteRepository.save(utente);
	}

	@Transactional
	public List<Utente> getAllUtenti() {
		List<Utente> result = new ArrayList<>();
		Iterable<Utente> iterable = this.utenteRepository.findAll();
		for(Utente Utente : iterable)
			result.add(Utente);
		return result;
	}

	@Transactional
	public List<Utente> getMembriProgetto(Progetto progetto){	
		List<Utente> utenti = new ArrayList<>();
		Iterable <Utente> iterable = this.utenteRepository.findByProgettiVisibili(progetto);
		
		for (Utente utente : iterable)
			utenti.add(utente);
		
		return utenti;
	}
}
