package br.org.crea.commons.converter.fiscalizacao;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.art.RamoArt;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.fiscalizacao.ContratoAtividade;

public class FiscalizacaoDomainConverter {

	public List<GenericDto> toListAtividadesDto(List<ContratoAtividade> lista) {
		List<GenericDto> atividades = new ArrayList<GenericDto>();
		
		for(ContratoAtividade c : lista){
			atividades.add(toAtividadeDto(c));
		}
		
		return atividades;
	}

	public GenericDto toAtividadeDto(ContratoAtividade c) {
		GenericDto dto = new GenericDto();
		dto.setCodigo(String.valueOf(c.getCodigo()));
		dto.setDescricao(c.getDescricao());
		
		return dto;
	}
	
	public List<GenericDto> toListRamosDto(List<RamoArt> lista){
		List<GenericDto> ramos = new ArrayList<GenericDto>();
		
		for(RamoArt ramo : lista){
			ramos.add(toRamoDto(ramo));
		}
		
		return ramos;
	}

	public GenericDto toRamoDto(RamoArt model) {
		GenericDto dto = new GenericDto();

		dto.setId(String.valueOf(model.getId()));
		dto.setDescricao(model.getDescricao());

		return dto;
	}	
}
