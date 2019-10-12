package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
import br.org.crea.commons.models.siacol.RlAssuntosSiacol;

public class AssuntoSiacolConverter {
		
	@Inject
	AssuntoConfeaConverter confeaConvertermodel;
	
	public List<AssuntoDto> toListDto(List<AssuntoSiacol> listModel) {
		
		List<AssuntoDto> listDto = new ArrayList<AssuntoDto>();
		
		for(AssuntoSiacol s : listModel){
			listDto.add(toDto(s));
		}
		
		return listDto;
		
	}

	public AssuntoDto toDto(AssuntoSiacol model) {
		
		if (model != null){
			AssuntoDto dto = new AssuntoDto();
			
			dto.setId(model.getId());
			dto.setCodigo(model.getCodigo());
			dto.setNome(model.getNome());
			dto.setAtivo(model.getAtivo());
			dto.setAssuntoConfea(confeaConvertermodel.toDto(model.getAssuntoConfea()));
			dto.setLegislacao(model.getLegislacao());
			dto.setTempoServico(model.getTempoServico());
			dto.setPesoInstrucao(model.getPesoInstrucao());
			dto.setPesoCamara(model.getPesoCamara());
			dto.setPesoComissao(model.getPesoComissao());
			dto.setPesoPlenaria(model.getPesoPlenaria());
			
			return dto;			
		}else{
			return null;
		}

	}
	
	public AssuntoSiacol toModel(AssuntoDto dto){
		
		AssuntoSiacol model = new AssuntoSiacol();

		if(dto.getId() != null){
			model.setId(dto.getId());
		}
		model.setCodigo(dto.getCodigo());
		model.setNome(dto.getNome());
		model.setLegislacao(dto.getLegislacao());
		model.setTempoServico(dto.getTempoServico());
		model.setAtivo(dto.getAtivo());
		model.setPesoInstrucao(dto.getPesoInstrucao());
		model.setPesoCamara(dto.getPesoCamara());
		model.setPesoComissao(dto.getPesoComissao());
		model.setPesoPlenaria(dto.getPesoPlenaria());
		if(dto.getAssuntoConfea() != null){
			model.setAssuntoConfea(confeaConvertermodel.toModel(dto.getAssuntoConfea()));
		}

		
		return model;
		
	}

	public List<RlAssuntosSiacol> toListModelRlAssunto(AssuntoDto dto){
		
		List<RlAssuntosSiacol> listaRlAssuntos = new ArrayList<RlAssuntosSiacol>();		
		
		
		for(AssuntoDto rl : dto.getListaAssuntoProtocolo()){
			RlAssuntosSiacol rlAssuntos = new RlAssuntosSiacol();
								
			AssuntoSiacol assuntoSiacol = new AssuntoSiacol();
			assuntoSiacol.setId(dto.getId());
			
			Assunto assunto = new Assunto();
			assunto.setId(rl.getId());
			
			rlAssuntos.setAssunto(assunto);
			rlAssuntos.setAssuntoSiacol(assuntoSiacol);
			
			listaRlAssuntos.add(rlAssuntos);
			
		}
		
		return listaRlAssuntos;
		
	}
}
