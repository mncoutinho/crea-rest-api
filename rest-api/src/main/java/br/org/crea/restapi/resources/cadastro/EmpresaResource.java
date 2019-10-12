package br.org.crea.restapi.resources.cadastro;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.empresa.EmpresaDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.QuadroTecnicoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.empresa.EmpresaService;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.ResponsavelTecnicoService;

@Resource
@Path("cadastro/empresa")
public class EmpresaResource {

	@Inject
	ResponseRestApi response;

	@Inject
	ResponsavelTecnicoService responsavelTecnicoService;
	
	@Inject
	EmpresaService service;
	
	@Inject
	HttpClientGoApi httpClientGoApi;

	@POST
	@Path("consulta-por-nome")
	@Publico
	public Response buscaEmpresaByNome(PesquisaGenericDto dto) {

		List<EmpresaDto> listDto = new ArrayList<EmpresaDto>();
		listDto = service.buscaListEmpresaByNome(dto);
		if (!listDto.isEmpty()) {
			if (dto.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalbuscaProfissionalByNome(dto)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			} 
		} else {
			return response.error().message("interessado.notExist").build();
		}
	}
	
	@GET
	@Path("consulta-por-cnpj/{numeroCNPJ}")
	@Publico
	public Response buscaEmpresaByCNPJ(@PathParam("numeroCNPJ") String numeroCNPJ) {

		List<EmpresaDto> listDto = new ArrayList<EmpresaDto>();
		listDto = service.buscaEmpresaByCNPJ(numeroCNPJ);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}	
		

	}
	
	
	@GET
	@Path("consulta-por-registro/{numeroRegistro}")
	@Publico
	public Response buscaEmpresaByRegistro(@PathParam("numeroRegistro") Long numeroRegistro) {

		List<EmpresaDto> listDto = new ArrayList<EmpresaDto>();
		listDto = service.buscaEmpresaByRegistro(numeroRegistro);
	
		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}	

	}
	
	@GET
	@Path("detalhar/{idEmpresa}")
	@Publico
	public Response detalharEmpresa(@PathParam("idEmpresa") Long idEmpresa) {

		List<EmpresaDto> listDto = new ArrayList<EmpresaDto>();
		listDto = service.buscaEmpresaDetalhadaByRegistro(idEmpresa);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}

	}
	
	@GET
	@Path("detalhar/dados-gerais/{idEmpresa}")
	@Publico
	public Response detalharDadosGeraisEmpresa(@PathParam("idEmpresa") Long idEmpresa) {

		List<EmpresaDto> listDto = new ArrayList<EmpresaDto>();
		listDto = service.buscaEmpresaDetalharDadosGeraisByRegistro(idEmpresa);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}

	}
	
	@POST
	@Path("quadro-tecnico")
	@Publico
	public Response getQuadroTecnicoByIdEmpresa(PesquisaGenericDto pesquisa) {
		
		List<QuadroTecnicoDto> listDto = service.getQuadroTecnico(pesquisa);
		
		if (!listDto.isEmpty()) {
			if (pesquisa.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalDeRegistrosQuadroTecnico(pesquisa)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			}
		} else {
			return response.information().message("Não foram encontrados quadro técnicos.").build();
		}
	}
	
	@GET
	@Path("quadro-tecnico-detalhado/{id}")
	@Publico
	public Response getQuadroTecnicoDetalhadoByIdProfissional(@PathParam("id") Long id) {
		
		QuadroTecnicoDto dto = service.getQuadroTecnicoDetalhado(id);
		
		if (dto != null) {
			return response.success().data(dto).build();
		} else {
			return response.information().message("Quadro técnico não encontrado").build();
		}
	}
	
	@GET
	@Path("curriculo")
	@Publico
	public Response getEmpresasCurriculo(@HeaderParam("Authorization") String token) {

		return response.success().data(service.getEmpresasCurriculo(httpClientGoApi.getUserDto(token))).build();

	}

	
	
}

