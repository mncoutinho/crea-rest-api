package br.org.crea.restapi.resources.art;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.art.ValidaArtService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/art/valida")
public class ValidaArtResource {
	
	@Inject
	ResponseRestApi response;
	
	@Inject
	ValidaArtService service;
	
	@Inject
	HttpClientGoApi httpClientGoApi;
	
	
	@GET
	@Path("contratos/preenchimento-contratante/{idContrato}")
	public Response validaPreenchimentoContratanteDoContrato(@PathParam("idContrato") String idContrato) {
		List<String> mensagens = service.validaONaoPreenchimentoDoContratanteDoContrato(idContrato);
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
		
	}
	
	@GET
	@Path("contratos/preenchimento-dados-iniciais/{idContrato}")
	public Response validaPreenchimentoDadosIniciaisDoContrato(@PathParam("idContrato") String idContrato) {
		List<String> mensagens = service.validaONaoPreenchimentoDosDadosIniciaisObrigatoriosDoContrato(idContrato);
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
		
	}
	
	@GET
	@Path("contratos/preenchimento-dados-tecnicos/{idContrato}")
	public Response validaPreenchimentoDadosTecnicosDoContrato(@PathParam("idContrato") String idContrato) {
		List<String> mensagens = service.validaONaoPreenchimentoDosDadosTecnicosObrigatoriosDoContrato(idContrato);
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
	}
	
	@GET
	@Path("contratos/registros-periodo-contrato/{idContrato}")
	public Response validaRegistrosDoProfissionalEEmpresaNoPeriodoDoContrato(@PathParam("idContrato") String idContrato) {
		List<String> mensagens = service.validaRegistrosDoProfissionalEEmpresaNoPeriodoDoContrato(idContrato);
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
	}
	

	
	@GET
	@Path("contratos/preenchimento-proprietario/{idContrato}")
	public Response validaPreenchimentoProprietarioDoContrato(@PathParam("idContrato") String idContrato) {
		List<String> mensagens = service.validaONaoPreenchimentoDoEnderecoDoProprietarioDoContrato(idContrato);
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
		
	}
	
	@GET
	@Path("contratos/preenchimento-endereco-contrato/{idContrato}")
	public Response validaPreenchimentoEnderecoDoContrato(@PathParam("idContrato") String idContrato) {
		List<String> mensagens = service.validaONaoPreenchimentoDoEnderecoDoContrato(idContrato);
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
		
	}
	
	@GET
	@Path("contratos/preenchimento-acessibilidade-arbitragem/{idContrato}")
	public Response validaPreenchimentoAcessibilidadeArbitragem(@PathParam("idContrato") String idContrato) {
		List<String> mensagens = service.validaPreenchimentoAcessibilidadeArbitragem(idContrato);
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
		
	}
	
	
	@GET
	@Path("contratos/se-todos-os-contratos-estao-finalizados/{numeroArt}")
	public Response validaSeTodosOsContratosEstaoFinalizados(@PathParam("numeroArt")  String numeroArt) {
		if(service.verificaSeTodosOsContratosEstaoFinalizados(numeroArt)) {
			return response.success().build();
		} else {
			return response.information().data("Favor finalizar todos os contratos antes de prosseguir para finalizar a ART!").build();
		}
		
	}
	
	@GET
	@Path("finalizar/{numeroArt}")
	public Response validaFinalizarArt(@PathParam("numeroArt")  String numeroArt) {
		
		if(service.verificaSeTodosOsContratosEstaoFinalizados(numeroArt)) {
			List<String> mensagens = service.validaFinalizarArt(numeroArt);
			if (mensagens.size() > 0) {
				return response.error().messages(mensagens).build();
			} else {
				return response.success().build();
			}	
		} else {
			return response.information().data("Favor finalizar todos os contratos antes de prosseguir para finalizar a ART!").build();
		}			
	}

}
