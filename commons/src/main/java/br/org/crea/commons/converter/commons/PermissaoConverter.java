package br.org.crea.commons.converter.commons;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.funcionario.FuncionarioConverter;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.protocolo.RlAutorizacaoProtocolo;
import br.org.crea.commons.models.protocolo.dtos.AutorizacaoProtocoloDto;

public class PermissaoConverter {
	
	@Inject FuncionarioConverter funcionarioConverter;
	
	public List<AutorizacaoProtocoloDto> toListPermissaoProtocoloDto(List<RlAutorizacaoProtocolo> listModel) {
		List<AutorizacaoProtocoloDto> listDto = new ArrayList<AutorizacaoProtocoloDto>();
		
		listModel.forEach(model -> listDto.add(toDto(model)));
		return listDto;
	
	}
	
	public AutorizacaoProtocoloDto toDto(RlAutorizacaoProtocolo model) {
		AutorizacaoProtocoloDto dto = new AutorizacaoProtocoloDto();
		
		Optional.ofNullable(model.getPermissaoProtocolo()).ifPresent(p -> {
			
			DomainGenericDto domain = new DomainGenericDto();
			domain.setId(p.getId());
			domain.setDescricao(p.getDescricao());
			dto.setPermissao(domain);
			dto.setFuncionario(funcionarioConverter.toDto(model.optionalFuncionario().get())); 
		});
		
		return dto;
	}
}
