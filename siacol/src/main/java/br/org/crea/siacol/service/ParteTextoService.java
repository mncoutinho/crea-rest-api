package br.org.crea.siacol.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.siacol.ParteTexto;
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
		if (dto.getAtivo() && dto.getOrdem() == null) {
			dto.setOrdem(getProximoNumeroOrdem());
		}
		return converter.toDto(dao.create(converter.toModel(dto)));
	}

	private Long getProximoNumeroOrdem() {
		return dao.getProximoNumeroOrdem();
	}

	public ParteTextoDto atualiza(ParteTextoDto dto) {
		return converter.toDto(dao.update(converter.toModel(dto)));
	}

	public ParteTextoDto deleta(Long id) {
		ParteTexto parteTexto = dao.getBy(id);
		parteTexto.setAtivo(false);
		return converter.toDto(dao.update(parteTexto));
	}	
	
}
