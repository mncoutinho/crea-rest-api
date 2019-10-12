package br.org.crea.commons.models.protocolo.dtos;

import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;

public class AutorizacaoProtocoloDto {

	private FuncionarioDto funcionario;
	
	private DomainGenericDto permissao;

	public FuncionarioDto getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioDto funcionario) {
		this.funcionario = funcionario;
	}

	public DomainGenericDto getPermissao() {
		return permissao;
	}

	public void setPermissao(DomainGenericDto permissao) {
		this.permissao = permissao;
	}
	
	public boolean ehAdministrador() {
		return this.permissao.getId().equals(new Long(9000));
	}
	
}
