package br.org.crea.commons.docflow.client;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.exception.DefaultExceptionDocflow;
import br.org.crea.commons.docflow.model.response.ResponseAutuacaoProcessoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseCadastroDocumentoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseCriarProtocoloAssociarDocumentoDocflow;
import br.org.crea.commons.docflow.model.response.ResponseUploadDocumentoDocflow;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class ClientCadastroDocumentoProtocolo {
	
	@Inject HttpClientGoApi httpGoApi;
	
//	@Inject	ProtocoloService protocoloService;
	
	private Gson gson;
	
	@Inject
	private Properties properties;
	
	private String url;
	
	@PostConstruct
	public void before () {
	 this.url = properties.getProperty("docflow.url");
	}

	@PreDestroy
	public void reset () {
		properties.clear();
	}

	public ResponseCadastroDocumentoDocflow cadastrarMetadadosDocumento(DocflowGenericDto dto) {

		ResponseCadastroDocumentoDocflow result = new ResponseCadastroDocumentoDocflow();
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			headers.put("Accept-Charset", "UTF-8");

			List<NameValuePair> parametros = new ArrayList<NameValuePair>();

			parametros.add(new BasicNameValuePair("codigo_tipo_documento", dto.getTipoDocumento()));
			parametros.add(new BasicNameValuePair("login_autor", 		   dto.getMatricula()));
			parametros.add(new BasicNameValuePair("user_login", 		   dto.getMatricula()));
			parametros.add(new BasicNameValuePair("assunto", 		       dto.getAssunto()));
			parametros.add(new BasicNameValuePair("interessado", "nome=" + dto.getInteressado()));
			parametros.add(new BasicNameValuePair("observacao", 		   dto.getObservacaoDoDocumento()));
			parametros.add(new BasicNameValuePair("data_documento",        dto.getDataArquivo()));
			parametros.add(new BasicNameValuePair("codigo_situacao", 	   "0"));
			parametros.add(new BasicNameValuePair("codigo_unidade_origem", String.valueOf(dto.getUnidadeOrigem())));
			parametros.add(new BasicNameValuePair("codigo_unidade_destino", String.valueOf(dto.getUnidadeDestino())));
			parametros.add(new BasicNameValuePair("codigo_organizacao_interna", 	   "1"));
			parametros.add(new BasicNameValuePair("codigo_sigilo", 	   "0"));
			
//			if( dto.getNumeroProtocolo() != null && protocoloService.vefificaDigitalicao(dto.getNumeroProtocolo())) {
			if( dto.getNumeroProtocolo() != null && !dto.isEhDigital()) {
				parametros.add(new BasicNameValuePair("protocolo_documento", dto.getNumeroProtocolo()));
			} 

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parametros, "UTF-8");

			HttpPost httpPost = new HttpPost(url + "/docflow/ws/protocolo/documento/metadados?auth=" + dto.getToken() + "&response_type=json");
			httpPost.setEntity(entity);
			
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
			
				gson = new GsonBuilder().create();

				result = gson.fromJson(baos.toString("UTF-8"), ResponseCadastroDocumentoDocflow.class);
			}
			
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientCadastroDocumentoProtocolo || cadastrarMetadadosDocumento", StringUtil.convertObjectToJson(dto), e);
			result.setMessage(new DefaultExceptionDocflow().getMessageError());

		} finally {
			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}
	
	public ResponseUploadDocumentoDocflow uploadBinarioDocumentoPdf(DocflowGenericDto dto) {
		ResponseUploadDocumentoDocflow result = new ResponseUploadDocumentoDocflow();

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			HttpPost httpPost = new HttpPost(url + "/docflow/ws/protocolo/documento/bin?id_documento=" + dto.getIdDocumento() + "&user_login=" + dto.getMatricula() + "&codigo_unidade="+ dto.getCodigoDepartamento() + "&auth="
					+ dto.getToken() + "&response_type=json");

			InputStreamEntity reqEntity = new InputStreamEntity(dto.getInputStreamArquivoPdf(), -1);

			reqEntity.setContentType("application/pdf");
			httpPost.addHeader("Content-Disposition", "attachment;filename=" + dto.getNomeArquivoPdf() + ".pdf");
			reqEntity.setChunked(true);

			httpPost.setEntity(reqEntity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity e = response.getEntity();

			if (e != null) {
				
				e.writeTo(baos);
				EntityUtils.consume(e);
				gson = new GsonBuilder().create();

				result = gson.fromJson(baos.toString("UTF-8"), ResponseUploadDocumentoDocflow.class);

			}
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientCadastroDocumentoProtocolo || uploadBinarioDocumentoPdf", StringUtil.convertObjectToJson(dto), e);
			result.setMessage(new DefaultExceptionDocflow().getMessageError());
			
		} finally {
			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}
	
	public ResponseAutuacaoProcessoDocflow autuarProcessoComBaseEmDocumentoExistente(DocflowGenericDto dto) {
		ResponseAutuacaoProcessoDocflow result = new ResponseAutuacaoProcessoDocflow();
		
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			headers.put("Accept-Charset", "UTF-8");

			List<NameValuePair> parametros = new ArrayList<NameValuePair>();

			parametros.add(new BasicNameValuePair("id_documento", 		dto.getIdDocumento()));
			parametros.add(new BasicNameValuePair("assunto_processo", 	dto.getAssunto()));
			parametros.add(new BasicNameValuePair("processo_crearj", 	dto.getNumeroProcesso()));
			parametros.add(new BasicNameValuePair("user_login", 		dto.getMatricula()));
			parametros.add(new BasicNameValuePair("cod_tipo_processo", 	dto.getTipoProcesso().equals("0") ? "10" : dto.getTipoProcesso()));
			parametros.add(new BasicNameValuePair("numero_processo", 	dto.getNumeroProcesso()));
			parametros.add(new BasicNameValuePair("codigo_situacao", 	"0"));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parametros, "UTF-8");

			HttpPost httpPost = new HttpPost(url + "/docflow/ws/protocolo/processo/autuacao?auth=" + dto.getToken() + "&response_type=json");
			httpPost.setEntity(entity);
			
			for (Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity e = response.getEntity();

			if (e != null) {
				
				e.writeTo(baos);
				EntityUtils.consume(e);
				gson = new GsonBuilder().create();

				result = gson.fromJson(baos.toString("UTF-8"), ResponseAutuacaoProcessoDocflow.class);
			}
			
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientCadastroDocumentoProtocolo || autuarProcessoComBaseEmDocumentoExistente", StringUtil.convertObjectToJson(dto), e);
			result.setMessage(new DefaultExceptionDocflow().getMessageError());
			
		} finally {
			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}

	public ResponseUploadDocumentoDocflow associarDocumentoAProcessoExistente(DocflowGenericDto dto) {
		ResponseUploadDocumentoDocflow result = new ResponseUploadDocumentoDocflow();

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			HttpPost httppost = new HttpPost(url + "/docflow/ws/include/processo/documento?auth=" + dto.getToken() + "&response_type=json");

			List<NameValuePair> parametros = new ArrayList<NameValuePair>();

			parametros.add(new BasicNameValuePair("user_login", dto.getMatricula()));
			parametros.add(new BasicNameValuePair("protocolo_processo",	dto.getProtocoloDoDocumento()));
			parametros.add(new BasicNameValuePair("id_documento", dto.getIdDocumento()));
			parametros.add(new BasicNameValuePair("codigo_unidade", dto.getCodigoDepartamento()));

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(parametros,	"UTF-8");
			httppost.setEntity(ent);
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				
				entity.writeTo(baos);
				EntityUtils.consume(entity);
				gson = new GsonBuilder().create();

				result = gson.fromJson(baos.toString("UTF-8"), ResponseUploadDocumentoDocflow.class);
			}
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientCadastroDocumentoProtocolo || associarDocumentoAProcessoExistente", StringUtil.convertObjectToJson(dto), e);
			result.setMessage(new DefaultExceptionDocflow().getMessageError());

		} finally {
			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}

	public ResponseCriarProtocoloAssociarDocumentoDocflow criarProtocoloAssociarDocumento(DocflowGenericDto dto) {
		ResponseCriarProtocoloAssociarDocumentoDocflow result = new ResponseCriarProtocoloAssociarDocumentoDocflow();

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			headers.put("Accept-Charset", "UTF-8");

			HttpPost httppost = new HttpPost(url + "/docflow/ws/protocolo/processo/autuacao?auth=" + dto.getToken() + "&response_type=json");
			
			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
			
			parametros.add(new BasicNameValuePair("id_documento", 			dto.getIdDocumento()));
			parametros.add(new BasicNameValuePair("assunto_processo", 		dto.getAssunto()));
			parametros.add(new BasicNameValuePair("processo_crearj",		dto.getProtocoloDoDocumento()));
			parametros.add(new BasicNameValuePair("user_login", 		   	dto.getMatricula()));
			parametros.add(new BasicNameValuePair("cod_tipo_processo", 		dto.getTipoProcesso()));
			parametros.add(new BasicNameValuePair("numero_processo", 		dto.getNumeroProcesso()));
			parametros.add(new BasicNameValuePair("codigo_situacao", 	   	"0"));

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(parametros,	"UTF-8");
			httppost.setEntity(ent);
			
			for (Entry<String, String> entry : headers.entrySet()) {
				httppost.addHeader(entry.getKey(), entry.getValue());
			}
			
			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				
				entity.writeTo(baos);
				EntityUtils.consume(entity);
				gson = new GsonBuilder().create();

				result = gson.fromJson(baos.toString("UTF-8"), ResponseCriarProtocoloAssociarDocumentoDocflow.class);
			}
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientCadastroDocumentoProtocolo || criarProtocoloAssociarDocumento", StringUtil.convertObjectToJson(dto), e);
			result.setMessage(new DefaultExceptionDocflow().getMessageError());

		} finally {
			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}
}
