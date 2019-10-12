package br.org.crea.commons.dao.cadastro.empresa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.ObjetoSocial;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ObjetoSocialDao extends GenericDao<ObjetoSocial, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;
	
	public ObjetoSocialDao() {
		super(ObjetoSocial.class);
	}
	
	public ObjetoSocial getUltimoObjetoSocialEmpresaNovaInscritaPor(Long codigoPessoaJuridica) {
		ObjetoSocial objetoSocial = new ObjetoSocial();
		
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT O FROM ObjetoSocial O ");
		sql.append("	WHERE O.empresaNovaInscrita.id = :codigoPessoaJuridica ");
		sql.append("	AND O.id = (SELECT MAX(X.id) FROM ObjetoSocial X ");
		sql.append("					WHERE  X.id = O.id ");
		sql.append("					AND    X.empresaNovaInscrita.id = :codigoPessoaJuridica ");
		sql.append("				) ");
		
		try {
			TypedQuery<ObjetoSocial> query = em.createQuery(sql.toString(), ObjetoSocial.class);
			query.setParameter("codigoPessoaJuridica", codigoPessoaJuridica);
			
			objetoSocial = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("ObjetoSocialDao || getUltimoObjetoSocialEmpresaNovaInscritaPor ", StringUtil.convertObjectToJson(codigoPessoaJuridica), e);
		}
		
		return objetoSocial;
	}

	public List<ObjetoSocial> getObjetosSociaisByEmpresa(Long idEmpresa) {
		List<ObjetoSocial> lista = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT os ");
		sql.append("     FROM ObjetoSocial os ");
		sql.append("    WHERE os.empresa.id = :idEmpresa ");
		sql.append(" ORDER BY os.descricao ");
		
		try {
			TypedQuery<ObjetoSocial> query = em.createQuery(sql.toString(), ObjetoSocial.class);
			query.setParameter("idEmpresa", idEmpresa);
			
			lista = query.getResultList();
		} catch (Throwable e) {
			httpGoApi.geraLog("ObjetoSocialDao || getObjetosSociaisByEmpresa ", StringUtil.convertObjectToJson(idEmpresa), e);
		}
		
		return lista;
	}
	
	public ObjetoSocial getUltimoObjetoSocialEmpresaCadastradaPor(Long codigoEmpresa) {
		ObjetoSocial objetoSocial = new ObjetoSocial();
		
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT O FROM ObjetoSocial O ");
		sql.append("	WHERE O.empresa.id = :codigoEmpresa ");
		sql.append("	AND O.id = (SELECT MAX(X.id) FROM ObjetoSocial X ");
		sql.append("					WHERE  X.id = O.id ");
		sql.append("					AND    X.empresa.id = :codigoEmpresa ");
		sql.append("				) ");
		
		try {
			TypedQuery<ObjetoSocial> query = em.createQuery(sql.toString(), ObjetoSocial.class);
			query.setParameter("codigoEmpresa", codigoEmpresa);
			
			objetoSocial = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ObjetoSocialDao || getUltimoObjetoSocialEmpresaCadastradaPor ", StringUtil.convertObjectToJson(codigoEmpresa), e);
		}
		
		return objetoSocial;
	}
	
}

