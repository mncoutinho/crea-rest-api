package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.IInteressado;
import br.org.crea.commons.models.corporativo.pessoa.PessoaJuridica;

@Entity
@Table(name = "CAD_INSTITUICOES_ENSINO")
public class InstituicaoEnsino implements IInteressado{

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_ID_PESSOAS_JURIDICAS")
	private PessoaJuridica pessoaJuridica;
	
	@Transient
	private String nomeRazaoSocial;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getRegistro() {
		return null;
	}

	@Override
	public TipoPessoa getTipoPessoa() {
		return null;
	}

	@Override
	public String getCpfCnpj() {
		return null;
	}

	@Override
	public Long getMatricula() {
		return null;
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
	public void setDepartamento(Departamento departamento) {
		
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
	public String getPerfil() {
		return null;
	}

	@Override
	public Long getIdPessoa() {
		if(this.pessoaJuridica != null)
			return this.pessoaJuridica.getId();
		else
			return null;
	}

	@Override
	public String toString() {
		return "InstituicaoEnsino [id=" + id + ", pessoaJuridica=" + pessoaJuridica.getId() + ", nomeRazaoSocial="
				+ nomeRazaoSocial + "]";
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
