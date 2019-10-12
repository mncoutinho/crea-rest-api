package br.org.crea.commons.docflow.client;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import br.org.crea.commons.docflow.dto.DocflowGenericDto;
import br.org.crea.commons.docflow.exception.DefaultExceptionDocflow;
import br.org.crea.commons.docflow.model.response.ResponseConsultaProcessoDocflow;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientInformacoesProtocoloDocflow {
	
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
	public ResponseConsultaProcessoDocflow consultarProtocolo(DocflowGenericDto dto) {
		
		ResponseConsultaProcessoDocflow  result = new ResponseConsultaProcessoDocflow();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			HttpGet httpGet = new HttpGet(url + "/docflow/ws/consultas/processo/documentos?load_metadata=true&protocolo_processo=" + dto.getNumeroProtocolo() +"&auth=" + dto.getToken() + "&response_type=json");
			
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				
				entity.writeTo(baos);
				EntityUtils.consume(entity);
				gson = new GsonBuilder().create();
				
				result = gson.fromJson(baos.toString("UTF-8"), ResponseConsultaProcessoDocflow.class);
			}
			
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientInformacoesProtocoloDocflow  || buscarInformacoesProtocoloDocflow", StringUtil.convertObjectToJson(dto), e);
			result.setMessage(new DefaultExceptionDocflow().getMessageError());
			
		} finally {
			
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		
		return result;
	}
	
}
