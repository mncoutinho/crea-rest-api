package br.org.crea.commons.models.atendimento.dtos;



public class PesquisaAtendimentoDto {
	
	private Long idDepartamento;
	
	private Long status;
	
	private String nomePessoa;
	
	private String senha;
	
	private String cpfOuCnpj;
	
	private int page = 0;
	
	private int rows;

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}


	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public boolean temSenha() {
		return this.senha != null ? true : false;
	}
	public boolean temNome() {
		return this.nomePessoa != null ? true : false;
	}
	public boolean temCpfOuCnpj() {
		return this.cpfOuCnpj != null ? true : false;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
