package br.org.crea.commons.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * <pre>
 * Acesso ao arquivo de propriedades com as chaves
 * de configura��o.
 *
 */
public class PropertiesFile {

	public static final String VSIGNER_AUTH_URL_KEY = "valid.auth.url";
	public static final String VSIGNER_EXT_SIGN_URL_KEY = "valid.signer.url";
	public static final String USER_CODE_KEY = "valid.user.code";
	public static final String CONTRACT_UUID_KEY = "valid.contract.uuid";
	public static final String PDF_FILE_TO_SIGN_KEY = "pdf.file.to.sign";
	public static final String PDF_FILE_SIGNED_KEY = "pdf.file.signed";
	public static final String CLIENT_PFX_FILE = "valid.client.pfx.file";
	public static final String CLIENT_PFX_PASSWORD = "valid.client.pfx.password";
	public static final String CLIENT_TRUSTSTORE_FILE = "valid.truststore.file";

	public static String getKeyValue(String key) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("C:\\wildfly-9.0.1.Final\\standalone\\configuration\\rest-api.properties"));
			return (String) properties.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

}
