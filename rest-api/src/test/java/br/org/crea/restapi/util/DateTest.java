package br.org.crea.restapi.util;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.CommonsGenericDao;
import br.org.crea.commons.util.DateUtils;

public class DateTest {
	
	
	private CommonsGenericDao dao;
	
	@Before
	public void inicio() {
		
		dao = new CommonsGenericDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		dao.em = factory.createEntityManager();

	}
	
	
	
//	@Test
	public void verficaPrazoSePassouDe10Dias (Calendar dataPgto, Calendar dataFinal) {
		Boolean ok = false;
//		
//		int dias = 1;
////		int vdias = TransformacoesData.diferencaEmDias(dataPgto, dataFinal);
//		do {
//			if (dataFinal.get(Calendar.DAY_OF_WEEK) == 1) {
//				dataFinal.add(Calendar.DAY_OF_MONTH, 1);
//			} else if (dataFinal.get(Calendar.DAY_OF_WEEK) == 7) {
//				dataFinal.add(Calendar.DAY_OF_MONTH, 2);
//			} else if (dao.verificaSeFeriado(DateUtils.convertCalendarToString(dataFinal))) {
//				dataFinal.add(Calendar.DAY_OF_MONTH, 1);
//			} else {
//				if (dias < 10) {
//					dataFinal.add(Calendar.DAY_OF_MONTH, 1);
//					dias = dias + 1;
//				} else {
//					ok = true;
//				}
//			}	
//			//System.out.println("OK: " + ok + "  dias: " + dias + " Data: " + TransformacoesData.dateToString(dataFinal));
//		} while (dias < 10 || ok.equals(false));
//		vdias = TransformacoesData.diferencaEmDias(dataPgto, dataFinal);
//		if (vdias >= 0) {
//			//System.out.println("Multipla antes dos 10 dias uteis: " + vdias + " dias " + TransformacoesData.dateToString(dataFinal));
//			return 0;
//		}
		
	}
	
	
	
//	@Test
	public void verificaSeheFeriado() {
		String data ="07/10/2017";
		assertTrue(dao.verificaSeFeriado(data));
	}

	@Test
	public void getTest() {
		System.out.println("Ano" + DateUtils.getAnoCorrente());
		System.out.println(DateUtils.getMesCorrente());
		System.out.println(DateUtils.format(DateUtils.getDataAtual(), DateUtils.DD));
	}
	


}
