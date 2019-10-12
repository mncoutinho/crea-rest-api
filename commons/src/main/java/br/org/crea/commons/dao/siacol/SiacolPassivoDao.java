package br.org.crea.commons.dao.siacol;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.siacol.SiacolPassivo;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol06Dto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class SiacolPassivoDao extends GenericDao<SiacolPassivo, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;
	
	public SiacolPassivoDao() {
		super(SiacolPassivo.class);
	}

	public List<SiacolPassivo> getPassivoSemSaidaPorAnoEMes(Long ano, Long mes) {

		List<SiacolPassivo> listaPassivo = new ArrayList<SiacolPassivo>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM SiacolPassivo P  ");
		sql.append(" WHERE P.ano = :ano            ");
		if (mes != null) {
			sql.append("   AND P.mes = :mes            ");
		}		
		sql.append("   AND P.anoSaida IS NOT NULL  ");

		try {
			TypedQuery<SiacolPassivo> query = em.createQuery(sql.toString(), SiacolPassivo.class);
			query.setParameter("ano", ano);
			if (mes != null) {
				query.setParameter("mes", mes);
			}

			listaPassivo = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolPassivoDao || getPassivoSemSaidaPorAnoEMes", StringUtil.convertObjectToJson(ano + " - " + mes), e);
		}

		return listaPassivo;
	}
	
	public boolean existePassivoParaOAno(Long ano) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM SIACOL_PASSIVO P  ");
		sql.append("  WHERE P.ANO = :ano                    ");
		sql.append("    AND P.ANO_SAIDA IS NOT NULL         ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", ano);

			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) > 0;

		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolPassivoDao || existePassivoParaOAno", StringUtil.convertObjectToJson(ano), e);
		}

		return false;
	}

	public void atualizarDataDeSaida(List<RelDetalhadoSiacol06Dto> listaPassivoQueSaiu) {
		
		List<Long> idsProtocolos = listaPassivoQueSaiu.stream().map(RelDetalhadoSiacol06Dto::getNumeroProtocoloLong).collect(Collectors.toList());
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_PASSIVO P                         ");
		sql.append("    SET P.ANO_SAIDA = TO_CHAR(SYSDATE, 'YYYY'),  ");
		sql.append("        P.MES_SAIDA = TO_CHAR(SYSDATE, 'MM')     ");
		sql.append("  WHERE P.ANO_SAIDA IS NULL                      ");
		sql.append("    AND P.FK_PROTOCOLO_SIACOL IN (:idsProtocolo) ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsProtocolos", idsProtocolos);

			query.executeUpdate(); 
			

		} catch (Throwable e) {
			httpGoApi.geraLog("SiacolPassivoDao || atualizarDataDeSaida", StringUtil.convertObjectToJson(listaPassivoQueSaiu), e);
		}

	}

}
