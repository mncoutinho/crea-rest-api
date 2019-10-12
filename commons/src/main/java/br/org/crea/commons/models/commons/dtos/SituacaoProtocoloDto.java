package br.org.crea.commons.models.commons.dtos;

public class SituacaoProtocoloDto {
	
	private Long id;
	
	private Long codigo;
	
	private Long desabilitado;
	
	private Boolean siacol;
		
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Boolean getSiacol() {
		return siacol;
	}

	public void setSiacol(Boolean siacol) {
		this.siacol = siacol;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Long getDesabilitado() {
		return desabilitado;
	}

	public void setDesabilitado(Long desabilitado) {
		this.desabilitado = desabilitado;
	}

	
	

}
