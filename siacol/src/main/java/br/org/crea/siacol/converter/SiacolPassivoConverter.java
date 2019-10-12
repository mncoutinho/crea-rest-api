package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.siacol.SiacolPassivo;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol06Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol06PesoDto;

public class SiacolPassivoConverter {
	
	public List<RelDetalhadoSiacol06Dto> toListDto(List<SiacolPassivo> listModel) {
		
		List<RelDetalhadoSiacol06Dto> listDto = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		for(SiacolPassivo s : listModel){
			listDto.add(toRelDetalhadoSiacol06Dto(s));
		}
		
		return listDto;
		
	}

	public RelDetalhadoSiacol06Dto toRelDetalhadoSiacol06Dto(SiacolPassivo model) {
		
		if (model != null){
			RelDetalhadoSiacol06Dto dto = new RelDetalhadoSiacol06Dto();
			
			dto.setNumeroProtocolo(String.valueOf(model.getProtocoloSiacol()));
			
			return dto;			
		}else{
			return null;
		}

	}
}
