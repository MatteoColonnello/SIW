package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

@Entity
public class Task {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column (nullable = false)
	private String nometask;

	@Column (length = 200)
	private String descrizione;

	@Column (updatable=false, nullable=false)
	private LocalDateTime dataCreazione;

	//Associazione con TAG
	@ManyToMany (mappedBy = "taskAssociati", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private List<Tag> tagAssociati;

	//Associazione con UTENTE
	@ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Utente utenteAssegnato;

	//Associazione con COMMENTO
	@OneToMany 
	private List <Commento> commenti;

	public Task() {
		this.tagAssociati= new ArrayList<>();
		this.commenti= new ArrayList<>();
	}


	public Task (String nometask, String descrizione) { this.nometask=nometask;
	this.descrizione=descrizione; /* this.commenti=new ArrayList<>(); */ }


	@PrePersist
	protected void onPersist () {
		this.dataCreazione = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNometask() {
		return nometask;
	}

	public void setNometask(String nometask) {
		this.nometask = nometask;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public List<Tag> getTagAssociati() {
		return tagAssociati;
	}

	public void setTagAssociati(List<Tag> tagAssociati) {
		this.tagAssociati = tagAssociati;
	}

	public void aggiungiTag (Tag tag) {
		this.tagAssociati.add(tag);
	}

	public void aggiungiCommento (Commento commento) {
		this.commenti.add(commento);
	}

	public List<Commento> getCommenti () {
		return this.commenti;
	}

	public Utente getUtenteAssegnato() {
		return utenteAssegnato;
	}

	public void setUtenteAssegnato(Utente utenteAssegnato) {
		this.utenteAssegnato = utenteAssegnato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCreazione == null) ? 0 : dataCreazione.hashCode());
		result = prime * result + ((nometask == null) ? 0 : nometask.hashCode());
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
		Task other = (Task) obj;
		if (dataCreazione == null) {
			if (other.dataCreazione != null)
				return false;
		} else if (!dataCreazione.equals(other.dataCreazione))
			return false;
		if (nometask == null) {
			if (other.nometask != null)
				return false;
		} else if (!nometask.equals(other.nometask))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", nometask=" + nometask + ", descrizione=" + descrizione + ", dataCreazione=" + dataCreazione
				+ ", commenti=" + commenti + ", tagAssociati=" + tagAssociati + "]";
	}

}
