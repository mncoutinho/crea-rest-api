package br.org.crea.commons.converter.commons;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.commons.RlArquivo;
import br.org.crea.commons.models.commons.dtos.RlArquivoDto;

public class RlArquivoConverter {
	
	@Inject ArquivoConverter arquivoConverter;
		
	public List<RlArquivoDto> toListDto(List<RlArquivo> listModel) {
		
		List<RlArquivoDto> listDto = new ArrayList<RlArquivoDto>();
		
		listModel.forEach(model -> {
			listDto.add(toDto(model));
		});

		return listDto;
		
	}
	
	public RlArquivoDto toDto(RlArquivo model) {
		
		RlArquivoDto dto = new RlArquivoDto();
		
		if (model != null) {
			
			dto.setId(model.getId());
			dto.setIdTipoRlArquivo(model.getIdTipoRlArquivo());
			dto.setArquivo(arquivoConverter.toDto(model.getArquivo()));
			if (model.temTipoRlArquivo()) {
				dto.setTipoRlArquivo(model.getTipoRlArquivo());
			}
			if ( model.getPosicao() != null ) {
				 dto.setPosicao( model.getPosicao() );
			}
			
			if ( model.getDescricao() != null ) {
			     dto.setDescricao( model.getDescricao() );
			}
			

		}
		return dto;
	}

	public RlArquivo toModel(RlArquivoDto dto) {
		RlArquivo model = new RlArquivo();
		
		if (dto != null) {
			
			model.setId(dto.getId());
			model.setIdTipoRlArquivo(dto.getIdTipoRlArquivo());
			
			Arquivo arquivo = new Arquivo();
			
			Long idArquivo = dto.temArquivo() ? dto.getArquivo().getId() : dto.getIdArquivo();  
			arquivo.setId(idArquivo);
			
			model.setArquivo(arquivo);
			if (dto.temTipoRlArquivo()) {
				model.setTipoRlArquivo(dto.getTipoRlArquivo());
			}
			
			
			if ( dto.getPosicao() != null ) {
				 model.setPosicao( dto.getPosicao() );
			}
			
			if ( dto.getDescricao() != null ) {
			     model.setDescricao( dto.getDescricao() );
			}
			

		}
		return model;
	}
}
