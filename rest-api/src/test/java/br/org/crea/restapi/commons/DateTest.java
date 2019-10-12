package br.org.crea.restapi.commons;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.financeiro.FinFeriadoDao;
import br.org.crea.commons.util.DateUtils;

public class DateTest {

	FinFeriadoDao finFeriadoDao;
	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		finFeriadoDao = new FinFeriadoDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		finFeriadoDao.setEntityManager(em);
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2019() {
		assertEquals("15/01/2019", DateUtils.format(getEnesimoDiaUtilDoMesAno(10, "01", "2019"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2020() {
		assertEquals("14/02/2020", DateUtils.format(getEnesimoDiaUtilDoMesAno(10, "02", "2020"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2021() {
		assertEquals("12/03/2021", DateUtils.format(getEnesimoDiaUtilDoMesAno(10, "03", "2021"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2022() {
		assertEquals("14/04/2022", DateUtils.format(getEnesimoDiaUtilDoMesAno(10, "04", "2022"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2023() {
		assertEquals("08/05/2023", DateUtils.format(getEnesimoDiaUtilDoMesAno(5, "05", "2023"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2024() {
		assertEquals("14/06/2024", DateUtils.format(getEnesimoDiaUtilDoMesAno(10, "06", "2024"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2025() {
		assertEquals("11/07/2025", DateUtils.format(getEnesimoDiaUtilDoMesAno(9, "07", "2025"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2026() {
		assertEquals("11/08/2026", DateUtils.format(getEnesimoDiaUtilDoMesAno(7, "08", "2026"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2027() {
		assertEquals("15/09/2027", DateUtils.format(getEnesimoDiaUtilDoMesAno(10, "09", "2027"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2028() {
		assertEquals("02/10/2028", DateUtils.format(getEnesimoDiaUtilDoMesAno(1, "10", "2028"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2029() {
		assertEquals("26/11/2029", DateUtils.format(getEnesimoDiaUtilDoMesAno(15, "11", "2029"), DateUtils.DD_MM_YYYY));
	}
	
	@Test
	public void deveTrazerEnesimoDiaUtilDoMesAnoInformado2030() {
		assertEquals("04/12/2030", DateUtils.format(getEnesimoDiaUtilDoMesAno(3, "12", "2030"), DateUtils.DD_MM_YYYY));
	}
	
	
	public Date getEnesimoDiaUtilDoMesAno(int qtdDiaUtil, String mes, String ano) {
		Calendar data = new GregorianCalendar(Integer.parseInt(ano), Integer.parseInt(mes)-1, 1);
		
		int dias = 0;
		 while (dias < qtdDiaUtil) {
			if (data.get(Calendar.DAY_OF_WEEK) == 1) {
				data.add(Calendar.DAY_OF_MONTH, 1);
			} else if (data.get(Calendar.DAY_OF_WEEK) == 7) {
				data.add(Calendar.DAY_OF_MONTH, 2);
			} else if (finFeriadoDao.verificaSeEhFeriado(data.getTime())) {
				data.add(Calendar.DAY_OF_MONTH, 1);
			} else {
				data.add(Calendar.DAY_OF_MONTH, 1);
				dias = dias + 1;
			}
		}

		data.add(Calendar.DAY_OF_MONTH, -1);
		return data.getTime();
	}
}
