package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.service.PessoaFisicaService;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.commons.util.StringUtil;

@Resource
@Path("/cadastro/pessoa-fisica")
public class PessoaFisicaResource {

	@Inject
	ResponseRestApi response;
	
	@Inject
	PessoaFisicaService service;

	@GET
	@Path("pis-pasep/{idPessoa}")
	public Response getPisPasep(@PathParam("idPessoa") Long idPessoa) {
		return response.success().data(service.getPisPasep(idPessoa)).build();
	}
	
	@PUT
	@Path("pis-pasep")
	public Response salvaPisPasep(DomainGenericDto dto) {
		// FIXME ele poderá remover um numero já digitado?
		if (StringUtil.ehPisPasepNit(dto.getDescricao()) ) {
			return response.success().data(service.salvaPisPasep(dto)).build();
		} else {
			return response.error().data("PIS/PASEP inválido").build();
		}
		
	}
	
	@GET
	@Path("assinatura/{idPessoa}")
	public Response getAssinatura(@PathParam("idPessoa") Long idPessoa) {
		return response.success().data(service.getAssinatura(idPessoa)).build();
	}
	
}
