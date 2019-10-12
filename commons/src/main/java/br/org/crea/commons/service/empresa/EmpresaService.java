package br.org.crea.commons.service.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.empresa.EmpresaConverter;
import br.org.crea.commons.converter.cadastro.empresa.QuadroTecnicoConverter;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.cadastro.dtos.empresa.EmpresaDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.QuadroTecnicoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.service.financeiro.FinDividaService;

public class EmpresaService {
	
	@Inject	EmpresaConverter empresaConverter;
	
	@Inject	QuadroTecnicoConverter quadroTecnicoConverter;
		
	@Inject EmpresaDao empresaDao;
	
	@Inject InteressadoDao interessadoDao;
	
	@Inject FinDividaService finDividaService;
	
	public List<EmpresaDto> buscaListEmpresaByNome(PesquisaGenericDto dto) {
		
		
		List<Empresa> listEmpresa = new ArrayList<Empresa>();

		listEmpresa = empresaDao.buscaListEmpresaByNome(dto);

		
		return !listEmpresa.isEmpty() ? empresaConverter.toListDtoEmpresa(listEmpresa) : new ArrayList<EmpresaDto>();
	}
	
	public int getTotalbuscaProfissionalByNome(PesquisaGenericDto pesquisa) {
		return empresaDao.totalBuscaListEmpresaByNome(pesquisa);
	}
	
	
	public List<EmpresaDto> buscaEmpresaByCNPJ(String numeroCNPJ) {
		
		List<Empresa> listEmpresa = new ArrayList<Empresa>();
		listEmpresa = empresaDao.buscaEmpresaByCNPJ(numeroCNPJ);
		
		return !listEmpresa.isEmpty() ? empresaConverter.toListDtoEmpresa(listEmpresa) : new ArrayList<EmpresaDto>();
	}
	
	
	public List<EmpresaDto> buscaEmpresaByRegistro(Long numeroRegistro) {
		
		List<Empresa> listEmpresa = new ArrayList<Empresa>();
		listEmpresa = empresaDao.buscaEmpresaByRegistro(numeroRegistro);
		
		return !listEmpresa.isEmpty() ? empresaConverter.toListDtoEmpresa(listEmpresa) : new ArrayList<EmpresaDto>();

	}

	public List<EmpresaDto> buscaEmpresaDetalhadaByRegistro(Long numeroRegistro) {
		
		List<Empresa> listEmpresa = new ArrayList<Empresa>();
		listEmpresa = empresaDao.buscaEmpresaDetalhadaByRegistro(numeroRegistro);
		
		return !listEmpresa.isEmpty() ? empresaConverter.toListDtoEmpresaDetalhada(listEmpresa) : new ArrayList<EmpresaDto>();

	}

	public List<EmpresaDto> buscaEmpresaDetalharDadosGeraisByRegistro(Long idEmpresa) {
		List<Empresa> listEmpresa = new ArrayList<Empresa>();
		listEmpresa = empresaDao.buscaEmpresaDetalhadaByRegistro(idEmpresa);
		
		return !listEmpresa.isEmpty() ? empresaConverter.toListDtoEmpresaDetalharDadosGerais(listEmpresa) : new ArrayList<EmpresaDto>();
	}
	
	public boolean verificaProvisorio(Long registroEmpresa) {
		return empresaDao.vefificaProvisorio(registroEmpresa);
	}

	public List<QuadroTecnicoDto> getQuadroTecnico(PesquisaGenericDto pesquisa) {
		return this.quadroTecnicoConverter.toListDtoDadosGerais(this.empresaDao.getQuadroTecnicoPorEmpresaPaginado(pesquisa));
	}
	
	public int getTotalDeRegistrosQuadroTecnico(PesquisaGenericDto pesquisa) {
		return empresaDao.getTotalDeRegistrosQuadroTecnico(pesquisa);
	}

	public QuadroTecnicoDto getQuadroTecnicoDetalhado(Long id) {
		return this.quadroTecnicoConverter.toDtoDetalhado(this.empresaDao.getQuadroTecnicoPorId(id));
	}

	public List<DomainGenericDto> getEmpresasCurriculo(UserFrontDto dto) {
		return empresaDao.getEmpresasCurriculo(dto);
	}

}
