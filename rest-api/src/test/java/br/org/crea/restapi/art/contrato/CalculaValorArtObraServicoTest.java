package br.org.crea.restapi.art.contrato;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.dao.art.ArtCUBDao;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ArtQuantificacaoDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.financeiro.FinMoedaDao;
import br.org.crea.commons.models.art.ArtCUB;
import br.org.crea.commons.models.art.ArtTipoTaxa;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.enuns.TipoTaxaArtEnum;
import br.org.crea.commons.models.financeiro.FinMoeda;
import br.org.crea.commons.util.DateUtils;

public class CalculaValorArtObraServicoTest {

	ContratoArtDao dao;
	ArtQuantificacaoDao artQuantificacaoDao;
	ArtCUBDao artCUBDao;
	FinMoedaDao finMoedaDao;
	ArtDao artDao;
	private static EntityManager em = null;
	
	ContratoArt contrato;
	
	@Before
	public void inicio() {
		dao = new ContratoArtDao();
		artQuantificacaoDao = new ArtQuantificacaoDao();
		artDao = new ArtDao();
		artCUBDao = new ArtCUBDao();
		finMoedaDao = new FinMoedaDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		dao.setEntityManager(em);
		artDao.setEntityManager(em);
		artCUBDao.setEntityManager(em);
		finMoedaDao.setEntityManager(em);
		artQuantificacaoDao.setEntityManager(em);
		contrato = dao.getContratoPor("2020180000508-1");
	}
	
	@After
	public void fim() {
		em.close();
	}
	
	@Test
	public void deveAtualizarValorDaArtEDoContrato() {
		dao.createTransaction();
		assertTrue(this.calcularObraServico());
		dao.commitTransaction();
	}
	
	private boolean calcularObraServico() {
		
		Long tipoTaxa = TipoTaxaArtEnum.METRAGEM_QUADRADA.getId();
		
		contrato.setQuantificacao(artQuantificacaoDao.getByIdContrato(contrato.getId()));
		contrato.setListCodigoAtividades(dao.getListaDeCodigosDasAtividadesDoContratoPor(contrato.getId()));
		contrato.setListCodigoEspecificacoes(dao.getListaDeCodigosDasEspecificacoesDoContratoPor(contrato.getId()));
		contrato.setListCodigoComplementos(dao.getListaDeCodigosDosComplementosDoContratoPor(contrato.getId()));
		contrato.setIdStringModalidade(getIdStringModalidadePeloRamoArt());
		
		
		BigDecimal valorRecolher 			= new BigDecimal(0);
		BigDecimal valorObraEServico	 	= defineValorDoContratoCorrigido(contrato);
		BigDecimal valorCalculo				= contrato.temQuantificacao() ? contrato.getQuantificacao().getValor() : new BigDecimal(0);
		BigDecimal valorEspecial 			= new BigDecimal(0);
		Date dataBase = new Date();
		
				
		List<Long> listTipoTaxa = artDao.getTaxaCobrancaEspecial(contrato, valorCalculo, dataBase);
		
		if(listTipoTaxa == null){
			listTipoTaxa = new ArrayList<Long>();
			tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();	
			valorCalculo = valorObraEServico;
		}else{
			tipoTaxa = listTipoTaxa.get(0);
		}
		
		if(tipoTaxa.longValue() != TipoTaxaArtEnum.METRAGEM_QUADRADA.getId().longValue() && 
			tipoTaxa.longValue() != TipoTaxaArtEnum.LAUDO_VISTORIA_PROPRIEDADE_RURAL.getId().longValue() &&
			tipoTaxa.longValue() != TipoTaxaArtEnum.BOLETIM_PRODUCAO_AGRICOLA.getId().longValue() ) {
			
			listTipoTaxa.clear();
			tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
			valorCalculo = valorObraEServico;
		}
		
		if(tipoTaxa.longValue() == TipoTaxaArtEnum.METRAGEM_QUADRADA.getId().longValue()) {
			ArtCUB artCUB = artCUBDao.buscarPorMesEAno(DateUtils.getMesCorrente(), DateUtils.getAnoCorrente());
			valorCalculo = contrato.temQuantificacao() ? contrato.getQuantificacao().getValor().multiply(artCUB.getValor()) : new BigDecimal(0);
			tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
		}
		
		if (listTipoTaxa.contains(TipoTaxaArtEnum.MULTIPLA.getId()) || listTipoTaxa.contains(TipoTaxaArtEnum.MULTIPLA_MENSAL_FAIXA_7.getId())) {
			valorEspecial = artDao.getTaxaPorValorCalculoDataBaseETipoTaxaSemFaixaInicial(valorCalculo, dataBase, tipoTaxa);
			if(valorEspecial == null) {
				valorEspecial = new BigDecimal(0);
				tipoTaxa = TipoTaxaArtEnum.VALOR_CONTRATO_SERVICO.getId();
			}
		}
		
		valorEspecial = artDao.getTaxaPorValorCalculoDataBaseETipoTaxaComFaixaInicial(valorCalculo, dataBase, tipoTaxa);
		if(valorEspecial == null) {
			valorEspecial = new BigDecimal(0);
		}
		
		valorRecolher = valorEspecial;
		
		atualizaValorETipoTaxaArt(valorRecolher, tipoTaxa);
		return true;
	}
	

	private String getIdStringModalidadePeloRamoArt() {
		return String.valueOf(contrato.getRamoArt().getId()).substring(0, 1);
	}

	private BigDecimal defineValorDoContratoCorrigido(ContratoArt contrato) {
		
		FinMoeda moeda = finMoedaDao.getMoedaBy(contrato.getDataInicio());
		
		if (moeda != null) {
			BigDecimal valorCorrigido = contrato.getValorContrato().divide(moeda.getFatorConversao(), MathContext.DECIMAL128);
			return valorCorrigido.intValue() == 0 ? new BigDecimal(0) : valorCorrigido;
		}
		
		return new BigDecimal(0);
	}
	
	private void atualizaValorETipoTaxaArt(BigDecimal valorArt, Long tipoTaxa) {
		contrato.setValorReceber(valorArt);
		contrato.setValorCalculado(valorArt);
		
		ArtTipoTaxa tipoTaxaContrato = new ArtTipoTaxa();
		tipoTaxaContrato.setId(tipoTaxa);
		contrato.setTipoTaxa(tipoTaxaContrato);
		
		contrato.getArt().setValorReceber(valorArt);
		contrato.getArt().setTipoTaxa(tipoTaxaContrato);
		dao.atualizaValorReceberCalculadoETipoTaxa(contrato);
		artDao.atualizaValorReceberETipoTaxa(contrato.getArt());
	}
}
