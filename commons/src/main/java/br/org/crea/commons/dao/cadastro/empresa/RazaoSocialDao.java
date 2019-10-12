package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.RazaoSocial;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RazaoSocialDao extends GenericDao<RazaoSocial, Serializable> {

	@Inject HttpClientGoApi httpGoApi;
	
	public RazaoSocialDao() {
		super(RazaoSocial.class);
	}

	public RazaoSocial getRazaoSocialBy(Long idPessoaJuridica) {
		RazaoSocial razaoSocial = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM RazaoSocial R ");
		sql.append("	WHERE R.pessoaJuridica.id = :idPessoaJuridica ");
		sql.append("	AND R.ativo = true ");

		try {
			TypedQuery<RazaoSocial> query = em.createQuery(sql.toString(), RazaoSocial.class);
			query.setParameter("idPessoaJuridica", idPessoaJuridica);
			
			razaoSocial = query.getSingleResult();
			
		}catch (NoResultException e) {
			return null;			
		} catch (Throwable e) {
			httpGoApi.geraLog("RazaoSocialDao || Get RazaoSocial Por", StringUtil.convertObjectToJson(idPessoaJuridica), e);
		}
		
		return razaoSocial;
	}

	public String buscaDescricaoRazaoSocial(Long idPessoaJuridica) {

		String razaoSocial = "";

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R.DESCRICAO FROM CAD_RAZOES_SOCIAIS R ");
		sql.append("WHERE R.FK_CODIGO_PJS= :idPessoaJuridica ");
		sql.append("AND R.ATIVO = 1 ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoaJuridica", idPessoaJuridica);

			razaoSocial = (String) query.getSingleResult();

		} catch (Throwable e) {
			httpGoApi.geraLog("RazaoSocialDao || Busca Descricao RazaoSocial", StringUtil.convertObjectToJson(idPessoaJuridica), e);
		}

		return razaoSocial;

	}


}
