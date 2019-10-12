package br.org.crea.commons.models.corporativo.personalidade.dto;

import java.util.Date;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.corporativo.personalidade.entity.CargoPersonalidade;
import br.org.crea.commons.models.siacol.ConselheiroRegionalSiacol;
import br.org.crea.commons.models.siacol.PersonalidadeSiacol;

public class CargoConselheiroDto {
	
	private Long id;
	private CargoPersonalidade cargo;
	private ConselheiroRegionalSiacol conselheiro;
	private String dataDesligamentoCargo;
	private String dataFinalCargo;
	private String dataInicialCargo;
	private String dataPosseCargo;
	private String portariaDecisaoPlenaria;
	private Boolean removido;
	private Date ultimaAtualizacao;
	private Long matriculaAlteracao;
	private PersonalidadeSiacol personalidade;
	private Long CargoRaiz;
	private Departamento departamento;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CargoPersonalidade getCargo() {
		return cargo;
	}
	public void setCargo(CargoPersonalidade cargo) {
		this.cargo = cargo;
	}
	public ConselheiroRegionalSiacol getConselheiro() {
		return conselheiro;
	}
	public void setConselheiro(ConselheiroRegionalSiacol conselheiro) {
		this.conselheiro = conselheiro;
	}
	public String getDataDesligamentoCargo() {
		return dataDesligamentoCargo;
	}
	public void setDataDesligamentoCargo(String dataDesligamentoCargo) {
		this.dataDesligamentoCargo = dataDesligamentoCargo;
	}
	public String getDataFinalCargo() {
		return dataFinalCargo;
	}
	public void setDataFinalCargo(String dataFinalCargo) {
		this.dataFinalCargo = dataFinalCargo;
	}
	public String getDataInicialCargo() {
		return dataInicialCargo;
	}
	public void setDataInicialCargo(String dataInicialCargo) {
		this.dataInicialCargo = dataInicialCargo;
	}
	public String getDataPosseCargo() {
		return dataPosseCargo;
	}
	public void setDataPosseCargo(String dataPosseCargo) {
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
	public PersonalidadeSiacol getPersonalidade() {
		return personalidade;
	}
	public void setPersonalidade(PersonalidadeSiacol personalidade) {
		this.personalidade = personalidade;
	}	
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public Long getCargoRaiz() {
		return CargoRaiz;
	}
	public void setCargoRaiz(Long cargoRaiz) {
		this.CargoRaiz = cargoRaiz;
	}
	
	
}
