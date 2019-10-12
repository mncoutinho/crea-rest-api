package br.org.crea.restapi.resources.cadastro;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.CertidaoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.CertidaoService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/certidoes")
public class CertidaoResource {

	@Inject
	ResponseRestApi response;

	@Inject
	CertidaoService service;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@GET
	public Response getCertidaoByIdPessoa(@HeaderParam("Authorization") String token) {

		Long idPessoa = httpClientGoApi.getUserDto(token).getIdPessoa();

		return response.success().data(service.getCertidaoByIdPessoa(idPessoa)).build();

	}
	
	@POST
	@Path("pesquisa")
	@Publico
	public Response getCertidoesByIdPessoaPaginada(PesquisaGenericDto pesquisa) {
		List<CertidaoDto> listDto = service.getListCertidaoByIdPessoaPaginada(pesquisa);
		if (!listDto.isEmpty()) {
			if (pesquisa.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalDeRegistrosDaPesquisa(pesquisa)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			}
		} else {
			return response.information().message("Não existe certidões cadastrados.").build();
		}
	}

	@GET
	@Path("registro/valida")
	@Publico
	public Response validaPessoaParaCertidaoRegistro(@HeaderParam("Authorization") String token) {

		List<String> mensagens = service.validaPessoaParaCertidaoRegistro(httpClientGoApi.getUserDto(token));
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
	}

	@GET
	@Path("atribuicoes/valida")
	@Publico
	public Response validaPessoaParaCertidaoAtribuicao(@HeaderParam("Authorization") String token) {

		List<String> mensagens = service.validaPessoaParaCertidaoAtribuicao(httpClientGoApi.getUserDto(token));
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
	}

	@GET
	@Path("empresa-registro/valida")
	@Publico
	public Response validaPessoaJuridicaParaCertidaoRegistro(@HeaderParam("Authorization") String token) {

		List<String> mensagens = service.validaPessoaJuridicaParaCertidaoRegistro(httpClientGoApi.getUserDto(token));
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
	}

	@GET
	@Path("quadro-tecnico/valida")
	@Publico
	public Response validaQuadroTecnico(@HeaderParam("Authorization") String token) {

		if (service.validaQuadroTecnico(httpClientGoApi.getUserDto(token))) {
			return response.information().message(" Possui irregularidades no Quadro Tecnico .").build();
		} else {
			return response.success().build();
		}
	}

	@GET
	@Path("objeto-social/valida")
	@Publico
	public Response validaObjetoSocial(@HeaderParam("Authorization") String token) {

		if (service.existeObjetoSocial(httpClientGoApi.getUserDto(token))) {
			return response.success().build();
		} else {
			return response.information().message(" Não possui objeto social .").build();
		}
	}
		

}
