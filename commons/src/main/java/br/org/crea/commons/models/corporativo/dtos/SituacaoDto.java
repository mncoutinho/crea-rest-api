package br.org.crea.commons.models.corporativo.dtos;


public class SituacaoDto {
	
	private Long   id;
	
	private String descricao;
	
	private String descricaoPublica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricaoPublica() {
		return descricaoPublica;
	}

	public void setDescricaoPublica(String descricaoPublica) {
		this.descricaoPublica = descricaoPublica;
	}
	
	

}
