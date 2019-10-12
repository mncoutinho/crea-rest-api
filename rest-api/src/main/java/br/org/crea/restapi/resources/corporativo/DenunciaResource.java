package br.org.crea.restapi.resources.corporativo;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.corporativo.dtos.DenunciaDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.DenunciaService;


@Resource
@Path("/corporativo/denuncia")
public class DenunciaResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject DenunciaService service;
	
	
	@POST @Publico
	public Response cadastroDenunciaAnonima(DenunciaDto dto){
		return response.success().message("Denuncia registrada com sucesso!").data(service.cadastroDenunciaAnonima(dto)).build();
	}
}
