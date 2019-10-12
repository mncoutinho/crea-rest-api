package br.org.crea.commons.converter.cadastro;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.ConfeaTituloDao;
import br.org.crea.commons.models.cadastro.ConfeaTitulo;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;

public class ConfeaTituloConverter {

	@Inject ConfeaTituloDao dao;
	
	public DomainGenericDto toDto(ConfeaTitulo model) {
		DomainGenericDto dto = new DomainGenericDto();
		try {
			if(model == null) {
				return null;
			}
			
			dto.setId(model.getId());
			dto.setDescricao(model.getDescricao());
				
			
		}catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		return dto;
	}
	
	
	public ConfeaTitulo toModel(DomainGenericDto dto) {
		ConfeaTitulo titulo = new ConfeaTitulo();
		
		titulo.setId(dto.getId() != null ? dto.getId() : null);
		titulo.setDescricao(dto.getDescricao() != null ? dto.getDescricao() : "");
	
		return titulo;
	}
	
	public List<DomainGenericDto> toListTitulo(List<ConfeaTitulo> listModel){
		List<DomainGenericDto> listDto = new ArrayList<DomainGenericDto>();
		
		listModel.forEach(model -> {
			listDto.add(toDto(model));
		});
		return listDto;
 	}
}
