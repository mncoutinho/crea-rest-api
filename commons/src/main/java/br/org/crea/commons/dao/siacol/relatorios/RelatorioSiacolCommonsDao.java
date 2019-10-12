package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.cadastro.EventoDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.AssuntoConfea;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacolCommonsDao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	EventoDao eventoDao;
	@Inject	DepartamentoDao departamentoDao;
	@Inject	DepartamentoConverter departamentoConverter;
	@Inject	AssuntoDao assuntoCorporativoDao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	
	String[] mesesDoAno = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	
	public RelatorioSiacolCommonsDao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public List<Long> populaListaIdsAssuntos(List<AssuntoDto> assuntos) {
		List<Long> idsAssuntos = new ArrayList<Long>();
		
		for (AssuntoDto assunto : assuntos) {
			idsAssuntos.add(assunto.getId());
		}
		return idsAssuntos;
	}
	
	public int qtdNaoClassificado(PesquisaRelatorioSiacolDto pesquisa) {
		List<String> statusRevisao = new ArrayList<String>();
		statusRevisao.add("REVISAO_VISTAS_VOTADO");
		statusRevisao.add("REVISAO_DECISAO_PRESENCIAL");
		statusRevisao.add("REVISAO_ITENS_PROTOCOLADO");
		statusRevisao.add("REVISAO_DECISAO_VIRTUAL");
		statusRevisao.add("REVISAO_DECISAO_VISTAS");
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT COUNT(P.NO_PROTOCOLO)                                             ");
		sql.append("    FROM SIACOL_PROTOCOLOS P                                               ");
		sql.append("   WHERE (P.CLASSIFICACAO IS NULL OR P.CLASSIFICACAO = 'NAO_CLASSIFICADO' OR P.CLASSIFICACAO_FINAL IS NULL)        ");
		sql.append("     AND P.STATUS IN (:statusRevisao)                                          ");
		
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append("     AND P.FK_DEPARTAMENTO IN (:idsDepartamentos)                               ");
		}
		
		
		sql.append("    AND P.ID IN(                                                                ");
		sql.append("	 SELECT RL.FK_PROTOCOLO FROM SIACOL_DOCUMENTO_PROTOCOLO RL                  ");
		sql.append("	  WHERE RL.FK_DOCUMENTO IN (SELECT R.FK_DOCUMENTO FROM SIACOL_REUNIAO R     ");
		sql.append("	  WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                    ");
		if (pesquisa.temMeses()) {
			sql.append("	AND TO_CHAR(R.HR_FIM, 'MM') = :meses                                    ");
		}
		sql.append("	    )                                   ");
		sql.append("	     OR RL.FK_DOCUMENTO IN (SELECT R.FK_EXTRA_PAUTA FROM SIACOL_REUNIAO R   ");
		sql.append("      WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                    ");
		if (pesquisa.temMeses()) {
			sql.append("	AND TO_CHAR(R.HR_FIM, 'MM') = :meses                                    ");
		}
		sql.append("	    ))                                                                      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsDepartamentos", pesquisa.getIdsDepartamentos());
			query.setParameter("statusRevisao", statusRevisao);
			query.setParameter("ano", pesquisa.getAno());
			if (pesquisa.temMeses()) {
				query.setParameter("meses", pesquisa.getMeses());
			}
			
			return Integer.parseInt(query.getSingleResult().toString());
			
		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacolCommonsDao || qtdNaoClassificado", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return 0;
	}
	
	public List<Long> populaListaIdsDepartamentos(List<DepartamentoDto> departamentos) {
		List<Long> listaIds = new ArrayList<Long>();
		
		for (DepartamentoDto departamento : departamentos) {
			listaIds.add(departamento.getId());
		}
		return listaIds;
	}
	
	public AssuntoConfea populaAssuntoConfeaPeloAssuntoSiacol(Long codigoAssunto) {

		AssuntoConfea assuntoConfea = new AssuntoConfea();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.CODIGO, A.NOME FROM SIACOL_ASSUNTO_CONFEA A     ");
		sql.append("  WHERE A.ID = (SELECT DISTINCT S.FK_ASSUNTO_CONFEA       ");
		sql.append("   FROM SIACOL_ASSUNTO S WHERE S.CODIGO = :codigoAssunto  ");
		sql.append("    AND S.FK_ASSUNTO_CONFEA IS NOT NULL)                  ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("codigoAssunto", codigoAssunto);
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					BigDecimal valor = (BigDecimal) result[0];
					assuntoConfea.setCodigo(valor.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					assuntoConfea.setNome(result[1] != null ? result[1].toString() : null);
					
				}
			}
			
		} catch (NoResultException e) {
			return assuntoConfea;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacolCommonsDao || populaAssuntoConfeaPeloAssuntoSiacol", StringUtil.convertObjectToJson(codigoAssunto), e);
		}
		return assuntoConfea;
	}
}
