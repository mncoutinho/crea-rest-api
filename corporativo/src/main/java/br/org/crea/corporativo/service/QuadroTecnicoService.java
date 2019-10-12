package br.org.crea.corporativo.service;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.empresa.QuadroTecnicoConverter;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.dao.cadastro.empresa.QuadroTecnicoDao;
import br.org.crea.commons.dao.cadastro.empresa.ResponsavelTecnicoDao;
import br.org.crea.commons.factory.commons.AcaoFactory;
import br.org.crea.commons.models.cadastro.QuadroTecnico;
import br.org.crea.commons.models.cadastro.ResponsavelTecnico;
import br.org.crea.commons.models.cadastro.dtos.empresa.QuadroTecnicoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.QuadroTecnicoProfissionalDto;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;

public class QuadroTecnicoService {
	
	@Inject QuadroTecnicoConverter converter;
	
	@Inject QuadroTecnicoDao dao;
	
	@Inject	ResponsavelTecnicoService responsavelService;
	
	@Inject	ResponsavelTecnicoDao responsavelTecnicoDao;
	
	@Inject EmpresaDao empresaDao;
	
	@Inject	AcaoFactory acaoFactory;
	
	public List<QuadroTecnicoDto> getQuadroTecnico(Long idEmpresa){
		return converter.toListDto(dao.getQuadroTecnicoPorProfissional(idEmpresa));
	}

	public void baixarQt(QuadroTecnicoProfissionalDto dto) {
		
		List<ResponsavelTecnico> listResponsavelTecnico = responsavelTecnicoDao.getResponsavelTecnicoByQT(dto.getIdQuadroTecnico());
		
		for (ResponsavelTecnico rt : listResponsavelTecnico) {
			responsavelService.acaoBaixaResponsavelTecnico(rt, dto.getIdProfissional(), dto.getIdEmpresa());
		}
		
		QuadroTecnico qt = dao.getBy(dto.getIdQuadroTecnico());
		qt.setDataFim(new Date());
		qt.setDataEfetivacaoBaixa(qt.getDataFim());
		qt.setDataDesligamento(qt.getDataFim());
		qt.setMatriculaAlteracao(Funcionario.getIdUsuarioPortal());
		qt.setMatriculaBaixa(qt.getMatriculaAlteracao());
		dao.update(qt);		
		
	}

}
