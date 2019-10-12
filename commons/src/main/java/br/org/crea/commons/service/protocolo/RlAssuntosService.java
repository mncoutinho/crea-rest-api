package br.org.crea.commons.service.protocolo;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.RlAssuntosConverter;
import br.org.crea.commons.dao.protocolo.RlAssuntosDao;
import br.org.crea.commons.models.siacol.RlAssuntosSiacol;
import br.org.crea.commons.models.siacol.dtos.RlAssuntosDto;


public class RlAssuntosService {
	
	@Inject RlAssuntosConverter converter;
	
	@Inject RlAssuntosDao dao;
	

	public List<RlAssuntosDto> getAll() {
		return converter.toListDto(dao.getAll());
	}

	public RlAssuntosDto salvar(RlAssuntosSiacol dto) {
		return converter.toDto(dao.create(converter.toModel(dto)));
	}

	public List<RlAssuntosDto> getByAssuntoSiacol(Long codigo) {
		return converter.toListDto(dao.getByAssuntoSiacol(codigo));
	}

	public Boolean deletaByAssuntoSiacol(Long codigo) {			
		return dao.deleteByAssuntoSiacol(codigo);
	}
	
}
