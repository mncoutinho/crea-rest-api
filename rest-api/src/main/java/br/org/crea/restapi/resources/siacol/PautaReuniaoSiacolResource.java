package br.org.crea.restapi.resources.siacol;

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

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.ArquivoFormUploadDto;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.siacol.dtos.ConsultaProtocoloDto;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaItemPautaSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.service.DocumentoService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.PautaReuniaoSiacolService;


@Resource
@Path("siacol/pauta")
public class PautaReuniaoSiacolResource {

	@Inject 
	ResponseRestApi response;

	@Inject 
	PautaReuniaoSiacolService service;
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	@Inject HttpClientGoApi httpClientGoApi;
	
	@Inject DocumentoService documentoService;
	
	
	@GET
	@Path("assuntos/{idDepartamento}") @Publico
	public Response getAssuntosDeProtocoloDaPauta(@PathParam("idDepartamento") Long idDepartamento) { 
		return response.success().data(service.getAssuntosDeProtocoloDaPauta(idDepartamento)).build();
	}
	
	@GET
	@Path("itens/{idDocumento}") @Publico
	public Response getItensByIdDocumento(@PathParam("idDocumento") Long idDocumento) { 
		return response.success().data(service.getItensByIdDocumento(idDocumento)).build();
	}
	
	@POST
	@Path("busca-itens-reuniao")
	@Publico
	public Response buscaItensReuniao(ReuniaoSiacolDto dto) {
		return response.success().data(service.buscaItensReuniao(dto)).build();
	}

	@POST
	@Path("item") @Publico
	public Response salvaItem(ItemPautaDto dto, @HeaderParam("Authorization") String token) { 
		return response.success().data(service.salvaItem(dto, httpClientGoApi.getUserDto(token))).build();
	}
	
	@POST
	@Path("itens") @Publico
	public Response salvaItem(List<ItemPautaDto> listItem, @HeaderParam("Authorization") String token) { 
		return response.success().data(service.acrescentaItensEmUmaPauta(listItem, httpClientGoApi.getUserDto(token))).build();
	}

	@PUT
	@Path("item") @Publico
	public Response atualizaItemDto(ItemPautaDto dto, @HeaderParam("Authorization") String token) { 
		return response.success().data(service.atualizaItemDto(dto, httpClientGoApi.getUserDto(token))).build();
	}
	
	@PUT
	@Path("itens") @Publico
	public Response atualizaItens(List<ItemPautaDto> listItens, @HeaderParam("Authorization") String token) { 
		return response.success().data(service.atualizaItens(listItens, httpClientGoApi.getUserDto(token))).build();
	}
	
	@PUT
	@Path("itens/{tipo}/{idReuniao}") @Publico
	public Response atualizaItem(@PathParam("tipo") String tipo, @PathParam("idReuniao") Long idReuniao, List<ItemPautaDto> listItens, @HeaderParam("Authorization") String token) { 
		return response.success().data(service.atualizaItensPor(listItens, tipo, idReuniao, httpClientGoApi.getUserDto(token))).build();
	}
		
	@PUT
	@Path("retirar-item/{numeroProtocolo}") @Publico
	public Response retirarItem(@PathParam("numeroProtocolo") Long numeroProtocolo) { 
		return response.success().data(service.retirarItem(numeroProtocolo)).build();
	}
	
	@POST
	@Path("pesquisa") @Publico
	public Response pesquisa(PesquisaItemPautaSiacolDto dto) { 
		return response.success().data(service.pesquisa(dto)).build();
	}

	@GET
	@Path("anexo/item/{idItem}")
	public Response getAnexosByItem(@PathParam("idItem") Long idItem, @HeaderParam("Authorization") String token) {
		return 	response.success().data(service.getAnexosByItem(idItem)).build();
	}
	
	@DELETE
	@Path("item/{idItem}")
	public Response excluiItem(@PathParam("idItem") Long idItem, @HeaderParam("Authorization") String token) {
		service.excluiItem(idItem, httpGoApi.getUserDto(token));
		return response.success().build();
	}
	
	@POST
	@Path("anexo/item")
	public Response salvaAnexoItem(@MultipartForm ArquivoFormUploadDto arquivo, @HeaderParam("Authorization") String token) { 
		return response.success().data(service.salvaAnexoItem(arquivo, httpClientGoApi.getUserDto(token).getIdPessoa() )).build();
	}
	
	@DELETE
	@Path("anexo/item/{idArquivo}")
	public Response excluiAnexo(@PathParam("idArquivo") Long idArquivo, @HeaderParam("Authorization") String token) {
		service.excluiAnexo(idArquivo, httpGoApi.getUserDto(token));
		return response.success().build();
	}
	
	@POST
	@Path("publica-arquivo-pauta")
	@Publico
	public Response geraArquivo(@Context HttpServletRequest request, DocumentoGenericDto dto, @HeaderParam("Authorization") String token) {
		DocumentoGenericDto documento = documentoService.geraArquivo(request, dto, httpClientGoApi.getUserDto(token));
		return response.success().data(service.mergePautaEPublica(documento)).build();
		//return response.success().data(documento).build();
	}
	
	@PUT
	@Path("concede-vista")
	@Publico
	public Response concedeVista(List<ItemPautaDto> listItens,  @HeaderParam("Authorization") String token) {
		service.concedeVista(listItens, httpClientGoApi.getUserDto(token));
		return response.success().build();
	}
	
	
	@GET
	@Path("item/{idItem}") @Publico
	public Response getItem(@PathParam("idItem") Long idItem) {
		return 	response.success().data(service.getItemBy(idItem)).build();
	}
	
	@POST
	@Path("busca-revisao-decisao")
	@Publico
	public Response buscaRevisaoDecisao(ConsultaProtocoloDto dto) {
		return response.success().data(service.buscaRevisaoDecisao(dto)).build();
	}
	

	@PUT
	@Path("observacao-coordenador")
	@Publico
	public Response observacaoCoordenador(ItemPautaDto dto) {
		return response.success().data(service.observacaoCoordenador(dto)).build();
	}
	
	
	@GET
	@Path("busca-itens-para-assinar/{idDepartamento}")
	public Response buscaItensParaAssinar(@PathParam("idDepartamento") Long idDepartamento, @HeaderParam("Authorization") String token) {
			return response.success().data(service.buscaItensParaAssinar(idDepartamento)).build();
	}
	
	@DELETE
	@Path("itens/pauta/{idPauta}") @Publico
	public Response excluirItensPauta(@PathParam("idPauta") Long idPauta) { 
		service.excluirItensPauta(idPauta);
		return response.success().build();
	}
	
	@POST
	@Path("valida-protocolo-emergencial/{numeroProtocolo}")@Publico	
	public Response validaInclusaoProtocoloEmergencialNaExtraPauta(@PathParam("numeroProtocolo") Long numeroProtocolo) {	
		ProtocoloSiacolDto dto = service.validaInclusaoProtocoloEmergencialNaExtraPauta(numeroProtocolo);
		if (dto != null) {
			return response.success().data(dto).build();
		} else {
			return response.information().message("Protocolo emergencial não é valido para inclusão na extra pauta").build();
		}
		
	}
	
	@POST
	@Path("valida-solicitacao-de-vistas") @Publico
	public Response validaSolicitacaoDeVistas(List<ItemPautaDto> listItens){
		
		if (service.validaSolicitacaoDeVistas(listItens)) {
			return response.information().build();
		} else {
			return response.success().build();
		}
		
	}
	
	@POST
	@Path("adiciona-item-extrapauta") @Publico
	public Response adicionaItemExtraPauta(List<ItemPautaDto> listItens){
		
		if (service.validaSolicitacaoDeVistas(listItens)) {
			return response.information().build();
		} else {
			return response.success().build();
		}
		
	}
}
