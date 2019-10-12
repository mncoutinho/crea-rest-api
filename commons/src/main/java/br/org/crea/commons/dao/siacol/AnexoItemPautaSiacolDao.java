package br.org.crea.commons.dao.siacol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.siacol.AnexosItemPautaReuniaoSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class AnexoItemPautaSiacolDao extends GenericDao<AnexosItemPautaReuniaoSiacol, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public AnexoItemPautaSiacolDao() {
		super(AnexosItemPautaReuniaoSiacol.class);
	}

	public void deletaAnexo(Long idArquivo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  SIACOL_ANEXO_ITEM_PAUTA S ");
		sql.append("	 WHERE S.FK_ARQUIVO = :idArquivo ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idArquivo", idArquivo);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("AnexoItemPautaSiacolDao || deletaAnexo", StringUtil.convertObjectToJson(idArquivo), e);
		}
		
	}
	
	public void deletaAnexoItem(Long idItem) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  SIACOL_ANEXO_ITEM_PAUTA S ");
		sql.append("	 WHERE S.FK_ITEM = :idItem ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idItem", idItem);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("AnexoItemPautaSiacolDao || deletaAnexoItem", StringUtil.convertObjectToJson(idItem), e);
		}
		
	}

	public List<Arquivo> getAnexosItensPauta(Long idDocumento) {
		
		List<AnexosItemPautaReuniaoSiacol> listaAnexos = new ArrayList<AnexosItemPautaReuniaoSiacol>();
		List<Arquivo> listaArquivo = new ArrayList<Arquivo>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT S FROM AnexosItemPautaReuniaoSiacol S  ");
		sql.append("	 WHERE S.item.documento.id = :idDocumento ");
		sql.append("  ORDER BY S.item.documento.id                ");
		
		try {
			TypedQuery<AnexosItemPautaReuniaoSiacol> query = em.createQuery(sql.toString(), AnexosItemPautaReuniaoSiacol.class);
			query.setParameter("idDocumento", idDocumento);
			
			listaAnexos = query.getResultList();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AnexoItemPautaSiacolDao || getAnexosItensPauta", StringUtil.convertObjectToJson(idDocumento), e);
		}
		
		listaAnexos.forEach(anexo -> {
			listaArquivo.add(anexo.getArquivo());
		});
		return listaArquivo;
	}

	public List<Arquivo> getAnexosByItem(Long idItem) {
		
		List<Arquivo> listaArquivo = new ArrayList<Arquivo>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT S.arquivo FROM AnexosItemPautaReuniaoSiacol S  ");
		sql.append("	 WHERE S.item.id = :idItem ");
		
		try {
			TypedQuery<Arquivo> query = em.createQuery(sql.toString(), Arquivo.class);
			query.setParameter("idItem", idItem);
			
			listaArquivo = query.getResultList();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AnexoItemPautaSiacolDao || getAnexosByItem", StringUtil.convertObjectToJson(idItem), e);
		}

		return listaArquivo;
	}



	


}
