package br.org.crea.commons.service.commons;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.commons.RlArquivoConverter;
import br.org.crea.commons.dao.commons.ArquivoDao;
import br.org.crea.commons.dao.commons.RlArquivoDao;
import br.org.crea.commons.models.commons.RlArquivo;
import br.org.crea.commons.models.commons.dtos.RlArquivoDto;

public class RlArquivoService {
	
	@Inject RlArquivoDao dao;
	
	@Inject RlArquivoConverter converter;
	
	@Inject ArquivoService arquivoService;
	
	@Inject ArquivoDao arquivoDao;

	public RlArquivoDto criarRlArquivo(RlArquivoDto dto) {
		return converter.toDto(dao.create(converter.toModel(dto)));
	}

	public boolean delete(Long id) {
		
		try {
			RlArquivo rlArquivo = dao.getBy(id);
			arquivoService.deletaSomenteArquivo(rlArquivo.getArquivo().getId());
			dao.deleta(id);
			arquivoDao.deleta(rlArquivo.getArquivo().getId());
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	public List<RlArquivoDto> getRlByTipoId(RlArquivoDto dto) {
		return converter.toListDto(dao.getRlByTipoId(dto));
	}

}
