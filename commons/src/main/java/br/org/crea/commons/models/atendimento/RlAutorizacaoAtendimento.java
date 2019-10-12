package br.org.crea.commons.models.atendimento;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.pessoa.Funcionario;


@Entity
@Table(name="ATE_AUTORIZACOES")
public class RlAutorizacaoAtendimento implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="FK_ATE_PERMISSOES")
	private PermissaoAtendimento permissaoAtendimento;

	@Id
	@ManyToOne
	@JoinColumn(name="FK_CAD_FUNCIONARIO")
	private Funcionario funcionario;
	
	

	public PermissaoAtendimento getPermissaoAtendimento() {
		return permissaoAtendimento;
	}

	public void setPermissaoAtendimento(PermissaoAtendimento permissaoAtendimento) {
		this.permissaoAtendimento = permissaoAtendimento;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((permissaoAtendimento == null) ? 0 : permissaoAtendimento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RlAutorizacaoAtendimento other = (RlAutorizacaoAtendimento) obj;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (permissaoAtendimento == null) {
			if (other.permissaoAtendimento != null)
				return false;
		} else if (!permissaoAtendimento.equals(other.permissaoAtendimento))
			return false;
		return true;
	}

	
	

}
