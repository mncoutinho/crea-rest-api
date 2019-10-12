package br.org.crea.commons.models.corporativo.dtos;


public class DocumentacaoDto {
	
	private Long id;

	private String nome;

	private String descricao;

	private Boolean status;

	private String link;
//
//	private Date dataCriacao;
//
//	private Date dataAlteracao;
//
//	private Funcionario funcionario;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
	
	

}
