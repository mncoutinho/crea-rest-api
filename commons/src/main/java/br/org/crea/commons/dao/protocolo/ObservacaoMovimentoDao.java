package br.org.crea.commons.dao.protocolo;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.Movimento;
import br.org.crea.commons.models.commons.ObservacoesMovimento;
import br.org.crea.commons.models.protocolo.ObservacaoProtocolo;
import br.org.crea.commons.models.protocolo.dtos.TramiteDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;


@Stateless
public class ObservacaoMovimentoDao extends GenericDao<ObservacoesMovimento, Serializable> {

	@Inject HttpClientGoApi httpGoApi;
	
	public ObservacaoMovimentoDao() {
		super(ObservacoesMovimento.class);
	}
	
	
	public ObservacoesMovimento gerarObservacaoMovimentoNoTramite(TramiteDto dto, Movimento movimento) {
		ObservacoesMovimento observacaoMovimento = new ObservacoesMovimento();
		
		try {
			
			observacaoMovimento.setData(new Date());
			observacaoMovimento.setIdDepartamento(dto.getUltimoMovimento().getIdDepartamentoDestino());
			observacaoMovimento.setIdFuncionario(dto.getFuncionarioTramite().getId());
			observacaoMovimento.setMovimento(movimento);
			observacaoMovimento.setObservacao(getObservacaoProtocoloPor(dto.getIdObservacaoTramite()));
			create(observacaoMovimento);
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ObservacaoMovimentoDao || gerarObservacaoMovimentoNoTramite", StringUtil.convertObjectToJson(dto), e);
		}
		
		return observacaoMovimento;
	}
	
	public ObservacaoProtocolo getObservacaoProtocoloPor(Long codigoObservacao) {
		ObservacaoProtocolo observacao = new ObservacaoProtocolo();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT O FROM ");
		sql.append("	ObservacaoProtocolo O ");
		sql.append("	WHERE O.id = :codigoObservacao ");
		
		try {
			
			TypedQuery<ObservacaoProtocolo> query = em.createQuery(sql.toString(), ObservacaoProtocolo.class);
			query.setParameter("codigoObservacao", codigoObservacao);
			
			observacao = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ObservacaoMovimentoDao || getObservacaoProtocoloPor", StringUtil.convertObjectToJson(codigoObservacao), e);
		}
		
		return observacao;
	}
}