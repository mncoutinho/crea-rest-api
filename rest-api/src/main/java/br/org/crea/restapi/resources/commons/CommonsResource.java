package br.org.crea.restapi.resources.commons;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.redmine.RedMineIssueDto;
import br.org.crea.commons.service.CommonsService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.AuditoriaService;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.AuthService;

@Resource
@Path("/commons")
public class CommonsResource {

	@Inject
	ResponseRestApi response;

	@Inject
	CommonsService service;

	@Inject
	AuditoriaService audita;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@Inject
	AuthService authService;

	@GET
	@Path("tipo-atendimento")
	public Response getBy() {
		return response.success().data(service.getTiposAtendimento()).build();
	}

	@GET
	@Path("entidades-classe")
	@Publico
	public Response getEntidadesClasse() {
		return response.success().data(service.getEntidadesClasse()).build();
	}

	@POST
	@Path("redmine")
	public Response issue(RedMineIssueDto dto, @HeaderParam("Authorization") String token) {

		return response.success().data(service.issueRedmine(dto, httpClientGoApi.getUserDto(token))).build();

	}
	
	@GET
	@Path("verify-api")
	@Publico
	public Response serverOn() {
		return response.success().data(true).build();
	}

	@GET
	@Path("verify-bd")
	@Publico
	public Response bancoOn() {
		return response.success().data(service.bancoOn()).build();
	}
}
