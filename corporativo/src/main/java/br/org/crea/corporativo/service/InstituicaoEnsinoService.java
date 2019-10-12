package br.org.crea.corporativo.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.commons.dtos.CampusDto;
import br.org.crea.commons.models.commons.dtos.CursoDto;
import br.org.crea.commons.models.commons.dtos.InstituicaoEnsinoDto;
import br.org.crea.corporativo.converter.InstituicaoEnsinoConverter;
import br.org.crea.corporativo.dao.InstituicaoEnsinoDao;

public class InstituicaoEnsinoService {

	@Inject InstituicaoEnsinoConverter converter;
	
	@Inject InstituicaoEnsinoDao dao;

	
	public List<InstituicaoEnsinoDto> getAllInstituicaoEnsino(){
		return converter.toListDto(dao.getAll());
	}
	
	public List<CampusDto> getInstituicaoListByCursoId(Long idCurso){
		return converter.toListCampusDto(dao.getInstituicaoListByCursoId(idCurso));
	}

	public List<CursoDto> getAllCurso() {
		return converter.toListCursoDto(dao.getAllCurso());
	}
	
	public List<CampusDto> getCursoListByInstituicaoId(Long idInstituicao) {
		return converter.toListCampusDto(dao.getCursoListByInstituicaoId(idInstituicao));
	}

}