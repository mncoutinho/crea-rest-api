package br.org.crea.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <pre>
 * Fun��es auxiliares de acesso a arquivo.
 * 
 *
 */
public class PDFFile {

	public static byte[] getPdfContent() throws Exception {
		File f = new File(PropertiesFile.getKeyValue(PropertiesFile.PDF_FILE_TO_SIGN_KEY));
		byte[] content = new byte[(int) f.length()];

		FileInputStream fin = new FileInputStream(PropertiesFile.getKeyValue(PropertiesFile.PDF_FILE_TO_SIGN_KEY));
		fin.read(content);
		fin.close();

		return content;
	}

	public static void savePdfContent(byte[] content) throws Exception {
		File f = new File(PropertiesFile.getKeyValue(PropertiesFile.PDF_FILE_SIGNED_KEY));
		FileOutputStream fout = new FileOutputStream(f);
		fout.write(content);
		fout.close();
	}

	public static int getPdfLength() throws Exception {
		File f = new File(PropertiesFile.getKeyValue(PropertiesFile.PDF_FILE_TO_SIGN_KEY));
		return (int) f.length();
	}

}
