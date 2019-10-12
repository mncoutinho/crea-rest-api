package br.org.crea.restapi.art.finaliza;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.art.ExigenciaArtDao;
import br.org.crea.commons.models.art.ArtExigencia;
import br.org.crea.commons.models.art.ArtSituacaoLiberacao;
import br.org.crea.commons.models.art.ArtTipoAcao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.ExigenciaArt;
import br.org.crea.commons.models.art.enuns.TipoExigenciaArtEnum;

public class GerarExigenciaArtTest {

	ContratoArtDao dao;
	ArtDao artDao;
	private static EntityManager em = null;
	
	ContratoArt contrato;
	
	private ArtTipoAcao tipoAcaoArt;
	
	private ArtSituacaoLiberacao situacaoLiberacao;
	
	ExigenciaArtDao exigenciaArtDao;
	
	@Before
	public void inicio() {
		exigenciaArtDao = new ExigenciaArtDao();
		dao = new ContratoArtDao();
		artDao = new ArtDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao.setEntityManager(em);
		artDao.setEntityManager(em);
		exigenciaArtDao.setEntityManager(em);
		contrato = new ContratoArt();
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void deveGerarDividaArt() {
		contrato = dao.getContratoPor("2020180000525-1");
		exigenciaArtDao.createTransaction();
		assertTrue(this.gerarExigenciasDeUmaNovaArt());
		exigenciaArtDao.commitTransaction();
	}
	
	private boolean gerarExigenciasDeUmaNovaArt() {
		
		tipoAcaoArt = new ArtTipoAcao();
		tipoAcaoArt.setId(0L);
		situacaoLiberacao = new ArtSituacaoLiberacao();
		situacaoLiberacao.setId(0L);
		
		incluirExigencia(TipoExigenciaArtEnum.COPIA_ART_COM_ASSINATURA_CONTRATADO);
				
		
//		if (!contrato.getArt().getIsAcaoOrdinaria() && !contrato.getArt().getIsTermoAditivo() && !pagaEmJuizo) 
		{
			incluirExigencia(TipoExigenciaArtEnum.ART_SEM_PAGAMENTO);
		}
		
		// se nao eh receituario agronomico \/
			// se tem divida taxa de incorporacao, incluirExigencia(47)
		
		
		// Verificacoes fos Contratos - FOR
		{	
		// complemento igual 184, criticaHonorario = false ??
		// Exigencia 29 - tipoTaxa = 9, acredito que não tenha caso deste tipo?? valorContrato virá preenchido
			
		if (contrato.artEhObraServico() || contrato.artEhDesempenhoDeCargoEFuncao()) {
			incluirExigencia(TipoExigenciaArtEnum.COPIA_ART_COM_ASSINATURA_CONTRATANTE);
		}
			
		if (contrato.temDescricaoComplementares()) {
			incluirExigencia(TipoExigenciaArtEnum.CAMPO_27_SUJEITO_NOVA_ANALISE);
		}
					
		// Exigencia 43 - Nao foi cadastrado nenhum contratante, if se refere a outra coisa, nao relacionada ao codigo
		}
		
		return artDao.atualizaExigencia(contrato.getArt().getNumero(), true);
	}

	private void incluirExigencia(TipoExigenciaArtEnum tipoExigencia) {

		ExigenciaArt exigencia = new ExigenciaArt();
		
		exigencia.setData(new Date());
		exigencia.setArt(contrato.getArt());
		exigencia.setContrato(contrato);
		exigencia.setExigencia(populaExigencia(tipoExigencia));
		exigencia.setTipoAcaoArt(tipoAcaoArt);
		exigencia.setMotivo(tipoExigencia.getDescricao());
		exigencia.setSituacaoLiberacao(situacaoLiberacao);
		
		exigenciaArtDao.create(exigencia);
	}

	private ArtExigencia populaExigencia(TipoExigenciaArtEnum tipoExigencia) {
		ArtExigencia artExigencia = new ArtExigencia();
		
		artExigencia.setId(tipoExigencia.getId());
		return artExigencia;
	}

}
