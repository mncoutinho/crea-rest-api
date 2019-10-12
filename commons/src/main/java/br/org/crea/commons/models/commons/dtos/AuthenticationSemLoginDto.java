package br.org.crea.commons.models.commons.dtos;

public class AuthenticationSemLoginDto {
	
	private String tipoPessoa;
	
	private String origem;
	
	private String cpfOuCnpj;
	
	private String nome;

	private String email;
	
	private TelefoneDto telefone;
	
	private String senha;

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean hePessoaFisica(){
		return this.getTipoPessoa().equals("PESSOAFISICA");
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public TelefoneDto getTelefone() {
		return telefone;
	}

	public void setTelefone(TelefoneDto telefone) {
		this.telefone = telefone;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	public boolean heAcessoWifi () {
		if ( this.origem != null ){
			return this.origem.equals("WIFI");
		}else {
			return false;
		}
	}
	
	
}