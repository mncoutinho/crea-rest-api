package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_CURSOS")
public class CursoCadastro {

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	private String nome;
	
	private String  titulo;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_CAMPI")
	private CampusCadastro campi;

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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public CampusCadastro getCampi() {
		return campi;
	}

	public void setCampi(CampusCadastro campi) {
		this.campi = campi;
	}

}
