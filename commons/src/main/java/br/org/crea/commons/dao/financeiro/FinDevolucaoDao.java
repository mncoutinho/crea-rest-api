package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.FinDevolucao;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class FinDevolucaoDao extends GenericDao<FinDevolucao, Serializable> {

	@Inject
	private HttpClientGoApi httpGoApi;

	public FinDevolucaoDao() {
		super(FinDevolucao.class);
	}

	public List<FinDevolucao> getDevolucaoByIdDivida(Long idDivida) {

		List<FinDevolucao> listFinDevolucao = new ArrayList<FinDevolucao>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT F FROM FinDevolucao F   ");
		sql.append("  WHERE F.idDivida = :idDivida  ");

		try {
			TypedQuery<FinDevolucao> query = em.createQuery(sql.toString(), FinDevolucao.class);
			query.setParameter("idDivida", idDivida);
			
			listFinDevolucao = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDevolucaoDao || getDevolucaoByIdDivida", StringUtil.convertObjectToJson(idDivida), e);
		}

		return listFinDevolucao;

	}
	
	public List<FinDevolucao> getDevolucaoByIdBoleto(Long idBoleto) {

		List<FinDevolucao> listFinDevolucao = new ArrayList<FinDevolucao>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT F FROM FinDevolucao F   ");
		sql.append("  WHERE F.idBoleto = :idBoleto  ");

		try {
			TypedQuery<FinDevolucao> query = em.createQuery(sql.toString(), FinDevolucao.class);
			query.setParameter("idBoleto", idBoleto);
			
			listFinDevolucao = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FinDevolucaoDao || getDevolucaoByIdBoleto", StringUtil.convertObjectToJson(idBoleto), e);
		}

		return listFinDevolucao;

	}

}
