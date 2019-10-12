package br.org.crea.restapi.siacol.relatorio.rel06;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol06Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacolCommonsDao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol06Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol06Dto;

public class Relatorio06AnalistaTest {

	static RelatorioSiacol06Dao dao;
	static AssuntoSiacolDao assuntoDao;
	static AssuntoConverter assuntoConverter;
	static RelatorioSiacolCommonsDao commonsDao;
	private static EntityManager em = null;
	String[] mesesDoAno = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	List<RelDetalhadoSiacol06Dto> listaEntradaComPassivoMensalRel06 = new ArrayList<RelDetalhadoSiacol06Dto>();
	
	@Before
	public void inicio() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao = new RelatorioSiacol06Dao();
		assuntoDao = new AssuntoSiacolDao();
		assuntoConverter = new AssuntoConverter();
		commonsDao = new RelatorioSiacolCommonsDao();
		dao.setEntityManager(em);
		assuntoDao.setEntityManager(em);
		commonsDao.setEntityManager(em);
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
		responsavel.setPerfil("siacolanalista");
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
		
		pesquisa.setTodosAssuntos(true);
		
		return pesquisa;
	}
	
	@Test
	public void rel06AnalistaTesteIntegrado() {
		
		try {
			List<RelSiacol06Dto> listaRelatorio = quantidadeDeProtocolosPorAnalistaOuConselheiroPorMes(populaPesquisa());
			
			System.out.println("Mês | Entrada | Saída | Percentual | Pausado | Retorno | Total");
			listaRelatorio.forEach(relatorio -> {
				System.out.println(relatorio.getMes() + " | " + relatorio.getEntrada() + " | " + relatorio.getSaida() + " | " + relatorio.getPercentual() + " | " + relatorio.getPausado() + " | " + relatorio.getRetorno() + " | " + relatorio.getTotal());
			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public List<RelSiacol06Dto> quantidadeDeProtocolosPorAnalistaOuConselheiroPorMes(PesquisaRelatorioSiacolDto pesquisa) {
		
		if (pesquisa.isTodosAssuntos()) {
			pesquisa.setAssuntos(assuntoConverter.toListDtoSiacol(assuntoDao.getAll()));
		}
		List<Long> idsAssuntoSiacol = commonsDao.populaListaIdsAssuntos(pesquisa.getAssuntos());

		List<RelSiacol06Dto> listaRelatorio = new ArrayList<RelSiacol06Dto>();
		
		for (String mes : mesesDoAno) {
			RelSiacol06Dto linha = new RelSiacol06Dto();
			linha.setMes(mes);
			
			if (pesquisa.temPerfilAnalista()) {
		//		populaPassivoAnalista(linha, pesquisa, mes, idsAssuntoSiacol); //FIXME fazer passivo
		//		populaEntradaAnalistaRel06(linha, pesquisa, mes, idsAssuntoSiacol);
		//		populaSaidaAnalistaRel06(linha, pesquisa, mes, idsAssuntoSiacol);
		//		populaPausadoAnalistaRel06(linha, pesquisa, mes, idsAssuntoSiacol);
				populaRetornoAnalistaRel06(linha, pesquisa, mes, idsAssuntoSiacol);
			}
			
	//		if (pesquisa.temPerfilConselheiro()) {
	//			populaPassivoConselheiro(linha, pesquisa, mes, idsAssuntoSiacol);
	//			populaEntradaConselheiroRel06(linha, pesquisa, mes, idsAssuntoSiacol);
	//			populaSaidaConselheiroRel06(linha, pesquisa, mes, idsAssuntoSiacol);
	//		}
			
			populaPercentual(linha);
			
			listaRelatorio.add(linha);
		}

		return listaRelatorio;
	}

	/***** ANALISTA 
	 * @param idsAssuntoSiacol *****/
	private void populaPassivoAnalista(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		if (mes.equals("01")) {
//			List<RelDetalhadoSiacol06Dto> listaPassivoEntrada = dao.getPassivoEntradaAnalista(pesquisa, idsAssuntoSiacol);
			List<RelDetalhadoSiacol06Dto> listaPassivo = new ArrayList<RelDetalhadoSiacol06Dto>();
			
//			for (RelDetalhadoSiacol06Dto entrada : listaPassivoEntrada) {
//				RelDetalhadoSiacol06Dto saida = getPassivoSaidaAnalista(pesquisa, mes, entrada, idsAssuntoSiacol);
//				if (saida == null) listaPassivo.add(saida);
//			}
			linha.setProtocolosPassivo(listaPassivo);
			linha.setPassivo(linha.getProtocolosPassivo().size());
			listaEntradaComPassivoMensalRel06.addAll(listaPassivo);
		} else {
			linha.setProtocolosPassivo(listaEntradaComPassivoMensalRel06);
			linha.setPassivo(linha.getProtocolosPassivo().size());
		}
	}
	
//	private RelDetalhadoSiacol06Dto getPassivoSaidaAnalista(PesquisaRelatorioSiacolDto pesquisa, String mes, RelDetalhadoSiacol06Dto entrada, List<Long> idsAssuntoSiacol) {
//		boolean ehPassivo = true;
//		RelDetalhadoSiacol06Dto saida = dao.getSaidaAnalistaDistribuicaoTramitacaoOuAdReferendumOuCriacaoOficio(pesquisa, mes, entrada, ehPassivo, idsAssuntoSiacol);

//		return saida;
//	}

	private void populaEntradaAnalistaRel06(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		linha.setProtocolosEntrada(dao.getEntradaAnalistaDistribuicaoOuTramitacao(pesquisa, mes, idsAssuntoSiacol));
		linha.setEntrada(linha.getProtocolosEntrada().size());
		listaEntradaComPassivoMensalRel06.addAll(linha.getProtocolosEntrada());
	}
	
	private void populaSaidaAnalistaRel06(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		List<RelDetalhadoSiacol06Dto> listaSaida = new ArrayList<RelDetalhadoSiacol06Dto>();
		List<RelDetalhadoSiacol06Dto> listaPassivo = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		for (RelDetalhadoSiacol06Dto entrada : listaEntradaComPassivoMensalRel06) {
//			RelDetalhadoSiacol06Dto saida = getSaidaAnalista(pesquisa, mes, entrada, idsAssuntoSiacol);
//			if (saida != null) {
//				listaSaida.add(saida);
//			} else {
//				listaPassivo.add(entrada);
//			}
		}
		listaEntradaComPassivoMensalRel06 = listaPassivo;
		linha.setProtocolosSaida(listaSaida);
		linha.setSaida(listaSaida.size());
	}

//	private RelDetalhadoSiacol06Dto getSaidaAnalista(PesquisaRelatorioSiacolDto pesquisa, String mes, RelDetalhadoSiacol06Dto entrada, List<Long> idsAssuntoSiacol) {
		boolean ehPassivo = true;
//		RelDetalhadoSiacol06Dto saida = dao.getSaidaAnalistaDistribuicaoTramitacaoOuAdReferendumOuCriacaoOficio(pesquisa, mes, entrada, !ehPassivo, idsAssuntoSiacol);
//		return saida;
//	}

	private void populaPausadoAnalistaRel06(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		List<RelDetalhadoSiacol06Dto> listaPausado = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		for (RelDetalhadoSiacol06Dto entrada : listaEntradaComPassivoMensalRel06) {
			RelDetalhadoSiacol06Dto pausa = dao.getPausadoAnalista(pesquisa, mes, entrada, idsAssuntoSiacol);
			if (pausa != null) listaPausado.add(pausa);
		}
		linha.setProtocolosPausado(listaPausado);
		linha.setPausado(listaPausado.size());
	}
	
	private void populaRetornoAnalistaRel06(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		linha.setProtocolosRetorno(dao.getRetornoAnalista(pesquisa, mes, idsAssuntoSiacol));
		linha.setRetorno(linha.getProtocolosRetorno().size());
	}

	private void populaPercentual(RelSiacol06Dto linha) {
		float percentual;
		if (listaEntradaComPassivoMensalRel06.size() == 0) { // evitar divisão por zero
			percentual = 0;
		} else {
//			percentual = linha.getSaida() / listaEntradaComPassivoMensalRel06.size() * 100;
		}
//		linha.setPercentual(String.valueOf(percentual));
	}
	
}
