package br.org.crea.cadastro.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.InstituicaoEnsinoCadastroDao;
import br.org.crea.commons.models.cadastro.CampusCadastro;
import br.org.crea.commons.models.cadastro.CursoCadastro;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.InstituicaoEnsinoCadastroDto;
public class InstituicaoEnsinoCadastroService {
	
	
	@Inject InstituicaoEnsinoCadastroDao dao;
	
	public List<InstituicaoEnsinoCadastroDto> getInstituicaoEnsinoCadastroDtoByUf(){
		return dao.getInstituicaoEnsinoDTOListByUF();
	}
	
	public List<DomainGenericDto> getCampusListByInstituicaoEnsinoCadastroId(Long idInstituicao){
		List<DomainGenericDto> listCampusDto = new ArrayList<DomainGenericDto>();
		List<CampusCadastro> campusCadastros = dao.getCampusListByInstituicaoEnsinoCadastroId(idInstituicao);
		campusCadastros.forEach(campi -> {
			DomainGenericDto domainGenericDto = new DomainGenericDto();
			domainGenericDto.setId(campi.getId());
			domainGenericDto.setDescricao(campi.getNome());
			listCampusDto.add(domainGenericDto);
		});
		
		return listCampusDto;
	}
	
	public List<DomainGenericDto> getCursoListByCampusId(Long idCampus){
		List<DomainGenericDto> listCampusDto = new ArrayList<DomainGenericDto>();
		List<CursoCadastro> cursoCadastros = dao.getCursoListByCampusCadastroId(idCampus);
		cursoCadastros.forEach(curso -> {
			DomainGenericDto domainGenericDto = new DomainGenericDto();
			domainGenericDto.setIdString(String.valueOf(curso.getId()));
			domainGenericDto.setDescricao(curso.getNome());
			listCampusDto.add(domainGenericDto);
		});
		
		return listCampusDto;
	}
}
