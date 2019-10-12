package br.org.crea.commons.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.siacol.ReuniaoSiacolDao;
import br.org.crea.commons.models.cadastro.NumeroDocumento;
import br.org.crea.commons.models.cadastro.TipoDocumento;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class NumeroDocumentoDao extends GenericDao<NumeroDocumento, Serializable>{

	@Inject HttpClientGoApi httpGoApi;
	
	@Inject ReuniaoSiacolDao reuniaoSiacolDao;
	
	public NumeroDocumentoDao() {
		super(NumeroDocumento.class);
	}

	public NumeroDocumento getNumeroDocumento(Long idDepartamento, Long idTipoDocumento) {
		
		NumeroDocumento numeroDocumento = new NumeroDocumento();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT D FROM NumeroDocumento D            ");
		sql.append("  WHERE D.departamento.id = :idDepartamento ");
		sql.append("	AND D.tipo.id = :idTipoDocumento)       ");
		sql.append("  ORDER BY D.tipo.descricao                 ");

		try {
			TypedQuery<NumeroDocumento> query = em.createQuery(sql.toString(), NumeroDocumento.class);
			query.setParameter("idDepartamento", idDepartamento);
			query.setParameter("idTipoDocumento", idTipoDocumento);
			
			numeroDocumento = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("NumeroDocumentoDao || getNumeroDocumento", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		
		return numeroDocumento;
	}

	public List<NumeroDocumento> getNumeroDocumentoDepartamento(Long idDepartamento) {
		
		List<NumeroDocumento> listNumeroDocumento = new ArrayList<NumeroDocumento>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM NumeroDocumento D ");
		sql.append("	WHERE D.departamento.id = :idDepartamento ");
		sql.append("   	ORDER BY D.tipo.descricao " );

		try {
			TypedQuery<NumeroDocumento> query = em.createQuery(sql.toString(), NumeroDocumento.class);
			query.setParameter("idDepartamento", idDepartamento);
			
			listNumeroDocumento = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("NumeroDocumentoDao || getNumeroDocumentoDepartamento", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		
		return listNumeroDocumento;
	}

	public List<TipoDocumento> getNumeroDocumentoParaCriacao(Long idDepartamento) {
		
		List<TipoDocumento> listTipoDocumento = new ArrayList<TipoDocumento>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TD FROM TipoDocumento TD ");
		sql.append("	WHERE TD.modulo = 2 ");
		sql.append("	AND TD.id not in ( ");
		sql.append("		SELECT D.tipo.id FROM NumeroDocumento D ");
		sql.append("		WHERE D.departamento.id = :idDepartamento ");
		sql.append("    ) ORDER BY TD.descricao " );

		try {
			TypedQuery<TipoDocumento> query = em.createQuery(sql.toString(), TipoDocumento.class);
			query.setParameter("idDepartamento", idDepartamento);
			
			listTipoDocumento = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("NumeroDocumentoDao || getNumeroDocumentoParaCriacao", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		
		return listTipoDocumento;
	}


	

}
