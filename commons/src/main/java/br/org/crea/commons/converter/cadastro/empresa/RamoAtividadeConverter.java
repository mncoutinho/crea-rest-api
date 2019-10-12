package br.org.crea.commons.converter.cadastro.empresa;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.RamoAtividade;
import br.org.crea.commons.models.cadastro.dtos.empresa.RamoAtividadeDto;
import br.org.crea.commons.util.DateUtils;

public class RamoAtividadeConverter {

	public List<RamoAtividadeDto> toListDto(List<RamoAtividade> listaModel) {
		List<RamoAtividadeDto> listaDto = new ArrayList<RamoAtividadeDto>();
		for (RamoAtividade ramoAtividade : listaModel) {
			RamoAtividadeDto dto = this.toDto(ramoAtividade);
			listaDto.add(dto);
		}
		return listaDto;
	}

	public RamoAtividadeDto toDto(RamoAtividade model) {
		RamoAtividadeDto dto = new RamoAtividadeDto();
		
		dto.setCodigo(model.getId().toString());
		dto.setAtividade(model.getAtividade().getDescricao());
		dto.setRamo(model.getRamo().getDescricao());
		dto.setDataInclusao(DateUtils.format(model.getDataInclusao(), DateUtils.DD_MM_YYYY));
		dto.setDataAprovacao(DateUtils.format(model.getDataAprovacao(), DateUtils.DD_MM_YYYY));
		dto.setDataCancelamento(DateUtils.format(model.getDataCancelamento(), DateUtils.DD_MM_YYYY));
		dto.setPossuiResponsavelTecnico(model.getPossuiResponsavelTecnico() ? "SIM" : "NAO");

		return dto;
	}

}
