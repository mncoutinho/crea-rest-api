package br.org.crea.restapi.resources.commons;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.InstituicaoEnsinoService;


@Resource
@Path("/instituicao-ensino")
public class InstituicaoEnsinoResource {

	@Inject	ResponseRestApi response;
	
	@Inject InstituicaoEnsinoService service;
	
	
	@GET @Publico
	public Response getAllInstituicaoEnsino(){	
		return response.success().data(service.getAllInstituicaoEnsino()).build();
	}
	
	@GET
	@Path("curso") @Publico
	public Response getAllCurso(){	
		return response.success().data(service.getAllCurso()).build();
	}
	
	@GET
	@Path("campus/instituicao/{idInstituicao}") @Publico
	public Response getCampusByInstituicao(@PathParam("idInstituicao") Long id){	
		return response.success().data(service.getCursoListByInstituicaoId(id)).build();
	}
	
	@GET
	@Path("campus/curso/{idCurso}") @Publico
	public Response getCampusByCurso(@PathParam("idCurso") Long idCurso){	
		return response.success().data(service.getInstituicaoListByCursoId(idCurso)).build();
	}
	
	
}
