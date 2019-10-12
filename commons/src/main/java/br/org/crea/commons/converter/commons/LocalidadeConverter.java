package br.org.crea.commons.converter.commons;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.models.commons.Localidade;
import br.org.crea.commons.models.commons.UF;
import br.org.crea.commons.models.commons.dtos.LocalidadeDto;

public class LocalidadeConverter {
	
	@Inject EnderecoConverter enderecoConverter;
	
	public LocalidadeDto toDto( Localidade model ) {
		
		LocalidadeDto dto = new LocalidadeDto();
		
		if ( model != null ) {
			
			dto.setCep( model.getCep() );
			dto.setDescricao( model.getDescricao() );
			dto.setId( model.getId() );
			dto.setUf( enderecoConverter.toUfDto( model.getUf() ));
			dto.setNome( model.getDescricao() );
			
		}
		
		return dto;
	}
	
	
	public Localidade toModel ( LocalidadeDto dto ) {
		
		Localidade model = new Localidade();
		
		model.setId( dto.getId() );
        model.setDescricao( dto.getDescricao() );
        model.setCep( dto.getCep() );
        
        if ( dto.getUf() != null ) {
           	 UF uf = new UF();
    		 uf.setId(dto.getUf().getId());
    		 uf.setSigla(dto.getUf().getSigla());	
    		}
		    
		return model;
		
	}
	
	public List<LocalidadeDto> toListDto(List<Localidade> listModel) {
		
		List<LocalidadeDto> listDto = new ArrayList<LocalidadeDto>();
		
		listModel.forEach(model -> {
			listDto.add(toDto(model));
		});
		
		return listDto;
		
	}
	
}
