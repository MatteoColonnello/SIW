package it.uniroma3.siw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Commento {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (length = 500)
	private String commento;
	
	public Commento (String commento) {
		this.commento=commento;
	}
	
	public void setCommento (String commento) {
		this.commento=commento;
	}
	
	public String getCommento () {
		return this.commento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
