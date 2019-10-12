package br.org.crea.commons.models.corporativo.dtos;

import java.util.List;

public class AssuntoDto {
	
	private Long id;
	
	private Long codigo;
	
	private String nome;
	
	private String descricao;
	
	private Long mobile;
	
	private Boolean viaPortal;
	
	private Boolean ativo;
	
	private Boolean siacol;
	
	private AssuntoDto assuntoConfea;
	
	private List<AssuntoDto> listaAssuntoProtocolo;
	
	private List<AssuntoDto> listaAssuntoSiacol;
	
	private String tipoAssunto;
	
	private String legislacao;

	private Long tempoServico;
	
	private Double pesoInstrucao;
	
	private Double pesoComissao;
	
	private Double pesoCamara;
	
	private Double pesoPlenaria;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public Boolean getViaPortal() {
		return viaPortal;
	}

	public void setViaPortal(Boolean viaPortal) {
		this.viaPortal = viaPortal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getSiacol() {
		return siacol;
	}

	public void setSiacol(Boolean siacol) {
		this.siacol = siacol;
	}

	public AssuntoDto getAssuntoConfea() {
		return assuntoConfea;
	}

	public void setAssuntoConfea(AssuntoDto assuntoConfea) {
		this.assuntoConfea = assuntoConfea;
	}

	public List<AssuntoDto> getListaAssuntoProtocolo() {
		return listaAssuntoProtocolo;
	}

	public void setListaAssuntoProtocolo(List<AssuntoDto> listaAssuntoProtocolo) {
		this.listaAssuntoProtocolo = listaAssuntoProtocolo;
	}

	public List<AssuntoDto> getListaAssuntoSiacol() {
		return listaAssuntoSiacol;
	}

	public void setListaAssuntoSiacol(List<AssuntoDto> listaAssuntoSiacol) {
		this.listaAssuntoSiacol = listaAssuntoSiacol;
	}
	
	public String getTipoAssunto() {
		return tipoAssunto;
	}

	public void setTipoAssunto(String tipoAssunto) {
		this.tipoAssunto = tipoAssunto;
	}

	public boolean ehSiacol() {
		return this.siacol == true ? true : false;
	}

	public String getLegislacao() {
		return legislacao;
	}

	public void setLegislacao(String legislacao) {
		this.legislacao = legislacao;
	}

	public Long getTempoServico() {
		return tempoServico;
	}

	public void setTempoServico(Long tempoServico) {
		this.tempoServico = tempoServico;
	}

	public Double getPesoInstrucao() {
		return pesoInstrucao;
	}

	public void setPesoInstrucao(Double pesoInstrucao) {
		this.pesoInstrucao = pesoInstrucao;
	}

	public Double getPesoComissao() {
		return pesoComissao;
	}

	public void setPesoComissao(Double pesoComissao) {
		this.pesoComissao = pesoComissao;
	}

	public Double getPesoCamara() {
		return pesoCamara;
	}

	public void setPesoCamara(Double pesoCamara) {
		this.pesoCamara = pesoCamara;
	}

	public Double getPesoPlenaria() {
		return pesoPlenaria;
	}

	public void setPesoPlenaria(Double pesoPlenaria) {
		this.pesoPlenaria = pesoPlenaria;
	}

	

}
