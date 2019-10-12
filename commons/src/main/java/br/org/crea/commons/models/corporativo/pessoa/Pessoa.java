package br.org.crea.commons.models.corporativo.pessoa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "CAD_PESSOAS")
@SequenceGenerator(name = "PESSOAS_SEQUENCE", sequenceName = "CAD_PESSOAS_SEQ",allocationSize = 1)
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO")
	private Long id;

	@Column(name = "SENHA")
	private String senha;

	@Column(name = "PERFIL")
	private String perfil;
	
	@Column(name = "ID_INSTITUICAO")
	private Long idInstituicao;

	@Column(name = "DS_PERFIL")
	private String descricaoPerfil;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPOPESSOA")
	private TipoPessoa tipoPessoa;

	@OneToOne
	@JoinColumn(name = "FK_CODIGO_SITUACAO_REGISTRO")
	private SituacaoRegistro situacao;

	@Column(name = "FK_PESSOA_RESPONSAVEL")
	private Long idPessoaResponsavel;

	@Column(name = "DATA_CRIACAO_LOGIN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacaoLogin;

	@Column(name = "DATA_SENHA")
	@Temporal(TemporalType.DATE)
	private Date dataSenha;

	@Transient
	private boolean semRegistroNoCrea;

	@Transient
	private String cpfOuCnpj;

	@Transient
	private String nome;

	@Transient
	private String email;

	public Long getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(Long idInstituicao) {
		this.idInstituicao = idInstituicao;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public SituacaoRegistro getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoRegistro situacao) {
		this.situacao = situacao;
	}

	public boolean isSemRegistroNoCrea() {
		return semRegistroNoCrea;
	}

	public void setSemRegistroNoCrea(boolean semRegistroNoCrea) {
		this.semRegistroNoCrea = semRegistroNoCrea;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public boolean temSituacao() {
		return this.situacao != null ? true : false;
	}

	public String getDescricaoPerfil() {
		return descricaoPerfil;
	}

	public void setDescricaoPerfil(String descricaoPerfil) {
		this.descricaoPerfil = descricaoPerfil;
	}

	public Long getIdPessoaResponsavel() {
		return idPessoaResponsavel;
	}

	public void setIdPessoaResponsavel(Long idPessoaResponsavel) {
		this.idPessoaResponsavel = idPessoaResponsavel;
	}

	public Date getDataSenha() {
		return dataSenha;
	}

	public void setDataSenha(Date dataSenha) {
		this.dataSenha = dataSenha;
	}

	public Date getDataCriacaoLogin() {
		return dataCriacaoLogin;
	}

	public void setDataCriacaoLogin(Date dataCriacaoLogin) {
		this.dataCriacaoLogin = dataCriacaoLogin;
	}
	public boolean temIdInstituicao() {
		return this.idInstituicao != null ? true : false;
	}
	
	public boolean ehPessoaFisica() {
		return  this.tipoPessoa == TipoPessoa.LEIGOPF || 
				this.tipoPessoa == TipoPessoa.PESSOAFISICA || 
				this.tipoPessoa == TipoPessoa.PROFISSIONAL || 
				this.tipoPessoa == TipoPessoa.FUNCIONARIO || 
				this.tipoPessoa == TipoPessoa.FORMANDO;    
	}
	
	public boolean ehPessoaJuridica() {
		return  this.tipoPessoa == TipoPessoa.EMPRESA || 
				this.tipoPessoa == TipoPessoa.LEIGOPJ || 
				this.tipoPessoa == TipoPessoa.PESSOAJURIDICA || 
				this.tipoPessoa == TipoPessoa.ESCOLA || 
				this.tipoPessoa == TipoPessoa.ENTIDADE;    
	}

}
