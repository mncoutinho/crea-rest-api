package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.siacol.SiacolRlProtocoloTags;
import br.org.crea.commons.models.siacol.SiacolTags;
import br.org.crea.commons.models.siacol.dtos.SiacolRlProtocoloTagsDto;

public class SiacolRlProtocoloTagsConverter {
	
	@Inject ProtocoloSiacolConverter protocoloSiacolConverter;
	@Inject SiacolTagsConverter tagsConverter;

	public List<SiacolRlProtocoloTagsDto> toListDto(List<SiacolRlProtocoloTags> listModel) {
		
		List<SiacolRlProtocoloTagsDto> listDto = new ArrayList<SiacolRlProtocoloTagsDto>();
		
		for(SiacolRlProtocoloTags s : listModel){
			listDto.add(toDto(s));
		}
		
		return listDto;
		
	}

	public SiacolRlProtocoloTagsDto toDto(SiacolRlProtocoloTags model) {
		
		if (model != null){
			SiacolRlProtocoloTagsDto dto = new SiacolRlProtocoloTagsDto();
			
			dto.setId(model.getId());
			dto.setNumeroProtocolo(model.getNumeroProtocolo());
			dto.setTag(tagsConverter.toDto(model.getTag()));
								
			return dto;			
		}else{
			return null;
		}

	}
	
	public SiacolRlProtocoloTags toModel(SiacolRlProtocoloTagsDto dto) {
		
		if (dto != null){
			SiacolRlProtocoloTags model = new SiacolRlProtocoloTags();

			SiacolTags tag = new SiacolTags();
			tag.setId(dto.getTag().getId());
			tag.setDescricao(dto.getTag().getDescricao());
					
			if (dto.getId() != null) {
				model.setId(dto.getId());
			}
			model.setNumeroProtocolo(dto.getNumeroProtocolo());
			model.setTag(tag);
								
			return model;			
		}else{
			return null;
		}

	}
	
	public List<SiacolRlProtocoloTagsDto> toListDtoSemProtocolo(List<SiacolRlProtocoloTags> listModel) {
		
		List<SiacolRlProtocoloTagsDto> listDto = new ArrayList<SiacolRlProtocoloTagsDto>();
		
		for(SiacolRlProtocoloTags s : listModel){
			listDto.add(toDtoSemProtocolo(s));
		}
		
		return listDto;
		
	}

	public SiacolRlProtocoloTagsDto toDtoSemProtocolo(SiacolRlProtocoloTags model) {
		
		if (model != null){
			SiacolRlProtocoloTagsDto dto = new SiacolRlProtocoloTagsDto();
			
			dto.setId(model.getId());
			dto.setNumeroProtocolo(model.getNumeroProtocolo());
			dto.setDescricao(model.getTag().getDescricao());
			dto.setTag(tagsConverter.toDto(model.getTag()));
								
			return dto;			
		}else{
			return null;
		}

	}
	
}
