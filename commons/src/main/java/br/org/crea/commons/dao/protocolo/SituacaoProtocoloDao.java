package br.org.crea.commons.dao.protocolo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.SituacaoProtocolo;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class SituacaoProtocoloDao extends GenericDao<SituacaoProtocolo, Serializable>{
	
	@Inject HttpClientGoApi httpGoApi;
	
	public SituacaoProtocoloDao() {
		super(SituacaoProtocolo.class);
	}

	public List<SituacaoProtocolo> getSituacaoSiacol() {
					
			List<SituacaoProtocolo> situacao = new ArrayList<SituacaoProtocolo>();
			
			try {
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT S FROM SituacaoProtocolo S ");
				sql.append("   WHERE S.siacol = 1 ");
								
				TypedQuery<SituacaoProtocolo> query = em.createQuery(sql.toString(), SituacaoProtocolo.class);
				situacao = query.getResultList();	
			} catch (Exception e) {
				httpGoApi.geraLog("SituacaoProtocoloDao || getSituacaoSiacol", StringUtil.convertObjectToJson(" sem parametro "), e);
			}

			return situacao;
		
	}

	public SituacaoProtocolo getSituacaoByCodigo(Long codigoSituacao) {
		SituacaoProtocolo situacaoProtocolo = new SituacaoProtocolo();
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT S FROM SituacaoProtocolo S ");
			sql.append("   WHERE S.codigo = :codigo ");
							
			TypedQuery<SituacaoProtocolo> query = em.createQuery(sql.toString(), SituacaoProtocolo.class);
			query.setParameter("codigo", codigoSituacao);
			
			situacaoProtocolo = query.getSingleResult();
			
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("SituacaoProtocoloDao || getSituacaoByCodigo", StringUtil.convertObjectToJson(codigoSituacao), e);
		}

		return situacaoProtocolo;
	
	}	

	public SituacaoProtocolo verificaCodigo(Long codigo) {
		
		SituacaoProtocolo situacao = new SituacaoProtocolo();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM SituacaoProtocolo A ");
		sql.append("     WHERE A.codigo = :codigo ");

		try {

			TypedQuery<SituacaoProtocolo> query = em.createQuery(sql.toString(),SituacaoProtocolo.class);
			query.setParameter("codigo", codigo);

			situacao = query.getSingleResult();
			
		}catch (NoResultException e) {
			return null;
		}
		catch (Throwable e) {
			httpGoApi.geraLog("SituacaoProtocoloDao || verificaCodigo",	StringUtil.convertObjectToJson(codigo), e);
		}
		return situacao;
	}
}
