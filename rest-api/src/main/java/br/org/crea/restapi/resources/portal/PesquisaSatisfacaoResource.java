package br.org.crea.restapi.resources.portal;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.portal.dto.AtendimentoDto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.portal.service.AtendimentoService;


@Resource
@Path("/portal")
public class PesquisaSatisfacaoResource {
	
	
	@Inject	ResponseRestApi response;
	
	@Inject AtendimentoService service;
	
	@GET
	@Path("pesquisa-satisfacao/{numeroChamado}") @Publico
	public Response getChamado(@PathParam("numeroChamado") Long numeroChamado){
		if(service.chamadoDisponivelParaPesquisa(numeroChamado)){
			return response.success().message("Chamado disponível para pesquisa!").data(service.getBy(numeroChamado)).build();
		}else{
			return response.information().message("Sua pesquisa já foi feita!").build();
		}
	}
	
	@POST
	@Path("pesquisa-satisfacao") @Publico
	public Response salvaPesquisa(AtendimentoDto dto){
		return response.success().message("Pesquisa feita com sucesso!").data(service.atualizaPesquisa(dto)).build();
	}

}
