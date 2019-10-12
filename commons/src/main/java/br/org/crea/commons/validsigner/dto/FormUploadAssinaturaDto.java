package br.org.crea.commons.validsigner.dto;

import java.io.InputStream;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.validsigner.enums.TipoDocumentoAssinaturaEnum;

public class FormUploadAssinaturaDto {

	@FormParam("file")
	@PartType("application/octet-stream")
	private InputStream file;

	@FormParam("name")
	@PartType("text")
	private String fileName;

	@FormParam("size")
	@PartType("text/plain")
	private Long tamanho;
	
	@FormParam("matricula")
	@PartType("text/plain")
	private Long matricula;

	@FormParam("descricao")
	@PartType("text/plain")
	private String descricao;

	@FormParam("send")
	@PartType("text/plain")
	private String send;

	@FormParam("path")
	@PartType("text/plain")
	private String path;
	
	@FormParam("idDocumento")
	@PartType("text/plain")
	private Long idDocumento;

	@FormParam("thumbprint")
	@PartType("text/plain")
	private String thumbprint;
	
	@FormParam("signerCertificate")
	@PartType("text/plain")
	private String signerCertificate;
	
	@FormParam("signaturePackage")
	@PartType("text/plain")
	private String signaturePackage;
	
	@FormParam("signature")
	@PartType("text/plain")
	private String signature;
	
	@FormParam("signatureAlgortimo")
	@PartType("text/plain")
	private String signatureAlgortimo;
	
	@FormParam("tipoDocumento")
	@PartType("text/plain")
	private TipoDocumentoAssinaturaEnum tipoDocumento;
	
	@FormParam("isValid")
	@PartType("text/plain")
	private boolean isValid;
	
	@FormParam("chaveAssinaturaRedis")
	@PartType("text/plain")
	private String chaveAssinaturaRedis;
	
	@FormParam("assuntoDocumento")
	@PartType("text/plain")
	private String assuntoDocumento;
	
	@FormParam("observacaoDocumento")
	@PartType("text/plain")
	private String observacaoDocumento;
	
	@FormParam("unidadeDestino")
	@PartType("text/plain")
	private String unidadeDestino;
	
	@FormParam("interessadoDocumento")
	@PartType("text/plain")
	private String interessadoDocumento;
	
	@FormParam("codigoTipoDocumento")
	@PartType("text/plain")
	private String codigoTipoDocumento;
	
	@FormParam("moduloSistema")
	@PartType("text/plain")
	private ModuloSistema moduloSistema;
	
	@FormParam("protocolo")
	@PartType("text/plain")
	private ProtocoloSiacolDto protocolo;
	
	@FormParam("numeroprotocolo")
	@PartType("text/plain")
	private String numeroprotocolo;

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

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getThumbprint() {
		return thumbprint;
	}

	public void setThumbprint(String thumbprint) {
		this.thumbprint = thumbprint;
	}

	public String getSignerCertificate() {
		return signerCertificate;
	}

	public void setSignerCertificate(String signerCertificate) {
		this.signerCertificate = signerCertificate;
	}

	public String getSignaturePackage() {
		return signaturePackage;
	}

	public void setSignaturePackage(String signaturePackage) {
		this.signaturePackage = signaturePackage;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignatureAlgortimo() {
		return signatureAlgortimo;
	}

	public void setSignatureAlgortimo(String signatureAlgortimo) {
		this.signatureAlgortimo = signatureAlgortimo;
	}

	public TipoDocumentoAssinaturaEnum getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoAssinaturaEnum tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getChaveAssinaturaRedis() {
		return chaveAssinaturaRedis;
	}

	public void setChaveAssinaturaRedis(String chaveAssinaturaRedis) {
		this.chaveAssinaturaRedis = chaveAssinaturaRedis;
	}

	public String getAssuntoDocumento() {
		return assuntoDocumento;
	}

	public void setAssuntoDocumento(String assuntoDocumento) {
		this.assuntoDocumento = assuntoDocumento;
	}

	public String getObservacaoDocumento() {
		return observacaoDocumento;
	}

	public void setObservacaoDocumento(String observacaoDocumento) {
		this.observacaoDocumento = observacaoDocumento;
	}

	public String getUnidadeDestino() {
		return unidadeDestino;
	}

	public void setUnidadeDestino(String unidadeDestino) {
		this.unidadeDestino = unidadeDestino;
	}

	public String getInteressadoDocumento() {
		return interessadoDocumento;
	}

	public void setInteressadoDocumento(String interessadoDocumento) {
		this.interessadoDocumento = interessadoDocumento;
	}

	public String getCodigoTipoDocumento() {
		return codigoTipoDocumento;
	}

	public void setCodigoTipoDocumento(String codigoTipoDocumento) {
		this.codigoTipoDocumento = codigoTipoDocumento;
	}

	public ModuloSistema getModuloSistema() {
		return moduloSistema;
	}

	public void setModuloSistema(ModuloSistema moduloSistema) {
		this.moduloSistema = moduloSistema;
	}

	public ProtocoloSiacolDto getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloSiacolDto protocolo) {
		this.protocolo = protocolo;
	}

	public String getNumeroProtocolo() {
		return numeroprotocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroprotocolo = numeroProtocolo;
	}

}

