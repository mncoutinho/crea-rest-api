package br.org.crea.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.pdf.codec.Base64;

public class FileUtils {

	public static boolean delete(String localArquivo) {
		try {
			File file = new File(localArquivo);
			file.delete();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static byte[] converteInputStreamParaByteArray(InputStream arquivo) {
		
		try {
			
			byte[] bytes = IOUtils.toByteArray(arquivo);
			return bytes;

		} catch (IOException e) {
			System.out.println("FileUtils || converteFileParaBytes : " + e.getMessage());
		}
		return null;
	}
	
	public static InputStream converteByteArrayParaInputStream(byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}

	public static ByteArrayInputStream converteInputStreamParaByteArrayInput(InputStream arquivo) {
		byte currentXMLBytes[] = arquivo.toString().getBytes();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(currentXMLBytes);
		return byteArrayInputStream;
	}

	public static String obterExtensaoDoArquivo(String nomeArquivo) {
		return nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1, nomeArquivo.length()).toLowerCase();
	}

	public static String obterContentTypeArquivo(String nomeArquivo) {
		switch (obterExtensaoDoArquivo(nomeArquivo)) {
		case "jpg":
			return "image/jpg";
		case "pdf":
			return "application/pdf";
		case "xls":
			return "application/vnd.ms-excel";
		case "xlsx":
			return "application/vnd.ms-excel";
		case "odt":
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		default:
			return "";
		}
	}
	
	public static Long obterTamanhoDoArquivo(String caminhoArquivo) {
		File arquivo = new File(caminhoArquivo);
		return arquivo.length();
	}

	public static boolean converteBytesParaFile(byte[] bytes, String destinoArquivo) {
		try {

			FileOutputStream fos = new FileOutputStream(destinoArquivo);
			fos.write(bytes);
			fos.flush();
			fos.close();

			return true;

		} catch (IOException e) {
			System.out.println("Erro ao converter byte para File : " + e.getMessage());
			return false;
		}
	}
	
	@SuppressWarnings("resource")
	public static byte[] converteFileParaBytes(File file) {
		
		int length = (int)file.length();  
	    byte[] arrayBytes = new byte[length];
	    FileInputStream inFile = null;
		
		try {
			
			inFile = new FileInputStream(file);         
	        inFile.read(arrayBytes, 0, length);  
			
		} catch (IOException e) {
			System.out.println("FileUtils || converteFileParaBytes : " + e.getMessage());
		}
		 return arrayBytes;
	}

	public static void criaDiretorio(String diretorioFinalPdf, String nomeDiretorio) {
		try {
			File diretorio = new File(diretorioFinalPdf + nomeDiretorio);
			if (!diretorio.exists()) {
				diretorio.mkdir();
			}
		} catch (Exception e) {
			System.out.println("Erro ao criar diretorio: " + e.getMessage());
		}

	}

	public static void criarDiretorio(String caminho) {

		File diretorio = new File(caminho);

		try {
			if (!diretorio.exists()) {
				diretorio.mkdirs();
			}
		} catch (Exception e) {
			new Exception("Arvore de Diretorio não pode ser Criada: " + caminho);
		}

	}

	public static boolean existeDocumento(String caminho) {

		if (caminho == null) {
			throw new IllegalArgumentException("Erro ao verificar se existe Documento: " + caminho);
		}

		File arquivo = new File(caminho);
		if (arquivo.exists()) {
			return true;
		}
		return false;

	}

	public static String tratarNome(String nome) {
		nome = StringUtil.removeAcentos(nome);
		nome = nome.replaceAll("\\ ", "_");
		return nome;
	}

	public void remover(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; ++i) {
				remover(files[i]);
			}
		}
		file.delete();
	}

	public static String obterExtensaoNomeDocumento(String localArquivo) {
		return localArquivo.substring(localArquivo.lastIndexOf("."), localArquivo.length());
	}

	public static String obterNomeDocumentoSemExtensao(String nomeDocumento) {
		return nomeDocumento.substring(0, nomeDocumento.lastIndexOf("."));
	}
	
	public static void salvaContentPdf(byte[] content, String path) {
		
		try {
			
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content);
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getBase64FromByteArrayOutputStream(ByteArrayOutputStream baos) {
		return Base64.encodeBytes(baos.toByteArray());
	}
	
	public static ByteArrayOutputStream getByteArrayOutputStreamFromBase64(String base64) {
		byte[] ba = Base64.decode(base64);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(ba.length);
		baos.write(ba, 0, ba.length);
		return baos;
	}

}
