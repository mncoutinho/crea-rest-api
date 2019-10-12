package br.org.crea.commons.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class ItextUtil {
	
	public static final String RETRATO = "RETRATO";
	
	public static final String PAISAGEM = "PAISAGEM";
	
	public static final String PROTOCOLO = "PROTOCOLO";
	
	public static final String ARQUIVO = "ARQUIVO";
	
	public static final String BYTE = "BYTE";
	
	private static String nomeArquivo;
	
	private static Document documento;
	
	private static File arquivo;

	private static PdfWriter writer;
	
	private static ByteArrayOutputStream baos;

	public static Document getDocumento() {
		return documento;
	}


	private static void setDocumentoA4(String orientacao) {
		if (orientacao.equals(RETRATO)) {
			documento = new Document(PageSize.A4, 25, 28, 20, 26);
		} else if (orientacao.equals(PAISAGEM)) {
			documento = new Document(PageSize.A4.rotate(), 25, 28, 20, 26);
		} else if (orientacao.equals(PROTOCOLO)) {
			documento = new Document(PageSize.A4);
		}
	}
	
	public static File getArquivo() {
		return arquivo;
	}

	private static void setArquivo(String nomeArquivo, String caminhoCompleto) {
		arquivo = new File(caminhoCompleto);
	}

	public static PdfWriter getWriter() {
		return writer;
	}

	private static void setWriter(String tipo) {

		try {
			if (tipo.equals(ARQUIVO)) {
				writer = PdfWriter.getInstance(documento, new FileOutputStream(arquivo));
			} else if (tipo.equals(BYTE)){
				baos = new ByteArrayOutputStream();
				writer = PdfWriter.getInstance(documento, baos);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static void iniciarDocumentoParaDownload (String nome, String orientacao) {
		nomeArquivo = nome;
		setDocumentoA4(orientacao);
		setWriter(BYTE); // não salvará arquivo
		adicionaMetadados(nome);
		
	}
	
	public static void iniciarDocumentoParaArquivo (String nome, String orientacao, String caminhoCompleto) {
		nomeArquivo = nome;
		setArquivo(nome, caminhoCompleto);
		setDocumentoA4(orientacao);
		setWriter(ARQUIVO); // salvará arquivo
		adicionaMetadados(nome);
	}
	
	private static void adicionaMetadados (String titulo) {
		documento.addTitle(titulo);
		documento.addSubject("Crea-RJ - Sistema Corporativo");
		documento.addAuthor("Crea-RJ - Sistema Corporativo");
	}
	
	public static void adicionaCabecalhoPadrao () {
		
		try {
			// Cabeçalho - Imagem do Brasão

			Image imagem = Image.getInstance("/opt/arquivos/brasao.png");
			imagem.scalePercent(5);
			imagem.setAlignment(Element.ALIGN_CENTER);


			Table table = new Table(1);
			table.setAlignment(Table.ALIGN_CENTER);
			table.setBorder(Table.NO_BORDER);

			Cell cell = new Cell();
			cell.setBorder(Cell.NO_BORDER);
			cell.add(imagem);
			table.addCell(cell);

			// Font - Cabeçalho
			Font fonteCabecalho = FontFactory.getFont(FontFactory.COURIER, (float) 12.0, Font.BOLD);
			
			// Cabeçalho - Texto
			Phrase conteudoCabecalho = new Phrase();
			conteudoCabecalho.add(table);

			Phrase texto = new Phrase("SERVIÇO PÚBLICO FEDERAL\n\n" + "CONSELHO REGIONAL DE ENGENHARIA E AGRONOMIA DO RIO DE JANEIRO\n" + "CREA-RJ", fonteCabecalho);
			
			conteudoCabecalho.add(texto);

			HeaderFooter cabecalho = new HeaderFooter(conteudoCabecalho, false);
			cabecalho.setAlignment(HeaderFooter.ALIGN_CENTER);
			cabecalho.setBorder(Rectangle.NO_BORDER);
			documento.setHeader(cabecalho);
			
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void adicionaRodapePadrao () {
		
		Font fonteRodape = FontFactory.getFont(FontFactory.COURIER, (float) 10.0, Font.NORMAL);
		
		Phrase texto = new Phrase("Rua Buenos Aires, nº 40 - Centro - 20.070-022 - Rio de Janeiro - RJ\n" + "crea-rj@crea-rj.org.br - www.crea-rj.org.br", fonteRodape);


		HeaderFooter rodape = new HeaderFooter(texto, false);
		rodape.setAlignment(HeaderFooter.ALIGN_CENTER);
		rodape.setBorder(Rectangle.NO_BORDER);
		documento.setFooter(rodape);
	}
	
	public static void iniciarDocumento () {
		documento.open();
	}
	
	public static void fecharDocumento() {
		documento.close();
	}

	
	public static void adicionaConteudo (Object objeto) {
		Phrase linha = new Phrase();
		linha.add(objeto);
		try {
			documento.add(linha);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static void adicionaObjetoAoConteudoEFecha (Object objeto) {
		
		try {
			Paragraph textoTopo = new Paragraph(nomeArquivo + " - " + DateUtils.format(new Date(), DateUtils.DD_MM_YYYY_HH_MM_SS) + "\n\n");
			textoTopo.setFont(FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD));
			textoTopo.setAlignment(Element.ALIGN_CENTER);
			
			documento.add(textoTopo);
			
			Phrase linha = new Phrase();
			linha.add(objeto);
			documento.add(linha);
			
			documento.close();
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static void adicionaObjetoAoConteudo (Object objeto) {
		
		try {
			
			Phrase linha = new Phrase();
			linha.add(objeto);
			documento.add(linha);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
		
	public static void adicionaObjetoAoConteudoSemNomeDoArquivo (Object objeto) {
		
		try {
			// Conteúdo
			Phrase linha = new Phrase();
			linha.add(objeto);
			documento.add(linha);
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static void adicionaParagrafoAoConteudoSemNomeDoArquivo (Paragraph paragraph) {
		
		try {
			// Conteúdo
			documento.add(paragraph);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] getPdfBytes () {
		return baos.toByteArray();
	}

}