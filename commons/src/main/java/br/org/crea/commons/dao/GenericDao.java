package br.org.crea.commons.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.org.crea.commons.util.StringUtil;

public abstract class GenericDao<T, I extends Serializable> {

	@PersistenceContext(unitName = "dscrea")
	protected EntityManager em;

	private Class<T> classe;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * Métodos para usar nos tests
	 */
	public void createTransaction() {
		this.em.getTransaction().begin();
	}

	public void commitTransaction() {
		this.em.getTransaction().commit();
	}

	public void roolbackTransaction() {
		this.em.getTransaction().rollback();
	}

	public void destroyEntityManager() {
		this.em.close();
	}

	/**
	 * Fim métodos de testes
	 */

	public GenericDao(Class<T> classe) {
		this.classe = classe;
	}

	public T create(T entity) {
		em.persist(entity);
		em.flush();
		return entity;
	}
	
	public T createWithOutFlush(T entity) {
		em.persist(entity);
		return entity;
	}

	public T update(T entity) {
		try {
		em.merge(entity);
		em.flush();
		return entity;
	
	} catch (Exception e) {
		
      return entity;
	}
	
	}

	public T getBy(Long id) {
		return em.find(classe, id);
	}
	
	public T getByIdString(String id) {
		return em.find(classe, id);
	}

	public T getByStringId(String id) {
		return em.find(classe, id);
	}

	public T getBy(Object id) {
		return em.find(classe, id);
	}

	/**
	 * Finds an entity by one of its properties.
	 * 
	 * 
	 * @param clazz
	 *            entidade.
	 * @param propertyName
	 *            atributo da entidade.
	 * @param value
	 *            o valor do que deseja achar.
	 * @return
	 */
	public List<T> findByProperty(Class<T> clazz, String propertyName, Object value) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clazz);
		Root<T> root = cq.from(clazz);
		cq.where(cb.equal(root.get(propertyName), value));
		return em.createQuery(cq).getResultList();
	}

	public T deleta(Long id) {
		T entity = em.find(classe, id);		
		em.remove(entity);
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Query query = em.createQuery("FROM " + classe.getName());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(int pagina, int limitePagina, String pPath, String pJoin, String pCondicao, String pOrdenacao, String pTipoOrdenacao) {

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("FROM").append(" ").append(classe.getName()).append(" ").append(pPath);

		if (StringUtil.isValidAndNotEmpty(pJoin)) {
			strBuilder.append(" ").append(pJoin);
		}

		if (StringUtil.isValidAndNotEmpty(pCondicao)) {
			strBuilder.append(" ").append("WHERE").append(" ").append(pCondicao);
		}

		if (StringUtil.isValidAndNotEmpty(pOrdenacao)) {
			strBuilder.append(" ").append("ORDER BY").append(" ").append(pOrdenacao).append(" ").append(pTipoOrdenacao);
		}

		Query query = em.createQuery(strBuilder.toString());
		query.setFirstResult((pagina - 1) * limitePagina);
		query.setMaxResults(limitePagina);

		return query.getResultList();
	}

	public int getRecordCount(String pPath, String pJoin, String pCondicao) {

		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("FROM").append(" ").append(classe.getName()).append(" ").append(pPath);

		if (StringUtil.isValidAndNotEmpty(pJoin)) {
			strBuilder.append(" ").append(pJoin);
		}

		if (StringUtil.isValidAndNotEmpty(pCondicao)) {
			strBuilder.append(" ").append("WHERE").append(" ").append(pCondicao);
		}

		Query query = em.createQuery(strBuilder.toString());

		return query.getResultList().size();
	}

	public Boolean isLoaded(T entity, String nomeColecao) {
		PersistenceUnitUtil unitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
		return unitUtil.isLoaded(entity, nomeColecao);
	}

}
