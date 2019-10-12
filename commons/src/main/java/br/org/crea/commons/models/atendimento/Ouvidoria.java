package br.org.crea.commons.models.atendimento;

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

import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name = "OUVI_ATENDIMENTO")
@SequenceGenerator(name = "sqOuvidoria", sequenceName = "OUVI_ATENDIMENTO_SEQ")
public class Ouvidoria {

	@Id
	@Column(name = "CODIGO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqOuvidoria")
	private Long id;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "DATA_ATENDIMENTO")
	private Date dataAtendimento;

	@Column(name = "PROVIDENCIA")
	private String providencia;

	@Column(name = "SOLUCAO")
	private String solucao;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_PESSOAS")
	private Pessoa pessoa;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_TIPOS_DEMANDA")
	private OuvidoriaTipoDemanda tipoDemanda;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_ASSUNTOS")
	private OuvidoriaAssunto assunto;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_ORIGENS")
	private OuvidoriaOrigem origem;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_FORMAS_ATENDIMENTO")
	private OuvidoriaFormaAtendimento formaAtendimento;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_SITUACOES")
	private OuvidoriaSituacoes situacao;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_ASSUNTOS_ESPECIFICOS")
	private OuvidoriaAssuntosEspecificos assuntoEspecifico;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_ASSUNTOS_GERAIS")
	private OuvidoriaAssuntosGerais assuntoGeral;

	@OneToOne
	@JoinColumn(name = "FK_NUMERO_PROTOCOLO")
	private Protocolo protocolo;
	
	@OneToOne
	@JoinColumn(name = "FK_ARQUIVO")
	private Arquivo arquivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public OuvidoriaTipoDemanda getTipoDemanda() {
		return tipoDemanda;
	}

	public void setTipoDemanda(OuvidoriaTipoDemanda tipoDemanda) {
		this.tipoDemanda = tipoDemanda;
	}

	@Override
	public String toString() {
		return "Ouvidoria [id=" + id + ", descricao=" + descricao + ", pessoa=" + pessoa + ", tipoDemanda="
				+ tipoDemanda.getDescricao() + "]";
	}

	public OuvidoriaAssunto getAssunto() {
		return assunto;
	}

	public void setAssunto(OuvidoriaAssunto assunto) {
		this.assunto = assunto;
	}

	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	public OuvidoriaOrigem getOrigem() {
		return origem;
	}

	public void setOrigem(OuvidoriaOrigem origem) {
		this.origem = origem;
	}

	public OuvidoriaFormaAtendimento getFormaAtendimento() {
		return formaAtendimento;
	}

	public void setFormaAtendimento(OuvidoriaFormaAtendimento formaAtendimento) {
		this.formaAtendimento = formaAtendimento;
	}

	public OuvidoriaSituacoes getSituacao() {
		return situacao;
	}

	public void setSituacao(OuvidoriaSituacoes situacao) {
		this.situacao = situacao;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public boolean existeTipoDemanda() {
		return this.tipoDemanda != null;
	}

	public boolean existeAssunto() {
		return this.assunto != null;
	}

	public boolean existeSituacao() {

		return this.situacao != null;
	}

	public boolean existeFormaAtendimento() {
		return this.formaAtendimento != null;
	}

	public boolean existeOrigem() {
		return this.origem != null;
	}

	public String getProvidencia() {
		return providencia;
	}

	public void setProvidencia(String providencia) {
		this.providencia = providencia;
	}

	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	public boolean existeProvidencia() {
		return this.providencia != null;
	}

	public boolean existeSolucao() {
		return this.solucao != null;
	}

	public boolean existeAssuntoGeral() {
		return this.assuntoGeral != null;
	}

	public boolean existeAssuntoEspecifico() {
		return this.assuntoEspecifico != null;
	}

	public OuvidoriaAssuntosEspecificos getAssuntoEspecifico() {
		return assuntoEspecifico;
	}

	public void setAssuntoEspecifico(OuvidoriaAssuntosEspecificos assuntoEspecifico) {
		this.assuntoEspecifico = assuntoEspecifico;
	}

	public OuvidoriaAssuntosGerais getAssuntoGeral() {
		return assuntoGeral;
	}

	public void setAssuntoGeral(OuvidoriaAssuntosGerais assuntoGeral) {
		this.assuntoGeral = assuntoGeral;
	}

	public boolean existeId() {
		return this.id != null;
	}

	public boolean existeDataAtendimento() {
		return this.dataAtendimento != null;
	}

	public boolean existeDescricao() {
		return this.descricao != null;
	}

	public boolean existeProtocolo() {
		return this.protocolo != null;
	}

	public boolean temArquivo() {
		return this.arquivo != null;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}
	
	
}
