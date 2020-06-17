package it.uniroma3.siw.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired 
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public Credentials saveCredentials (Credentials credentials) {
		credentials.setRuolo(Credentials.RUOLO_DEFAULT);
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialsRepository.save(credentials);
	}

	@Transactional //Ricerca su id
	public Credentials getCredentials (Long id) {
		Optional <Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional //Ricerca su username
	public Credentials getCredentials (String username) {
		Optional <Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}

}
