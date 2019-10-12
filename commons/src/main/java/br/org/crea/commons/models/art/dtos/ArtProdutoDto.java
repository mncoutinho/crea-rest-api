package br.org.crea.commons.models.art.dtos;



public class ArtProdutoDto {
	
	private Long codigo;
	
	private String descricao;
	
	private String ingredienteAtivo;
	
	private String concentracao;
	
	private String classeUso;
	
	private String classeToxicologica;
	
	private String titularCadastro;
	
	private String cnpj;
	
	private String processoAdm;
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getIngredienteAtivo() {
		return ingredienteAtivo;
	}
	public void setIngredienteAtivo(String ingredienteAtivo) {
		this.ingredienteAtivo = ingredienteAtivo;
	}
	public String getConcentracao() {
		return concentracao;
	}
	public void setConcentracao(String concentracao) {
		this.concentracao = concentracao;
	}
	public String getClasseUso() {
		return classeUso;
	}
	public void setClasseUso(String classeUso) {
		this.classeUso = classeUso;
	}
	public String getClasseToxicologica() {
		return classeToxicologica;
	}
	public void setClasseToxicologica(String classeToxicologica) {
		this.classeToxicologica = classeToxicologica;
	}
	public String getTitularCadastro() {
		return titularCadastro;
	}
	public void setTitularCadastro(String titularCadastro) {
		this.titularCadastro = titularCadastro;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getProcessoAdm() {
		return processoAdm;
	}
	public void setProcessoAdm(String processoAdm) {
		this.processoAdm = processoAdm;
	}

}
