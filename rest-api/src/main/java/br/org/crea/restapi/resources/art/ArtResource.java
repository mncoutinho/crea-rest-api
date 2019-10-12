package br.org.crea.restapi.resources.art;

import java.io.IOException;
import java.util.List;

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
import javax.xml.parsers.ParserConfigurationException;

import br.org.crea.art.service.ArtService;
import br.org.crea.art.service.FinalizaArtService;
import br.org.crea.commons.annotations.EhAutorDaArt;
import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.art.dtos.ArtMinDto;
import br.org.crea.commons.models.art.dtos.PesquisaArtDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.art.ContratoArtService;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.commons.util.StringUtil;

@Resource
@Path("/art")
public class ArtResource {

	@Inject
	ResponseRestApi response;

	@Inject
	ArtService service;
	
	@Inject
	HttpClientGoApi httpClientGoApi;
	
	@Inject
	FinalizaArtService finalizaArtService; 
	
	@Inject
	ContratoArtService contratoService;

	@POST
	@Path("rascunhos")
	@Publico
	public Response getListRascunhos(PesquisaArtDto pesquisa) {
		return response.success().data(service.getListRascunhos(pesquisa)).build();
	}
	
	@POST
	@Path("modelos")
	@Publico
	public Response getListModelos(PesquisaArtDto pesquisa) {
		return response.success().data(service.getListModelos(pesquisa)).build();
	}
	
	@POST
	@Path("exigencias")
	public Response getListExigencias(PesquisaArtDto pesquisa) {
		return response.success().data(service.getListExigencias(pesquisa)).build();
	}
	
	@GET
	@Path("{numero}")
	@Publico
	public Response getPorNumero(@PathParam("numero") String numero) {
		ArtDto art = service.getArtPor(numero);
		return response.success().data(art).build();
	}
	
	@GET
	@Path("formulario/{numero}")
	public Response getPorNumeroParaFormulario(@PathParam("numero") String numero, @HeaderParam("Authorization") String token) {
		ArtDto art = service.getArtFormularioPor(numero);
		
		if (service.validaEdicaoArt(art, httpClientGoApi.getUserDto(token))) {
			return response.success().data(art).build();
		} else {
			httpClientGoApi.geraLog("ArtResource || getPorNumeroParaFormulario", StringUtil.convertObjectToJson(numero + "--" + httpClientGoApi.getUserDto(token)), new Exception("Erro ao abrir ART para editar"));
			return response.error().build();
		}
	}

	@POST
	@Path("pesquisa")
	@Publico
	public Response pesquisaARTs(PesquisaArtDto pesquisa) {
		
		List<ArtMinDto> listDto = service.pesquisaARTs(pesquisa);
		
		if (!listDto.isEmpty()) {
			if (pesquisa.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalDeRegistrosDaPesquisa(pesquisa)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			}
		} else {
			return response.information().message("Não existem contratos cadastrados").build();
		}
		
	}
	
	
	@GET
	@Path("profissional/{idProfissional}")
	@Publico
	public Response getPorProfissional(@PathParam("idProfissional") Long idProfissional) {
		List<String> arts = service.getArtPorProfissional(idProfissional);
		if (arts.size() == 0) {
			return response.error().message("art.notExist").build();
		} else {
			return response.success().data(arts).build();
		}
	}

	@GET
	@Path("exigencia/profissional/{idProfissional}")
	@Publico
	public Response getEmExigenciaPorProfissional(@PathParam("idProfissional") Long idProfissional) {
		List<String> arts = service.getArtEmExigenciaPorProfissional(idProfissional);
		if (arts.size() == 0) {
			return response.information().message("art.notExist").build();
		} else {
			return response.success().data(arts).build();
		}
	}

	@GET
	@Path("empresa/{idEmpresa}")
	@Publico
	public Response getPorEmpresa(@PathParam("idEmpresa") Long idEmpresa) {
		List<String> arts = service.getArtPorEmpresa(idEmpresa);
		if (arts.size() == 0) {
			return response.error().message("art.notExist").build();
		} else {
			return response.success().data(arts).build();
		}
	}

	@GET
	@Path("detalhado/{numeroArt}")
	@Publico
	public Response getDetalhado(@PathParam("numeroArt") String numeroArt) {

		ArtDto dto = service.getDetalhado(numeroArt);
		if (dto == null) {
			return response.error().message("art.notExist").build();
		} else {
			return response.success().data(dto).build();
		}
	}

	@POST
	@Publico
	public Response listaArts(PesquisaArtDto pesquisa) {
		return response.success().data(service.listaArts(pesquisa)).build();
	}

	@POST
	@Path("nova/{idNatureza}")
	public Response novaArt(@HeaderParam("Authorization") String token, @PathParam("idNatureza") Long idNatureza) {
		return response.success().data(service.novaArt(httpClientGoApi.getUserDto(token), idNatureza)).build();
	}
	
	@POST
	@Path("nova/modelo")
	public Response novaArt(@HeaderParam("Authorization") String token, ArtDto dto) {
		return response.success().data(service.novaArtModelo(httpClientGoApi.getUserDto(token), dto)).build();
	}
	
	@POST
	@Path("modelo/aplicar/{numeroModelo}")
	public Response aplicarModeloArt(@HeaderParam("Authorization") String token, @PathParam("numeroModelo") String numeroModelo) {
		return response.success().data(service.aplicarModeloArt(httpClientGoApi.getUserDto(token), numeroModelo)).build();
	}
	
	@DELETE
	@Path("modelo/{numeroArt}")
	public Response deletaModeloArt(@HeaderParam("Authorization") String token, @PathParam("numeroArt") String numeroArt) {
		return response.success().data(service.deletaArtModelo(httpClientGoApi.getUserDto(token), numeroArt)).build();
	}
	
	@PUT
	@Path("modelo/descricao")
	public Response atualizaDescricaoModelo(DomainGenericDto dto) {
		if (service.atualizaDescricaoModelo(dto)) {
			return response.success().data(dto).build();
		} else {
			return response.error().message("Erro ao gravar descrição do modelo, favor tentar novamente.").build();
		}
	}

	@PUT
	@Path("natureza")
	public Response setNatureza(@HeaderParam("Authorization") String token,DomainGenericDto dto) {
		return response.success().data(service.atualizaNatureza(dto,httpClientGoApi.getUserDto(token))).build();
	}

	@PUT
	@Path("fato-gerador")
	public Response setFatoGerador(DomainGenericDto dto) {
		dto.setDescricao("");
		service.atualizaDescricaoFatoGerador(dto);
		return response.success().data(service.atualizaFatoGerador(dto)).build();
	}
	
	@PUT
	@Path("descricao-fato-gerador")
	public Response setDescricaoFatoGerador(DomainGenericDto dto) {
		if(service.atualizaDescricaoFatoGerador(dto)) {
			return response.success().build();
		}
		return response.error().message("Erro ao gravar descrição do fato gerador, o texto não deve ultrapassar 30 caracteres").build();		
	}

	@PUT
	@Path("tipo/{numeroArt}")
	@EhAutorDaArt()
	public Response setTipo(@PathParam("numeroArt") String numeroArt, DomainGenericDto dto) {
		dto.setNome("");
		service.setNumeroArtPrincipal(dto);
		return response.success().data(service.atualizaTipo(dto)).build();
	}

	@PUT
	@Path("participacao-tecnica")
	public Response setParticipacaoTecnica(DomainGenericDto dto) {
		dto.setNome("-");
		service.setNumeroArtParticipacaoTecnica(dto);
		return response.success().data(service.atualizaParticipacaoTecnica(dto)).build();
	}
	
	@PUT
	@Path("primeira-participacao-tecnica")
	public Response setPrimeiraParticipacaoTecnica(DomainGenericDto dto) {
		dto.setNome("");
		service.setNumeroArtParticipacaoTecnica(dto);
		service.atualizaPrimeiraParticipacaoTecnica(dto);
		return response.success().build();
	}
	
	
	@PUT
	@Path("numero-art-principal")
	public Response setNumeroArtPrincipal(DomainGenericDto dto, @HeaderParam("Authorization") String token) {
		if (!service.validarSeNumeroArtPrincipalEhValido(dto)) {
			dto.setNome("");
			service.setNumeroArtPrincipal(dto);
			return response.information().data("Favor digitar um Número de ART Principal válido.").build();
		} else {
			if (service.verificaSeSubstitutaNaoEhDoProprio(dto, httpClientGoApi.getUserDto(token))) {
				return response.information().data("Não é possível substituir a ART de outro profissional.").build();
			}
		}
		if(dto.getId().equals(1l)) {
			service.setNumeroArtPrincipal(dto);
			return response.success().data(contratoService.carregarDadosDaArtPrincipalComplementarParaOContratoAtual(dto)).build();
		}
		return response.success().data(service.setNumeroArtPrincipal(dto)).build();
	}
	
	@PUT
	@Path("numero-art-participacao-tecnica")
	public Response setNumeroArtParticipacaoTecnica(DomainGenericDto dto, @HeaderParam("Authorization") String token) {
		if (!service.validarSeNumeroArtParticipacaoTecnicaEhValido(dto)) {
			dto.setNome("");
			service.setNumeroArtParticipacaoTecnica(dto);
			return response.information().data("Favor digitar um Número de ART Participação técnica válida. Favor se atentar as regras. Profissional não pode ser o mesmo.").build();
		}
		return response.success().data(service.setNumeroArtParticipacaoTecnica(dto)).build();
	}

	@PUT
	@Path("empresa-contratado")
	public Response setEmpresaContratado(DomainGenericDto dto) {
		return response.success().data(service.setEmpresaContratado(dto)).build();
	}

	@PUT
	@Path("profissional-contratado")
	public Response setProfissionalContratado(DomainGenericDto dto) {
		return response.success().data(service.setProfissionalContratado(dto)).build();
	}

	@PUT
	@Path("entidades-classe")
	public Response setEntidadesClasse(DomainGenericDto dto) {
		return response.success().data(service.setEntidadesClasse(dto)).build();
	}
	
	@PUT
	@Path("finalizar/{numero}")
	public Response finalizar(@PathParam("numero") String numero, @HeaderParam("Authorization") String token) {
		
		ArtMinDto dto = finalizaArtService.finaliza(numero, httpClientGoApi.getUserDto(token));
		
		return dto != null ? response.success().data(dto).build() : response.error().build();
		
	}
	
	@PUT
	@Path("baixa")
	public Response baixaArt(ArtDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.baixaArt(dto, httpClientGoApi.getUserDto(token))).build();
	}
	
	@GET
	@Path("valor/{numero}")
	public Response getValorArt(@PathParam("numero") String numero) {
		return response.success().data(service.getValorArt(numero)).build();
	}
	
	@GET
	@Path("preview/{numero}/{tipo}") 
	@Publico
	public Response previewArt(@Context HttpServletRequest request, @PathParam("numero") String numero,@PathParam("tipo") String tipo) throws IllegalArgumentException, IllegalAccessException, IOException, ParserConfigurationException {
		return service.previewArt(request,numero,tipo);
	}
	
	@GET
	@Path("acervo")
	@Publico
	public Response getArtsAcervoTecnico(@HeaderParam("Authorization") String token) {

		return response.success().data(service.getArtsAcervoTecnico(httpClientGoApi.getUserDto(token))).build();

	}

}
