package it.uniroma3.siw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Progetto;
import it.uniroma3.siw.model.Tag;
import it.uniroma3.siw.model.Task;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.ProgettoRepository;

@Service
public class ProgettoService {
	
	@Autowired
	private ProgettoRepository progettoRepository;
	
	@Transactional 
	public Progetto getProgetto(Long id) {
		Optional <Progetto> result = this.progettoRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional //Ricerca su nome progetto
	public Progetto getProgetto (String nome) {
		Optional <Progetto> result = this.progettoRepository.findByNome(nome);
		return result.orElse(null);
	}
	
	@Transactional
	public Progetto saveProgetto(Progetto progetto) {
		return this.progettoRepository.save(progetto);
	}
	
	@Transactional
	public void deleteProgetto(Progetto progetto) {
		this.progettoRepository.delete(progetto);
	}
	
	@Transactional
	public void deleteProgettoId(Long id) {
		this.progettoRepository.deleteById(id);
	}

	@Transactional
	public Progetto condividiProgettoConUtente(Progetto progetto, Utente utente) {
		progetto.aggiungiUtenteConVisibilita(utente);
		return this.progettoRepository.save(progetto);
	}
	
	@Transactional
	public Progetto aggiungiTagAProgetto(Progetto progetto, Tag tag) {
		progetto.aggiungiTag(tag);
		return this.progettoRepository.save(progetto);	
	}
	
	@Transactional
	public Progetto aggiungiTaskAProgetto (Progetto progetto, Task task) {
		progetto.aggiungiTask(task);
		return this.progettoRepository.save(progetto);	
	}

	@Transactional
	public List<Progetto> getProgettiCreatiDaUtente(Utente utente) {
		List <Progetto> progetti = new ArrayList<>();
		Iterable <Progetto> iterazione = this.progettoRepository.findByUtenteProprietario(utente);
		
		for (Progetto progetto : iterazione) {
			progetti.add(progetto);
		}
		
		return progetti;
	}

}
