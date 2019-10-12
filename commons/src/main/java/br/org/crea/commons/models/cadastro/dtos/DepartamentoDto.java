package br.org.crea.commons.models.cadastro.dtos;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;

public class DepartamentoDto {

	private Long id;
	
	private Long codigo;
	
	private Long codigoUnidade;

	private String nome;

	private String nomeExibicao;

	private String sigla;

	private DepartamentoDto departamentoPai;

	private String emailCoordenacao;

	private Boolean removido;

	private Boolean atendimento;

	private Boolean siacol;

	private Boolean importacaoSiacol;

	private Boolean executaJulgamentoRevelia;

	private Boolean enviaParaJulgamentoRevelia;

	private boolean dividaAtiva;
	
	private DomainGenericDto coordenador;
	
	private DomainGenericDto adjunto;
	
	private ModalidadeDto modalidade;
	
	private String modulo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoUnidade() {
		return codigoUnidade;
	}

	public void setCodigoUnidade(Long codigoUnidade) {
		this.codigoUnidade = codigoUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeExibicao() {
		return nomeExibicao;
	}

	public void setNomeExibicao(String nomeExibicao) {
		this.nomeExibicao = nomeExibicao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public DepartamentoDto getDepartamentoPai() {
		return departamentoPai;
	}

	public void setDepartamentoPai(DepartamentoDto departamentoPai) {
		this.departamentoPai = departamentoPai;
	}

	public String getEmailCoordenacao() {
		return emailCoordenacao;
	}

	public void setEmailCoordenacao(String emailCoordenacao) {
		this.emailCoordenacao = emailCoordenacao;
	}

	public Boolean getRemovido() {
		return removido;
	}

	public void setRemovido(Boolean removido) {
		this.removido = removido;
	}

	public Boolean getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Boolean atendimento) {
		this.atendimento = atendimento;
	}

	public Boolean getSiacol() {
		return siacol;
	}

	public void setSiacol(Boolean siacol) {
		this.siacol = siacol;
	}

	public Boolean getImportacaoSiacol() {
		return importacaoSiacol;
	}

	public void setImportacaoSiacol(Boolean importacaoSiacol) {
		this.importacaoSiacol = importacaoSiacol;
	}

	public Boolean getExecutaJulgamentoRevelia() {
		return executaJulgamentoRevelia;
	}

	public void setExecutaJulgamentoRevelia(Boolean executaJulgamentoRevelia) {
		this.executaJulgamentoRevelia = executaJulgamentoRevelia;
	}

	public Boolean getEnviaParaJulgamentoRevelia() {
		return enviaParaJulgamentoRevelia;
	}

	public void setEnviaParaJulgamentoRevelia(Boolean enviaParaJulgamentoRevelia) {
		this.enviaParaJulgamentoRevelia = enviaParaJulgamentoRevelia;
	}

	public boolean isDividaAtiva() {
		return dividaAtiva;
	}

	public void setDividaAtiva(boolean dividaAtiva) {
		this.dividaAtiva = dividaAtiva;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public DomainGenericDto getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(DomainGenericDto coordenador) {
		this.coordenador = coordenador;
	}

	public DomainGenericDto getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(DomainGenericDto adjunto) {
		this.adjunto = adjunto;
	}
	
	public boolean temCoordenador() {
		return this.coordenador != null ? true : false;
	}
	
	public boolean temAdjunto() {
		return this.adjunto != null ? true : false;
	}
	
	public boolean temDepartamentoPai() {
		return this.departamentoPai != null ? true : false;
	}

	public ModalidadeDto getModalidade() {
		return modalidade;
	}

	public void setModalidade(ModalidadeDto modalidade) {
		this.modalidade = modalidade;
	}
	
}
