package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Progetto;
import it.uniroma3.siw.model.Utente;

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Long> {
	
	
	// Riporta tutti gli utenti che hanno visibilita su un determinato progetto
	public List<Utente> findByProgettiVisibili (Progetto progetto);
	
	
}
