package br.org.crea.commons.models.cadastro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.corporativo.pessoa.Empresa;

@Entity
@Table(name = "CAD_RAMOS_ATIVIDADE")
public class RamoAtividade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "FK_CODIGO_EMPRESAS")
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "FK_CODIGO_RAMOS")
	private Ramo ramo;

	@ManyToOne
	@JoinColumn(name = "FK_CODIGO_ATIVIDADES")
	private Atividade atividade;

	@Column(name = "DATA_INCLUSAO")
	@Temporal(TemporalType.DATE)
	private Date dataInclusao;

	@Column(name = "DATAAPROVACAO")
	@Temporal(TemporalType.DATE)
	private Date dataAprovacao;

	@Column(name = "DATACANCELAMENTO")
	@Temporal(TemporalType.DATE)
	private Date dataCancelamento;

	@Column(name = "RT")
	private Boolean possuiResponsavelTecnico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Ramo getRamo() {
		return ramo;
	}

	public void setRamo(Ramo ramo) {
		this.ramo = ramo;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public Boolean getPossuiResponsavelTecnico() {
		return possuiResponsavelTecnico;
	}

	public void setPossuiResponsavelTecnico(Boolean possuiResponsavelTecnico) {
		this.possuiResponsavelTecnico = possuiResponsavelTecnico;
	}

	public boolean temRamo() {
		return this.ramo != null;
	}

	public boolean temAtividade() {
		return this.atividade != null;
	}

}
