package br.org.crea.commons.models.commons.dtos;

public class CursoDto {
	
	private Long id;
	
	private String descricao;
	

	public Long getId() {
		return id;
	}
	public void setId(Long codigo) {
		this.id = codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
