package br.org.crea.commons.models.cadastro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CAD_ESPECIALIDADES")
public class Especialidade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO")
	private Long id;

	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="DESCRICAO_FEMININO")
	private String descricaoFeminino;
	
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_TITULOS")
	private Titulo titulo;
	
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_CONFEA_TITULOS")
	private ConfeaTitulo confeaTitulo;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricaoFeminino() {
		return descricaoFeminino;
	}

	public void setDescricaoFeminino(String descricaoFeminino) {
		this.descricaoFeminino = descricaoFeminino;
	}

	public Titulo getTitulo() {
		return titulo;
	}

	public void setTitulo(Titulo titulo) {
		this.titulo = titulo;
	}

	public ConfeaTitulo getConfeaTitulo() {
		return confeaTitulo;
	}

	public void setConfeaTitulo(ConfeaTitulo confeaTitulo) {
		this.confeaTitulo = confeaTitulo;
	}


	
	

}
