package br.org.crea.commons.models.cadastro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CAD_CARTEIRAS_FILA")
public class CarteiraFila implements Serializable {

	private static final long serialVersionUID = -7454344946625315260L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="FK_CODIGO_PROFISSIONAL")
	private Long idProfissional;
	
	@Column(name="FK_UNIDADE_ATENDIMENTO")
	private Long idUnidadeAtendimento;
	
	@Column(name="FK_FUNCIONARIO")
	private Long idFuncionario;
	
	@Column(name="DATACADASTRO")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Column(name="DATAEMISSAO")
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;
	
	@Column(name="DATAEXCLUSAO")
	@Temporal(TemporalType.DATE)
	private Date dataExclusao;

	@Column(name="ATIVO")
	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(Long idProfissional) {
		this.idProfissional = idProfissional;
	}

	public Long getIdUnidadeAtendimento() {
		return idUnidadeAtendimento;
	}

	public void setIdUnidadeAtendimento(Long idUnidadeAtendimento) {
		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
}
