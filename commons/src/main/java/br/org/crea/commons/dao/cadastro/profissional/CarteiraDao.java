package br.org.crea.commons.dao.cadastro.profissional;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.Carteira;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class CarteiraDao extends GenericDao<Carteira, Serializable> {
	
	@Inject
	HttpClientGoApi httpGoApi;

	public CarteiraDao() {
		super(Carteira.class);
	}

	public Carteira buscaCarteiraAtivaPorProfissional(String numeroRegistro) {
		Carteira carteira = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c FROM Carteira c ");
		sql.append("WHERE  c.profissional.id = :numeroRegistro ");
		sql.append("AND    c.cancelada = false ");

		try {
			TypedQuery<Carteira> query = em.createQuery(sql.toString(), Carteira.class);
			query.setParameter("numeroRegistro", new Long(numeroRegistro));

			carteira = query.getSingleResult();

		} catch (NoResultException e) {
			return carteira;
		} catch (Throwable e) {
			httpGoApi.geraLog("CarteiraDao || buscaCarteiraAtivaPorProfissional", StringUtil.convertObjectToJson(numeroRegistro), e);
		}
		return carteira;
	}

	/**
	 * A informação de validade de carteiras emitidas pelo Confea é presumida, pois não temos a informação precisa.
	 * 
	 * @param idProfissional
	 * @return
	 */
	public Boolean ultimaCarteiraEhSemValidade(Long idProfissional) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C.VALIDADE FROM CAD_CARTEIRAS C             ");
		sql.append("  WHERE C.FK_CODIGO_PROFISSIONAIS = :idProfissional ");
		sql.append("    AND C.CANCELADA = 0                             ");
		sql.append("  ORDER BY C.DATAEMISSAO                            ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);

			return query.getSingleResult() == null;

		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("CarteiraDao || ultimaCarteiraEhSemValidade", StringUtil.convertObjectToJson(idProfissional), e);
		}
		return false;
	}
	
	public boolean ultimaCarteiraAtivaEhDefinitiva(Long idProfissional) {

		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT COUNT(C.CODIGO) FROM CAD_CARTEIRAS C          ");
		sql.append("   WHERE C.CODIGO =                                    ");
		sql.append(" (SELECT MAX(C2.CODIGO) FROM CAD_CARTEIRAS C2          ");
		sql.append("   WHERE C2.FK_CODIGO_PROFISSIONAIS = :idProfissional  ");
		sql.append("     AND C2.CANCELADA = 0)                             ");
		sql.append("     AND C.FK_CODIGO_TIPOS_CARTEIRAS IN (4, 12)        ");
	    
	    try {
	       Query query = em.createNativeQuery(sql.toString());
	 	   query.setParameter("idProfissional", idProfissional);
	 	   
	 	   BigDecimal resultado = (BigDecimal) query.getSingleResult();
	 	    
	 	   return resultado.compareTo(new BigDecimal(0)) > 0; 
	 	   
	    } catch (NoResultException e) {
	        return false;
	    } catch (Throwable e) {
			httpGoApi.geraLog("CarteiraDao || ultimaCarteiraAtivaEhDefinitiva", StringUtil.convertObjectToJson(idProfissional), e);
		}
	    return false;
	}

	public boolean existePedidoNaFilaDeImpressao(Long idProfissional) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(F.CODIGO)                                                       ");
		sql.append("   FROM CAD_CARTEIRAS_FILA F, ADM_UNIDADE_ATENDIMENTO U, PRT_DEPARTAMENTOS D  ");
		sql.append("  WHERE F.FK_CODIGO_PROFISSIONAL = :idProfissional                            ");
		sql.append("    AND F.ATIVO = 1                                                           ");
		sql.append("    AND U.CODIGO = F.FK_UNIDADE_ATENDIMENTO                                   ");
		sql.append("    AND D.ID = U.FK_CODIGO_DEPARTAMENTO                                       ");
		
	    try {
	    	Query query = em.createNativeQuery(sql.toString());
		    query.setParameter("idProfissional", idProfissional);
		    
		    BigDecimal resultado = (BigDecimal) query.getSingleResult();
	 	    
		 	return resultado.compareTo(new BigDecimal(0)) > 0; 
	        
	    } catch (NoResultException e) {
	        return false;
	    } catch (Exception e) {
	    	httpGoApi.geraLog("CarteiraDao || existePedidoNaFilaDeImpressao", StringUtil.convertObjectToJson(idProfissional), e);
	    }
	    
	    return false;   
	}
	

}
