package br.org.crea.corporativo.service;

import javax.inject.Inject;

import br.org.crea.commons.models.corporativo.Denuncia;
import br.org.crea.commons.models.corporativo.dtos.DenunciaDto;
import br.org.crea.corporativo.converter.DenunciaConverter;
import br.org.crea.corporativo.dao.DenunciaDao;

public class DenunciaService {

	@Inject DenunciaDao dao;
	
	@Inject DenunciaConverter converter;
	
	public Denuncia cadastroDenunciaAnonima(DenunciaDto dto) {
		return dao.cadastroDenunciaAnonima(converter.toModel(dto));
	}

}
