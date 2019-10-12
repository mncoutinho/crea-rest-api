package br.org.crea.siacol.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.SiacolTags;
import br.org.crea.commons.models.siacol.dtos.SiacolTagsDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class SiacolTagsDao extends GenericDao<SiacolTags, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public SiacolTagsDao() {
		super(SiacolTags.class);
	}

	public boolean jaExisteTag(SiacolTagsDto tag) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ST FROM SiacolTags ST ");
		sql.append("    WHERE ST.descricao LIKE :descricaoTag");
		if (tag.getId() != null && tag.getId() != 0) {
			sql.append("    AND ST.id != :idTag ");
		}

		try {

			TypedQuery<SiacolTags> query = em.createQuery(sql.toString(), SiacolTags.class);
			query.setParameter("descricaoTag", tag.getDescricao());
			if (tag.getId() != null && tag.getId() != 0) {
				query.setParameter("idTag", tag.getId());
			}

			query.getSingleResult();
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolTagsDao || tagJaFoiCadastrada", StringUtil.convertObjectToJson(tag), e);
		}
		return true;
	}

	public SiacolTags getTagByDescricao(SiacolTagsDto tag) {
		
		SiacolTags siacolTags = new SiacolTags();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ST FROM SiacolTags ST ");
		sql.append("    WHERE ST.descricao LIKE :descricaoTag");

		try {

			TypedQuery<SiacolTags> query = em.createQuery(sql.toString(), SiacolTags.class);
			query.setParameter("descricaoTag", tag.getDescricao());

			siacolTags = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolTagsDao || tagJaFoiCadastrada", StringUtil.convertObjectToJson(tag), e);
		}
		return siacolTags;
	}

}
