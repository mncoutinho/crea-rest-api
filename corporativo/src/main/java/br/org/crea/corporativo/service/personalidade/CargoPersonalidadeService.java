package br.org.crea.corporativo.service.personalidade;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.corporativo.personalidade.dto.CargoPersonalidadeDto;
import br.org.crea.corporativo.converter.personalidade.CargoPersonalidadeConverter;
import br.org.crea.corporativo.dao.personalidade.CargoPersonalidadeDao;

public class CargoPersonalidadeService {
	
    @Inject
	CargoPersonalidadeConverter converter;
    
    @Inject
    CargoPersonalidadeDao dao;
    
	public List<CargoPersonalidadeDto> getAll() {
		return converter.toListDto(dao.getAll());
	}
	
    public CargoPersonalidadeDto atualizar(CargoPersonalidadeDto dto) {
		
		try {	
	    dao.update(converter.toModel(dto));
		return dto;
		} catch (Exception e) {
			return null;
		}
	}
    
    public CargoPersonalidadeDto getByCodigo(Long codigo) {
    	return converter.toDto(dao.getBy(codigo));
    }
    
    public CargoPersonalidadeDto salvar(CargoPersonalidadeDto dto) {		
		return converter.toDto(dao.create(converter.toModel(dto)));
	}
    
	public void deletar(Long id) {
		dao.deleta(id);
	}
	
	public String countByAjudaCusto(Integer ajudaCusto) {
		return converter.to_String(dao.getRecordCount("", " c ", " c.ajudaCusto.id =  " + ajudaCusto));
		
	}

}
