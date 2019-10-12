package br.org.crea.commons.docflow.util;

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
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import br.org.crea.commons.docflow.model.response.ResponseAuthDocflow;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuthUtilDocflow {

	private Gson gson;
	
	@Inject HttpClientGoApi httpGoApi;
	
	@Inject private Properties properties;
	
	private String url;
	
	private HttpClient httpClient;
	
	@PostConstruct
	public void before () {
	 this.url = properties.getProperty("docflow.url");
	}

	@PreDestroy
	public void reset () {
		properties.clear();
	}
	
	public ResponseAuthDocflow auth() {
		
		 ResponseAuthDocflow auth = new ResponseAuthDocflow();
		
		  httpClient = new DefaultHttpClient();

			try  {

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();

				formparams.add(new BasicNameValuePair("login", "corporativo.crearj"));
				formparams.add(new BasicNameValuePair("password", "crearjdocflow"));
				formparams.add(new BasicNameValuePair("tipo", "sistema"));
				
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,"UTF-8");

				HttpPost httppost = new HttpPost(url + "/docflow/ws/auth?&response_type=json");
				httppost.setEntity(entity);

				HttpResponse response = httpClient.execute(httppost);
				HttpEntity e = response.getEntity();
				
				if (e != null) {
					e.writeTo(baos);
					EntityUtils.consume(e);
					gson = new GsonBuilder().create();
					
					auth = gson.fromJson(baos.toString("UTF-8"), ResponseAuthDocflow.class);
				}
			} catch (Throwable e) {
				httpGoApi.geraLog("AuthUtilDocflow || auth", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
			} finally {
				if (httpClient != null) {
					httpClient.getConnectionManager().shutdown();
				}
			}
		
		return auth;
	}
}
