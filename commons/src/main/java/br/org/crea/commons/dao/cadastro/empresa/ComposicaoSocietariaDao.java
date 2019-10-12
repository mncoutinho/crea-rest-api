package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.pessoa.CapitalSocial;
import br.org.crea.commons.models.corporativo.pessoa.ComposicaoSocietaria;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class ComposicaoSocietariaDao extends GenericDao<ComposicaoSocietaria, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public ComposicaoSocietariaDao() {
		super(ComposicaoSocietaria.class);
	}

	public List<ComposicaoSocietaria> getSociosByIdEmpresa(Long idEmpresa) {

		List<ComposicaoSocietaria> list = new ArrayList<ComposicaoSocietaria>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT C FROM ComposicaoSocietaria C ");
		sql.append("  WHERE C.empresa.id = :idEmpresa     ");

		try {
			TypedQuery<ComposicaoSocietaria> query = em.createQuery(sql.toString(), ComposicaoSocietaria.class);
			query.setParameter("idEmpresa", idEmpresa);

			list = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ComposicaoSocietariaDao || getSociosByIdEmpresa", StringUtil.convertObjectToJson(idEmpresa), e);
		}

		return list;

	}

}
