package br.org.crea.commons.converter.cadastro.pessoa;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.Historico;
import br.org.crea.commons.models.cadastro.dtos.HistoricoDto;
import br.org.crea.commons.util.DateUtils;


public class HistoricoConverter {
	
	public List<HistoricoDto> toListHistoricoDto(List<Historico> listaModel) {

		List<HistoricoDto> listDto = new ArrayList<HistoricoDto>();
		
		for (Historico model : listaModel) {
			HistoricoDto dto = toDto(model);
			listDto.add(dto);
		}
		
		return listDto;
	}
	
	public HistoricoDto toDto(Historico model) {
		HistoricoDto dto = new HistoricoDto();
		dto.setDataInicial(DateUtils.format(model.getDataInicio(), DateUtils.DD_MM_YYYY));
		dto.setDataFinal(DateUtils.format(model.getDataFinal(), DateUtils.DD_MM_YYYY));
		dto.setEvento(model.getEvento().getDescricao());
		dto.setObservacao(model.getObservacoes());
		return dto;
	}

}
