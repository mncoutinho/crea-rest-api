package br.org.crea.commons.models.siacol.dtos;

import java.util.List;

public class EnqueteDto {
	
	private String pergunta;
	
	private List<RespostaEnqueteDto> respostas;
	
	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}
	
	public List<RespostaEnqueteDto> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<RespostaEnqueteDto> respostas) {
		this.respostas = respostas;
	}
	
	public boolean somenteUmaRespostaEhAMaisVotada() {
		return this.respostas.stream().filter(el -> el.getMaisVotado().equals(true)).count() == 1;
	}

}