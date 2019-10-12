package br.org.crea.commons.models.corporativo.pessoa;

import java.util.Base64;
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

import br.org.crea.commons.models.cadastro.CursoCadastro;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.InstituicaoEnsino;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

@Entity
@Table(name="CAD_FORMANDOS")
@SequenceGenerator(name="FORMANDOS_SEQUENCE",sequenceName="CAD_FORMANDOS_SEQ",allocationSize = 1)
public class Formando implements IInteressado {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FORMANDOS_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="REGISTRO")
	private String registro;
	
	@Column(name="DATAFORMATURA")
	@Temporal(TemporalType.DATE)
	private Date dataFormatura;
	
	@Column(name="PRECISAODATAFORMATURA")
	private String precisaoDataFormatura; //D,M,A
	
	@Column(name="DATACADASTRO")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Column(name="MATRICULA_CADASTRO")
	private Long matriculaCadastro;
	
	@Column(name="NUMEROPROCESSO")
	private String numeroProcesso;
	
	@Column(name="CADASTROATIVADO")
	private boolean cadastroAtivado;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_CURSOS")
	private CursoCadastro curso;
	
	@OneToOne
	@JoinColumn(name="FK_ID_PESSOAS_FISICAS")
	private PessoaFisica pessoaFisica;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_INSTITUICOES_ENSINO")
	private InstituicaoEnsino instituicaoEnsino;
	
	@Column(name="DATA_ALTERACAO")
	@Temporal(TemporalType.DATE)
	private Date dataAlteracao;
	
	@Column(name="MATRICULA_ALTERACAO")
	private Long matriculaAlteracao;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	@Override
	public TipoPessoa getTipoPessoa() {
		return TipoPessoa.FORMANDO;
	}

	@Override
	public String getCpfCnpj() {
		return pessoaFisica.getCpf();
	}

	@Override
	public String getNomeRazaoSocial() {
		return this.getPessoaFisica() != null ? this.getPessoaFisica().getNome() : "";
	}

	@Override
	public void setNomeRazaoSocial(String nomeRazaoSocial) {
		
	}

	@Override
	public String getNome() {
		return this.getPessoaFisica() != null ? this.getPessoaFisica().getNome() : "";
	}

	@Override
	public String getFotoBase64() {
		if(pessoaFisica.getFotografia() != null){
			StringBuilder sb = new StringBuilder();
			sb.append("data:image/png;base64,");
			sb.append(Base64.getEncoder().encodeToString(pessoaFisica.getFotografia()));
			return sb.toString();
		}else{
			return "";
		}
	}

	@Override
	public SituacaoRegistro getSituacao() {
		if(pessoaFisica != null){
			return pessoaFisica.getSituacao();
			
		}
		return null;
	}
	
	@Override
	public String getPerfil() {
		if(pessoaFisica != null){
			return pessoaFisica.getDescricaoPerfil();
			
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

	public boolean isCadastroAtivado() {
		return cadastroAtivado;
	}

	public void setCadastroAtivado(boolean cadastroAtivado) {
		this.cadastroAtivado = cadastroAtivado;
	}

	@Override
	public Long getIdPessoa() {
		return this.pessoaFisica.getId();
	}

	public Date getDataFormatura() {
		return dataFormatura;
	}

	public void setDataFormatura(Date dataFormatura) {
		this.dataFormatura = dataFormatura;
	}

	public String getPrecisaoDataFormatura() {
		return precisaoDataFormatura;
	}

	public void setPrecisaoDataFormatura(String precisaoDataFormatura) {
		this.precisaoDataFormatura = precisaoDataFormatura;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getMatriculaCadastro() {
		return matriculaCadastro;
	}

	public void setMatriculaCadastro(Long matriculaCadastro) {
		this.matriculaCadastro = matriculaCadastro;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public CursoCadastro getCurso() {
		return curso;
	}

	public void setCurso(CursoCadastro curso) {
		this.curso = curso;
	}

	public InstituicaoEnsino getInstituicaoEnsino() {
		return instituicaoEnsino;
	}

	public void setInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
		this.instituicaoEnsino = instituicaoEnsino;
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
	
	
}
