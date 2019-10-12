package br.org.crea.restapi.resources.commons;

import java.io.File;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.models.commons.dtos.ArquivoFormUploadDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.commons.ArquivoService;
import br.org.crea.commons.util.ResponseRestApi;


@Path("/commons/arquivo")
public class ArquivoResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject ArquivoService service;
	
	@Inject HttpClientGoApi httpClientGoApi;
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(@MultipartForm ArquivoFormUploadDto arquivo, @HeaderParam("Authorization") String token) {
		return response.success().data(service.upload(arquivo, httpClientGoApi.getUserDto(token).getIdPessoa())).build();
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Publico
	@Path("publico")
	public Response upload(@MultipartForm ArquivoFormUploadDto arquivo, @HeaderParam("idPessoa") Long idPessoa) {
		return response.success().data(service.upload(arquivo, idPessoa)).build();
	}
	
	@GET
	@Path("id/{id}") @Publico
	@Produces({ "application/msword", "application/pdf" })
	public Response download(@PathParam("id") Long id) {
		File arquivo = service.download(id);
		return Response.ok(arquivo).header("Content-Disposition", "attachment; filename=" + arquivo.getName() + ".pdf").build();
	}
	
	@GET
	@Path("{nome}") @Publico
	@Produces({ "application/msword", "application/pdf" })
	public Response download(@PathParam("nome") String nome) {
		return Response.ok(service.download(nome))
				.header("Content-Disposition", "attachment; filename=" + nome +".pdf").build();
	}
	
	@DELETE
	@Path("{id}") @Publico
	public Response delete(@PathParam("id") Long id) {
		service.delete(id);
		return response.success().build();
	}

}
