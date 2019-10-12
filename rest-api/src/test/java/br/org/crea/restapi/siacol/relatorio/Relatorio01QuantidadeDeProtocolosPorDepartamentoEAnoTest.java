package br.org.crea.restapi.siacol.relatorio;

public class Relatorio01QuantidadeDeProtocolosPorDepartamentoEAnoTest {

//	static RelatorioSiacolDao dao;
//	private static EntityManager em = null;
//	
//	@Before
//	public void inicio() {
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
//		em = factory.createEntityManager();
//		dao = new RelatorioSiacolDao();
//		dao.setEntityManager(em);
//	}
//	
//	@After
//	public void fim() {
//		em.close();
//	}
//	
//	private PesquisaRelatorioSiacolDto populaPesquisa() {
//
//		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
//		pesquisa.setAno("2020");
//		pesquisa.setTodosDepartamentos(false);
//		
//		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
//		DepartamentoDto dept = new DepartamentoDto();
//		dept.setId(1203L);
//		departamentos.add(dept);
//		pesquisa.setDepartamentos(departamentos);
//		
//		return pesquisa;
//	}
//	
////	@Test 
//	public void entradaTest() {
//		List<Long> idsDepartamentos = new ArrayList<Long>();
//		idsDepartamentos.add(1203L);
//		
//		List<RelDetalhadoSiacol01Dto> lista = dao.rel01Entrada(populaPesquisa(), idsDepartamentos, "2019", "01");
//		lista.forEach(relatorio -> {
//			System.out.println("Entrada: " + relatorio.getQtd() + " Protocolo: " + relatorio.getProtocolo());
//		});
//
////		Double soma = EstatisticaUtil.soma(lista.stream().map(RelDetalhadoSiacol01Dto::getQtd));
////		System.out.println("Entrada Total: " + soma.intValue());
//
//	}
//	
////	@Test
//	public void saidaTest() {
//		List<Long> idsDepartamentos = new ArrayList<Long>();
//		idsDepartamentos.add(1203L);
//		
//		List<RelDetalhadoSiacol01Dto> lista = dao.rel01Saida(populaPesquisa(), idsDepartamentos, "2019", "01");
//		lista.forEach(relatorio -> {
//			System.out.println("Saída: " + relatorio.getQtd() + " Protocolo: " + relatorio.getProtocolo());
//		});
//
////		Double soma = EstatisticaUtil.soma(lista.stream().map(RelDetalhadoSiacol01Dto::getQtd));
////		System.out.println("Saída Total: " + soma.intValue());
//
//	}
//	
////	@Test
//	public void passivoTest() {
//		List<Long> idsDepartamentos = new ArrayList<Long>();
//		idsDepartamentos.add(1203L);
//		List<RelDetalhadoSiacol01Dto> lista = dao.obtemPassivoPorAno(populaPesquisa(), idsDepartamentos, "2020");
//		lista.forEach(relatorio -> {
//			System.out.println("Passivo: " + relatorio.getQtd() + " Protocolo: " + relatorio.getProtocolo());
//		});
//
////		Double soma = EstatisticaUtil.soma(lista.stream().map(RelDetalhadoSiacol01Dto::getQtd));
////		System.out.println("Passivo Total: " + soma.intValue());
//
//	}
//	
//	@Test
//	public void relatorioRefatoradoTest() {
//		
//		try {
//			RelSiacol01Dto relatorio = dao.quantidadeDeProtocolosPorDepartamentosEPorAno(populaPesquisa());
//			
//			System.out.println(relatorio.getLabels().get(0).getLabel() + " " + relatorio.getDataset().get(0).getSeriesname() + ": " + relatorio.getDataset().get(0).getData().get(0).getValue());
//			relatorio.getLabels().forEach(item -> {
//				System.out.print(item.getLabel() + " | ");
//			});
//			System.out.println("");
//			
//			System.out.print("ENTRADA | ");
//			relatorio.getDataset().get(1).getData().forEach(item -> {
//				System.out.print(item.getValue() + " | ");
//			});
//			System.out.println("");
//			
//			System.out.print("SAIDA | ");
//			relatorio.getDataset().get(2).getData().forEach(item -> {
//				System.out.print(item.getValue() + " | ");
//			});
//			System.out.println("");
//			
//			System.out.print("SALDO | ");
//			relatorio.getDataset().get(3).getData().forEach(item -> {
//				System.out.print(item.getValue() + " | ");
//			});
//
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//	}

}
