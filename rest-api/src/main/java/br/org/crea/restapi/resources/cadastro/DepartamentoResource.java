package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.service.cadastro.DepartamentoService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/departamento")
public class DepartamentoResource {

	@Inject
	ResponseRestApi response;

	@Inject
	DepartamentoService service;

	@GET
	@Publico
	@Path("modulo/{moduloSistema}")
	public Response getAllDepartamentos(@PathParam("moduloSistema") String moduloSistema) {
		return response.success().data(service.getAllDepartamentos(moduloSistema)).build();
	}

	//FIXME revisar a query que usa este recurso
	@GET
	@Publico
	@Path("{idDepartamento}")
	public Response getDepartamentoBy(@PathParam("idDepartamento") Long idDepartamento) {
		return response.success().data(service.getDepartamentoBy(idDepartamento)).build();
	} 

	@POST
	@Publico
	@Path("pesquisa-por-nome")
	public Response pesquisaPorNome(GenericDto dto) {
		return response.success().data(service.pesquisaPorNome(dto)).build();
	}

	@PUT
	@Publico
	public Response atualiza(DepartamentoDto dto) {
		return response.success().data(service.atualizaDepartamento(dto)).build();
	}
	
	@GET
	@Publico
	@Path("departamentos-protocolo-siacol")
	public Response getDepartamentosProtocoloSiacol() {
		return response.success().data(service.getDepartamentosProtocoloSiacol()).build();
	}

}
