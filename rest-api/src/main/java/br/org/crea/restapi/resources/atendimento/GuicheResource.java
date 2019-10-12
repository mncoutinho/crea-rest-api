package br.org.crea.restapi.resources.atendimento;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.crea.atendimento.service.GuicheService;
import br.org.crea.commons.annotations.Funcionario;
import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.atendimento.dtos.AgendamentoDto;
import br.org.crea.commons.models.atendimento.dtos.PainelAtendimentoDto;
import br.org.crea.commons.models.atendimento.dtos.PesquisaAtendimentoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/atendimento")
public class GuicheResource {

	@Inject
	ResponseRestApi response;

	@Inject
	GuicheService service;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@POST
	@Path("fila-do-dia")
	public Response getFilaDoDia(PesquisaGenericDto pesquisa) {
		return response.success().data(service.getFilaDoDia(pesquisa)).build();
	}

	@POST
	@Path("guiche/fila-andamento")
	public Response getFilaEmAndamento(PesquisaGenericDto pesquisa, @HeaderParam("Authorization") String token) {
		return response.success().data(service.getFilaEmAndamento(pesquisa, httpClientGoApi.getIdFuncionario(token))).build();
	}

	@POST
	@Path("guiche/listar-atendidos-dia")
	public Response getAtendidosDoDia(PesquisaGenericDto pesquisa, @HeaderParam("Authorization") String token) {
		return response.success().data(service.getListaAtendidosDoDia(pesquisa, httpClientGoApi.getIdFuncionario(token))).build();
	}

	@PUT
	@Path("recepcao/confirmacao-presenca") @Funcionario
	public Response confirmarPresenca(AgendamentoDto dto, @HeaderParam("Authorization") String token) {
		Long idFuncionario = httpClientGoApi.getIdFuncionario(token);
		return response.success().message("atendimento.confirmaChegadaNoLocal").data(service.confirmarPresenca(dto, idFuncionario)).build();
	}
	
	@POST
	@Path("recepcao/pesquisa")
	public Response pesquisaPresenca(PesquisaAtendimentoDto pesquisa) {
		return response.success().data(service.pesquisaPresenca(pesquisa)).build();
	}
	
	@POST
	@Path("pesquisa")
	public Response pesquisaFiltro(PesquisaGenericDto pesquisa) {
		return response.success().data(service.pesquisaFiltroIndicadores(pesquisa)).build();
	}

	@PUT
	@Path("recepcao/chamar-cliente") @Funcionario
	public Response chamarCliente(AgendamentoDto dto, @HeaderParam("Authorization") String token) {
		Long idFuncionario = httpClientGoApi.getIdFuncionario(token);
		
		if (service.temAtendimentoAindaEmAberto(idFuncionario)) {
			return response.information().message("Você possui algum atendimento em aberto, por favor finalize antes de chamar outro na fila.").build();
		}else if (service.clienteFoiChamado(dto)) {
			return response.information().message("atendimento.clienteChamado").build();
		} else {
			return response.success().data(service.chamarCliente(dto, idFuncionario)).build();
		}
	}

	@PUT
	@Path("guiche/iniciar-atendimento")
	public Response iniciarAtendimento(AgendamentoDto dto, @HeaderParam("Authorization") String token) {

		if (service.clienteFoiCapturado(dto)) {
			return response.information().message("atendimento.clienteCapturado").build();
		} else {
			return response.success().data(service.iniciarAtendimento(dto, httpClientGoApi.getIdFuncionario(token))).build();
		}
	}

	@PUT
	@Path("guiche/liberar-cliente")
	public Response liberarChamada(AgendamentoDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("atendimento.liberarChamada").data(service.liberarChamada(dto, httpClientGoApi.getIdFuncionario(token))).build();
	}

	@PUT
	@Path("guiche/finalizar-atendimento")
	public Response finalizarAtendimento(AgendamentoDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("atendimento.encerrar").data(service.finalizarAtendimento(dto, httpClientGoApi.getIdFuncionario(token))).build();
	}

	@PUT
	@Path("guiche/cancela")
	public Response cancelaAgendamento(AgendamentoDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("atendimento.cancelado").data(service.cancela(dto, httpClientGoApi.getIdFuncionario(token))).build();
	}

	@PUT
	@Path("guiche/confirmacao-ausencia")
	public Response marcarAusencia(AgendamentoDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.marcarAusencia(dto, httpClientGoApi.getIdFuncionario(token))).build();
	}
	
	@GET
	@Path("indicadores") @Publico
	public Response relatorioIndicadores() {
		
		// para testes, definir parametro pesquisa e mudar de get para post e remover @Publico
		PesquisaGenericDto pesquisa = new PesquisaGenericDto();
		pesquisa.setUnidadeAtendimento(new Long(23020804));
		pesquisa.setStatus(new Long(99));
		pesquisa.setDataInicio(DateUtils.generateDate("01/06/2018"));
		pesquisa.setDataFim(DateUtils.generateDate("30/06/2018"));
		pesquisa.setRows(1000);
		pesquisa.setPage(0);
		
		return Response.ok(service.relatorioIndicadores(pesquisa))
				.header("Content-Disposition", "attachment; filename=" + "relatorio-de-agendamentos" + ".pdf" )
				.header("Content-Type", "application/pdf").type(MediaType.APPLICATION_OCTET_STREAM).build();
	}
	
	@PUT
	@Path("guiche/chamar-cliente-painel")
	public Response chamarClientePainel(PainelAtendimentoDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.chamarClientePainel(dto)).build();
	}

}
