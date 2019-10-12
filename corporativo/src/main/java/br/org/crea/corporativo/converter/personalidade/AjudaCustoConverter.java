package br.org.crea.corporativo.converter.personalidade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.commons.LocalidadeConverter;
import br.org.crea.commons.models.corporativo.personalidade.dto.AjudaCustoDto;
import br.org.crea.commons.models.corporativo.personalidade.entity.AjudaCusto;

public class AjudaCustoConverter {
	
	@Inject LocalidadeConverter localidadeConverter;
	
	public AjudaCustoDto toDto (AjudaCusto model) {
		
		AjudaCustoDto dto = new AjudaCustoDto();
		
		if ( model != null ) {

			dto.setDescricao(model.getLocalidade().getDescricao());
			dto.setDistancia(model.getDistancia());
			dto.setId(model.getId());
	        dto.setLocalidade( model.getLocalidade() );
    		dto.setRemovido(model.isRemovido());
			dto.setValor(model.getValor());
			dto.setDiaria(model.getDiaria());
			dto.setJeton(model.getJeton());
			
		}	
		
		return dto;
		
	}
	
	public AjudaCusto toModel ( AjudaCustoDto dto) {
		
		AjudaCusto model = new AjudaCusto();
		
		if ( dto != null ) {
			
			if ( dto.getId() != null ) {
			     model.setId(dto.getId());
			}
			
			
			model.setDescricao( dto.getLocalidade().getDescricao() );
			model.setDistancia(dto.getDistancia());
			model.setLocalidade( dto.getLocalidade() );
			model.setRemovido(dto.isRemovido());
			model.setValor(dto.getValor());
			model.setDiaria(dto.getDiaria());
			model.setJeton(dto.getJeton());
			
		}
		
		return model;
		
	}
	
	public List<AjudaCustoDto> toListDto(List<AjudaCusto> listModel) {
		
		List<AjudaCustoDto> listDto = new ArrayList<AjudaCustoDto>();
		
		for(AjudaCusto a : listModel){
			listDto.add(toDto(a));
		}
		
		return listDto;
		
	}

}
