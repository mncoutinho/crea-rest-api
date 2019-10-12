package br.org.crea.commons.service.commons;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.commons.PermissaoConverter;
import br.org.crea.commons.dao.protocolo.RlAutorizacaoProtocoloDao;
import br.org.crea.commons.models.protocolo.dtos.AutorizacaoProtocoloDto;

public class PermissaoService {

	@Inject 
	RlAutorizacaoProtocoloDao rlAutorizacaoProtocoloDao;
	
	@Inject
	PermissaoConverter converter;
	
	public List<AutorizacaoProtocoloDto> getPermissoesProtocoloPor(Long idFuncionario) {
		return converter.toListPermissaoProtocoloDto(rlAutorizacaoProtocoloDao.getAutorizacoesPor(idFuncionario));
	}
	
}
