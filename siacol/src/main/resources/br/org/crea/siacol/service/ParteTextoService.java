package br.org.crea.siacol.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.siacol.dtos.ParteTextoDto;
import br.org.crea.siacol.converter.ParteTextoConverter;
import br.org.crea.siacol.dao.ParteTextoDao;

public class ParteTextoService {
	
	@Inject ParteTextoConverter converter;
	
	@Inject ParteTextoDao dao;

	public List<ParteTextoDto> getAllTextos() {
		return converter.toListDto(dao.getAll());
	}

	public ParteTextoDto salva(ParteTextoDto dto) {
		return converter.toDto(dao.create(converter.toModel(dto)));
	}

	public ParteTextoDto atualiza(ParteTextoDto dto) {
		return converter.toDto(dao.update(converter.toModel(dto)));
	}
	
}
