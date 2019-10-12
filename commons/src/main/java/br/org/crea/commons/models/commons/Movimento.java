package br.org.crea.commons.models.commons;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.cadastro.Departamento;

@Entity
@Table(name = "PRT_MOVIMENTOS")
@SequenceGenerator(name="MOVIMENTOS_SEQUENCE", sequenceName="PRT_MOVIMENTOS_SEQ", allocationSize=1)
public class Movimento {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MOVIMENTOS_SEQUENCE")
	@Column(name = "ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "FK_ID_ORIGEM_DEPARTAMENTOS")
	private Departamento departamentoOrigem;

	@OneToOne
	@JoinColumn(name = "FK_ID_DESTINO_DEPARTAMENTOS")
	private Departamento departamentoDestino;

	@Column(name = "DATAENVIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvio;

	@Column(name = "DATARECEBIMENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRecebimento;

	@OneToOne
	@JoinColumn(name = "FK_ID_SITUACOES")
	private SituacaoProtocolo situacao;
	
	@OneToOne
	@JoinColumn(name = "FK_ID_PROTOCOLOS")
	private Protocolo protocolo;
	
	@Column(name="FK_ID_FUNCIONARIOS_RECEPTOR")
	private Long idFuncionarioReceptor;
	
	@Column(name="FK_ID_FUNCIONARIOS_REMETENTE")
	private Long idFuncionarioRemetente;
	
	@Column(name="TEMPOPERMANENCIA")
	private Long tempoPermanencia;
	
	@Column(name="DESPACHADO")
	private boolean despachado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Departamento getDepartamentoOrigem() {
		return departamentoOrigem;
	}

	public void setDepartamentoOrigem(Departamento departamentoOrigem) {
		this.departamentoOrigem = departamentoOrigem;
	}

	public Departamento getDepartamentoDestino() {
		return departamentoDestino;
	}

	public void setDepartamentoDestino(Departamento departamentoDestino) {
		this.departamentoDestino = departamentoDestino;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public SituacaoProtocolo getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProtocolo situacao) {
		this.situacao = situacao;
	}

	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	public Long getIdFuncionarioReceptor() {
		return idFuncionarioReceptor;
	}

	public Long getIdFuncionarioRemetente() {
		return idFuncionarioRemetente;
	}

	public void setIdFuncionarioReceptor(Long idFuncionarioReceptor) {
		this.idFuncionarioReceptor = idFuncionarioReceptor;
	}

	public void setIdFuncionarioRemetente(Long idFuncionarioRemetente) {
		this.idFuncionarioRemetente = idFuncionarioRemetente;
	}

	public Long getTempoPermanencia() {
		return tempoPermanencia;
	}

	public void setTempoPermanencia(Long tempoPermanencia) {
		this.tempoPermanencia = tempoPermanencia;
	}

	public boolean isDespachado() {
		return despachado;
	}

	public void setDespachado(boolean despachado) {
		this.despachado = despachado;
	}
	
	public boolean destinoEhArquivoVirtual() {
		return departamentoDestino.getId().equals(new Long(23040502)) ? true : false;
	}
	
	public boolean temDepartamentoDestino() {
		return this.departamentoDestino != null;
	}
	
	public boolean temDepartamentoOrigem() {
		return this.departamentoOrigem != null;
	}
}