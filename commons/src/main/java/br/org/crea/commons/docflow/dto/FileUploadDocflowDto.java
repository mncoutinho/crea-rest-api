package br.org.crea.commons.docflow.dto;

import java.io.InputStream;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUploadDocflowDto {
	
	@FormParam("file")
	@PartType("application/octet-stream")
	private InputStream file;

	@FormParam("name")
	@PartType("text/plain")
	private String fileName;
	
	@FormParam("interessado")
	@PartType("text/plain")
	private String interessado;
	
	@FormParam("unidade-destino")
	@PartType("text/plain")
	private String unidadeDestino;
	
	@FormParam("matricula")
	@PartType("text/plain")
	private String matricula;
	
	@FormParam("assunto")
	@PartType("text/plain")
	private String assunto;
	
	@FormParam("numero-processo")
	@PartType("text/plain")
	private String numeroProcesso;
	
	@FormParam("observacao")
	@PartType("text/plain")
	private String observacao;
	
	@FormParam("tipo-processo")
	@PartType("text/plain")
	private String tipoProcesso;
	
	@FormParam("tipo-documento")
	@PartType("text/plain")
	private String tipoDocumento;
	
	@FormParam("protocolo-documento")
	@PartType("text/plain")
	private String protocoloDoDocumento;

	@FormParam("size")
	@PartType("text/plain")
	private Long tamanho;

	@FormParam("descricao")
	@PartType("text/plain")
	private String descricao;

	@FormParam("send")
	@PartType("text/plain")
	private String send;

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public String getInteressado() {
		return interessado;
	}

	public void setInteressado(String interessado) {
		this.interessado = interessado;
	}

	public String getUnidadeDestino() {
		return unidadeDestino;
	}

	public void setUnidadeDestino(String unidadeDestino) {
		this.unidadeDestino = unidadeDestino;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getProtocoloDoDocumento() {
		return protocoloDoDocumento;
	}

	public void setProtocoloDoDocumento(String protocoloDoDocumento) {
		this.protocoloDoDocumento = protocoloDoDocumento;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(String tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
	
	


}
