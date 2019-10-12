package br.org.crea.corporativo.converter;

import br.org.crea.commons.models.corporativo.RazaoSocial;
import br.org.crea.commons.models.corporativo.dtos.RazaoSocialDto;

public class RazaoSocialConverter {

	public RazaoSocialDto toDto(RazaoSocial model){
		
		RazaoSocialDto dto = new RazaoSocialDto();
		
		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao());
		
		return dto;
		
	}
	
}
