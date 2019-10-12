package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.Auditoria;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.dtos.PesquisaAuditoriaDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;


@Stateless
public class AuditoriaDao extends GenericDao<Auditoria, Serializable> {
	
	@Inject
	HttpClientGoApi httpGoApi;

	public AuditoriaDao() {
		super(Auditoria.class);
	}
	
	
	public List<Auditoria> pesquisa(PesquisaAuditoriaDto pesquisa) {

		List<Auditoria> listAuditoria = new ArrayList<Auditoria>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Auditoria A WHERE 1 = 1 ");
//		sql.append("     AND  TO_CHAR(A.dataCriacao, 'mm/dd/yyyy')  BETWEEN  TO_CHAR(:dataInicio, 'mm/dd/yyyy') AND TO_CHAR(:dataFim, 'mm/dd/yyyy')  ");
		sql.append(" 	 AND  A.evento = :evento ");
		
		if (pesquisa.getIdDepartamentoDestino() != null) {
			sql.append(" 	 AND  A.idDepartamentoDestino = :idDepartamentoDestino ");
		}
		if (pesquisa.getNumero() != null) {
			sql.append(" 	 AND  A.numero = :numero ");
		}
		
		
		sql.append("ORDER BY A.dataCriacao ");

		try {

			TypedQuery<Auditoria> query = em.createQuery(sql.toString(), Auditoria.class);
			query.setParameter("evento", TipoEventoAuditoria.getIdBy(pesquisa.getEvento()));
			if (pesquisa.getIdDepartamentoDestino() != null) {
				query.setParameter("idDepartamentoDestino", pesquisa.getIdDepartamentoDestino());
			}
			if (pesquisa.getNumero() != null) {
				query.setParameter("numero", pesquisa.getNumero());
			}
			
			
//			query.setParameter("hoje", new Date());
//			if (!pesquisa.getStatus().equals(new Long(99))) {
//				query.setParameter("status", pesquisa.getStatus());
//			}
//
//			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
//			page.paginate(query);

			listAuditoria = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AuditoriaDao || pesquisa", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return listAuditoria;
	}

}
