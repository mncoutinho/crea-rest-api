package br.org.crea.corporativo.service;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.empresa.ResponsavelTecnicoConverter;
import br.org.crea.commons.dao.cadastro.empresa.QuadroTecnicoDao;
import br.org.crea.commons.dao.cadastro.empresa.ResponsavelTecnicoDao;
import br.org.crea.commons.factory.commons.AcaoFactory;
import br.org.crea.commons.models.cadastro.ResponsavelTecnico;
import br.org.crea.commons.models.cadastro.dtos.empresa.ResponsavelTecnicoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.QuadroTecnicoProfissionalDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.enuns.TipoAcaoEnum;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;

public class ResponsavelTecnicoService {
	
	@Inject
	ResponsavelTecnicoConverter converter;
	
	@Inject
	ResponsavelTecnicoDao dao;
	
	@Inject
	QuadroTecnicoDao quadroTecnicoDao;
	
	@Inject
	AcaoFactory acaoFactory;
	
	public List<ResponsavelTecnicoDto> getResponsabilidadeTecnica(PesquisaGenericDto pesquisa){
		return converter.toListDto(dao.getResponsabilidadeTecnica(pesquisa));
	}
	
	public void baixaResponsavelTecnico(QuadroTecnicoProfissionalDto dto) {
		ResponsavelTecnico rt = dao.getBy(dto.getIdResponsavelTecnico());
		acaoBaixaResponsavelTecnico(rt, rt.getQuadro().getProfissional().getId(), rt.getQuadro().getEmpresa().getId());
	}
	
	public void acaoBaixaResponsavelTecnico(ResponsavelTecnico rt, Long idProfissional, Long idEmpresa) {
		
		rt.setDataFim(new Date());	
		rt.setDataEfetivacaoBaixa(rt.getDataFim());
		rt.setDataAlteracao(rt.getDataFim());
		rt.setMatriculaBaixa(Funcionario.getIdUsuarioPortal());
		rt.setMatriculaAlteracao(rt.getMatriculaBaixa());
		dao.update(rt);
		
		acaoFactory.cadastraAcao(TipoAcaoEnum.BAIXA_DE_RESPONSAVEL_TECNICO.getId(), idProfissional, idEmpresa);
		
		List<ResponsavelTecnico> listaResponsavelTecnico =  dao.getResponsaveisTecnicosByEmpresa(idEmpresa);
		
		if(listaResponsavelTecnico == null || listaResponsavelTecnico.size() == 0) {
			quadroTecnicoDao.atualizaQTQuandoNaoExisteMaisRT(rt.getQuadro().getId());
		}
		
	}
	
}
