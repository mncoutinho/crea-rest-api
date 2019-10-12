package br.org.crea.restapi.resources.cadastro;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.factory.atendimento.LogAtendimentoFactory;
import br.org.crea.commons.models.atendimento.enuns.TipoAtendimentoEnum;
import br.org.crea.commons.models.cadastro.dtos.empresa.QuadroTecnicoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.EntidadeProfissionalDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.ProfissionalDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.ProfissionalHomologacaoDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.QuadroTecnicoProfissionalDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.TituloProfissionalDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.profissional.ProfissionalService;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.QuadroTecnicoService;
import br.org.crea.corporativo.service.ResponsavelTecnicoService;
import br.org.crea.portal.service.AtendimentoService;

@Resource
@Path("/cadastro/profissional")
public class ProfissionalResource {

	@Inject
	HttpClientGoApi httpClientGoApi;
	
	@Inject
	ResponseRestApi response;
	
	@Inject
	QuadroTecnicoService quadroTecnicoService;

	@Inject
	ResponsavelTecnicoService responsavelTecnicoService;
	
	@Inject
	ProfissionalService service;
	
	@Inject
	AtendimentoService atendimentoService;
	
	@Inject
	LogAtendimentoFactory logAtendimentoFactory;

	@POST
	@Path("responsabilidade-tecnica")
	@Publico
	public Response getResponsavelTecnicoProfissional(PesquisaGenericDto pesquisa) {
		return response.success().data(responsavelTecnicoService.getResponsabilidadeTecnica(pesquisa)).build();
	}
	
	@POST
	@Path("quadro-tecnico")
	@Publico
	public Response getQuadroTecnicoByProfissional(PesquisaGenericDto pesquisa) {
		List<QuadroTecnicoDto> listaQuadroTecnico = service.getQuadroTecnicoByIdProfissional(pesquisa);
		if (listaQuadroTecnico.size() > 0) {
			return response.success().data(listaQuadroTecnico).build();
		} else {
			return response.information().message("Não foi encontrado quadro técnico para este profissional").build();
		}		
	}
	
	@POST
	@Path("consulta-por-nome")
	@Publico
	public Response buscaProfissionalByNome(PesquisaGenericDto dto) {
		
		List<ProfissionalDto> listDto = new ArrayList<ProfissionalDto>();
		listDto = service.buscaListProfissionalByNome(dto);
		if (!listDto.isEmpty()) {
			if (dto.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalbuscaProfissionalByNome(dto)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			} 
		} else {
			return response.error().message("interessado.notExist").build();
		}
	}
	
	@GET
	@Path("consulta-por-cpf/{numeroCPF}")
	@Publico
	public Response buscaProfissionalByCNPJ(@PathParam("numeroCPF") String numeroCPF) {

		List<ProfissionalDto> listDto = new ArrayList<ProfissionalDto>();
		listDto = service.buscaProfissionalByCPF(numeroCPF);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}	
		

	}
	
	
	@GET
	@Path("consulta-por-registro/{numeroRegistro}")
	@Publico
	public Response buscaProfissionalByRegistro(@PathParam("numeroRegistro") String numeroRegistro) {

		List<ProfissionalDto> listDto = new ArrayList<ProfissionalDto>();
		listDto = service.buscaProfissionalByRegistro(numeroRegistro);
	
		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}	

	}
	
	
	@GET
	@Path("consulta-por-rnp/{numeroRnp}")
	@Publico
	public Response buscaProfissionalByRNP(@PathParam("numeroRnp") String numeroRnp) {

		List<ProfissionalDto> listDto = new ArrayList<ProfissionalDto>();
		listDto = service.buscaProfissionalByRNP(numeroRnp);
	
		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}	

	}
	
	@GET
	@Path("busca-titulos/{registroProfissional}") @Publico
	public Response getTitulosProfissional(@PathParam("registroProfissional") Long registroProfissional) {

		List<TituloProfissionalDto> listDto = service.buscaTitulosProfissionalPor(registroProfissional);
	
		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().message("Nenhum título cadastrado para o registro informado.").build();
		}	
	}
	
	@GET
	@Path("detalhar/{numeroRegistro}")
	@Publico
	public Response buscaDetalhadaProfissionalByRegistro(@PathParam("numeroRegistro") String numeroRegistro) {

		List<ProfissionalDto> listDto = new ArrayList<ProfissionalDto>();
		listDto = service.buscaDetalhadaProfissionalByRegistro(numeroRegistro);
	
		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.error().message("interessado.notExist").build();
		}	
	}

	/**
	 * Recurso útil para retornar profissionais para homologar de acordo com modalidade titulo
	 * @author Monique Santos
	 * @since 05/2018
	 * @param dto - dto.getId(): Deve trazer o id da modalidade de acordo com o Departamento(câmara) da reunião
	 *              dto.getPeriodo(): Deve corresponder a data da reunião de câmara    	
	 * */
	
	@POST
	@Path("registros-homologacao")
	public Response buscaProfissionaisParaHomologacaoNoPeriodo(DomainGenericDto dto) {
		
		List<ProfissionalHomologacaoDto> result = service.buscaProfissionaisParaHomologacaoNoPeriodo(dto.getId(), dto.getPeriodo());
		return result.isEmpty() ? response.information().message("Nenhum registro encontrado da modalidade para homologaçao!").build() : response.success().data(result).build();
	}
	
	/**
	 * Recurso responsável por atualizar os profissionais que já foram homologados na pauta da reunião de câmara
	 * Deve ser acionado após a geração da listagem do profissionais, disponível no recurso buscaProfissionaisParaHomologacaoNoPeriodo(DomainGenericDto dto)
	 * @author Monique Santos
	 * @since 05/2018
	 * @param dto - dto.getId(): Deve trazer o id da modalidade de acordo com o Departamento(câmara) da reunião
	 *              dto.getPeriodo(): Deve corresponder a data da reunião de câmara    	
	 * */
	
	@POST
	@Path("atualiza-titulos-incluidos-homologacao")
	public Response atualizaTitulosIncluidosHomologacao(List<ProfissionalHomologacaoDto> listDto) {
		service.atualizaProfissionaisParaHomologacao(listDto);
		return response.success().message("Titulos para homologação atualizados com sucesso!").build();
	}
	
	@POST
	@Path("excluir-homologacao-profissional")
	public Response excluirHomologacaoProfissional(GenericDto dto) {
		service.excluirHomologacaoProfissional(dto);
		return response.success().message("Titulos para homologação atualizados com sucesso!").build();
	}
	
	@POST
	@Path("atualizar-numero-documento-profissional")
	public Response atualizarNumeroDocumentoHomologacao(GenericDto dto) {
		return response.success().data(service.atualizarNumeroDocumentoHomologacao(dto)).build();
	}
	
	@POST
	@Path("recupera-profissionais-por-data-modalidade")
	public Response recuperaProfissionaisDataModalidade(GenericDto dto) {
		return response.success().data(service.recuperaProfissionaisDataModalidade(dto)).build();
	}

	@POST
	@Path("habilitar-titulo")
	public Response habilitarTitulo(@HeaderParam("Authorization") String token, TituloProfissionalDto dto) {
		
		if ( service.existeOpcaoDeVotoNoAnoAtual(httpClientGoApi.getUserDto(token))) {
			return response.information().message("O profissional já teve título habilitado este ano.").build();
		}
		if ( service.verificarSeHaModalidadesDoMesmoTipo(httpClientGoApi.getUserDto(token), dto) == 0) {
			return response.information().message(" Não há nenhum Título com a mesma modalidade de qualquer entidade acima.").build();
		}
		if (service.verificaSeTituloPodeSerHabilitado(httpClientGoApi.getUserDto(token), dto) == 0) {
			return response.information().message(" Título escolhido não possui a mesma modalidade da entidade habilitada.").build();
		}
		try {
			service.habilitartituloProfissional(httpClientGoApi.getUserDto(token), dto);
		} catch (Exception e) {
			return response.information().message(" Não foi possivel habilitar o título").build();
		}
		logAtendimentoFactory.cadastraLogAtendimento(httpClientGoApi.getUserDto(token), TipoAtendimentoEnum.HABILITACAO_TITULO_ENTIDADE_FILIADA);
		return response.success().build();
	}
	
	@GET
	@Path("participa-catalogo/{idProfissional}")
	public Response getParticipaCatalogoProfissional(@PathParam("idProfissional") Long idProfissional) {
		return response.success().data(service.getParticipaCatalogoProfissional(idProfissional)).build();
	}
	
	@POST
	@Path("participa-catalogo")
	public Response participaCatalogoProfissional(DomainGenericDto dto) {
		return response.success().data(service.participaCatalogoProfissional(dto)).build();
	}

	@GET
	@Publico
	@Path("valida-entidade-filiada/{idProfissional}")
	public Response validaEntidadesFiliadas(@PathParam("idProfissional") Long idProfissional) {
		
		return response.success().data(service.validaEntidadesFiliadas(idProfissional)).build();

	}
	
	@POST
	@Path("habilitar-opcao-voto")
	@Publico
	public Response habilitarOpcaoVoto(@HeaderParam("Authorization") String token, EntidadeProfissionalDto dto) {
		String mensagemErro = service.podeHabilitarOpcaoVoto(dto);
				
		if (mensagemErro != null) {
			return response.information().message(mensagemErro).build();
		}
		return response.success().build();
	}
	
	@POST
	@Path("baixar-rt")
	@Publico
	public Response baixarRt(@HeaderParam("Authorization") String token, QuadroTecnicoProfissionalDto dto) {
		responsavelTecnicoService.baixaResponsavelTecnico(dto);

		return response.success().build();
	}
	
	@POST
	@Path("baixar-qt")
	@Publico
	public Response baixarQt(@HeaderParam("Authorization") String token, QuadroTecnicoProfissionalDto dto) {
		quadroTecnicoService.baixarQt(dto);

		return response.success().build();
	}
}
