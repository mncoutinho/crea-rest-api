package br.org.crea.commons.docflow.dto;

import java.io.InputStream;

import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;

public class DocflowGenericDto {
	
	private String matricula;
	
	private String interessado;
	
	private String assunto;
	
	private String observacao;
	
	private String idDocumento;
	
	private String numeroProcesso;
	
	private String tipoProcesso;
	
	private String nomeArquivoPdf;
	
	private InputStream inputStreamArquivoPdf;
	
	private String token;
	
	private String dataArquivo;
	
	private String numeroProtocolo;
	
	private String tipoDocumento;
	
	private String codigoDepartamento;
	
	private String unidadeDestino;
	
	private String unidadeOrigem;
	
	private String observacaoDoDocumento;
	
	private DocumentoDto documento;
	
	private String protocoloDoDocumento;
	
	private String codigoClassificacaoTramite;
	
	private String codigoSituacao;
	
	private FuncionarioDto funcionarioDto;
	
	private ProtocoloDto protocoloDto;
	
	private String uniqueIdConteudo;
	
	private String contentType;
	
	private String numeroProtocoloPrincipal;
	
	private String numeroProtocoloJuntada;
	
	private String numeroProtocoloSubstituto;
	
	private String numeroProtocoloSubstituido;
	
	private boolean ehDigital;

	public String getMatricula() {
		return matricula;
	}

	public String getInteressado() {
		return interessado;
	}

	public String getAssunto() {
		return assunto;
	}

	public String getObservacao() {
		return observacao;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public String getTipoProcesso() {
		return tipoProcesso;
	}

	public String getNomeArquivoPdf() {
		return nomeArquivoPdf;
	}

	public InputStream getInputStreamArquivoPdf() {
		return inputStreamArquivoPdf;
	}

	public String getToken() {
		return token;
	}

	public String getDataArquivo() {
		return dataArquivo;
	}

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public String getUnidadeDestino() {
		return unidadeDestino;
	}

	public String getUnidadeOrigem() {
		return unidadeOrigem;
	}

	public String getObservacaoDoDocumento() {
		return observacaoDoDocumento;
	}

	public DocumentoDto getDocumento() {
		return documento;
	}

	public String getProtocoloDoDocumento() {
		return protocoloDoDocumento;
	}

	public String getCodigoClassificacaoTramite() {
		return codigoClassificacaoTramite;
	}

	public String getCodigoSituacao() {
		return codigoSituacao;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setInteressado(String interessado) {
		this.interessado = interessado;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public void setTipoProcesso(String tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public void setNomeArquivoPdf(String nomeArquivoPdf) {
		this.nomeArquivoPdf = nomeArquivoPdf;
	}

	public void setInputStreamArquivoPdf(InputStream inputStreamArquivoPdf) {
		this.inputStreamArquivoPdf = inputStreamArquivoPdf;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setDataArquivo(String dataArquivo) {
		this.dataArquivo = dataArquivo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	public void setUnidadeDestino(String unidadeDestino) {
		this.unidadeDestino = unidadeDestino;
	}

	public void setUnidadeOrigem(String unidadeOrigem) {
		this.unidadeOrigem = unidadeOrigem;
	}

	public void setObservacaoDoDocumento(String observacaoDoDocumento) {
		this.observacaoDoDocumento = observacaoDoDocumento;
	}

	public void setDocumento(DocumentoDto documento) {
		this.documento = documento;
	}

	public void setProtocoloDoDocumento(String protocoloDoDocumento) {
		this.protocoloDoDocumento = protocoloDoDocumento;
	}

	public void setCodigoClassificacaoTramite(String codigoClassificacaoTramite) {
		this.codigoClassificacaoTramite = codigoClassificacaoTramite;
	}

	public void setCodigoSituacao(String codigoSituacao) {
		this.codigoSituacao = codigoSituacao;
	}

	public FuncionarioDto getFuncionarioDto() {
		return funcionarioDto;
	}

	public void setFuncionarioDto(FuncionarioDto funcionarioDto) {
		this.funcionarioDto = funcionarioDto;
	}

	public ProtocoloDto getProtocoloDto() {
		return protocoloDto;
	}

	public void setProtocoloDto(ProtocoloDto protocoloDto) {
		this.protocoloDto = protocoloDto;
	}

	public String getUniqueIdConteudo() {
		return uniqueIdConteudo;
	}

	public void setUniqueIdConteudo(String uniqueIdConteudo) {
		this.uniqueIdConteudo = uniqueIdConteudo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getNumeroProtocoloPrincipal() {
		return numeroProtocoloPrincipal;
	}

	public void setNumeroProtocoloPrincipal(String numeroProtocoloPrincipal) {
		this.numeroProtocoloPrincipal = numeroProtocoloPrincipal;
	}

	public String getNumeroProtocoloJuntada() {
		return numeroProtocoloJuntada;
	}

	public void setNumeroProtocoloJuntada(String numeroProtocoloJuntada) {
		this.numeroProtocoloJuntada = numeroProtocoloJuntada;
	}

	public String getNumeroProtocoloSubstituto() {
		return numeroProtocoloSubstituto;
	}

	public void setNumeroProtocoloSubstituto(String numeroProtocoloSubstituto) {
		this.numeroProtocoloSubstituto = numeroProtocoloSubstituto;
	}

	public String getNumeroProtocoloSubstituido() {
		return numeroProtocoloSubstituido;
	}

	public void setNumeroProtocoloSubstituido(String numeroProtocoloSubstituido) {
		this.numeroProtocoloSubstituido = numeroProtocoloSubstituido;
	}

	public boolean isEhDigital() {
		return ehDigital;
	}

	public void setEhDigital(boolean ehDigital) {
		this.ehDigital = ehDigital;
	}
	
}

