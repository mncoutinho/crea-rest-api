package br.org.crea.commons.models.corporativo.pessoa;

import java.util.Base64;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;



@Entity
@Table(name="CAD_LEIGOS_PF")
public class LeigoPF implements IInteressado{

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="REGISTRO")
	private String registro;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FK_ID_PESSOAS_FISICAS",nullable=false)
	private PessoaFisica pessoaFisica;
	
	@Column(name="CADASTROATIVADO")
	private boolean cadastroAtivado;

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
		return TipoPessoa.LEIGOPF;
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
	
//	@Override
//	public String getAssinaturaBase64() {
//		if(pessoaFisica.getAssinatura() != null){
//			StringBuilder sb = new StringBuilder();
//			sb.append("data:image/png;base64,");
//			sb.append(Base64.getEncoder().encodeToString(pessoaFisica.getAssinatura()));
//			return sb.toString();
//		}else{
//			return "";
//		}
//	}

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
	
	
	
	
}
