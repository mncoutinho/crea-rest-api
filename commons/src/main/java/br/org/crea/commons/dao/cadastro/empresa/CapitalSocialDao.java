package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.pessoa.CapitalSocial;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class CapitalSocialDao extends GenericDao<CapitalSocial, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public CapitalSocialDao() {
		super(CapitalSocial.class);
	}

	public List<CapitalSocial> getCapitaisSociaisByEmpresa(Long idEmpresa) {

		List<CapitalSocial> list = new ArrayList<CapitalSocial>();
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT cs ");
		sql.append("   FROM CapitalSocial cs ");
		sql.append("  WHERE cs.empresa.id = :idEmpresa ");
		sql.append("    AND cs.ativo = true ");

		try {
			TypedQuery<CapitalSocial> query = em.createQuery(sql.toString(), CapitalSocial.class);
			query.setParameter("idEmpresa", idEmpresa);

			list = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || getCapitaisSociaisByEmpresa", StringUtil.convertObjectToJson(idEmpresa), e);
		}

		return list;

	}

}
