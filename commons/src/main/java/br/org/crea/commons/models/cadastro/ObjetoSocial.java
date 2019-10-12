package br.org.crea.commons.models.cadastro;

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

import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;

@Entity
@Table(name="CAD_OBJETOS_SOCIAIS")
@SequenceGenerator(name="OBJETOS_SOCIAIS_SEQUENCE",sequenceName="CAD_OBJETOS_SOCIAIS_SEQ",allocationSize = 1)
public class ObjetoSocial implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OBJETOS_SOCIAIS_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="OBSERVACAO")
	private String observacao;
	
	@Column(name="MATRICULA_CADASTRO")
	private Long matriculaCadastro;
	
	@Column(name="DATA_CADASTRO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@Column(name="MATRICULA_ALTERACAO")
	private Long matriculaAlteracao;
	
	@Column(name="DATA_ALTERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;
	
	@Column(name="FK_CODIGO_TIPOS_GRUPOS")
	private Long tipoGrupo;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_EMPRESAS")
	private Empresa empresa;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_PJS")
	private PessoaJuridica empresaNovaInscrita;

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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getMatriculaCadastro() {
		return matriculaCadastro;
	}

	public void setMatriculaCadastro(Long matriculaCadastro) {
		this.matriculaCadastro = matriculaCadastro;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getMatriculaAlteracao() {
		return matriculaAlteracao;
	}

	public void setMatriculaAlteracao(Long matriculaAlteracao) {
		this.matriculaAlteracao = matriculaAlteracao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Long getTipoGrupo() {
		return tipoGrupo;
	}

	public void setTipoGrupo(Long tipoGrupo) {
		this.tipoGrupo = tipoGrupo;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public PessoaJuridica getEmpresaNovaInscrita() {
		return empresaNovaInscrita;
	}

	public void setEmpresaNovaInscrita(PessoaJuridica empresaNovaInscrita) {
		this.empresaNovaInscrita = empresaNovaInscrita;
	}
	
}
