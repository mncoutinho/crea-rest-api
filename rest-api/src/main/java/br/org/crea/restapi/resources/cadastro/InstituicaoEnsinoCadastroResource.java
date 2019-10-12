package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.cadastro.service.InstituicaoEnsinoCadastroService;
import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/cadastro/instituicoes-ensino")
public class InstituicaoEnsinoCadastroResource {

	@Inject	ResponseRestApi response;
	
	@Inject InstituicaoEnsinoCadastroService service;
	
	@GET @Publico
	public Response getAllInstituicaoEnsino(){	
		return response.success().data(service.getInstituicaoEnsinoCadastroDtoByUf()).build();
	}
	
	@GET
	@Path("{idInstituicao}/campus") @Publico
	public Response getCampusByInstituicao(@PathParam("idInstituicao") Long id){	
		return response.success().data(service.getCampusListByInstituicaoEnsinoCadastroId(id)).build();
	}
	
	@GET
	@Path("campus/{idCampus}/cursos") @Publico
	public Response getCursoByCampus(@PathParam("idCampus") Long id){	
		return response.success().data(service.getCursoListByCampusId(id)).build();
	}
	
	/*
	@GET
	@Path("curso") @Publico
	public Response getAllCurso(){	
		return response.success().data(service.getAllCurso()).build();
	}
	
	
	
	@GET
	@Path("campus/curso/{idCurso}") @Publico
	public Response getCampusByCurso(@PathParam("idCurso") Long idCurso){	
		return response.success().data(service.getInstituicaoListByCursoId(idCurso)).build();
	}
*/	
}
