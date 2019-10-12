package br.org.crea.commons.models.fiscalizacao.dtos;

public class AutoReincidenciaDto {
	
	private String numeroAuto;
	
	private String deliberacao;
	
	private String dataDecisao;
	
	private String tipoInfracao;
	
	private String statusAnalise;
	
	private String dataInclusao;
	
	private long codigoTipoInfracao;

	public String getNumeroAuto() {
		return numeroAuto;
	}

	public void setNumeroAuto(String numeroAuto) {
		this.numeroAuto = numeroAuto;
	}

	public String getDeliberacao() {
		return deliberacao;
	}

	public void setDeliberacao(String deliberacao) {
		this.deliberacao = deliberacao;
	}

	public String getDataDecisao() {
		return dataDecisao;
	}

	public void setDataDecisao(String dataDecisao) {
		this.dataDecisao = dataDecisao;
	}

	public String getTipoInfracao() {
		return tipoInfracao;
	}

	public void setTipoInfracao(String tipoInfracao) {
		this.tipoInfracao = tipoInfracao;
	}

	public String getStatusAnalise() {
		return statusAnalise;
	}

	public void setStatusAnalise(String statusAnalise) {
		this.statusAnalise = statusAnalise;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(String dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public long getCodigoTipoInfracao() {
		return codigoTipoInfracao;
	}

	public void setCodigoTipoInfracao(long codigoTipoInfracao) {
		this.codigoTipoInfracao = codigoTipoInfracao;
	}
}
