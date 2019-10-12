package br.org.crea.restapi.resources.atendimento;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.atendimento.service.OuvidoriaService;
import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaAssuntoEspecificoDto;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaAssuntoGeraldto;
import br.org.crea.commons.models.atendimento.dtos.OuvidoriaDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/atendimento/ouvidoria")
public class OuvidoriaResource {

	@Inject
	ResponseRestApi response;

	@Inject
	OuvidoriaService service;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@POST
	@Publico
	public Response salvar(OuvidoriaDto dto) {
		OuvidoriaDto ouvidoriaDto = service.salvar(dto);
		if (service.salvar(dto) != null) {
			return response.success().message("Cadastro realizado com sucesso!").data(ouvidoriaDto).build();
		}
		return response.information().message("Não foi possível cadastrar!").build();
	}

	@POST
	@Path("pesquisa")
	@Publico
	public Response pesquisaAtendimentoOuvidoriaPaginado(PesquisaGenericDto pesquisa) {
		List<OuvidoriaDto> listDto = service.getByOuvidoriaAtendimentoPaginado(pesquisa);
		if (!listDto.isEmpty()) {
			if (pesquisa.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalDeRegistrosDaPesquisa(pesquisa)).data(listDto)
						.build();
			} else {
				return response.success().data(listDto).build();
			}
		} else {
			return response.information().message("Não existem atendimentos cadastrados.").build();
		}
	}
	
	@POST
	@Path("pesquisa-publica")
	@Publico
	public Response pesquisaAtendimentoOuvidoriaPublica(PesquisaGenericDto pesquisa) {
		List<OuvidoriaDto> listDto = service.getOuvidoriaPublica(pesquisa);
		return response.success().totalCount(service.getTotalRegistrosPesquisaPublica(pesquisa)).data(listDto).build();
	}

	@GET
	@Path("tipoDemanda")
	@Publico
	public Response getAllTipoDemanda() {
		List<DomainGenericDto> listTipoDemanda = service.getAllTipoDemanda();
		return response.success().data(listTipoDemanda).build();
	}
	
	@GET
	@Path("assuntosGerais")
	@Publico
	public Response getAllAssuntosGerais() {
		List<OuvidoriaAssuntoGeraldto> listAssuntosGerais = service.getAllAssuntosGerais();
		return response.success().data(listAssuntosGerais).build();
	}
	
	@GET
	@Path("assuntosEspecifico/{assuntosGerais}")
	@Publico
	public Response getAllAssuntosEspecificos(@PathParam("assuntosGerais") Long assuntosGerais) {
		List<OuvidoriaAssuntoEspecificoDto> listAssuntosEspecificos = service.getAllAssuntosEspecificos(assuntosGerais);
		return response.success().data(listAssuntosEspecificos).build();
	}
	@GET
	@Path("situacao")
	@Publico
	public Response getAllSituacao() {
		List<DomainGenericDto> listSituacao = service.getSituacao();
		return response.success().data(listSituacao).build();
	}
	
	@POST
	@Path("arquivo")
	@Publico
	public Response anexaArquivoOcorrencia(OuvidoriaDto dto) {
		service.anexaArquivoOcorrencia(dto);
		return response.success().data(dto).build();
	}
	
}
