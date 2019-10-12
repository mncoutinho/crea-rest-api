package br.org.crea.restapi.art.finaliza;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.util.DateUtils;

public class VerificaTaxaDeIncorporacaoDeAcervoTecnicoTest {

	private ContratoArtDao contratoDao;

	private ArtDao artDao;

	private Art art;

	private ContratoArt contrato;
	
	private ContratoArt contratoArtPrincipal;
	
	private String numeroArt;

	private static EntityManager em = null;
	
	@Before
	public void inicio() {
		artDao = new ArtDao();
		contratoDao = new ContratoArtDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		contratoDao.setEntityManager(em);
		artDao.setEntityManager(em);
		Assert.assertTrue("entity manager iniciado", em != null);
	}

	@After
	public void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen());
	}
	
	@Test
	public void deveRetornarFalsoCasoSejaReceituarioAgronomico() {
		numeroArt = "2020170000118";
		Assert.assertFalse(this.getTaxa());
	}
	
	@Test
	public void deveRetornarFalsoCasoSejaTaxaMinima() {
		numeroArt = "2020180000526";
		Assert.assertFalse(this.getTaxa());
	}
	
	@Test
	public void deveRetornarFalsoCasoTenhaArtPrincipalENaoSejaTaxaMinimaVinculadaCargoFuncao() {
		numeroArt = "2020180000583";
		Assert.assertFalse(this.getTaxa());
	}
	
	@Test
	public void deveRetornarFalsoCasoTenhaArtPrincipalENaoSejaTaxaMinimaESejaMultiplaVinculadaAObraServicoComMesmoNumeroDeContrato() {
		numeroArt = "2020180000615";
		Assert.assertFalse(this.getTaxa());
	}
	
	@Test
	public void deveRetornarFalsoCasoSejaCargoFuncaoEComPrazoIndeterminado() {
		numeroArt = "2020180000616";
		Assert.assertFalse(this.getTaxa());
	}
	
	@Test
	public void deveRetornarFalsoCasoSejaMultiplaComDataFinalMaiorIgualAHoje() {
		numeroArt = "2020180000617";
		Assert.assertFalse(this.getTaxa());
	}
	
	@Test
	public void deveRetornarFalsoCasoSejaMultiplaMensalComDecimoDiaUtilMesSeguinteMaiorQueHoje() {
		numeroArt = "2020180000618";
		Assert.assertFalse(this.getTaxa());
	}
	
	@Test
	public void deveRetornarTrueCasoSejaMultiplaMensalComDecimoDiaUtilMesSeguinteAnteriorAHoje() {
		numeroArt = "2020180000619";
		Assert.assertTrue(this.getTaxa());
	}
	
	@Test
	public void deveRetornarFalsoCasoNaoSejaMultiplaMensalComDataFinalMaiorQueHoje() {
		numeroArt = "2020180000620";
		Assert.assertFalse(this.getTaxa());
	}
	
	public boolean getTaxa() { // linha 1903 legado
		Date hoje = new Date();
		Date dataFinal = hoje;
		
		art = artDao.getByIdString(numeroArt);
		if (art.ehReceituarioAgronomico()) {
			return false;
		}
		
		
		contrato = contratoDao.getPrimeiroContratoPor(numeroArt);
		
		if (contrato.getArt().heTaxaMinima()) {
			return false;
		}		
		
		if (temArtPrincipalENaoEhTaxaMinima()) {
			contratoArtPrincipal = contratoDao.getPrimeiroContratoPor(contrato.getArt().getNumeroARTPrincipal());

			if (verificaSeARTevinculadaCargoFuncao()) {
				return false;
			}

			if (contrato.artEhMultipla() && contratoArtPrincipal.artEhObraServico()) {
				if (temMesmoNumeroDeContrato()) {
					return false;
				}
			}
		}
		
		// simplificação da regra de prazo determinado, como só as antigas terão falha no preenchimento dos prazos, o único 
		// caso atual que é indeterminado é a de cargo e função
		if (contrato.artEhDesempenhoDeCargoEFuncao() && contrato.temPrazoIndeterminado()) {
			return false;
		}
		
		if (contrato.artEhMultipla()) {
			dataFinal = contratoDao.pegaMaiorDataFinaldeContrato(contrato.getArt().getNumero());
		}
				
//		if (art.getArtNaoCobrarIncorporacao() != null && art.getArtNaoCobrarIncorporacao().getNumeroArt() != null ){
//				return false;
//			}

		if (DateUtils.primeiraDataeMaiorOuIgualQueSegunda(dataFinal, hoje)) {
			return false;
		}
		
				
		if (contrato.getArt().heMultiplaMensal()) {
			Date decimoDiaUtilDoMesSeguinte = DateUtils.getEnesimoDiaUtilDoMesAno(10, DateUtils.format(dataFinal, DateUtils.MM), DateUtils.format(dataFinal, DateUtils.YYYY));
			if (DateUtils.primeiraDataeMaiorOuIgualQueSegunda(decimoDiaUtilDoMesSeguinte, hoje)) {
				return false;
			}
		}			
		
		return true;
	}

	private boolean temArtPrincipalENaoEhTaxaMinima() {
		return contrato.getArt().temArtPrincipal() && contrato.getArt().naoEhTaxaMinima();
	}

	private boolean temMesmoNumeroDeContrato() {
		if (contrato.temNumeroContrato() && contratoArtPrincipal.temNumeroContrato()) {
			return contrato.getNumeroContrato().equals(contratoArtPrincipal.getNumeroContrato());
		}
		return false;
	}

	private boolean verificaSeARTevinculadaCargoFuncao() {

		if (contratoArtPrincipal.artEhDesempenhoDeCargoEFuncao()) {
			if (ehOMesmoProfissional() && ehAMesmaEmpresa() && ehOMesmoContratante()) {
				return true;
			}
		}

		return false;
	}

	private boolean ehOMesmoContratante() {
		if (contrato.temPessoa() && contratoArtPrincipal.temPessoa()) {
			return contrato.getPessoa().getId().equals(contratoArtPrincipal.getPessoa().getId());
		}
		return false;
	}

	private boolean ehOMesmoProfissional() {
		return contrato.getArt().getProfissional().getPessoaFisica().getId().equals(contratoArtPrincipal.getArt().getProfissional().getPessoaFisica().getId());
	}

	private boolean ehAMesmaEmpresa() {
		if (contrato.getArt().temEmpresa() && contratoArtPrincipal.getArt().temEmpresa()) {
			if (contrato.getArt().getEmpresa().getPessoaJuridica().getId().equals(contratoArtPrincipal.getArt().getEmpresa().getPessoaJuridica().getId())) {
				return true;
			}
		}
		return false;
	}

}
