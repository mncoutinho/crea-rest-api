package br.org.crea.commons.models.corporativo.personalidade.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.org.crea.commons.models.corporativo.pessoa.LeigoPF;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;



@Entity
@Table(name="PER_PERSONALIDADES")
public class Personalidade {
	
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
	private Date dataCadastro;
	private Boolean removido;
	private LeigoPF leigo;
	private Profissional profissional;
	private String nomeBanco;
	private Date dataAlteracao;
	private Long matriculaAlteracao;
    private AjudaCusto ajudaCusto;
	private String apelido;
	private String senhaPersonalidade;
	private Long cracha;
	private String nomeGuerra;
	private String codigoBarra;
	@SuppressWarnings("unused")
	private String nome;
	
	
	@Id
	@Column(name="CODIGO")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="TRATAMENTO")
	public String getTratamento() {
		return tratamento;
	}

	public void setTratamento(String tratamento) {
		this.tratamento = tratamento;
	}
	@Column(name="TRATAMENTO_TEXTO")
	public String getTratamentoTexto() {
		return tratamentoTexto;
	}

	public void setTratamentoTexto(String tratamentoTexto) {
		this.tratamentoTexto = tratamentoTexto;
	}

	@Column(name="VOCATIVO")
	public String getVocativo() {
		return vocativo;
	}

	public void setVocativo(String vocativo) {
		this.vocativo = vocativo;
	}

	@Column(name="NOME_CONJUGE")
	public String getNomeConjuge() {
		return nomeConjuge;
	}

	public void setNomeConjuge(String nomeConjuge) {
		this.nomeConjuge = nomeConjuge;
	}

	@Column(name="DATA_NASCIMENTO_CONJUGE")
	public String getDataNascimentoConjuge() {
		return dataNascimentoConjuge;
	}

	public void setDataNascimentoConjuge(String dataNascimentoConjuge) {
		this.dataNascimentoConjuge = dataNascimentoConjuge;
	}

	@Column(name="FILHOS")
	public Long getFilhos() {
		return filhos;
	}

	public void setFilhos(Long filhos) {
		this.filhos = filhos;
	}

	@Column(name="NUMERO_AGENCIA")
	public String getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	@Column(name="NUMERO_CONTA")
	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	@Column(name="KM_DOMICILIO_CREA")
	public String getKmDomicilioCrea() {
		return kmDomicilioCrea;
	}

	public void setKmDomicilioCrea(String kmDomicilioCrea) {
		this.kmDomicilioCrea = kmDomicilioCrea;
	}

	@Column(name="DATA_CADASTRO")
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Column(name="REMOVIDO")
	public Boolean getRemovido() {
		return removido;
	}

	public void setRemovido(Boolean removido) {
		this.removido = removido;
	}

	@Column(name="NOME_BANCO")
	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	@Column(name="DATA_ALTERACAO")
	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	@Column(name="MATRICULA_ALTERACAO")
	public Long getMatriculaAlteracao() {
		return matriculaAlteracao;
	}

	public void setMatriculaAlteracao(Long matriculaAlteracao) {
		this.matriculaAlteracao = matriculaAlteracao;
	}

	@Column(name="APELIDO")
	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	@Column(name="SENHA")
	public String getSenhaPersonalidade() {
		return senhaPersonalidade;
	}

	public void setSenhaPersonalidade(String senhaPersonalidade) {
		this.senhaPersonalidade = senhaPersonalidade;
	}

	@Column(name="CRACHA")
	public Long getCracha() {
		return cracha;
	}

	public void setCracha(Long cracha) {
		this.cracha = cracha;
	}

	@Column(name="NOME_GUERRA")
	public String getNomeGuerra() {
		return nomeGuerra;
	}

	public void setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
	}

	@OneToOne
	@JoinColumn(name="FK_CODIGO_AJUDA_CUSTOS")
	public AjudaCusto getAjudaCusto() {
		return ajudaCusto;
	}

	public void setAjudaCusto(AjudaCusto ajudaCusto) {
		this.ajudaCusto = ajudaCusto;
	}

	@OneToOne
	@JoinColumn(name="FK_CODIGO_LEIGOS")
	public LeigoPF getLeigo() {
		return leigo;
	}

	public void setLeigo(LeigoPF leigo) {
		this.leigo = leigo;
	}

	@OneToOne
	@JoinColumn(name="FK_CODIGO_PROFISSIONAIS")
	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
	@Transient
	public String getNome() {
		
		if(getProfissional() != null) {
			return nome = getProfissional().getPessoaFisica().getNome().toUpperCase();
		}else{
			return nome = getLeigo().getPessoaFisica().getNome().toUpperCase();
		}
		
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name="CODIGO_BARRAS")
	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

}
