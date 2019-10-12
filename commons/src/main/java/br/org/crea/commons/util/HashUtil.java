package br.org.crea.commons.util;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class HashUtil {

	private static final String hexDigits = "0123456789abcdef";

	public static byte[] digest(byte[] input, String algoritmo)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algoritmo);
		md.reset();
		return md.digest(input);
	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			int j = ((int) b[i]) & 0xFF;
			buf.append(hexDigits.charAt(j / 16));
			buf.append(hexDigits.charAt(j % 16));
		}

		return buf.toString();
	}


	public static String criptografa(String password) {

		try {
			byte[] passwordByte = digest(password.getBytes(), "md5");
			password = byteArrayToHexString(passwordByte);
			return password;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public static byte[] calcHashAssinatura(byte[] buffer) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(buffer);
	}

	public static X509Certificate getCertificateDigital(String pemCertificate) throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate x509Certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(pemCertificate.getBytes()));
		return x509Certificate;
	}

}
