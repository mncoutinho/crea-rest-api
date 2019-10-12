package br.org.crea.corporativo.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.funcionario.FuncionarioConverter;
import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;

public class FuncionarioService {
	
	
	@Inject FuncionarioConverter converter;
	
	@Inject FuncionarioDao dao;

	public List<FuncionarioDto> getAll() {
		return converter.toListDto(dao.getAll());
	}
	
	public List<FuncionarioDto> getFuncionarios(PesquisaGenericDto pesquisa){
		return converter.toListDto(dao.getFuncionariosByDepartamento(pesquisa));
		
	}
	
	public List<FuncionarioDto> getFuncionariosPorNome(PesquisaGenericDto pesquisa){
		return converter.toListDto(dao.getFuncionariosPorNome(pesquisa));
		
	}

	public FuncionarioDto getFuncionariosPorMatricula(Long matricula) {
		return converter.toDto(dao.getFuncionariosPorMatricula(matricula));
	}

}