package br.org.crea.corporativo.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.corporativo.dtos.DocumentacaoDto;
import br.org.crea.corporativo.converter.DocumentacaoConverter;
import br.org.crea.corporativo.dao.DocumentacaoDao;

public class DocumentacaoService {

	@Inject
	DocumentacaoConverter converter;

	@Inject
	DocumentacaoDao dao;

	public List<DocumentacaoDto> getDocumentacaoDisponiveis(Long idAssunto) {
		return converter.toListDto(dao.getDocumentacaoDisponiveis(idAssunto));
	}
	
	public List<DocumentacaoDto> getDocumentacaoIndisponiveis(Long idAssunto) {
		return converter.toListDto(dao.getDocumentacaoIndisponiveis(idAssunto));
	}

//	public List<AssuntoDto> getAssuntosDisponiveis() {
//		return converter.toListDto(dao.getAssuntosDisponiveis());
//	}
//	
//	public List<AssuntoDto> getAssuntosComDocumentacao() {
//		return converter.toListDto(dao.getAssuntosComDocumentacao());
//	}
	
	
}
