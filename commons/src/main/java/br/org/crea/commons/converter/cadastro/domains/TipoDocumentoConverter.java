package br.org.crea.commons.converter.cadastro.domains;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.TipoDocumento;
import br.org.crea.commons.models.commons.dtos.TipoDocumentoDto;

public class TipoDocumentoConverter {

	public List<TipoDocumentoDto> toListDto(List<TipoDocumento> listModel) {

		List<TipoDocumentoDto> listDto = new ArrayList<TipoDocumentoDto>();

		for (TipoDocumento s : listModel) {
			listDto.add(toDto(s));
		}

		return listDto;

	}

	public TipoDocumentoDto toDto(TipoDocumento model) {

		TipoDocumentoDto dto = new TipoDocumentoDto();
		dto.setModulo(model.getModulo());
		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao());
		dto.setTemplate(model.getTemplate());
		return dto;

	}

	public TipoDocumento toModel(TipoDocumentoDto dto) {

		TipoDocumento model = new TipoDocumento();

		if (dto.temID()) {
			model.setId(dto.getId());
		}
		model.setModulo(dto.getModulo());
		model.setDescricao(dto.getDescricao());
		model.setTemplate(dto.getTemplate());
		return model;

	}

}
