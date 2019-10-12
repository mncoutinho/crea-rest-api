package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
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
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol13Dto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol13Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	EventoDao eventoDao;
	@Inject	DepartamentoDao departamentoDao;
	@Inject	DepartamentoConverter departamentoConverter;
	@Inject	AssuntoDao assuntoCorporativoDao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	@Inject	RelatorioSiacolCommonsDao commonsDao;
	
	String[] mesesDoAno = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	
	public RelatorioSiacol13Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public List<RelSiacol13Dto> relatorioDeProtocolosAssuntosSiacolECorporativoPorDepartamento(PesquisaRelatorioSiacolDto pesquisa) {

		List<DepartamentoDto> departamentos = pesquisa.getDepartamentos();
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		List<Long> idsDepartamentos = new ArrayList<Long>();
		
		if (!pesquisa.isTodosDepartamentos()) {
			idsDepartamentos = commonsDao.populaListaIdsDepartamentos(departamentos);
		}
		
		List<Long> idsAssuntos = new ArrayList<Long>();
		if (!pesquisa.isTodosAssuntos()) {
			idsAssuntos = commonsDao.populaListaIdsAssuntos(assuntos);
		}
		
		List<RelSiacol13Dto> listaRelatorio = new ArrayList<RelSiacol13Dto>();

		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT P.NO_PROTOCOLO,                                               	");
		sql.append("		 P.FK_ASSUNTO,                                                 	");
		sql.append("		CA.DESCRICAO,                                                  	");
		sql.append("		SA.CODIGO,                                          		   	");
		sql.append("		SA.NOME,                                                       	");
		sql.append("		DEP.SIGLA	                                            		");
		sql.append("    FROM SIACOL_PROTOCOLOS P               	                            ");
		sql.append("	LEFT JOIN SIACOL_ASSUNTO SA ON (SA.ID = P.FK_ASSUNTO_SIACOL)       	");
		sql.append("	JOIN PRT_ASSUNTOS CA ON (CA.CODIGO = P.FK_ASSUNTO),					");
		sql.append("	PRT_DEPARTAMENTOS DEP                 								");
		sql.append("	WHERE DEP.ID = P.FK_DEPARTAMENTO                                   	");
		
		sql.append(" AND P.NO_PROTOCOLO IN (                                                ");
		sql.append(" SELECT A.NUMERO                                                        ");
		sql.append("   FROM CAD_AUDITORIA A                                                 ");
		sql.append("  WHERE A.MODULO = :modulo                                              ");
		sql.append("    AND A.EVENTO = :tipoEvento                                          ");
		sql.append("   AND (A.ACAO = 'C' OR ACAO = 'U')                                     ");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append(" 	  AND A.ID_DEPARTAMENTO_DESTINO IN (:idsDepartamentos)          ");
		}
		sql.append(" 		  AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                       ");
		if (pesquisa.temMeses()) {
			sql.append(" 	  AND TO_CHAR(A.DT_CREATE, 'MM') = :meses                       ");
		}
		sql.append("       )                                                                ");
		
		if (!pesquisa.isTodosAssuntos()) {
//			sql.append("  AND P.FK_ASSUNTO_SIACOL IN (:idsAssuntos)                         ");
		}
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("tipoEvento", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idsDepartamentos", idsDepartamentos);
			}
//			if (!pesquisa.isTodosAssuntos()) {
//				query.setParameter("idsAssuntos", idsAssuntos);
//			}
			if (pesquisa.temMeses()) {
				query.setParameter("meses", pesquisa.getMeses());
			}			

			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					
					RelSiacol13Dto relatorio = new RelSiacol13Dto();
					
					relatorio.setNumeroProtocolo(result[0] == null ? "" : result[0].toString());
					relatorio.setCodigoAssuntoCorporativo(result[1] == null ? "" : result[1].toString());
					relatorio.setDescricaoAssuntoCorporativo(result[2] == null ? "" : result[2].toString());
					relatorio.setCodigoAssuntoSiacol(result[3] == null ? "" : result[3].toString());
					relatorio.setDescricaoAssuntoSiacol(result[4] == null ? "" : result[4].toString());
					relatorio.setSiglaDepartamentoProtocolo(result[5] == null ? "" : result[5].toString());

					listaRelatorio.add(relatorio);
				}
			}

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog(
					"RelatorioSiacol13Dao || relatorioDeProtocolosAssuntosSiacolECorporativoPorDepartamento",
					StringUtil.convertObjectToJson(pesquisa), e);
		}

		populaDadosCriacaoProtocolo(listaRelatorio);
		
		
		return listaRelatorio;
	}

	private void populaDadosCriacaoProtocolo(List<RelSiacol13Dto> listaRelatorio) {
		for (RelSiacol13Dto relatorio : listaRelatorio) {
			StringBuilder sql = new StringBuilder();
			sql.append("  SELECT D.NOME AS DEPARTAMENTO, F.MATRICULA, PF.NOME                                         ");
			sql.append("    FROM PRT_MOVIMENTOS P                                                                     ");
			sql.append("	JOIN PRT_DEPARTAMENTOS D ON (D.CODIGO = P.FK_ID_ORIGEM_DEPARTAMENTOS)                     ");
			sql.append("	JOIN CAD_FUNCIONARIOS F ON (F.FK_ID_PESSOAS_FISICAS = P.FK_ID_FUNCIONARIOS_REMETENTE)     ");
			sql.append("	JOIN CAD_PESSOAS_FISICAS PF ON (PF.CODIGO = P.FK_ID_FUNCIONARIOS_REMETENTE)               ");
			sql.append("	WHERE P.ID IN                                                                             ");
			sql.append("  (SELECT PRIMEIROMOVIMENTO FROM PRT_PROTOCOLOS WHERE NUMERO = :numeroProtocolo)              ");
			
			try {
				Query query = em.createNativeQuery(sql.toString());
				query.setParameter("numeroProtocolo", relatorio.getNumeroProtocolo());

				Iterator<?> it = query.getResultList().iterator();

				if (query.getResultList().size() > 0) {
					while (it.hasNext()) {
						
						Object[] result = (Object[]) it.next();
						
						relatorio.setDepartamentoCriacao(result[0] == null ? "" : result[0].toString());
						relatorio.setMatriculaCriador(result[1] == null ? "" : result[1].toString());
						relatorio.setNomeCriador(result[2] == null ? "" : result[2].toString());
					}
				}

			} catch (Throwable e) {
				httpGoApi.geraLog("RelatorioSiacol13Dao || populaDadosCriacaoProtocolo", StringUtil.convertObjectToJson(listaRelatorio), e);
			}
		}		
	}

}
