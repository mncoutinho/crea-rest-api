package br.org.crea.siacol.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.protocolo.RlAssuntosDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
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
		List<AssuntoSiacol> listAssuntoSiacol = dao.getAll();
		Collections.sort(listAssuntoSiacol, new Comparator<AssuntoSiacol>(){
		     public int compare(AssuntoSiacol o1, AssuntoSiacol o2){
		         if(o1.getCodigo() == o2.getCodigo())
		             return 0;
		         return o1.getCodigo() < o2.getCodigo() ? -1 : 1;
		     }
		});	
		return converter.toListDto(listAssuntoSiacol);
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
