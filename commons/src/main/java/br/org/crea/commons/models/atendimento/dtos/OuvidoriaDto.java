package br.org.crea.commons.models.atendimento.dtos;

import java.util.Date;

import br.org.crea.commons.models.commons.dtos.ArquivoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;

public class OuvidoriaDto {
	
	private Long id;
	
	private DomainGenericDto pessoa;
	
	private DomainGenericDto tipoDemanda;
	
	private DomainGenericDto situacao;
	
	private DomainGenericDto origem;
	
	private OuvidoriaAssuntoEspecificoDto assuntoEspecifico;
	
	private OuvidoriaAssuntoGeraldto assuntoGeral;
	
	private ProtocoloDto protocolo;
	
	private ArquivoDto arquivo;
	
	private String providencia;
	
	private String solucao;	
	
	private Date dataAtendimento;
	
	private String dataAtendimentoFormatada;
	
	private String descricao;
	
	private String numeroOcorrencia;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DomainGenericDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(DomainGenericDto pessoa) {
		this.pessoa = pessoa;
	}

	public DomainGenericDto getSituacao() {
		return situacao;
	}

	public void setSituacao(DomainGenericDto situacao) {
		this.situacao = situacao;
	}

	public DomainGenericDto getOrigem() {
		return origem;
	}

	public void setOrigem(DomainGenericDto origem) {
		this.origem = origem;
	}
	
	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public String getDataAtendimentoFormatada() {
		return dataAtendimentoFormatada;
	}

	public void setDataAtendimentoFormatada(String dataAtendimentoFormatada) {
		this.dataAtendimentoFormatada = dataAtendimentoFormatada;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNumeroOcorrencia() {
		return numeroOcorrencia;
	}

	public void setNumeroOcorrencia(String numeroOcorrencia) {
		this.numeroOcorrencia = numeroOcorrencia;
	}

	public DomainGenericDto getTipoDemanda() {
		return tipoDemanda;
	}

	public void setTipoDemanda(DomainGenericDto tipoDemanda) {
		this.tipoDemanda = tipoDemanda;
	}

	public String getProvidencia() {
		return providencia;
	}

	public void setProvidencia(String providencia) {
		this.providencia = providencia;
	}

	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	public OuvidoriaAssuntoEspecificoDto getAssuntoEspecifico() {
		return assuntoEspecifico;
	}

	public void setAssuntoEspecifico(OuvidoriaAssuntoEspecificoDto assuntoEspecifico) {
		this.assuntoEspecifico = assuntoEspecifico;
	}

	public OuvidoriaAssuntoGeraldto getAssuntoGeral() {
		return assuntoGeral;
	}

	public void setAssuntoGeral(OuvidoriaAssuntoGeraldto assuntoGeral) {
		this.assuntoGeral = assuntoGeral;
	}

	public ProtocoloDto getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloDto protocolo) {
		this.protocolo = protocolo;
	}

	public ArquivoDto getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoDto arquivo) {
		this.arquivo = arquivo;
	}

	public boolean temArquivo() {
		return this.arquivo != null;
	}

		
}
