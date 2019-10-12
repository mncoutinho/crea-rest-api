package br.org.crea.commons.models.corporativo.pessoa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;


@Entity
@Table(name="CAD_LEIGOS_PJ")
public class LeigoPJ implements IInteressado{

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="REGISTRO")
	private String registro;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FK_ID_PESSOAS_JURIDICAS")
	private PessoaJuridica pessoaJuridica;
	
	@Column(name="CADASTROATIVADO")
	private boolean cadastroAtivado;	
	
	@Transient
	private String nomeRazaoSocial;
	
	
	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}


	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setRegistro(String registro) {
		this.registro = registro;
	}


	@Transient
	public TipoPessoa getTipoPessoa() {
		return TipoPessoa.LEIGOPJ;
	}


	@Override
	public Long getId() {
		return id;
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
	public void setNomeRazaoSocial( String nomeRazaoSocial) {
		this.nomeRazaoSocial = nomeRazaoSocial;
	}


	@Override
	public String getNome() {
		return null;
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


	public boolean isCadastroAtivado() {
		return cadastroAtivado;
	}


	public void setCadastroAtivado(boolean cadastroAtivado) {
		this.cadastroAtivado = cadastroAtivado;
	}


	@Override
	public String getPerfil() {
		return null;
	}
	
	@Override
	public Long getIdPessoa() {
		return this.pessoaJuridica.getId();
	}
	
}
