package br.org.crea.restapi.resources.commons;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.models.art.dtos.PesquisaContratoDto;
import br.org.crea.commons.service.art.ContratoService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/servicos-engenharia")
public class ServicoEngenhariaResource {

	@Inject
	ResponseRestApi response;

	@Inject
	ContratoService service;

	@POST
	@Path("contratante")
	@Publico
	public Response getServicosPorCondominio(PesquisaContratoDto dto) {
		return response.success().data(service.getServicosPorContratante(dto)).build();
	}

	@POST
	@Path("existe-contrato-atividade")
	@Publico
	public Response verificarExisteContratoAtividade(ContratoServicoDto dto) {
		return service.existeAtividadeParaContratanteInformado(dto) ? response.information().build() : response.success().build();
	}

	@POST
	@Path("salvar")
	public Response salvarServicoEngenharia(ContratoServicoDto dto) {
		
		if (!service.existeAtividadeParaContratanteInformado(dto)) {
			return response.success().data(service.salvar(dto)).message("contrato.salvo").build();
		} else {
			return response.error().message("contrato.duplicado").build();
		}
	}

	@POST
	@Path("substituir")
	public Response substituirServicoEngenharia(ContratoServicoDto dto) {

		if (!service.existeAtividadeParaContratanteInformado(dto)) {
			return response.success().data(service.substituirContratoAtividade(dto)).message("contrato.substituido").build();
		} else {
			return response.error().message("contrato.duplicado").build();
		}
	}
	
	@POST
	@Path("interromper")
	public Response interromperServicoEngenharia(ContratoServicoDto dto) {
		service.interromperContratoAtividade(dto);
		return response.success().message("contrato.interrompido").build();
	}		
}
