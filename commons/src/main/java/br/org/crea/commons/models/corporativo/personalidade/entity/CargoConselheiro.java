package br.org.crea.commons.models.corporativo.personalidade.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.siacol.ConselheiroRegionalSiacol;
import br.org.crea.commons.models.siacol.PersonalidadeSiacol;

@Entity
@Table(name="PER_CARGO_CONSELHEIRO")
public class CargoConselheiro {
	
	
	private Long id;
	private CargoPersonalidade cargo;
	private ConselheiroRegionalSiacol conselheiro;
	private Date dataDesligamentoCargo;
	private Date dataFinalCargo;
	private Date dataInicialCargo;
	private Date dataPosseCargo;
	private String portariaDecisaoPlenaria;
	private Boolean removido;
	private Date ultimaAtualizacao;
	private Long matriculaAlteracao;
	private PersonalidadeSiacol personalidade;
	private Long CargoRaiz;
	private Departamento departamento;

	@Id
	@Column(name="CODIGO")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name="FK_CODIGO_CARGO")
	public CargoPersonalidade getCargo() {
		return cargo;
	}

	public void setCargo(CargoPersonalidade cargo) {
		this.cargo = cargo;
	}

	@OneToOne
	@JoinColumn(name="FK_CODIGO_CONSELHEIRO")
	public ConselheiroRegionalSiacol getConselheiro() {
		return conselheiro;
	}

	public void setConselheiro(ConselheiroRegionalSiacol conselheiro) {
		this.conselheiro = conselheiro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATADESLIGAMENTOCARGO")
	public Date getDataDesligamentoCargo() {
		return dataDesligamentoCargo;
	}

	public void setDataDesligamentoCargo(Date dataDesligamentoCargo) {
		this.dataDesligamentoCargo = dataDesligamentoCargo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATAFINALCARGO")
	public Date getDataFinalCargo() {
		return dataFinalCargo;
	}

	public void setDataFinalCargo(Date dataFinalCargo) {
		this.dataFinalCargo = dataFinalCargo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATAINICIALCARGO")
	public Date getDataInicialCargo() {
		return dataInicialCargo;
	}

	public void setDataInicialCargo(Date dataInicialCargo) {
		this.dataInicialCargo = dataInicialCargo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATAPOSSECARGO")
	public Date getDataPosseCargo() {
		return dataPosseCargo;
	}

	public void setDataPosseCargo(Date dataPosseCargo) {
		this.dataPosseCargo = dataPosseCargo;
	}

	@Column(name="PORTARIADECISAOPLENARIA")
	public String getPortariaDecisaoPlenaria() {
		return portariaDecisaoPlenaria;
	}

	public void setPortariaDecisaoPlenaria(String portariaDecisaoPlenaria) {
		this.portariaDecisaoPlenaria = portariaDecisaoPlenaria;
	}

	@Column(name="REMOVIDO")
	public Boolean getRemovido() {
		return removido;
	}

	public void setRemovido(Boolean removido) {
		this.removido = removido;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ULTIMA_ATUALIZACAO")
	public Date getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(Date ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}

	@Column(name="MATRICULA_ALTERACAO")
	public Long getMatriculaAlteracao() {
		return matriculaAlteracao;
	}

	public void setMatriculaAlteracao(Long matriculaAlteracao) {
		this.matriculaAlteracao = matriculaAlteracao;
	}

	@OneToOne
	@JoinColumn(name="FK_CODIGO_PERSONALIDADE")
	public PersonalidadeSiacol getPersonalidade() {
		return personalidade;
	}

	public void setPersonalidade(PersonalidadeSiacol personalidade) {
		this.personalidade = personalidade;
	}

	@OneToOne
	@JoinColumn(name="FK_DEPARTAMENTO")
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Column(name="FK_CARGO_RAIZ")
	public Long getCargoRaiz() {
		return CargoRaiz;
	}

	public void setCargoRaiz(Long cargoRaiz) {
		this.CargoRaiz = cargoRaiz;
	}
	
}
