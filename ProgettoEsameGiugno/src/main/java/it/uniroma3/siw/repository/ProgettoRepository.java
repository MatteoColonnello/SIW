package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.uniroma3.siw.model.Progetto;
import it.uniroma3.siw.model.Utente;

@Repository
public interface ProgettoRepository extends CrudRepository <Progetto, Long> {

	// Ricerca dei progetti sulla base del creatore
	public List<Progetto> findByUtenteProprietario (Utente utenteProprietario);

	// Ricerca dei progetti sulla base della visibilità del singolo utente
	public List<Progetto> findByUtentiConVisibilita (Utente utenteConVisibilita);
	
	// Ricerca di un progetto sull'unicità del nome
	public Optional<Progetto> findByNome (String nome);

	
	
	
	
	

}
