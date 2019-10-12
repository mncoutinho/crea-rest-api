package br.org.crea.commons.models.art;

import java.io.Serializable;
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

import br.org.crea.commons.models.corporativo.pessoa.Funcionario;

@Entity
@SequenceGenerator(name = "EXIGENCIA_ART_SEQUENCE", sequenceName = "ART_EXIGENCIA_ART_SEQ", initialValue = 1, allocationSize = 1)
@Table(name="ART_EXIGENCIA_ART")
public class ExigenciaArt implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8529652603076892515L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXIGENCIA_ART_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_ART")
	private Art art;
	
	@OneToOne
	@JoinColumn(name="FK_EXIGENCIA")
	private ArtExigencia exigencia;
	
	@OneToOne
	@JoinColumn(name="FK_CONTRATO")
	private ContratoArt contrato;
	
	@OneToOne
	@JoinColumn(name="FK_TIPO_ACAO_ART")
	private ArtTipoAcao tipoAcaoArt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA")
	private Date data;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_ANALISE")
	private Date dataAnalise;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_LIBERACAO")
	private Date dataLiberacao;
	
	@OneToOne
	@JoinColumn(name="FK_FUNCIONARIO_ANALISE")
	private Funcionario funcionarioAnalise;
	
	@OneToOne
	@JoinColumn(name="FK_FUNCIONARIO_LIBERACAO")
	private Funcionario funcionarioLiberacao;
	
	@Column(name="MOTIVO")
	private String motivo;
	
	@OneToOne
	@JoinColumn(name="FK_SITUACAO_LIBERACAO")
	private ArtSituacaoLiberacao situacaoLiberacao;
	
	@Column(name="DESCRICAO_LIBERACAO")
	private String descricaoLiberacao;

	public Art getArt() {
		return art;
	}

	public void setArt(Art art) {
		this.art = art;
	}

	public ArtExigencia getExigencia() {
		return exigencia;
	}

	public void setExigencia(ArtExigencia exigencia) {
		this.exigencia = exigencia;
	}

	public ContratoArt getContrato() {
		return contrato;
	}

	public void setContrato(ContratoArt contrato) {
		this.contrato = contrato;
	}

	public ArtTipoAcao getTipoAcaoArt() {
		return tipoAcaoArt;
	}

	public void setTipoAcaoArt(ArtTipoAcao tipoAcaoArt) {
		this.tipoAcaoArt = tipoAcaoArt;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataAnalise() {
		return dataAnalise;
	}

	public void setDataAnalise(Date dataAnalise) {
		this.dataAnalise = dataAnalise;
	}

	public Date getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	public Funcionario getFuncionarioAnalise() {
		return funcionarioAnalise;
	}

	public void setFuncionarioAnalise(Funcionario funcionarioAnalise) {
		this.funcionarioAnalise = funcionarioAnalise;
	}

	public Funcionario getFuncionarioLiberacao() {
		return funcionarioLiberacao;
	}

	public void setFuncionarioLiberacao(Funcionario funcionarioLiberacao) {
		this.funcionarioLiberacao = funcionarioLiberacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public ArtSituacaoLiberacao getSituacaoLiberacao() {
		return situacaoLiberacao;
	}

	public void setSituacaoLiberacao(ArtSituacaoLiberacao situacaoLiberacao) {
		this.situacaoLiberacao = situacaoLiberacao;
	}

	public String getDescricaoLiberacao() {
		return descricaoLiberacao;
	}

	public void setDescricaoLiberacao(String descricaoLiberacao) {
		this.descricaoLiberacao = descricaoLiberacao;
	}
	

}
