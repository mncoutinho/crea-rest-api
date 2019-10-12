package br.org.crea.restapi.resources.art;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.crea.art.service.ArtLivroOrdemService;
import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.art.dtos.ArtLivroDeOrdemDto;
import br.org.crea.commons.models.art.dtos.PesquisaArtDto;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/art/livro-ordem")
public class ArtLivroOrdemResource {

	@Inject
	ResponseRestApi response;

	@Inject
	ArtLivroOrdemService service;
		
	@GET
	@Path("{numeroArt}")
	public Response getByArt(@PathParam("numeroArt") String numeroArt) {
		List<ArtLivroDeOrdemDto> lista = service.getByArt(numeroArt);
		return response.success().data(lista).build();
	}
	
	@POST
	@Path("pesquisa")
	public Response getByArtPaginado(PesquisaArtDto pesquisa) {
		List<ArtLivroDeOrdemDto> lista = service.getByArtPaginado(pesquisa);
		
		if (!lista.isEmpty()) {
			if (pesquisa.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalDeRegistrosDaPesquisa(pesquisa)).data(lista).build();
			} else {
				return response.success().data(lista).build();
			}
		} else {
			return response.information().message("Não existem livros de ordem cadastrados").build();
		}
	}
	
	@POST
	public Response cadastrar(ArtLivroDeOrdemDto dto) {
		List<String> mensagens = service.cadastrar(dto);
		if (mensagens.size() > 0)
			return response.error().messages(mensagens).build();
		else
			return response.success().build();
	}
	
	@PUT
	public Response atualizar(ArtLivroDeOrdemDto dto) {
		List<String> mensagens = service.atualizar(dto);
		if (mensagens.size() > 0)
			return response.error().messages(mensagens).build();
		else
			return response.success().build();
	}
	
	@DELETE
	@Path("{codigo}")
	public Response deletar(@PathParam("codigo") String codigo) {
		if(service.deletar(codigo)) return response.success().build(); 
		return response.error().build();
	}

	@DELETE
	@Path("arquivo/{idArquivo}")
	public Response deletarArquivo(@PathParam("idArquivo") String idArquivo) {
		if(service.deletarArquivo(idArquivo)) return response.success().build(); 
		return response.error().build();
	}
	
	@GET
	@Path("download/{numeroArt}/{registro}")
	public Response getRelatorio(@PathParam("numeroArt") String numeroArt, @PathParam("registro") String registro) {
		byte[] retorno = service.download(numeroArt,registro);
		
		if(retorno == null) return response.error().build();
		
		return Response.ok(retorno).type(MediaType.APPLICATION_OCTET_STREAM).build();
	}
}