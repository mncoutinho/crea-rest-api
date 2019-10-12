package br.org.crea.commons.models.commons.dtos;

import br.org.crea.commons.models.commons.enuns.ModuloSistema;

public class ArquivoDto {
	
	private Long id;

	private String caminhoOriginal;
	
	private String caminhoStorage;
	
	private String nomeOriginal;
	
	private String nomeStorage;
	
	private String uri;
	
	private String descricao;
	
	private String extensao;
	
	private Long tamanho;

	private ModuloSistema modulo;
	
	private boolean privado;
	
	private String dataFormatada;
	
	private DomainGenericDto pessoa;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaminhoOriginal() {
		return caminhoOriginal;
	}

	public void setCaminhoOriginal(String caminhoOriginal) {
		this.caminhoOriginal = caminhoOriginal;
	}
	
	public String getCaminhoStorage() {
		return caminhoStorage;
	}

	public void setCaminhoStorage(String caminhoStorage) {
		this.caminhoStorage = caminhoStorage;
	}

	public String getNomeOriginal() {
		return nomeOriginal;
	}

	public void setNomeOriginal(String nomeOriginal) {
		this.nomeOriginal = nomeOriginal;
	}


	public String getNomeStorage() {
		return nomeStorage;
	}

	public void setNomeStorage(String nomeStorage) {
		this.nomeStorage = nomeStorage;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	public ModuloSistema getModulo() {
		return modulo;
	}

	public void setModulo(ModuloSistema modulo) {
		this.modulo = modulo;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}


	public DomainGenericDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(DomainGenericDto pessoa) {
		this.pessoa = pessoa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isPrivado() {
		return privado;
	}

	public void setPrivado(boolean privado) {
		this.privado = privado;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean temPessoa() {
		return this.pessoa != null;
	}

	public boolean temModulo() {
		return this.modulo != null;
	}


	

}
