package br.org.crea.commons.models.siacol.dtos;

public class AuthPainelSiacolDto {

	private Long senha;

	private Long idReuniao;
	
	private Long idDepartamento;

	public Long getSenha() {
		return senha;
	}

	public Long getIdReuniao() {
		return idReuniao;
	}

	public void setSenha(Long senha) {
		this.senha = senha;
	}

	public void setIdReuniao(Long idReuniao) {
		this.idReuniao = idReuniao;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	

}
