package br.org.crea.commons.service;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpCouchApi {

	@Inject
	private Properties properties;

	private String url;

	private String basicAuth;

	@PostConstruct
	public void before() {
		this.url = properties.getProperty("couchApi.url");
		this.basicAuth = properties.getProperty("couchApi.auth");
	}

	@PreDestroy
	public void reset() {
		properties.clear();
	}

	public void salvaPerfil(Object obj) {

		Gson gson = new Gson();
		String json = gson.toJson(obj);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpPost post = new HttpPost(url + "/perfis");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.addHeader("Authorization", basicAuth);
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			HttpEntity e = response.getEntity();

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpCouchApi || salvaPerfil " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}

	public Object getPerfil(String id) {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpGet request = new HttpGet(url + "/perfis" + "/" + id);
			request.addHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(request);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);

				Gson gson = new GsonBuilder().create();
				Object responseApi = gson.fromJson(baos.toString("UTF-8"), Object.class);

				return responseApi;
			}

		} catch (Exception es) {
			System.err.println("HttpCouchApi || getPerfil " + es.getMessage());
		} finally {

			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return id;

	}

	public void alteraPerfil(String id, Object obj) {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		Gson gson = new Gson();
		String json = gson.toJson(obj);

		try {

			HttpPut post = new HttpPut(url + "/perfis/" + id);
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.addHeader("Authorization", basicAuth);
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			HttpEntity e = response.getEntity();

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpCouchApi || salvaPerfil " + es.getMessage());
		}finally {

			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}

}
