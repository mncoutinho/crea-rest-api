package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.pessoa.TipoClasseEmpresa;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class TipoClasseEmpresaDao extends GenericDao<TipoClasseEmpresa, Serializable> {
	@Inject HttpClientGoApi httpGoApi;
	
	public TipoClasseEmpresaDao() {
		super(TipoClasseEmpresa.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoClasseEmpresa> buscaTipoClasseEmpresaByRegistro(Long idPessoa) {

		List<TipoClasseEmpresa> list = new ArrayList<TipoClasseEmpresa>();
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT c.id, ");
		sql.append("        c.descricao "); 
		sql.append(" FROM   cad_empresas e ");
		sql.append("        INNER JOIN cad_empresasxclasse ec "); 
		sql.append("                ON e.codigo = ec.fk_empresa ");
		sql.append("        INNER JOIN cad_tipos_classe_empresa c ");
		sql.append("                ON ec.fk_id_classe = c.id ");
		sql.append(" WHERE  e.codigo = :idPessoa ");

		try {
			Query query = em.createNativeQuery(sql.toString(), TipoClasseEmpresa.class);
			query.setParameter("idPessoa", idPessoa);
		
			list = query.getResultList();
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("EmpresaDao || buscaEmpresaByRegistro", StringUtil.convertObjectToJson(idPessoa), e);
		}
		
		return list;

	}
	
}
