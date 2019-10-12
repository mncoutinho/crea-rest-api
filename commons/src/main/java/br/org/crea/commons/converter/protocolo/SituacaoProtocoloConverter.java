package br.org.crea.commons.converter.protocolo;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.commons.SituacaoProtocolo;
import br.org.crea.commons.models.commons.dtos.SituacaoProtocoloDto;

public class SituacaoProtocoloConverter {

	public List<SituacaoProtocoloDto> toListDto(List<SituacaoProtocolo> listModel) {
		
		List<SituacaoProtocoloDto> listDto = new ArrayList<SituacaoProtocoloDto>();
		
		for(SituacaoProtocolo s : listModel){
			listDto.add(toDto(s));
		}
		
		return listDto;
		
	}

	public SituacaoProtocoloDto toDto(SituacaoProtocolo model) {
		
		SituacaoProtocoloDto dto = new SituacaoProtocoloDto();
		
		if (model != null ){
			dto.setId(model.getId());
			dto.setCodigo(model.getCodigo());
			dto.setDesabilitado(model.getDesabilitado());
			dto.setDescricao(model.getDescricao());
			dto.setSiacol(model.getSiacol());
			
			return dto;
		}else{
			return null;
		}

	}
	
	public SituacaoProtocolo toModel(SituacaoProtocoloDto dto){
		
		SituacaoProtocolo model = new SituacaoProtocolo();

		if(dto.getId() != null){
			model.setId(dto.getId());
		}
		model.setCodigo(dto.getCodigo());
		model.setDesabilitado(dto.getDesabilitado());
		model.setDescricao(dto.getDescricao());
		model.setSiacol(dto.getSiacol());
		
		return model;
		
	}

}
