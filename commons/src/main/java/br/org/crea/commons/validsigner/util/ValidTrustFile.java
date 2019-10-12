package br.org.crea.commons.validsigner.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;



/**
 * Configura o canal SSL client/server para autenticacao do usuario do servico.
 * Funções auxiliares de cálculo de hash e parser de certificado digital.
 *
 *@author Monique Santos
 *@since 06/2018
 */
public class ValidTrustFile {
	
	@Inject 
	private Properties properties;
	
	@Inject
	static HttpClientGoApi httpGoApi;
	
	private String pathClientPfx;
	
	private String keyClientPfx;
	
	private String pathTrustStore;
	
	@PostConstruct
	public void before () {
		this.pathClientPfx = properties.getProperty("valid.client.pfx.file");
		this.keyClientPfx = properties.getProperty("valid.client.pfx.password");
		this.pathTrustStore = properties.getProperty("valid.truststore.file");
	}

	@PreDestroy
	public void reset () {
		properties.clear();
	}

	private static boolean sslConfigured = false;
	
	
	/**
	 * Inicia contexto SSL com keyStore(cliente) e trustStore(servidor)
	 * */
	public synchronized void configuraCanalSSL() {
		
		if (sslConfigured) {
			return;
		}
		
		try {
			
			HostnameVerifier hostNameVerifier = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
		
			HttpsURLConnection.setDefaultHostnameVerifier(hostNameVerifier);
			SSLContext contextoSSL = SSLContext.getInstance("SSL");
			contextoSSL.init(configuraKeyStoreCliente().getKeyManagers(), configuraTrustStoreServidor().getTrustManagers(), new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(contextoSSL.getSocketFactory());
		
			sslConfigured = true;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidTrustFile || configuraCanalSSL", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
	}
	
	
	public void configurarSSL(final URL url) throws  Exception {
		
		if(url.toString().contains("https")) {
			HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(new FileInputStream(pathClientPfx), keyClientPfx.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(keyStore, keyClientPfx.toCharArray());
			
			KeyStore trustStore = KeyStore.getInstance("JKS");
			trustStore.load(new FileInputStream(pathTrustStore), null);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			
			SSLContext ctx = SSLContext.getInstance("SSL");
			ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
			
			HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
			sslConfigured = true;
		}
		
	}
	
	/**
	 * Inicia truststore com certificado do servidor.  
	 * Arquivo JKS atualizado em 06/2018 
	 * */
	public TrustManagerFactory configuraTrustStoreServidor() {
		
		try {
			
			KeyStore trustStore = KeyStore.getInstance("JKS");
			trustStore.load(new FileInputStream(pathTrustStore), null);
			
			TrustManagerFactory trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManager.init(trustStore);
			
			return trustManager;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidTrustFile || configuraTrustStoreServidor", StringUtil.convertObjectToJson(pathTrustStore), e);
		}
		return null;
	}
	
	/**
	 * Inicia keystore com certificado e chaves do cliente SSL
	 * Arquivo PFX atualizado em 06/2018 
	 * */
	public KeyManagerFactory configuraKeyStoreCliente() {
		
		try {
			
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(new FileInputStream(pathClientPfx),keyClientPfx.toCharArray());
			
			KeyManagerFactory keyManager = KeyManagerFactory.getInstance("SunX509");
			keyManager.init(keyStore, keyClientPfx.toCharArray());
			
			return keyManager;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ValidTrustFile || configuraKeyStoreCliente", StringUtil.convertObjectToJson(pathTrustStore), e);
		}
		return null;
	}
	

	public byte[] calcHash(byte[] buffer) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(buffer);
	}

	public X509Certificate getX509SignerCertificate(String pemCertificate) throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate x509Certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(pemCertificate.getBytes()));
		return x509Certificate;
	}
	
}
