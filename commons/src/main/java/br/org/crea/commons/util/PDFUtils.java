package br.org.crea.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

public class PDFUtils {

	ByteArrayOutputStream outputByteArrayPDF = new ByteArrayOutputStream();

	private List<InputStream> pdfs = new ArrayList<InputStream>();

	public PDFUtils adicionar(byte[] bytes) {

		try {
			pdfs.add(new ByteArrayInputStream(bytes));
			return this;
		} catch (Exception e) {
			throw new IllegalArgumentException("Documento não encontrado: ", e);
		}
	}

	public void convertePDF(String arquivo, String extensao) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("java -jar /opt/jdconverter/jodconverter-core-3.0-beta-4.jar ");
			sb.append("/opt/arquivos/" + arquivo + extensao + " ");
			sb.append("/opt/arquivos/" + arquivo + ".pdf");
			Runtime.getRuntime().exec(sb.toString());
		} catch (Exception e) {
			throw new IllegalArgumentException("Erro ao conterter para pdf: ",
					e);
		}
	}

	public PDFUtils concatenar() {
		try {
			if (pdfs.size() > 0) {
				PDFUtils.concatPDFs(pdfs, outputByteArrayPDF);
			}

			return this;

		} catch (Exception e) {
			throw new IllegalArgumentException("Documento não encontrado!");
		}
	}

	public static void concatPDFs(List<InputStream> streamOfPDFFiles,
			OutputStream outputStream) throws IOException, DocumentException {

		List<PdfReader> pdfs = new ArrayList<PdfReader>();

		int contadorTotalPaginas = 0;

		for (InputStream localPdf : streamOfPDFFiles) {
			PdfReader pdfReader = new PdfReader(localPdf);
			pdfs.add(pdfReader);
			contadorTotalPaginas += pdfReader.getNumberOfPages();
		}

		Document document = new Document();
		PdfCopy copy = new PdfCopy(document, outputStream);
		document.open();

		int numeroPaginas = 0;
		int contadorPaginas = 0;
		PdfImportedPage page;
		PdfCopy.PageStamp stamp = null;

		for (PdfReader pdf : pdfs) {
			numeroPaginas = pdf.getNumberOfPages();

			for (int i = 0; i < numeroPaginas;) {
				contadorPaginas++;
				page = copy.getImportedPage(pdf, ++i);

				stamp = copy.createPageStamp(page);

				// XXX Rodapé contador de páginas
				// Rectangle mediabox = pdf.getPageSize(1);
				// float larguraPagina = mediabox.getRight();
				// ColumnText.showTextAligned(stamp.getOverContent(),
				// Element.ALIGN_CENTER, new
				// Phrase(String.format("Página %d de %d", contadorPaginas,
				// contadorTotalPaginas)), (larguraPagina - 70), 25,
				// 0);

				stamp.alterContents();
				copy.addPage(page);

			}

		}

		document.close();

	}

	public byte[] buildFile() throws IOException {

		ByteArrayOutputStream baos = outputByteArrayPDF;
		return baos.toByteArray();

	}

}