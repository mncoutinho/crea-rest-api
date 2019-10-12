package br.org.crea.corporativo.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.cadastro.dtos.TaxaDto;
import br.org.crea.corporativo.converter.TaxaConverter;
import br.org.crea.corporativo.dao.TaxaDao;

public class TaxaService {

	@Inject
	TaxaConverter converter;

	@Inject
	TaxaDao dao;

	public TaxaDto getTaxaBy(Long idTaxa) {
		return converter.toDto(dao.getTaxaBy(idTaxa));
	}

	public List<TaxaDto> getTaxas() {
		return converter.toListDto(dao.getTaxas());
	}

	
}
