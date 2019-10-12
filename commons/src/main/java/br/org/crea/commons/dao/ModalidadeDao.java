package br.org.crea.commons.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.models.cadastro.Modalidade;
import br.org.crea.commons.models.cadastro.RlModalidadeDepartamento;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ModalidadeDao extends GenericDao<Modalidade, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public ModalidadeDao() {
		super(Modalidade.class);
	}

	public Modalidade getByIdDepartamento(Long idDepartamento) {

		// RlModalidadeDepartamento rlModalidadeDepartamento = new
		// RlModalidadeDepartamento();
		Modalidade modalidade = new Modalidade();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M.modalidade FROM RlModalidadeDepartamento M ");
		sql.append("     WHERE M.departamento.id = :idDepartamento ");

		try {
			TypedQuery<Modalidade> query = em.createQuery(sql.toString(), Modalidade.class);
			query.setParameter("idDepartamento", idDepartamento);

			// rlModalidadeDepartamento = query.getSingleResult();
			// modalidade = rlModalidadeDepartamento.getModalidade();
			modalidade = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ModalidadeDao || getByIdDepartamento", StringUtil.convertObjectToJson(idDepartamento),
					e);
		}
		return modalidade;
	}

	public Modalidade getByCodigo(Long codigo) {

		Modalidade modalidade = new Modalidade();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT M FROM Modalidade M ");
		sql.append("     WHERE M.codigo = :codigo ");

		try {
			TypedQuery<Modalidade> query = em.createQuery(sql.toString(), Modalidade.class);
			query.setParameter("codigo", codigo);

			modalidade = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ModalidadeDao || getByCodigo", StringUtil.convertObjectToJson(codigo), e);
		}
		return modalidade;

	}

	public Long getIdRlModalidadeDepartamentoByIdDepartamento(Long idDepartamento) {

		RlModalidadeDepartamento rlModalidadeDepartamento = new RlModalidadeDepartamento();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RMD FROM RlModalidadeDepartamento RMD ");
		sql.append("     WHERE RMD.departamento.id = :idDepartamento ");

		try {
			TypedQuery<RlModalidadeDepartamento> query = em.createQuery(sql.toString(), RlModalidadeDepartamento.class);
			query.setParameter("idDepartamento", idDepartamento);

			// rlModalidadeDepartamento = query.getSingleResult();
			// modalidade = rlModalidadeDepartamento.getModalidade();
			rlModalidadeDepartamento = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ModalidadeDao || getIdRlModalidadeDepartamentoByIdDepartamento",
					StringUtil.convertObjectToJson(idDepartamento), e);
		}
		return rlModalidadeDepartamento.getId();
	}

}
