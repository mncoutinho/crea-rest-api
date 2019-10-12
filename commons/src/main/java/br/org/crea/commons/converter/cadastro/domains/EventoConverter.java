package br.org.crea.commons.converter.cadastro.domains;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.Evento;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.dtos.EventoDto;

public class EventoConverter {

	public List<EventoDto> toListDto(List<Evento> listModel) {

		List<EventoDto> listDto = new ArrayList<EventoDto>();

		for (Evento s : listModel) {
			listDto.add(toDto(s));
		}

		return listDto;

	}

	public EventoDto toDto(Evento model) {

		EventoDto dto = new EventoDto();
		
		AssuntoDto assuntoDto = new AssuntoDto();
		
		if(model.getAssunto() != null){
			assuntoDto.setId(model.getAssunto().getId());
			assuntoDto.setDescricao(model.getAssunto().getDescricao());	
			assuntoDto.setViaPortal(model.getAssunto().getViaPortal());	
			dto.setAssunto(assuntoDto);
		}

		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao());
		dto.setProfissional(model.getProfissional());
		dto.setEmpresa(model.getEmpresa());
		dto.setLeigo(model.getLeigo());
		dto.setPermanente(model.getPermanente());
		dto.setFim(model.getFim());
		dto.setEtico(model.getEtico());
		

		return dto;

	}

	public Evento toModel(EventoDto dto) {

		Evento model = new Evento();

		if (dto.getId() != null) {
			model.setId(dto.getId());
		}
		model.setDescricao(dto.getDescricao());
		model.setProfissional(dto.getProfissional());
		model.setEmpresa(dto.getEmpresa());
		model.setLeigo(dto.getLeigo());
		model.setPermanente(dto.getPermanente());
		model.setFim(dto.getFim());
		model.setEtico(dto.getEtico());
		
		if (dto.getAssunto() != null) {
			Assunto assunto = new Assunto();
			assunto.setId(dto.getAssunto().getId());
			model.setAssunto(assunto);
		}
		

		return model;

	}

}
