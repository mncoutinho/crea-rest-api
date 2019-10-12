package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.RamoAtividade;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class RamoAtividadeDao extends GenericDao<RamoAtividade, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public RamoAtividadeDao() {
		super(RamoAtividade.class);
	}

	public List<RamoAtividade> getRamosAtividadesByEmpresa(Long idEmpresa) {

		List<RamoAtividade> list = new ArrayList<RamoAtividade>();
		StringBuilder sql = new StringBuilder();

		sql.append("   SELECT ra ");
		sql.append("     FROM RamoAtividade ra ");
		sql.append("    WHERE ra.empresa.id = :idEmpresa ");
		sql.append(" ORDER BY ra.dataInclusao DESC ");

		try {
			TypedQuery<RamoAtividade> query = em.createQuery(sql.toString(), RamoAtividade.class);
			query.setParameter("idEmpresa", idEmpresa);

			list = query.getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || getRamosAtividadesByEmpresa", StringUtil.convertObjectToJson(idEmpresa), e);
		}

		return list;

	}
	
}
