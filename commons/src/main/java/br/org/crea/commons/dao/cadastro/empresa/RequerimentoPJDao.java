package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.RequerimentoPJ;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RequerimentoPJDao  extends GenericDao<RequerimentoPJ, Serializable> {

	public RequerimentoPJDao() {
		super(RequerimentoPJ.class);
	}
	
	@Inject HttpClientGoApi httpGoApi;
	
	public RequerimentoPJ buscaRequerimentoEmpresa(PesquisaGenericDto dto) {
		RequerimentoPJ requerimento = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM RequerimentoPJ R ");
		sql.append(" 	WHERE R.protocolo.numeroProtocolo = :numeroProtocolo ");
		sql.append("	AND   R.profissionalResponsavel.id = :idProfissional ");
		sql.append("    AND   R.id = (SELECT MAX(X.id) FROM RequerimentoPessoaJuridica X ");
		sql.append("	    				 WHERE R.protocolo.numeroProtocolo = X.protocolo.numeroProtocolo ");
		sql.append("						 AND X.profissionalResponsavel.id = :idProfissional ");
		sql.append("	    		  )");

		try {

			TypedQuery<RequerimentoPJ> query = em.createQuery(sql.toString(), RequerimentoPJ.class);
			query.setParameter("numeroProtocolo", dto.getProtocolo());
			query.setParameter("idProfissional", dto.getRegistroProfissional());

			requerimento = query.getSingleResult();	

		} catch (NoResultException e) {
			return requerimento;
		} catch (Throwable e) {
			httpGoApi.geraLog("RequerimentoPessoaJuridicaDao || buscaRequerimentoEmpresa", StringUtil.convertObjectToJson(dto), e);
		}
		return requerimento;
	}
	
	public List<RequerimentoPJ> buscaRequerimentosPor(Long numeroProtocolo) {
		List<RequerimentoPJ> listRequerimentos = new ArrayList<RequerimentoPJ>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM RequerimentoPJ R  ");
		sql.append(" 	WHERE R.protocolo.numeroProtocolo = :numeroProtocolo ");

		try {

			TypedQuery<RequerimentoPJ> query = em.createQuery(sql.toString(), RequerimentoPJ.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);

			listRequerimentos = query.getResultList();	

		} catch (NoResultException e) {
			return listRequerimentos;
		} catch (Throwable e) {
			httpGoApi.geraLog("RequerimentoPessoaJuridicaDao || buscaRtsRequerimentoEmpresaPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return listRequerimentos;
	}
	
	public RequerimentoPJ getRequerimentoPor(Long numeroProtocolo) {
		RequerimentoPJ requerimento = new RequerimentoPJ();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM RequerimentoPJ R  ");
		sql.append(" 	WHERE R.protocolo.numeroProtocolo = :numeroProtocolo ");

		try {

			TypedQuery<RequerimentoPJ> query = em.createQuery(sql.toString(), RequerimentoPJ.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setMaxResults(1);

			requerimento = query.getSingleResult();	

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RequerimentoPessoaJuridicaDao || getRequerimentoPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return requerimento;
	}
	
}
