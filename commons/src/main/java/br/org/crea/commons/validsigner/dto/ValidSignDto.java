package br.org.crea.commons.validsigner.dto;

import java.util.List;

import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.validsigner.enums.TipoDocumentoAssinaturaEnum;

public class ValidSignDto {
	
	private Long idDocumento;
	private String thumbprint;
	private String signerCertificate;
	private String signaturePackage;
	private String signature;
	private String signatureAlgortimo;
	private byte[] documento;
	private boolean assinaturaValida;
	private TipoDocumentoAssinaturaEnum tipoDocumentoAssinatura;
	private ModuloSistema moduloSistema;
	private UserFrontDto user;
	private List<String> mensagemValidacao;
	private String chaveAssinaturaRedis;
	private String assuntoDocumento;
	private String observacaoDocumento;
	private String unidadeDestino;
	private String interessadoDocumento;
	private String codigoTipoDocumento;
	
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

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getSignaturePackage() {
		return signaturePackage;
	}

	public void setSignaturePackage(String signaturePackage) {
		this.signaturePackage = signaturePackage;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public String getSignatureAlgortimo() {
		return signatureAlgortimo;
	}

	public void setSignatureAlgortimo(String signatureAlgortimo) {
		this.signatureAlgortimo = signatureAlgortimo;
	}

	public boolean assinaturaValida() {
		return assinaturaValida ? assinaturaValida : false;
	}

	public void setAssinaturaValida(boolean assinaturaValida) {
		this.assinaturaValida = assinaturaValida;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public TipoDocumentoAssinaturaEnum getTipoDocumentoAssinatura() {
		return tipoDocumentoAssinatura;
	}

	public void setTipoDocumento(TipoDocumentoAssinaturaEnum tipoDocumentoAssinatura) {
		this.tipoDocumentoAssinatura = tipoDocumentoAssinatura;
	}

	public ModuloSistema getModuloSistema() {
		return moduloSistema;
	}

	public void setModuloSistema(ModuloSistema moduloSistema) {
		this.moduloSistema = moduloSistema;
	}

	public UserFrontDto getUser() {
		return user;
	}

	public void setUser(UserFrontDto user) {
		this.user = user;
	}
	
	public List<String> getMensagemValidacao() {
		return mensagemValidacao;
	}

	public void setMensagemValidacao(List<String> mensagemValidacao) {
		this.mensagemValidacao = mensagemValidacao;
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

	public boolean temSignerCertificate() {
		return this.signerCertificate != null;
	}
	
	public boolean temThumbprint() {
		return this.thumbprint != null;
	}
	
	public boolean temModulo() {
		return this.moduloSistema != null;
	}
	
	public boolean temTipoDocumentoAssinatura() {
		return this.tipoDocumentoAssinatura != null;
	}
	
	public boolean temIdDocumento() {
		return this.idDocumento != null;
	}
	
	public boolean temDocumento() {
		return this.documento != null;
	}
	
	public boolean temSignature() {
		return this.signature != null;
	}
	
	public boolean temSignaturePackage() {
		return this.signaturePackage != null;
	}
	
	public boolean temChaveAssinaturaRedis() {
		return this.chaveAssinaturaRedis != null;
	}
}
