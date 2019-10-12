package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.service.CadastroService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.DepartamentoService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro")
public class CadastroResource {

	@Inject
	ResponseRestApi response;

	@Inject
	CadastroService service;

	@Inject
	DepartamentoService departamentoService;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@GET
	@Path("quadro-tecnico/{idPessoa}/{tipo}")
	@Publico
	public Response getQuadrosTecnicos(@PathParam("idPessoa") Long idPessoa, @PathParam("tipo") String tipo) {
		return response.success().data(service.getQuadosTecnicos(idPessoa, tipo)).build();
	}

	@GET
	@Path("unidades-atendimento-coordenacao")
	public Response getUnidadesCoordenacaoPor(@HeaderParam("Authorization") String token) {
		return response.success().data(service.getUnidadesCoordenacaoPor(httpClientGoApi.getMatriculaFuncionario(token))).build();
	}
	
	@GET
	@Path("unidades-atendimento")
	@Publico
	public Response getCoordenacoes() {
		return response.success().data(service.getAllUnidades()).build();
	}

	@GET
	@Path("adm-unidades-atendimento")
	@Publico
	public Response getUnidadesAtendimentoAdm() {
		return response.success().data(service.getAllUnidadesAtendimentoAdm()).build();
	}

	@GET
	@Path("unidades-atendimento-seat")
	@Publico
	public Response getUnidadesCoordenacaoSeat() {
		return response.success().data(service.getUnidadesCoordenacaoSeat()).build();
	}

	@GET
	@Path("unidades-atendimento-regional")
	public Response getUnidadesAtendimentoRegional(@HeaderParam("Authorization") String token) {
		return response.success().data(service.getUnidadesAtendimentoRegionalPor(httpClientGoApi.getMatriculaFuncionario(token))).build();
	}
	
	@GET
	@Path("logs") @Publico
	public Response getLogs(){
		return response.success().data(httpClientGoApi.getLogs()).build();
	}
	
	@GET
	@Path("logs/{filtro}") @Publico
	public Response getLogsComFiltro(@PathParam("filtro") String filtro){
		return response.success().data(httpClientGoApi.getLogsComFiltro(filtro)).build();
	}

	@DELETE
	@Path("delete/logs/{id}") @Publico
	public Response deleteLog(@PathParam("id") String id){
		httpClientGoApi.deleteLog(id);
		return response.success().build();
	}

}
