package br.org.crea.corporativo.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.Taxa;
import br.org.crea.commons.models.cadastro.dtos.TaxaDto;

public class TaxaConverter {

	public TaxaDto toDto(Taxa model){
		
		TaxaDto dto = new TaxaDto();
		
		
		if(model != null){
			
			dto.setId(model.getId());
			dto.setDescricao(model.getNatureza().getDescricao());
			dto.setValor(model.getValor());
			dto.setProfissionais(model.getNatureza().getProfissionais());
			dto.setEmpresas(model.getNatureza().getEmpresas());
			dto.setLeigos(model.getNatureza().getLeigos());
			dto.setCancelados(model.getNatureza().getCancelados());
			dto.setAtivo(model.getAtivo());
			
		}
		
		return dto;
	}
	
	
	public List<TaxaDto> toListDto(List<Taxa> listModel) {
		
		List<TaxaDto> listDto = new ArrayList<TaxaDto>();
		
		for(Taxa a : listModel){
			listDto.add(toDto(a));
		}
		
		return listDto;
	}
	
}
