package br.org.crea.commons.models.cadastro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="CAD_RESPONSAVEIS_TECNICOS")
public class ResponsavelTecnico {
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DATAINICIORT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Column(name="DATAFIMRT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
	
	@Column(name="MATRICULA_ALTERACAO")
	private Long matriculaAlteracao;
	
	@Column(name="MATRICULA_BAIXA")
	private Long matriculaBaixa;
	
	@Column(name="DATAALTERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;
	
	@Column(name="DATAEFETIVACAOBAIXA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEfetivacaoBaixa;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_QUADROS_TECNICOS")
	private QuadroTecnico quadro;
	
	@OneToOne
	@JoinColumn(name="FK_COD_RAMOS_ATIVIDADE")
	private RamoAtividade ramoAtividade;

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

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public QuadroTecnico getQuadro() {
		return quadro;
	}

	public void setQuadro(QuadroTecnico quadro) {
		this.quadro = quadro;
	}

	public RamoAtividade getRamoAtividade() {
		return ramoAtividade;
	}

	public void setRamoAtividade(RamoAtividade ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}

	public boolean temRamoAtividade() {
		return this.ramoAtividade != null;
	}

	public boolean temRamo() {
		if (temRamoAtividade()) {
			return this.ramoAtividade.temRamo();
		}
		return false;
	}
	
	public boolean temAtividade() {
		if (temRamoAtividade()) {
			return this.ramoAtividade.temAtividade();
		}
		return false;
	}

	public Date getDataEfetivacaoBaixa() {
		return dataEfetivacaoBaixa;
	}

	public void setDataEfetivacaoBaixa(Date dataEfetivacaoBaixa) {
		this.dataEfetivacaoBaixa = dataEfetivacaoBaixa;
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

	public Long getMatriculaBaixa() {
		return matriculaBaixa;
	}

	public void setMatriculaBaixa(Long matriculaBaixa) {
		this.matriculaBaixa = matriculaBaixa;
	}

}
