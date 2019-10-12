package br.org.crea.commons.service;

import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import br.org.crea.commons.models.atendimento.dtos.PainelAtendimentoDto;
import br.org.crea.commons.models.siacol.dtos.IndicadoresReuniaoSiacolDto;

public class HttpFirebaseApi {

	@Inject
	private Properties properties;

	private String url;
	
	private String urlPainelAtendimento;

	@PostConstruct
	public void before() {
		this.url = properties.getProperty("firebaseApi.url");
		this.urlPainelAtendimento = properties.getProperty("firebaseApi.urlPainelAtendimento");
	}
	
	@PreDestroy
	public void reset() {
		properties.clear();
	}

	public void salvaApuracao(Object obj, Long idReuniao) {

		Gson gson = new Gson();
		String json = gson.toJson(obj);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpPut put = new HttpPut(url + "/apuracao/" + idReuniao + ".json");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			put.addHeader("content-type", "application/json");
			// put.addHeader("Authorization", basicAuth);
			put.setEntity(body);

			HttpResponse response = httpClient.execute(put);
			HttpEntity e = response.getEntity();

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpFirebaseApi || salvaApuracao " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
	}
	
	public void salvarIndicadores(IndicadoresReuniaoSiacolDto populaIndicadores, Long idReuniao) {

		Gson gson = new Gson();
		String json = gson.toJson(populaIndicadores);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			HttpPut put = new HttpPut(url + "/indicadores/" + idReuniao + ".json");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			put.addHeader("content-type", "application/json");
			// put.addHeader("Authorization", basicAuth);
			put.setEntity(body);

			HttpResponse response = httpClient.execute(put);
			HttpEntity e = response.getEntity();

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpFirebaseApi || salvarIndicadores " + es.getMessage());
		}
	}
	
	public void salvarPainelAtendimento(PainelAtendimentoDto atendimento) {

		atendimento.setHash(UUID.randomUUID().toString());
		Gson gson = new Gson();
		String json = gson.toJson(atendimento);
		DefaultHttpClient httpClient = new DefaultHttpClient();
	
		try {
			HttpPost post = new HttpPost(urlPainelAtendimento + "/" + atendimento.getIdDepartamento() + ".json");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			// put.addHeader("Authorization", basicAuth);
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			HttpEntity e = response.getEntity();

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpFirebaseApi || salvarPainelAtendimento " + es.getMessage());
		}
	}

}
