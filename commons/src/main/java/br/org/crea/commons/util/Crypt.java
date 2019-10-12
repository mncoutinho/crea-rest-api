package br.org.crea.commons.util;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

/**
 * <pre>
 * Configura o canal SSL client/server para autenticacao 
 * do usuario do servico.
 * 
 * Fun��es auxiliares de c�lculo de hash e parser de
 * certificado digital.
 *
 */
public class Crypt {

	private static boolean sslConfigured = false;

	public static synchronized void configureSSLChannel() throws Exception {
		if (sslConfigured) {
			return;
		}

		/**
		 * Nao e recomendado sobrescrever o HostnameVerifier em producao. Isso
		 * pode ser feito durante a homologacao para desprezar algumas
		 * configuracoes de ambiente
		 */

		HostnameVerifier hv = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				// TODO Auto-generated method stub
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		// Inicia keystore com certificado e chaves do cliente SSL
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(new FileInputStream(PropertiesFile.getKeyValue(PropertiesFile.CLIENT_PFX_FILE)), PropertiesFile.getKeyValue(PropertiesFile.CLIENT_PFX_PASSWORD).toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(keyStore, PropertiesFile.getKeyValue(PropertiesFile.CLIENT_PFX_PASSWORD).toCharArray());

		// Inicia truststore com certificado do servidor
		KeyStore trustStore = KeyStore.getInstance("JKS");
		trustStore.load(new FileInputStream(PropertiesFile.getKeyValue(PropertiesFile.CLIENT_TRUSTSTORE_FILE)), null);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(trustStore);

		// Inicia contexto SSL com keyStore e trustStore
		SSLContext ctx = SSLContext.getInstance("SSL");
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());

		HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());

		sslConfigured = true;
	}


}
