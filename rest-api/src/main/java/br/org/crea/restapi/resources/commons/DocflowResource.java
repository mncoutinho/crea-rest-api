package br.org.crea.restapi.resources.commons;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.dto.FileUploadDocflowDto;
import br.org.crea.commons.docflow.model.response.ResponseAuthDocflow;
import br.org.crea.commons.docflow.model.response.ResponseCadastroDocumentoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseExclusaoConteudoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseTramiteProtocoloDocflow;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.protocolo.ProtocoloService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/commons/docflow")
public class DocflowResource {
	
	@Inject	ResponseCadastroDocumentoDocflow responseCadastroDocumento;
	
	@Inject ResponseTramiteProtocoloDocflow responseTramitacao;
	
	@Inject ResponseExclusaoConteudoDocflow responseExclusaoConteudo;
	
	@Inject ResponseAuthDocflow responseAutorizacao;
	
	@Inject DocflowService docflowService;
	
	@Inject	ResponseRestApi response;
	
	@Inject	ProtocoloService protocoloService;
	
	@Inject HttpClientGoApi httpClientGoApi;
	
	@POST
	@Path("cadastrar-documento") 
	public Response gerarDocumentoEmProcessoEletronico(@Context HttpServletRequest request, DocflowGenericDto dto, @HeaderParam("Authorization") String token) throws IllegalArgumentException, IllegalAccessException, IOException {

		if (dto.getNumeroProtocolo() != null && protocoloService.vefificaDigitalicao(dto.getNumeroProtocolo())) {
			dto.setEhDigital(true);
			responseCadastroDocumento = docflowService.gerarInserirDocumentoEmProcessoEletronico(request, dto, httpClientGoApi.getUserDto(token));
		} else {
			dto.setEhDigital(false);
			responseCadastroDocumento = docflowService.gerarInserirDocumentoEmProcessoEletronicoNaoDigital(request, dto, httpClientGoApi.getUserDto(token));
			if (!responseCadastroDocumento.hasError()) {
				protocoloService.atualizarParaDigital(dto.getNumeroProtocolo());
			}
		}
		
		if (responseCadastroDocumento.hasError()) {
			return response.error().message(responseCadastroDocumento.getMessage().getValue()).build();
		} else {
			return response.success().message("Documento cadastrado com sucesso!").data(responseCadastroDocumento).build();
		}
	}
	 
	@POST
	@Path("upar-documento")
	public Response uparDocumentoEmProcessoEletronico(@MultipartForm FileUploadDocflowDto form) {
		
		responseCadastroDocumento = docflowService.uparDocumentoEmProcessoEletronico(form);
		
		if (responseCadastroDocumento.hasError()) {
			return response.error().message(responseCadastroDocumento.getMessage().getValue()).build();
		} else {
			return response.success().message("Documento inserido com sucesso!").data(responseCadastroDocumento).build();
		}
	}
	
	@POST
	@Path("cadastrar-documento-avulso")
	public Response cadastrarDocumentoSemProcesso(@MultipartForm FileUploadDocflowDto form) {
		
		responseCadastroDocumento = docflowService.cadastrarDocumentoSemProcesso(form);

		if (responseCadastroDocumento.hasError()) {
			return response.error().message(responseCadastroDocumento.getMessage().getValue()).build();
		} else {
			String documentoDocflow = responseCadastroDocumento.getData().getDoc().getProtocolo();
			return response.success().message("Documento " + documentoDocflow + " cadastrado com sucesso!").data(responseCadastroDocumento).build();
		}
	}
	
	@GET
	@Path("token") @Publico //FIXME remover @publico, para uso no painel plenaria, remover quando token estiver sendo utilizado
	public Response getToken() {

		responseAutorizacao = docflowService.getToken();
		
		if (responseAutorizacao.hasError()) {
			return response.error().message(responseAutorizacao.getMessage().getValue()).build();
		} else {
			return response.success().data(responseAutorizacao).build();
		}
	}
	
	@POST
	@Path("consultar-protocolo") 
	public Response consultarProtocolo(DocflowGenericDto dto) {
		return response.success().data(docflowService.consultarProtocolo(dto)).build();
	}
	
	@POST
	@Path("consultar-usuario")
	public Response consultarUsuario(DocflowGenericDto dto) {
		return response.success().data(docflowService.consultarUsuario(dto)).build();
	}
	
	@GET
	@Path("unidades-usuario") 
	public Response consultarUnidadesUsuario(@HeaderParam("Authorization") String token) {
		DocflowGenericDto dto = new DocflowGenericDto();
		dto.setMatricula(httpClientGoApi.getToken(token).getMatricula().toString());
		
		return response.success().data(docflowService.consultarUsuario(dto).getUnidades()).build();
	}
	
	@GET
	@Path("unidades-siacol-usuario") 
	public Response consultarUnidadesSiacolUsuario(@HeaderParam("Authorization") String token) {
		DocflowGenericDto dto = new DocflowGenericDto();
		dto.setMatricula(httpClientGoApi.getToken(token).getMatricula().toString());
		
		return response.success().data(docflowService.consultarUnidadesSiacolUsuario(dto).getUnidades()).build();
	}
	
	@GET
	@Path("ids-unidades-usuario") 
	public Response consultarIdsUnidadesUsuario(@HeaderParam("Authorization") String token) {
		DocflowGenericDto dto = new DocflowGenericDto();
		dto.setMatricula(httpClientGoApi.getToken(token).getMatricula().toString());
		
		return response.success().data(docflowService.consultarIdsUnidadesUsuario(dto)).build();
	}
	
	@POST
	@Path("excluir-documento") 
	public Response excluirDocumento(DocflowGenericDto dto, @HeaderParam("Authorization") String token) {

		dto.setMatricula(httpClientGoApi.getToken(token).getMatricula().toString());
		responseExclusaoConteudo = docflowService.excluirDocumento(dto);
		
		if (responseExclusaoConteudo.hasError()) {
			return response.error().message(responseExclusaoConteudo .getMessage().getValue()).build();
		} else {
			return response.success().message("Documento excluído com sucesso!").data(responseExclusaoConteudo ).build();
		}
	}
}
