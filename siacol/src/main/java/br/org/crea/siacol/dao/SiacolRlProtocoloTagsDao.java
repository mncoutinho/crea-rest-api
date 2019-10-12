package br.org.crea.siacol.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.SiacolRlProtocoloTags;
import br.org.crea.commons.models.siacol.SiacolTags;
import br.org.crea.commons.models.siacol.dtos.SiacolRlProtocoloTagsDto;
import br.org.crea.commons.models.siacol.dtos.SiacolTagsDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class SiacolRlProtocoloTagsDao  extends GenericDao<SiacolRlProtocoloTags, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public SiacolRlProtocoloTagsDao() {
		super(SiacolRlProtocoloTags.class);
	}

	public List<SiacolRlProtocoloTags> consultarTags(List<SiacolTagsDto> listTagsDto) {
		
		List<Long> listLong = new ArrayList<Long>();
		List<SiacolRlProtocoloTags> listaRlProtocoloTags = new ArrayList<SiacolRlProtocoloTags>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PT.numeroProtocolo");
		sql.append("    FROM SiacolRlProtocoloTags PT WHERE 1 = 1 ");

		sql.append("AND ");
		for (SiacolTagsDto s : listTagsDto) {
			if (listTagsDto.indexOf(s) == 0) {
				sql.append("PT.numeroProtocolo in ( SELECT PT.numeroProtocolo FROM SiacolRlProtocoloTags PT WHERE  PT.tag.descricao = '"+ s.getDescricao() + "' ) ");
			}else{
				sql.append("AND PT.numeroProtocolo in ( SELECT PT.numeroProtocolo FROM SiacolRlProtocoloTags PT WHERE  PT.tag.descricao = '"+ s.getDescricao() + "' ) ");
			}
		}
		sql.append("GROUP BY NUMERO_PROTOCOLO");

		try {

			TypedQuery<Long> query = em.createQuery(sql.toString(), Long.class);
			
			listLong = query.getResultList();
			
			for (Long numeroProtocolo : listLong) {
				SiacolTags tag = new SiacolTags();
				tag.setDescricao("");
				SiacolRlProtocoloTags siacolRlProtocoloTags = new SiacolRlProtocoloTags();
				siacolRlProtocoloTags.setId(new Long(0));
				siacolRlProtocoloTags.setNumeroProtocolo(numeroProtocolo);
				siacolRlProtocoloTags.setTag(tag);
				listaRlProtocoloTags.add(siacolRlProtocoloTags);
			}

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolRlProtocoloTagsDao || consultarTags", "SEM PARAMETRO", e);
		}
		return listaRlProtocoloTags;
	}

	public List<SiacolRlProtocoloTags> getTagsByProtocolo(Long numeroProtocolo) {

		List<SiacolRlProtocoloTags> listaRlProtocoloTags = new ArrayList<SiacolRlProtocoloTags>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PT FROM SiacolRlProtocoloTags PT ");
		sql.append("    WHERE PT.numeroProtocolo = :numeroProtocolo ");
		sql.append("		ORDER BY PT.tag.descricao");

		try {

			TypedQuery<SiacolRlProtocoloTags> query = em.createQuery(sql.toString(), SiacolRlProtocoloTags.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);

			listaRlProtocoloTags = query.getResultList();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolRlProtocoloTagsDao || getTagsByProtocolo", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return listaRlProtocoloTags;
	}

	public boolean tagJaFoiCadastrada(SiacolRlProtocoloTagsDto siacolRlProtocoloTagsDto) {
		
		//SiacolRlProtocoloTags siacolRlProtocoloTags = new SiacolRlProtocoloTags();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PT FROM SiacolRlProtocoloTags PT ");
		sql.append("    WHERE PT.numeroProtocolo = :numeroProtocolo ");
		sql.append("		AND PT.tag.descricao LIKE :descricaoTag");

		try {

			TypedQuery<SiacolRlProtocoloTags> query = em.createQuery(sql.toString(), SiacolRlProtocoloTags.class);
			query.setParameter("numeroProtocolo", siacolRlProtocoloTagsDto.getNumeroProtocolo());
			query.setParameter("descricaoTag", siacolRlProtocoloTagsDto.getTag().getDescricao());

			//siacolRlProtocoloTags = 
			query.getSingleResult();
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolRlProtocoloTagsDao || tagJaFoiCadastrada", StringUtil.convertObjectToJson(siacolRlProtocoloTagsDto), e);
		}
		return true;
	}

	public boolean naoTemUsoDaTag(Long idTag) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PT FROM SiacolRlProtocoloTags PT ");
		sql.append("    WHERE PT.tag.id = :idTag ");
		sql.append("		AND ROWNUM = 1");

		try {

			TypedQuery<SiacolRlProtocoloTags> query = em.createQuery(sql.toString(), SiacolRlProtocoloTags.class);
			query.setParameter("idTag", idTag);

			query.getSingleResult();
			
		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolRlProtocoloTagsDao || naoTemUsoDaTag", StringUtil.convertObjectToJson(idTag), e);
		}
		return false;
	}

}
