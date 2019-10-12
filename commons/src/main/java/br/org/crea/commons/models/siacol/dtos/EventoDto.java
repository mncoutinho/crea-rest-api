package br.org.crea.commons.models.siacol.dtos;

import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;

public class EventoDto {

	private Long id;
	
	private AssuntoDto assunto;
	
	private String descricao;
	
	private Boolean profissional;
	
	private Boolean empresa;
	
	private Boolean leigo;

	private Boolean permanente;

	private Boolean fim;

	private Boolean etico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AssuntoDto getAssunto() {
		return assunto;
	}

	public void setAssunto(AssuntoDto assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getProfissional() {
		return profissional;
	}

	public void setProfissional(Boolean profissional) {
		this.profissional = profissional;
	}

	public Boolean getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Boolean empresa) {
		this.empresa = empresa;
	}

	public Boolean getLeigo() {
		return leigo;
	}

	public void setLeigo(Boolean leigo) {
		this.leigo = leigo;
	}

	public Boolean getPermanente() {
		return permanente;
	}

	public void setPermanente(Boolean permanente) {
		this.permanente = permanente;
	}

	public Boolean getFim() {
		return fim;
	}

	public void setFim(Boolean fim) {
		this.fim = fim;
	}

	public Boolean getEtico() {
		return etico;
	}

	public void setEtico(Boolean etico) {
		this.etico = etico;
	}

	
	
	
	
	
}
