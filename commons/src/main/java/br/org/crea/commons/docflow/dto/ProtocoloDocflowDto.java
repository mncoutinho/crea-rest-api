package br.org.crea.commons.docflow.dto;
import java.util.ArrayList;
import java.util.List;

public class ProtocoloDocflowDto {
	
	private String numero;
	
	private String protocolo;
	
	private String dataCriacao;
	
	private String interessado;
	
	private List<String> cointeressados;
	
	private String assunto;
	
	private String observacao;
	
	private String externo;
	
	private String situacao;
	
	private String codigoSigilo;
	
	private String sigilo;
	
	private String tipoProcesso;
	
	private String paginas;
	
	private String volumes;
	
	private String orgaoPrimeiroDestino;
	
	private String codigoUnidadePrimeiroDestino;
	
	private String unidadePrimeiroDestino;
	
	private String cidadePrimeiroDestino;
	
	private String orgaoAutor;
	
	private String codigoUnidadeAutor;
	
	private String unidadeAutor;
	
	private String cidadeAutor;
	
	private String autor;
	
	private String orgaoAtual;
	
	private String codigoUnidadeAtual;
	
	private String unidadeAtual;
	
	private String cidadeAtual;
	
	private String detentor;
	
	private String dataEnvio;
	
	private String dataRecebido;
	
	private String messageError = null;
	
	private List<DocumentoDocflowDto> documentosDoProcesso = new ArrayList<DocumentoDocflowDto>();

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getInteressado() {
		return interessado;
	}

	public void setInteressado(String interessado) {
		this.interessado = interessado;
	}

	public List<String> getCointeressados() {
		return cointeressados;
	}

	public void setCointeressados(List<String> cointeressados) {
		this.cointeressados = cointeressados;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getExterno() {
		return externo;
	}

	public void setExterno(String externo) {
		this.externo = externo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getCodigoSigilo() {
		return codigoSigilo;
	}

	public void setCodigoSigilo(String codigoSigilo) {
		this.codigoSigilo = codigoSigilo;
	}

	public String getSigilo() {
		return sigilo;
	}

	public void setSigilo(String sigilo) {
		this.sigilo = sigilo;
	}

	public String getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(String tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public String getPaginas() {
		return paginas;
	}

	public void setPaginas(String paginas) {
		this.paginas = paginas;
	}

	public String getVolumes() {
		return volumes;
	}

	public void setVolumes(String volumes) {
		this.volumes = volumes;
	}

	public String getOrgaoPrimeiroDestino() {
		return orgaoPrimeiroDestino;
	}

	public void setOrgaoPrimeiroDestino(String orgaoPrimeiroDestino) {
		this.orgaoPrimeiroDestino = orgaoPrimeiroDestino;
	}

	public String getCodigoUnidadePrimeiroDestino() {
		return codigoUnidadePrimeiroDestino;
	}

	public void setCodigoUnidadePrimeiroDestino(String codigoUnidadePrimeiroDestino) {
		this.codigoUnidadePrimeiroDestino = codigoUnidadePrimeiroDestino;
	}

	public String getUnidadePrimeiroDestino() {
		return unidadePrimeiroDestino;
	}

	public void setUnidadePrimeiroDestino(String unidadePrimeiroDestino) {
		this.unidadePrimeiroDestino = unidadePrimeiroDestino;
	}

	public String getCidadePrimeiroDestino() {
		return cidadePrimeiroDestino;
	}

	public void setCidadePrimeiroDestino(String cidadePrimeiroDestino) {
		this.cidadePrimeiroDestino = cidadePrimeiroDestino;
	}

	public String getOrgaoAutor() {
		return orgaoAutor;
	}

	public void setOrgaoAutor(String orgaoAutor) {
		this.orgaoAutor = orgaoAutor;
	}

	public String getCodigoUnidadeAutor() {
		return codigoUnidadeAutor;
	}

	public void setCodigoUnidadeAutor(String codigoUnidadeAutor) {
		this.codigoUnidadeAutor = codigoUnidadeAutor;
	}

	public String getUnidadeAutor() {
		return unidadeAutor;
	}

	public void setUnidadeAutor(String unidadeAutor) {
		this.unidadeAutor = unidadeAutor;
	}

	public String getCidadeAutor() {
		return cidadeAutor;
	}

	public void setCidadeAutor(String cidadeAutor) {
		this.cidadeAutor = cidadeAutor;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getOrgaoAtual() {
		return orgaoAtual;
	}

	public void setOrgaoAtual(String orgaoAtual) {
		this.orgaoAtual = orgaoAtual;
	}

	public String getCodigoUnidadeAtual() {
		return codigoUnidadeAtual;
	}

	public void setCodigoUnidadeAtual(String codigoUnidadeAtual) {
		this.codigoUnidadeAtual = codigoUnidadeAtual;
	}

	public String getUnidadeAtual() {
		return unidadeAtual;
	}

	public void setUnidadeAtual(String unidadeAtual) {
		this.unidadeAtual = unidadeAtual;
	}

	public String getCidadeAtual() {
		return cidadeAtual;
	}

	public void setCidadeAtual(String cidadeAtual) {
		this.cidadeAtual = cidadeAtual;
	}

	public String getDetentor() {
		return detentor;
	}

	public void setDetentor(String detentor) {
		this.detentor = detentor;
	}

	public String getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(String dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getDataRecebido() {
		return dataRecebido;
	}

	public void setDataRecebido(String dataRecebido) {
		this.dataRecebido = dataRecebido;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public List<DocumentoDocflowDto> getDocumentosDoProcesso() {
		return documentosDoProcesso;
	}

	public void setDocumentosDoProcesso(List<DocumentoDocflowDto> documentosDoProcesso) {
		this.documentosDoProcesso = documentosDoProcesso;
	}


}
