package br.org.crea.commons.dao.protocolo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.protocolo.RlAutorizacaoProtocolo;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RlAutorizacaoProtocoloDao extends GenericDao<RlAutorizacaoProtocolo, Serializable>{

	
	@Inject
	HttpClientGoApi httpGoApi;

	public RlAutorizacaoProtocoloDao() {
		super(RlAutorizacaoProtocolo.class);
	}
	
	public List<RlAutorizacaoProtocolo> getAutorizacoesPor(Long idFuncionario) {
		List<RlAutorizacaoProtocolo> listResult = new ArrayList<RlAutorizacaoProtocolo>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlAutorizacaoProtocolo RL ");
		sql.append("	WHERE RL.funcionario.id = :idFuncionario ");
		
		try {
			
			TypedQuery<RlAutorizacaoProtocolo> query = em.createQuery(sql.toString(), RlAutorizacaoProtocolo.class);
			query.setParameter("idFuncionario", idFuncionario);
			listResult = query.getResultList();
			
		} catch (NoResultException e) {
			return listResult;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlAutorizacaoProtocoloDao || getAutorizacoesPor ", StringUtil.convertObjectToJson(idFuncionario), e);
		}
		return listResult;
	}
	
	/**
	 * Considera-se funcionário habilitado a substituir protocolo se 
	 * possuir permissão de Admnistrador (código 9000) OU permissão própria de
	 * substituição do protocolo (código 5)
	 * */
	public boolean podeSubstituirProtocolo(Long idFuncionario) {

		try {
			   List<RlAutorizacaoProtocolo> autorizacoes = getAutorizacoesPor(idFuncionario);
			   return autorizacoes.stream().anyMatch(rl -> rl.getPermissaoProtocolo().getId().equals(new Long(9000))) || 
					  autorizacoes.stream().anyMatch(rl -> rl.getPermissaoProtocolo().getId().equals(new Long(5)));
			
		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlAutorizacaoProtocoloDao || podeSubstituirProtocolo ", StringUtil.convertObjectToJson(idFuncionario), e);
		}
		return false;
	}
}
