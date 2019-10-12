package br.org.crea.commons.converter.cadastro.pessoa;

import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.dtos.SituacaoDto;

public class SituacaoPessoaConverter {
	
	
	public SituacaoDto toDto(SituacaoRegistro situacao){
		SituacaoDto dto = new SituacaoDto();
		
		dto.setId(situacao.getId());
		dto.setDescricao(situacao.getDescricao());
		dto.setDescricaoPublica(situacao.getDescricaoPublica());
		return dto;
	}

}
