package br.org.crea.commons.models.cadastro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="CAD_HISTORICOS")
@SequenceGenerator(name = "HISTORICOS_SEQUENCE", sequenceName = "CAD_HISTORICOS_SEQ", initialValue = 1, allocationSize = 1)
public class Historico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8473736060919016704L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISTORICOS_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="DATAINICIO")
	private Date dataInicio;

	@Temporal(TemporalType.DATE)
	@Column(name="DATAFINAL")
	private Date dataFinal;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_EVENTOS")
	private Evento evento;
	
	@Column(name="OBSERVACOES")
	private String observacoes;

	@OneToOne
	@JoinColumn(name="FK_CODIGO_PESSOAS")
	private Pessoa pessoa;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INCLUSAO")
	private Date dataInclusao;
	
	@Column(name="OPTLOCK")
	private Long optLock;
	
	@Column(name="MATRICULA_INCLUSAO")
	private Long matriculaInclusao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Long getOptLock() {
		return optLock;
	}

	public void setOptLock(Long optLock) {
		this.optLock = optLock;
	}

	public Long getMatriculaInclusao() {
		return matriculaInclusao;
	}

	public void setMatriculaInclusao(Long matriculaInclusao) {
		this.matriculaInclusao = matriculaInclusao;
	}
}
