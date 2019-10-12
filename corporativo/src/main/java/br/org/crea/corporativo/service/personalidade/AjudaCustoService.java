package br.org.crea.corporativo.service.personalidade;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.corporativo.personalidade.dto.AjudaCustoDto;
import br.org.crea.commons.models.corporativo.personalidade.entity.AjudaCusto;
import br.org.crea.corporativo.converter.personalidade.AjudaCustoConverter;
import br.org.crea.corporativo.dao.personalidade.AjudaCustoDao;

public class AjudaCustoService {

	@Inject
	AjudaCustoConverter converter;

	@Inject
	AjudaCustoDao dao;

	public List<AjudaCusto> getAll() {
//		return converter.toListDto(dao.getAll());
		return dao.getAll();
	}
	
    public AjudaCustoDto getByLocalidade( Long localidade ) {
    	return converter.toDto(dao.getByLocalidade( localidade ));
    	
    }
    
    public AjudaCustoDto atualizar(AjudaCustoDto dto) {
		
		try {	
	    dao.update(converter.toModel(dto));
		return dto;
		} catch (Exception e) {
			return null;
		}
	}

}
