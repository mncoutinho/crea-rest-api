package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.HistoricoConverter;
import br.org.crea.commons.dao.cadastro.pessoa.HistoricoDao;
import br.org.crea.commons.models.cadastro.dtos.HistoricoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;

public class HistoricoService {

	@Inject
	HistoricoConverter converter;

	@Inject
	HistoricoDao dao;

	public List<HistoricoDto> getHistoricosByPessoaEByEvento(Long idPessoa, Long idEvento) {
		return converter.toListHistoricoDto(dao.getHistoricosByPessoaEByEvento(idPessoa, idEvento));
	}

	public List<HistoricoDto> getHistoricosPaginadoByIdPessoa(PesquisaGenericDto pesquisa) {
		return converter.toListHistoricoDto(dao.getHistoricosPaginadoByPessoa(pesquisa));
	}

	public int getTotalDeHistoricos(PesquisaGenericDto pesquisa) {
		return dao.getTotalDeHistoricos(pesquisa);
	}

}
