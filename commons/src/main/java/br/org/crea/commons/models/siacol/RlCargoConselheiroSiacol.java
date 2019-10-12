package br.org.crea.commons.models.siacol;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.cadastro.Departamento;

@Entity
@Table(name="PER_CARGO_CONSELHEIRO")
public class RlCargoConselheiroSiacol implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_CARGO")
	private CargosReuniaoSiacol cargo;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_CONSELHEIRO")
	private ConselheiroRegionalSiacol conselheiro;
	
	@Column(name="DATADESLIGAMENTOCARGO")
	private Date dataDesligamentoCargo;
	
	@Column(name="DATAFINALCARGO")
	private Date dataFinalCargo;
	
	@Column(name="DATAINICIALCARGO")
	private Date dataInicialCargo;
	
	@Column(name="DATAPOSSECARGO")
	private Date dataPosseCargo;
	
	@Column(name="PORTARIADECISAOPLENARIA")
	private String portariaDecisaoPlenaria;
	
	@Column(name="REMOVIDO")
	private Boolean removido;
	
	@Column(name="ULTIMA_ATUALIZACAO")
	private Date ultimaAtualizacao;
	
	@Column(name="MATRICULA_ALTERACAO")
	private Long matriculaAlteracao;
	
	@Column(name="FK_CODIGO_PERSONALIDADE")
	private PersonalidadeSiacol personalidade;
	
	@Column(name="FK_CARGO_RAIZ")
	private Long idCargoRaiz;
	
	@OneToOne
	@JoinColumn(name="FK_DEPARTAMENTO")
	private Departamento departamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CargosReuniaoSiacol getCargo() {
		return cargo;
	}

	public void setCargo(CargosReuniaoSiacol cargo) {
		this.cargo = cargo;
	}

	public ConselheiroRegionalSiacol getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(ConselheiroRegionalSiacol conselheiro) {
		this.conselheiro = conselheiro;
	}

	public Date getDataDesligamentoCargo() {
		return dataDesligamentoCargo;
	}

	public void setDataDesligamentoCargo(Date dataDesligamentoCargo) {
		this.dataDesligamentoCargo = dataDesligamentoCargo;
	}

	public Date getDataFinalCargo() {
		return dataFinalCargo;
	}

	public void setDataFinalCargo(Date dataFinalCargo) {
		this.dataFinalCargo = dataFinalCargo;
	}

	public Date getDataInicialCargo() {
		return dataInicialCargo;
	}

	public void setDataInicialCargo(Date dataInicialCargo) {
		this.dataInicialCargo = dataInicialCargo;
	}

	public Date getDataPosseCargo() {
		return dataPosseCargo;
	}

	public void setDataPosseCargo(Date dataPosseCargo) {
		this.dataPosseCargo = dataPosseCargo;
	}

	public String getPortariaDecisaoPlenaria() {
		return portariaDecisaoPlenaria;
	}

	public void setPortariaDecisaoPlenaria(String portariaDecisaoPlenaria) {
		this.portariaDecisaoPlenaria = portariaDecisaoPlenaria;
	}

	public Boolean getRemovido() {
		return removido;
	}

	public void setRemovido(Boolean removido) {
		this.removido = removido;
	}

	public Date getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(Date ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}

	public Long getMatriculaAlteracao() {
		return matriculaAlteracao;
	}

	public void setMatriculaAlteracao(Long matriculaAlteracao) {
		this.matriculaAlteracao = matriculaAlteracao;
	}

	public PersonalidadeSiacol getIdPersonalidade() {
		return personalidade;
	}

	public void setIdPersonalidade(PersonalidadeSiacol personalidade) {
		this.personalidade = personalidade;
	}

	public Long getIdCargoRaiz() {
		return idCargoRaiz;
	}

	public void setIdCargoRaiz(Long idCargoRaiz) {
		this.idCargoRaiz = idCargoRaiz;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
}
