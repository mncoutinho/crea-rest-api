package br.org.crea.commons.service.fiscalizacao;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.fiscalizacao.FiscalizacaoDomainConverter;
import br.org.crea.commons.dao.fiscalizacao.FiscalizacaoDomainDao;
import br.org.crea.commons.models.commons.dtos.GenericDto;

public class FiscalizacaoDomainService {

	@Inject
	FiscalizacaoDomainConverter converter;
	
	@Inject
	FiscalizacaoDomainDao domainDao;

	public List<GenericDto> getAllAtividades() {
		return converter.toListAtividadesDto(domainDao.getAllAtividades());
	}
	
	public List<GenericDto> getAllAtividadesCondominio() {
		return converter.toListAtividadesDto(domainDao.getAllAtividades());
	}

	public List<GenericDto> getAllRamos() {
		return converter.toListRamosDto(domainDao.getAllRamos());
	}

	public List<GenericDto> getAtividades(String tipoPJ) {
		return converter.toListAtividadesDto(domainDao.getAtividades(tipoPJ));
	}
}
