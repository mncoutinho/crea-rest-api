package br.org.crea.corporativo.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.corporativo.Documentacao;
import br.org.crea.commons.models.corporativo.dtos.DocumentacaoDto;

public class DocumentacaoConverter {

	public DocumentacaoDto toDto(Documentacao model){
		
		DocumentacaoDto dto = new DocumentacaoDto();
		
		
		if(model != null){
			
			dto.setId(model.getId());
			dto.setNome(model.getNome());
			dto.setDescricao(model.getDescricao());
			dto.setStatus(model.getStatus());
			dto.setLink(model.getLink());
//			dto.setDataCriacao(model.getDataCriacao());
//			dto.setDataAlteracao(model.getDataAlteracao());
//			dto.setFuncionario(model.getFuncionario());
			
		}
		
		return dto;
	}
	
	
	public List<DocumentacaoDto> toListDto(List<Documentacao> listModel) {
		
		List<DocumentacaoDto> listDto = new ArrayList<DocumentacaoDto>();
		
		for(Documentacao a : listModel){
			listDto.add(toDto(a));
		}
		
		return listDto;
	}
	
}
