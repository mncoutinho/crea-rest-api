package br.org.crea.commons.dao.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.RlArquivo;
import br.org.crea.commons.models.commons.dtos.RlArquivoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RlArquivoDao extends GenericDao<RlArquivo, Serializable>{
	
	@Inject
	HttpClientGoApi httpGoApi;

	public RlArquivoDao() {
		super(RlArquivo.class);
	}
	
//	public List<RlArquivo> getArquivoBy(String nome) {
//		
//		Arquivo arquivo = new Arquivo();
//		StringBuilder sql = new StringBuilder();
//		sql.append("SELECT A FROM Arquivo A ");
//		sql.append("	WHERE A.nomeStorage = :nome ");
//
//		try {
//			TypedQuery<Arquivo> query = em.createQuery(sql.toString(), Arquivo.class);
//			query.setParameter("nome", nome);
//			
//			arquivo =  query.getSingleResult();
//		} catch (Throwable e) {
//			httpGoApi.geraLog("ArquivoDao || getArquivoBy", StringUtil.convertObjectToJson(nome), e);
//		}
//		
//		return arquivo;
//	
//	}

	public List<RlArquivo> getRlByTipoId(RlArquivoDto dto) {
		
		List<RlArquivo> listaRlArquivo = new ArrayList<RlArquivo>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlArquivo RL ");
		sql.append("	WHERE RL.tipoRlArquivo = :tipoRlArquivo ");
		sql.append("	AND RL.idTipoRlArquivo = :idTipoRlArquivo ");
		sql.append("    ORDER BY RL.posicao ");
		

		try {
			TypedQuery<RlArquivo> query = em.createQuery(sql.toString(), RlArquivo.class);
			query.setParameter("tipoRlArquivo", dto.getTipoRlArquivo());
			query.setParameter("idTipoRlArquivo", dto.getIdTipoRlArquivo());
			
			listaRlArquivo =  query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("RlArquivoDao || getRlByTipoId", StringUtil.convertObjectToJson(dto), e);
		}
		
		return listaRlArquivo;
	}
	

}
