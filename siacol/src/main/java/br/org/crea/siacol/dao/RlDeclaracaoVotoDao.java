package br.org.crea.siacol.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.RlDeclaracaoVoto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RlDeclaracaoVotoDao extends GenericDao<RlDeclaracaoVoto, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public RlDeclaracaoVotoDao() {
		super(RlDeclaracaoVoto.class);
	}

	public List<RlDeclaracaoVoto> getDeclaracaoByIdItem(Long idItem) {
		
		List<RlDeclaracaoVoto> listDeclaracao = new ArrayList<RlDeclaracaoVoto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DV FROM RlDeclaracaoVoto DV ");
		sql.append("    WHERE DV.item.id = :idItem");

		try {

			TypedQuery<RlDeclaracaoVoto> query = em.createQuery(sql.toString(), RlDeclaracaoVoto.class);
			query.setParameter("idItem", idItem);

			listDeclaracao = query.getResultList();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDeclaracaoVotoDao || getDeclaracaoByIdItem", StringUtil.convertObjectToJson(idItem), e);
		}
		return listDeclaracao;
	}

	public boolean validaDeclaracaoVoto(Long idItem) {
				
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DV FROM RlDeclaracaoVoto DV ");
		sql.append("    WHERE DV.item.id = :idItem");
		sql.append("    AND DV.idArquivoDocflow is null ");

		try {

			TypedQuery<RlDeclaracaoVoto> query = em.createQuery(sql.toString(), RlDeclaracaoVoto.class);
			query.setParameter("idItem", idItem);

			return query.getResultList().isEmpty() ? true : false;
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDeclaracaoVotoDao || getDeclaracaoByIdItem", StringUtil.convertObjectToJson(idItem), e);
		}
		return false;
		
	}

	public RlDeclaracaoVoto getByItemConselheiro(Long idItem, Long idConselheiro) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DV FROM RlDeclaracaoVoto DV ");
		sql.append("    WHERE DV.item.id = :idItem ");
		sql.append("    AND DV.conselheiro = :idConselheiro ");

		try {

			TypedQuery<RlDeclaracaoVoto> query = em.createQuery(sql.toString(), RlDeclaracaoVoto.class);
			query.setParameter("idItem", idItem);
			query.setParameter("idConselheiro", idConselheiro);

			return query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDeclaracaoVotoDao || getDeclaracaoByIdItem", StringUtil.convertObjectToJson(idItem), e);
		}
		return null;
	}


}
