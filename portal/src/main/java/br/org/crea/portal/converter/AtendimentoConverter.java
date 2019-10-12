package br.org.crea.portal.converter;

import br.org.crea.commons.models.portal.Atendimento;
import br.org.crea.commons.models.portal.dto.AtendimentoDto;

public class AtendimentoConverter {

	public AtendimentoDto toDto(Atendimento model) {

		AtendimentoDto dto = new AtendimentoDto();
		
		dto.setNumeroChamado(model.getCodigo());
	
		return dto;
	}

}
