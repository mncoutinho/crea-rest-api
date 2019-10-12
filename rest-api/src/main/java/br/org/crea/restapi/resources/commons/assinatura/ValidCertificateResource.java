package br.org.crea.restapi.resources.commons.assinatura;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.docflow.model.response.ResponseCadastroDocumentoDocflow;
import br.org.crea.commons.docflow.service.DocflowService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.commons.validsigner.converter.ValidSignerConverter;
import br.org.crea.commons.validsigner.dto.FormUploadAssinaturaDto;
import br.org.crea.commons.validsigner.dto.ValidSignDto;
import br.org.crea.commons.validsigner.service.ValidSignerService;

@Resource
@Path("/commons/certificado")
public class ValidCertificateResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject ValidSignerService service;
	
	@Inject ValidSignerConverter converter;
	
	@Inject HttpClientGoApi httpClientGoApi;
	
	@Inject DocflowService docflowService;
	
	@Inject	ResponseCadastroDocumentoDocflow responseCadastroDocumento;
	
	/**
	 * Contempla casos onde o documento a ser assinado está em cad-documento(json)
	 * ou em cad-arquivo(file system)
	 * 
	 * Primeira etapa da assinatura que busca no WS da Valid Certificadora informações necessárias 
	 * para commitar uma assinatura no documento
	 * */
	@POST
	@Path("/presign-documento")
	public Response preparaAssinatura(@Context HttpServletRequest request, ValidSignDto dto, @HeaderParam("Authorization") String token) {
		
		ValidSignDto resultPreSign = service.preparaAssinatura(dto, request, httpClientGoApi.getUserDto(token));
		return resultPreSign.assinaturaValida() ? response.success().data(dto).build() : response.error().data(resultPreSign).build();  
	}
	
	/**
	 * Contempla caso onde o documento a ser assinado é submetido via upload (form-data)
	 * 
	 * Primeira etapa da assinatura que busca no WS da Valid Certificadora informações necessárias 
	 * para commitar uma assinatura no documento
	 * */
	@POST 
	@Path("/presign-upload-file") 
	public Response preparaAssinaturaUploadFile(@Context HttpServletRequest request, @MultipartForm FormUploadAssinaturaDto arquivo, @HeaderParam("Authorization") String token) {
		ValidSignDto dto = converter.toDto(arquivo, request);
		ValidSignDto resultPreSign = service.preparaAssinatura(dto, request, httpClientGoApi.getUserDto(token));
		return resultPreSign.assinaturaValida() ? response.success().data(resultPreSign).build() : response.error().data(resultPreSign).build();  
	}
	
	/**
	 * Recurso necessário para finalizar um assinatura e inputá-la no documento a ser assinado.
	 * Deve ser acionado no término do processamento do pré-sign, caso o mesmo retorne success. 
	 * */
	@POST
	@Path("/finalizar-sign")
	public Response finalizaAssinatura(ValidSignDto dto, @HeaderParam("Authorization") String token) {
		
		ValidSignDto resultSign = service.finalizaAssinatura(dto, httpClientGoApi.getUserDto(token));
		return resultSign.assinaturaValida() ? response.success().data(dto).build() : response.error().data(resultSign).build();  
	}
	
	/**
	 * Recurso necessário para finalizar um assinatura e inputá-la no documento a ser assinado.
	 * Deve ser acionado no término do processamento do pré-sign, caso o mesmo retorne success. 
	 * @throws IOException 
	 * */
	@POST
	@Path("/finalizar-sign-upload-file")
	public Response finalizaAssinaturaUploadFile(@Context HttpServletRequest request, @MultipartForm FormUploadAssinaturaDto arquivo, @HeaderParam("Authorization") String token) throws IOException {
		ValidSignDto dto = converter.toDto(arquivo, request);
		ValidSignDto resultSign = service.finalizaAssinatura(dto, httpClientGoApi.getUserDto(token));
		
        try {   	
        	
//        	File file = null;
//    		if (arquivo.getIdDocumento() != null) {
//    			file = new File("C://temp//" + arquivo.getFileName() + ".pdf");
//    		} else {
//    			file = new File("C://temp//" + arquivo.getFileName());
//    		}
//            
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(resultSign.getDocumento());
//            fos.flush();
//            fos.close();
//            
//            file.createNewFile();
     	
            responseCadastroDocumento = docflowService.uparDocumentoEmProcessoEletronicoAssinado(arquivo, resultSign.getDocumento());

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	    	    
		return response.success().data(dto).build();
	}
}
