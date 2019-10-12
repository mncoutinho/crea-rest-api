package br.org.crea.commons.models.atendimento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OUVI_ASSUNTOS_ESPECIFICOS")

public class OuvidoriaAssuntosEspecificos  {

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="DATAALTERACAO")
	private Date dataAlteracao;

	@Column(name="FK_CODIGO_ASSUNTOS_GERAIS")
	private Long idAssuntosGerais;
	
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

	
	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Long getIdAssuntosGerais() {
		return idAssuntosGerais;
	}

	public void setIdAssuntosGerais(Long idAssuntosGerais) {
		this.idAssuntosGerais = idAssuntosGerais;
	}

	
}
