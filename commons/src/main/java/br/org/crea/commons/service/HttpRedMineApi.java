package br.org.crea.commons.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import br.org.crea.commons.models.redmine.RedMineIssueDto;

public class HttpRedMineApi {

	
	public void salvaIssue(RedMineIssueDto obj) {

		Gson gson = new Gson();
		String json = gson.toJson(obj);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpPost post = new HttpPost("http://redmine.crea-rj.org.br/issues.json");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.addHeader("Authorization", "Basic YWRtaW46aW5waTEyM0A=");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			HttpEntity e = response.getEntity();

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpRedMineApi || salvaIssue " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}





}
