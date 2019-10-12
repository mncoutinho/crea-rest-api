package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.QuadroTecnico;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class QuadroTecnicoDao extends GenericDao<QuadroTecnico, Serializable> {

	@Inject HttpClientGoApi httpGoApi;
	
	public QuadroTecnicoDao() {
		super(QuadroTecnico.class);
	}

	public List<QuadroTecnico> getQuadroTecnicoPorProfissional(Long idProfissional) {

		List<QuadroTecnico> quadroTecnico = new ArrayList<QuadroTecnico>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT Q FROM QuadroTecnico Q ");
		sql.append("	WHERE Q.profissional.id = :idProfissional ");

		try {
			TypedQuery<QuadroTecnico> query = em.createQuery(sql.toString(), QuadroTecnico.class);
			query.setParameter("idProfissional", idProfissional);
			
			quadroTecnico = query.getResultList();
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("QuadroTecnicoDao || Get Quadro Tecnico", StringUtil.convertObjectToJson(idProfissional), e);
		}
		
		return quadroTecnico;
	}
	
	public boolean fazParteDoQuadroTecnicoNaData(Long idProfissional, Long idEmpresa, Date data) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(QT.CODIGO) FROM CAD_QUADROS_TECNICOS QT    ");
		sql.append("	WHERE QT.FK_CODIGO_PROFISSIONAIS = :idProfissional   ");
		sql.append("	  AND QT.FK_CODIGO_EMPRESAS = :idEmpresa             ");
		sql.append("	  AND TO_CHAR(QT.DATAINICIOQT, 'YYYYMMDD') <= :data  ");
		sql.append("	  AND (QT.DATAFIMQT IS NULL                          ");
		sql.append("	   OR TO_CHAR(QT.DATAFIMQT, 'YYYYMMDD') > :data)     ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			query.setParameter("idEmpresa", idEmpresa);
			query.setParameter("data", DateUtils.format(data, DateUtils.YYYY_MM_DD));
			
			BigDecimal qt = (BigDecimal) query.getSingleResult();
			
			return qt.compareTo(new BigDecimal(0)) > 0;
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("QuadroTecnicoDao || fazParteDoQuadroTecnicoNaData", StringUtil.convertObjectToJson(idProfissional+" - "+idEmpresa+" - "+data), e);
		}
		return false;
	}
	
	public boolean fazParteDoQuadroTecnicoNoPeriodo(Long idProfissional, Long idEmpresa, Date dataInicio, Date dataFim) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(QT.CODIGO) FROM CAD_QUADROS_TECNICOS QT          ");
		sql.append("	WHERE QT.FK_CODIGO_PROFISSIONAIS = :idProfissional         ");
		sql.append("	  AND QT.FK_CODIGO_EMPRESAS = :idEmpresa                   ");
		sql.append("	  AND TO_CHAR(QT.DATAINICIOQT, 'YYYYMMDD') <= :dataInicio  ");
		sql.append("	  AND (QT.DATAFIMQT IS NULL                                ");
		sql.append("	   OR TO_CHAR(QT.DATAFIMQT, 'YYYYMMDD') >= :dataFim)       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProfissional", idProfissional);
			query.setParameter("idEmpresa", idEmpresa);
			query.setParameter("dataInicio", DateUtils.format(dataInicio, DateUtils.YYYY_MM_DD));
			query.setParameter("dataFim", DateUtils.format(dataFim, DateUtils.YYYY_MM_DD));
			
			BigDecimal qt = (BigDecimal) query.getSingleResult();
			
			return qt.compareTo(new BigDecimal(0)) > 0;
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("QuadroTecnicoDao || fazParteDoQuadroTecnicoNaData", StringUtil.convertObjectToJson(idProfissional+" - "+idEmpresa+" - "+dataInicio), e);
		}
		return false;
	}
	
	public boolean atualizaQTQuandoNaoExisteMaisRT(Long codigo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CAD_QUADROS_TECNICOS C  ");
		sql.append("    SET C.RT = 0                ");
		sql.append("  WHERE C.CODIGO = :codigo  ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigo", codigo);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("QuadroTecnicoDao || atualizaQTQuandoNaoExisteMaisRT", StringUtil.convertObjectToJson(codigo), e);
			return false;
		}
		return true;
	}
	
}
