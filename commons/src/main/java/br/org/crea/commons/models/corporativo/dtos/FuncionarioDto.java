package br.org.crea.commons.models.corporativo.dtos;

public class FuncionarioDto {
	
	private Long id;
	
	private Long matricula;
	
	private String nome;
	
	private Long idDepartamento;
	
	private Boolean siacol;
	
	private String perfil;
	
	private Long idPessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public Boolean getSiacol() {
		return siacol;
	}

	public void setSiacol(Boolean siacol) {
		this.siacol = siacol;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

}
