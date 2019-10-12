package br.org.crea.commons.dao.corporativo;

import java.io.Serializable;
import java.text.NumberFormat;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.GeradorSequencia;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class GeradorSequenciaDao extends GenericDao<GeradorSequencia, Serializable>{

	@Inject HttpClientGoApi httpGoApi;

	public GeradorSequenciaDao(){
		super(GeradorSequencia.class);
	}
	
	public long getSequencia(long tipo) {
		GeradorSequencia gerador = new GeradorSequencia();
		
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT G FROM GeradorSequencia G ");
		sql.append("	 WHERE G.ano = :ano ");
		sql.append("	 AND G.tipo =  :tipo ");
		sql.append("	 AND G.paraNotificacao = false ");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("ano", (long) DateUtils.getAnoCorrente());
		query.setParameter("tipo", tipo);
		
		try {
			
			gerador = (GeradorSequencia) query.getSingleResult();	
			gerador.setSequencial(gerador.getSequencial() + 1);
								
		} catch (NoResultException e) {
			
			gerador = new GeradorSequencia();
			gerador.setSequencial(1);
			gerador.setAno((long) DateUtils.getAnoCorrente());
			gerador.setTipo(tipo);
			gerador.setParaNotificacao(false);
			em.persist(gerador);
		}

		catch (Throwable e) {
			httpGoApi.geraLog("GeradorSequenciaDao || getSequenciaProtocolo", StringUtil.convertObjectToJson(tipo), e);
		}
			
		return formataSequencia(DateUtils.getAnoCorrente(), tipo, gerador.getSequencial());
		
	}
	/**
	 * Nesse método de gerar sequencia, foi necessário capturar a execeção OptimisticLockException devido
	 * a acesso simultaneo. Ele retorna 0 para um nova execução. FIXME: Refatorar
	 * @param tipo
	 * @return long
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long getSequenciaWithFlush(long tipo) {
		
		GeradorSequencia gerador = null;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT G FROM GeradorSequencia G ");
		sql.append("	 WHERE G.ano = :ano ");
		sql.append("	 AND G.tipo =  :tipo ");
		sql.append("	 AND G.paraNotificacao = false ");
		
		try {

			Query query = em.createQuery(sql.toString());
			query.setParameter("ano", (long) DateUtils.getAnoCorrente());
			query.setParameter("tipo", tipo);			
			gerador = (GeradorSequencia) query.getSingleResult();
			gerador.setSequencial(gerador.getSequencial() + 1);
			em.flush();
								
		} catch (NoResultException e) {
			
			gerador = new GeradorSequencia();
			gerador.setSequencial(1);
			gerador.setAno((long) DateUtils.getAnoCorrente());
			gerador.setTipo(tipo);
			gerador.setParaNotificacao(false);
			em.persist(gerador);
			em.flush();
			
		} catch (OptimisticLockException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("GeradorSequenciaDao || getSequenciaProtocolo", StringUtil.convertObjectToJson(tipo), e);
		}
		return formataSequencia(DateUtils.getAnoCorrente(), tipo, gerador.getSequencial());
		
	}
	
	private long formataSequencia(long ano, long tipo, long sequencial){
		
		StringBuilder montador = new StringBuilder();
		NumberFormat nf = NumberFormat.getInstance();
		
		if(tipo == 7 || tipo == 0){
			nf.setMinimumIntegerDigits(7);
		}else{
			nf.setMinimumIntegerDigits(5);
		}
		
		nf.setGroupingUsed(false);
		nf.setParseIntegerOnly(true);
		
		montador.append(ano);
		montador.append(tipo);
		montador.append(nf.format(sequencial));
		
		return new Long(montador.toString());
	}
}
