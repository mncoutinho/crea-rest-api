package br.org.crea.commons.dao.art;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.art.ArtCUB;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ArtCUBDao extends GenericDao<ArtCUB, Serializable>{
	
	@Inject
	HttpClientGoApi httpGoApi;

	public ArtCUBDao() {
		super(ArtCUB.class);
	}
	
	public ArtCUB buscarPorMesEAno(int mes, int ano){
		ArtCUB artCUB = new ArtCUB();
		mes = mes + 1;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ArtCUB C                                      ");
		sql.append("  WHERE C.codigo IN (                                        ");
		sql.append("  	SELECT max(C2.codigo) FROM ArtCUB C2                     ");
		sql.append("  	WHERE  (C2.ano * 100 + C2.mes) <= (:ano * 100 + :mes)    ");
		sql.append("  )                                                          ");
		
		TypedQuery<ArtCUB> query = em.createQuery(sql.toString(),ArtCUB.class);
		query.setParameter("mes", mes);
		query.setParameter("ano", ano);
		
		try{
			artCUB =  (ArtCUB) query.getSingleResult();
		}catch(NoResultException e){
			httpGoApi.geraLog("ArtCUBDao || buscarPorMesEAno", StringUtil.convertObjectToJson(mes+','+ano), e);
			artCUB.setValor(new BigDecimal(0));
			return artCUB;
		}catch(Exception e){
			httpGoApi.geraLog("ArtCUBDao || buscarPorMesEAno", StringUtil.convertObjectToJson(mes+','+ano), e);
			return null;
		}
		return artCUB;
	}
}
