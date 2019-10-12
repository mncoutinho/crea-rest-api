package br.org.crea.commons.converter.commons;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.commons.dtos.ArquivoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.util.DateUtils;

public class ArquivoConverter {
	
	
	
	public List<ArquivoDto> toListDto(List<Arquivo> listModel) {
		
		List<ArquivoDto> listDto = new ArrayList<ArquivoDto>();
		
		listModel.forEach(model -> {
			listDto.add(toDto(model));
		});

		return listDto;
		
	}
	
	public ArquivoDto toDto(Arquivo model) {
		
		ArquivoDto dto = new ArquivoDto();
		
		if (model != null) {
			
			dto.setId(model.getId());
			dto.setCaminhoOriginal(model.getCaminhoOriginal());
			dto.setCaminhoStorage(model.getCaminhoStorage());
			dto.setNomeOriginal(model.getNomeOriginal());
			dto.setNomeStorage(model.getNomeStorage());
			dto.setDescricao(model.getDescricao());
			dto.setExtensao(model.getExtensao());
			dto.setTamanho(model.getTamanho());
			dto.setDataFormatada(DateUtils.format(model.getDataInclusao(), DateUtils.DD_MM_YYYY_HH_MM));
			
			if (model.temPessoa()) {
				DomainGenericDto pessoaDto = new DomainGenericDto();
				pessoaDto.setId(model.getPessoa().getId());
				pessoaDto.setNome(model.getPessoa().getNome());
				dto.setPessoa(pessoaDto);
			}
			
			if (model.temModulo()) {
				dto.setModulo(model.getModulo());
			}

			dto.setStatus(model.getStatus());
			dto.setPrivado(model.isPrivado());
			dto.setUri(model.getUri());
		}
		return dto;
	}

	public Arquivo toModel(ArquivoDto dto) {
		Arquivo model = new Arquivo();
		
		if (dto != null) {
					
			model.setId(dto.getId());
			model.setCaminhoOriginal(dto.getCaminhoOriginal());
			model.setCaminhoStorage(dto.getCaminhoStorage());
			model.setNomeOriginal(dto.getNomeOriginal());
			model.setNomeStorage(dto.getNomeStorage());
			model.setDescricao(dto.getDescricao());
			model.setExtensao(dto.getExtensao());
			model.setTamanho(dto.getTamanho());
			model.setDataInclusao(DateUtils.convertStringToDate(dto.getDataFormatada(), DateUtils.DD_MM_YYYY_HH_MM));
			
			if (dto.temPessoa()) {
				Pessoa pessoa = new Pessoa();
				pessoa.setId(dto.getPessoa().getId());
				pessoa.setNome(dto.getPessoa().getNome());
				model.setPessoa(pessoa);
			}
			
			if (dto.temModulo()) {
				model.setModulo(dto.getModulo());
			}

			model.setStatus(dto.getStatus());
			model.setPrivado(dto.isPrivado());
			model.setUri(dto.getUri());
		}
		return model;
	}

}
