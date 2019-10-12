package br.org.crea.commons.models.corporativo.pessoa;

import java.io.Serializable;
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
import javax.persistence.Transient;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;


@Entity
@Table(name="CAD_EMPRESAS")
public class Empresa implements IInteressado, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="REGISTRO")
	private String registro;
	
	@Column(name="OBSERVACOES")
	private String observacoes;
	
	@OneToOne
	@JoinColumn(name="FK_ID_PESSOAS_JURIDICAS")
	private PessoaJuridica pessoaJuridica;

	@Column(name="DATAEXPREGISTRO")
	@Temporal(TemporalType.DATE)
	private Date dataExpedicaoRegistro;
	
	@Column(name="DATACADASTRO")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Column(name="DATAVALIDADE")
	@Temporal(TemporalType.DATE)
	private Date dataValidade;

	@Column(name="DATAVISTO")
	@Temporal(TemporalType.DATE)
	private Date dataVisto;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_TIPOS_EMPRESAS")
	private TipoEmpresa tipoEmpresa;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_TIPOS_CAT_REGI")
	private TipoCategoriaRegistro tipoCategoriaRegistro;

	@ManyToOne
	@JoinColumn(name="FK_CODIGO_TIPOS_GRUPOS")
	private TipoGrupo tipoGrupo;

	@Transient
	private String nomeRazaoSocial;
	
	@Transient
	public TipoPessoa getTipoPessoa() {
		return TipoPessoa.EMPRESA;
	}
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public Date getDataVisto() {
		return dataVisto;
	}

	public void setDataVisto(Date dataVisto) {
		this.dataVisto = dataVisto;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}
	
	public TipoCategoriaRegistro getTipoCategoriaRegistro() {
		return tipoCategoriaRegistro;
	}

	public void setTipoCategoriaRegistro(TipoCategoriaRegistro tipoCategoriaRegistro) {
		this.tipoCategoriaRegistro = tipoCategoriaRegistro;
	}
	
	public TipoGrupo getTipoGrupo() {
		return tipoGrupo;
	}

	public void setTipoGrupo(TipoGrupo tipoGrupo) {
		this.tipoGrupo = tipoGrupo;
	}

	@Override
	public String getRegistro() {
		return registro;
	}

	@Override
	public String getCpfCnpj() {
		return pessoaJuridica.getCnpj();
	}

	@Override
	public String getNomeRazaoSocial() {
		return this.nomeRazaoSocial;
	}

	@Override
	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
	}

	@Override
	public String getNome() {
		return this.getPessoaJuridica() != null ? this.getPessoaJuridica().getNomeFantasia() : "";
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	@Override
	public String getFotoBase64() {
		return null;
	}

	@Override
	public SituacaoRegistro getSituacao() {
		if(pessoaJuridica != null){
			return pessoaJuridica.getSituacao();
		}
		return null;
	}

	@Override
	public Departamento getDepartamento() {
		return null;
	}

	@Override
	public Long getMatricula() {
		return null;
	}

	
	@Override
	public void setDepartamento(Departamento departamento) {
		
	}

	@Override
	public String getPerfil() {
		return null;
	}

	public boolean temRegistroProvisorio() {
		return (this.getTipoCategoriaRegistro() != null && this.getTipoCategoriaRegistro().getCodigo() != null && this.getTipoCategoriaRegistro().getCodigo().equals(new Long(2))) ? true : false;
	}

	@Override
	public Long getIdPessoa() {
		return this.pessoaJuridica.getId();
	}

	public boolean situacaoEhNormal() {
		return this.getSituacao().getDescricao().equals("NORMAL");
	}

	public boolean situacaoEhInativa() {
		return this.getSituacao().getDescricao().equals("INTERROMPIDO") 
			|| this.getSituacao().getDescricao().equals("CANCELADO") 
			|| this.getSituacao().getDescricao().equals("SUSPENSO")
			|| this.getSituacao().getDescricao().equals("SEM VALIDADE");
	}

	public Date getDataExpedicaoRegistro() {
		return dataExpedicaoRegistro;
	}

	public void setDataExpedicaoRegistro(Date dataExpedicaoRegistro) {
		this.dataExpedicaoRegistro = dataExpedicaoRegistro;
	}

	public boolean temDataExpedicaoRegistro() {
		return this.dataExpedicaoRegistro != null;
	}
}
