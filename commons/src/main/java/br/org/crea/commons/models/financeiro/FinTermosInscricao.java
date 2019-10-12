package br.org.crea.commons.models.financeiro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.financeiro.enuns.FinFase;

@Entity
@Table(name="FIN_TERMOS_INSCRICAO")
@SequenceGenerator(name="FIN_TERMOS_INSCRICAO_SEQUENCE", sequenceName="FIN_TERMOS_INSCRICAO_SEQ",allocationSize = 1)
public class FinTermosInscricao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_TERMOS_INSCRICAO_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@Column(name="DATA_INCLUSAO")
	private Date dataInclusao;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_PESSOA")
	private Pessoa pessoa;
	
	@Column(name="ATIVO")
	private boolean ativo;
	
	@Column(name="TIPO_PESSOA")
	private TipoPessoa tipoPessoa;
	
	@Column(name="FK_CODIGO_FASE")
	private FinFase finFase;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_NATUREZA")
	private NaturezaDivida natureza;
	
	@Column(name="FK_CODIGO_PROCESSO")
	private Long codigoProcesso;
	
	@Column(name="NUMERO_TERMO")
	private Long numeroTermo;

	@Column(name="FK_CODIGO_MATRICULA")
	private Long codigoMatricula;
	
	@Column(name="DATA_ATUAL")
	private Date dataAtual;
	
	@Column(name="DATA_EMISSAO_SERIE")
	private Date dataEmissaoSerie;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public FinFase getFinFase() {
		return finFase;
	}

	public void setFinFase(FinFase finFase) {
		this.finFase = finFase;
	}

	public NaturezaDivida getNatureza() {
		return natureza;
	}

	public void setNatureza(NaturezaDivida natureza) {
		this.natureza = natureza;
	}

	public Long getCodigoProcesso() {
		return codigoProcesso;
	}

	public void setCodigoProcesso(Long codigoProcesso) {
		this.codigoProcesso = codigoProcesso;
	}

	public Long getNumeroTermo() {
		return numeroTermo;
	}

	public void setNumeroTermo(Long numeroTermo) {
		this.numeroTermo = numeroTermo;
	}

	public Long getCodigoMatricula() {
		return codigoMatricula;
	}

	public void setCodigoMatricula(Long codigoMatricula) {
		this.codigoMatricula = codigoMatricula;
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	public Date getDataEmissaoSerie() {
		return dataEmissaoSerie;
	}

	public void setDataEmissaoSerie(Date dataEmissaoSerie) {
		this.dataEmissaoSerie = dataEmissaoSerie;
	}
	
	
}
