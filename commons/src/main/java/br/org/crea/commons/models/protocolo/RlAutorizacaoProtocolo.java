package br.org.crea.commons.models.protocolo;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.pessoa.Funcionario;


@Entity
@Table(name="PRT_AUTORIZACOES")
public class RlAutorizacaoProtocolo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@OneToOne
	@JoinColumn(name="FK_ID_PERMISSOES")
	private PermissaoProtocolo permissaoProtocolo;

	@Id
	@OneToOne
	@JoinColumn(name="FK_ID_FUNCIONARIOS")
	private Funcionario funcionario;
	
	public PermissaoProtocolo getPermissaoProtocolo() {
		return permissaoProtocolo;
	}

	public void setPermissaoProtocolo(PermissaoProtocolo permissaoProtocolo) {
		this.permissaoProtocolo = permissaoProtocolo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public Optional<Funcionario> optionalFuncionario() {
		return Optional.ofNullable(this.funcionario);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcionario == null) ? 0 : funcionario.hashCode());
		result = prime * result + ((permissaoProtocolo == null) ? 0 : permissaoProtocolo.hashCode());
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
		RlAutorizacaoProtocolo other = (RlAutorizacaoProtocolo) obj;
		if (funcionario == null) {
			if (other.funcionario != null)
				return false;
		} else if (!funcionario.equals(other.funcionario))
			return false;
		if (permissaoProtocolo == null) {
			if (other.permissaoProtocolo != null)
				return false;
		} else if (!permissaoProtocolo.equals(other.permissaoProtocolo))
			return false;
		return true;
	}
	
}
