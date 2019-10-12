package br.org.crea.commons.converter.cadastro.domains;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.Modalidade;
import br.org.crea.commons.models.cadastro.dtos.ModalidadeDto;

public class ModalidadeConverter {
	
	public List<ModalidadeDto> toListDto(List<Modalidade> listModel) {

		List<ModalidadeDto> listDto = new ArrayList<ModalidadeDto>();

		for (Modalidade s : listModel) {
			listDto.add(toDto(s));
		}

		return listDto;

	}
	
	public ModalidadeDto toDto(Modalidade model) {

		ModalidadeDto dto = new ModalidadeDto();

		dto.setCodigo(model.getCodigo());
		dto.setDescricao(model.getDescricao());

		return dto;

	}
}
