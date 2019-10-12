package br.org.crea.portal.service;

import javax.inject.Inject;

import br.org.crea.commons.dao.atendimento.AtendimentoDao;
import br.org.crea.commons.dao.atendimento.AtendimentoLogDao;
import br.org.crea.commons.models.portal.dto.AtendimentoDto;
import br.org.crea.portal.converter.AtendimentoConverter;

public class AtendimentoService {
	
	@Inject
	AtendimentoConverter converter;
	
	@Inject
	AtendimentoDao dao;
	
	@Inject
	AtendimentoLogDao atendimentoLogDao;

	public AtendimentoDto getBy(Long numeroChamado) {
		return converter.toDto(dao.getAtendimentoBy(numeroChamado));
	}

	public boolean chamadoDisponivelParaPesquisa(Long numeroChamado) {
		return dao.atendimentoEstaDisponivelParaPesquisa(numeroChamado);
	}

	public AtendimentoDto atualizaPesquisa(AtendimentoDto dto) {
		dao.atualizaPesquisa(dto);
		return dto;
	}

}
