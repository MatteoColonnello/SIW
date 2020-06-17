package it.uniroma3.siw.model;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Credentials {
	
	public static final String RUOLO_ADMIN = "ADMIN";
	public static final String RUOLO_DEFAULT = "DEFAULT";
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false, unique = true)
	private String username;

	@Column (nullable = false)
	private String password;
	
	@Column(nullable = false, length = 10)
	private String ruolo;
	
	
	//Refers to: UTENTE
	@OneToOne (cascade = CascadeType.ALL)
	private Utente utente;
	
	public Credentials() {
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credentials)) return false;
        Credentials utente = (Credentials) o;
        return Objects.equals(username, utente.username) && Objects.equals(ruolo, utente.ruolo);    
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, ruolo);
    }

	@Override
	public String toString() {
		return "Credentials [id=" + id + ", username=" + username + ", ruolo=" + ruolo + "]";
	}
    
	
	

}
