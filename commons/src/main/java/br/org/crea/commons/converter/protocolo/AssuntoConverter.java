package br.org.crea.commons.converter.protocolo;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.AssuntoSiacol;

public class AssuntoConverter {

	public AssuntoDto toDto(Assunto model){
		
		AssuntoDto dto = new AssuntoDto();
		
		if(model != null){
			
			dto.setId(model.getId());
			dto.setDescricao(model.getDescricao());
			dto.setViaPortal(model.getViaPortal());
			dto.setSiacol(model.getSiacol());
			return dto;
			
		} else {
			return null;
		}
	}
	
	public List<AssuntoDto> toListDto(List<Assunto> listModel) {
		
		List<AssuntoDto> listDto = new ArrayList<AssuntoDto>();
		
		for(Assunto a : listModel){
			listDto.add(toDto(a));
		}
		
		return listDto;
	}
	
	public List<AssuntoDto> toListDtoSiacol(List<AssuntoSiacol> listModel) {
		
		List<AssuntoDto> listDto = new ArrayList<AssuntoDto>();
		
		for(AssuntoSiacol s : listModel){
			listDto.add(toDtoSiacol(s));
		}
		
		return listDto;
		
	}

	public AssuntoDto toDtoSiacol(AssuntoSiacol model) {
		
		if (model != null){
			AssuntoDto dto = new AssuntoDto();
			
			dto.setId(model.getId());
			dto.setCodigo(model.getCodigo());
			dto.setNome(model.getNome());
			dto.setAtivo(model.getAtivo());	
								
			return dto;			
		} else{
			return null;
		}
	}	
	
}
