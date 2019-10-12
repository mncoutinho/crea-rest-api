package br.org.crea.commons.models.cadastro.dtos;

import br.org.crea.commons.models.commons.Endereco;

public class UnidadeAtendimentoDto {

private Long id;
	
	private String nome;
	
	private Long regional;
	
	private String descricaoRegional;
	
	private Endereco endereco;
	
	private String telefone;
	
	private String email;
	

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

	public Long getRegional() {
		return regional;
	}

	public void setRegional(Long regional) {
		this.regional = regional;
	}

	public String getDescricaoRegional() {
		return descricaoRegional;
	}

	public void setDescricaoRegional(String descricaoRegional) {
		this.descricaoRegional = descricaoRegional;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
