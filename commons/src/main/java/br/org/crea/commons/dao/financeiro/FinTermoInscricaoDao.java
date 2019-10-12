package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.financeiro.FinTermosInscricao;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class FinTermoInscricaoDao extends GenericDao<FinTermosInscricao, Serializable>{
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	public FinTermoInscricaoDao(){
		super(FinTermosInscricao.class);
	}

	public boolean existeTermoInscricaoParaDivida(Long codigoDivida) {
		FinTermosInscricao termo = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T.codigo ");
		sql.append("	FROM  fin_divida DIV, ");
		sql.append("	      fin_divida_termos_inscricao DTI, ");
		sql.append("		  fin_termos_inscricao T ");
		sql.append("	WHERE DIV.codigo = :codigoDivida ");
		sql.append("	AND   DIV.codigo = DTI.fk_codigo_divida ");
		sql.append("	AND   DTI.fk_codigo_termos_inscricao = T.codigo ");
		sql.append("	AND   T.ativo = 1 ");
		sql.append("	AND   T.codigo IN (SELECT MAX(A.codigo)  ");
		sql.append("					 		  FROM  fin_divida_termos_inscricao B, ");
		sql.append("					 		  	    fin_termos_inscricao A ");
		sql.append("					 		  WHERE B.fk_codigo_divida = :codigoDivida ");
		sql.append("							  AND   A.codigo = B.fk_codigo_termos_inscricao ");
		sql.append("							  AND   A.ativo = 1 ");
		sql.append("					   )");
		
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("codigoDivida", codigoDivida);
		
		try {
			
			BigDecimal codigoTermo = (BigDecimal) query.getSingleResult();
			
			StringBuilder sql2 = new StringBuilder();
			sql2.append("SELECT T FROM FinTermosInscricao T ");
			sql2.append("	WHERE T.id = :codigoTermo ");

			Query query2 = em.createQuery(sql2.toString());
			query2.setParameter("codigoTermo", codigoTermo.longValue());

			termo = (FinTermosInscricao) query2.getSingleResult();
			return termo != null ? true : false;
			
		} catch (NoResultException e) {
			return false;	

		} catch (Throwable e) {
			httpGoApi.geraLog("TermosInscricaoDao || buscaTermoInscricaoDividaPor", StringUtil.convertObjectToJson(codigoDivida), e);
		}
		return false;
	}

}
