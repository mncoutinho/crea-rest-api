package br.org.crea.commons.dao.siacol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.RlReuniaoRelatorioSiacol;
import br.org.crea.commons.models.siacol.enuns.TipoRelatorioSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RlReuniaoRelatorioSiacolDao extends GenericDao<RlReuniaoRelatorioSiacol, Serializable>{

	@Inject
	HttpClientGoApi httpGoApi;
	
	public RlReuniaoRelatorioSiacolDao () {
		super(RlReuniaoRelatorioSiacol.class);
	}
	
	public RlReuniaoRelatorioSiacol getRelatorioPor(Long idReuniao, TipoRelatorioSiacolEnum tipo) {
		
		RlReuniaoRelatorioSiacol relatorio = new RlReuniaoRelatorioSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT R FROM RlReuniaoRelatorioSiacol R ");
		sql.append("  WHERE R.reuniao.id = :idReuniao         ");
		sql.append("	AND R.tipo = :tipo                    ");

		try {

			TypedQuery<RlReuniaoRelatorioSiacol> query = em.createQuery(sql.toString(), RlReuniaoRelatorioSiacol.class);
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("tipo", tipo);
			
			relatorio = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlReuniaoRelatorioSiacolDao || getRelatorioPor", StringUtil.convertObjectToJson(idReuniao + " - " + tipo), e);
		}
		
		return relatorio;
	}
	
	public List<RlReuniaoRelatorioSiacol> getRelatoriosPor(Long idReuniao) {
		
		List<RlReuniaoRelatorioSiacol> relatorio = new ArrayList<RlReuniaoRelatorioSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT R FROM RlReuniaoRelatorioSiacol R ");
		sql.append("  WHERE R.reuniao.id = :idReuniao         ");
		sql.append("  ORDER BY R.relatorio.dataInclusao       ");

		try {

			TypedQuery<RlReuniaoRelatorioSiacol> query = em.createQuery(sql.toString(), RlReuniaoRelatorioSiacol.class);
			query.setParameter("idReuniao", idReuniao);
			relatorio = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlReuniaoRelatorioSiacolDao || getRelatoriosPor", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return relatorio;
	}
	
	public boolean existeRelatorio(Long idReuniao, TipoRelatorioSiacolEnum tipo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT R FROM RlReuniaoRelatorioSiacol R ");
		sql.append("  WHERE R.reuniao.id = :idReuniao         ");
		sql.append("	AND R.tipo = :tipo                    ");

		try {

			TypedQuery<RlReuniaoRelatorioSiacol> query = em.createQuery(sql.toString(), RlReuniaoRelatorioSiacol.class);
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("tipo", tipo);
			
			return query.getSingleResult() != null;

		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlReuniaoRelatorioSiacolDao || existeRelatorio", StringUtil.convertObjectToJson(idReuniao + " - " + tipo), e);
		}
		
		return false;
	}
}
