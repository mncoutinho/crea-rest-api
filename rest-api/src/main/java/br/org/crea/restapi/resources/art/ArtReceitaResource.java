package br.org.crea.restapi.resources.art;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.art.service.ArtReceitaService;
import br.org.crea.art.service.ContratoServicoService;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.art.dtos.ArtReceitaDto;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/art/receitas")
public class ArtReceitaResource {

	@Inject
	ArtReceitaService service;

	@Inject
	ContratoServicoService contratoService;

	@Inject
	ResponseRestApi response;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@GET
	@Path("arts-disponiveis")
	public Response getArtsDisponiveis(@HeaderParam("Authorization") String token) {

		return response.success().data(service.getArtsDisponiveis(httpClientGoApi.getUserDto(token).getRegistro())).build();

	}

	@GET
	@Path("{numeroArt}")
	public Response getAll(@PathParam("numeroArt") String numeroArt) {

		List<ContratoServicoDto> listDto = contratoService.getContratoReceitaPor(numeroArt);
		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.error().build();
		}

	}

	@GET
	@Path("recuperar/{idReceita}")
	public Response recuperarReceita(@PathParam("idReceita") Long idReceita) {
		ArtReceitaDto dto = service.recuperarReceitasEProdutos(idReceita);
		if (dto != null) {
			return response.success().data(dto).build();
		} else {
			return response.error().build();
		}
	}


	@POST
	public Response salvarReceita(ArtReceitaDto dto) {
		return response.success().data(service.salvaReceita(dto)).message("receita.salva").build();
	}
}
