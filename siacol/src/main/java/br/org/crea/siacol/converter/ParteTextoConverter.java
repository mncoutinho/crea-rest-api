package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.siacol.ParteTexto;
import br.org.crea.commons.models.siacol.dtos.ParteTextoDto;

public class ParteTextoConverter {

	public List<ParteTextoDto> toListDto(List<ParteTexto> listModel) {

		List<ParteTextoDto> listDto = new ArrayList<ParteTextoDto>();

		for (ParteTexto s : listModel) {
			listDto.add(toDto(s));
		}

		return listDto;

	}

	public ParteTextoDto toDto(ParteTexto model) {

		ParteTextoDto dto = new ParteTextoDto();

		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao());
		dto.setAtivo(model.getAtivo());
		dto.setOrdem(model.getOrdem());

		return dto;

	}

	public ParteTexto toModel(ParteTextoDto dto) {

		ParteTexto model = new ParteTexto();

		if (dto.getId() != null) {
			model.setId(dto.getId());
		}
		model.setDescricao(dto.getDescricao());
		model.setAtivo(dto.getAtivo());
		model.setOrdem(dto.getOrdem());

		return model;

	}

}
