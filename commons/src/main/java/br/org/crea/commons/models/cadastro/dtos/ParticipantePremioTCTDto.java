package br.org.crea.commons.models.cadastro.dtos;

import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

public class ParticipantePremioTCTDto {

	private Long id;

	private Long idPremio;

	private String papel;

	private PessoaDto pessoa;

	private String email;

	private String telefone;

	private String celular;

	private String cpf;

	private String __index;

	private String idEndereco;
	
	private Long idTitulo;
	
	private String titulo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPremio() {
		return idPremio;
	}

	public void setIdPremio(Long idPremio) {
		this.idPremio = idPremio;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
		this.pessoa = pessoa;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String get__index() {
		return __index;
	}

	public void set__index(String __index) {
		this.__index = __index;
	}

	public boolean ehAutor() {
		return this.papel.equals("1");
	}
	
	public boolean ehAvaliador() {
		return this.papel.equals("2");
	}
	
	public boolean ehCoorientador() {
		return this.papel.equals("3");
	}
	
	public boolean ehOrientador() {
		return this.papel.equals("4");
	}
	
	public boolean ehComissao() {
		return this.papel.equals("5");
	}

	public String getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(String idEndereco) {
		this.idEndereco = idEndereco;
	}

	public Long getIdTitulo() {
		return idTitulo;
	}

	public void setIdTitulo(Long idTitulo) {
		this.idTitulo = idTitulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



}
