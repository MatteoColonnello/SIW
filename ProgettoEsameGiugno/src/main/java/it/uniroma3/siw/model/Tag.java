package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false)
	private String nome;
	
	@Column (nullable = false)
	private String colore;
	
	@Column (length = 200)
	private String descrizione;
	
	//Associazione con TASK
	@ManyToMany
	private List<Task> taskAssociati;
	
	public Tag () {
		this.taskAssociati= new ArrayList<>();
	}
	
	public Tag (String nome, String colore, String descrizione) {
		this.nome=nome;
		this.colore=colore;
		this.descrizione=descrizione;
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

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Task> getTaskAssociati() {
		return taskAssociati;
	}

	public void setTaskAssociati(List<Task> taskAssociati) {
		this.taskAssociati = taskAssociati;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(nome, tag.nome) && Objects.equals(colore, tag.colore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, colore);
    }


	@Override
	public String toString() {
		return "Tag [id=" + id + ", nome=" + nome + ", colore=" + colore + ", descrizione=" + descrizione + "]";
	}
	

}
