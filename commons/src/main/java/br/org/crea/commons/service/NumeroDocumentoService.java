package br.org.crea.commons.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.NumeroDocumentoConverter;
import br.org.crea.commons.converter.cadastro.domains.TipoDocumentoConverter;
import br.org.crea.commons.dao.NumeroDocumentoDao;
import br.org.crea.commons.models.cadastro.NumeroDocumento;
import br.org.crea.commons.models.cadastro.dtos.NumeroDocumentoDto;
import br.org.crea.commons.models.commons.dtos.TipoDocumentoDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;

public class NumeroDocumentoService {

	@Inject private NumeroDocumentoConverter converter;
	@Inject private NumeroDocumentoDao dao;
	@Inject private TipoDocumentoConverter tipoDocumentoConverter;
	
	public List<NumeroDocumentoDto> getAll() {
		return converter.toListDto(dao.getAll());
	}

	public NumeroDocumentoDto getNumeroDocumento(Long idDepartamento, Long idTipoDocumento) {
		return converter.toDto(dao.getNumeroDocumento(idDepartamento, idTipoDocumento));
	}
	
	public String getProximoNumeroDocumento(Long idDepartamento, Long idTipoDocumento) {
		NumeroDocumento numeroDocumento = new NumeroDocumento();
		String numeroReal = "";
		numeroDocumento = dao.getNumeroDocumento(idDepartamento, idTipoDocumento);
		
		if (numeroDocumento != null) {
			if (numeroDocumento.isTem_ano()) {
				numeroReal = numeroDocumento.getNumero() + (numeroDocumento.getAno() != null ? "/"+ numeroDocumento.getAno() : "") + " - " + numeroDocumento.getDepartamento().getSigla(); 
			}else {
				numeroReal = numeroDocumento.getNumero() + " - " + numeroDocumento.getDepartamento().getSigla();
			}
			
			numeroDocumento.setNumero(numeroDocumento.getNumero()+1);
			dao.update(numeroDocumento);
			converter.toDto(numeroDocumento);
		}
		
		return numeroReal;
	}

	public NumeroDocumentoDto salvaNumeroDocumento(NumeroDocumentoDto dto, UserFrontDto userDto) {
		return converter.toDto(dao.create(converter.toModel(dto)));

	}

	public NumeroDocumentoDto atualizaNumeroDocumento(NumeroDocumentoDto dto, UserFrontDto userDto) {
		return converter.toDto(dao.update(converter.toModel(dto)));
	}

	public List<NumeroDocumentoDto> getNumeroDocumentoDepartamento(Long idDepartamento) {
		return converter.toListDto(dao.getNumeroDocumentoDepartamento(idDepartamento));
	}

	public List<TipoDocumentoDto> getNumeroDocumentoParaCriacao(Long idDepartamento) {
		return tipoDocumentoConverter.toListDto(dao.getNumeroDocumentoParaCriacao(idDepartamento));
	}

}
