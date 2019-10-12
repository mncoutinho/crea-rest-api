package br.org.crea.commons.converter.protocolo;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.RlAssuntosSiacol;
import br.org.crea.commons.models.siacol.dtos.RlAssuntosDto;

public class RlAssuntosConverter {

	public List<RlAssuntosDto> toListDto(List<RlAssuntosSiacol> listModel) {
		
		List<RlAssuntosDto> listDto = new ArrayList<RlAssuntosDto>();
		
		for(RlAssuntosSiacol s : listModel){
			listDto.add(toDto(s));
		}
		
		return listDto;
		
	}
	
	public RlAssuntosDto toDto(RlAssuntosSiacol model) {
		
		RlAssuntosDto dto = new RlAssuntosDto();
		

		AssuntoDto assunto = new AssuntoDto();
		assunto.setId(model.getAssunto().getId());
		assunto.setDescricao(model.getAssunto().getDescricao());
		assunto.setAtivo(model.getAssunto().getSiacol());
		
		AssuntoDto assuntoSiacol = new AssuntoDto();
		assuntoSiacol.setId(model.getAssuntoSiacol().getId());
		assuntoSiacol.setCodigo(model.getAssuntoSiacol().getCodigo());
		assuntoSiacol.setNome(model.getAssuntoSiacol().getNome());
		assuntoSiacol.setAtivo(model.getAssuntoSiacol().getAtivo());
		
		if(model.getAssuntoSiacol().getAssuntoConfea() != null ){
			AssuntoDto assuntoConfea = new AssuntoDto();
			assuntoConfea.setId(model.getAssuntoSiacol().getAssuntoConfea().getId());
			assuntoConfea.setCodigo(model.getAssuntoSiacol().getAssuntoConfea().getCodigo());
			assuntoConfea.setNome(model.getAssuntoSiacol().getAssuntoConfea().getNome());
			assuntoConfea.setAtivo(model.getAssuntoSiacol().getAssuntoConfea().getAtivo());			
			assuntoSiacol.setAssuntoConfea(assuntoConfea);
		}
		
		dto.setAssunto(assunto);
		dto.setAssuntoSiacol(assuntoSiacol);	
		
		
		return dto;		

	}
	
	public RlAssuntosSiacol toModel(RlAssuntosSiacol dto){
		
		RlAssuntosSiacol model = new RlAssuntosSiacol();
		
		model.setAssunto(dto.getAssunto());
		model.setAssuntoSiacol(dto.getAssuntoSiacol());	

		return model;
		
	}

}
