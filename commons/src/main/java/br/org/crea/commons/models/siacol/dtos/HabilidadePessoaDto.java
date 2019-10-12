package br.org.crea.commons.models.siacol.dtos;


public class HabilidadePessoaDto {
	
	private Long id;

	private Long idPessoa;

	private Long idAssunto;

	private Long idDepartamento;

	private Boolean siacol;

	private Boolean ativo;
	
	private Boolean liberadoParaDistribuicao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Long getIdAssunto() {
		return idAssunto;
	}

	public void setIdAssunto(Long idAssunto) {
		this.idAssunto = idAssunto;
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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean liberadoParaDistribuicao() {
		return liberadoParaDistribuicao;
	}

	public void setLiberadoParaDistribuicao(Boolean liberadoParaDistribuicao) {
		this.liberadoParaDistribuicao = liberadoParaDistribuicao;
	}

	public boolean heSiacol() {
		return this.siacol == true ? true : false;
	}

}
