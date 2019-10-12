package br.org.crea.restapi.resources.commons;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.PesquisaAuditoriaDto;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/commons/auditoria")
public class AuditoriaResource {
	
	
	@Inject	ResponseRestApi response;
	
	@Inject AuditoriaService service;
	
	
	@POST
	@Path("pesquisa") @Publico
	public Response pesquisa(PesquisaAuditoriaDto dto){
		return response.success().data(service.pesquisa(dto)).build();
	}
	
	
	@POST
	@Path("confirmacao-leitura") @Publico
	public Response confirmacaoLeitura(PesquisaAuditoriaDto dto){
		return response.success().data(service.confirmacaoLeitura(dto)).build();
	}

}
