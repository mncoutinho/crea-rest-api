package br.org.crea.commons.dao.siacol;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.PresencaReuniaoSiacol;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PresencaReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.RelatorioReuniaoSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RelatorioReuniaoSiacolDao extends GenericDao<ProtocoloSiacol, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public RelatorioReuniaoSiacolDao() {
		super(ProtocoloSiacol.class); //FIXME
	}
	
	public List<RelatorioReuniaoSiacolDto> relatorioDeCoordenadoresEAdjuntosPorDepartamento(Long idReuniao) {
		
		List<RelatorioReuniaoSiacolDto> listaRelatorio = new ArrayList<RelatorioReuniaoSiacolDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT D.SIGLA AS DEPARTAMENTO,                                                    ");
		sql.append("        S.DESCRICAO,                                                                ");
		sql.append("        P.CRACHA,                                                                   ");
		sql.append("        F.NOME,                                                                     ");
		sql.append("        CASE WHEN EXISTS(                                                           ");
		sql.append("        SELECT 1 FROM SIACOL_REUNIAO_PRESENCA E                                     ");
		sql.append("        WHERE E.FK_REUNIAO = :idReuniao                                             ");
		sql.append("        AND E.FK_PESSOA = C.FK_CODIGO_PERSONALIDADE)                                ");
		sql.append("        THEN 'PRESENTE'                                                             ");
		sql.append("        ELSE  'AUSENTE'                                                             ");
		sql.append("        END AS \"statusPresenca\"                                                   ");
		sql.append("   FROM PER_CARGO_CONSELHEIRO C                                                     ");
		sql.append("   JOIN PER_CARGOS S ON (C.FK_CARGO_RAIZ = S.CODIGO)                                ");
		sql.append("   JOIN PER_PERSONALIDADES P ON (C.FK_CODIGO_PERSONALIDADE = P.CODIGO)              ");
		sql.append("   JOIN CAD_PESSOAS_FISICAS F ON (C.FK_CODIGO_PERSONALIDADE = F.CODIGO)             ");
		sql.append("   JOIN PRT_DEPARTAMENTOS D ON (C.FK_DEPARTAMENTO = D.ID)                           ");
		sql.append("        WHERE C.FK_CARGO_RAIZ IN (2559, 2560)                                       ");
		sql.append("          AND TO_CHAR(C.DATAFINALCARGO, 'YYYYMMDD') >= TO_CHAR(SYSDATE, 'YYYYMMDD') ");
		sql.append("          AND C.REMOVIDO = 0                                                        ");
		sql.append("          AND C.DATADESLIGAMENTOCARGO IS NULL                                       ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					RelatorioReuniaoSiacolDto dto = new RelatorioReuniaoSiacolDto();
					
					Object[] result = (Object[]) it.next();
					
					dto.setDepartamento(result[0] == null ? "" : result[0].toString());
					dto.setCargo(result[1] == null ? "" : result[1].toString());
					dto.setCracha(result[2] == null ? "" : result[2].toString());
					dto.setNome(result[3] == null ? "" : result[3].toString());
					dto.setPresenca(result[4] == null ? "" : result[4].toString());
					
					listaRelatorio.add(dto);
				}
			}
			
		} catch (NoResultException e) {
			return listaRelatorio;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || relatorioDeCoordenadoresEAdjuntosPorDepartamento", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return listaRelatorio;		
	}
	
	public List<RelatorioReuniaoSiacolDto> relatorioPresentesNoMomento(Long idReuniao) {
		
		List<RelatorioReuniaoSiacolDto> listaRelatorio = new ArrayList<RelatorioReuniaoSiacolDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT F.NOME FROM SIACOL_REUNIAO_PRESENCA P            ");
		sql.append(" JOIN CAD_PESSOAS_FISICAS F ON (F.CODIGO = P.FK_PESSOA)  ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao                        ");
		sql.append("    AND (P.HORA_DEVOLUCAO_CRACHA IS NULL)                ");
		sql.append("    AND P.HORA_ENTREGA_CRACHA IS NOT NULL                ");
		sql.append("    AND (P.PARTE = 0 OR P.PARTE IS NULL)                 ");
		sql.append("  ORDER BY F.NOME                                        ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					RelatorioReuniaoSiacolDto dto = new RelatorioReuniaoSiacolDto();
					
					String result = (String) it.next();
					
					dto.setNome(result);
					listaRelatorio.add(dto);
				}
			}
			
		} catch (NoResultException e) {
			return listaRelatorio;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || relatorioPresentesNoMomento", StringUtil.convertObjectToJson(idReuniao), e);
		}
		return listaRelatorio;
	}
	
	public List<RelatorioReuniaoSiacolDto> relatorioDeComparecimento(Long idReuniao) {
		
		List<RelatorioReuniaoSiacolDto> listaRelatorio = new ArrayList<RelatorioReuniaoSiacolDto>();
	        
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT F.NOME,                                                                                      ");
//		sql.append("        R.DESCRICAO,                                                                                 ");
		sql.append("        TO_CHAR(P.HORA_ENTREGA_CRACHA, 'HH24:MI'),                                                   ");
		sql.append("        TO_CHAR(P.HORA_DEVOLUCAO_CRACHA, 'HH24:MI'),                                                 ");
		sql.append("        (SELECT TRUNC((S.HORA_DEVOLUCAO_CRACHA - S.HORA_ENTREGA_CRACHA) * 86400 / 3600) || ':' ||    "); 
		sql.append("         TRUNC(MOD((S.HORA_DEVOLUCAO_CRACHA - S.HORA_ENTREGA_CRACHA) * 86400, 3600) / 60) || ':' ||  ");
		sql.append("         TRUNC(MOD(MOD((S.HORA_DEVOLUCAO_CRACHA - S.HORA_ENTREGA_CRACHA) * 86400, 3600), 60))        ");
		sql.append("          FROM SIACOL_REUNIAO_PRESENCA S                                                             ");
		sql.append("               WHERE S.FK_PESSOA = P.FK_PESSOA                                                       ");
		sql.append("                 AND S.FK_REUNIAO = :idReuniao),                                                     ");
		
		sql.append("        (SELECT COUNT(ID) FROM SIACOL_VOTO_REUNIAO V                                                 ");
		sql.append("          WHERE V.FK_PESSOA = P.FK_PESSOA                                                            ");
		sql.append("            AND V.FK_REUNIAO = :idReuniao                                                            ");
		sql.append("            AND V.FK_PROTOCOLO IS NOT NULL),                                                          ");
		
		 
			     
		// DIÁRIA   
		sql.append(" (SELECT CASE                                                                                                        "); 
		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) = 0 THEN '0,00'     ");
		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) IS NULL THEN '0,00' ");
		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1310 THEN '0,00'                   "); 
		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1307 THEN '0,00'                   "); 
		sql.append("  WHEN (SELECT L.REGIAO_METROPOLITANA FROM CAD_LOCALIDADES L                                                         "); 
		sql.append("   WHERE CODIGO IN                                                                                                   "); 
		sql.append(" 	(SELECT E.FK_CODIGO_LOCALIDADES                                                                                  "); 
		sql.append(" 		FROM CAD_ENDERECOS E                                                                                         "); 
		sql.append(" 		WHERE E.FK_CODIGO_PESSOAS = P.FK_PESSOA                                                                      "); 
		sql.append(" 	      AND E.POSTAL = 1 AND E.ENDERECOVALIDO = 1)) = 1  THEN '0,00'                                               "); 
		sql.append("  WHEN P.KM_DOMICILIO_CREA > 400 THEN '321,49'                                                                       "); 
		sql.append("  ELSE '160,65' END AS DIARIA_JETON                                                                                  "); 
		sql.append("  FROM PER_PERSONALIDADES P                                                                                          "); 
		sql.append("  WHERE P.CODIGO = P.FK_PESSOA),                                                                                     ");
		
		
		// JETON  IMPORTANTE.... SÓ HAVERÁ JETON PARA QUEM COMPLETAR OS 80%, FILTRAR
		sql.append(" (SELECT CASE                                                                                                        "); 
		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) = 0 THEN '0,00'     ");
		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) IS NULL THEN '0,00' ");
		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1310 THEN '0,00'                   "); 
		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1307 THEN '0,00'                   "); 
		sql.append("  WHEN (SELECT L.REGIAO_METROPOLITANA FROM CAD_LOCALIDADES L                                                         "); 
		sql.append("   WHERE CODIGO IN                                                                                                   "); 
		sql.append(" 	(SELECT E.FK_CODIGO_LOCALIDADES                                                                                  "); 
		sql.append(" 		FROM CAD_ENDERECOS E                                                                                         "); 
		sql.append(" 		WHERE E.FK_CODIGO_PESSOAS = P.FK_PESSOA                                                                      "); 
		sql.append(" 	      AND E.POSTAL = 1 AND E.ENDERECOVALIDO = 1)) = 1  THEN '145,00'                                             "); 
		sql.append("  WHEN P.KM_DOMICILIO_CREA > 400 THEN '145,00'                                                                       "); 
		sql.append("  ELSE '145,00' END AS DIARIA_JETON                                                                                  "); 
		sql.append("  FROM PER_PERSONALIDADES P                                                                                          "); 
		sql.append("  WHERE P.CODIGO = P.FK_PESSOA),                                                                                     ");
		
		
		// SOMA
		sql.append(" (SELECT CASE                                                                                                        "); 
		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) = 0 THEN '0,00'     ");
		sql.append("  WHEN (SELECT PR.ATINGIU_80 FROM SIACOL_REUNIAO_PRESENCA PR WHERE PR.FK_REUNIAO = :idReuniao AND PR.FK_PESSOA = P.FK_PESSOA) IS NULL THEN '0,00' ");
		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1310 THEN 'REEMBOLSO TRANSPORTE'   "); 
		sql.append("  WHEN (SELECT R.FK_DEPARTAMENTO FROM SIACOL_REUNIAO R WHERE R.ID = :idReuniao) = 1307 THEN 'REEMBOLSO TRANSPORTE'   "); 
		sql.append("  WHEN (SELECT L.REGIAO_METROPOLITANA FROM CAD_LOCALIDADES L                                                         "); 
		sql.append("   WHERE CODIGO IN                                                                                                   "); 
		sql.append(" 	(SELECT E.FK_CODIGO_LOCALIDADES                                                                                  "); 
		sql.append(" 		FROM CAD_ENDERECOS E                                                                                         "); 
		sql.append(" 		WHERE E.FK_CODIGO_PESSOAS = P.FK_PESSOA                                                                      "); 
		sql.append(" 	      AND E.POSTAL = 1 AND E.ENDERECOVALIDO = 1)) = 1  THEN '145,00'                                             "); 
		sql.append("  WHEN P.KM_DOMICILIO_CREA > 400 THEN '466,49'                                                                       "); 
		sql.append("  ELSE '305,65' END AS DIARIA_JETON                                                                                  "); 
		sql.append("  FROM PER_PERSONALIDADES P                                                                                          "); 
		sql.append("  WHERE P.CODIGO = P.FK_PESSOA)                                                                                      "); 
				
		sql.append("        FROM SIACOL_REUNIAO_PRESENCA P                                                               ");
		sql.append("        JOIN CAD_PESSOAS_FISICAS F ON (P.FK_PESSOA = F.CODIGO)                                       ");
		sql.append("             WHERE P.FK_REUNIAO = :idReuniao                                                         ");
		sql.append("               AND P.PAPEL NOT LIKE '%PRESIDENTE%'                                                   ");
		sql.append("               AND P.PAPEL NOT LIKE 'TERCEIRO%'                                                      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					RelatorioReuniaoSiacolDto dto = new RelatorioReuniaoSiacolDto();
					
					Object[] result = (Object[]) it.next();
					
					dto.setNome(result[0] == null ? "" : result[0].toString());
//					dto.setEntidade(result[1] == null ? "" : result[1].toString());
					dto.setEntrada(result[1] == null ? "" : result[1].toString());
					dto.setSaida(result[2] == null ? "" : result[2].toString());
					dto.setTempoPresente(result[3] == null ? "" : result[3].toString());
					dto.setQtdVotado(result[4] == null ? "" : result[4].toString());
//					dto.setDiaria(result[5] == null ? "" : result[5].toString());
//					dto.setJeton(result[6] == null ? "" : result[6].toString());
//					dto.setSoma(result[7] == null ? "" : result[7].toString());
					
					listaRelatorio.add(dto);
				}
			}
			
		} catch (NoResultException e) {
			return listaRelatorio;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || relatorioDeComparecimento", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return listaRelatorio;
	}
	
	public RelatorioReuniaoSiacolDto relatorioDeComparecimentoComPausa(Long idReuniao, int qtdPartesDaReuniao) {
		
		RelatorioReuniaoSiacolDto relatorio = new RelatorioReuniaoSiacolDto();
		relatorio.setRelatorio(new ArrayList<RelatorioReuniaoSiacolDto>());
			
		RelatorioReuniaoSiacolDto dto = new RelatorioReuniaoSiacolDto();
		dto.setRelatorio(new ArrayList<RelatorioReuniaoSiacolDto>());
				
		dto.setParte("0");
				
		// PARTE INICIAL DO RELATORIO, VALORES DA DIARIA, JETON E SOMA
		List<PresencaReuniaoSiacolDto> listaPresencas = getParticipantesComPresencaNaReuniaoComPartes(idReuniao);
		
		for (PresencaReuniaoSiacolDto participante : listaPresencas) {
			RelatorioReuniaoSiacolDto dtoDetalheParticipante = new RelatorioReuniaoSiacolDto();
			dtoDetalheParticipante.setNome(participante.getPessoa().getNome());
			dtoDetalheParticipante.setQtdVotado(String.valueOf(getQuantidadeVotado(idReuniao, participante.getPessoa().getId())));
		
			BigDecimal qtdPartesQueAtingiuOitentaPorcento = buscaPresencasDaReuniaoPosDezenoveHoras(idReuniao, participante.getPessoa().getId());
			
			if (idReuniao.equals(1307L) || idReuniao.equals(1310L) || qtdPartesQueAtingiuOitentaPorcento.equals(new BigDecimal("0"))) {
				dtoDetalheParticipante.setDiaria(new BigDecimal("0.0"));
				dtoDetalheParticipante.setJeton(new BigDecimal("0.0"));
			} else {
				BigDecimal diaria = getValorDiaria(participante.getPessoa().getId());
				BigDecimal jeton = getValorJeton(participante.getPessoa().getId());
				dtoDetalheParticipante.setDiaria(qtdPartesQueAtingiuOitentaPorcento.multiply(diaria));
				dtoDetalheParticipante.setJeton(qtdPartesQueAtingiuOitentaPorcento.multiply(jeton));
			}
			
			dtoDetalheParticipante.setSoma(dtoDetalheParticipante.getDiaria().add(dtoDetalheParticipante.getJeton()));
					
			dto.getRelatorio().add(dtoDetalheParticipante);
		}
		relatorio.getRelatorio().add(dto);

				
		// OBTEM NOME, ENTRADA, SAIDA, TEMPO_PRESENTE DE CADA PARTE DA REUNIAO
		for (int parte = 1; parte < qtdPartesDaReuniao; parte++) {
			
			dto = new RelatorioReuniaoSiacolDto();
			dto.setRelatorio(new ArrayList<RelatorioReuniaoSiacolDto>());
					
			dto.setParte(String.valueOf(parte));
			
			List<PresencaReuniaoSiacol> listaPresenca = getParticipantesComPresencaNaReuniaoComParte(idReuniao, new Long(parte));
			
			for (PresencaReuniaoSiacol participante : listaPresenca) {
				RelatorioReuniaoSiacolDto dtoDetalheParticipante = new RelatorioReuniaoSiacolDto();
				dtoDetalheParticipante.setNome(participante.getPessoa().getNome());
				dtoDetalheParticipante.setEntrada(DateUtils.format(participante.getHoraEntregaCracha(), DateUtils.HH_MM));
				dtoDetalheParticipante.setSaida(DateUtils.format(participante.getHoraDevolucaoCracha(), DateUtils.HH_MM));
				dtoDetalheParticipante.setTempoPresente(getTempoPresenteNaReuniaoComParte(idReuniao, participante.getPessoa().getId(), parte));
				dto.getRelatorio().add(dtoDetalheParticipante);
			}
					
			relatorio.getRelatorio().add(dto);
		}	
			
		return relatorio;
	}

	public BigDecimal buscaPresencasDaReuniaoPosDezenoveHoras(Long idReuniao, Long idPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.ID)                                         ");
		sql.append("   FROM SIACOL_REUNIAO_PRESENCA P                           ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao                           ");
		sql.append("    AND P.FK_PESSOA = :idPessoa                             ");
		sql.append("    AND P.PAPEL NOT LIKE '%PRESIDENTE%'                     ");
		sql.append("    AND P.PAPEL NOT LIKE 'TERCEIRO%'                        ");
		sql.append("    AND P.ATINGIU_80 = 1                                    ");
		sql.append("    AND TO_CHAR(P.HORA_DEVOLUCAO_CRACHA, 'HH24MI') > '1900' ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("idPessoa", idPessoa);

			return (BigDecimal) query.getSingleResult();
			
		} catch (NoResultException e) {
			return new BigDecimal("0.0");
		} catch (Throwable e) {
//			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || buscaPresencasDaReuniao", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return new BigDecimal("0.0");
	}

	public List<RelatorioReuniaoSiacolDto> relatorioDerelatorioDeOitentaPorcento(Long idReuniao) {
		
		List<RelatorioReuniaoSiacolDto> listaRelatorio = new ArrayList<RelatorioReuniaoSiacolDto>();
	        
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT F.NOME,                                                                                      ");
		sql.append("        TO_CHAR(P.HORA_ENTREGA_CRACHA, 'HH24:MI'),                                                   ");
		sql.append("        TO_CHAR(P.HORA_DEVOLUCAO_CRACHA, 'HH24:MI'),                                                 ");
		sql.append("        ( TRUNC((P.HORA_DEVOLUCAO_CRACHA - P.HORA_ENTREGA_CRACHA) * 86400 / 3600) || ':' ||          "); 
		sql.append("         TRUNC(MOD((P.HORA_DEVOLUCAO_CRACHA - P.HORA_ENTREGA_CRACHA) * 86400, 3600) / 60) || ':' ||  ");
		sql.append("         TRUNC(MOD(MOD((P.HORA_DEVOLUCAO_CRACHA - P.HORA_ENTREGA_CRACHA) * 86400, 3600), 60)) ),     ");
		sql.append("        TO_CHAR(P.HORA_80, 'HH24:MI')                                                                ");
		sql.append("        FROM SIACOL_REUNIAO_PRESENCA P                                                               ");
		sql.append("        JOIN CAD_PESSOAS_FISICAS F ON (P.FK_PESSOA = F.CODIGO)                                       ");
		sql.append("             WHERE P.FK_REUNIAO = :idReuniao                                                         ");
		sql.append("               AND P.ATINGIU_80 = 1                                                                  ");
		sql.append("               AND P.PAPEL NOT LIKE '%PRESIDENTE%'                                                   ");
		sql.append("               AND P.PAPEL NOT LIKE 'TERCEIRO%'                                                      ");
		sql.append("             ORDER BY P.HORA_80                                                                      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					RelatorioReuniaoSiacolDto dto = new RelatorioReuniaoSiacolDto();
					
					Object[] result = (Object[]) it.next();
					
					dto.setNome(result[0] == null ? "" : result[0].toString());
					dto.setEntrada(result[1] == null ? "" : result[1].toString());
					dto.setSaida(result[2] == null ? "" : result[2].toString());
					dto.setTempoPresente(result[3] == null ? "" : result[3].toString());
					dto.setHoraOitentaPorcento(result[4] == null ? "" : result[4].toString());
					
					listaRelatorio.add(dto);
				}
			}
			
		} catch (NoResultException e) {
			return listaRelatorio;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || relatorioDeOitentaPorcento", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return listaRelatorio;
	}

	public List<PresencaReuniaoSiacol> getParticipantesComPresencaNaReuniao(Long idReuniao) {
		
		List<PresencaReuniaoSiacol> presentes = new ArrayList<PresencaReuniaoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P   ");
		sql.append("  WHERE P.reuniao.id = :idReuniao        ");
		sql.append("    AND P.papel NOT LIKE '%PRESIDENTE%'  ");
		sql.append("    AND P.papel NOT LIKE 'TERCEIRO%'     ");
		
		try {
			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);
			
			presentes = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || getParticipantesComPresencaNaReuniao", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return presentes;
	}
	
	public List<PresencaReuniaoSiacolDto> getParticipantesComPresencaNaReuniaoComPartes(Long idReuniao) {
		
		List<PresencaReuniaoSiacolDto> presentes = new ArrayList<PresencaReuniaoSiacolDto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT P.FK_PESSOA,                              ");
		sql.append("                 F.NOME                                    ");
		sql.append("   FROM SIACOL_REUNIAO_PRESENCA P                          ");
		sql.append("   JOIN CAD_PESSOAS_FISICAS F ON (P.FK_PESSOA = F.CODIGO)  ");
		sql.append("  WHERE P.FK_REUNIAO = :idReuniao                          ");
		sql.append("    AND P.PAPEL NOT LIKE '%PRESIDENTE%'                    ");
		sql.append("    AND P.PAPEL NOT LIKE 'TERCEIRO%'                       ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			
			Iterator<?> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {
					
					Object[] result = (Object[]) it.next();
					
					PresencaReuniaoSiacolDto dto = new PresencaReuniaoSiacolDto();
					
					PessoaDto pessoa = new PessoaDto();
					
					BigDecimal id = (BigDecimal) result[0];
					pessoa.setId(id.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					pessoa.setNome(result[1] == null ? "" : result[1].toString());
					
					dto.setPessoa(pessoa);
					
					presentes.add(dto);
				}
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || getParticipantesComPresencaNaReuniaoComPartes", StringUtil.convertObjectToJson(idReuniao), e);
		}
		
		return presentes;
	}
	
	public List<PresencaReuniaoSiacol> getParticipantesComPresencaNaReuniaoComParte(Long idReuniao, Long parte) {
		
		List<PresencaReuniaoSiacol> presentes = new ArrayList<PresencaReuniaoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P FROM PresencaReuniaoSiacol P   ");
		sql.append("  WHERE P.reuniao.id = :idReuniao        ");
		sql.append("    AND P.papel NOT LIKE '%PRESIDENTE%'  ");
		sql.append("    AND P.papel NOT LIKE 'TERCEIRO%'     ");
		sql.append("    AND P.parte = :parte                 ");
		
		try {
			TypedQuery<PresencaReuniaoSiacol> query = em.createQuery(sql.toString(), PresencaReuniaoSiacol.class);
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("parte", parte);
			
			presentes = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || getParticipantesComPresencaNaReuniaoComParte", StringUtil.convertObjectToJson(idReuniao + " - " + parte), e);
		}
		
		return presentes;
	}

	public int getQuantidadeVotado(Long idReuniao, Long idPessoa) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM SIACOL_VOTO_REUNIAO V ");
		sql.append("  WHERE V.FK_REUNIAO = :idReuniao           ");
		sql.append("	AND V.FK_PESSOA = :idPessoa             ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("idPessoa", idPessoa);

			return Integer.parseInt(query.getSingleResult().toString());

		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || getQuantidadeVotado", StringUtil.convertObjectToJson(idReuniao + " -- " + idPessoa), e);
		}

		return 0;
	}
	
	public BigDecimal getValorDiaria(Long idPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT (DIARIA * VALOR)             ");
		sql.append("   FROM PER_AJUDA_CUSTO              ");
		sql.append("  WHERE CODIGO IN                    ");
		sql.append("  (SELECT FK_CODIGO_AJUDA_CUSTOS     ");
		sql.append("	 FROM PER_PERSONALIDADES         ");
		sql.append("	WHERE CODIGO = :idPessoa)        ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);

			return (BigDecimal) query.getSingleResult();

		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || getValorDiaria", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return new BigDecimal("0");
	}

	public BigDecimal getValorJeton(Long idPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT (JETON * VALOR)             ");
		sql.append("   FROM PER_AJUDA_CUSTO             ");
		sql.append("  WHERE CODIGO IN                   ");
		sql.append("  (SELECT FK_CODIGO_AJUDA_CUSTOS    ");
		sql.append("	 FROM PER_PERSONALIDADES        ");
		sql.append("	WHERE CODIGO = :idPessoa)       ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);

			return (BigDecimal) query.getSingleResult();

		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || getValorJeton", StringUtil.convertObjectToJson(idPessoa), e);
		}

		return new BigDecimal("0");
	}

	public String getTempoPresenteNaReuniao(Long idReuniao, Long idPessoa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT TRUNC((S.HORA_DEVOLUCAO_CRACHA - S.HORA_ENTREGA_CRACHA) * 86400 / 3600) || ':' ||     ");
		sql.append("  TRUNC(MOD((S.HORA_DEVOLUCAO_CRACHA - S.HORA_ENTREGA_CRACHA) * 86400, 3600) / 60) || ':' ||  ");
		sql.append("  TRUNC(MOD(MOD((S.HORA_DEVOLUCAO_CRACHA - S.HORA_ENTREGA_CRACHA) * 86400, 3600), 60))        ");
		sql.append("   FROM SIACOL_REUNIAO_PRESENCA S                                                             ");
		sql.append("  WHERE S.FK_PESSOA = :idPessoa                                                               ");
		sql.append("    AND S.FK_REUNIAO = :idReuniao                                                             ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("idPessoa", idPessoa);
			
			Object result = query.getSingleResult();
			
			return result != null ? result.toString() : "";
			
		} catch (NoResultException e) {
			return "";
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || getTempoPresenteNaReuniao", StringUtil.convertObjectToJson(idReuniao + " -- " + idPessoa), e);
		}
		
		return "";
	}

	public String getTempoPresenteNaReuniaoComParte(Long idReuniao, Long idPessoa, int parte) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT TRUNC((S.HORA_DEVOLUCAO_CRACHA - S.HORA_ENTREGA_CRACHA) * 86400 / 3600) || ':' ||     ");
		sql.append("  TRUNC(MOD((S.HORA_DEVOLUCAO_CRACHA - S.HORA_ENTREGA_CRACHA) * 86400, 3600) / 60) || ':' ||  ");
		sql.append("  TRUNC(MOD(MOD((S.HORA_DEVOLUCAO_CRACHA - S.HORA_ENTREGA_CRACHA) * 86400, 3600), 60))        ");
		sql.append("   FROM SIACOL_REUNIAO_PRESENCA S                                                             ");
		sql.append("  WHERE S.FK_PESSOA = :idPessoa                                                               ");
		sql.append("    AND S.FK_REUNIAO = :idReuniao                                                             ");
		sql.append("    AND S.PARTE = :parte                                                                      ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("parte", parte);
			
			Object result = query.getSingleResult();
			
			return result != null ? result.toString() : "";
			
		} catch (NoResultException e) {
			return "";
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioReuniaoSiacolDao || getTempoPresenteNaReuniaoComParte", StringUtil.convertObjectToJson(idReuniao + " -- " + idPessoa + " -- " + parte), e);
		}
		
		return "";
	}

	
}


