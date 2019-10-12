package br.org.crea.commons.converter.cadastro.empresa;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.ObjetoSocial;
import br.org.crea.commons.models.cadastro.dtos.empresa.ObjetoSocialDto;
import br.org.crea.commons.util.DateUtils;

public class ObjetoSocialConverter {

	public List<ObjetoSocialDto> toListDto(List<ObjetoSocial> listaModel) {
		List<ObjetoSocialDto> listaDto = new ArrayList<ObjetoSocialDto>();
		for (ObjetoSocial objetoSocial : listaModel) {
			ObjetoSocialDto dto = this.toDto(objetoSocial);
			listaDto.add(dto);
		}
		return listaDto;
	}

	public ObjetoSocialDto toDto(ObjetoSocial model) {
		ObjetoSocialDto dto = new ObjetoSocialDto();

		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao());
		dto.setDataCadastro(model.getDataCadastro() != null ? DateUtils.format(model.getDataCadastro(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataAlteracao(model.getDataAlteracao() != null ? DateUtils.format(model.getDataAlteracao(), DateUtils.DD_MM_YYYY) : "-");

		return dto;
	}

}
