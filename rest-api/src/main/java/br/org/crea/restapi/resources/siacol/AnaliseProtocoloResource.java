package br.org.crea.restapi.resources.siacol;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.AnaliseProtocoloSiacolService;


@Resource
@Path("siacol/analise")
public class AnaliseProtocoloResource {

	@Inject
	ResponseRestApi response;

	@Inject
	AnaliseProtocoloSiacolService service;
	
	@POST 
	@Path("protocolo") @Publico
	public Response analisar(ProtocoloDto dto) { 
		return response.success().data(service.analisar(dto)).build();
	}
	
	@GET
	@Path("busca-requerimento/{numeroProtocolo}")
	public Response verificarRequerimentoProtocolo(@PathParam("numeroProtocolo") Long numeroProtocolo) { 
		
		if(service.protocoloEmAnalisePossuiRequerimento(numeroProtocolo)) {
			return response.success().message("O protocolo em análise possui requerimento.").build();
		} else {
			return response.information().message("O protocolo em análise não possui requerimento").build();
		}
	}
}
