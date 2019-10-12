package br.org.crea.commons.models.cadastro;

import java.math.BigDecimal;
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

import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;


@Entity
@Table(name="CAD_PESSOAS_REQUERIMENTOS_RPJ")
@SequenceGenerator(name = "REQ_RPJ_SEQ", sequenceName = "CAD_REQ_PJ", initialValue = 1, allocationSize = 1)
public class RequerimentoPJ {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REQ_RPJ_SEQ")
	@Column(name="CODIGO")
	private Long id;

	@OneToOne
	@JoinColumn(name="FK_EMPRESA_REQUERENTE")
	private Pessoa empresaRequerente;
	
	@OneToOne
	@JoinColumn(name="FK_PROFISSIONAL")
	private Pessoa profissionalResponsavel;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CADASTRO")
	private Date dataCadastro;
	
	@Column(name="NUMERO_ART")
	private String numeroArt;
	
	@Column(name="CARGA_HORARIA")
	private String cargaHoraria;
	
	@Column(name="SALARIO")
	private BigDecimal salario;
	
	@Column(name="FK_TIPO_VINCULO_TECNICO")
	private Long tipoVinculoTecnico;
	
	@OneToOne
	@JoinColumn(name="FK_PROTOCOLO")
	private Protocolo protocolo;
	
	@OneToOne
	@JoinColumn(name="FK_REQUERIMENTO")
	private LogRequerimento logRequerimento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getEmpresaRequerente() {
		return empresaRequerente;
	}

	public void setEmpresaRequerente(Pessoa empresaRequerente) {
		this.empresaRequerente = empresaRequerente;
	}

	public Pessoa getProfissionalResponsavel() {
		return profissionalResponsavel;
	}

	public void setProfissionalResponsavel(Pessoa profissionalResponsavel) {
		this.profissionalResponsavel = profissionalResponsavel;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Long getTipoVinculoTecnico() {
		return tipoVinculoTecnico;
	}

	public void setTipoVinculoTecnico(Long tipoVinculoTecnico) {
		this.tipoVinculoTecnico = tipoVinculoTecnico;
	}

	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	public LogRequerimento getLogRequerimento() {
		return logRequerimento;
	}

	public void setLogRequerimento(LogRequerimento logRequerimento) {
		this.logRequerimento = logRequerimento;
	}

}
