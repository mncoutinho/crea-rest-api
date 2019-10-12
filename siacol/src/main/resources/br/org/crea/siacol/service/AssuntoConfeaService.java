package br.org.crea.siacol.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.protocolo.RlAssuntosDao;
import br.org.crea.commons.dao.siacol.AssuntoConfeaDao;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.siacol.converter.AssuntoConfeaConverter;

public class AssuntoConfeaService {

	@Inject
	AssuntoConfeaConverter converter;

	@Inject
	AssuntoConfeaDao dao;
	
	@Inject
	RlAssuntosDao daoassunto;

	public List<AssuntoDto> getAll() {
		return converter.toListDto(dao.getAll());
	}
	
	public List<AssuntoDto> getAtivo() {
		return converter.toListDto(dao.getAllAssuntos());
	}
	
	public AssuntoDto salvar(AssuntoDto dto) {		
		return converter.toDto(dao.create(converter.toModel(dto)));
	}
	
	public AssuntoDto atualizar(AssuntoDto dto) {
	    dao.update(converter.toModel(dto));
	    dao.deleteByAssuntoConfea(dto.getId());
	    
		return dto;
	}

	public void deletar(Long id) {
		dao.deleta(id);
	}

	public AssuntoDto existeCodigoAssunto(Long codigo) {
		return converter.toDto(dao.verificaCodigo(codigo));
	}



}
