package br.org.crea.commons.models.corporativo.pessoa;

import java.io.Serializable;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.cadastro.Cargo;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;

@Entity
@Table(name = "CAD_FUNCIONARIOS")
@SequenceGenerator(name = "FUNCIONARIOS_SEQUENCE", sequenceName = "CAD_FUNCIONARIOS_SEQ",allocationSize = 1)
public class Funcionario implements IInteressado, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUNCIONARIOS_SEQUENCE")
	@Column(name = "CODIGO")
	private Long id;

	@Column(name = "MATRICULA")
	private Long matricula;

	@OneToOne
	@JoinColumn(name = "FK_ID_PESSOAS_FISICAS", nullable = false)
	private PessoaFisica pessoaFisica;

	@OneToOne
	@JoinColumn(name = "FK_ID_DEPARTAMENTOS")
	private Departamento departamento;
	
	@Column(name="CADASTROATIVO")
	private Boolean cadastroAtivo;
	
	@OneToOne
	@JoinColumn(name = "FK_CODIGO_CARGOS")
	private Cargo cargo;

	public boolean temCargo() {
		return this.cargo != null ? true : false;
	}
	
	public boolean temDepartamento() {
		return this.departamento != null ? true : false;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public String getRegistro() {
		return String.valueOf(pessoaFisica.getId());
	}

	@Override
	public TipoPessoa getTipoPessoa() {
		return TipoPessoa.FUNCIONARIO;
	}

	@Override
	public String getCpfCnpj() {
		return pessoaFisica.getCpf();
	}

	@Override
	public String getPerfil() {
		return pessoaFisica.getDescricaoPerfil();
	}
	
	@Override
	public String getNomeRazaoSocial() {
		return null;
	}

	@Override
	public void setNomeRazaoSocial(String nomeRazaoSocial) {
	}

	@Override
	public String getNome() {
		return pessoaFisica.getNome();
	}

	@Override
	public String getFotoBase64() {

		if (pessoaFisica.getFotografia() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("data:image/png;base64,");
			sb.append(Base64.getEncoder().encodeToString(pessoaFisica.getFotografia()));
			return sb.toString();
		} else {
			return "";
		}

	}

	@Override
	public SituacaoRegistro getSituacao() {

		if (pessoaFisica != null) {
			return pessoaFisica.getSituacao();
		}
		return null;
	}

	@Override
	public Departamento getDepartamento() {
		return this.departamento;
	}

	public Boolean getCadastroAtivo() {
		return cadastroAtivo;
	}

	public void setCadastroAtivo(Boolean cadastroAtivo) {
		this.cadastroAtivo = cadastroAtivo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	@Override
	public Long getIdPessoa() {
		return this.pessoaFisica.getId();
	}
	public static Funcionario getUsuarioPortal() { 
		Funcionario funcionario = new Funcionario();
		funcionario.setId(getIdUsuarioPortal());
		return funcionario;
	}
	public static Long getIdUsuarioPortal() { 
		return 99990L;

	}

}
