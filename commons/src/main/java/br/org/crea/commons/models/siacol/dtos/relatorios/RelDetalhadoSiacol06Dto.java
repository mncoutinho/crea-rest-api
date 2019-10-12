package br.org.crea.commons.models.siacol.dtos.relatorios;

public class RelDetalhadoSiacol06Dto {

	private String idAuditoria;

	private String numeroProtocolo;
	
	private String numeroProtocoloReferencia;
	
	private String data;
	
	private String idDepartamento;
	
	private String nomeDepartamento;
	
	private String codigoAssunto;
	
	private String nomeAssunto;
	
	private RelSiacol06PesoDto pesoDto;

	public String getIdAuditoria() {
		return idAuditoria;
	}

	public void setIdAuditoria(String idAuditoria) {
		this.idAuditoria = idAuditoria;
	}

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNumeroProtocoloReferencia() {
		return numeroProtocoloReferencia;
	}

	public void setNumeroProtocoloReferencia(String numeroProtocoloReferencia) {
		this.numeroProtocoloReferencia = numeroProtocoloReferencia;
	}

	public boolean temNumeroProtocoloReferencia() {
		return this.numeroProtocoloReferencia != null;
	}

	public String getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(String idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getCodigoAssunto() {
		return codigoAssunto;
	}

	public void setCodigoAssunto(String codigoAssunto) {
		this.codigoAssunto = codigoAssunto;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getNomeAssunto() {
		return nomeAssunto;
	}

	public void setNomeAssunto(String nomeAssunto) {
		this.nomeAssunto = nomeAssunto;
	}

	public RelSiacol06PesoDto getPesoDto() {
		return pesoDto;
	}

	public void setPesoDto(RelSiacol06PesoDto pesoDto) {
		this.pesoDto = pesoDto;
	}
	
	public Long getNumeroProtocoloLong() {
		return this.numeroProtocolo != null ? Long.parseLong(this.numeroProtocolo) : null;
	}
}
