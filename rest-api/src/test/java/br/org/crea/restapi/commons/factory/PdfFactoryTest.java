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
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import br.org.crea.commons.dao.corporativo.GeradorSequenciaDao;
import br.org.crea.commons.util.ItextUtil;

public class PdfFactoryTest {

	private EntityManagerFactory emf;
	private EntityManager em;
	private GeradorSequenciaDao geradorSequenciaDao;
	
	private String IMAGE = "/opt/arquivos/rp.gif";
	
	private final float IMAGEM_MARKETING_WIDTH = 511.2f;
	private final float IMAGEM_MARKETING_HEIGHT = 3341.3f;
	
	private final float IMAGEM_BOLETO_WIDTH = 530.0f;
	private final float IMAGEM_BOLETO_HEIGHT = 865.0f;

	
	Image imgReabilitacaoRegistroFrente = null;

	
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
		
		
		ItextUtil.iniciarDocumentoParaArquivo("protocolo", ItextUtil.PROTOCOLO, "/opt/arquivos/meupdf.pdf");
		ItextUtil.iniciarDocumento();
		
		try {
			
			
			PdfContentByte canvas = ItextUtil.getWriter().getDirectContentUnder();			
			
			
			//gera template para a Reabilitação de Registro
			imgReabilitacaoRegistroFrente = Image.getInstance(IMAGE);
			imgReabilitacaoRegistroFrente.scaleToFit(IMAGEM_BOLETO_WIDTH,IMAGEM_BOLETO_HEIGHT);
			imgReabilitacaoRegistroFrente.setAbsolutePosition(0,0);


			PdfTemplate tempImgReabilitacaoRegistroFrente = canvas.createTemplate(IMAGEM_MARKETING_WIDTH,IMAGEM_MARKETING_HEIGHT);
	        tempImgReabilitacaoRegistroFrente.addImage(imgReabilitacaoRegistroFrente);    
	        
	        
	        float altura = 358;

			BaseFont bfTextoSimples = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
			BaseFont bfTextoCB = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);

			// Inicio das descricoes do Requerimento
			ItextUtil.getDocumento().newPage();
			canvas.addTemplate(tempImgReabilitacaoRegistroFrente, ItextUtil.getDocumento().left(), ItextUtil.getDocumento().top()-770); // 750

			
			canvas.beginText();
			canvas.setFontAndSize(bfTextoCB,10);

			canvas.setTextMatrix(ItextUtil.getDocumento().left()+363, altura+383);
			canvas.showText("201970001234");

	        canvas.setFontAndSize(bfTextoCB,8);

	        
            canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura+283);
            canvas.showText("BERNARD TEIXEIRA MENDES HAZLEHURST"); 
	        

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura+258);
	        canvas.showText("BRIAN HAZLEHURST"); 

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura+234);
	        canvas.showText("JURACY DA SILVA TEIXEIRA MENDES"); 

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura+209);
	        canvas.showText("BRASILEIRA"); 

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+351, altura+209);
	        canvas.showText("RJ"); 

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+182, altura+209);
	        canvas.showText("RIO DE JANEIRO");

	        //if(pessoaFisica.getNacionalidade() != null && "BRASILEIRA".equalsIgnoreCase(pessoaFisica.getNacionalidade().getDescricao())){
	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+404, altura+209);
	            canvas.showText("BRASIL");
	        //}

	        //if("s".equalsIgnoreCase(pessoaFisica.getEstadoCivil().getCodigoConfea())){ //solteiro
	         //   canvas.setTextMatrix(ItextUtil.getDocumento().left()+59, altura+187);
	          //  canvas.showText("x");
	       // }

	        //if("c".equalsIgnoreCase(pessoaFisica.getEstadoCivil().getCodigoConfea())){ //casado
	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+100, altura+187);
	            canvas.showText("x");
	       // }

	       // if("j".equalsIgnoreCase(pessoaFisica.getEstadoCivil().getCodigoConfea())){ //separado judicialmente
	           // canvas.setTextMatrix(ItextUtil.getDocumento().left()+141, altura+187);
	          //  canvas.showText("x");
	       // }

	       // if("d".equalsIgnoreCase(pessoaFisica.getEstadoCivil().getCodigoConfea())){ //divorciado
	          //  canvas.setTextMatrix(ItextUtil.getDocumento().left()+228, altura+187);
	          //  canvas.showText("x");
	       // }
	       // if("v".equalsIgnoreCase(pessoaFisica.getEstadoCivil().getCodigoConfea())){ //viúvo
	           // canvas.setTextMatrix(ItextUtil.getDocumento().left()+278, altura+187);
	         //   canvas.showText("x");
	        //}

	        //if("v".equalsIgnoreCase(pessoaFisica.getEstadoCivil().getCodigoConfea())){ //outros
	          //  canvas.setTextMatrix(ItextUtil.getDocumento().left()+314, altura+187);
	            //canvas.showText("x");
	       // }

	       // if(pessoaFisica.getTipoSexo().getCodigo() == 0){ //sexo masculino
	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+412, altura+187);
	            canvas.showText("x");
	        //}

	     //   if(pessoaFisica.getTipoSexo().getCodigo() == 1){ //sexo feminino
	      //      canvas.setTextMatrix(ItextUtil.getDocumento().left()+463, altura+187);
	       //     canvas.showText("x");
	      //  }

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+60, altura+157);
	        canvas.showText("13/01/1991");

	      //  if("n".equalsIgnoreCase(pessoaFisica.getNecessidadeEspecial().getCodigoConfea().trim())){ //nenhuma necessidade especial.
	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+138, altura+157);
	            canvas.showText("x");
	     //   }

	       // if(!"n".equalsIgnoreCase(pessoaFisica.getNecessidadeEspecial().getCodigoConfea().trim())){ //possui necessidade especial.
	         //   canvas.setTextMatrix(ItextUtil.getDocumento().left()+163, altura+157);
	          //  canvas.showText("x");

	         //   canvas.setTextMatrix(ItextUtil.getDocumento().left()+210, altura+156);
	         //   canvas.showText(tratarValor(pessoaFisica.getNecessidadeEspecial().getDescricao()));
	       // }

	    //    if("a".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getTipoSanguineo())) && "p".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getFatorRh()))){
	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+343, altura+157);
	            canvas.showText("x");

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+429, altura+157);
	            canvas.showText("x");
	   //     }

	   //     if("a".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getTipoSanguineo())) && "n".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getFatorRh()))){
	  //          canvas.setTextMatrix(ItextUtil.getDocumento().left()+343, altura+157);
	  ///          canvas.showText("x");
//
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+469, altura+157);
//	            canvas.showText("x");
//	        }
//
//	        if("b".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getTipoSanguineo())) && "p".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getFatorRh()))){
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+362, altura+157);
//	            canvas.showText("x");
//
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+429, altura+157);
//	            canvas.showText("x");
//	        }
//
//	        if("b".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getTipoSanguineo())) && "n".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getFatorRh()))){
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+362, altura+157);
//	            canvas.showText("x");
//
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+469, altura+157);
//	            canvas.showText("x");
//	        }

//	        if("ab".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getTipoSanguineo())) && "p".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getFatorRh()))){
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+381, altura+157);
//	            canvas.showText("x");
//
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+429, altura+157);
///	            canvas.showText("x");
//	        }
//
//	        if("ab".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getTipoSanguineo())) && "n".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getFatorRh()))){
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+381, altura+157);
//	            canvas.showText("x");
//
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+469, altura+157);
//	            canvas.showText("x");
//	        }
//
//	        if("o".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getTipoSanguineo())) && "p".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getFatorRh()))){
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+400, altura+157);
//	            canvas.showText("x");
//
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+429, altura+157);
//	            canvas.showText("x");
//	        }

//	        if("o".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getTipoSanguineo())) && "n".equalsIgnoreCase(tratarValor(pessoaFisica.getFatorRH().getFatorRh()))){
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+400, altura+157);
//	            canvas.showText("x");
//
//	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+469, altura+157);
//	            canvas.showText("x");
//	        }

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura+129);
	        canvas.showText("120.757.027-30");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+180, altura+129);
	        canvas.showText("123.0.1256-9");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+265, altura+129);
	        canvas.showText("22.370.589-8");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+350, altura+129);
	        canvas.showText("06/02/2009");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+450, altura+129);
	        canvas.showText("DETRAN-RJ");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura+103);
	        canvas.showText("100823568974"); 

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+206, altura+103);
	        canvas.showText("200");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+260, altura+103);
	        canvas.showText("10");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+310, altura+103);
	        canvas.showText("RIO DE JANEIRO");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+485, altura+103);
	        canvas.showText("RJ"); 

//	        if(endereco.getTipoEndereco().getPessoaFisica() == true && endereco.isEnderecoValido()){
	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura+78);
//	            if(endereco.getLogradouro() != null && !"".equalsIgnoreCase(endereco.getLogradouro())){
//	                String enderecoTemp = endereco.getLogradouro();
//	                if(endereco.getNumero()!= null && !"".equalsIgnoreCase(endereco.getNumero())){
//	                    enderecoTemp += ", " + tratarValor(endereco.getNumero());
//	                }
//	                if(endereco.getComplemento() != null && !"".equalsIgnoreCase(endereco.getComplemento())){
//	                    enderecoTemp += " - " + tratarValor(endereco.getComplemento());
//	                }
	                canvas.showText("RUA BUENOS AIRES, 40, SL 908");
//	            }

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura+50);
	            canvas.showText("CENTRO"); 

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+240, altura+50);
	            canvas.showText("RIO DE JANEIRO"); 

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+420, altura+50);
	            canvas.showText("RJ"); 

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+449, altura+50);
	            canvas.showText("20070-022");
	      //  }


	//        int i = 0;
	 //       for (Telefone telefoneTemp : telefone) {
	       //     if(!"COMERCIAL".equals(telefoneTemp.getTipoTelefone().getDescricao())){
	         //       if( i==0){
	                    canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura+23);
	           //     }
	             //   if( i==1){
	                 //   canvas.setTextMatrix(ItextUtil.getDocumento().left()+183, altura+23);
	               // }
	         //       if(telefoneTemp.getDdd() != null){
	                    canvas.showText("(21) 2179-2007");
	           //     }
	             //   i++;
	          //  }
	       // }

	       // for (CaixaPostal caixaPostalTemp : caixaPostal) {
	       //     canvas.setTextMatrix(ItextUtil.getDocumento().left()+318, altura+23);
	        //    canvas.showText(tratarValor(caixaPostalTemp.getCaixaPostal()));

	     
	    //        canvas.setTextMatrix(ItextUtil.getDocumento().left()+414, altura+23);
	     //       canvas.showText(tratarValor(caixaPostalTemp.getCaixaPostal())); 
	    //    }


//	        if(endereco.getTipoEndereco().getPessoaJuridica() == true && endereco.isEnderecoValido()){

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura-8);
//	            if(endereco.getLogradouro() != null && !"".equalsIgnoreCase(endereco.getLogradouro())){
//	                String enderecoTemp = endereco.getLogradouro();
//	                if(endereco.getNumero()!= null && !"".equalsIgnoreCase(endereco.getNumero())){
//	                    enderecoTemp += ", " + tratarValor(endereco.getNumero());
//	                }
//	                if(endereco.getComplemento() != null && !"".equalsIgnoreCase(endereco.getComplemento())){
//	                    enderecoTemp += " - " + tratarValor(endereco.getComplemento());
//	                }
	                canvas.showText("RUA BUENOS AIRES, 40, SL 908");
//	            }

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura-33);
	            canvas.showText("CENTRO"); 

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+240, altura-33);
	            canvas.showText("RIO DE JANEIRO"); 

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+414, altura-33);
	            canvas.showText("RJ"); 

	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+449, altura-33);
	            canvas.showText("20070-022");
	 //       }

	   //     i = 0;
	   //     for (Telefone telefoneTemp : telefone) {
	     //       if("COMERCIAL".equals(telefoneTemp.getTipoTelefone().getDescricao())){
	       //         if( i==0){
	                    canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura-58);
	         //       }
	           //     if( i==1){
	             //       canvas.setTextMatrix(ItextUtil.getDocumento().left()+183, altura-58);
	            //    }
	              //  if(telefoneTemp.getDdd() != null){
	                    canvas.showText("(21) 2179-2007");
	                //}
	   //             i++;
	    //        }
	    //    }

	    //    if(endereco.getTipoEndereco().getPessoaFisica() == true && endereco.isEnderecoValido() && endereco.getPostal() == true){
	            canvas.setTextMatrix(ItextUtil.getDocumento().left()+330, altura-58);
	            canvas.showText("x"); 
	      //  }

	//        if(endereco.getTipoEndereco().getPessoaJuridica() == true && endereco.isEnderecoValido() && endereco.getPostal() == true){
	  //          canvas.setTextMatrix(ItextUtil.getDocumento().left()+394, altura-58);
	    //        canvas.showText("x"); 
	      //  }

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura-80);
	        canvas.showText("bernard.hazlehurst@crea-rj.org.br");

	        canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura-110);
	        canvas.showText("Emitir 2a via da carteira de Identidade Profissional.");

	        //if(textoObservacao != null){
	            float alturaTemp = 175;
	           // for (String string : textoObservacao) {
	                canvas.setTextMatrix(ItextUtil.getDocumento().left()+58, altura-alturaTemp);
	                canvas.showText("Local de retirada da carteira: SEDE CREA-RJ");
	      //          alturaTemp += 12;
	         //   }
	       // }


	        canvas.endText();



	        
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
