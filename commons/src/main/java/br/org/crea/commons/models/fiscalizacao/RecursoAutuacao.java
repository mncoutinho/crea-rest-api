package br.org.crea.commons.models.fiscalizacao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="FIS_RECURSO")
@SequenceGenerator(name="RECURSO_SEQUENCE",sequenceName="FIS_RECURSO_SEQ",allocationSize = 1)
public class RecursoAutuacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RECURSO_SEQUENCE")
	private Long codigo;
	
	@Column(name="APRESENTOU_DEFESA_RECURSO")
	private Boolean apresentouDefesaRecurso;
	
	@Column(name="REGULARIZOU")
	private Boolean regularizou;
	
	@Column(name="PAGOU")
	private Boolean pagou;
	
	@Column(name="REVELIA")
	private Boolean Revelia;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_DECISAO")
	private Date dataDecisao;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_APRESENTACAO")
	private Date dataApresentacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CADASTRO")
	private Date dataCadastro;
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@Column(name="EMISSAO")
	private Date emissao;
	
	@Column(name="FK_CODIGO_SITUACAO_RECURSO")
	private Long idSituacaoRecurso;
	
	@Column(name="FK_CODIGO_INSTANCIA")
	private Long idInstancia;
	
	@Column(name="FK_CODIGO_PROTOCOLO")
	private  Long numeroProtocolo;
	
	@Column(name="FK_CODIGO_FUNCIONARIO")
	private Long idFuncionarioCadastro;
	
	@Column(name="DOCUMENTO_EMITIDO")
	private Boolean emiteOficio;
	
	@Column(name="ULTIMO")
	private Boolean ultimoRecurso;
	
	@Column(name="PROCESSO")
	private Long processo;

	public Long getCodigo() {
		return codigo;
	}

	public Boolean getApresentouDefesaRecurso() {
		return apresentouDefesaRecurso;
	}

	public Boolean getRegularizou() {
		return regularizou;
	}

	public Boolean getPagou() {
		return pagou;
	}

	public Boolean getRevelia() {
		return Revelia;
	}

	public Date getDataDecisao() {
		return dataDecisao;
	}

	public Date getDataApresentacao() {
		return dataApresentacao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public String getObservacao() {
		return observacao;
	}

	public Date getEmissao() {
		return emissao;
	}

	public Long getIdSituacaoRecurso() {
		return idSituacaoRecurso;
	}

	public Long getIdInstancia() {
		return idInstancia;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public Long getIdFuncionarioCadastro() {
		return idFuncionarioCadastro;
	}

	public Boolean getEmiteOficio() {
		return emiteOficio;
	}

	public Boolean getUltimoRecurso() {
		return ultimoRecurso;
	}

	public Long getProcesso() {
		return processo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public void setApresentouDefesaRecurso(Boolean apresentouDefesaRecurso) {
		this.apresentouDefesaRecurso = apresentouDefesaRecurso;
	}

	public void setRegularizou(Boolean regularizou) {
		this.regularizou = regularizou;
	}

	public void setPagou(Boolean pagou) {
		this.pagou = pagou;
	}

	public void setRevelia(Boolean revelia) {
		Revelia = revelia;
	}

	public void setDataDecisao(Date dataDecisao) {
		this.dataDecisao = dataDecisao;
	}

	public void setDataApresentacao(Date dataApresentacao) {
		this.dataApresentacao = dataApresentacao;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public void setIdSituacaoRecurso(Long idSituacaoRecurso) {
		this.idSituacaoRecurso = idSituacaoRecurso;
	}

	public void setIdInstancia(Long idInstancia) {
		this.idInstancia = idInstancia;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public void setIdFuncionarioCadastro(Long idFuncionarioCadastro) {
		this.idFuncionarioCadastro = idFuncionarioCadastro;
	}

	public void setEmiteOficio(Boolean emiteOficio) {
		this.emiteOficio = emiteOficio;
	}

	public void setUltimoRecurso(Boolean ultimoRecurso) {
		this.ultimoRecurso = ultimoRecurso;
	}

	public void setProcesso(Long processo) {
		this.processo = processo;
	}
	
}
