package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.models.siacol.RlProtocoloResponsavelSiacol;
import br.org.crea.commons.models.siacol.dtos.VinculoProtocoloDto;

public class ProtocoloResponsavelSiacolConverter {
	
	@Inject ProtocoloSiacolConverter protocoloSiacolConverter;
	@Inject ProtocoloConverter protocoloCorporativoConverter;
	
	public VinculoProtocoloDto toDto(RlProtocoloResponsavelSiacol model) {
		
		if (model != null) {
			VinculoProtocoloDto dto = new VinculoProtocoloDto();
			
			dto.setProtocoloPai(protocoloSiacolConverter.toDto(model.getProtocoloPai()));
			dto.setProtocoloFilho(protocoloCorporativoConverter.toDto(model.getProtocoloFilho()));
			dto.setIdResponsavelVinculo(model.getResponsavel().getId());
			dto.setProtocoloFilhoFoiImportado(model.foiImportadoCorporativo());
			return dto;
		}
		return null;
	}
	
	public List<VinculoProtocoloDto> toListDto(List<RlProtocoloResponsavelSiacol> listModel) {
		List<VinculoProtocoloDto> listVinculoProtocoloDto = new ArrayList<VinculoProtocoloDto>(); 
		
		for (RlProtocoloResponsavelSiacol rl : listModel) {
			listVinculoProtocoloDto.add(toDto(rl));
		}
		
		return listVinculoProtocoloDto;
	}
	
}
