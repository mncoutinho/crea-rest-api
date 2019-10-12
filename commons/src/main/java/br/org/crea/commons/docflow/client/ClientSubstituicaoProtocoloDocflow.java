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
import br.org.crea.commons.docflow.model.response.ResponseSubstituicaoProtocoloDocflow;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientSubstituicaoProtocoloDocflow {
	
    @Inject HttpClientGoApi httpGoApi;
	
	@Inject private Properties properties;
	
	private String url;
	
	@PostConstruct
	public void before () {
	 this.url = properties.getProperty("docflow.url");
	}

	@PreDestroy
	public void reset () {
		properties.clear();
	}

	private Gson gson;
	
	public ResponseSubstituicaoProtocoloDocflow substituirProtocolo(DocflowGenericDto dto) {

		ResponseSubstituicaoProtocoloDocflow result = new ResponseSubstituicaoProtocoloDocflow();
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
			
			parametros.add(new BasicNameValuePair("user_login", dto.getMatricula()));
			parametros.add(new BasicNameValuePair("protocolo_processo_substituido", dto.getNumeroProtocoloSubstituido()));
			parametros.add(new BasicNameValuePair("protocolo_processo_substituto", dto.getNumeroProtocoloSubstituto()));
			parametros.add(new BasicNameValuePair("codigo_unidade", dto.getCodigoDepartamento()));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parametros, "UTF-8");

			HttpPost post = new HttpPost(url + "/docflow/ws/protocolo/processo/substituir?auth=" + dto.getToken() + "&response_type=json");
			post.setEntity(entity);
			
			HttpResponse response = httpClient.execute(post);
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
			
				gson = new GsonBuilder().create();
				result = gson.fromJson(baos.toString("UTF-8"), ResponseSubstituicaoProtocoloDocflow.class);
			}
			
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientSubstituicaoProtocoloDocflow || substituirProtocolo", StringUtil.convertObjectToJson(dto), e);
			result.setMessage(new DefaultExceptionDocflow().getMessageError());

		} finally {
			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return result;
	}

}
