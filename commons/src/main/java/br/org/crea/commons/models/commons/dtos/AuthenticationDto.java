package br.org.crea.commons.models.commons.dtos;


public class AuthenticationDto {
	
	private String tipo;
	
	private String login;
	
	private String senha;
	
	private String novaSenha;
	
	private String lembreteSenha;
	
	private boolean enviarResponsavel;
	
	private String recaptchaToken;
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean temMatricula () {
		return this.tipo.equals("MATRICULA") ? true : false;
	}

	public boolean isEnviarResponsavel() {
		return enviarResponsavel;
	}

	public void setEnviarResponsavel(boolean enviarResponsavel) {
		this.enviarResponsavel = enviarResponsavel;
	}

	public boolean ehCpf() {
		return this.tipo.equals("CPF");
	}

	public String getRecaptchaToken() {
		return recaptchaToken;
	}

	public void setRecaptchaToken(String recaptchaToken) {
		this.recaptchaToken = recaptchaToken;
	}

	public boolean ehPessoaFisica() {
		return this.tipo.equals("PESSOA_FISICA");
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getLembreteSenha() {
		return lembreteSenha;
	}

	public void setLembreteSenha(String lembreteSenha) {
		this.lembreteSenha = lembreteSenha;
	}
	

}
