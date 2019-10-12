package br.org.crea.corporativo.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.Campus;
import br.org.crea.commons.models.commons.Curso;
import br.org.crea.commons.models.commons.InstituicaoEnsinoAtu;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class InstituicaoEnsinoDao extends GenericDao<InstituicaoEnsinoAtu, Serializable>{
	
	@Inject HttpClientGoApi httpGoApi;

	public InstituicaoEnsinoDao() {
		super(InstituicaoEnsinoAtu.class);
	}


	public List<InstituicaoEnsinoAtu> getAll() {
		
		List<InstituicaoEnsinoAtu> listInstituicaoEnsino = new ArrayList<InstituicaoEnsinoAtu>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("FROM InstituicaoEnsinoAtu i ");
		sql.append("  	ORDER BY i.descricao ");
		
		try {
			TypedQuery<InstituicaoEnsinoAtu> query = em.createQuery(sql.toString(), InstituicaoEnsinoAtu.class);
			
			listInstituicaoEnsino = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("InstituicaoEnsinoDao || Get All", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return listInstituicaoEnsino;
		
	}

	public List<Curso> getAllCurso() {
		
		List<Curso> listCurso = new ArrayList<Curso>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("FROM Curso c ");
		sql.append("	ORDER BY c.descricao ");
		
		try {
			TypedQuery<Curso> query = em.createQuery(sql.toString(), Curso.class);
			
			listCurso =  query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("InstituicaoEnsinoDao || Get All Curso", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return listCurso;
	}
	
	public List<Campus> getInstituicaoListByCursoId(Long idCurso) {

		List<Campus> listCampus = new ArrayList<Campus>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("FROM Campus C ");
		sql.append("	WHERE C.curso.id = :idCurso ");
		sql.append("	ORDER BY C.instituicao.descricao ");
		
		try {
			TypedQuery<Campus> query =  em.createQuery(sql.toString(), Campus.class);
			query.setParameter("idCurso", idCurso);
			
			listCampus =  query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("InstituicaoEnsinoDao || Get Instituicao List By Curso Id", StringUtil.convertObjectToJson(idCurso), e);
		}
		
		return listCampus;

	}
	
	public List<Campus> getCursoListByInstituicaoId(Long idInstituicao) {

		List<Campus> listCampus = new ArrayList<Campus>();
		StringBuilder sql = new StringBuilder();

		sql.append("FROM Campus C ");
		sql.append("	WHERE C.instituicao.id = :idInstituicao ");
		sql.append("	ORDER BY C.curso.descricao ");
 
		try {
			TypedQuery<Campus> query = em.createQuery(sql.toString(), Campus.class);
			query.setParameter("idInstituicao", idInstituicao);
			
			listCampus =  query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("DepartamentoDao || Get Curso List By Instituicao Id", StringUtil.convertObjectToJson(idInstituicao), e);
		}
		
		return listCampus;

	}
	
}
