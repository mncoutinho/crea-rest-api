package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.RlModalidadeDepartamentoDto;
import br.org.crea.commons.service.cadastro.ModalidadeService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/modalidade")
public class ModalidadeResource {

	@Inject
	ResponseRestApi response;

	@Inject
	ModalidadeService service;

	@GET
	@Publico
	public Response getAll() {
		return response.success().data(service.getAll()).build();
	}
	
	@POST
	@Publico
	public Response salvaRlModalidadeDepartamento(RlModalidadeDepartamentoDto dto) {
		return response.success().data(service.salvaRlModalidadeDepartamento(dto)).build();		
	}
	

}
