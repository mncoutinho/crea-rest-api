//package br.org.crea.restapi.permissionamento;
//
//import java.io.ByteArrayOutputStream;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.junit.Test;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//public class PermissionamentoTest {
//	
//	
//	HttpClient httpClient;
//	
//	@Test
//	public void testeCouch() {
//		
//		httpClient = new DefaultHttpClient();
//
//		try {
//
//			HttpGet request = new HttpGet( "http://apicouchdb.crea-rj.org.br/servicos-ramos/4bccef7b1c524886f0017dc018e3ea3f");
//			request.addHeader("Content-Type", "application/json");
//
//			HttpResponse response = httpClient.execute(request);
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			HttpEntity e = response.getEntity();
//
//			if (e != null) {
//				e.writeTo(baos);
//				EntityUtils.consume(e);
//
//				Gson gson = new GsonBuilder().create();
//				Object responseApi = gson.fromJson(baos.toString("UTF-8"), Object.class);
//				
//				System.out.println(responseApi);
//
//			}
//
//		} catch (Exception es) {
//			System.err.println("HttpClientGoApi || getToken " + es.getMessage());
//		} 
//
//	
//
//	}
//
//}
