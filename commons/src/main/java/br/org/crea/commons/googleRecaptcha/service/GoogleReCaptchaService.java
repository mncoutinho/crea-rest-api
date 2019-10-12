package br.org.crea.commons.googleRecaptcha.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.crea.commons.googleRecaptcha.dto.GoogleRecaptchaResponseApi;

public class GoogleReCaptchaService {
	
	public boolean verify (String gRecaptchaResponse) {
		
		final String GOOGLE_RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
		final String SECRET = "6LfLB3MUAAAAALpAyZXiEI97hbyKrJI2ff8V9lsO";
		
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}
		
		Gson gson = new Gson();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {

			HttpPost post = new HttpPost(GOOGLE_RECAPTCHA_URL);
			
			List<NameValuePair> parametros = new ArrayList<NameValuePair>();
			parametros.add(new BasicNameValuePair("secret", SECRET));
			parametros.add(new BasicNameValuePair("response", gRecaptchaResponse));
			
			post.setEntity(new UrlEncodedFormEntity(parametros));
			HttpResponse response = httpClient.execute(post);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
				gson = new GsonBuilder().create();
				GoogleRecaptchaResponseApi responseApi = gson.fromJson(baos.toString("UTF-8"), GoogleRecaptchaResponseApi.class);
				return responseApi.getSuccess();
			}

		} catch (Exception es) {
			System.err.println("GoogleReCaptcha || verify " + es.getMessage());
			return false;
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		
		return false;
	}

}
