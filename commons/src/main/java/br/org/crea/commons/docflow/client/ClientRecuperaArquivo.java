package br.org.crea.commons.docflow.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@JsonSerialize
public class ClientRecuperaArquivo implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	public File recuperaArquivo(Long idDocumento, String authToken, Long idPessoa) {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			
			HttpGet httpGet = new HttpGet(url + "/docflow/ws/download/documento/?auth=" + authToken + "&id_documento=" + idDocumento + "&user_login=" + idPessoa );
			
			HttpResponse response = httpClient.execute(httpGet);
			response.setHeader("Content-Type", "application/pdf");
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {			    
			    
				entity.writeTo(baos);
				EntityUtils.consume(entity);

				File arquivo = new File("/opt/temp/"+idDocumento+".pdf");
			    FileOutputStream fout = new FileOutputStream(arquivo);
			    fout.write(baos.toByteArray());
			    fout.close();
			    
			    return arquivo;
				
				
			}
			
		} catch (Throwable e) {
			
			httpGoApi.geraLog("ClientInformacoesUsuarioDocflow || consultarUsuario", StringUtil.convertObjectToJson(idDocumento), e);
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return null;
		
	}


}
