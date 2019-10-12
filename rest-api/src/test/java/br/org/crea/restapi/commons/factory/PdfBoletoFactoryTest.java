package br.org.crea.restapi.commons.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BarcodeInter25;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import br.org.crea.commons.dao.corporativo.GeradorSequenciaDao;
import br.org.crea.commons.util.ItextUtil;

public class PdfBoletoFactoryTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private GeradorSequenciaDao geradorSequenciaDao;
	
	private String IMAGE = "/opt/arquivos/rp.gif";
	
	private final float IMAGEM_MARKETING_WIDTH = 511.2f;
	private final float IMAGEM_MARKETING_HEIGHT = 3341.3f;
	
	private final float IMAGEM_BOLETO_WIDTH = 530.0f;
	private final float IMAGEM_BOLETO_HEIGHT = 865.0f;
	
	private final float IMAGEM_BANCO_WIDTH = 100.0f;
	private final float IMAGEM_BANCO_HEIGHT = 23.0f;



	
	Image imgReabilitacaoRegistroFrente = null;
	
	Image imgTitulo = null;
	
	Image imgBanco = null;

	
	@Before
	public void setup() {
		 emf = Persistence.createEntityManagerFactory("dsCreaTest");
		 em = emf.createEntityManager();
		 geradorSequenciaDao = new GeradorSequenciaDao();
		 geradorSequenciaDao.setEntityManager(em);
	}
	
	@After
	public void finishTest() {
		em.close();
		emf.close();
	}
	
	@Test
	public void geraPDFTest () {
		
		this.geraPdf();
	}
	
	
	public void geraPdf() {
		
		
		ItextUtil.iniciarDocumentoParaArquivo("protocolo", ItextUtil.PROTOCOLO, "/opt/arquivos/meuboleto.pdf");
		ItextUtil.iniciarDocumento();
		
		try {
			
			
			PdfContentByte canvas = ItextUtil.getWriter().getDirectContentUnder();	
			
			//gera template com o fundo do boleto
			imgTitulo = Image.getInstance("/opt/arquivos/boleto_bb1.gif");
			imgTitulo.scaleToFit(IMAGEM_BOLETO_WIDTH,IMAGEM_BOLETO_HEIGHT);
			imgTitulo.setAbsolutePosition(0,0);
			
			imgBanco = Image.getInstance("/opt/arquivos/1.gif");
			imgBanco.scaleToFit(IMAGEM_BANCO_WIDTH,IMAGEM_BANCO_HEIGHT);
			imgBanco.setAbsolutePosition(0,0);


			PdfTemplate tempImgReabilitacaoRegistroFrente = canvas.createTemplate(IMAGEM_MARKETING_WIDTH,IMAGEM_MARKETING_HEIGHT);

	        
	        
	        float altura = 358;

			BaseFont bfTextoSimples = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
			BaseFont bfTextoCB = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);

			// Inicio das descricoes do Requerimento
			ItextUtil.getDocumento().newPage();
			canvas.addTemplate(tempImgReabilitacaoRegistroFrente, ItextUtil.getDocumento().left(), ItextUtil.getDocumento().top()-765); // 750

			
			
			PdfTemplate tempImgBoleto = canvas.createTemplate(IMAGEM_BOLETO_WIDTH,IMAGEM_BOLETO_HEIGHT);
			PdfTemplate tempImgBanco = canvas.createTemplate(IMAGEM_BANCO_WIDTH,IMAGEM_BANCO_HEIGHT);
			
			tempImgBoleto = canvas.createTemplate(IMAGEM_BOLETO_WIDTH,IMAGEM_BOLETO_HEIGHT);
	        tempImgBoleto.addImage(imgTitulo);

	        tempImgBoleto = canvas.createTemplate(IMAGEM_BOLETO_WIDTH,IMAGEM_BOLETO_HEIGHT);
	        tempImgBoleto.addImage(imgTitulo);

	        //gera template com a imagem do logo do banco
	        tempImgBanco = canvas.createTemplate(IMAGEM_BANCO_WIDTH,IMAGEM_BANCO_HEIGHT);
	        tempImgBanco.addImage(imgBanco);
			
			
	        canvas.addTemplate(tempImgBoleto, ItextUtil.getDocumento().left(),ItextUtil.getDocumento().top()-765); // 750
	        canvas.addTemplate(tempImgBanco, ItextUtil.getDocumento().left()+20,ItextUtil.getDocumento().top()-557); //535
	        canvas.addTemplate(tempImgBanco, ItextUtil.getDocumento().left()+20,altura+410);




	        













	        //inicio das descricoes do boleto
	        canvas.beginText();
	        canvas.setFontAndSize(bfTextoCB,10);

//	        if (boleto.getDescricoes() != null) {
//	            Vector descricoes = boleto.getDescricoes();
//	            for (int y=0; y < descricoes.size(); y++) {
	       int y = 1; // improvisado
	                canvas.setTextMatrix(ItextUtil.getDocumento().left(),ItextUtil.getDocumento().top()-87+y*15);
	                canvas.showText("");
//	            }
//	        }
	        canvas.endText();

	        //fim descricoes

	        canvas.beginText();
	        canvas.setFontAndSize(bfTextoSimples,8);

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30, altura+389);
	        canvas.showText("CREA-RJ - CONS. REG. ENGENHARIA E AGRONOMIA - CNPJ: 34.260.596/0001-80"); //imprime o cedente 

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+345,altura+389);
	        canvas.showText("30/06/2019");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+440,altura+389);
	        canvas.showText("52,86");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+355);
	        canvas.showText("13/06/2019");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+100,altura+355);
	        canvas.showText("28342400000120545");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+210,altura+355);
	        canvas.showText("RC");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+255,altura+355);
	        canvas.showText("N");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+290,altura+355);
	        canvas.showText("13/06/2019");

	        // ALTERADO POR GLADYSTON
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+360,altura+355);
	        canvas.showText("28342400000120545-1");


	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+100,altura+337);
	        canvas.showText("017/027");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+170,altura+337);
	        canvas.showText("R$");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+360,altura+337);          
	        canvas.showText("1769-8 / 8184-1");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30, altura+319);
	        canvas.showText("BERNARD TEIXEIRA MENDES HAZLEHURST");

	        //Registro 
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+300, altura+319);
	        canvas.showText("2008132882");

	        //         CPF ou CNPJ
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+400, altura+319);
	        canvas.showText("120.757.027-30");


	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+303);
	        canvas.showText("RUA BUENOS AIRES, 40, SL 908");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+360,altura+303);
	        canvas.showText("CENTRO");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+360,altura+287);
	        canvas.showText("RJ");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+287);
	        canvas.showText("RIO DE JANEIRO");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+395,altura+287);
	        canvas.showText("20070-022)");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+268);
	        canvas.showText("Taxa expedicao de carteira ou 2 via - Exercício 2019");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+258);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+248);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+238);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+228);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+218);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+208);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura+198);
//	        if (boleto.getInstrucao8() == null || boleto.getInstrucao8().trim().equals("")) {
	            canvas.showText("NÃO RECEBER APÓS O VENCIMENTO. Desconsiderar se quitado.");
//	        } else {
//	            canvas.showText("boleto.getInstrucao8()");
//	        }
//	        if (boleto.getInstrucao1().substring(0, 5).equals("Taxa ")) {
	            canvas.setTextMatrix(ItextUtil.getDocumento().left() + 30, altura + 188);
	            canvas.showText("O CREA-RJ SOMENTE ANALISARÁ O REQUERIMENTO DO SERVIÇO OBJETO");
	            canvas.setTextMatrix(ItextUtil.getDocumento().left() + 30, altura + 178);
	            canvas.showText("DESTE PROTOCOLO APÓS O PAGAMENTO DA RESPECTIVA TAXA.");
//	        }
//	        if (boleto.getInstrucao1().contains("Auto de Infração nº") || boleto.getInstrucao1().contains("MULTA: ") ) {
	            canvas.setTextMatrix(ItextUtil.getDocumento().left() + 20, 418);
	            canvas.showText("Somente o pagamento não regulariza o Auto de Infração");
//	        }
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+20, 408);
	        canvas.showText("Os serviços serão liberados após a compensação bancária.");
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+20, 398);
	        canvas.showText("Caso efetue o pagamento por meio de transação eletrônica (online, telefone, aplicativos), acompanhe");
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+20, 388);
	        canvas.showText("a efetivação do pagamento, face o limite de horário de compensação do boleto.");

	        
	        canvas.endText();

	        altura = 350;

	        canvas.beginText();
	        canvas.setFontAndSize(bfTextoCB,13);

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+135,altura-97);

	        canvas.showText("001-9");
	        canvas.endText();

	        canvas.beginText();
	        canvas.setFontAndSize(bfTextoCB,10);


	        // Acho que e o numero usado para pagamento
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+175,altura-100);
	        canvas.showText("00190.00009 02834.240000 00120.545173 1 79190000005286");
	        canvas.endText();

	        canvas.beginText();
	        canvas.setFontAndSize(bfTextoSimples,8);

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-119);
	        canvas.showText("Pagável em qualquer Banco até o vencimento.");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+5,altura-119); //121
	        canvas.showText("boleto.getLocalPagamento2()");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+380,altura-119);
	        canvas.showText("30/06/2019");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-135);
	        canvas.showText("CREA-RJ - CONS. REG. ENGENHARIA E AGRONOMIA - CNPJ: 34.260.596/0001-80");

	        // ALTERADO POR GLADYSTON
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+380,altura-135);
	        canvas.showText("1769-8 / 8184-1");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-152);
	        canvas.showText("13/06/2019");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+100,altura-152);
	        canvas.showText("28342400000120545");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+210,altura-152);
	        canvas.showText("RC");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+255,altura-152);
	        canvas.showText("N");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+290,altura-152);
	        canvas.showText("13/06/2019");

	        // ALTERADO POR GLADYSTON
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+380,altura-152);
	        canvas.showText("28342400000120545-1");

	        // ALTERADO POR GLADYSTON
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+100,altura-170);
	        canvas.showText("017/027");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+170,altura-170);
	        canvas.showText("R$");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+440,altura-170);
	        //canvas.showText(Validacoes.formataMoedaSemCifraoBoleto(new BigDecimal(boleto.getValorBoleto())));
	        canvas.showText("52,86");

	        altura = 350;
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-185);
	        canvas.showText("Taxa expedicao de carteira ou 2 via - Exercício 2019");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-195);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-205);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-215);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-225);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-235);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-245);
	        canvas.showText("");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+30,altura-255);
//	        if (boleto.getInstrucao8() == null || boleto.getInstrucao8().trim().equals("")) {
	            canvas.showText("NÃO RECEBER APÓS O VENCIMENTO. Desconsiderar se quitado.");
//	        } else {
//	            canvas.showText("boleto.getInstrucao8()");
//	        }


	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+60,altura-265);//263
	        canvas.showText("BERNARD TEIXEIRA MENDES HAZLEHURST") ;

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+380,altura-265);
	        canvas.showText( "CPF/CNPJ: "+ "120.757.027-30"); 

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+60,altura-276);
//	        if (boleto.getEnderecoSacado().length() < 60) {
//	            canvas.showText("boleto.getEnderecoSacado()+ \" \" + boleto.getBairroSacado()");
//	        } else {
	            canvas.showText("RUA BUENOS AIRES, 40, SL 908");
//	        }
	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+60,altura-287);
	        canvas.showText("20070-022 + \" - \" + RIO DE JANEIRO + \" \" + RJ");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+380,altura-287);
	        canvas.showText( "REGISTRO: "+ "2008132882");

	        canvas.endText();

	        BarcodeInter25 code = new BarcodeInter25();
	        code.setCode("0190000090283424000000120545173179190000005286");
	        code.setExtended(true);

	        code.setTextAlignment(Element.ALIGN_LEFT);
	        code.setBarHeight(27.00f);
	        code.setFont(null);
	        code.setX(0.73f);
	        code.setN(3);

	        PdfTemplate imgCB = code.createTemplateWithBarcode(canvas,null,null);
	        canvas.addTemplate(imgCB,55,7); // 40   10


	        
	        System.out.println("FIM PDF");
			
			
			
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		
		
		ItextUtil.fecharDocumento();
		
		
	  
	}
}
