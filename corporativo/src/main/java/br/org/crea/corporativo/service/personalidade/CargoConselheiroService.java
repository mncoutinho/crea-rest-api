package br.org.crea.corporativo.service.personalidade;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.corporativo.personalidade.dto.CargoConselheiroDto;
import br.org.crea.corporativo.converter.personalidade.CargoConselheiroConverter;
import br.org.crea.corporativo.dao.personalidade.CargoConselheiroDao;

public class CargoConselheiroService {

	@Inject
	CargoConselheiroConverter converter;

	@Inject
	CargoConselheiroDao dao;

	public CargoConselheiroDto byId(Long id) {
		return converter.toDto(dao.getBy(id));
	}


	public List<CargoConselheiroDto> getAll() {
		return converter.toListDto(dao.getAll());
	}


	public List<CargoConselheiroDto> getByCargo(Long cargo) {
		return converter.toListDto(dao.getByCargo(cargo));
	}

	public String countByCargo(Integer cargo) {
		return converter.to_String(dao.getRecordCount("", " c ", " c.cargo.id =  " + cargo));
		
	}
	

}
