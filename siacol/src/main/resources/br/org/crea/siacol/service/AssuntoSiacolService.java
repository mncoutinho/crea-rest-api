package br.org.crea.siacol.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.protocolo.RlAssuntosDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.RlAssuntosSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.siacol.converter.AssuntoSiacolConverter;

public class AssuntoSiacolService {

	@Inject
	AssuntoSiacolConverter converter;

	@Inject
	AssuntoSiacolDao dao;
	
	@Inject
	RlAssuntosDao daoassunto;

	public List<AssuntoDto> getAll() {
		return converter.toListDto(dao.getAll());
	}
	
	public List<AssuntoDto> getAtivo() {
		return converter.toListDto(dao.getAllAssuntos());
	}
	
	public AssuntoDto salvar(AssuntoDto dto) {
		
		try {
			
			dto.setId(dao.create(converter.toModel(dto)).getId());
			for (RlAssuntosSiacol rl : converter.toListModelRlAssunto(dto)){
				daoassunto.create(rl);
			}
			return dto;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public GenericSiacolDto atribuicaoConfea(GenericSiacolDto dto) {
		
		return dao.atribuicaoConfea(dto);
	}

	public AssuntoDto atualizar(AssuntoDto dto) {
		
		try {	
	    dao.update(converter.toModel(dto));
	    daoassunto.deleteByAssuntoSiacol(dto.getId());
	    for (RlAssuntosSiacol rl : converter.toListModelRlAssunto(dto)){
			daoassunto.create(rl);
		}
	    
		return dto;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void deletar(Long id) {
		dao.deleta(id);
	}

	public AssuntoDto existeCodigoAssunto(Long codigo) {
		return converter.toDto(dao.verificaCodigo(codigo));
	}



}
