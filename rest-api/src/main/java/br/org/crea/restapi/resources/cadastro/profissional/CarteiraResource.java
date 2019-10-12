package br.org.crea.restapi.resources.cadastro.profissional;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.profissional.CarteiraService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/profissional/carteira")
public class CarteiraResource {

	@Inject
	ResponseRestApi response;

	@Inject
	CarteiraService service;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@GET
	@Path("valida-solicitacao/{idPessoa}")
	public Response solicitaCarteiraCreaonline(@PathParam("idPessoa") Long idPessoa) {
		
		List<String> mensagens = service.validaSolicitacaoCreaonline(idPessoa);
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
	}
	
	@POST
	@Path("solicita-segunda-via")
	public Response solicitaSegundaViaCarteiraCreaonline(DomainGenericDto dto, @HeaderParam("Authorization") String token) {
		
		List<String> mensagens = service.solicitaCarteiraCreaonline(dto, httpClientGoApi.getUserDto(token));
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
	}


}
