package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.siacol.SiacolTags;
import br.org.crea.commons.models.siacol.dtos.SiacolTagsDto;

public class SiacolTagsConverter {

	public List<SiacolTagsDto> toListDto(List<SiacolTags> listModel) {
		
		List<SiacolTagsDto> listDto = new ArrayList<SiacolTagsDto>();
		
		for(SiacolTags s : listModel){
			listDto.add(toDto(s));
		}
		
		return listDto;
		
	}

	public SiacolTagsDto toDto(SiacolTags model) {
		
		if (model != null){
			SiacolTagsDto dto = new SiacolTagsDto();
			
			dto.setId(model.getId());
			dto.setDescricao(model.getDescricao());
								
			return dto;			
		}else{
			return null;
		}

	}
	
	public SiacolTags toModel(SiacolTagsDto dto) {
		
		if (dto != null){
			SiacolTags model = new SiacolTags();
			
			if (dto.getId() != null && dto.getId() != 0) {
				model.setId(dto.getId());
			}
			model.setDescricao(dto.getDescricao());
											
			return model;			
		}else{
			return null;
		}

	}
	
}
