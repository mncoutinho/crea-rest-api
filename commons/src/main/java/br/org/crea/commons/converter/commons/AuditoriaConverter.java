package br.org.crea.commons.converter.commons;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;

import br.org.crea.commons.models.cadastro.Auditoria;
import br.org.crea.commons.models.commons.dtos.AuditoriaDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class AuditoriaConverter {
	
	
	@Inject	private HttpClientGoApi httpGoApi;

	public List<AuditoriaDto> toListDto(List<Auditoria> listAuditoria) {
		
		List<AuditoriaDto> listDto = new ArrayList<AuditoriaDto>();
		
		for(Auditoria a : listAuditoria){
			listDto.add(toDto(a));
		}
		
		return listDto;
		
	}

	private AuditoriaDto toDto(Auditoria model) {
		
		AuditoriaDto dto = new AuditoriaDto();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			dto.setId(model.getId());
			dto.setAcao(model.getAcao());
			dto.setDataCriacao(model.getDataCriacao());
			dto.setDataCriacaoFormatada(DateUtils.format(model.getDataCriacao(), DateUtils.DD_MM_YYYY_HH_MM));
			dto.setNumero(model.getNumero());
			if(model.temDtoNovo()){
				dto.setDtoNovo(mapper.readValue(model.getDtoNovo(), Object.class));
			}
			if(model.temDtoAntigo()){
				dto.setDtoAntigo(mapper.readValue(model.getDtoAntigo(), Object.class));
			}
			
			
		} catch (Exception e) {
			httpGoApi.geraLog("AuditoriaConverter || toDto", StringUtil.convertObjectToJson(model), e);
		}
	
		
		
		return dto;
		
	}

}
