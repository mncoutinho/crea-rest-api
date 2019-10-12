package br.org.crea.commons.dao.siacol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class AssuntoSiacolDao extends GenericDao<AssuntoSiacol, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public AssuntoSiacolDao() {
		super(AssuntoSiacol.class);
	}

	public AssuntoSiacol verificaCodigo(Long codigo) {

		AssuntoSiacol assunto = new AssuntoSiacol();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AssuntoSiacol A ");
		sql.append("     WHERE A.codigo = :codigo ");
		sql.append("     AND A.ativo = 1 ");

		try {

			TypedQuery<AssuntoSiacol> query = em.createQuery(sql.toString(), AssuntoSiacol.class);
			query.setParameter("codigo", codigo);

			assunto = query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoSiacolDao || verificaCodigo", StringUtil.convertObjectToJson(codigo), e);
		}
		return assunto;
	}

	public List<AssuntoSiacol> getAllAssuntos() {
		List<AssuntoSiacol> listaAssunto = new ArrayList<AssuntoSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AssuntoSiacol A ");
		sql.append("     WHERE A.ativo = 1 ");

		try {

			TypedQuery<AssuntoSiacol> query = em.createQuery(sql.toString(), AssuntoSiacol.class);

			listaAssunto = query.getResultList();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoSiacolDao || getAllAssuntos", "SEM PARAMETRO", e);
		}
		return listaAssunto;
	}

	public GenericSiacolDto atribuicaoConfea(GenericSiacolDto dto) {

		try {				

			StringBuilder sb = new StringBuilder();
			sb.append(" UPDATE SIACOL_ASSUNTO A ");
			sb.append(" 	SET A.FK_ASSUNTO_CONFEA = :id ");
			sb.append(" 	WHERE A.ID IN (:idAssuntoSiacol) ");

			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("id", new Long (dto.getCodigo()));
			query.setParameter("idAssuntoSiacol", dto.getListaId());
			query.executeUpdate();
			return dto;
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoSiacolDao || atualizaConfea", "SEM PARAMETRO", e);
		}
		return null;
	}


}
