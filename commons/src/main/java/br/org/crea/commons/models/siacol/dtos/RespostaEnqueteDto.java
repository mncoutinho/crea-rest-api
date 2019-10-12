package br.org.crea.commons.models.siacol.dtos;

public class RespostaEnqueteDto {

	private String id;
	
	private String resposta;
	
	private String peso;
	
	private int totalVotos;
	
	private Boolean maisVotado;
	
	private Boolean checked;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public int getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(int totalVotos) {
		this.totalVotos = totalVotos;
	}

	public Boolean getMaisVotado() {
		return maisVotado;
	}

	public void setMaisVotado(Boolean maisVotado) {
		this.maisVotado = maisVotado;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
