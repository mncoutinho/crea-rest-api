package br.org.crea.commons.converter.protocolo;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.protocolo.ObservacaoProtocolo;

public class ObservacaoProtocoloConverter {

	public List<GenericDto> toListDto(List<ObservacaoProtocolo> listModel) {
		
		List<GenericDto> listDto = new ArrayList<GenericDto>();
		
		for(ObservacaoProtocolo s : listModel){
			listDto.add(toDto(s));
		}
		
		return listDto;
		
	}

	public GenericDto toDto(ObservacaoProtocolo model) {
		
		GenericDto dto = new GenericDto();
		
		if (model != null ){
			dto.setId(model.getId().toString());
			dto.setDescricao(model.getDescricao());
			
			return dto;
		}else{
			return null;
		}

	}

}
