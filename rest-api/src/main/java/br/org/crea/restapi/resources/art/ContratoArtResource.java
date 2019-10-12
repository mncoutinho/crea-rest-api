package br.org.crea.restapi.resources.art;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.art.dtos.ContratoArtDto;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.art.ContratoArtService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/art/contratos")
public class ContratoArtResource {
	
	@Inject
	ResponseRestApi response;
	
	@Inject
	ContratoArtService service;
	
	@Inject
	HttpClientGoApi httpClientGoApi;
		
	
	@POST
	@Path("pesquisa")
	public Response getAll(PesquisaGenericDto pesquisa) {

		List<ContratoArtDto> listDto = service.getContratosPor(pesquisa);
		
		if (!listDto.isEmpty()) {
			if (pesquisa.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalDeRegistrosDaPesquisaGetAll(pesquisa)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			}

		} else {
			return response.information().message("Não existem contratos cadastrados").build();
		}

	}
	
	@GET
	@Path("detalhado/{idContrato}")
	public Response getDetalhado(@PathParam("idContrato") String idContrato) {

		ContratoArtDto dto = service.getDetalhado(idContrato);
		if (dto == null) {
			return response.error().message("art.notExist").build();
		} else {
			return response.success().data(dto).build();
		}
	}
	
	@DELETE
	@Path("{idContrato}")
	public Response excluiContrato(@PathParam("idContrato") String idContrato, @HeaderParam("Authorization") String token) {
		service.excluiContrato(idContrato, httpClientGoApi.getUserDto(token));
		return response.success().build();
	}
	
	@POST
	@Publico
	public Response listaContratosPorArt(PesquisaGenericDto pesquisa) {
		List<ContratoServicoDto> listDto = service.listaContratosPorArt(pesquisa);
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
	
	@POST
	@Path("novo")
	public Response novoContrato(DomainGenericDto dto) {
		return response.success().data(service.novoContrato(dto)).build();
	}
	
	@PUT
	@Path("numero-contrato")
	public Response atualizaNumeroContrato(DomainGenericDto dto) {
		if (service.atualizaNumeroContrato(dto)) {
			return response.success().build();
		}
		return response.error().message("Erro ao atualizar o número do contrato, favor tentar novamente").build();
	}
	
	@PUT
	@Path("salario")
	public Response atualizaSalario(DomainGenericDto dto) {
		if (service.atualizaSalario(dto)) {
			return response.success().build();
		} else {
			return response.error().message("Erro ao gravar salário, favor tentar novamente.").build();
		}
	}
	
	@PUT
	@Path("pro-labore")
	public Response atualizaProLabore(DomainGenericDto dto) {
		return response.success().data(service.atualizaProLabore(dto)).build();
	}
	
	@PUT
	@Path("valor-contrato")
	public Response atualizaValorContrato(DomainGenericDto dto) {
		if(service.atualizaValorContrato(dto)) {
			return response.success().build();
		} else {
			return response.error().message("Erro ao gravar valor do contrato, favor tentar novamente.").build();
		}
	}
	
	@PUT
	@Path("nhhjt")
	public Response atualizaNhhjt(DomainGenericDto dto) {
		return response.success().data(service.atualizaNhhjt(dto)).build();
	}
	
	@PUT
	@Path("data-inicio")
	public Response atualizaDataInicio(DomainGenericDto dto) {
		if(service.atualizaDataInicio(dto)) {
			return response.success().build();
		} else {
			return response.error().message("Erro ao gravar valor data de início, favor tentar novamente.").build();
		}
	}
	
	@PUT
	@Path("data-fim")
	public Response atualizaDataFim(DomainGenericDto dto) {
		if(service.atualizaDataFim(dto)) {
			return response.success().build();
		} else {
			return response.error().message("Erro ao gravar valor data fim, favor tentar novamente.").build();
		}
	}
	
	@PUT
	@Path("prazo-determinado")
	public Response atualizaPrazoDeterminado(DomainGenericDto dto) {
		return response.success().data(service.atualizaPrazoDeterminado(dto)).build();
	}
	
	@PUT
	@Path("prazo-mes")
	public Response atualizaPrazoMes(DomainGenericDto dto) {
		if(service.atualizaPrazoMes(dto)) {
			return response.success().build();
		} else {
			return response.error().message("Erro ao gravar valor Prazo em meses, favor tentar novamente.").build();
		}
		
	}
	
	@PUT
	@Path("prazo-dia")
	public Response atualizaPrazoDia(DomainGenericDto dto) {
		if(service.atualizaPrazoDia(dto)) {
			return response.success().build();
		} else {
			return response.error().message("Erro ao gravar valor Prazo em dia, favor tentar novamente.").build();
		}
		
	}
	
	@PUT
	@Path("ramo")
	public Response atualizaRamo(DomainGenericDto dto) {
		return response.success().data(service.atualizaRamo(dto)).build();
	}
	
	@POST
	@Path("atividade")
	public Response atualizaAtividade(DomainGenericDto dto) {
		return response.success().data(service.atualizaAtividade(dto)).build();
	}
	
	@POST
	@Path("especificacao")
	public Response atualizaEspecificacao(DomainGenericDto dto) {
		return response.success().data(service.atualizaEspecificacao(dto)).build();
	}
	
	@POST
	@Path("complemento")
	public Response atualizaComplemento(DomainGenericDto dto) {
		return response.success().data(service.atualizaComplemento(dto)).build();
	}
	
	@PUT
	@Path("quantificacao")
	public Response atualizaQuantificacao(DomainGenericDto dto) {
		return response.success().data(service.atualizaQuantificacao(dto)).build();
	}
	
	@PUT
	@Path("unidade-medida")
	public Response atualizaUnidadeDeMedida(DomainGenericDto dto) {
		return response.success().data(service.atualizaUnidadeDeMedida(dto)).build();
	}
	
	@PUT
	@Path("numero-pavimentos")
	public Response atualizaNumeroPavimentos(DomainGenericDto dto) {
		
		if(service.atualizaNumeroPavimentos(dto)) {
			return response.success().build();
		} else {
			return response.error().message("Erro ao gravar Nº Pavimentos, favor tentar novamente.").build();
		}
		
	}
	
	@PUT
	@Path("convenio-publico")
	public Response atualizaConvenioPublico(DomainGenericDto dto) {
		return response.success().data(service.atualizaConvenioPublico(dto)).build();
	}
	
	@PUT
	@Path("descricao-complementar")
	public Response atualizaDescricaoComplementar(DomainGenericDto dto) {
		if(service.atualizaDescricaoComplementar(dto)) {
			return response.success().build();
		} else {
			return response.error().message("Erro ao gravar descrição complementar, favor tentar novamente.").build();
		}
		
	}
	
	@PUT
	@Path("acessibilidade")
	public Response atualizaAcessibilidade(DomainGenericDto dto) {
		return response.success().data(service.atualizaAcessibilidade(dto)).build();
	}

	@DELETE
	@Path("atividade/{numeroArt}/{idAtividade}")
	public Response deletaAtividade(@PathParam("numeroArt") String numeroArt, @PathParam("idAtividade") String idAtividade) {
		service.deletaAtividade(numeroArt, idAtividade);
		return response.success().build();
	}
	
	@DELETE
	@Path("especificacao/{numeroArt}/{idEspecificacao}")
	public Response deletaEspecificacao(@PathParam("numeroArt") String numeroArt, @PathParam("idEspecificacao") String idEspecificacao) {
		service.deletaEspecificacao(numeroArt, idEspecificacao);
		return response.success().build();
	}
	
	@DELETE
	@Path("complemento/{numeroArt}/{idComplemento}")
	public Response deletaComplemento(@PathParam("numeroArt") String numeroArt, @PathParam("idComplemento") String idComplemento) {
		service.deletaComplemento(numeroArt, idComplemento);
		return response.success().build();
	}
	
	@PUT
	@Path("tipo-unidade-administrativa")
	public Response atualizaTipoUnidadeAdministrativa(DomainGenericDto dto) {
		service.atualizaTipoUnidadeAdministrativa(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("tipo-acao-institucional")
	public Response atualizaTipoAcaoInstitucional(DomainGenericDto dto) {
		service.atualizaTipoAcaoInstitucional(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("tipo-cargo-funcao")
	public Response atualizaTipoCargoFuncao(DomainGenericDto dto) {
		service.atualizaTipoCargoFuncao(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("tipo-funcao")
	public Response atualizaTipoFuncao(DomainGenericDto dto) {
		service.atualizaTipoFuncao(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("descricao-cargo-funcao")
	public Response atualizaDescricaoCargoFuncao(DomainGenericDto dto) {
		if (service.atualizaDescricaoCargoFuncao(dto)) {
			return response.data(dto).success().build();
		} else {
			return response.error().message("Erro ao gravar descrição cargo e função, favor tentar novamente.").build();
		}
		
	}
	
	@PUT
	@Path("tipo-vinculo")
	public Response atualizaTipoVinculo(DomainGenericDto dto) {
		service.atualizaTipoVinculo(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("data-celebracao")
	public Response atualizaDataCelebracao(DomainGenericDto dto) {
		if(service.atualizaDataCelebracao(dto)) {
			return response.success().build();
		} else {
			return response.error().message("Erro ao gravar Data Celebração, favor tentar novamente.").build();
		}
		
	}
	
	@PUT
	@Path("tipo-contratante")
	public Response atualizaTipoContratante(DomainGenericDto dto) {
		service.atualizaTipoContratante(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("finalidade")
	public Response atualizaFinalidade(DomainGenericDto dto) {
		service.atualizaFinalidade(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("codigo-obra-servico")
	public Response atualizaCodigoObraServico(DomainGenericDto dto) {
		service.atualizaCodigoObraServico(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("arbitragem")
	public Response atualizaArbitragem(DomainGenericDto dto) {
		service.atualizaArbitragem(dto);
		return response.success().build();
	}
	
	@PUT
	@Path("contratante")
	public Response atualizaContratante(DomainGenericDto dto) {
		service.atualizaContratante(dto);
		return response.data(dto).success().build();
	}

	@PUT
	@Path("proprietario")
	public Response atualizaProprietario(DomainGenericDto dto) {
		service.atualizaProprietario(dto);
		return response.data(dto).success().build();
	}
	
	@PUT
	@Path("endereco-contrato")
	public Response atualizaEnderecoContrato(DomainGenericDto dto) {
		service.atualizaEnderecoContrato(dto);
		return response.data(dto).success().build();
	}
	
	@PUT
	@Path("endereco-contratante")
	public Response atualizaEnderecoContratante(DomainGenericDto dto) {
		service.atualizaEnderecoContratante(dto);
		return response.data(dto).success().build();
	}
	
	@PUT
	@Path("endereco-proprietario")
	public Response atualizaEnderecoProprietario(DomainGenericDto dto) {
		service.atualizaEnderecoProprietario(dto);
		return response.data(dto).success().build();
	}
	
	@PUT
	@Path("copia-endereco-contratante")
	public Response atualizaEnderecoAPartirDoEnderecoContratante(DomainGenericDto dto) {
		return response.data(service.atualizaEnderecoAPartirDoEnderecoContratante(dto)).success().build();
	}
	
	@PUT
	@Path("calcula-data-fim")
	public Response calculaDataFim(DomainGenericDto dto) {
		service.calculaEAtualizaDataFim(dto);
		return response.data(dto).success().build();
	}
	
	@PUT
	@Path("numero-art-vinculada-contrato")
	public Response setNumeroArtPrincipal(DomainGenericDto dto, @HeaderParam("Authorization") String token) {
		/*
		if (!service.validarSeNumeroArtPrincipalEhValido(dto)) {
			dto.setNome("");
			service.setNumeroArtPrincipal(dto);
			return response.information().data("Favor digitar um Número de ART Principal válido.").build();
		} else {
			if (service.verificaSeSubstitutaNaoEhDoProprio(dto, httpClientGoApi.getUserDto(token))) {
				return response.information().data("Não é possível substituir a ART de outro profissional.").build();
			}
		}
		return response.success().data(service.setNumeroArtPrincipal(dto)).build();
		*/
		if(service.numeroArtVinculadaAoContratoEhValido(dto)) {
			return response.success().data(service.setNumeroArtVinculadaAoContrato(dto)).build();
		} else {
			dto.setNome("");
			service.setNumeroArtVinculadaAoContrato(dto);
			return response.information().data("Número de ART informado não está de acordo com as regras. Favor verificar se a ART existe, se o endereço da obra é o mesmo e se a empresa do responsável técnico é diferente da ART atual.").build();
		}
		
	}
	

}
