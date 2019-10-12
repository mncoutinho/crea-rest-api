package br.org.crea.commons.converter.protocolo;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.corporativo.BlocosAssuntos;
import br.org.crea.commons.models.corporativo.dtos.BlocosAssuntosDto;

public class BlocosAssuntosConverter {

	public BlocosAssuntosDto toDto(BlocosAssuntos model){
		
		BlocosAssuntosDto dto = new BlocosAssuntosDto();
		
		
		if(model != null){
			
			dto.setId(model.getId());
			dto.setDescricao(model.getDescricao());
			
		}
		
		return dto;
	}
	
	
	public List<BlocosAssuntosDto> toListDto(List<BlocosAssuntos> listModel) {
		
		List<BlocosAssuntosDto> listDto = new ArrayList<BlocosAssuntosDto>();
		
		for(BlocosAssuntos a : listModel){
			listDto.add(toDto(a));
		}
		
		return listDto;
	}
	
}
