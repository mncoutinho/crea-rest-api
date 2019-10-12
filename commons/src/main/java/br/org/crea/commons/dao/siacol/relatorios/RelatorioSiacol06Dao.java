package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol06Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol06PesoDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol06Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	
	public RelatorioSiacol06Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public List<RelDetalhadoSiacol06Dto> getEntradaAnalistaDistribuicaoOuTramitacao(PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		
		List<RelDetalhadoSiacol06Dto> lista = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO, A.NUMERO_REFERENCIA, A.ID_DEPARTAMENTO_DESTINO,  ");
		sql.append("        D.NOME AS DEPARTAMENTO, S.CODIGO, S.NOME AS ASSUNTO,             ");
		sql.append("        A.DT_CREATE                                                      ");
		sql.append("   FROM CAD_AUDITORIA A                                                  ");
		sql.append("   LEFT JOIN PRT_DEPARTAMENTOS D ON (D.ID = A.ID_DEPARTAMENTO_DESTINO)   ");
		sql.append("   LEFT JOIN SIACOL_PROTOCOLOS P ON (P.NO_PROTOCOLO = A.NUMERO)          ");
		sql.append("   LEFT JOIN SIACOL_ASSUNTO S ON (S.ID = P.FK_ASSUNTO_SIACOL)            ");
		sql.append("  WHERE A.MODULO = :modulo                                               ");
		sql.append("    AND A.EVENTO IN (:eventoDistribuicao, :eventoTramitacao)             ");
		sql.append("    AND A.ID_USUARIO_DESTINATARIO = :idAnalista                          ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                              ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') = :mes                                ");
		sql.append("    AND (A.STATUS_NOVO NOT IN (:devolucao, :distribuicaoCoacAnalise)     ");
		sql.append("      OR A.STATUS_NOVO IS NULL)                                          ");
		sql.append("    AND (P.FK_ASSUNTO_SIACOL IN (:idsAssunto)                            ");
		sql.append("      OR P.FK_ASSUNTO_SIACOL IS NULL)                                    ");
		sql.append("  ORDER BY A.ID                                                          ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
			query.setParameter("eventoTramitacao", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("devolucao", StatusProtocoloSiacol.DEVOLUCAO.toString());
			query.setParameter("distribuicaoCoacAnalise", StatusProtocoloSiacol.DISTRIBUICAO_COORD_COAC_PARA_ANALISE.toString());
			query.setParameter("idAnalista", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("mes", mes);
			query.setParameter("idsAssunto", idsAssuntoSiacol);
			
			lista = populaResultList(query, lista);
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol06Dao || getEntradaAnalistaDistribuicaoOuTramitacao", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;	
	}
	
	private RelSiacol06PesoDto obtemPeso(RelDetalhadoSiacol06Dto dto) {

		RelSiacol06PesoDto peso = new RelSiacol06PesoDto();
		
		if (dto.getCodigoAssunto() != null) {
			
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  A.INSTRUCAO AS INSTRUCAO,   ");
			sql.append("         A.CAMARA AS CAMARA,         ");
			sql.append("         A.COMISSAO AS COMISSAO,     ");
			sql.append("         A.PLENARIA AS PLENARIA,     ");
			
			sql.append(" A.TEMPO_SERVICO *        ");
			
			if (dto.getIdDepartamento().contains("12")) {
				sql.append(" A.CAMARA             ");
			} else if (dto.getIdDepartamento().contains("13")) {
				sql.append(" A.COMISSAO           ");
			} else if (dto.getIdDepartamento().contains("11")) {
				sql.append(" A.PLENARIA           ");
			}
			
			sql.append("   FROM SIACOL_ASSUNTO A  ");
			sql.append("  WHERE A.CODIGO = :idAssunto ");
			
			try {
				Query query = em.createNativeQuery(sql.toString());
				query.setParameter("idAssunto", dto.getCodigoAssunto());
				
				Object[] resultado = (Object[]) query.getSingleResult();
				
				BigDecimal pesoInstrucao = (BigDecimal) resultado[0];
				BigDecimal pesoCamara = (BigDecimal) resultado[1];
				BigDecimal pesoComissao = (BigDecimal) resultado[2];
				BigDecimal pesoPlenaria = (BigDecimal) resultado[3];
				BigDecimal pesoValido = (BigDecimal) resultado[4];
				
				
				peso.setPesoInstrucao(pesoInstrucao != null ? pesoInstrucao.doubleValue() : 0d);
				peso.setPesoCamara(pesoCamara != null ? pesoCamara.doubleValue() : 0d);
				peso.setPesoComissao(pesoComissao != null ? pesoComissao.doubleValue() : 0d);
				peso.setPesoPlenaria(pesoPlenaria != null ? pesoPlenaria.doubleValue() : 0d);
				peso.setPesoValido(pesoValido != null ? pesoValido.doubleValue() : 0d);
				
			} catch (NoResultException e) {
				return peso;
			} catch (Throwable e) {
				httpGoApi.geraLog("RelatorioSiacol06Dao || obtemPeso", StringUtil.convertObjectToJson(dto), e);
			}
		} else {
			peso.setPesoInstrucao(1d);
			peso.setPesoCamara(1d);
			peso.setPesoComissao(1d);
			peso.setPesoPlenaria(1d);
			peso.setPesoValido(1d);
		}
		
		return peso;
	}
	
	public List<RelDetalhadoSiacol06Dto> getPassivoAnalistaAnosAnteriores(PesquisaRelatorioSiacolDto pesquisa, List<Long> idsAssuntoSiacol) {
		
		List<RelDetalhadoSiacol06Dto> lista = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO,                                                        ");
		sql.append("  A.NUMERO_REFERENCIA, A.ID_DEPARTAMENTO_DESTINO,                              ");
		sql.append("        D.NOME AS DEPARTAMENTO, S.CODIGO, S.NOME AS ASSUNTO, A.DT_CREATE "); 
		sql.append("   FROM CAD_AUDITORIA A                                                        "); 
		sql.append("   LEFT JOIN PRT_DEPARTAMENTOS D ON (D.ID = A.ID_DEPARTAMENTO_DESTINO)         "); 
		sql.append("   LEFT JOIN SIACOL_PROTOCOLOS P ON (P.NO_PROTOCOLO = A.NUMERO)                "); 
		sql.append("   LEFT JOIN SIACOL_ASSUNTO S ON (S.ID = P.FK_ASSUNTO_SIACOL)                  ");
		sql.append("   WHERE 1=1                                                                   ");
		
		// incluir casos que nao teve saida nenhuma, verificar se query já atende a esses casos
		
		sqlPassivoComReferencia(sql);
		sqlPassivoSemReferencia(sql);
		
		sql.append("    AND (P.FK_ASSUNTO_SIACOL IN (:idsAssunto)                                  ");
		sql.append("      OR P.FK_ASSUNTO_SIACOL IS NULL)                                          ");
		
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
			query.setParameter("eventoTramitacao", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("devolucao", StatusProtocoloSiacol.DEVOLUCAO.toString());
			query.setParameter("distribuicaoCoacAnalise", StatusProtocoloSiacol.DISTRIBUICAO_COORD_COAC_PARA_ANALISE.toString());
			query.setParameter("idAnalista", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("idsAssunto", idsAssuntoSiacol);
			
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("assinaturaAdReferendum", StatusProtocoloSiacol.ASSINATURA_AD_REFERENDUM.toString());
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("assinarOficio", StatusProtocoloSiacol.ASSINAR_OFICIO.toString());
			
			lista = populaResultList(query, lista);
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol06Dao || getPassivoAnalistaAnosAnteriores", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;
	}
	
	private List<RelDetalhadoSiacol06Dto> populaResultList(Query query, List<RelDetalhadoSiacol06Dto> lista) {
		Iterator<?> it = query.getResultList().iterator();

		if (query.getResultList().size() > 0) {
			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();
				
				RelDetalhadoSiacol06Dto dto = new RelDetalhadoSiacol06Dto();
				
				dto.setIdAuditoria(result[0] != null ? result[0].toString() : null);
				dto.setNumeroProtocolo(result[1] != null ? result[1].toString() : null);
				dto.setNumeroProtocoloReferencia(result[2] != null ? result[2].toString() : null);
				
				dto.setIdDepartamento(result[3] != null ? result[3].toString() : null);
				dto.setNomeDepartamento(result[4] != null ? result[4].toString() : null);
				dto.setCodigoAssunto(result[5] != null ? result[5].toString() : null);
				dto.setNomeAssunto(result[6] != null ? result[6].toString() : null);
				dto.setData(result[7] != null ? DateUtils.format((Date) result[7], DateUtils.DD_MM_YYYY_HH_MM) : null);
				
				dto.setPesoDto(obtemPeso(dto));
				
				lista.add(dto);
			}
		}
		return lista;
	}

	private void sqlPassivoComReferencia(StringBuilder sql) {
		// PRIMEIRO OS SEM REFERENCIA
		sql.append("   AND A.ID IN (                                                   ");
		
		sql.append(" SELECT MAX(ENTRADA.ID) AS ENTRADA_ID                              ");
		sql.append("   FROM CAD_AUDITORIA ENTRADA                                      "); 
		
		// LEFT JOIN
		sql.append("  LEFT JOIN (                                                      ");
		sql.append(" SELECT MAX(SAIDA.ID) AS SAIDA_ID, SAIDA.NUMERO                    ");
		sql.append("   FROM CAD_AUDITORIA SAIDA                                        ");
		sql.append("  WHERE SAIDA.MODULO = :modulo                                     ");
		sql.append("    AND (SAIDA.EVENTO IN (:eventoDistribuicao, :eventoTramitacao)  ");		
		sql.append("    OR (SAIDA.EVENTO = :eventoAlteracaoStatus                      ");
		sql.append("    AND SAIDA.STATUS_NOVO = :assinaturaAdReferendum)               ");		
		sql.append("    OR (SAIDA.EVENTO = :eventoAlteracaoStatus                      ");
		sql.append("    AND SAIDA.STATUS_NOVO = :assinarOficio)  )                     ");	
		
	    sql.append("    AND SAIDA.ID_PESSOA_ACAO = :idAnalista                         ");
	  	sql.append("    AND TO_CHAR(SAIDA.DT_CREATE, 'YYYY') < :ano                    ");
	    
		sql.append("  GROUP BY SAIDA.NUMERO                                            ");
		
		sql.append("  ) SAIDA ON (SAIDA.NUMERO = ENTRADA.NUMERO)                       ");
		// FIM LEFT JOIN
		
		
		sql.append("  WHERE ENTRADA.MODULO = :modulo                                               ");
		sql.append("    AND ENTRADA.EVENTO IN (:eventoDistribuicao, :eventoTramitacao)             ");
		sql.append("    AND ENTRADA.ID_USUARIO_DESTINATARIO = :idAnalista                          ");
		sql.append("    AND TO_CHAR(ENTRADA.DT_CREATE, 'YYYY') < :ano                              ");
		sql.append("    AND (ENTRADA.STATUS_NOVO NOT IN (:devolucao, :distribuicaoCoacAnalise)     ");
		sql.append("      OR ENTRADA.STATUS_NOVO IS NULL)                                          ");
		
		// AQUI BUSCO SÓ OS SEM REFERENCIA
		sql.append("     AND ENTRADA.NUMERO_REFERENCIA IS NULL                                      ");
		
		
		
		// ENTRADA SEM SAÍDA POSTERIOR FIXME COMO SERÁ QUANDO NAO HOUVER SAIDA NENHUM
		sql.append("      AND ((ENTRADA.ID > SAIDA.SAIDA_ID) OR (SAIDA.SAIDA_ID IS NULL))          ");
		
		
		
		sql.append("  GROUP BY ENTRADA.NUMERO                                                      ");
		
		sql.append("   )                                                                           ");
		
	}

	private void sqlPassivoSemReferencia(StringBuilder sql) {
		// SOMENTE OS SEM REFERENCIA
        sql.append("   AND A.ID IN (                                                    ");
		
		sql.append(" SELECT MAX(ENTRADA.ID) AS ENTRADA_ID                              ");
		sql.append("   FROM CAD_AUDITORIA ENTRADA                                      "); 
		
		// LEFT JOIN
		sql.append("  LEFT JOIN (                                                      ");
		sql.append(" SELECT MAX(SAIDA.ID) AS SAIDA_ID, SAIDA.NUMERO_REFERENCIA         ");
		sql.append("   FROM CAD_AUDITORIA SAIDA                                        ");
		sql.append("  WHERE SAIDA.MODULO = :modulo                                     ");
		sql.append("    AND (SAIDA.EVENTO IN (:eventoDistribuicao, :eventoTramitacao)  ");		
		sql.append("    OR (SAIDA.EVENTO = :eventoAlteracaoStatus                      ");
		sql.append("    AND SAIDA.STATUS_NOVO = :assinaturaAdReferendum)               ");		
		sql.append("    OR (SAIDA.EVENTO = :eventoAlteracaoStatus                      ");
		sql.append("    AND SAIDA.STATUS_NOVO = :assinarOficio)  )                     ");	
		
	    sql.append("    AND SAIDA.ID_PESSOA_ACAO = :idAnalista                         ");
	  	sql.append("    AND TO_CHAR(SAIDA.DT_CREATE, 'YYYY') < :ano                    ");
	    
		sql.append("  GROUP BY SAIDA.NUMERO_REFERENCIA                                 ");
		
		sql.append("  ) SAIDA ON (SAIDA.NUMERO_REFERENCIA = ENTRADA.NUMERO_REFERENCIA) ");
		// FIM LEFT JOIN
		
		
		sql.append("  WHERE ENTRADA.MODULO = :modulo                                               ");
		sql.append("    AND ENTRADA.EVENTO IN (:eventoDistribuicao, :eventoTramitacao)             ");
		sql.append("    AND ENTRADA.ID_USUARIO_DESTINATARIO = :idAnalista                          ");
		sql.append("    AND TO_CHAR(ENTRADA.DT_CREATE, 'YYYY') < :ano                              ");
		sql.append("    AND (ENTRADA.STATUS_NOVO NOT IN (:devolucao, :distribuicaoCoacAnalise)     ");
		sql.append("      OR ENTRADA.STATUS_NOVO IS NULL)                                          ");
		
		// AQUI BUSCO SÓ OS SEM REFERENCIA
		sql.append("     AND ENTRADA.NUMERO_REFERENCIA IS NOT NULL                                 ");
		
		// MAIOR ENTRADA É MAIOR QUE MAIOR SAÍDA OU ENTRADA SEM SAÍDA
		sql.append("      AND ((ENTRADA.ID > SAIDA.SAIDA_ID) OR (SAIDA.SAIDA_ID IS NULL))          ");
		
		sql.append("  GROUP BY ENTRADA.NUMERO                                                      ");
		sql.append("   )                                                                           ");
		
	}
	
	public RelDetalhadoSiacol06Dto getSaidaAnalistaDistribuicaoTramitacaoOuAdReferendumOuCriacaoOficio(PesquisaRelatorioSiacolDto pesquisa, String mes, RelDetalhadoSiacol06Dto entrada, List<Long> idsAssuntoSiacol) {
		
		RelDetalhadoSiacol06Dto saida = new RelDetalhadoSiacol06Dto();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO, A.NUMERO_REFERENCIA, A.DT_CREATE       ");
		sql.append("   FROM CAD_AUDITORIA A                                        ");
		sql.append("  WHERE A.MODULO = :modulo                                     ");
		sql.append("    AND (A.EVENTO IN (:eventoDistribuicao, :eventoTramite)     ");		
		sql.append("    OR (A.EVENTO = :eventoAlteracaoStatus                      ");
		sql.append("    AND A.STATUS_NOVO = :assinaturaAdReferendum)               ");		
		sql.append("    OR (A.EVENTO = :eventoAlteracaoStatus                      ");
		sql.append("    AND A.STATUS_NOVO = :assinarOficio)  )                     ");	
		
	    sql.append("    AND A.ID_PESSOA_ACAO = :idAnalista                         ");
	    if (entrada.temNumeroProtocoloReferencia()) {
	    	sql.append("    AND A.NUMERO_REFERENCIA = :numeroReferencia            ");
	    } else {
	    	sql.append("    AND A.NUMERO = :numero                                 ");
	    }
    	sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                    ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') = :mes                      ");
	    queryAssunto(sql);
		sql.append("    AND A.ID > :idEntrada                                      ");
		sql.append("    AND ROWNUM = 1                                             ");
		sql.append("  ORDER BY A.ID                                                ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
			query.setParameter("eventoTramite", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("assinaturaAdReferendum", StatusProtocoloSiacol.ASSINATURA_AD_REFERENDUM.toString());
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("assinarOficio", StatusProtocoloSiacol.ASSINAR_OFICIO.toString());
			query.setParameter("idAnalista", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("idEntrada", entrada.getIdAuditoria());
			query.setParameter("idsAssunto", idsAssuntoSiacol);
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("mes", mes);
			if (entrada.temNumeroProtocoloReferencia()) {
				query.setParameter("numeroReferencia", entrada.getNumeroProtocoloReferencia());
			} else {
				query.setParameter("numero", entrada.getNumeroProtocolo());
			}
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					saida.setIdAuditoria(result[0] != null ? result[0].toString() : null);
					saida.setNumeroProtocolo(result[1] != null ? result[1].toString() : null);
					saida.setNumeroProtocoloReferencia(result[2] != null ? result[2].toString() : null);
					saida.setData(result[3] != null ? DateUtils.format((Date) result[3], DateUtils.DD_MM_YYYY_HH_MM) : null);
					
					return saida;
				}
			}
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol06Dao || getSaidaAnalistaDistribuicaoOuTramitacao", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return null;	
	}
	
	public RelDetalhadoSiacol06Dto getPausadoAnalista(PesquisaRelatorioSiacolDto pesquisa, String mes, RelDetalhadoSiacol06Dto entrada, List<Long> idsAssuntoSiacol) {

		RelDetalhadoSiacol06Dto pausa = new RelDetalhadoSiacol06Dto();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO, A.NUMERO_REFERENCIA, A.DT_CREATE    ");
		sql.append("   FROM CAD_AUDITORIA A                                ");
		sql.append("  WHERE A.MODULO = :modulo                             ");
		sql.append("    AND A.EVENTO = :eventoPausa                        ");
	    sql.append("    AND A.ID_USUARIO = :idAnalista                     ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano            ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') = :mes              ");
		if (entrada.temNumeroProtocoloReferencia()) {
	    	sql.append("    AND A.NUMERO_REFERENCIA = :numeroReferencia    ");
	    } else {
	    	sql.append("    AND A.NUMERO = :numero                         ");
	    }
		queryAssunto(sql);
		sql.append("    AND A.ID > :idEntrada                              ");
		sql.append("    AND ROWNUM = 1                                     ");
		sql.append("  ORDER BY A.ID                                        ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("eventoPausa", TipoEventoAuditoria.SIACOL_PAUSAR_PROTOCOLO.getId());
			query.setParameter("idAnalista", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("mes", mes);
			query.setParameter("idEntrada", entrada.getIdAuditoria());
			query.setParameter("idsAssunto", idsAssuntoSiacol);
			if (entrada.temNumeroProtocoloReferencia()) {
				query.setParameter("numeroReferencia", entrada.getNumeroProtocoloReferencia());
			} else {
				query.setParameter("numero", entrada.getNumeroProtocolo());
			}
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					pausa.setIdAuditoria(result[0] != null ? result[0].toString() : null);
					pausa.setNumeroProtocolo(result[1] != null ? result[1].toString() : null);
					pausa.setNumeroProtocoloReferencia(result[2] != null ? result[2].toString() : null);
					pausa.setData(result[3] != null ? DateUtils.format((Date) result[3], DateUtils.DD_MM_YYYY_HH_MM) : null);
					
					return pausa;
				}
			}
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol06Dao || getPausadoAnalista", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return null;
	}
	
	public List<RelDetalhadoSiacol06Dto> getRetornoAnalista(PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {

		List<RelDetalhadoSiacol06Dto> lista = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO, A.NUMERO_REFERENCIA, A.DT_CREATE        ");
		sql.append("   FROM CAD_AUDITORIA A                                         ");
		sql.append("  WHERE A.MODULO = :modulo                                      ");
		sql.append("    AND A.EVENTO = :eventoAlteracaoStatus                       ");
	    sql.append("    AND A.ID_PESSOA_ACAO = :idAnalista                          ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                     ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') = :mes                       ");
		sql.append("    AND A.STATUS_NOVO IN (:devolucao, :distribuicaoCoacAnalise) ");
		queryAssunto(sql);
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("devolucao", StatusProtocoloSiacol.DEVOLUCAO.toString());
			query.setParameter("distribuicaoCoacAnalise", StatusProtocoloSiacol.DISTRIBUICAO_COORD_COAC_PARA_ANALISE.toString());
			query.setParameter("idAnalista", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("mes", mes);
			query.setParameter("idsAssunto", idsAssuntoSiacol);
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol06Dto dto = new RelDetalhadoSiacol06Dto();
					
					dto.setIdAuditoria(result[0] != null ? result[0].toString() : null);
					dto.setNumeroProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setNumeroProtocoloReferencia(result[2] != null ? result[2].toString() : null);
					dto.setData(result[3] != null ? DateUtils.format((Date) result[3], DateUtils.DD_MM_YYYY_HH_MM) : null);
					
					lista.add(dto);
				}
			}
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol06Dao || getRetornoAnalista", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;	
	}
	
	public List<RelDetalhadoSiacol06Dto> getEntradaConselheiroDistribuicao(PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		
		List<RelDetalhadoSiacol06Dto> lista = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO, A.NUMERO_REFERENCIA, A.DT_CREATE ");
		sql.append("   FROM CAD_AUDITORIA A                             ");
		sql.append("  WHERE A.MODULO = :modulo                          ");
		sql.append("    AND A.EVENTO = :eventoDistribuicao              ");
		sql.append("    AND A.ID_USUARIO_DESTINATARIO = :idConselheiro  ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano         ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') = :mes           ");
		queryAssunto(sql);
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
			query.setParameter("idConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("mes", mes);
			query.setParameter("idsAssunto", idsAssuntoSiacol);
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol06Dto dto = new RelDetalhadoSiacol06Dto();
					
					dto.setIdAuditoria(result[0] != null ? result[0].toString() : null);
					dto.setNumeroProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setNumeroProtocoloReferencia(result[2] != null ? result[2].toString() : null);
					dto.setData(result[3] != null ? DateUtils.format((Date) result[3], DateUtils.DD_MM_YYYY_HH_MM) : null);
					
					lista.add(dto);
				}
			}
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol06Dao || getEntradaConselheiroDistribuicao", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;	
	}
	
	public RelDetalhadoSiacol06Dto getSaidaConselheiroImpedimentoOuAssinaturaRelatorioVoto(PesquisaRelatorioSiacolDto pesquisa, String mes, RelDetalhadoSiacol06Dto entrada, List<Long> idsAssuntoSiacol) {
	
		String[] status = new String[]{
				StatusProtocoloSiacol.A_PAUTAR.toString(), 
				StatusProtocoloSiacol.A_PAUTAR_PRESENCIAL.toString(), 
				StatusProtocoloSiacol.A_PAUTAR_SEM_QUORUM.toString(), 
				StatusProtocoloSiacol.A_PAUTAR_PROVISORIO.toString(), 
				StatusProtocoloSiacol.A_PAUTAR_VISTAS.toString(), 
				StatusProtocoloSiacol.A_PAUTAR_DESTAQUE.toString(), 
				StatusProtocoloSiacol.A_PAUTAR_AD_REFERENDUM.toString()};
		
		RelDetalhadoSiacol06Dto saida = new RelDetalhadoSiacol06Dto();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO, A.NUMERO_REFERENCIA, A.DT_CREATE       ");
		sql.append("   FROM CAD_AUDITORIA A                                        ");
		sql.append("  WHERE A.MODULO = :modulo                                     ");
		sql.append("    AND A.ID_USUARIO_DESTINATARIO = :idConselheiro             ");
		sql.append("    AND ( (A.EVENTO = :eventoImpedimento)                      ");
		sql.append("       OR (A.EVENTO = :eventoAlteracaoStatus                   ");
		sql.append("       AND A.STATUS_NOVO IN (:status)) )                       ");
		if (entrada.temNumeroProtocoloReferencia()) {
	    	sql.append("    AND A.NUMERO_REFERENCIA = :numeroReferencia            ");
	    } else {
	    	sql.append("    AND A.NUMERO = :numero                                 ");
	    }
    	sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                    ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') = :mes                      ");
	    queryAssunto(sql);
		sql.append("    AND A.ID > :idEntrada                                      ");
		sql.append("    AND ROWNUM = 1                                             ");
		sql.append("  ORDER BY A.ID                                                ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("eventoImpedimento", TipoEventoAuditoria.CONSELHEIRO_IMPEDIDO.getId());
			query.setParameter("eventoAlteracaoStatus", TipoEventoAuditoria.ALTERA_STATUS_PROTOCOLO.getId());
			query.setParameter("status", status);
			query.setParameter("idConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("idsAssunto", idsAssuntoSiacol);
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("mes", mes);
			if (entrada.temNumeroProtocoloReferencia()) {
				query.setParameter("numeroReferencia", entrada.getNumeroProtocoloReferencia());
			} else {
				query.setParameter("numero", entrada.getNumeroProtocolo());
			}
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					saida.setIdAuditoria(result[0] != null ? result[0].toString() : null);
					saida.setNumeroProtocolo(result[1] != null ? result[1].toString() : null);
					saida.setNumeroProtocoloReferencia(result[2] != null ? result[2].toString() : null);
					saida.setData(result[3] != null ? DateUtils.format((Date) result[3], DateUtils.DD_MM_YYYY_HH_MM) : null);
					
					return saida;
				}
			}
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol06Dao || getSaidaConselheiroImpedimentoOuAssinaturaRelatorioVoto", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return null;	
	}

	public List<RelDetalhadoSiacol06Dto> getPassivoConselheiro(PesquisaRelatorioSiacolDto pesquisa, String mes) {
		
		List<RelDetalhadoSiacol06Dto> lista = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO, A.NUMERO_REFERENCIA, A.DT_CREATE       ");
		sql.append("   FROM CAD_AUDITORIA A                                        ");
		sql.append("  WHERE A.MODULO = :modulo                                     ");
		sql.append("    AND A.EVENTO IN (:eventoDistribuicao, :eventoTramitacao)   ");
		sql.append("    AND A.ID_USUARIO_DESTINATARIO = :idConselheiro             ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                    ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') = :mes                      ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
			query.setParameter("eventoTramitacao", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("idConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("mes", mes);
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol06Dto dto = new RelDetalhadoSiacol06Dto();
					
					dto.setIdAuditoria(result[0] != null ? result[0].toString() : null);
					dto.setNumeroProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setNumeroProtocoloReferencia(result[2] != null ? result[2].toString() : null);
					dto.setData(result[3] != null ? DateUtils.format((Date) result[3], DateUtils.DD_MM_YYYY_HH_MM) : null);
					
					lista.add(dto);
				}
			}
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol06Dao || getPassivoConselheiro", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;	
	}

	public List<RelDetalhadoSiacol06Dto> getPassivoEntradaConselheiro(PesquisaRelatorioSiacolDto pesquisa, List<Long> idsAssuntoSiacol) {
		List<RelDetalhadoSiacol06Dto> lista = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.ID, A.NUMERO, A.NUMERO_REFERENCIA, A.DT_CREATE ");
		sql.append("   FROM CAD_AUDITORIA A                             ");
		sql.append("  WHERE A.MODULO = :modulo                          ");
		sql.append("    AND A.EVENTO = :eventoDistribuicao              ");
		sql.append("    AND A.ID_USUARIO_DESTINATARIO = :idConselheiro  ");
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') < :ano         ");
		queryAssunto(sql);
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			query.setParameter("eventoDistribuicao", TipoEventoAuditoria.SIACOL_DISTRIBUICAO_PROTOCOLO.getId());
			query.setParameter("idConselheiro", pesquisa.getResponsaveis().get(0).getId());
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("idsAssunto", idsAssuntoSiacol);
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol06Dto dto = new RelDetalhadoSiacol06Dto();
					
					dto.setIdAuditoria(result[0] != null ? result[0].toString() : null);
					dto.setNumeroProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setNumeroProtocoloReferencia(result[2] != null ? result[2].toString() : null);
					dto.setData(result[3] != null ? DateUtils.format((Date) result[3], DateUtils.DD_MM_YYYY_HH_MM) : null);
					
					lista.add(dto);
				}
			}
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol06Dao || getPassivoEntradaConselheiro", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;	
	}
	
	private void queryAssunto(StringBuilder sql) {
		sql.append("     AND A.NUMERO IN (                             ");
		sql.append("  SELECT P.NO_PROTOCOLO FROM SIACOL_PROTOCOLOS P   ");
		sql.append("   WHERE (P.FK_ASSUNTO_SIACOL IN (:idsAssunto)     ");
		sql.append("       OR P.FK_ASSUNTO_SIACOL IS NULL))            ");
	}
}
