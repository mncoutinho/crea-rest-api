package br.org.crea.commons.service;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import br.org.crea.commons.models.commons.dtos.ParcelamentoRequestDTO;
import br.org.crea.commons.models.commons.dtos.ParcelamentoResponseDTO;

public class HttpClientLegado {
	
	@Inject
	private Properties properties;

	private String url;

	
	@PostConstruct
	public void before() {
		this.url = properties.getProperty("corporativo.url");
	}

	public ParcelamentoResponseDTO buscaDividasParcelamentoAnuidadeProfissional(ParcelamentoRequestDTO parcelamentoRequestDTO) throws Exception {

		Gson gson = new Gson();
		String json = gson.toJson(parcelamentoRequestDTO);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			
			HttpPost post = new HttpPost(url + "/creaOnLine/home/realizarAtendimentoPublico.do?funcao=buscaDividasParcelamentoAnuidadeProfissional");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
				return gson.fromJson(baos.toString("UTF-8"), ParcelamentoResponseDTO.class);
			}
			
			throw new Exception();

		} catch (Exception es) {
			System.err.println("HttpClientLegado || buscaListaDividasAParcelar " + es.getMessage());
			throw new Exception();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}
	
	public ParcelamentoResponseDTO verificaTipoParcelamentoAnuidadeProfissional(ParcelamentoRequestDTO parcelamentoRequestDTO) throws Exception {

		Gson gson = new Gson();
		String json = gson.toJson(parcelamentoRequestDTO);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			
			HttpPost post = new HttpPost(url + "/creaOnLine/home/realizarAtendimentoPublico.do?funcao=verificaTipoParcelamentoAnuidadeProfissional");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
				return gson.fromJson(baos.toString("UTF-8"), ParcelamentoResponseDTO.class);
			}
			
			throw new Exception();

		} catch (Exception es) {
			System.err.println("HttpClientLegado || validaTipoParcelamento " + es.getMessage());
			throw new Exception();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}
	
	public ParcelamentoResponseDTO calcularValorTotalAnuidadesProfissional(ParcelamentoRequestDTO parcelamentoRequestDTO) throws Exception {

		Gson gson = new Gson();
		String json = gson.toJson(parcelamentoRequestDTO);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			
			HttpPost post = new HttpPost(url + "/creaOnLine/home/realizarAtendimentoPublico.do?funcao=calcularValorTotalAnuidadesProfissional");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
				return gson.fromJson(baos.toString("UTF-8"), ParcelamentoResponseDTO.class);
			}
			
			throw new Exception();

		} catch (Exception es) {
			System.err.println("HttpClientLegado || validaTipoParcelamento " + es.getMessage());
			throw new Exception();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}
	
	public byte[] gerarTermoEBoletoParcelamento(ParcelamentoRequestDTO parcelamentoRequestDTO) throws Exception {

		Gson gson = new Gson();
		String json = gson.toJson(parcelamentoRequestDTO);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			
			HttpPost post = new HttpPost(url + "/creaOnLine/home/realizarAtendimentoPublico.do?funcao=gerarDocumentoProfissional");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				return baos.toByteArray();
			}
			
			throw new Exception();

		} catch (Exception es) {
			System.err.println("HttpClientLegado || validaTipoParcelamento " + es.getMessage());
			throw new Exception();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}
	
	public ParcelamentoResponseDTO buscaTermosInscricaoParcelamento(ParcelamentoRequestDTO parcelamentoRequestDTO) throws Exception {

		Gson gson = new Gson();
		String json = gson.toJson(parcelamentoRequestDTO);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			
			HttpPost post = new HttpPost(url + "/creaOnLine/home/realizarAtendimentoPublico.do?funcao=buscaTermosInscricao");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
				return gson.fromJson(baos.toString("UTF-8"), ParcelamentoResponseDTO.class);
			}
			
			throw new Exception();

		} catch (Exception es) {
			System.err.println("HttpClientLegado || buscaTermosInscricaoParcelamento " + es.getMessage());
			throw new Exception();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}
	
	public ParcelamentoResponseDTO listaBoletosParcelamentos(ParcelamentoRequestDTO parcelamentoRequestDTO) throws Exception {

		Gson gson = new Gson();
		String json = gson.toJson(parcelamentoRequestDTO);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			
			HttpPost post = new HttpPost(url + "/creaOnLine/home/realizarAtendimentoPublico.do?funcao=reimpressaoBoletosParcelamento");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);
				return gson.fromJson(baos.toString("UTF-8"), ParcelamentoResponseDTO.class);
			}
			
			throw new Exception();

		} catch (Exception es) {
			System.err.println("HttpClientLegado || listaBoletosParcelamentos " + es.getMessage());
			throw new Exception();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}
	
	public byte[] gerarBoleto(ParcelamentoRequestDTO parcelamentoRequestDTO) throws Exception {

		Gson gson = new Gson();
		String json = gson.toJson(parcelamentoRequestDTO);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			
			HttpPost post = new HttpPost(url + "/creaOnLine/home/realizarAtendimentoPublico.do?funcao=geraReimpressaoBoleto");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				return baos.toByteArray();
			}
			
			throw new Exception();

		} catch (Exception es) {
			System.err.println("HttpClientLegado || gerarBoleto " + es.getMessage());
			throw new Exception();
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}

	}
	
}