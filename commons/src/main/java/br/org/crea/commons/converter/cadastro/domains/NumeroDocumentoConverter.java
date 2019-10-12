package br.org.crea.commons.converter.cadastro.domains;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.cadastro.NumeroDocumento;
import br.org.crea.commons.models.cadastro.dtos.NumeroDocumentoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class NumeroDocumentoConverter {

	@Inject	private HttpClientGoApi httpGoApi;
	
	@Inject private TipoDocumentoConverter tipoDocumentoConverter;
	
	@Inject DepartamentoConverter departamentoConverter;
	
	public List<NumeroDocumentoDto> toListDto(List<NumeroDocumento> listModel) {
		
		List<NumeroDocumentoDto> listDto = new ArrayList<NumeroDocumentoDto>();
		
		for(NumeroDocumento NumeroDocumento : listModel){
			listDto.add(toDto(NumeroDocumento));
		}
		
		return listDto;
	}

	public NumeroDocumentoDto toDto(NumeroDocumento model) {

		NumeroDocumentoDto dto = new NumeroDocumentoDto();

		try {
			
			dto.setId(model.getId());	
			dto.setNumero(model.getNumero());
			dto.setAno(model.getAno());
			dto.setTem_ano(model.isTem_ano());
			dto.setDepartamento(departamentoConverter.toDto(model.getDepartamento()));
			dto.setTipo(tipoDocumentoConverter.toDto(model.getTipo()));

		} catch (Exception e) {
			httpGoApi.geraLog("NumeroDocumentoConverter || toDto", StringUtil.convertObjectToJson(model), e);
		}

		return dto;
	}


	public NumeroDocumento toModel(NumeroDocumentoDto dto) {

		NumeroDocumento model = new NumeroDocumento();

		try {

			if (dto.temId()) {
				model.setId(dto.getId());
			}
			model.setNumero(dto.getNumero());
			model.setAno(dto.getAno());
			model.setTem_ano(dto.isTem_ano());
			model.setDepartamento(departamentoConverter.toModel(dto.getDepartamento()));
			model.setTipo(tipoDocumentoConverter.toModel(dto.getTipo()));
			

		} catch (Exception e) {
			httpGoApi.geraLog("NumeroDocumentoConverter || toModel", StringUtil.convertObjectToJson(dto), e);
		}

		return model;
	}
	
}
