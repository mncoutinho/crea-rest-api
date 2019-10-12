package br.org.crea.restapi.commons;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.commons.ArquivoDao;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.util.PDFUtils;

public class ArquivoTest {
	
	static ArquivoDao dao;
	private static EntityManager em = null;
	
	PDFUtils pdfUtil;
	
	@Before
	public  void inicio() {
		
	    dao = new ArquivoDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		Assert.assertTrue("entity manager iniciado", em != null);
		pdfUtil = new PDFUtils();
	}
	
	@After
	public void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	@Test
	public void deveTrazerEnderecoResidencial() {
		
		List<Arquivo> arquivos = new ArrayList<Arquivo>();
		
		try {
			dao.setEntityManager(em);
			byte[] resultado = null;
			
			arquivos = dao.getAll();
//			File file = new File("/opt/arquivos/" + arquivos.get(0).getCaminhoStorage());
			
			for(Arquivo a : arquivos) {
				pdfUtil.adicionar(IOUtils.toByteArray(new FileInputStream("/opt/arquivos/" + a.getCaminhoStorage())));
			}
			
			System.out.println(arquivos.size());
//			PDFUtils.concatPDFs(streamOfPDFFiles, outputStream);
			resultado = pdfUtil.concatenar().buildFile();
			
			FileUtils.writeByteArrayToFile(new File("/var/www/html/arquivos/SIACOL/" + pathAnoMes() + "/" + "pauta01" + ".pdf"), resultado);
			
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
			
	}
	
	public String pathAnoMes() {
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return hoje.format(formatador);
	}

}
