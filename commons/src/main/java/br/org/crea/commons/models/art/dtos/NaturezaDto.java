package br.org.crea.commons.models.art.dtos;

public class NaturezaDto {
	
	private Long id;
	
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean heDesempenhoCargoFuncao() {
		return this.id == 2 ? true : false;
	}

	public boolean heReceituarioAgronomico() {
		return this.id == 3 ? true : false;
	}

	public boolean heCompativelComAtividadeTecnicaParaDesempenhoCargoFuncao(Long idAtividade) {
		return ((idAtividade == 15) || (idAtividade == 16) || (idAtividade == 68) || (idAtividade == 69)) ? true : false;
	}

	public boolean heCompativelComAtividadeTecnicaParaReceituarioAgronomico(Long idAtividade) {
		return (idAtividade == 42) ? true : false;
	}

	public boolean heCompativelComEspecificacaoParaReceituarioAgronomico(Long idEspecificacao) {
		return (idEspecificacao == 28) ? true : false;
	}

	public boolean heCompativelComComplementoParaDesempenhoCargoFuncao(Long idComplemento) {
		return ((idComplemento == 189) || (idComplemento == 190)) ? true : false;
	}

	public boolean heCompativelComComplementosParaReceituarioAgronomico(Long idComplemento) {
		return (idComplemento == 136) ? true : false;
	}

	public boolean heCompativelComNaturezaParaReceituarioAgronomico(Long idNatureza) {
		return (idNatureza == 3) ? true : false;
	}



	

}
