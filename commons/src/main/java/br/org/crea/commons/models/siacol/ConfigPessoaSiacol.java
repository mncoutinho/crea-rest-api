package br.org.crea.commons.models.siacol;

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

import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="SIACOL_CONFIG_PESSOA")
@SequenceGenerator(name="sqSiacolConfigPessoa",sequenceName="SQ_SIACOL_CONFIG_PESSOA", initialValue = 1, allocationSize = 1)
public class ConfigPessoaSiacol {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolConfigPessoa")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_FUNCIONARIO")	
	private Pessoa pessoa;
	
	@Column(name="DATA_INICIO", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Column(name="DATA_FIM", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
	
	@Column(name="ATIVO")
	private Boolean ativo;
	
	@Column(name="FK_ID_DEPARTAMENTO")
	private Long idDepartamento;

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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	
	
	
	
}
