package br.org.crea.restapi.resources.cadastro;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.empresa.ComposicaoSocietariaDto;
import br.org.crea.commons.service.cadastro.ComposicaoSocietariaService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/socio")
public class ComposicaoSocietariaResource {

	@Inject
	ResponseRestApi response;

	@Inject
	ComposicaoSocietariaService service;
	
	@GET
	@Path("{idEmpresa}")
	public Response getComposicaoSocietariaByIdEmpresa(@PathParam("idEmpresa") Long idEmpresa) {
		
		List<ComposicaoSocietariaDto> listDto = service.getComposicaoSocietariaByIdEmpresa(idEmpresa);
		
		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().message("Não há sócios").build();
		}
	}
}
