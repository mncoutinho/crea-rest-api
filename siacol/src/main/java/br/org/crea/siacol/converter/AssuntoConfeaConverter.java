package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.AssuntoConfea;

public class AssuntoConfeaConverter {
		
	
	public List<AssuntoDto> toListDto(List<AssuntoConfea> listModel) {
		
		List<AssuntoDto> listDto = new ArrayList<AssuntoDto>();
		
		for(AssuntoConfea s : listModel){
			listDto.add(toDto(s));
		}
		
		return listDto;
		
	}

	public AssuntoDto toDto(AssuntoConfea model) {
		
		if (model != null){
			AssuntoDto dto = new AssuntoDto();
			
			dto.setId(model.getId());
			dto.setCodigo(model.getCodigo());
			dto.setNome(model.getNome());
			dto.setAtivo(model.getAtivo());	
								
			return dto;			
		}else{
			return null;
		}

	}
	
	public AssuntoConfea toModel(AssuntoDto dto){
		
		AssuntoConfea model = new AssuntoConfea();

		if(dto.getId() != null){
			model.setId(dto.getId());
		}
		model.setCodigo(dto.getCodigo());
		model.setNome(dto.getNome());
		model.setAtivo(dto.getAtivo());	
				
		return model;
		
	}
	

}
