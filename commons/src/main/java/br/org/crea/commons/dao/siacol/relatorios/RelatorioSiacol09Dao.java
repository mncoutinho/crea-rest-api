package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol09Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	
	public RelatorioSiacol09Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public int qtdSemAssuntoSiacol(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoCorporativo, String status) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.FK_ASSUNTO) FROM SIACOL_PROTOCOLOS P  	");
		sql.append("    WHERE P.FK_RESPONSAVEL = :idAnalistaOuConselheiro	");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append("  AND P.FK_DEPARTAMENTO in (:idDepartamento)		");
		}
		sql.append("    AND P.ATIVO = 1			  							");
		sql.append("    AND P.STATUS = :status  							");
		sql.append("    AND P.FK_ASSUNTO_SIACOL IS NULL           			");
		sql.append("    AND P.FK_ASSUNTO = :idAssuntoCorporativo   			");
			
		try {
			Query query = em.createNativeQuery(sql.toString());
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idDepartamento", pesquisa.getListaIdsDepartamentos());
			}
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("status", status);
			query.setParameter("idAssuntoCorporativo", idAssuntoCorporativo);
			
			int numeroRetorno = Integer.parseInt(query.getSingleResult().toString());
			return numeroRetorno;
		
		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol09Dao || qtdSemAssuntoSiacol", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return 0;
	}
		
	public int qtdProtocolosPorAssuntoStatusEDepartamento(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, String status) {
		
	    StringBuilder sql = new StringBuilder();
	    sql.append(" SELECT COUNT(P.FK_ASSUNTO_SIACOL)                     	");
	    sql.append("   FROM SIACOL_PROTOCOLOS P                            	");
	    sql.append("    WHERE P.FK_RESPONSAVEL = :idAnalistaOuConselheiro	");
	    if (!pesquisa.isTodosDepartamentos()) {
	    	sql.append("  AND P.FK_DEPARTAMENTO in (:idDepartamento) 		");
		}
	    sql.append("    AND P.ATIVO = 1			  							");
		sql.append("    AND P.STATUS = :status   						   	");
		sql.append("    AND P.FK_ASSUNTO_SIACOL = :idAssuntoSiacol  	   	");
	  
	    try {
			Query query = em.createNativeQuery(sql.toString());
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idDepartamento", pesquisa.getListaIdsDepartamentos());
			}
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("status", status);
			query.setParameter("idAssuntoSiacol", idAssuntoSiacol);
			
			return Integer.parseInt(query.getSingleResult().toString());
			
		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol09Dao || qtdProtocolosPorAssuntoStatusEDepartamento", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return 0;
	}
}
