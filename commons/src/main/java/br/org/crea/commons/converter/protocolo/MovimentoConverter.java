package br.org.crea.commons.converter.protocolo;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.corporativo.dtos.MovimentoDto;
import br.org.crea.commons.util.DateUtils;

public class MovimentoConverter {
	
	public MovimentoDto toDto(Movimento model) {

		//FIXME Estagiarios, eh necessario procurar quem esta fazendo uso deste método para fazer os
		//devidos acertos com posterior teste...
		
		MovimentoDto dto = new MovimentoDto();

		dto.setId(model.getId());
		
		Departamento departamentoOrigemDto = new Departamento();
		departamentoOrigemDto.setId(model.getDepartamentoOrigem().getId());
		departamentoOrigemDto.setNome(model.getDepartamentoOrigem().getNome());
		departamentoOrigemDto.setSigla(model.getDepartamentoOrigem().getSigla());
		
		dto.setDepartamentoOrigem(departamentoOrigemDto);
		
		Departamento departamentoDestinoDto = new Departamento();
		departamentoDestinoDto.setId(model.getDepartamentoDestino().getId());
		departamentoDestinoDto.setNome(model.getDepartamentoDestino().getNome());
		departamentoDestinoDto.setSigla(model.getDepartamentoDestino().getSigla());
		
		dto.setDepartamentoDestino(departamentoDestinoDto);
		
		dto.setDataEnvio(model.getDataEnvio());
		dto.setDataEnvioFormatada(model.getDataEnvio() != null ? DateUtils.format(model.getDataEnvio(), DateUtils.DD_MM_YYYY_HH_MM) : "-");
		dto.setDataRecebimento(model.getDataRecebimento());
		dto.setDataRecebimentoFormatada(model.getDataRecebimento() != null ? DateUtils.format(model.getDataRecebimento(), DateUtils.DD_MM_YYYY_HH_MM) : "-");
		
		if (model.getSituacao() != null) {
			Departamento situacaoDto = new Departamento();
			situacaoDto.setId(model.getSituacao().getId());
			situacaoDto.setNome(model.getSituacao().getDescricao());	
			dto.setSituacao(situacaoDto);
		}
		
		return dto;

	}

	public List<MovimentoDto> toListDto(List<Movimento> listModel) {

		List<MovimentoDto> listDto = new ArrayList<MovimentoDto>();

		for(Movimento m : listModel){
			listDto.add(toDto(m));
		}

		return listDto;
	}

}
