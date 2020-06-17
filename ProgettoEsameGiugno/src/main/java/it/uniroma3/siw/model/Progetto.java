package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

@Entity
public class Progetto {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false)
	private String nome;
	
	@Column (updatable=false, nullable=false)
	private LocalDateTime dataInizio;
	
	// Associazioni con classe UTENTE
	@ManyToOne
	private Utente utenteProprietario;
	
	@ManyToMany
	private List<Utente> utentiConVisibilita;
	
	// Associazioni con classe TASK
	@OneToMany (fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn (name="progetto_id")
	private List<Task> tasks;
	
	// Associazioni con classe TAG
	@OneToMany (cascade = {CascadeType.ALL})
	@JoinColumn (name="progetto_id")
	private List<Tag> tags;
	
	
	public Progetto () {
		this.tasks= new ArrayList<>();
		this.utentiConVisibilita = new ArrayList<>();
		this.tags= new ArrayList<>();
	}
	
	public Progetto (String nome) {
		this.nome = nome;	
	}
	
	@PrePersist
	protected void onPersist() {
		this.dataInizio=LocalDateTime.now();
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

	public Utente getUtenteProprietario() {
		return utenteProprietario;
	}

	public void setUtenteProprietario(Utente utenteProprietario) {
		this.utenteProprietario = utenteProprietario;
	}

	public List<Utente> getUtentiConVisbilita() {
		return utentiConVisibilita;
	}

	public void setUtentiConVisbilita(List<Utente> utentiConVisbilita) {
		this.utentiConVisibilita = utentiConVisbilita;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void aggiungiUtenteConVisibilita(Utente utente) {
		this.utentiConVisibilita.add(utente);
	}

	public void aggiungiTag(Tag tag) {
		this.tags.add(tag);
	}

	public void aggiungiTask(Task task) {
		this.tasks.add(task);
	}
	
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Progetto progetto = (Progetto) o;
        return Objects.equals(nome, progetto.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

	@Override
	public String toString() {
		return "Progetto [id=" + id + ", nome=" + nome + ", tasks=" + tasks + ", tags=" + tags + "]";
	}
	
	

}
