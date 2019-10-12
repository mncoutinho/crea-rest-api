package br.org.crea.commons.dao.protocolo;

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
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.commons.Cointeressado;
import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class CoInteressadoDao extends GenericDao<Cointeressado, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;
	
	public CoInteressadoDao() {
		super(Cointeressado.class);
	}
	
	public List<Cointeressado> getListaFiltroCoInteressadoProtocolosPaginado(PesquisaGenericDto pesquisa) {
		
		List<Cointeressado> listProtocolo = new ArrayList<Cointeressado>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM Cointeressado P ");
		sql.append("  WHERE P.pessoa.id = :idPessoa ");
		
		if(pesquisa.temProcesso()) {
			sql.append("	AND P.protocolo.numeroProcesso = :processo");
		}
		if(pesquisa.temNumeroProtocolo()) {
			sql.append("	AND P.protocolo.numeroProtocolo = :numeroProtocolo");
		}
		sql.append("  ORDER BY P.protocolo.dataEmissao DESC ");

		try {
			TypedQuery<Cointeressado> query = em.createQuery(sql.toString(), Cointeressado.class);
			query.setParameter("idPessoa", pesquisa.getIdPessoa());
			
			if(pesquisa.temNumeroProtocolo()) {
				query.setParameter("numeroProtocolo",Long.parseLong(pesquisa.getNumeroProtocolo()));
			}
			if(pesquisa.temProcesso()) {
				query.setParameter("processo",Long.parseLong(pesquisa.getProcesso()));
			}
			
			Page page = new Page(pesquisa.getPage(), pesquisa.getRows());
			page.paginate(query);
			
			listProtocolo = query.getResultList();
		} catch (NoResultException e) {
			return listProtocolo;
		} catch (Throwable e) {
			httpGoApi.geraLog("CoInteressadoDao || getListaFiltroCoInteressadoProtocolosPaginado", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return listProtocolo;
	}
	
	public int getTotalDeProtocolosCoInteressados(PesquisaGenericDto pesquisa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.ID) FROM PRT_COINTERESSADOS P");
		sql.append("  WHERE P.FK_ID_PESSOAS = :idPessoa ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", pesquisa.getIdPessoa());
		
			BigDecimal resultado = (BigDecimal) query.getSingleResult();
			return resultado.intValue();
		

		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloDao || getTotalDeProtocolos", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return 0;
	}
	
	
}
