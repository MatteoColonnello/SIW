package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

@Entity
public class Utente {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;

	@Column (nullable = false)
	private String nome;

	@Column (nullable = false)
	private String cognome;

	@Column (updatable=false, nullable=false)
	private LocalDateTime dataCreazione;
	
	//Associazioni con classe PROGETTO
	@OneToMany (mappedBy = "utenteProprietario", cascade = CascadeType.REMOVE)
	private List<Progetto> progettiCreati;
	
	@ManyToMany (mappedBy = "utentiConVisibilita" )
	private List<Progetto> progettiVisibili;
	
	//Associazioni con classe TASK
	@OneToMany (mappedBy = "utenteAssegnato")
	private List <Task> taskVisibili;
	
	public Utente () {
		this.progettiCreati=new ArrayList<>();
		this.progettiVisibili=new ArrayList<>();
	}

	public Utente (String nome, String cognome) {
		this.nome=nome;
		this.cognome=cognome;
	}


	@PrePersist
	protected void onPersist() {
		this.dataCreazione=LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Progetto> getProgettiCreati() {
		return progettiCreati;
	}

	public void setProgettiCreati(List<Progetto> progettiCreati) {
		this.progettiCreati = progettiCreati;
	}

	public List<Progetto> getProgettiVisibili() {
		return progettiVisibili;
	}

	public void setProgettiVisibili(List<Progetto> progettiVisibili) {
		this.progettiVisibili = progettiVisibili;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public List<Task> getTaskVisibili() {
		return taskVisibili;
	}

	public void setTaskVisibili(List<Task> taskVisibili) {
		this.taskVisibili = taskVisibili;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((dataCreazione == null) ? 0 : dataCreazione.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (dataCreazione == null) {
			if (other.dataCreazione != null)
				return false;
		} else if (!dataCreazione.equals(other.dataCreazione))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", dataCreazione=" + dataCreazione
				+ ", progettiCreati=" + progettiCreati + "]";
	}

}
