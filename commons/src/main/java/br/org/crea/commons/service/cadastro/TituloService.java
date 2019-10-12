package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.ConfeaTituloConverter;
import br.org.crea.commons.dao.cadastro.ConfeaTituloDao;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;


public class TituloService {
	
	@Inject HttpClientGoApi httpGoApi;
	@Inject ConfeaTituloConverter converter;
	@Inject ConfeaTituloDao dao;

	public DomainGenericDto getby(Long id) {
		return converter.toDto(dao.getBy(id));
		
	}
	public List<DomainGenericDto> getAllTitulos(){
		return converter.toListTitulo(dao.getAllTitulos());
	}

	public String getTituloById(Long id) {
		
		return dao.getTituloById(id);
	}

}
