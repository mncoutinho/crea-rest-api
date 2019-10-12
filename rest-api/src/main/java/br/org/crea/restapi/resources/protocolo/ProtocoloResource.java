package br.org.crea.restapi.resources.protocolo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.SubstituicaoProtocoloDto;
import br.org.crea.commons.models.protocolo.dtos.TramitacaoProtocoloDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.protocolo.ProtocoloService;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.protocolo.service.TramiteProtocoloService;

@Resource
@Path("/protocolo")
public class ProtocoloResource {

	@Inject
	ResponseRestApi response;

	@Inject
	ProtocoloService service;

	@Inject
	TramiteProtocoloService tramiteService;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@Context
	HttpServletRequest request;

	@GET
	@Path("{protocolo}")
	@Publico
	public Response getProtocolo(@PathParam("protocolo") Long numeroProtocolo) {
		ProtocoloDto dto = new ProtocoloDto();
		dto = service.getProtocoloById(numeroProtocolo);

		if (dto != null) {
			return response.success().data(dto).build();
		} else {
			return response.error().message("protocolo.notExist").build();
		}
	}
	
	@GET
	@Path("existe/{idProtocolo}")
	@Publico
	public Response getExisteProtocolo(@PathParam("idProtocolo") Long idProtocolo) {
		if (service.getProtocoloById(idProtocolo) != null) {
			return response.success().build();
		} else {
			return response.information().data("Protocolo não existe!").build();
		}
	}

	@GET
	@Path("movimentos/{idProtocolo}")
	@Publico
	public Response getMovimentos(@PathParam("idProtocolo") Long idProtocolo) {
		return response.success().data(service.getMovimentos(idProtocolo)).build();
	}

	@GET
	@Path("observacoes/{idMovimento}")
	@Publico
	public Response getObservacoesMovimento(@PathParam("idMovimento") Long idMovimento) {
		return response.success().data(service.getObservacoesMovimentos(idMovimento)).build();
	}

	@GET
	@Path("anexo/{idObservacao}")
	@Publico
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response download(@PathParam("idObservacao") Long idObservacao) throws IOException {
		return Response.ok(service.downloadAnexoObservacao(idObservacao)).type(MediaType.APPLICATION_OCTET_STREAM)
				.header("ContentDisposition", "attachment; filename=" + "anexo.pdf").build();
	}

	/**
	 * Método para envio de uma lista de um ou mais protocolos a um departamento de
	 * destino
	 * 
	 * 
	 * @param dto   - contém uma lista de um ou mais protocolos, funcionário que
	 *              tramitou, departamento de destino e departamento pai em relação
	 *              ao de destino (útil para validação de algumas regras).
	 * @param token
	 * @return lista de TramiteDto dos protocolos passados no dto com suas
	 *         respectivas mensagens de sucesso ou de erro conforme o caso.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@POST
	@Path("tramite-envio")
	public Response enviar(TramitacaoProtocoloDto dto, @HeaderParam("Authorization") String token)
			throws InterruptedException, ExecutionException {
		return response.success().data(tramiteService.enviar(dto, httpClientGoApi.getUserDto(token))).build();
	}

	/**
	 * Método para recebimento de uma lista de um ou mais protocolos em um
	 * departamento de destino
	 * 
	 * @param dto   - contém uma lista de um ou mais protocolos, funcionário que
	 *              tramitou, departamento de destino e departamento pai em relação
	 *              ao de destino (útil para validação de algumas regras).
	 * @param token
	 * @return lista de TramiteDto dos protocolos passados no dto com suas
	 *         respectivas mensagens de sucesso ou de erro conforme o caso.
	 * @throws InterruptedException - exceção relacionada a interrupção da thread
	 * @throws ExecutionException   - exceção relacionada a execução da thread
	 */
	@POST
	@Path("tramite-recebimento")
	public Response receber(TramitacaoProtocoloDto dto, @HeaderParam("Authorization") String token)
			throws InterruptedException, ExecutionException {
		return response.success().data(tramiteService.receber(dto, httpClientGoApi.getUserDto(token))).build();
	}

	/**
	 * @author Monique Santos
	 * @since 04/2018 Recurso útil para verificar se existe protocolo que foi
	 *        cadastrado pelo usuário do portal está localizado no arquivo virtual e
	 *        está vinculado a processo que já foi digitalizado.
	 */

	@GET
	@Path("verifica-cadastro-portal/{protocoloPrincipal}")
	public Response verificarProtocoloFisicoPortalDoProcessoEletronico(
			@PathParam("protocoloPrincipal") Long protocoloPrincipal) {

		if (service.buscaProtocolosFisicosDoPortalVinculadoAProcessoDigital(protocoloPrincipal).isEmpty()) {
			return response.success().data("").build();
		} else {
			return response.information()
					.message("O processo digital possui vinculo com protocolo físico gerado no portal.").build();
		}
	}

	/**
	 * @author Monique Santos
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @since 04/2018 Recurso útil somente para tramite indireto, ou seja, fora da
	 *        rotina de tramitação para apoiar a anexação de protocolo quando existe
	 *        protocolo de arquivo virtual (físico) vinculado a processo digital
	 */

	@POST
	@Path("movimenta-arquivo-virtual")
	public Response movimentarProtocolosArquivoVirtual(ProtocoloDto protocoloPrincipal,
			@HeaderParam("Authorization") String token) throws InterruptedException, ExecutionException,
			IllegalArgumentException, IllegalAccessException, IOException {
		return response.success().data(tramiteService.movimentarProtocolosArquivoVirtual(request,
				httpClientGoApi.getUserDto(token), protocoloPrincipal)).build();
	}

	@POST
	@Path("verifica-digitalizacao")
	@Publico // FIXME remover @publico, para uso no painel plenaria, remover quando token
				// estiver sendo utilizado
	public Response verificaDigitalizacao(PesquisaGenericDto dto) {
		if (service.vefificaDigitalicao(dto.getProtocolo())) {
			return response.success().data("É digitalizado").build();
		} else {
			return response.information().message("Não é digitalizado").build();
		}
	}

	@GET
	@Path("consulta/{numeroProtocolo}")
	public Response consultarProtocoloEVinculos(@PathParam("numeroProtocolo") Long numeroProtocolo)
			throws InterruptedException, ExecutionException {
		return response.success().data(service.buscaProtocoloEVinculos(numeroProtocolo)).build();
	}

	@POST
	@Path("verifica-anexo")
	public Response verificaAnexo(ProtocoloDto protocoloDto) {
		return response.success().data(service.verificaAnexo(protocoloDto)).build();
	}

	@POST
	@Path("cadastrar")
	public Response cadastrarProtocolo(ProtocoloDto protocoloDto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.cadastrarProtocolo(protocoloDto, httpClientGoApi.getUserDto(token)))
				.build();
	}

	@POST
	@Path("anexar")
	public Response anexarProtocolo(JuntadaProtocoloDto dto, @HeaderParam("Authorization") String token) {

		JuntadaProtocoloDto result = service.anexarProtocolo(dto, httpClientGoApi.getUserDto(token));
		return result.possuiErrosNaJuntada() ? response.information().data(result).build()
				: response.success().data(result).build();
	}

	@POST
	@Path("desanexar")
	public Response desanexarProtocolo(JuntadaProtocoloDto dto, @HeaderParam("Authorization") String token) {

		JuntadaProtocoloDto result = service.desvincularProtocoloJuntada(dto, httpClientGoApi.getUserDto(token));
		return result.possuiErrosNaJuntada() ? response.information().data(result).build()
				: response.success().data(result).build();
	}

	@POST
	@Path("apensar")
	public Response apensarProtocolo(JuntadaProtocoloDto dto, @HeaderParam("Authorization") String token) {

		JuntadaProtocoloDto result = service.apensarProtocolo(dto, httpClientGoApi.getUserDto(token));
		return result.possuiErrosNaJuntada() ? response.information().data(result).build()
				: response.success().data(result).build();
	}

	@POST
	@Path("desapensar")
	public Response desapensarProtocolo(JuntadaProtocoloDto dto, @HeaderParam("Authorization") String token) {

		JuntadaProtocoloDto result = service.desvincularProtocoloJuntada(dto, httpClientGoApi.getUserDto(token));
		return result.possuiErrosNaJuntada() ? response.information().data(result).build()
				: response.success().data(result).build();
	}

	@POST
	@Path("substituir")
	public Response substituirProtocolo(SubstituicaoProtocoloDto dto, @HeaderParam("Authorization") String token) {

		SubstituicaoProtocoloDto result = service.substituirProtocolo(dto, httpClientGoApi.getUserDto(token));
		return result.possuiErrosSubstituicao() ? response.error().data(result).build()
				: response.success().data(result).build();
	}

	@POST
	@Path("pesquisa")
	public Response getListaProtocoloPorNumero(PesquisaGenericDto dto, @HeaderParam("Authorization") String token) {

		List<ProtocoloDto> listDto = service.getListaProtocoloPorNumero(dto);

		if (!listDto.isEmpty()) {
			if (dto.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalDeProtocolos(dto)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			}
		} else {
			return response.success().data(listDto).build();
		}
	}
	
	
}
