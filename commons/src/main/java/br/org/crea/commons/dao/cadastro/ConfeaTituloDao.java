package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.converter.cadastro.ConfeaTituloConverter;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.ConfeaTitulo;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ConfeaTituloDao extends GenericDao<ConfeaTitulo, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;
	@Inject
	ConfeaTituloConverter converter;

	public ConfeaTituloDao() {
		super(ConfeaTitulo.class);
	}

	public List<ConfeaTitulo> getAllTitulos() {

		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT CT              ");
		sql.append("     FROM ConfeaTitulo CT ");
		sql.append("    WHERE CT.ativo = 1    ");
		sql.append(" ORDER BY CT.descricao    ");

		try {
			TypedQuery<ConfeaTitulo> query = em.createQuery(sql.toString(), ConfeaTitulo.class);

			return query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ConfeaTituloDao || Get Lista Titulo", StringUtil.convertObjectToJson(""), e);
		}
		return null;
	}

	public String getTituloById(Long id) {

		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT descricao FROM CAD_CONFEA_TITULOS  ");
			sql.append("  WHERE codigo = :idTitulo      ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idTitulo", id);
			
			String  titulo = (String) query.getSingleResult();
			
			return titulo; 
			

		} catch (Throwable e) {
			httpGoApi.geraLog("ConfeaTituloDao || Get Titulo By Id", StringUtil.convertObjectToJson(""), e);
		}
		return "";

	}

}
