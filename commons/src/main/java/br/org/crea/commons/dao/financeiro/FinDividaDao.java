package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class FinDividaDao extends GenericDao<FinDivida, Serializable> {

	@Inject
	private HttpClientGoApi httpGoApi;

	@Inject
	private Properties properties;

	private String ativaAnuidade;

	private String dataFinalParcelamentoAnuidadeCorrente;

	@PostConstruct
	public void before() {
		this.ativaAnuidade = properties.getProperty("financeiro.AtivaAnuidadeAnoSeguinte");
		this.dataFinalParcelamentoAnuidadeCorrente = properties.getProperty("financeiro.dataFinalParcelamentoAnuidadeCorrente");
	}

	public FinDividaDao() {
		super(FinDivida.class);
	}

	public List<FinDivida> getAnuidadeDivida(Long idPessoa) {

		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE F.pessoa.id = :idPessoa  ");
		sql.append("	    AND ( F.parcela = 0 OR ");
		sql.append("	            ( F.parcela > 0  AND NOT EXISTS ");
		sql.append("	               ( FROM FinParcelamentoDivida PD WHERE PD.finDivida.id = F.id ) ");
		sql.append("	            ) ");
		sql.append("	         ) ");
		sql.append("	    AND ( ");
		sql.append("	               ( F.status.quitado = 0 AND F.natureza.tipoRecebivel <> 4 AND F.natureza.id <> 1 ) ");
		sql.append("	               OR ( F.status.id IN (3,23) ) ");
		sql.append("	               OR ( F.natureza.id = 1 AND F.status.id IN (1, 2, 3) ) ");
		sql.append("	               OR ( F.natureza.id = 800 AND F.status.id IN (1, 2) ) ");
		sql.append("	         ) ");
		sql.append("	    AND (NOT EXISTS ( FROM FinDivida X WHERE X.id = F.id  ");
		sql.append("	                        AND  X.natureza.id = 6 ");
		sql.append("	                        AND  X.identificadorDivida > TO_CHAR(SYSDATE, 'YYYY') ");
		sql.append("	                        AND  X.status.id <> 8 ");
		sql.append("	                        AND  X.status.quitado = 0 ");
		sql.append("	                   ) or '0' = :ativaAnuidade ) ");

		sql.append("	    ORDER BY F.natureza.id, F.identificadorDivida, F.parcela  ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("ativaAnuidade", ativaAnuidade);
			listFinDivida = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || Get Anuidade Divida", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return listFinDivida;

	}

	public List<FinDivida> getAnuidadesDividas(Long idPessoa) {

		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("   SELECT fd FROM FinDivida fd ");
		sql.append("    WHERE fd.pessoa.id = :idPessoa ");
		sql.append("      AND fd.natureza.id = 6 ");
		sql.append(" ORDER BY fd.identificadorDivida, fd.parcela ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			listFinDivida = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || Get Anuidades Divida", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return listFinDivida;

	}
	
	/**
	 * Libera botão para emissão da primeira guia para parcelamento da anuidade
	 * do ano corrente até o 31/01
	 * 
	 * @param idPessoa
	 * @return
	 */
	public boolean liberaParcelamentoAnuidadeCorrente(Long idPessoa) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(*) ");
		sql.append("	From Fin_Divida D ");
		sql.append("	Where D.Fk_Codigo_Pessoa = :idPessoa ");
		sql.append("	  And D.Fk_Codigo_Natureza = 6 ");
		sql.append("	  And D.Parcela = 0 ");
		sql.append("	  And D.Fk_Codigo_Status_Divida < 3 ");
		sql.append("	  And D.Identificador_Divida = :ano ");
		sql.append("	  And Sysdate < To_Date(:data, 'DD/MM/YYYY') ");
		sql.append("	  And Not Exists (Select F.Codigo From Fin_Divida F Where F.Fk_Codigo_Pessoa = D.Fk_Codigo_Pessoa ");
		sql.append("	                     And F.Fk_Codigo_Natureza = 6 ");
		sql.append("	                     And F.Identificador_Divida < D.Identificador_Divida ");
		sql.append("	                     And F.Fk_Codigo_Status_Divida < 4) ");
		sql.append("	  And Not Exists (Select H.Codigo From Cad_Historicos H ");
		sql.append("	                   Where H.Fk_Codigo_Pessoas = D.Fk_Codigo_Pessoa ");
		sql.append("	                     And H.Fk_Codigo_Eventos In (127, 128, 129, 130, 131, 132, 133, 134, 135) ");
		sql.append("	                     And (H.Datafinal Is Null Or To_Char(H.Datafinal, 'YYYY') = D.Identificador_Divida)) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("ano", dataFinalParcelamentoAnuidadeCorrente.substring(6, 10));
			query.setParameter("data", dataFinalParcelamentoAnuidadeCorrente);

			BigDecimal quantidadeParcelamentos = (BigDecimal) query.getSingleResult();
			return quantidadeParcelamentos.setScale(0, BigDecimal.ROUND_UP).longValueExact() > new Long(0) ? true : false;

		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || liberaParcelamentoAnuidadeCorrente", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return false;
	}

	public List<FinDivida> getParcelasAvencer(Long idPessoa, String anuidade) {

		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE F.pessoa.id = :idPessoa  AND F.identificadorDivida = :anuidade ");
		sql.append("	    AND F.status.quitado = 0 ");
		sql.append("	    AND F.natureza.id = 6 ");
		sql.append("	    AND F.status.id IN (1, 2, 3) ");
		sql.append("	    AND ( F.parcela > 0 ");
		sql.append("	              AND EXISTS ( FROM FinParcelamentoDivida FP  WHERE FP.finDivida.id = F.id) ) ");
		sql.append("	    ORDER BY F.dataVencimento  ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("anuidade", anuidade);
			listFinDivida = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || Get Parcelas a Vencer", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return listFinDivida;

	}

	public List<FinDivida> getMultasAvencer(Long idPessoa, String anuidade) {

		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE F.pessoa.id = :idPessoa  AND F.identificadorDivida = :anuidade ");
		sql.append("	    AND F.status.quitado = 0 ");
		sql.append("	    AND F.natureza.id = 2 ");
		sql.append("	    AND F.status.id IN (1, 2, 3) ");
		sql.append("	    AND ( F.parcela > 0 ");
		sql.append("	              AND EXISTS ( FROM  FinParcelamentoDivida FP  WHERE FP.finDivida.id = F.id) ) ");
		sql.append("	    ORDER BY F.dataVencimento  ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("anuidade", anuidade);
			listFinDivida = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || Get Multas a Vencer", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return listFinDivida;
	}

	public FinDivida getUltimaAnuidadePagaPor(Long idPessoa) {

		FinDivida finDivida = new FinDivida();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE  F.pessoa.id = :idPessoa ");
		sql.append("	AND    F.natureza.id = 6 ");
		sql.append("	AND    F.parcela = 0 ");
		sql.append("	AND    F.status.id IN (4, 10, 22) ");
		sql.append("	AND    F.dataQuitacao IS NOT NULL ");
		sql.append("	AND    F.id = (SELECT MAX(X.id) FROM FinDivida X ");
		sql.append("	                    WHERE X.pessoa.id = :idPessoa ");
		sql.append("	                    AND   X.natureza.id = 6 ");
		sql.append("	                    AND   X.status.id IN (4, 10, 22) ");
		sql.append("	                    AND   X.parcela = 0 ");
		sql.append("						AND   X.dataQuitacao IS NOT NULL ");
		sql.append("						AND   X.identificadorDivida = (SELECT MAX(FD.identificadorDivida) FROM FinDivida FD ");
		sql.append("	                        								WHERE  FD.pessoa.id = :idPessoa ");
		sql.append("	                        								AND    FD.natureza.id = 6 ");
		sql.append("	                        								AND    FD.parcela = 0 ");
		sql.append("	                        								AND    FD.status.id IN (4, 10, 22) ");
		sql.append("	                        								AND    FD.dataQuitacao IS NOT NULL ");
		sql.append("	                )) ");

		try {

			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);

			finDivida = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getUltimaAnuidadePagaPor", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return finDivida;

	}

	public FinDivida getUltimaParcelaAnuidadeVencidaPagaPor(Long idPessoa) {

		FinDivida finDivida = new FinDivida();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE  F.pessoa.id = :idPessoa ");
		sql.append("		AND    F.natureza.id = 6 ");
		sql.append("		AND    F.status.id <> 8 ");
		sql.append("		AND    F.status.quitado = 1 ");
		sql.append("	    AND    F.parcela > 0 ");
		sql.append("	    AND    EXISTS ( FROM  FinParcelamentoDivida FP WHERE FP.finDivida.id = F.id ) ");
		sql.append("	    AND    F.dataVencimento < sysdate ");
		sql.append("	    AND    F.dataQuitacao IS NOT NULL ");
		sql.append("		AND    F.dataQuitacao = (SELECT MAX(X.dataQuitacao) FROM FinDivida X ");
		sql.append("	                    				WHERE X.pessoa.id = :idPessoa ");
		sql.append("	                    				AND   X.natureza.id = 6 ");
		sql.append("	                    				AND   X.status.id <> 8 ");
		sql.append("	                    				AND   X.status.quitado = 1 ");
		sql.append("	    								AND   X.parcela > 0 ");
		sql.append("	    								AND   X.dataVencimento < sysdate ");
		sql.append("	    								AND   X.dataQuitacao IS NOT NULL) ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			finDivida = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getUltimaParcelaAnuidadeVencidaPagaPor", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return finDivida;
	}

	public boolean pessoaPossuiDividaAnuidadeParcelada(Long idPessoa) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(F) FROM FinDivida F ");
		sql.append("	WHERE F.pessoa.id = :idPessoa ");
		sql.append("	AND   F.natureza.id = 6 ");
		sql.append("	AND   F.status.id = 19 ");
		sql.append("	AND   F.parcela = 0 ");

		try {

			Query query = em.createQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);

			Long quantidadeParcelamentos = (Long) query.getSingleResult();
			return quantidadeParcelamentos > new Long(0) ? true : false;

		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || pessoaPossuiDividaAnuidadeParcelada", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return false;
	}

	public boolean dividaArtEstaQuitada(String identificadorDivida) {

		FinDivida finDivida = new FinDivida();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE  F.natureza.id = 1 ");
		sql.append("	AND    F.parcela = 0 ");
		sql.append("	AND    F.status.id IN (4, 10, 22) ");
		sql.append("	AND    F.dataQuitacao IS NOT NULL ");
		sql.append("	AND    F.identificadorDivida = :identificadorDivida");

		try {

			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("identificadorDivida", identificadorDivida);

			finDivida = query.getSingleResult();
			return finDivida != null ? true : false;

		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || dividaArtEstaQuitada", StringUtil.convertObjectToJson(identificadorDivida), e);
		}
		return false;
	}
	
	public List<FinDivida> dividasArtsNaoQuitadas(String numeroArt) {
		
		List<FinDivida> listDividas = new ArrayList<FinDivida>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	AND    F.parcela = 0 ");
		sql.append("	AND    F.status.quitado = 0 ");
		sql.append("	AND    F.identificadorDivida = :numeroArt");
		
		try {
			
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("numeroArt", numeroArt);
			
			listDividas = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || dividasArtsNaoQuitadas", StringUtil.convertObjectToJson(numeroArt), e);
		}
		
		return listDividas;
	}

	public FinDivida buscaDividaAutoInfracaoPor(String identificadorDivida, Long idPessoa) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE F.identificadorDivida = :identificadorDivida ");
		sql.append("	AND   F.pessoa.id = :idPessoa ");
		sql.append("	AND   F.parcela = 0 ");

		try {

			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("identificadorDivida", identificadorDivida);
			query.setParameter("idPessoa", idPessoa);

			return (FinDivida) query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || buscaDividaAutoInfracaoPor", StringUtil.convertObjectToJson(identificadorDivida), e);
		}
		return null;

	}

	public void atualizaStatusDividaEmRecursoPor(Long numeroProtocolo) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE Fin_Divida F ");
		sql.append("      SET F.fk_codigo_status_divida = 9 ");
		sql.append(" 	  WHERE F.identificador_divida = :numeroProtocolo ");
		sql.append(" 	  AND   F.fk_codigo_natureza = 2 ");
		sql.append(" 	  AND   F.fk_codigo_status_divida NOT IN (4, 5, 8, 9, 10, 19, 21, 25, 26) ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || atualizaStatusDividaEmRecursoPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}

	}

	public FinDivida getTaxaQuitadaRegistroEmpresa(Long idPessoa) {
		FinDivida finDivida = new FinDivida();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE  F.natureza.id = 404 ");
		sql.append("	AND    F.status.id IN (4, 10, 22) ");
		sql.append("	AND    F.dataQuitacao IS NOT NULL ");
		sql.append("	AND    F.pessoa.id = :idPessoa ");
		sql.append("	AND    F.id = (SELECT MAX(X.id) FROM FinDivida X ");
		sql.append("                   		WHERE X.pessoa.id = :idPessoa ");
		sql.append("						AND   X.natureza.id = 404 ");
		sql.append("						AND   X.status.id IN (4, 10, 22) ");
		sql.append("                  ) ");

		try {

			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);

			finDivida = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getTaxaQuitadaRegistroEmpresa", StringUtil.convertObjectToJson(idPessoa), e);
		}
		return finDivida;
	}

	public FinDivida getDividaPor(String identificadorDivida) {
		FinDivida finDivida = new FinDivida();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE  F.identificadorDivida = :identificadorDivida ");
		sql.append("	AND    F.parcela = 0 ");

		try {

			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("identificadorDivida", identificadorDivida);

			finDivida = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getDividaPor", StringUtil.convertObjectToJson(identificadorDivida), e);
		}
		return finDivida;
	}
	
	public boolean existeDividaParaGerarTermoInscricao(String identificadorDivida) {
		FinDivida finDivida = new FinDivida();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT F FROM FinDivida F ");
		sql.append("	WHERE  F.identificadorDivida = :identificadorDivida ");
		sql.append("	AND    F.natureza.id = 2 ");
		sql.append("	AND    F.parcela = 0 ");
		sql.append("	AND    F.status.quitado = 0 ");
		sql.append("	AND    F.status.id != 19 ");

		try {

			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("identificadorDivida", identificadorDivida);

			finDivida = query.getSingleResult();
			
			return finDivida != null ? true : false;

		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || existeDividaParaGerarTermoInscricao", StringUtil.convertObjectToJson(identificadorDivida), e);
		}
		return false;
	}

	public List<FinDivida> getDebitosAnuidadePorPessoa(Long idPessoa) {
		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("   SELECT f FROM FinDivida f ");
		sql.append("    WHERE f.pessoa.id = :idPessoa ");
		sql.append("      AND f.status.quitado = 0 ");
		sql.append("      AND f.natureza.id = 6 ");
		sql.append(" ORDER BY f.identificadorDivida, f.parcela ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			listFinDivida = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getDebitosPorPessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return listFinDivida;
	}

	public List<FinDivida> getDebitosArtPorPessoa(Long idPessoa) {
		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("   SELECT f FROM FinDivida f ");
		sql.append("    WHERE f.pessoa.id = :idPessoa ");
		sql.append("      AND f.status.quitado = 0 ");
		sql.append("      AND f.natureza.id = 1 ");
		sql.append(" ORDER BY f.identificadorDivida, f.parcela ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			listFinDivida = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getDebitosPorEmpresa", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return listFinDivida;
	}
	
	
	public List<FinDivida> getDebitosArtETaxaIncorporacaoPorPessoa(Long idPessoa) {
		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("   SELECT f FROM FinDivida f ");
		sql.append("    WHERE f.pessoa.id = :idPessoa ");
		sql.append("      AND f.status.quitado = 0 ");
		sql.append("      AND f.natureza.id in (1,800) ");
		sql.append(" ORDER BY f.dataVencimento DESC ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			listFinDivida = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getDebitosArtETaxaIncorporacaoPorPessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return listFinDivida;
	}

	public List<FinDivida> getDebitosTaxasPorPessoa(Long idPessoa) {
		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("   SELECT f FROM FinDivida f ");
		sql.append("    WHERE f.pessoa.id = :idPessoa ");
		sql.append("      AND f.status.id = 1 ");
		sql.append("      AND TO_CHAR(f.dataVencimento, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD') ");
		sql.append("      AND f.natureza.id IN (403, 404, 405) ");
		sql.append(" ORDER BY f.identificadorDivida ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			
			listFinDivida = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getDebitosTaxasPorPessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return listFinDivida;
	}


	public boolean verificaTaxaPaga(Long idPessoa, Long idAssunto) {
		List<FinDivida> listaDividas = new ArrayList<FinDivida>();
		
		Long idNaturezaDivida = null;
		if (idAssunto.equals(new Long(1001))) {
			idNaturezaDivida = new Long(403);
		}
		if (idAssunto.equals(new Long(2001))) {
			idNaturezaDivida = new Long(404);
		}
		if (idAssunto.equals(new Long(2003))) {
			idNaturezaDivida = new Long(405);
		}
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT F FROM FinDivida F                ");
		sql.append("  WHERE F.natureza.id = :idNaturezaDivida ");
		sql.append("    AND F.pessoa.id = :idPessoa           ");
		sql.append("    AND F.status.id = 4                   ");
		sql.append("    AND F.servicoExecutado = 0            ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("idNaturezaDivida", idNaturezaDivida);
			
			listaDividas = query.getResultList();
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || verificaTaxaPaga", StringUtil.convertObjectToJson(idPessoa + " -- " + idAssunto), e);
		}

		return listaDividas.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<FinDivida> buscaTodasDivNaoQuitadasByPessoaParaReabilitacao(Long codigo) {
		List<FinDivida> resultado = new ArrayList<FinDivida>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT div.* ");
		sql.append("	FROM Fin_Divida Div, fin_status_divida sta,fin_natureza nat ");
		sql.append("	WHERE Div.Fk_Codigo_Pessoa = :codigo ");
		sql.append("	AND Div.Parcela = 0 ");
		sql.append("	AND Sta.Codigo = Div.Fk_Codigo_Status_Divida ");
		sql.append("	AND Nat.Codigo = Div.fk_codigo_natureza ");
		sql.append("	AND Div.Fk_Codigo_Status_Divida Not In (9, 19) ");
		sql.append("	AND ( (sta.quitado  = 0 and nat.fk_tipo_recebivel <> 4 and nat.codigo <> 1 and div.data_vencimento < sysdate) ");
		sql.append("	OR (Div.Fk_Codigo_Status_Divida In (3, 23) and div.data_vencimento < sysdate) ");
		sql.append("	OR (Div.Fk_Codigo_Natureza = 800 ");
		sql.append("	AND div.fk_codigo_status_divida < 4 ");
		sql.append("	AND Exists (Select X.Codigo From Fin_Divida X ");
		sql.append("					WHERE X.Fk_Codigo_Pessoa = :codigo ");
		sql.append("					AND X.Fk_Codigo_Natureza = 1 ");
		sql.append("					AND X.Identificador_Divida = Div.Identificador_Divida ");
		sql.append("					AND x.fk_codigo_status_divida = 4) ) ) ");
		sql.append("ORDER BY nat.descricao, div.identificador_divida");
		
		Query query = em.createNativeQuery(sql.toString(), FinDivida.class);
		query.setParameter("codigo", codigo); 
		try {
			resultado = query.getResultList();
		} catch (NoResultException e) {
			return resultado;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || buscaTodasDivNaoQuitadasByPessoaParaReabilitacao", StringUtil.convertObjectToJson(codigo), e);
		}
		
		return resultado;
	}
	
	public List<FinDivida> getDebitosArtPorNumero(String identificadorDivida) {
		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("   SELECT f FROM FinDivida f ");
		sql.append("    WHERE f.identificadorDivida = :identificadorDivida ");
		sql.append("      AND f.status < 4 ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("identificadorDivida", identificadorDivida);
			listFinDivida = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getDebitosArtPorNumero", StringUtil.convertObjectToJson(identificadorDivida), e);
		}

		return listFinDivida;
	}

	public void cancelarDividaDeArtETaxaDeIncorporacao(String numeroArtPrincipal) {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE FIN_DIVIDA D                                   ");
		sql.append("    SET D.FK_CODIGO_STATUS_DIVIDA = 8                  ");
		sql.append("  WHERE D.IDENTIFICADOR_DIVIDA = :numeroArtPrincipal   ");
		sql.append("    AND D.FK_CODIGO_NATUREZA IN (1, 800)               ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroArtPrincipal", numeroArtPrincipal);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || darBaixaEmDividaDeArtETaxaDeIncorporacao", StringUtil.convertObjectToJson(numeroArtPrincipal), e);
		}
	}
	
	public boolean pessoaPossuiDivida(Long idPessoa) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(D.CODIGO) FROM FIN_DIVIDA D                       ");
		sql.append("  WHERE D.FK_CODIGO_PESSOA = :idPessoa                          ");
		sql.append("    AND (D.FK_CODIGO_NATUREZA IN (1, 2, 6, 700)                 ");
		sql.append("    AND D.FK_CODIGO_STATUS_DIVIDA IN (0, 1, 2, 3, 19, 21, 23)   ");
		sql.append("     OR D.FK_CODIGO_STATUS_DIVIDA = 3)                          ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);
			
			BigDecimal resultado = (BigDecimal) query.getSingleResult();
			
			return resultado.compareTo(new BigDecimal(0)) > 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || pessoaPossuiDivida", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return false;
	}


	public List<FinDivida> getDebitosAnuidadeQuadroTecnicoPorPessoa(Long idPessoa) {
		
		List<FinDivida> listFinDivida = new ArrayList<FinDivida>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT D FROM FinDivida D                        ");
		sql.append("  WHERE D.pessoa.id = :idPessoa                   ");
		sql.append("    AND (D.natureza.id IN (1, 2, 6, 700)          ");
		sql.append("    AND D.status.id IN (0, 1, 2, 3, 19, 21, 23)   ");
		sql.append("     OR D.status.id = 3)                          ");

		try {
			TypedQuery<FinDivida> query = em.createQuery(sql.toString(), FinDivida.class);
			query.setParameter("idPessoa", idPessoa);
			
			listFinDivida = query.getResultList();
			return listFinDivida;
			
		} catch (NoResultException e) {
			return listFinDivida;
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDividaDao || getDebitosAnuidadeQuadroTecnicoPorPessoa", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return listFinDivida;
	}
}
