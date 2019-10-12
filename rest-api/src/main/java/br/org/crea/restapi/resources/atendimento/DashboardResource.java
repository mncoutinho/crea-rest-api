package br.org.crea.restapi.resources.atendimento;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.atendimento.service.AgendamentoService;
import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.atendimento.dtos.AgendamentoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.CommonsService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/atendimento/dashboard")
public class DashboardResource {

	@Inject
	ResponseRestApi response;

	@Inject
	AgendamentoService service;
	
	@Inject
	CommonsService commonsService;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@POST
	@Path("cria-horarios")
	public Response postWebAgendamento(List<AgendamentoDto> agendamentos, @HeaderParam("Authorization") String token) {

		return response.success().message("dashboard.sucesso").data(service.agendarWeb(agendamentos, httpClientGoApi.getUserDto(token))).build();
	}

	@POST
	@Path("cria-horario-extra")
	public Response postHorarioExtra(AgendamentoDto horario, @HeaderParam("Authorization") String token) {

		return response.success().message("dashboard.sucesso").data(service.criaHorarioExtra(horario, httpClientGoApi.getUserDto(token))).build();

	}

	@POST
	@Path("agendamentos-edicao")
	public Response getAgendamentosParaEditar(PesquisaGenericDto dto, @HeaderParam("Authorization") String token) {

		return response.success().data(service.getAgendamentosParaEdicao(dto)).build();
	}

	@PUT
	@Path("deletar-agendamentos")
	public Response excluirAgendamentos(List<AgendamentoDto> dto, @HeaderParam("Authorization") String token) {

		if (dto.isEmpty()) {
			return response.information().build();
		} else {
			return response.success().message("dashboard.confirmExclusao").data(service.excluirAgendamentos(dto)).build();
		}
	}

	@POST
	@Path("consulta-agendamento-futuro")
	public Response getAgendamentosFuturos(PesquisaGenericDto dto, @HeaderParam("Authorization") String token) {

		return response.success().data(service.getAgendamentosFuturos(dto)).build();
	}

	@POST
	@Path("duplica-agendamento")
	public Response duplicaAtendamento(AgendamentoDto dto, @HeaderParam("Authorization") String token) {

		service.duplica(dto, httpClientGoApi.getUserDto(token));
		return response.success().message("agendamento.duplicado").build();

	}
	
	@PUT
	@Path("apagar-todos-agendados")
	public Response apagarAgendadosEdicao(PesquisaGenericDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("dashboard.confirmExclusao").data(service.apagarAgendadosEdicao(dto)).build();
	}
	
	@GET
	@Path("assuntos-mobile/{tipo_pessoa}")
	public Response getAssuntosMobileParaHorarioExtra(@PathParam("tipo_pessoa") String tipoPessoa) {
		return response.success().data(service.getAssuntosMobileParaHorarioExtra(tipoPessoa)).build();
	}

	@GET
	@Path("cpf-cnpj-valido/{cpfOuCnpj}")
	@Publico
	public Response testaCpfOuCnpj(@PathParam("cpfOuCnpj") String cpfOuCnpf){
		
		if (!commonsService.validaFormatoCpfOuCnpj(cpfOuCnpf)) {
			return response.error().message("Cpf ou Cnpj Inválido").build();
		}else {
			return response.success().build();
		}
	}
}
