package br.org.crea.restapi.siacol.relatorio.rel06;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol06Dao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol06Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol06Dto;

public class Relatorio06ConselheiroTest {

	static RelatorioSiacol06Dao dao;
	private static EntityManager em = null;
	String[] mesesDoAno = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	List<RelDetalhadoSiacol06Dto> listaEntradaRel06 = null;
	
	@Before
	public void inicio() {
	    dao = new RelatorioSiacol06Dao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	private PesquisaRelatorioSiacolDto populaPesquisa() {
		PesquisaRelatorioSiacolDto pesquisa = new PesquisaRelatorioSiacolDto();
		
		List<PessoaDto> responsaveis = new ArrayList<PessoaDto>();
		PessoaDto responsavel = new PessoaDto();
		responsavel.setId(1983103508L);
		responsavel.setPerfil("siacolconselheiro");
		responsaveis.add(responsavel);
		pesquisa.setResponsaveis(responsaveis);

		List<DepartamentoDto> departamentos = new ArrayList<DepartamentoDto>();
		DepartamentoDto departamento = new DepartamentoDto();
		departamento.setId(1205L);
		departamento.setNome("CAMARA CIVIL");
		departamentos.add(departamento);
		pesquisa.setDepartamentos(departamentos);
		
		pesquisa.setAno("2018");
		List<String> meses = new ArrayList<String>();
		meses.add("11");
		pesquisa.setMeses(meses);
		
		return pesquisa;
	}
	
	@Test
	public void rel06ConselheiroTesteIntegrado() {
		
		try {
			dao.setEntityManager(em);
			
			List<RelSiacol06Dto> listaRelatorio = quantidadeDeProtocolosPorAnalistaOuConselheiroPorMes(populaPesquisa());
			
			System.out.println("Mês | Entrada | Saída | Percentual | Total");
			listaRelatorio.forEach(relatorio -> {
				System.out.println(relatorio.getMes() + " | " + relatorio.getEntrada() + " | " + relatorio.getSaida() + " | " + relatorio.getPercentual() + " | " + relatorio.getTotal());
			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	// RELATÓRIO #06
	public List<RelSiacol06Dto> quantidadeDeProtocolosPorAnalistaOuConselheiroPorMes(PesquisaRelatorioSiacolDto pesquisa) {

		String perfil = pesquisa.getResponsaveis().get(0).getPerfil();
		List<RelSiacol06Dto> listaRelatorio = new ArrayList<RelSiacol06Dto>();
		
		for (String mes : mesesDoAno) {
			RelSiacol06Dto linha = new RelSiacol06Dto();
			
			linha.setMes(mes);
			
			if (ehPerfilConselheiro(perfil)) {
//				linha.setEntrada(populaEntradaConselheiroRel06(pesquisa, mes));
//				linha.setSaida(populaSaidaConselheiroRel06(pesquisa, mes));
//
//				linha.setPassivo(populaPassivoConselheiro(pesquisa, mes));
//				linha.setPercentual(populaPercentual(linha));
			}
			
			listaRelatorio.add(linha);
		}

		return listaRelatorio;
	}
	
//	private int populaEntradaConselheiroRel06(PesquisaRelatorioSiacolDto pesquisa, String mes) {
//		return dao.getEntradaConselheiroDistribuicao(pesquisa, mes);
//	}
//
//	private int populaSaidaConselheiroRel06(PesquisaRelatorioSiacolDto pesquisa, String mes) {
//		int qtd = dao.getSaidaConselheiroImpedimento(pesquisa, mes);
//		
//		int qtd2 = dao.getSaidaConselheiroAssinaturaRelatorioVoto(pesquisa, mes);
//		
//		return qtd + qtd2;
//	}
	
//	private int populaPassivoConselheiro(PesquisaRelatorioSiacolDto pesquisa, String mes) {
//		return dao.getPassivoConselheiro(pesquisa, mes);
//	}

//	private String populaPercentual(RelSiacol06Dto linha) {
//		float percentual;
//		if (linha.getEntrada() == 0) { // evitar divisão por zero
//			percentual = 0;
//		} else {
//			percentual = linha.getSaida() / linha.getEntrada() * 100; //FIXME considerar saldo acumulado
//		}
//		return String.valueOf(percentual);
//	}

	private boolean ehPerfilConselheiro(String perfil) {
		return perfil.equals("siacolconselheiro") || perfil.equals("siacolcoordenadorcamara");
	}
}
