package br.org.crea.commons.dao.fiscalizacao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.org.crea.commons.models.art.RamoArt;
import br.org.crea.commons.models.fiscalizacao.ContratoAtividade;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class FiscalizacaoDomainDao {

	@Inject
	HttpClientGoApi httpGoApi;

	@PersistenceContext(unitName = "dscrea")
	protected EntityManager em;

	public List<RamoArt> getAllRamos() {
		List<RamoArt> lista = new ArrayList<RamoArt>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM RamoArt R ");
		sql.append("    WHERE R.fiscalizacao = 1 ");
		sql.append("	ORDER BY R.descricao ");

		try {
			TypedQuery<RamoArt> queryRamo = em.createQuery(sql.toString(), RamoArt.class);
			
			lista = queryRamo.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FiscalizacaoDomainDao || Get All getAllRamosFiscalizacao", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}

	public List<ContratoAtividade> getAllAtividades() {
		List<ContratoAtividade> lista = new ArrayList<ContratoAtividade>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ContratoAtividade C ");
		sql.append(" WHERE ativo = 1 ");
		sql.append(" ORDER BY C.descricao");

		try {
			TypedQuery<ContratoAtividade> query = em.createQuery(sql.toString(), ContratoAtividade.class);
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FiscalizacaoDomainDao || Get All ContratoAtividade", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}
	
	public List<ContratoAtividade> getAtividades(String tipoPJ) {
		List<ContratoAtividade> lista = new ArrayList<ContratoAtividade>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ContratoAtividade C ");
		sql.append(" WHERE ativo = 1 ");
		
		if(tipoPJ.equals("CONDOMINIO")){
			sql.append(" and C.condominio = 1 ");
		}
		sql.append(" ORDER BY C.descricao");

		try {
			TypedQuery<ContratoAtividade> query = em.createQuery(sql.toString(), ContratoAtividade.class);
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("FiscalizacaoDomainDao || Get All ContratoAtividade", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}

		return lista;
	}
}
