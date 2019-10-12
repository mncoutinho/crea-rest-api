package br.org.crea.commons.dao.art;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.art.ArtQuantificacao;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArtQuantificacaoDao extends GenericDao<ArtQuantificacao, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public ArtQuantificacaoDao() {
		super(ArtQuantificacao.class);
	}

	public ArtQuantificacao getByIdContrato(String idContrato) {
		
		ArtQuantificacao quantificacao = new ArtQuantificacao();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT Q FROM ArtQuantificacao Q   ");
		sql.append("  WHERE Q.contrato.id = :idContrato ");

		try {

			TypedQuery<ArtQuantificacao> query = em.createQuery(sql.toString(), ArtQuantificacao.class);
			query.setParameter("idContrato", idContrato);

			quantificacao = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtQuantificacaoDao || getByIdContrato", StringUtil.convertObjectToJson(idContrato), e);
		}

		return quantificacao;
	}
	
	public BigDecimal getValorByIdContrato(String idContrato) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT Q.VALOR FROM ART_QUANTIFICACAO Q   ");
		sql.append("  WHERE Q.FK_CONTRATO = :idContrato        ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idContrato", idContrato);

			return (BigDecimal) query.getSingleResult();
			
		} catch (NoResultException e) {
			return new BigDecimal(0);
		} catch (Throwable e) {
			httpGoApi.geraLog("ArtQuantificacaoDao || getValorByIdContrato", StringUtil.convertObjectToJson(idContrato), e);
		}

		return new BigDecimal(0);
	}
}
