package br.org.crea.commons.models.art;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.cadastro.Modalidade;

@Entity
@Table(name="ART_RAMO")
public class RamoArt implements Serializable  {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6025298421203376690L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@OneToOne
	@JoinColumn(name="FK_MODALIDADE")
	private Modalidade modalidade;
	
	@Column(name="FISCALIZACAO")
	private boolean fiscalizacao;
	
	@Column(name="AUTOVISTORIA")
	private Long autoVistoria;


	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getAutoVistoria() {
		return autoVistoria;
	}

	public void setAutoVistoria(Long autoVistoria) {
		this.autoVistoria = autoVistoria;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public boolean isFiscalizacao() {
		return fiscalizacao;
	}

	public void setFiscalizacao(boolean fiscalizacao) {
		this.fiscalizacao = fiscalizacao;
	}

	

}
