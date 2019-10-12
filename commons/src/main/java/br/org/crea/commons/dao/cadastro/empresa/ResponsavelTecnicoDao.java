package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.cadastro.ResponsavelTecnico;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ResponsavelTecnicoDao extends GenericDao<ResponsavelTecnico, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public ResponsavelTecnicoDao() {
		super(ResponsavelTecnico.class);
	}

	public List<ResponsavelTecnico> getResponsabilidadeTecnica(PesquisaGenericDto pesquisa) {

		List<ResponsavelTecnico> responsavelTecnico = new ArrayList<ResponsavelTecnico>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT R FROM ResponsavelTecnico R ");

		if (pesquisa.getTipoPessoa().equals("EMPRESA")) {
			sql.append("	WHERE R.quadro.empresa.id = :idEmpresa ");
		} else {
			sql.append("	WHERE R.quadro.profissional.id = :idProfissional ");
		}

		try {
			TypedQuery<ResponsavelTecnico> query = em.createQuery(sql.toString(), ResponsavelTecnico.class);
			if (pesquisa.getTipoPessoa().equals("EMPRESA")) {
				query.setParameter("idEmpresa", pesquisa.getIdPessoa());
			} else {
				query.setParameter("idProfissional", pesquisa.getIdPessoa());
			}
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);

			responsavelTecnico = query.getResultList().isEmpty() ? null : query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ResponsavelTecnicoDao || getResponsavelTecnicoProfissional", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return responsavelTecnico;
	}

	public List<ResponsavelTecnico> getResponsaveisTecnicosByEmpresa(Long idEmpresa) {

		List<ResponsavelTecnico> responsavelTecnico = new ArrayList<ResponsavelTecnico>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT r FROM ResponsavelTecnico r ");
		sql.append("  WHERE r.quadro.empresa.id = :idEmpresa ");
		sql.append("    AND r.dataFim is null ");

		try {
			TypedQuery<ResponsavelTecnico> query = em.createQuery(sql.toString(), ResponsavelTecnico.class);
			query.setParameter("idEmpresa", idEmpresa);
			responsavelTecnico = query.getResultList().isEmpty() ? null : query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ResponsavelTecnicoDao || getResponsaveisTecnicosByEmpresa", StringUtil.convertObjectToJson(idEmpresa), e);
		}

		return responsavelTecnico;
	}

	public List<ResponsavelTecnico> getResponsavelTecnicoAtivoByQT(Long idQuadroTecnico) {
		
		List<ResponsavelTecnico> lista = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM CAD_RESPONSAVEIS_TECNICOS RT         				");
		sql.append("	WHERE RT.DATAFIMRT IS NULL        				  				");
		sql.append("	AND RT.FK_CODIGO_QUADROS_TECNICOS = :idQuadroTecnico            ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idQuadroTecnico", idQuadroTecnico);
			
			lista = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("QuadroTecnicoDao || getResponsavelTecnicoAtivoByQT", StringUtil.convertObjectToJson(idQuadroTecnico), e);
		}

		return lista;
	}
	
	public ResponsavelTecnico getResponsavelTecnicoByQTeRamo(Long idQuadroTecnico, Long idRamo) {
		
		ResponsavelTecnico rt = new ResponsavelTecnico();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT RT FROM ResponsavelTecnico RT         			  ");
		sql.append("	WHERE RT.ramoAtividade.id = :idRamo		  			  ");
		sql.append("	AND RT.quadro.id = :idQuadroTecnico            ");
		sql.append("	AND RT.dataFim IS NULL        				  		  ");
		
		try {
			TypedQuery<ResponsavelTecnico> query = em.createQuery(sql.toString(), ResponsavelTecnico.class);
			query.setParameter("idQuadroTecnico", idQuadroTecnico);
			query.setParameter("idRamo", idRamo);
			
			rt = query.getSingleResult();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("QuadroTecnicoDao || getResponsavelTecicoByQTeRamo", StringUtil.convertObjectToJson(idQuadroTecnico + idRamo), e);
		}

		return rt;
	}

	public List<ResponsavelTecnico> getResponsavelTecnicoByQT(Long idQuadroTecnico) {
		
		List<ResponsavelTecnico> lista = new ArrayList<ResponsavelTecnico>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT RT FROM ResponsavelTecnico RT   ");
		sql.append("  WHERE RT.quadro.id = :idQuadroTecnico ");
		sql.append("	AND RT.dataFim IS NULL        		");
		
		try {
			TypedQuery<ResponsavelTecnico> query = em.createQuery(sql.toString(), ResponsavelTecnico.class);
			query.setParameter("idQuadroTecnico", idQuadroTecnico);
			
			lista = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ResponsavelTecnicoDao || getResponsavelTecnicoByQT", StringUtil.convertObjectToJson(idQuadroTecnico), e);
		}

		return lista;
	}

	public boolean ehResponsavelTecnico(Long idQuadroTecnico) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(RT.CODIGO) FROM CAD_RESPONSAVEIS_TECNICOS RT   ");
		sql.append("  WHERE RT.FK_CODIGO_QUADROS_TECNICOS = :idQuadroTecnico     ");
		sql.append("	AND RT.DATAFIMRT IS NULL        	                     ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idQuadroTecnico", idQuadroTecnico);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) > 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ResponsavelTecnicoDao || ehResponsavelTecnico", StringUtil.convertObjectToJson(idQuadroTecnico), e);
		}

		return false;
	}
}