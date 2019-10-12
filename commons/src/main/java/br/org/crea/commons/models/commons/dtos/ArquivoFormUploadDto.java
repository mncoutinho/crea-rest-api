package br.org.crea.commons.models.commons.dtos;

import java.io.InputStream;
import java.io.Serializable;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import br.org.crea.commons.models.commons.enuns.ModuloSistema;

public class ArquivoFormUploadDto implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7798677655839554007L;

	@FormParam("file")
	@PartType("application/octet-stream")
	private InputStream file;

	@FormParam("name")
	@PartType("text")
	private String fileName;

	@FormParam("size")
	@PartType("text/plain")
	private Long tamanho;

	@FormParam("descricao")
	@PartType("text/plain")
	private String descricao;

	@FormParam("send")
	@PartType("text/plain")
	private String send;

	@FormParam("modulo")
	@PartType("text/plain")
	private ModuloSistema modulo;
    
	@FormParam("path")
	@PartType("text/plain")
	private String path;
	
	@FormParam("privado")
	@PartType("text/plain")
	private boolean privado;
	
	@FormParam("idClient")
	@PartType("text/plain")
	private Long idClient;

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

	public ModuloSistema getModulo() {
		return modulo;
	}

	public void setModulo(ModuloSistema modulo) {
		this.modulo = modulo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isPrivado() {
		return privado;
	}

	public void setPrivado(boolean privado) {
		this.privado = privado;
	}

	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}
	
	public boolean possuiPathDoArquivo() {
		return this.path != null;
	}
}
