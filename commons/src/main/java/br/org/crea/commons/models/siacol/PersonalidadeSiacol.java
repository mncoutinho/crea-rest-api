package br.org.crea.commons.models.siacol;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="PER_PERSONALIDADES")
@PrimaryKeyJoinColumn(name = "CODIGO")
public class PersonalidadeSiacol extends Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="TRATAMENTO")
	private String tratamento;
	
	@Column(name="TRATAMENTO_TEXTO")
	private String tratamentoTexto;
	
	@Column(name="VOCATIVO")
	private String vocativo;
	
	@Column(name="NOME_CONJUGE")
	private String nomeConjuge;
	
	@Column(name="DATA_NASCIMENTO_CONJUGE")
	private String dataNascimentoConjuge;
	
	@Column(name="FILHOS")
	private Long filhos;
	
	@Column(name="NUMERO_AGENCIA")
	private String numeroAgencia;
	
	@Column(name="NUMERO_CONTA")
	private String numeroConta;
	
	@Column(name="KM_DOMICILIO_CREA")
	private String kmDomicilioCrea;
	
	@Column(name="DATA_CADASTRO")
	private Date dataCadastro;
	
	@Column(name="REMOVIDO")
	private Boolean removido;
	
	@Column(name="FK_CODIGO_LEIGOS")
	private Long idLeigo;
	
	@Column(name="FK_CODIGO_PROFISSIONAIS")
	private Long idProfissional;
	
	@Column(name="NOME_BANCO")
	private String nomeBanco;
	
	@Column(name="DATA_ALTERACAO")
	private Date dataAlteracao;
	
	@Column(name="MATRICULA_ALTERACAO")
	private Long matriculaAlteracao;
	
	@Column(name="FK_CODIGO_AJUDA_CUSTOS")
	private Long idAjudaCustos;
	
	@Column(name="APELIDO")
	private String apelido;
	
	@Column(name="SENHA")
	private String senhaPersonalidade;
	
	@Column(name="CRACHA")
	private Long cracha;
	
	@Column(name="NOME_GUERRA")
	private String nomeGuerra;

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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getRemovido() {
		return removido;
	}

	public void setRemovido(Boolean removido) {
		this.removido = removido;
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

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Long getMatriculaAlteracao() {
		return matriculaAlteracao;
	}

	public void setMatriculaAlteracao(Long matriculaAlteracao) {
		this.matriculaAlteracao = matriculaAlteracao;
	}

	public Long getIdAjudaCustos() {
		return idAjudaCustos;
	}

	public void setIdAjudaCustos(Long idAjudaCustos) {
		this.idAjudaCustos = idAjudaCustos;
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
	
	
}
