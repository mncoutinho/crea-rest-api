package br.org.crea.commons.models.cadastro;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;


@Entity
@Table(name="CAD_QUADROS_TECNICOS")
public class QuadroTecnico {
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DATAINICIOQT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Column(name="DATAFIMQT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
	
	@Column(name="DATAADMISSAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAdmissao;
	
	@Column(name="DATADESLIGAMENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDesligamento;
	
	@Column(name="DATAEFETIVACAOBAIXA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEfetivacaoBaixa;
	
	@Column(name="DATACONTRATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataContrato;
	
	@Column(name="VALIDADECONTRATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataValidadeContrato;
	
	@Column(name="DATAALTERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;

	@Column(name="JORNADATRABALHO")
	private Long jornadaTrabalho;
	
	@Column(name="MATRICULA_ALTERACAO")
	private Long matriculaAlteracao;
	
	@Column(name="MATRICULA_BAIXA")
	private Long matriculaBaixa;
	
	@Column(name="REMUNERACAO")
	private BigDecimal remuneracao;
	
	@Column(name="RT")
	private Boolean ehResponsavelTecnico;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_EMPRESAS")
	private Empresa empresa;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_PROFISSIONAIS")
	private Profissional profissional;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_TIPOS_VINCULOS")
	private TipoVinculoQuadroTecnico vinculo;
	
	//FIXME retirar este mapeamento
	@OneToMany(mappedBy = "quadro", fetch= FetchType.EAGER)
	private List<ResponsavelTecnico> responsaveis;
	
	@Transient
	private boolean ehVisaoProfissional;

	public Boolean getEhResponsavelTecnico() {
		return ehResponsavelTecnico;
	}

	public void setEhResponsavelTecnico(Boolean ehResponsavelTecnico) {
		this.ehResponsavelTecnico = ehResponsavelTecnico;
	}

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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public List<ResponsavelTecnico> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<ResponsavelTecnico> responsaveis) {
		this.responsaveis = responsaveis;
	}
	
	public Long getJornadaTrabalho() {
		return jornadaTrabalho;
	}

	public void setJornadaTrabalho(Long jornadaTrabalho) {
		this.jornadaTrabalho = jornadaTrabalho;
	}
	
	public TipoVinculoQuadroTecnico getVinculo() {
		return vinculo;
	}

	public void setVinculo(TipoVinculoQuadroTecnico vinculo) {
		this.vinculo = vinculo;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Date getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(Date dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}

	public Date getDataContrato() {
		return dataContrato;
	}

	public void setDataContrato(Date dataContrato) {
		this.dataContrato = dataContrato;
	}

	public Date getDataValidadeContrato() {
		return dataValidadeContrato;
	}

	public void setDataValidadeContrato(Date dataValidadeContrato) {
		this.dataValidadeContrato = dataValidadeContrato;
	}

	public BigDecimal getRemuneracao() {
		return remuneracao;
	}

	public void setRemuneracao(BigDecimal remuneracao) {
		this.remuneracao = remuneracao;
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

	public boolean isEhVisaoProfissional() {
		return ehVisaoProfissional;
	}

	public void setEhVisaoProfissional(boolean ehVisaoProfissional) {
		this.ehVisaoProfissional = ehVisaoProfissional;
	}

}
