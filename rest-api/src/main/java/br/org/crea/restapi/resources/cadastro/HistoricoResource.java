package br.org.crea.restapi.resources.cadastro;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.HistoricoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.cadastro.HistoricoService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/historico")
public class HistoricoResource {

	@Inject
	ResponseRestApi response;

	@Inject
	HistoricoService service;

	@GET
	@Path("{idPessoa}/{idEvento}")
	public Response getEventosByPessoa(@PathParam("idPessoa") Long idPessoa, @PathParam("idEvento") Long idEvento) {
		return response.success().data(service.getHistoricosByPessoaEByEvento(idPessoa, idEvento)).build();
	}
	
	@POST
	public Response getHistoricosPaginadoByIdPessoa(PesquisaGenericDto pesquisa) {
		
		List<HistoricoDto> listDto = service.getHistoricosPaginadoByIdPessoa(pesquisa);
		
		if (!listDto.isEmpty()) {
			if (pesquisa.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalDeHistoricos(pesquisa)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			}
		} else {
			return response.information().message("Não há históricos").build();
		}
	}
}
