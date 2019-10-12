package br.org.crea.restapi.art.finaliza;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ArtLogDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.art.ExigenciaArtDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.models.art.ArtLog;
import br.org.crea.commons.models.art.ArtTipoAcao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.enuns.TipoBaixaArtEnum;

public class TratarVinculacoesArtTest {

	ContratoArtDao dao;
	ArtDao artDao;
	private static EntityManager em = null;
	
	ContratoArt contrato;
	ContratoArt contratoArtPrincipal;
	
	ArtTipoAcao tipoAcaoArt;
	
	ExigenciaArtDao exigenciaArtDao;
	FinDividaDao finDividaDao;
	ArtLogDao artLogDao;
	
	@Before
	public void inicio() {
		exigenciaArtDao = new ExigenciaArtDao();
		finDividaDao = new FinDividaDao();
		artLogDao = new ArtLogDao();
		dao = new ContratoArtDao();
		artDao = new ArtDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao.setEntityManager(em);
		artDao.setEntityManager(em);
		exigenciaArtDao.setEntityManager(em);
		finDividaDao.setEntityManager(em);
		artLogDao.setEntityManager(em);
		contrato = new ContratoArt();
	}
	
	@After
	public void fim() {
		em.close();
	}

//	@Test
	public void deveBaixarArtPrincipalPorSubstituicao() {
		contrato = dao.getContratoPor("2020180000517-1");
		artDao.createTransaction();
//		artDao.baixarArtPrincipalPorSubstituicao(contrato.getArt());
		assertTrue(true);
		artDao.commitTransaction();
	}
	
//	@Test
	public void deveBaixarContratosArtPrincipalMultiplaPorSubstituicao() {
		contrato = dao.getContratoPor("2020180000517-1");
		contratoArtPrincipal = dao.getContratoPor(contrato.getArt().getNumeroARTPrincipal()+"-1");
		dao.createTransaction();
		dao.darBaixaNosContratos(contratoArtPrincipal.getArt().getNumero(), TipoBaixaArtEnum.CANCELAMENTO_POR_SUBSTITUICAO_DE_ART);
		assertTrue(true);
		dao.commitTransaction();
	}
	
//	@Test
	public void deveCancelarDividasDaArtPrincipal() {
		contrato = dao.getContratoPor("2020180000517-1");
		contratoArtPrincipal = dao.getContratoPor(contrato.getArt().getNumeroARTPrincipal()+"-1");
		finDividaDao.createTransaction();
		cancelarDividasDaArtPrincipal();
		assertTrue(true);
		finDividaDao.commitTransaction();
	}
	
//	@Test
	public void deveCriarLogDaSubstituicao() {
		contrato = dao.getContratoPor("2020180000517-1");
		artLogDao.createTransaction();
		tipoAcaoArt = new ArtTipoAcao();
		criarLogDaSubstituicao();
		assertTrue(true);
		artLogDao.commitTransaction();
	}
	
//	@Test
	public void deveCriarLogDaSubstituicaoDasVinculadas() {
		contrato = dao.getContratoPor("2020180000517-1");
		artLogDao.createTransaction();
		tipoAcaoArt = new ArtTipoAcao();
		List<String> listaVinculadas = artDao.getListNumeroArtsVinculadas(contrato.getArt().getNumeroARTPrincipal());
		
		criarLogDaSubstituicaoDasVinculadas(listaVinculadas);
		assertTrue(true);
		artLogDao.commitTransaction();
	}
	
	@Test
	public void deveSubstituirArtPrincipalDasVinculadasAArtSubstituida() {
		contrato = dao.getContratoPor("2020180000517-1");
		artDao.createTransaction();
		artDao.substituirNumeroArtPrincipalDasVinculadasAArtSubstituida(contrato.getArt());
		assertTrue(true);
		artDao.commitTransaction();
	}
	
	private boolean trataVinculacoes() {

		// Ação 2 - contrato nao calculado
		// Ação 3 - art numero principal invalido ? não ocorrerá
		if (contrato.artPossuiArtPrincipal() && contrato.getArt().ehSubstituta())
		{
			contratoArtPrincipal = dao.getPrimeiroContratoPor(contrato.getArt().getNumeroARTPrincipal());

//			artDao.baixarArtPrincipalPorSubstituicao(contrato.getArt());
			
			if (contratoArtPrincipal.artEhMultipla()) {
				dao.darBaixaNosContratos(contratoArtPrincipal.getArt().getNumero(), TipoBaixaArtEnum.CANCELAMENTO_POR_SUBSTITUICAO_DE_ART);
			}

			cancelarDividasDaArtPrincipal();
			
			criarLogDaSubstituicao();
			
			List<String> listaVinculadas = artDao.getListNumeroArtsVinculadas(contrato.getArt().getNumeroARTPrincipal());
			
			artDao.substituirNumeroArtPrincipalDasVinculadasAArtSubstituida(contrato.getArt());
			
			criarLogDaSubstituicaoDasVinculadas(listaVinculadas);
			
		}
	
		return true;
	}

	private void criarLogDaSubstituicaoDasVinculadas(List<String> listaVinculadas) {
		tipoAcaoArt.setId(14L);
		listaVinculadas.remove(contrato.getArt().getNumero());
		
		for (String numeroArtVinculada : listaVinculadas) {
			ArtLog log = new ArtLog();
			
			log.setNumeroArt(numeroArtVinculada);
			log.setDataHora(new Date());
			log.setTipoAcaoArt(14L);
			log.setFuncionario(contrato.getArt().getFuncionarioCadastro());
			log.setDescricao("ART Principal nº " + contrato.getArt().getNumeroARTPrincipal() + " substituída por " + contrato.getArt().getNumero()); 
			
			artLogDao.create(log);
		}
		
	}

	private void criarLogDaSubstituicao() {
		ArtLog log = new ArtLog();
		
		log.setNumeroArt(contrato.getArt().getNumeroARTPrincipal());
		log.setDataHora(new Date());
		log.setTipoAcaoArt(14L);
		log.setFuncionario(contrato.getArt().getFuncionarioCadastro());
		log.setDescricao("ART Principal nº " + contrato.getArt().getNumeroARTPrincipal() + " substituída por " + contrato.getArt().getNumero()); 
		
		artLogDao.create(log);
	}

	private void cancelarDividasDaArtPrincipal() {
		finDividaDao.cancelarDividaDeArtETaxaDeIncorporacao(contratoArtPrincipal.getArt().getNumero());
	}

}
