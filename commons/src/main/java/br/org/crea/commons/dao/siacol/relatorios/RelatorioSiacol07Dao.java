package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.enuns.TipoEventoAuditoria;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07RecebimentoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol07Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	
	public RelatorioSiacol07Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public List<ProtocolosRel07RecebimentoDto> getDistribuicaoPorOutroUsuario(PesquisaRelatorioSiacolDto pesquisa, Long idAssunto) {
	
		List<ProtocolosRel07RecebimentoDto> lista = new ArrayList<ProtocolosRel07RecebimentoDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT U.ID, U.NUMERO, U.DT_CREATE                             "); 
		sql.append("    FROM CAD_AUDITORIA U                                         ");
	    sql.append("   WHERE U.EVENTO = :eventoDistribuicao                          ");
	    sql.append("     AND U.MODULO = :moduloSiacol                                ");
	    sql.append("     AND U.ID_PESSOA_ACAO <> U.ID_USUARIO                        ");
	    sql.append("     AND U.ID_PESSOA_ACAO = :idAnalistaOuConselheiro             ");
		sql.append("     AND TO_CHAR(U.DT_CREATE, 'YYYY') = :ano                     ");
		sql.append("     AND TO_CHAR(U.DT_CREATE, 'MM') IN (:meses)                  ");
	    sql.append("     AND U.NUMERO IN                                             ");
		sql.append("    (SELECT P.NO_PROTOCOLO FROM SIACOL_PROTOCOLOS P              "); 
		sql.append("      WHERE P.FK_ASSUNTO_SIACOL = :idAssunto)                    ");
		// FIXME IF ASSUNTO_SIACOL != NULL
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idAssunto", idAssunto);
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("meses", pesquisa.getMeses());
			
			lista = populaListaRel07RecebimentoDto(query, lista);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getDistribuicaoPorOutroUsuario", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;
	}
	
	public ProtocolosRel07RecebimentoDto getDistribuicaoConselheiroOuImpedimento(PesquisaRelatorioSiacolDto pesquisa, Long idAssunto, String idRecebimento) {
		
		ProtocolosRel07RecebimentoDto saida = new ProtocolosRel07RecebimentoDto();
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT U.ID, U.NUMERO, U.DT_CREATE                                                         "); 
		sql.append("    FROM CAD_AUDITORIA U                                                                     ");
	    sql.append("   WHERE U.EVENTO IN (:eventoDistribuicao, :eventoConselheiroImpedido)                       ");
	    sql.append("     AND U.MODULO = :moduloSiacol                                                            ");
	    sql.append("     AND U.ID_PESSOA_ACAO <> U.ID_USUARIO                                                    ");
	    sql.append("     AND U.ID_PESSOA_ACAO = :idAnalistaOuConselheiro                                         ");
	    sql.append("     AND U.PERFIL_USUARIO_DESTINATARIO IN ('siacolconselheiro', 'siacolcoordenadorcamara')   ");
		sql.append("     AND TO_CHAR(U.DT_CREATE, 'YYYY') = :ano                                                 ");
		sql.append("     AND TO_CHAR(U.DT_CREATE, 'MM') IN (:meses)                                              ");
	    sql.append("     AND U.NUMERO IN                                                                         ");
		sql.append("    (SELECT P.NO_PROTOCOLO FROM SIACOL_PROTOCOLOS P                                          "); 
		sql.append("      WHERE P.FK_ASSUNTO_SIACOL = :idAssunto)                                                ");// FIXME IF ASSUNTO_SIACOL != NULL
		sql.append("     AND U.ID < :idRecebimento                                                               ");
		sql.append("     AND ROWNUM = 1                                                                          ");
		sql.append("     ORDER BY U.ID DESC                                                                      ");
	
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
			query.setParameter("eventoConselheiroImpedido", TipoEventoAuditoria.CONSELHEIRO_IMPEDIDO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idAssunto", idAssunto);
			query.setParameter("idRecebimento", idRecebimento);
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("meses", pesquisa.getMeses());
			
			saida = populaRel07RecebimentoDto(query, saida);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getDistribuicaoConselheiroOuImpedimento", StringUtil.convertObjectToJson(""), e);
		}
		return saida;
	}
	
	public List<ProtocolosRel07RecebimentoDto> getRecebimentoSiacol(PesquisaRelatorioSiacolDto pesquisa, Long idAssunto) {
		
		List<ProtocolosRel07RecebimentoDto> listaRecebimentosSiacol = new ArrayList<ProtocolosRel07RecebimentoDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO, A.DT_CREATE                               ");
		sql.append("   FROM CAD_AUDITORIA A                                           ");
		sql.append("   JOIN SIACOL_PROTOCOLOS P ON (A.NUMERO = P.NO_PROTOCOLO)        ");
		sql.append("  WHERE A.EVENTO = :eventoRecebimento                             ");
		sql.append("    AND A.MODULO = :moduloSiacol                                  ");
		sql.append("    AND A.ID_USUARIO = :idAnalistaOuConselheiro                   ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                       ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') IN (:meses)                    ");
//			sql.append("    AND P.FK_ASSUNTO_SIACOL = :idAssunto                          ");// FIXME IF ASSUNTO_SIACOL != NULL, corrigir erro numero invalido

		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoRecebimento", TipoEventoAuditoria.SIACOL_RECEBER_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
//				query.setParameter("idAssunto", idAssunto);
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("meses", pesquisa.getMeses());
			
			listaRecebimentosSiacol = populaListaRel07RecebimentoDto(query, listaRecebimentosSiacol);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getRecebimentoSiacol", StringUtil.convertObjectToJson(""), e);
		}
		return listaRecebimentosSiacol;
	}
	
	public ProtocolosRel07RecebimentoDto getDistribuicaoAnalista(PesquisaRelatorioSiacolDto pesquisa, String numeroProtocolo, String idRecebimento) {
		
		ProtocolosRel07RecebimentoDto distribuicao = new ProtocolosRel07RecebimentoDto();
		
		StringBuilder sql = new StringBuilder();
		// DISTRIBUICAO A PARTIR DA LISTA DOS RECEBIDOS PELA PESSOA, caso nao retorne nada, deverá ser usada a query para buscar o envio
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                          ");
		sql.append("   FROM CAD_AUDITORIA U                                      ");
		sql.append("  WHERE U.EVENTO = :eventoDistribuicao                       ");
		sql.append("    AND U.MODULO = :moduloSiacol                             ");
		sql.append("    AND U.ID_USUARIO_DESTINATARIO = :idAnalistaOuConselheiro ");
		sql.append("    AND U.NUMERO = :numeroProtocolo                          ");
			// distribuicao deve estar entre tramitacao e recebimento
		sql.append("    AND U.ID < :idRecebimento                                ");
		sql.append("    AND U.ID > (SELECT A.ID FROM CAD_AUDITORIA A             ");
		sql.append("                 WHERE A.EVENTO = :eventoTramitacao          ");
		sql.append("                   AND A.MODULO = :moduloSiacol              ");
		sql.append("                   AND (A.ACAO = 'C' OR A.ACAO = 'U')        ");
		sql.append("                   AND A.NUMERO = :numeroProtocolo           ");
		sql.append("                   AND A.ID < :idRecebimento)                ");
		sql.append("    AND ROWNUM = 1                                           ");
		sql.append("  ORDER BY U.ID DESC                                         ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
			query.setParameter("eventoTramitacao", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setParameter("idRecebimento", idRecebimento);
			
			distribuicao = populaRel07RecebimentoDto(query, distribuicao);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getDistribuicaoAnalista", StringUtil.convertObjectToJson(""), e);
		}
		return distribuicao;
	}
	
	public ProtocolosRel07RecebimentoDto getTramiteEnvioAnalista(PesquisaRelatorioSiacolDto pesquisa, String numeroProtocolo, String idRecebimento) {
		
		ProtocolosRel07RecebimentoDto tramiteEnvio = new ProtocolosRel07RecebimentoDto();
		
		// FIXME não precisa de id analista ou filtro assunto??
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                  ");
		sql.append("   FROM CAD_AUDITORIA U                              ");
		sql.append("  WHERE U.EVENTO = :eventoTramitacao                 ");
		sql.append("    AND U.MODULO = :moduloSiacol                     ");
		sql.append("    AND (U.ACAO = 'C' OR U.ACAO = 'U')               ");
		sql.append("    AND U.NUMERO = :numeroProtocolo                  ");
		sql.append("    AND U.ID < :idRecebimento                        ");
		
		// inclusão tramite deve ser maior que ultimo recebimento que seja menor que recebimento atual
		// atenção, pois há 2 tipos de recebimento
		sql.append("    AND U.ID > (SELECT A.ID FROM CAD_AUDITORIA A             ");
		sql.append("                 WHERE A.EVENTO = :eventoTramitacao          ");
		sql.append("                   AND A.MODULO = :moduloSiacol              ");
		sql.append("                   AND (A.ACAO = 'C' OR A.ACAO = 'U')        ");
		sql.append("                   AND A.NUMERO = :numeroProtocolo           ");
		sql.append("                   AND A.ID < :idRecebimento)                ");
		
		
		sql.append("    AND ROWNUM = 1                                   ");
		sql.append("  ORDER BY U.ID DESC                                 ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoTramitacao", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setParameter("idRecebimento", idRecebimento);
			
			tramiteEnvio = populaRel07RecebimentoDto(query, tramiteEnvio);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getTramiteEnvioAnalista", StringUtil.convertObjectToJson(""), e);
		}
		return tramiteEnvio;
	}
	
	public ProtocolosRel07RecebimentoDto getSaidaAnaliseAssinaEPautaConselheiro(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
		// 1º ID DEPOIS DO RECEBIMENTO NOS CASOS ABAIXO, SE NAO ACHAR NA PRIMEIRA QUERY, PROCURAR NA PROXIMA
		ProtocolosRel07RecebimentoDto saidaAnalise = new ProtocolosRel07RecebimentoDto();
		
		String[] statusAPautarEDerivados = new String[]{
				"A_PAUTAR",
				"A_PAUTAR_PRESENCIAL",
				"A_PAUTAR_SEM_QUORUM",
				"A_PAUTAR_PROVISORIO",
				"A_PAUTAR_VISTAS",
				"A_PAUTAR_DESTAQUE",
				"A_PAUTAR_AD_REFERENDUM"
				};
		// FIXME ASSINA E PAUTA
		// VERIFICAR EVENTO DE ALTERACAO DE STATUS ONDE STATUS NOVO É IGUAL A ALGUM A_PAUTAR E SUAS DERIVAÇÕES
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                           ");
		sql.append("   FROM CAD_AUDITORIA U                                       ");
		sql.append("  WHERE U.EVENTO = :eventoAlteracaoStatus                     ");
		sql.append("    AND U.MODULO = :moduloSiacol                              ");
		sql.append("    AND U.ID_USUARIO_DESTINATARIO = :idAnalistaOuConselheiro  ");  // FIXME REALMENTE É O DESTINATARIO QUE QUEREMOS?
		sql.append("    AND U.STATUS_NOVO IN (:statusAPautarEDerivados)           ");
//			sql.append("    AND U.NUMERO = :numeroProtocolo                   ");
//				// distribuicao deve estar entre tramitacao e recebimento
//			sql.append("    AND U.ID < :idRecebimento                         ");
//			sql.append("    AND U.ID > (SELECT U.ID FROM CAD_AUDITORIA U      ");
//			sql.append("                 WHERE U.EVENTO = :eventoTramitacao   ");
//			sql.append("                   AND U.MODULO = :moduloSiacol       ");
//			sql.append("                  AND (U.ACAO = 'C' OR U.ACAO = 'U')  ");
////			sql.append("    AND U.ID_DEPARTAMENTO_DESTINO = :idDepartamento   ");
//			sql.append("    AND U.NUMERO = :numeroProtocolo                   ");
//			sql.append("    AND U.ID < :idRecebimento                         ");
//			sql.append("  ORDER BY U.ID DESC)                                 ");
//			sql.append("    AND ROWNUM = 1                                    ");
//			sql.append("  ORDER BY U.ID DESC                                  ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("statusAPautarEDerivados", statusAPautarEDerivados);
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			
			saidaAnalise = populaRel07RecebimentoDto(query, saidaAnalise);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getSaidaAnaliseAssinaEPautaConselheiro", StringUtil.convertObjectToJson(""), e);
		}
		return saidaAnalise;
	}
	
	public ProtocolosRel07RecebimentoDto getSaidaAnaliseImpedimentoConselheiro(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {

		ProtocolosRel07RecebimentoDto saidaAnalise = new ProtocolosRel07RecebimentoDto();
		// VERIFICAR SE TEM EVENTO DE IMPEDIMENTO(37)
		StringBuilder sql = new StringBuilder();
		// DISTRIBUICAO A PARTIR DA LISTA DOS RECEBIDOS PELA PESSOA, caso nao retorne nada, deverá ser usada a query para buscar o envio
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                           ");
		sql.append("   FROM CAD_AUDITORIA U                                       ");
		sql.append("  WHERE U.EVENTO = :eventoConselheiroImpedido                 ");
		sql.append("    AND U.MODULO = :moduloSiacol                              ");
		sql.append("    AND U.ID_USUARIO_DESTINATARIO = :idAnalistaOuConselheiro  "); // FIXME DESTINATARIO?
//			sql.append("    AND U.NUMERO = :numeroProtocolo                   ");
//				// distribuicao deve estar entre tramitacao e recebimento
//			sql.append("    AND U.ID < :idRecebimento                         ");
//			sql.append("    AND U.ID > (SELECT U.ID FROM CAD_AUDITORIA U      ");
//			sql.append("                 WHERE U.EVENTO = :eventoTramitacao   ");
//			sql.append("                   AND U.MODULO = :moduloSiacol       ");
//			sql.append("                  AND (U.ACAO = 'C' OR U.ACAO = 'U')  ");
//			sql.append("    AND U.NUMERO = :numeroProtocolo                   ");
//			sql.append("    AND U.ID < :idRecebimento                         ");
//			sql.append("  ORDER BY U.ID DESC)                                 ");
//			sql.append("    AND ROWNUM = 1                                    ");
//			sql.append("  ORDER BY U.ID DESC                                  ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoConselheiroImpedido", TipoEventoAuditoria.CONSELHEIRO_IMPEDIDO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			
			saidaAnalise = populaRel07RecebimentoDto(query, saidaAnalise);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getSaidaAnaliseImpedimentoConselheiro", StringUtil.convertObjectToJson(""), e);
		}
		return saidaAnalise;
	}
	// FIXME é utilizado?
	public ProtocolosRel07RecebimentoDto getSaidaAnaliseDistribuicao(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
		// VERIFICAR EVENTO DE ALTERACAO DE STATUS ONDE STATUS NOVO É IGUAL A DEVOLUÇÃO
		
		
		// FIXME DISTRIBUICAO ANALISTA
		// VERIFICAR EVENTO DE ALTERACAO DE STATUS ONDE STATUS NOVO É IGUAL A DEVOLUÇÃO
		
		ProtocolosRel07RecebimentoDto saidaAnalise = new ProtocolosRel07RecebimentoDto();
		
		StringBuilder sql = new StringBuilder();
		// DISTRIBUICAO A PARTIR DA LISTA DOS RECEBIDOS PELA PESSOA, caso nao retorne nada, deverá ser usada a query para buscar o envio
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                            ");
		sql.append("   FROM CAD_AUDITORIA U                                        ");
		sql.append("  WHERE U.EVENTO = :eventoAlteracaoStatus                      ");
		sql.append("    AND U.MODULO = :moduloSiacol                               ");
		sql.append("    AND U.ID_USUARIO_DESTINATARIO = :idAnalistaOuConselheiro   ");
		sql.append("    AND U.STATUS_NOVO = 'DEVOLUCAO'                            ");
//			sql.append("    AND U.NUMERO = :numeroProtocolo                   ");
//				// distribuicao deve estar entre tramitacao e recebimento
//			sql.append("    AND U.ID < :idRecebimento                         ");
//			sql.append("    AND U.ID > (SELECT U.ID FROM CAD_AUDITORIA U      ");
//			sql.append("                 WHERE U.EVENTO = :eventoTramitacao   ");
//			sql.append("                   AND U.MODULO = :moduloSiacol       ");
//			sql.append("                  AND (U.ACAO = 'C' OR U.ACAO = 'U')  ");
////			sql.append("    AND U.ID_DEPARTAMENTO_DESTINO = :idDepartamento   ");
//			sql.append("    AND U.NUMERO = :numeroProtocolo                   ");
//			sql.append("    AND U.ID < :idRecebimento                         ");
//			sql.append("  ORDER BY U.ID DESC)                                 ");
//			sql.append("    AND ROWNUM = 1                                    ");
//			sql.append("  ORDER BY U.ID DESC                                  ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			
			saidaAnalise = populaRel07RecebimentoDto(query, saidaAnalise);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getSaidaAnaliseDistribuicao", StringUtil.convertObjectToJson(""), e);
		}
		return saidaAnalise;
	}
	
	public ProtocolosRel07RecebimentoDto getSaidaAnaliseIntervencaoCoordenadorCamara(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {

		ProtocolosRel07RecebimentoDto saidaAnalise = new ProtocolosRel07RecebimentoDto();
		
		StringBuilder sql = new StringBuilder();
		// DISTRIBUICAO A PARTIR DA LISTA DOS RECEBIDOS PELA PESSOA, caso nao retorne nada, deverá ser usada a query para buscar o envio
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                           ");
		sql.append("   FROM CAD_AUDITORIA U                                       ");
		sql.append("  WHERE U.EVENTO = :eventoDistribuicao                        ");
		sql.append("    AND U.MODULO = :moduloSiacol                              ");
		sql.append("    AND U.ID_USUARIO_DESTINATARIO = :idAnalistaOuConselheiro  ");
//			sql.append("    AND U.NUMERO = :numeroProtocolo                   ");
//				// distribuicao deve estar entre tramitacao e recebimento
//			sql.append("    AND U.ID < :idRecebimento                         ");
//			sql.append("    AND U.ID > (SELECT U.ID FROM CAD_AUDITORIA U      ");
//			sql.append("                 WHERE U.EVENTO = :eventoTramitacao   ");
//			sql.append("                   AND U.MODULO = :moduloSiacol       ");
//			sql.append("                  AND (U.ACAO = 'C' OR U.ACAO = 'U')  ");
//			sql.append("    AND U.NUMERO = :numeroProtocolo                   ");
//			sql.append("    AND U.ID < :idRecebimento                         ");
//			sql.append("  ORDER BY U.ID DESC)                                 ");
//			sql.append("    AND ROWNUM = 1                                    ");
//			sql.append("  ORDER BY U.ID DESC                                  ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
//				query.setParameter("eventoTramitacao", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			
			saidaAnalise = populaRel07RecebimentoDto(query, saidaAnalise);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getSaidaAnaliseIntervencaoCoordenadorCamara", StringUtil.convertObjectToJson(""), e);
		}
		return saidaAnalise;
	}
	
	public List<ProtocolosRel07RecebimentoDto> getEntradaAnaliseAnalista(PesquisaRelatorioSiacolDto pesquisa, List<String> listaRecebimento) {
		
		List<String> listaStatus = new ArrayList<String>(Arrays.asList(
				"ANALISE",
				"PENDENTE_VINCULACAO",
				"VINCULADO",
				"DEVOLUCAO",
				"ANALISE_PROVISORIO_NEGADO",
				"ANALISE_AD_REFERENDUM",
				"ANALISE_CUMPRIMENTO_OFICIO",
				"DISTRIBUICAO_COORD_COAC_PARA_ANALISE"
				));
		
		List<ProtocolosRel07RecebimentoDto> listaEntradaAnalise =  new ArrayList<ProtocolosRel07RecebimentoDto>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                              "); 
		sql.append("   FROM CAD_AUDITORIA U                                          ");
		sql.append("  WHERE U.EVENTO = :eventoAlteracaoStatus                        ");
		sql.append("    AND U.ID_USUARIO_DESTINATARIO = :idAnalistaOuConselheiro     ");
		sql.append("    AND U.MODULO = :moduloSiacol                                 ");
		sql.append("    AND U.STATUS_NOVO IN (:listaStatus)                          ");
		sql.append("    AND U.NUMERO IN (:listaRecebimento)                          ");
		
		sql.append("    AND U.ID >                                                   ");
		sql.append("   (SELECT A.ID FROM CAD_AUDITORIA A                             ");
		sql.append("      JOIN SIACOL_PROTOCOLOS P ON (A.NUMERO = P.NO_PROTOCOLO)    ");
		sql.append("     WHERE A.EVENTO = :eventoTramitacao                          ");
		sql.append("       AND A.MODULO = :moduloSiacol                              ");
		sql.append("      AND (A.ACAO = 'C' OR A.ACAO = 'U')                         ");
		sql.append("       AND A.NUMERO IN (:listaRecebimento)                       ");
		sql.append("       AND ROWNUM = 1)                                           "); // order by ficava na subquery, mas nao funciona desta maneira, verificar se atende ou se é necessário separar as queries
		sql.append("   ORDER BY U.ID DESC                                            ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("eventoTramitacao", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("listaRecebimento", listaRecebimento);
			query.setParameter("listaStatus", listaStatus);
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					ProtocolosRel07RecebimentoDto dto = new ProtocolosRel07RecebimentoDto();
					
					dto.setIdAuditoria(result[0] != null ? result[0].toString() : "");
					dto.setNumeroProtocolo(result[1] != null ? result[1].toString() : "");
					dto.setData((Date) result[2]);
					
					listaEntradaAnalise.add(dto);
				}
			}
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getEntradaAnaliseAnalista", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return listaEntradaAnalise;
	}
	
	public ProtocolosRel07RecebimentoDto getSaidaAnaliseAnalista(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, ProtocolosRel07RecebimentoDto recebimentoAnalise) {
		
		ProtocolosRel07RecebimentoDto status = new ProtocolosRel07RecebimentoDto();
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT U.ID, U.NUMERO, U.DT_CREATE                ");
		sql.append("    FROM CAD_AUDITORIA U                            ");
		sql.append("   WHERE U.EVENTO = :eventoAlteracaoStatus          ");
		sql.append("     AND U.MODULO = :moduloSiacol                   ");
		sql.append("     AND U.ID_USUARIO_DESTINATARIO = :idPessoa      ");
		sql.append("     AND U.NUMERO = :numeroProtocolo                ");
		sql.append("     AND U.STATUS_NOVO <> 'PAUSADO'                 "); // FIXME CONFIRMAR SE PAUSADO NAO EH CONSIDERADO SAIDA
		sql.append("     AND U.ID > :idEntradaAnalise                   ");
		sql.append("     AND ROWNUM = 1                                 ");
		sql.append("   ORDER BY U.ID DESC                               ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idPessoa", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("idEntradaAnalise", recebimentoAnalise.getIdAuditoria());
			query.setParameter("numeroProtocolo", recebimentoAnalise.getNumeroProtocolo());
			
			status = populaRel07RecebimentoDto(query, status);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getSaidaAnaliseAnalista", StringUtil.convertObjectToJson(""), e);
		}
		
		return status;
	}
	
	public List<ProtocolosRel07RecebimentoDto> getEntradaPausadosAnalista(PesquisaRelatorioSiacolDto pesquisa, List<String> listaRecebimento) {

		List<ProtocolosRel07RecebimentoDto> listaPausados = new ArrayList<ProtocolosRel07RecebimentoDto>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                              "); 
		sql.append("   FROM CAD_AUDITORIA U                                          ");
		sql.append("  WHERE U.EVENTO = :eventoPausa                                  ");
		sql.append("    AND U.ID_USUARIO = :idAnalistaOuConselheiro                  ");
		sql.append("    AND U.MODULO = :moduloSiacol                                 ");
		sql.append("    AND U.NUMERO IN (:listaRecebimento)                          ");
		
		sql.append("    AND U.ID >                                                   ");
		sql.append("   (SELECT A.ID FROM CAD_AUDITORIA A                             ");
		sql.append("      JOIN SIACOL_PROTOCOLOS P ON (A.NUMERO = P.NO_PROTOCOLO)    ");
		sql.append("     WHERE A.EVENTO = :eventoTramite                             ");
		sql.append("       AND A.MODULO = :moduloSiacol                              ");
		sql.append("      AND (A.ACAO = 'C' OR A.ACAO = 'U')                         ");
		sql.append("       AND A.NUMERO IN (:listaRecebimento)                       "); // FIXME verificar comportamento ao usar sinal de maior com retorno deste select com uma lista no IN
		sql.append("       AND ROWNUM = 1)                                           "); // FIXME order by teve que ser retirado da subquery
		sql.append("     ORDER BY U.ID DESC                                          ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("listaRecebimento", listaRecebimento);
			query.setParameter("eventoPausa", TipoEventoAuditoria.SIACOL_PAUSAR_PROTOCOLO.getId());
			query.setParameter("eventoTramite", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			
			listaPausados = populaListaRel07RecebimentoDto(query, listaPausados);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getEntradaPausadosAnalista", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return listaPausados;
	}
	
	public ProtocolosRel07RecebimentoDto getSaidaPausadosAnalista(PesquisaRelatorioSiacolDto pesquisa, ProtocolosRel07RecebimentoDto entradaPausado) {
		ProtocolosRel07RecebimentoDto pausado = new ProtocolosRel07RecebimentoDto();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE              "); 
		sql.append("   FROM CAD_AUDITORIA U                          ");
		sql.append("  WHERE U.EVENTO = :eventoRetiraPausa            ");
		sql.append("    AND U.ID_USUARIO = :idAnalistaOuConselheiro  ");
		sql.append("    AND U.MODULO = :moduloSiacol                 ");
		sql.append("    AND U.ID > :idEntradaPausado                 ");
		sql.append("    AND ROWNUM = 1                               ");
		sql.append("    ORDER BY U.ID ASC                            ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("idEntradaPausado", entradaPausado.getIdAuditoria());
			query.setParameter("eventoRetiraPausa", TipoEventoAuditoria.SIACOL_RETIRA_PAUSA_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			
			pausado = populaRel07RecebimentoDto(query, pausado);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getSaidaPausadosAnalista", StringUtil.convertObjectToJson(pesquisa), e);
		}
		
		return pausado;
	}
	
	public List<ProtocolosRel07RecebimentoDto> getEntradaVinculoAnalista(PesquisaRelatorioSiacolDto pesquisa, Long idAssunto) {
		
		List<ProtocolosRel07RecebimentoDto> vinculo = new ArrayList<ProtocolosRel07RecebimentoDto>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                              "); 
		sql.append("   FROM CAD_AUDITORIA U                                          ");
		sql.append("  WHERE U.EVENTO = :eventoRetiraPausa                            ");
		sql.append("    AND U.ID_USUARIO_DESTINATARIO = :idAnalistaOuConselheiro     ");
		sql.append("    AND U.MODULO = :moduloSiacol                                 ");
		sql.append("    AND U.STATUS_NOVO = 'PENDENTE_VINCULO'                       ");
//			sql.append("    AND U.ID > :idEntradaPausado                                 ");
//			sql.append("    AND ROWNUM = 1                                               ");
//			sql.append("    ORDER BY U.ID ASC                                            ");
		// FIXME rever query
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("eventoRetiraPausa", TipoEventoAuditoria.SIACOL_RETIRA_PAUSA_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			
			vinculo = populaListaRel07RecebimentoDto(query, vinculo);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getEntradaVinculoAnalista", StringUtil.convertObjectToJson(""), e);
		}
		
		return vinculo;
	}
	
	public ProtocolosRel07RecebimentoDto getSaidaVinculoAnalista(PesquisaRelatorioSiacolDto pesquisa, String idEntradaVinculo) {
		
		ProtocolosRel07RecebimentoDto saidaVinculo = new ProtocolosRel07RecebimentoDto();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT U.ID, U.NUMERO, U.DT_CREATE                              "); 
		sql.append("   FROM CAD_AUDITORIA U                                          ");
		sql.append("  WHERE U.EVENTO = :eventoRetiraPausa                            ");
		sql.append("    AND U.ID_USUARIO_DESTINATARIO = :idAnalistaOuConselheiro     ");
		sql.append("    AND U.MODULO = :moduloSiacol                                 ");
		sql.append("    AND U.STATUS_ANTIGO = 'PENDENTE_VINCULO'                     ");
		sql.append("    AND U.ID > :idEntradaVinculo                                 ");
		sql.append("    AND ROWNUM = 1                                               ");
		sql.append("    ORDER BY U.ID ASC                                            ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idAnalistaOuConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("eventoRetiraPausa", TipoEventoAuditoria.SIACOL_RETIRA_PAUSA_PROTOCOLO.getId());
			query.setParameter("moduloSiacol", ModuloSistema.SIACOL.getId());
			query.setParameter("idEntradaVinculo", idEntradaVinculo);
			
			saidaVinculo = populaRel07RecebimentoDto(query, saidaVinculo);
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol07Dao || getSaidaVinculoAnalista", StringUtil.convertObjectToJson(""), e);
		}
		
		return saidaVinculo;
	}
	
	private List<ProtocolosRel07RecebimentoDto> populaListaRel07RecebimentoDto(Query query, List<ProtocolosRel07RecebimentoDto> listaRecebimentos) {
		Iterator<?> it = query.getResultList().iterator();

		if (query.getResultList().size() > 0) {
			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();
				
				ProtocolosRel07RecebimentoDto dto = new ProtocolosRel07RecebimentoDto();
				
				dto.setIdAuditoria(result[0] != null ? result[0].toString() : "");
				dto.setNumeroProtocolo(result[1] != null ? result[1].toString() : "");
				dto.setData((Date) result[2]);
				
				listaRecebimentos.add(dto);
			}
		}
		return listaRecebimentos;
	}
	
	private ProtocolosRel07RecebimentoDto populaRel07RecebimentoDto(Query query, ProtocolosRel07RecebimentoDto dto) {
		Iterator<?> it = query.getResultList().iterator();

		if (query.getResultList().size() > 0) {
			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();
				
				dto.setIdAuditoria(result[0] == null ? "" : result[0].toString());
				dto.setNumeroProtocolo(result[1] == null ? "" : result[1].toString());
				dto.setData((Date) result[2]);
			}
		} else {
			return null;
		}
		return dto;
	}
}
