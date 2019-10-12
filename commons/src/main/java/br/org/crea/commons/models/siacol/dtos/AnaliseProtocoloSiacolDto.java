package br.org.crea.commons.models.siacol.dtos;

import java.util.List;

import br.org.crea.commons.models.cadastro.dtos.empresa.EmpresaDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.ResponsavelTecnicoDto;
import br.org.crea.commons.models.fiscalizacao.dtos.AutoInfracaoDto;


public class AnaliseProtocoloSiacolDto {
	
	private List<ResponsavelTecnicoDto> listResponsaveisTecnicos;
	
	private EmpresaDto empresa;
	
	private AutoInfracaoDto autoInfracao;
	
	public List<ResponsavelTecnicoDto> getListResponsaveisTecnicos() {
		return listResponsaveisTecnicos;
	}

	public void setListResponsaveisTecnicos(List<ResponsavelTecnicoDto> listResponsaveisTecnicos) {
		this.listResponsaveisTecnicos = listResponsaveisTecnicos;
	}

	public EmpresaDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDto empresa) {
		this.empresa = empresa;
	}

	public AutoInfracaoDto getAutoInfracao() {
		return autoInfracao;
	}

	public void setAutoInfracao(AutoInfracaoDto autoInfracao) {
		this.autoInfracao = autoInfracao;
	}

	
}
