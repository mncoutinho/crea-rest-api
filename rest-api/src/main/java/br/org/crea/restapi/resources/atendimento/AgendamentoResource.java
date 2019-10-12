package br.org.crea.restapi.resources.atendimento;

import java.util.ArrayList;
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
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.atendimento.AgendamentoMobile;
import br.org.crea.commons.models.atendimento.dtos.AgendamentoDto;
import br.org.crea.commons.models.atendimento.dtos.HorariosDisponiveisDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/atendimento/agendamentos")
public class AgendamentoResource {

	@Inject
	ResponseRestApi response;

	@Inject
	AgendamentoService service;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@POST
	@Path("horarios-disponiveis")
	public Response horariosDisponiveis(PesquisaGenericDto dto) {

		List<HorariosDisponiveisDto> listHorariosDisponiveis = new ArrayList<HorariosDisponiveisDto>();

		if (dto.getData() != null) {
			listHorariosDisponiveis = service.horariosDisponiveis(dto);

			if (listHorariosDisponiveis.isEmpty()) {
				return response.information().build();
			} else {
				return response.success().data(service.horariosDisponiveis(dto)).build();
			}
		} else {
			return response.information().build();
		}

	}

	@POST
	@Path("recupera-horario-disponivel")
	public Response recuperaHorarioDisponivel(AgendamentoDto dto, @HeaderParam("Authorization") String token) {

		return response.success().data(service.recuperaHorarioDisponivel(dto)).build();

	}

	@GET
	@Path("agendados")
	public Response getAgendado(@HeaderParam("Authorization") String token) {
		String cpfOuCnpj = httpClientGoApi.getCpfOuCnpj(token);
		if (cpfOuCnpj != null) {
			return response.success().data(service.getAgendados(cpfOuCnpj)).build();
		} else {
			return response.error().message("Recurso inválido para essa solicitação!").build();
		}
	}

	@PUT
	@Path("novo")
	public Response postAgendamento(AgendamentoDto dto, @HeaderParam("Authorization") String token) {

		List<AgendamentoMobile> agendamentosDisponivel = new ArrayList<AgendamentoMobile>();

		if (service.podeAgendar(dto, httpClientGoApi.getCpfOuCnpj(token))) {

			agendamentosDisponivel = service.verificaAgendamentoDsiponivel(dto);

			if (!agendamentosDisponivel.isEmpty()) {

				dto.setId(agendamentosDisponivel.get(0).getId());
				dto.geraSenha();

				return response.success().message("agendamento.sucesso").data(service.agendaHorarioNovo(dto, httpClientGoApi.getUserDto(token))).build();
			} else {
				return response.information().message("agendamento.jaExiste").build();
			}
		} else {
			return response.error().message("agendamento.jaCriadoNoDia").build();
		}

	}

	@PUT
	@Path("agenda-cliente")
	public Response agendaCliente(AgendamentoDto dto, @HeaderParam("Authorization") String token) {

		if (service.podeAgendar(dto, httpClientGoApi.getCpfOuCnpj(token))) {
			return response.success().message("agendamento.sucesso").data(service.agendaHorarioNovo(dto, httpClientGoApi.getUserDto(token))).build();
		} else {
			return response.information().message("agendamento.jaExiste").build();
		}
	}

	@POST
	@Path("cancela")
	public Response cancelaAgendamento(AgendamentoDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("agendamento.cancela").data(service.cancelar(dto)).build();
	}

	@GET
	@Path("assuntos-mobile")
	public Response getAssuntosMobile(@HeaderParam("Authorization") String token) {
		return response.success().data(service.getAssuntosMobile(httpClientGoApi.getUserDto(token))).build();
	}
	
	@GET
	@Path("valida-pagamento-registro/{idAssunto}")
	public Response validaPagamentoRegistro(@PathParam("idAssunto") Long idAssunto,  @HeaderParam("Authorization") String token) {
	
		if(service.validaPagamentoRegistro(idAssunto, httpClientGoApi.getUserDto(token))){
			return response.success().build();
		}else{
			return response.information().build();
		}
	}
	
	@POST
	@Path("emitir-taxa")
	public Response agendamentoEmitirTaxa(GenericDto dto,  @HeaderParam("Authorization") String token) {
		service.agendamentoEmitirTaxa(dto, httpClientGoApi.getUserDto(token));
		return response.success().build();
	}

}
