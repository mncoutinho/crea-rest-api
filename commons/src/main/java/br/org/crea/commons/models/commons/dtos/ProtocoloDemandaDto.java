package br.org.crea.commons.models.commons.dtos;

import java.util.Date;

public class ProtocoloDemandaDto {
	
	private Long id;

	private Long protocoloFilho;
	
	private Long protocoloPai;
	
	private TipoDemandaDto assunto;	
	
	private Date dataRegistro;
	
	private Long descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProtocoloFilho() {
		return protocoloFilho;
	}

	public void setProtocoloFilho(Long protocoloFilho) {
		this.protocoloFilho = protocoloFilho;
	}

	public Long getProtocoloPai() {
		return protocoloPai;
	}

	public void setProtocoloPai(Long protocoloPai) {
		this.protocoloPai = protocoloPai;
	}

	public TipoDemandaDto getAssunto() {
		return assunto;
	}

	public void setAssunto(TipoDemandaDto assunto) {
		this.assunto = assunto;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Long getDescricao() {
		return descricao;
	}

	public void setDescricao(Long descricao) {
		this.descricao = descricao;
	}
	
	

}
