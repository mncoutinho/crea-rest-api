package br.org.crea.commons.models.atendimento.dtos;

public class PainelAtendimentoDto {
	
	private Long id;
	
	private String nome;
	
	private String guiche;
	
	private Long idDepartamento;
	
	private String hash;

	public Long getId() {
		return id;
	}

	public void setId(Long idPessoa) {
		this.id = idPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGuiche() {
		return guiche;
	}

	public void setGuiche(String guiche) {
		this.guiche = guiche;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
