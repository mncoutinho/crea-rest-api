package br.org.crea.commons.converter.protocolo;

import javax.inject.Inject;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.SituacaoProtocolo;
import br.org.crea.commons.models.protocolo.dtos.MovimentoProtocoloDto;
import br.org.crea.commons.util.DateUtils;

public class MovimentoProtocoloConverter {
	
	@Inject SituacaoProtocoloConverter situacaoConverter;
		
	public MovimentoProtocoloDto toDto(Movimento model) {
		
		MovimentoProtocoloDto dto = new MovimentoProtocoloDto();
		
		if(model != null) {
			
			dto.setId(model.getId());
			dto.setIdDepartamentoOrigem(model.temDepartamentoOrigem() ? model.getDepartamentoOrigem().getId() : null);
			dto.setIdDepartamentoDestino(model.temDepartamentoDestino() ? model.getDepartamentoDestino().getId() : null);
			dto.setSituacao(situacaoConverter.toDto(model.getSituacao()));
			dto.setDataEnvio(model.getDataEnvio());
			dto.setDataEnvioFormatada(DateUtils.format(model.getDataEnvio(), DateUtils.DD_MM_YYYY_HH_MM));
			dto.setDataRecebimento(model.getDataRecebimento());
			dto.setDataRecebimentoFormatada(DateUtils.format(model.getDataRecebimento(), DateUtils.DD_MM_YYYY_HH_MM));
			dto.setIdFuncionarioRemetente(model.getIdFuncionarioRemetente());
			dto.setIdFuncionarioReceptor(model.getIdFuncionarioReceptor());
			
			if(model.temDepartamentoDestino()) {
				
				dto.setModuloDepartamentoDestino(model.getDepartamentoDestino().getModuloDepartamento());

				if(model.getDepartamentoDestino().temDepartamentoPai()) {
					
					dto.setIdDepartamentoPaiDestino(model.getDepartamentoDestino().getDepartamentoPai().getId());
				}
			}
			
			return dto;
			
		} else {
			
			return null;
		}
	}
	
	public Movimento toModel(MovimentoProtocoloDto dto){
		
		Movimento movimento = new Movimento();
		movimento.setDataEnvio(dto.getDataEnvio());
		movimento.setDataRecebimento(dto.getDataRecebimento());
		
		Departamento departamentoDestino = new Departamento();
		departamentoDestino.setId(dto.getIdDepartamentoDestino());
		
		movimento.setDepartamentoDestino(departamentoDestino);
		
		Departamento departamentoOrigem = new Departamento();
		departamentoOrigem.setId(dto.getIdDepartamentoOrigem());
		
		movimento.setDepartamentoOrigem(departamentoOrigem);
		
		movimento.setIdFuncionarioReceptor(dto.getIdFuncionarioReceptor());
		movimento.setIdFuncionarioRemetente(dto.getIdFuncionarioRemetente());
		
		Protocolo protocolo = new Protocolo();
		protocolo.setIdProtocolo(dto.getProtocolo() != null ? dto.getProtocolo().getId() : null);
		
		movimento.setProtocolo(protocolo);
		
		SituacaoProtocolo situacao = new SituacaoProtocolo();
		situacao.setId(dto.getSituacao() != null ? dto.getSituacao().getId() : null);
		
		movimento.setSituacao(situacao);
		
		return movimento;
	}

}
