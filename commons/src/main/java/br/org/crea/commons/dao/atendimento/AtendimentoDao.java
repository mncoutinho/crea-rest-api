package br.org.crea.commons.dao.atendimento;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.portal.Atendimento;
import br.org.crea.commons.models.portal.dto.AtendimentoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class AtendimentoDao extends GenericDao<Atendimento, Serializable> {

	@Inject HttpClientGoApi httpGoApi;
	
	public AtendimentoDao() {
		super(Atendimento.class);
	}

	public Atendimento getAtendimentoBy(Long codigo) {

		Atendimento atendimento = new Atendimento();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Atendimento A "
				+ "		LEFT JOIN FETCH A.pessoaAtendida "
				+ "	WHERE A.codigo = :codigo ");

		try {
			TypedQuery<Atendimento> query = em.createQuery(sql.toString(), Atendimento.class);
			query.setParameter("codigo", codigo);
			
			atendimento = query.getSingleResult();
		} catch (Throwable e) {
			httpGoApi.geraLog("AtendimentoDao || Get Atendimento By", StringUtil.convertObjectToJson(codigo), e);
		}
		
		return atendimento;
	}

	public void atualizaPesquisa(AtendimentoDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  Atendimento A "
				+ "	 SET A.tempoEspera = :tempoEspera, "
				+ "      A.cordialidade = :cordialidade, "
				+ "		 A.clareza = :clareza, "
				+ "		 A.orientacoes = :orientacoes, "
				+ "		 A.sugestao = :sugestao, "
				+ "		 A.dataResposta = :dataResposta "
				+ "	 WHERE A.codigo = :codigo");

		try {
			Query query = em.createQuery(sql.toString());
			query.setParameter("tempoEspera", dto.getTempoEspera());
			query.setParameter("cordialidade", dto.getCordialidade());
			query.setParameter("clareza", dto.getClareza());
			query.setParameter("orientacoes", dto.getOrientacao());
			query.setParameter("sugestao", dto.getSugestao());
			query.setParameter("codigo", dto.getNumeroChamado());
			query.setParameter("dataResposta", new Date());
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("AtendimentoDao || Atualiza Pesquisa", StringUtil.convertObjectToJson(dto), e);
		}
	}
	
	public boolean atendimentoEstaDisponivelParaPesquisa(Long codigo){
		
		boolean resposta = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM Atendimento A "
				+ " 	WHERE A.codigo = :codigo "
				+ " 	AND A.tempoEspera = 0 ");

		try {
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AtendimentoDao || Atendimento Esta Disponivel Para Pesquisa", StringUtil.convertObjectToJson(codigo), e);
		}
		Query query = em.createQuery(sql.toString(), Atendimento.class);
		query.setParameter("codigo", codigo);
		resposta = !query.getResultList().isEmpty();
		
		return resposta;
	}

}
