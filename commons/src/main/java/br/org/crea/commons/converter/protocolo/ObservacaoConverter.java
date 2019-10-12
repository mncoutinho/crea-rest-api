package br.org.crea.commons.converter.protocolo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.ObservacoesMovimento;
import br.org.crea.commons.models.commons.dtos.ObservacaoDto;
import br.org.crea.commons.util.DateUtils;

public class ObservacaoConverter {
	
	@Inject DepartamentoDao departamentoDao;
	@Inject DepartamentoConverter departamentoConverter;
	
	
	public List<ObservacaoDto> toListDto(List<ObservacoesMovimento> listModel){
		
		List<ObservacaoDto> listDto = new ArrayList<ObservacaoDto>();
		
		for(ObservacoesMovimento o : listModel){
			listDto.add(toDto(o));
		}
		
		return listDto;
	}

	private ObservacaoDto toDto(ObservacoesMovimento o) {
		ObservacaoDto dto = new ObservacaoDto();
		List<DepartamentoDto> departamentoDto = departamentoConverter.toListDto(departamentoDao.getDepartamentoBy(o.getIdDepartamento()));
		
		if(!departamentoDto.isEmpty()) {
			dto.setDepartamento(departamentoDto.get(0));
		}
		dto.setId(o.getId());
		dto.setData(o.getData());
		dto.setDataFormatada(o.getData() != null ? DateUtils.format(o.getData(), DateUtils.DD_MM_YYYY_HH_MM) : "-");
		if(o.getAnexo() != null){
			dto.setAnexo("S");
		}
		if(o.getObservacao() != null){
			dto.setObservacao(o.getObservacao().getDescricao());
		}
		
		return dto;
	}

}
