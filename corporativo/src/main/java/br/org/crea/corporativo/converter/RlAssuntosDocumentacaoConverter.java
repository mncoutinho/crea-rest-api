package br.org.crea.corporativo.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.corporativo.RlAssuntosDocumentacao;
import br.org.crea.commons.models.corporativo.dtos.RlAssuntosDocumentacaoDto;

public class RlAssuntosDocumentacaoConverter {

	public RlAssuntosDocumentacaoDto toDto(RlAssuntosDocumentacao model){
		
		RlAssuntosDocumentacaoDto dto = new RlAssuntosDocumentacaoDto();
		
		
		if(model != null){
			
			dto.setAssunto(model.getAssunto());
			dto.setDocumentacao(model.getDocumentacao());
			dto.setCopia(model.getCopia());
			dto.setOriginal(model.getOriginal());
			
		}
		
		return dto;
	}
	
	
	public List<RlAssuntosDocumentacaoDto> toListDto(List<RlAssuntosDocumentacao> listModel) {
		
		List<RlAssuntosDocumentacaoDto> listDto = new ArrayList<RlAssuntosDocumentacaoDto>();
		
		for(RlAssuntosDocumentacao a : listModel){
			listDto.add(toDto(a));
		}
		
		return listDto;
	}
	
}
