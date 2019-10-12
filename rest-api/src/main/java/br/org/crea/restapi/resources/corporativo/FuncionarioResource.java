package br.org.crea.restapi.resources.corporativo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.FuncionarioService;


@Resource
@Path("/funcionarios")
public class FuncionarioResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject FuncionarioService service;
	
	

	@GET
	public Response getAll(){
		return response.success().message("Recuperado com sucesso!").data(service.getAll()).build();
	}
	
	/** Recurso criado para buscar funcionário por matricula, utilizado no cadastramento de mesa diretoria no painel de Reunião Plenária
	 * 
	 * @param idPessoa
	 * @return
	 */
	@GET
	@Path("matricula/{matricula}")
	@Publico
	public Response getFuncionariosPorMatricula(@PathParam("matricula") Long matricula){
		return response.success().message("Recuperado com sucesso!").data(service.getFuncionariosPorMatricula(matricula)).build();
	}
	
	@POST @Publico
	public Response getFuncionariosPorNome(PesquisaGenericDto pesquisa){
		return response.success().message("Recuperado com sucesso!").data(service.getFuncionariosPorNome(pesquisa)).build();
	}
	
	@POST
	@Path("departamento/")
	@Publico
	public Response getFuncionarios(PesquisaGenericDto pesquisa){
		return response.success().data(service.getFuncionarios(pesquisa)).build();
	}

}