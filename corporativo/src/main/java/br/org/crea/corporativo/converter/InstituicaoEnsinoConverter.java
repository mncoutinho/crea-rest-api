package br.org.crea.corporativo.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.commons.Campus;
import br.org.crea.commons.models.commons.Curso;
import br.org.crea.commons.models.commons.InstituicaoEnsinoAtu;
import br.org.crea.commons.models.commons.dtos.CampusDto;
import br.org.crea.commons.models.commons.dtos.CursoDto;
import br.org.crea.commons.models.commons.dtos.InstituicaoEnsinoDto;

public class InstituicaoEnsinoConverter {
	
	public List<InstituicaoEnsinoDto> toListDto(List<InstituicaoEnsinoAtu> listModel) {

		List<InstituicaoEnsinoDto> listDto = new ArrayList<InstituicaoEnsinoDto>();
		
		for(InstituicaoEnsinoAtu p : listModel){
			listDto.add(toDto(p));
		}
		
		return listDto;
	}
	
	public InstituicaoEnsinoDto toDto(InstituicaoEnsinoAtu model) {
		
		InstituicaoEnsinoDto dto = new InstituicaoEnsinoDto();
		
		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao());
		
		return dto;
		
	}
	
	
	public List<CursoDto> toListCursoDto(List<Curso> listModel) {

		List<CursoDto> listCursoDto = new ArrayList<CursoDto>();
		
		for(Curso p : listModel){
			listCursoDto.add(toCursoDto(p));
		}
		
		return listCursoDto;
	}
	
	public CursoDto toCursoDto(Curso model) {
		
		CursoDto dto = new CursoDto();
		
		dto.setId(model.getId());
		dto.setDescricao(model.getDescricao());
		
		return dto;
		
	}
	
	public List<CampusDto> toListCampusDto(List<Campus> listModel) {

		List<CampusDto> listCursoDto = new ArrayList<CampusDto>();
		
		for(Campus p : listModel){
			listCursoDto.add(toCampusDto(p));
		}
		
		return listCursoDto;
	}
	
	public CampusDto toCampusDto(Campus model) {
		
		CampusDto dto = new CampusDto();
		
		dto.setIdCurso(model.getCurso().getId());
		dto.setCurso(model.getCurso().getDescricao());
		dto.setInstituicao(model.getInstituicao().getDescricao());
		
		return dto;
		
	}

}
