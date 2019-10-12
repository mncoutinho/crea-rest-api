package br.org.crea.corporativo.converter.personalidade;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.corporativo.personalidade.dto.CargoPersonalidadeDto;
import br.org.crea.commons.models.corporativo.personalidade.entity.CargoPersonalidade;

public class CargoPersonalidadeConverter {
	
	public CargoPersonalidadeDto toDto(CargoPersonalidade model){
		
	CargoPersonalidadeDto dto = new CargoPersonalidadeDto();
		
		
		if(model != null){
			
			dto.setId(model.getId());
			dto.setDescricao(model.getDescricao());
			dto.setPermiteConsecutividade(model.getPermiteConsecutividade());
			dto.setVisivelConselheiros(model.isVisivelConselheiros());
			dto.setVisivelContatos(model.isVisivelContatos());
			dto.setVisivelDirecoes(model.isVisivelDirecoes());
			dto.setVisivelInspetores(model.isVisivelInspetores());
			dto.setRemovido(model.isRemovido());
			
			if ( model.getCargoRaiz() != null ) {
			     dto.setCargoRaiz( model.getCargoRaiz() );
			     dto.setIdCargoPersonalidade(model.getCargoRaiz().getId() );
			}
						
		}
		
		return dto;
	}
	
	
	public List<CargoPersonalidadeDto> toListDto(List<CargoPersonalidade> listModel) {
		
		List<CargoPersonalidadeDto> listDto = new ArrayList<CargoPersonalidadeDto>();
		
		for(CargoPersonalidade a : listModel){
			listDto.add(toDto(a));
		}
		
		return listDto;
	}
	
	public CargoPersonalidade toModel(CargoPersonalidadeDto dto){
		
		CargoPersonalidade model = new CargoPersonalidade();

		if(dto.getId() != null){
			model.setId(dto.getId());
		}
		model.setId(dto.getId());
		model.setDescricao(dto.getDescricao());
		model.setPermiteConsecutividade(dto.getPermiteConsecutividade());
		model.setRemovido(dto.isRemovido());
		model.setVisivelConselheiros(dto.isVisivelConselheiros());
		model.setVisivelContatos(dto.isVisivelContatos());
		model.setVisivelDirecoes(dto.isVisivelDirecoes());
		model.setVisivelInspetores(dto.isVisivelInspetores());
		
		if ( dto.getCargoRaiz() != null ) {
			 model.setCargoRaiz(dto.getCargoRaiz());
		}
		
		return model;
		
	}
	
	public String to_String(Integer inteiro) {
		
		return inteiro.toString();
	
	}

}
