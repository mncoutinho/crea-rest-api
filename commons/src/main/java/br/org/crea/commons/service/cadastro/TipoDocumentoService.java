package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.TipoDocumentoConverter;
import br.org.crea.commons.dao.cadastro.TipoDocumentoDao;
import br.org.crea.commons.models.commons.dtos.TipoDocumentoDto;

public class TipoDocumentoService {

	@Inject TipoDocumentoConverter converter;
	
	@Inject TipoDocumentoDao dao;

	public List<TipoDocumentoDto> porModulo(Long modulo) {
		return converter.toListDto(dao.porModulo(modulo));
	}

	public TipoDocumentoDto salva(TipoDocumentoDto dto) {
		return converter.toDto(dao.create(converter.toModel(dto)));
	}

	public TipoDocumentoDto atualiza(TipoDocumentoDto dto) {
		return converter.toDto(dao.update(converter.toModel(dto)));
	}

	public List<TipoDocumentoDto> getAll() {
		return converter.toListDto(dao.getAllTipoDocumentos());
	}
	

	
}
