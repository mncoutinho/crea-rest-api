package br.org.crea.commons.dao.cadastro.domains;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class DepartamentoDao extends GenericDao<Departamento, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public DepartamentoDao() {
		super(Departamento.class);
	}

	public List<Departamento> getAllDepartamentos(String moduloSistema) {

		List<Departamento> listDepartamento = new ArrayList<Departamento>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Departamento D WHERE 1 = 1 ");
		if(!moduloSistema.equals("TODOS")) {
			sql.append("	AND D.modulo = :moduloSistema ");
		}
		sql.append("	AND D.removido = 0  ");
		sql.append("	ORDER BY D.nome  ");

		try {
			TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
			if(!moduloSistema.equals("TODOS")) {
				query.setParameter("moduloSistema", moduloSistema);
			}
			listDepartamento = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("DepartamentoDao || getAllDepartamentos", "SEM PARAMETRO", e);
		}
		return listDepartamento;
	}


	public List<Departamento> getDepartamentoBy(Long idDepartamento) {

		List<Departamento> listDepartamento = new ArrayList<Departamento>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Departamento D ");
		sql.append("	WHERE D.removido = 0 ");

		if (idDepartamento != null) {
			sql.append("	and D.id = :idDepartamento ");
		}

		try {
			TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
			query.setParameter("idDepartamento", idDepartamento);

			listDepartamento = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("DepartamentoDao || getDepartamentoBy", "SEM PARAMETRO", e);
		}
		return listDepartamento;
	}
	
	public Departamento buscaDepartamentoPor(Long codigo) {

		Departamento departamento = new Departamento();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  D FROM Departamento D ");
		sql.append("	WHERE D.codigo = :codigo ");

		try {
			TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
			query.setParameter("codigo", codigo);

			departamento = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("DepartamentoDao || buscaDepartamentoPor", StringUtil.convertObjectToJson(codigo), e);
		}
		return departamento;
	}

	public List<Departamento> pesquisaPorNome(GenericDto dto) {
		
			List<Departamento> listDepartamento = new ArrayList<Departamento>();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT D FROM Departamento D ");
			sql.append("	WHERE D.removido = 0  ");
			sql.append("	AND D.nome LIKE :nomeDepartamento ");
			sql.append("	ORDER BY D.nome  ");

			try {
				TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
				
				query.setParameter("nomeDepartamento", "%"+dto.getNome()+"%");
				
				listDepartamento = query.getResultList();
			} catch (Throwable e) {
				httpGoApi.geraLog("DepartamentoDao || pesquisaPorNome", "SEM PARAMETRO", e);
			}
			return listDepartamento;
		}


	
	public Departamento getDepartamentoAtivoPor(Long idDepartamento) {

		Departamento departamento = new Departamento();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Departamento D ");
		sql.append("	WHERE D.removido = 0 ");
		sql.append("	AND D.id = :idDepartamento ");

		try {
			TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
			query.setParameter("idDepartamento", idDepartamento);

			departamento = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("DepartamentoDao || getDepartamentoAtivoPor", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		return departamento;
	}
	
	@SuppressWarnings("unchecked")
	public List<Departamento> getListUnidadesTramitacaoPor(Long idFuncionario) {
		List<Departamento> listUnidadesTramitacao = new ArrayList<Departamento>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D.* FROM Prt_Permissoestramitacao P, ");
		sql.append("	 Prt_Departamentos D ");
		sql.append("	WHERE P.fk_id_funcionarios = :idFuncionario ");
		sql.append("	AND D.id = P.fk_id_departamentos ");
//		sql.append("	AND D.removido = 0 "); FIXME foi removido a pedido de Jeferson
		sql.append("	ORDER BY D.id ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString(), Departamento.class);
			query.setParameter("idFuncionario", idFuncionario);
			listUnidadesTramitacao = query.getResultList();
			
		} catch (NoResultException e) {
			return listUnidadesTramitacao;
		} catch (Throwable e) {
			httpGoApi.geraLog("DepartamentoDao || getListUnidadesTramitacaoPor", StringUtil.convertObjectToJson(idFuncionario), e);
		}
		
		return listUnidadesTramitacao;
	}
	
	public boolean departamentoTramitaParaJulgamentoRevelia(Long idDepartamento) {
		Departamento departamento = new Departamento();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Departamento D ");
		sql.append("	WHERE D.id = :idDepartamento ");

		try {
			TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
			query.setParameter("idDepartamento", idDepartamento);

			departamento = query.getSingleResult();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("DepartamentoDao || getDepartamentoAtivoPor", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		
		return departamento.getEnviaParaJulgamentoRevelia() ? true : false;
	}
	
	public boolean departamentoExecutaJulgamentoRevelia(Long idDepartamento) {
		Departamento departamento = new Departamento();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT D FROM Departamento D ");
		sql.append("	WHERE D.id = :idDepartamento ");

		try {
			TypedQuery<Departamento> query = em.createQuery(sql.toString(), Departamento.class);
			query.setParameter("idDepartamento", idDepartamento);
			
			departamento = query.getSingleResult();

		} catch (Throwable e) {
			httpGoApi.geraLog("DepartamentoDao || getDepartamentoAtivoPor", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		
		return departamento.getExecutaJulgamentoRevelia() ? true : false;
	}

	public List<Departamento> getDepartamentosProtocoloSiacol() {
		
		List<BigDecimal> listIdUnidades = new ArrayList<BigDecimal>();
		List<Departamento> listUnidades = new ArrayList<Departamento>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT SP.FK_DEPARTAMENTO FROM SIACOL_PROTOCOLOS SP ");
		
		try {
			
			Query query = em.createNativeQuery(sql.toString());
			
			listIdUnidades = query.getResultList();
			
			for (BigDecimal id : listIdUnidades) {
				listUnidades.add(getBy(id.longValue()));
			}
			
		} catch (NoResultException e) {
			return listUnidades;
		} catch (Throwable e) {
			httpGoApi.geraLog("DepartamentoDao || getDepartamentosProtocoloSiacol", "SEM PARAMETRO", e);
		}
		
		return listUnidades;
		
	}
	


}
