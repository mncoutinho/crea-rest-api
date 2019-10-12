package br.org.crea.commons.models.corporativo.personalidade.dto;

import br.org.crea.commons.models.corporativo.personalidade.entity.AjudaCusto;
import br.org.crea.commons.models.corporativo.pessoa.LeigoPF;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;


public class PersonalidadeDto {
	
	private String nome;
	private Long id;
	private String tratamento;
	private String tratamentoTexto;
	private String vocativo;
	private String nomeConjuge;
	private String dataNascimentoConjuge;
	private Long filhos;
	private String numeroAgencia;
	private String numeroConta;
	private String kmDomicilioCrea;
	private String dataCadastro;
	private Boolean removido;
	private String nomeBanco;
	private String dataAlteracao;
	private Long matriculaAlteracao;
	private String apelido;
	private String senhaPersonalidade;
	private Long cracha;
	private String nomeGuerra;
	private AjudaCusto ajudaCusto;
	private LeigoPF leigo;
	private Profissional profissional;
	private Long idLeigo;
	private Long idProfissional;
	private Long idAjudaCusto;
	private Boolean finalizado;
	private String codigoBarra;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTratamento() {
		return tratamento;
	}
	public void setTratamento(String tratamento) {
		this.tratamento = tratamento;
	}
	public String getTratamentoTexto() {
		return tratamentoTexto;
	}
	public void setTratamentoTexto(String tratamentoTexto) {
		this.tratamentoTexto = tratamentoTexto;
	}
	public String getVocativo() {
		return vocativo;
	}
	public void setVocativo(String vocativo) {
		this.vocativo = vocativo;
	}
	public String getNomeConjuge() {
		return nomeConjuge;
	}
	public void setNomeConjuge(String nomeConjuge) {
		this.nomeConjuge = nomeConjuge;
	}
	public String getDataNascimentoConjuge() {
		return dataNascimentoConjuge;
	}
	public void setDataNascimentoConjuge(String dataNascimentoConjuge) {
		this.dataNascimentoConjuge = dataNascimentoConjuge;
	}
	public Long getFilhos() {
		return filhos;
	}
	public void setFilhos(Long filhos) {
		this.filhos = filhos;
	}
	public String getNumeroAgencia() {
		return numeroAgencia;
	}
	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}
	public String getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}
	public String getKmDomicilioCrea() {
		return kmDomicilioCrea;
	}
	public void setKmDomicilioCrea(String kmDomicilioCrea) {
		this.kmDomicilioCrea = kmDomicilioCrea;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Boolean getRemovido() {
		return removido;
	}
	public void setRemovido(Boolean removido) {
		this.removido = removido;
	}
	public String getNomeBanco() {
		return nomeBanco;
	}
	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}
	public String getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(String dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	public Long getMatriculaAlteracao() {
		return matriculaAlteracao;
	}
	public void setMatriculaAlteracao(Long matriculaAlteracao) {
		this.matriculaAlteracao = matriculaAlteracao;
	}
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public String getSenhaPersonalidade() {
		return senhaPersonalidade;
	}
	public void setSenhaPersonalidade(String senhaPersonalidade) {
		this.senhaPersonalidade = senhaPersonalidade;
	}
	public Long getCracha() {
		return cracha;
	}
	public void setCracha(Long cracha) {
		this.cracha = cracha;
	}
	public String getNomeGuerra() {
		return nomeGuerra;
	}
	public void setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
	}
	public AjudaCusto getAjudaCusto() {
		return ajudaCusto;
	}
	public void setAjudaCusto(AjudaCusto ajudaCusto) {
		this.ajudaCusto = ajudaCusto;
	}
	public LeigoPF getLeigo() {
		return leigo;
	}
	public void setLeigo(LeigoPF leigo) {
		this.leigo = leigo;
	}
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	public Long getIdLeigo() {
		return idLeigo;
	}
	public void setIdLeigo(Long idLeigo) {
		this.idLeigo = idLeigo;
	}
	public Long getIdProfissional() {
		return idProfissional;
	}
	public void setIdProfissional(Long idProfissional) {
		this.idProfissional = idProfissional;
	}
	public Long getIdAjudaCusto() {
		return idAjudaCusto;
	}
	public void setIdAjudaCusto(Long idAjudaCusto) {
		this.idAjudaCusto = idAjudaCusto;
	}
	public Boolean getFinalizado() {
		return finalizado;
	}
	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}
	public String getCodigoBarra() {
		return codigoBarra;
	}
	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}
	
}
