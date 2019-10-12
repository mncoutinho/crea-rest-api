package br.org.crea.commons.converter.atendimento;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.UnidadeAtendimento;
import br.org.crea.commons.models.cadastro.dtos.UnidadeAtendimentoDto;

public class UnidadeAtendimentoConverter {

	public List<UnidadeAtendimentoDto> toListDto(List<Departamento> listModel){

		List<UnidadeAtendimentoDto> listDto = new ArrayList<UnidadeAtendimentoDto>();

		for(Departamento u : listModel){
			listDto.add(toDto(u));
		}

		return listDto;

	}
	
	public UnidadeAtendimentoDto toDto(Departamento model) {

		UnidadeAtendimentoDto dto = new UnidadeAtendimentoDto();
		
		dto.setNome(model.getNomeExibicao());
		dto.setId(model.getId());
		dto.setRegional(model.getDepartamentoPai().getId());
		dto.setDescricaoRegional(model.getDepartamentoPai().getNome());
		dto.setEmail(model.getEmailCoordenacao());
		
		return dto;

	}
	
	public List<UnidadeAtendimentoDto> toListUnidadeAtendimentoDto(List<UnidadeAtendimento> listModel){

		List<UnidadeAtendimentoDto> listDto = new ArrayList<UnidadeAtendimentoDto>();

		for(UnidadeAtendimento u : listModel){
			listDto.add(toUnidadeAtendimentoDto(u));
		}

		return listDto;

	}
	
	public UnidadeAtendimentoDto toUnidadeAtendimentoDto(UnidadeAtendimento model) {

		UnidadeAtendimentoDto dto = new UnidadeAtendimentoDto();
		
		dto.setId(model.getId());
		dto.setNome(model.getNome());		
		return dto;

	}
}
