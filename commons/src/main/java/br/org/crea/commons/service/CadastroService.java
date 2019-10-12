package br.org.crea.commons.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.atendimento.UnidadeAtendimentoConverter;
import br.org.crea.commons.converter.cadastro.empresa.QuadroTecnicoConverter;
import br.org.crea.commons.dao.cadastro.CadastroDao;
import br.org.crea.commons.models.cadastro.dtos.UnidadeAtendimentoDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.QuadroTecnicoDto;

public class CadastroService {
	
	@Inject CadastroDao dao;
		
	@Inject QuadroTecnicoConverter converter;
	
	@Inject UnidadeAtendimentoConverter unidadeAtendimentoConverter;
	

	public List<QuadroTecnicoDto> getQuadosTecnicos(Long idPessoa, String type) {
		return converter.toListDto(dao.getQuadroBy(idPessoa, type));
	}
	
	public List<UnidadeAtendimentoDto> getUnidadesAtendimentoRegionalPor(Long matricula) {
		return unidadeAtendimentoConverter.toListDto(dao.getUnidadesAtendimentoRegionalPor(matricula));
	}
	
	public List<UnidadeAtendimentoDto> getUnidadesCoordenacaoPor(Long matricula) {
		return unidadeAtendimentoConverter.toListDto(dao.getUnidadesCoordenacaoPor(matricula));
	}
	
	public List<UnidadeAtendimentoDto> getUnidadesCoordenacaoSeat() {
		return unidadeAtendimentoConverter.toListDto(dao.getUnidadesCoordenacaoSeat());
	}

	public List<UnidadeAtendimentoDto> getAllUnidades() {
		return unidadeAtendimentoConverter.toListDto(dao.getAllUnidades());
	}

	public List<UnidadeAtendimentoDto> getAllUnidadesAtendimentoAdm() {
		return unidadeAtendimentoConverter.toListUnidadeAtendimentoDto(dao.getUnidadesAtendimento());
	}	
}
