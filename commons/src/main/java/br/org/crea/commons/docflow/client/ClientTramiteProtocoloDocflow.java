package br.org.crea.commons.docflow.client;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.exception.DefaultExceptionDocflow;
import br.org.crea.commons.docflow.model.response.ResponseTramiteProtocoloDocflow;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientTramiteProtocoloDocflow {
	

	@Inject HttpClientGoApi httpGoApi;
	
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
	
	public ResponseTramiteProtocoloDocflow enviarProtocolo(DocflowGenericDto dto) {
		
		ResponseTramiteProtocoloDocflow result = new ResponseTramiteProtocoloDocflow();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
			
			parametros.add(new BasicNameValuePair("user_login", dto.getMatricula()));
			parametros.add(new BasicNameValuePair("protocolo_processo", dto.getNumeroProtocolo()));
			parametros.add(new BasicNameValuePair("codigo_unidade_destino",	dto.getUnidadeDestino()));
			parametros.add(new BasicNameValuePair("codigo_organizacao_interna", "1"));
			parametros.add(new BasicNameValuePair("codigo_unidade_origem", dto.getUnidadeOrigem()));
			parametros.add(new BasicNameValuePair("codigo_classificacao", dto.getCodigoClassificacaoTramite()));
			parametros.add(new BasicNameValuePair("codigo_situacao", dto.getCodigoSituacao()));
            
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parametros, "UTF-8");
			HttpPost httpPost = new HttpPost(url + "/docflow/ws/tramitacao/envio?auth=" + dto.getToken() + "&response_type=json");	
			httpPost.setEntity(entity);
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
				gson = new GsonBuilder().create();

				result = gson.fromJson(baos.toString("UTF-8"), ResponseTramiteProtocoloDocflow.class);

			}
			
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientTramiteProtocoloDocflow || enviarProtocolo", StringUtil.convertObjectToJson(dto), e);
			result.setMessage(new DefaultExceptionDocflow().getMessageError());
			
		} finally {
			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}

	public ResponseTramiteProtocoloDocflow receberProtocolo(DocflowGenericDto dto) {
		ResponseTramiteProtocoloDocflow result = new ResponseTramiteProtocoloDocflow();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
			
			parametros.add(new BasicNameValuePair("user_login", dto.getMatricula()));
			parametros.add(new BasicNameValuePair("protocolo_processo", dto.getNumeroProtocolo()));
			parametros.add(new BasicNameValuePair("codigo_unidade_recebimento",	dto.getUnidadeDestino()));
			parametros.add(new BasicNameValuePair("codigo_organizacao_interna", "1"));
			            
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parametros, "UTF-8");
			HttpPost httpPost = new HttpPost(url + "/docflow/ws/tramitacao/recebimento?auth=" + dto.getToken() + "&response_type=json");	
			httpPost.setEntity(entity);
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
				gson = new GsonBuilder().create();

				result = gson.fromJson(baos.toString("UTF-8"), ResponseTramiteProtocoloDocflow.class);

			}
			
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientTramiteProtocoloDocflow || receberProtocolo", StringUtil.convertObjectToJson(dto), e);
			result.setMessage(new DefaultExceptionDocflow().getMessageError());
			
		} finally {
			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}

}
