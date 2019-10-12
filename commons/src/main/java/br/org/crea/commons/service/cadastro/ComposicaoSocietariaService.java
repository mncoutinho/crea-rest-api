package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.empresa.ComposicaoSocietariaConverter;
import br.org.crea.commons.dao.cadastro.empresa.ComposicaoSocietariaDao;
import br.org.crea.commons.models.cadastro.dtos.empresa.ComposicaoSocietariaDto;

public class ComposicaoSocietariaService {

	@Inject
	ComposicaoSocietariaConverter converter;

	@Inject
	ComposicaoSocietariaDao dao;

	public List<ComposicaoSocietariaDto> getComposicaoSocietariaByIdEmpresa(Long idPessoa) {
		return converter.toListDto(dao.getSociosByIdEmpresa(idPessoa));
	}

}
