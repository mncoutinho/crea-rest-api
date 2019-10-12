package br.org.crea.commons.dao.fiscalizacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.fiscalizacao.RecursoAutuacao;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RecursoAutuacaoDao extends GenericDao<RecursoAutuacao, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;
	
	public RecursoAutuacaoDao() {
		super(RecursoAutuacao.class);
	}
	
	public List<RecursoAutuacao> getListRecursosPor(Long numeroProtocolo) {
		List<RecursoAutuacao> listRecursos = new ArrayList<RecursoAutuacao>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT R FROM RecursoAutuacao R ");
		sql.append("	WHERE R.protocolo.numeroProcesso = :numeroProtocolo ");
		sql.append(" 	ORDER BY R.dataApresentacao, R.idInstancia ");

		try {

			TypedQuery<RecursoAutuacao> query = em.createQuery(sql.toString(), RecursoAutuacao.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);

			listRecursos = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("RecursoAutuacaoDao || buscaRecursoPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return listRecursos;
	}
	
	public RecursoAutuacao cadastraRecursoParaJulgamentoReveliaPor(Long numeroProtocolo, Long idFuncionario) {
		RecursoAutuacao recurso = new RecursoAutuacao();
		
		try {
			
			recurso.setDataApresentacao(new Date());
			recurso.setDataCadastro(new Date());
			recurso.setIdSituacaoRecurso(new Long(8));
			recurso.setIdInstancia(new Long(2));
			recurso.setNumeroProtocolo(numeroProtocolo);
			recurso.setObservacao("Para Julgamento a Revelia");
			recurso.setIdFuncionarioCadastro(idFuncionario);
			create(recurso);
			
		} catch (Throwable e) {
			httpGoApi.geraLog("RecursoAutuacaoDao || cadastraRecursoParaJulgamentoReveliaPor", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return recurso;
	}

}
