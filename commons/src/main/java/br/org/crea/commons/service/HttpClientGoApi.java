package br.org.crea.commons.service;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.crea.commons.models.commons.TokenUser;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.ExceptionUtil;
import br.org.crea.commons.util.TokenUtil;

public class HttpClientGoApi {

	@Inject
	private Properties properties;

	private String url;

	private String ambiente;

	@PostConstruct
	public void before() {
		this.url = properties.getProperty("goApi.url");
		this.ambiente = properties.getProperty("goApi.ambiente");
	}


	public void salvaToken(TokenUser token) {

		Gson gson = new Gson();
		String json = gson.toJson(token);
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpPost post = new HttpPost(url + "token");
			StringEntity body = new StringEntity(json.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			HttpEntity e = response.getEntity();

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpClientGoApi || salvaToken " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
			properties.clear();
		}

	}

	public UserFrontDto getToken(String token) {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpGet request = new HttpGet(url + "token" + "/" + token);
			request.addHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(request);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);

				Gson gson = new GsonBuilder().create();
				ResponseApiGo responseApi = gson.fromJson(baos.toString("UTF-8"), ResponseApiGo.class);

				return TokenUtil.recuperaDto(responseApi.getData());
			}

		} catch (Exception es) {
			System.err.println("HttpClientGoApi || getToken " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
			properties.clear();
		}
		return null;

	}

	public List<LogApi> getLogs() {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpGet request = new HttpGet(url + "log");
			request.addHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(request);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);

				Gson gson = new GsonBuilder().create();
				ResponseLogApi responseApi = gson.fromJson(baos.toString("UTF-8"), ResponseLogApi.class);

				return responseApi.getData();
			}

		} catch (Exception es) {
			System.err.println("HttpClientGoApi || getLogs " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
			properties.clear();
		}
		return null;

	}
	


	public List<LogApi> getLogsComFiltro(String filtro) {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpGet request = new HttpGet(url + "log/" + filtro);
			request.addHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(request);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpEntity e = response.getEntity();

			if (e != null) {
				e.writeTo(baos);
				EntityUtils.consume(e);

				Gson gson = new GsonBuilder().create();
				ResponseLogApi responseApi = gson.fromJson(baos.toString("UTF-8"), ResponseLogApi.class);

				return responseApi.getData();
			}

		} catch (Exception es) {
			System.err.println("HttpClientGoApi || getLogsComFiltro " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
			properties.clear();
		}
		return null;
	}

	public void geraLog(String metodo, String json, Throwable e) {

		LogApi log = new LogApi();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		StringBuilder sb = new StringBuilder();
		sb.append(ambiente);
		sb.append(":");
		sb.append(String.valueOf(DateUtils.format(DateUtils.getDataAtual(), DateUtils.YYYY)));
		sb.append(":");
		sb.append(String.valueOf(DateUtils.format(DateUtils.getDataAtual(), DateUtils.MM)));
		sb.append(":");
		sb.append(String.valueOf(DateUtils.format(DateUtils.getDataAtual(), DateUtils.DD)));
		sb.append(":");
		sb.append(String.valueOf(DateUtils.format(DateUtils.getDataAtual(), DateUtils.HH_MM)));
		sb.append(":");
		sb.append(UUID.randomUUID().toString().replace("-", "").substring(12));

		log.setId(sb.toString());
		log.setMetodo(metodo);
		log.setStack(ExceptionUtil.getExceptionMessageChain(e).toString());
		log.setParametro(json);
		log.setDataLog(DateUtils.format(new Date(), DateUtils.DD_MM_YYYY_HH_MM_SS));
		log.setAmbiente(ambiente);

		Gson gson = new Gson();

		try {

			String logJson = gson.toJson(log);
			HttpPost post = new HttpPost(url + "log");
			StringEntity body = new StringEntity(logJson.toString(), "UTF-8");

			post.addHeader("content-type", "application/json");
			post.addHeader("Accept", "application/json");
			post.setEntity(body);

			HttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();

			if (e != null) {
				EntityUtils.consume(entity);
			}

		} catch (Exception es) {
			System.err.println("HttpClientGoApi || geraLog " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
			properties.clear();
		}

	}

	public boolean estaLogado(String token) {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpGet request = new HttpGet(url + "token" + "/" + token + "/valida");
			request.addHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(request);
			HttpEntity e = response.getEntity();

			if (response.getStatusLine().getStatusCode() == 200) {
				return true;
			}

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpClientGoApi || estaLogado " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
			properties.clear();
		}
		return false;
	}

	public void deleteToken(String token) {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpDelete request = new HttpDelete(url + "token" + "/" + token);
			request.addHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(request);
			HttpEntity e = response.getEntity();

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpClientGoApi || deleteToken " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
			properties.clear();
		}

	}

	public void deleteLog(String id) {

		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {

			HttpDelete request = new HttpDelete(url + "log" + "/" + id);
			request.addHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(request);
			HttpEntity e = response.getEntity();

			if (e != null) {
				EntityUtils.consume(e);
			}

		} catch (Exception es) {
			System.err.println("HttpClientGoApi || deleteLog " + es.getMessage());
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
			properties.clear();
		}

	}

	public UserFrontDto validaToken(String token) {
		return getToken(token);
	}

	public String getCpfOuCnpj(String token) {
		return getToken(token).getCpfOuCnpj();
	}

	public UserFrontDto getUserDto(String token) {
		return getToken(token);
	}

	public Long getIdFuncionario(String token) {
		return getToken(token).getIdFuncionario();
	}
	
	public boolean temIdFuncionario(String token) {
		return getToken(token).getIdFuncionario() != null;
	}


	public Long getMatriculaFuncionario(String token) {
		UserFrontDto dto = getToken(token);
		return dto.getMatricula();
	}


}

class ResponseLogApi {

	private String type;

	private List<LogApi> data;

	private List<String> messages;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public List<LogApi> getData() {
		return data;
	}

	public void setData(List<LogApi> data) {
		this.data = data;
	}

}

class ResponseApiGo {

	private String type;

	private TokenUser data;

	private List<String> messages;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TokenUser getData() {
		return data;
	}

	public void setData(TokenUser data) {
		this.data = data;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}

class LogApi {

	private String id;

	private String metodo;

	private String stack;

	private String parametro;

	private String dataLog;

	private String ambiente;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getDataLog() {
		return dataLog;
	}

	public void setDataLog(String dataLog) {
		this.dataLog = dataLog;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

}