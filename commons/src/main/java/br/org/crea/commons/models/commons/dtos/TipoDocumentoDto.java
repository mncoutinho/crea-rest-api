package br.org.crea.commons.models.commons.dtos;


public class TipoDocumentoDto {
	
	private Long id;
	
	private Long modulo;
	
	private String descricao;
	
	private String template;

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

	public Long getModulo() {
		return modulo;
	}

	public void setModulo(Long modulo) {
		this.modulo = modulo;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public boolean temID() {
		return this.id != null ? true : false;
	}

	

}
