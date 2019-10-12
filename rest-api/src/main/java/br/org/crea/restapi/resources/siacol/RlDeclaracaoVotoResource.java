package br.org.crea.restapi.resources.siacol;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.siacol.dtos.RlDeclaracaoVotoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.dao.RlDeclaracaoVotoDao;
import br.org.crea.siacol.service.RlDeclaracaoVotoService;


@Resource
@Path("/siacol/declaracao-voto")
public class RlDeclaracaoVotoResource {

	@Inject	ResponseRestApi response;

	@Inject	RlDeclaracaoVotoService declaracaoVotoService;	
	
	@Inject HttpClientGoApi httpClientGoApi;

	@Inject RlDeclaracaoVotoDao rlDeclaracaoVotoDao;

	@GET
	@Path("item/{idItem}")
	@Publico
	public Response getDeclaracaoByIdItem(@PathParam("idItem") Long idItem) {
		return response.success().data(declaracaoVotoService.getDeclaracaoByIdItem(idItem)).build();
	}
	
	@GET
	@Path("valida-declaracao-voto/{idItem}")
	@Publico
	public Response validaDeclaracaoVoto(@PathParam("idItem") Long idItem) {
		return response.success().data(declaracaoVotoService.validaDeclaracaoVoto(idItem)).build();
	}
	
	@GET
	@Path("participantes/{idItem}")
	@Publico
	public Response participantes(@PathParam("idItem") Long idItem) {
		return response.success().data(declaracaoVotoService.participantes(idItem)).build();
	}
	
	@POST
	@Path("salva/{idItem}/{idConselheiro}")
	@Publico
	public Response salvarDeclaracaoVoto(@PathParam("idItem") Long idItem, @PathParam("idConselheiro") Long idConselheiro, @HeaderParam("Authorization") String token) {
		return response.success().data(declaracaoVotoService.salvarDeclaracaoVoto(idItem, idConselheiro, httpClientGoApi.getUserDto(token))).build();
	}
	
	@DELETE
	@Path("delete/{idItem}/{idConselheiro}")
	@Publico
	public Response deletarDeclaracaoVoto(@PathParam("idItem") Long idItem, @PathParam("idConselheiro") Long idConselheiro, @HeaderParam("Authorization") String token) {
		if (rlDeclaracaoVotoDao.getByItemConselheiro(idItem, idConselheiro) != null) {
			return response.information().message("O voto desse conselheiro já está declarado!").build();
		} else {
			return response.success().data(declaracaoVotoService.deletarDeclaracaoVoto(idItem, idConselheiro, httpClientGoApi.getUserDto(token))).build();
		}
	}
	
	@PUT
	@Publico
	public Response atualizarDeclaracaoVoto(RlDeclaracaoVotoDto dto) {
		return response.success().data(declaracaoVotoService.atualizarDeclaracaoVoto(dto)).build();
	}
}
