package br.org.crea.restapi.resources.cadastro;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.service.DocumentoService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/documento")
public class DocumentoResource {

	@Inject
	ResponseRestApi response;

	@Inject
	DocumentoService service;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@GET
	@Path("{idDocumento}")
	public Response recuperaDocumento(@PathParam("idDocumento") Long idDocumento) {
		return response.success().data(service.recuperaDocumento(idDocumento)).build();
	}

	@GET
	@Path("tipo-documento/{idTipoDocumento}")
	public Response recuperaDocumentosByIdTipoDocumento(@PathParam("idTipoDocumento") Long idTipoDocumento) {
		return response.success().data(service.recuperaDocumentosByIdTipoDocumento(idTipoDocumento)).build();
	}

	@GET
	@Path("protocolo/{numeroProtocolo}") @Publico //FIXME colocado @publico para uso no painel plenaria, remover quando token estiver sendo utilizado
	public Response recuperaDocumentosByNumeroProtocolo(@PathParam("numeroProtocolo") Long numeroProtocolo) {
		return response.success().data(service.recuperaDocumentosByNumeroProtocolo(numeroProtocolo)).build();
	}

	@GET
	@Path("protocolo/{idProtocolo}/{idTipoDocumento}")
	public Response recuperaByProtocoloDocumento(@PathParam("idProtocolo") Long idProtocolo, @PathParam("idTipoDocumento") Long idTipoDocumento) {
		return response.success().data(service.recuperaByProtocoloDocumento(idProtocolo, idTipoDocumento)).build();
	}

	@POST
	public Response salvaDocumento(DocumentoGenericDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.salvaDocumento(dto, httpClientGoApi.getUserDto(token))).build();
	}

	@POST
	@Path("assina")
	@Publico
	public Response assinaEnviaDocflow(DocumentoGenericDto dto) {
		return response.success().data(service.assinaEnviaDocflow(dto)).build();
	}

	@PUT
	public Response atualizaDocumento(DocumentoGenericDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.atualizaDocumento(dto, httpClientGoApi.getUserDto(token))).build();
	}
	
	@PUT
	@Path("numero-documento")
	public Response atualizaNumeroDocumento(DocumentoGenericDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.atualizaNumeroDocumento(dto, httpClientGoApi.getUserDto(token))).build();
	}
	
	@PUT
	@Path("liberar-pauta/{idDocumento}")
	public Response liberarDocumento(@PathParam("idDocumento") Long idDocumento, @HeaderParam("Authorization") String token) {
		return response.success().data(service.liberarDocumento(idDocumento, httpClientGoApi.getUserDto(token))).build();
	}

	@DELETE
	@Path("{idDocumento}")
	public Response excluiDocumento(@PathParam("idDocumento") Long idDocumento, @HeaderParam("Authorization") String token) {
		service.excluiDocumento(idDocumento, httpClientGoApi.getUserDto(token));
		return response.success().build();
	}

	@GET
	@Path("pdf/{idDocumento}/{token}")
	@Publico
	public Response pdfPorIdDocumento(@Context HttpServletRequest request, @PathParam("idDocumento") Long idDocumento, @PathParam("token") String token) throws IllegalArgumentException,
			IllegalAccessException, IOException {
		if (!httpClientGoApi.estaLogado(token)) {
			return response.error().message("Token expirado").build();
		} else {
			return service.exportPdfPorIdDocumento(request, idDocumento);
		}

	}

	@POST
	@Path("gera-arquivo")
	@Publico
	public Response geraArquivo(@Context HttpServletRequest request, DocumentoGenericDto dto, @HeaderParam("Authorization") String token) throws IllegalArgumentException, IllegalAccessException, IOException {
		return response.success().data(service.geraArquivo(request, dto, httpClientGoApi.getUserDto(token))).build();
	}

}
