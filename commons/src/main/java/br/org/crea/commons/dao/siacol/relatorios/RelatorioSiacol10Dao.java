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
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol10Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol10PesoDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol10Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	
	public RelatorioSiacol10Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}

	public List<RelDetalhadoSiacol10Dto> getEntradaAnalistaDistribuicaoOuTramitacao(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, String tipo) {
		
		
		String idsDepartamentos = getIdsDepartamentosPorTipo(tipo);
		
		List<RelDetalhadoSiacol10Dto> lista = new ArrayList<RelDetalhadoSiacol10Dto>();
		
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
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') IN (:mes)                             ");
		sql.append("    AND (A.STATUS_NOVO NOT IN (:devolucao, :distribuicaoCoacAnalise)     ");
		sql.append("      OR A.STATUS_NOVO IS NULL)                                          ");
		sql.append("    AND (P.FK_ASSUNTO_SIACOL IN (:idsAssunto)                            ");
		sql.append("      OR P.FK_ASSUNTO_SIACOL IS NULL)                                    ");
		if (!idsDepartamentos.isEmpty()) {
			sql.append("    AND P.FK_DEPARTAMENTO LIKE (:idsDepartamentos)                   ");
		}
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
			query.setParameter("mes", pesquisa.getMeses());
			query.setParameter("idsAssunto", idAssuntoSiacol);
			if (!idsDepartamentos.isEmpty()) {
				query.setParameter("idsDepartamentos", idsDepartamentos + "%");
			}
			
			lista = populaResultList(query, lista);
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol10Dao || getEntradaAnalistaDistribuicaoOuTramitacao", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;	
	}

	private String getIdsDepartamentosPorTipo(String tipo) {
		
		if (tipo.contains("CAMARA")) {
			return "12";
		} else if (tipo.contains("COMISSAO")) {
			return "13";
		} else if (tipo.contains("PLENARIA")) {
			return "11";
		}
		return "10";
	}

	public RelSiacol10PesoDto obtemPeso(String codigoAssunto) {

		RelSiacol10PesoDto peso = new RelSiacol10PesoDto();
		
		if (codigoAssunto != null) {
			
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  A.INSTRUCAO AS INSTRUCAO,   ");
			sql.append("         A.CAMARA AS CAMARA,         ");
			sql.append("         A.COMISSAO AS COMISSAO,     ");
			sql.append("         A.PLENARIA AS PLENARIA      ");
			sql.append("   FROM SIACOL_ASSUNTO A             ");
			sql.append("  WHERE A.CODIGO = :codigoAssunto    ");
			sql.append("    AND A.ATIVO = 1                  ");
			
			try {
				Query query = em.createNativeQuery(sql.toString());
				query.setParameter("codigoAssunto", codigoAssunto);
				
				Object[] resultado = (Object[]) query.getSingleResult();
				
				BigDecimal pesoInstrucao = (BigDecimal) resultado[0];
				BigDecimal pesoCamara = (BigDecimal) resultado[1];
				BigDecimal pesoComissao = (BigDecimal) resultado[2];
				BigDecimal pesoPlenaria = (BigDecimal) resultado[3];
				
				
				peso.setPesoInstrucao(pesoInstrucao != null ? pesoInstrucao.doubleValue() : 0d);
				peso.setPesoCamara(pesoCamara != null ? pesoCamara.doubleValue() : 0d);
				peso.setPesoComissao(pesoComissao != null ? pesoComissao.doubleValue() : 0d);
				peso.setPesoPlenaria(pesoPlenaria != null ? pesoPlenaria.doubleValue() : 0d);
				
			} catch (NoResultException e) {
				return peso;
			} catch (Throwable e) {
				httpGoApi.geraLog("RelatorioSiacol10Dao || obtemPeso", StringUtil.convertObjectToJson(codigoAssunto), e);
			}
		} else {
			peso.setPesoInstrucao(1d);
			peso.setPesoCamara(1d);
			peso.setPesoComissao(1d);
			peso.setPesoPlenaria(1d);
		}
		
		return peso;
	}
		
	private List<RelDetalhadoSiacol10Dto> populaResultList(Query query, List<RelDetalhadoSiacol10Dto> lista) {
		Iterator<?> it = query.getResultList().iterator();

		if (query.getResultList().size() > 0) {
			while (it.hasNext()) {

				Object[] result = (Object[]) it.next();
				
				RelDetalhadoSiacol10Dto dto = new RelDetalhadoSiacol10Dto();
				
				dto.setIdAuditoria(result[0] != null ? result[0].toString() : null);
				dto.setNumeroProtocolo(result[1] != null ? result[1].toString() : null);
				dto.setNumeroProtocoloReferencia(result[2] != null ? result[2].toString() : null);
				
				dto.setIdDepartamento(result[3] != null ? result[3].toString() : null);
				dto.setNomeDepartamento(result[4] != null ? result[4].toString() : null);
				dto.setCodigoAssunto(result[5] != null ? result[5].toString() : null);
				dto.setNomeAssunto(result[6] != null ? result[6].toString() : null);
				dto.setData(result[7] != null ? DateUtils.format((Date) result[7], DateUtils.DD_MM_YYYY_HH_MM) : null);
				
				dto.setPesoDto(obtemPeso(dto.getCodigoAssunto()));
				
				lista.add(dto);
			}
		}
		return lista;
	}
	
	public RelDetalhadoSiacol10Dto getSaidaAnalistaDistribuicaoTramitacaoOuAdReferendumOuCriacaoOficio(PesquisaRelatorioSiacolDto pesquisa, RelDetalhadoSiacol10Dto entrada, Long idAssuntoSiacol) {
		
		RelDetalhadoSiacol10Dto saida = new RelDetalhadoSiacol10Dto();
		
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
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') IN (:mes)                   ");
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
			query.setParameter("idsAssunto", idAssuntoSiacol);
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("mes", pesquisa.getMeses());
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
			httpGoApi.geraLog("RelatorioSiacol10Dao || getSaidaAnalistaDistribuicaoOuTramitacao", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return null;	
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
			httpGoApi.geraLog("RelatorioSiacol10Dao || getEntradaConselheiroDistribuicao", StringUtil.convertObjectToJson(pesquisa), e);
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
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'MM') IN (:mes)                   ");
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
			query.setParameter("mes", pesquisa.getMeses());
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
			httpGoApi.geraLog("RelatorioSiacol10Dao || getSaidaConselheiroImpedimentoOuAssinaturaRelatorioVoto", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return null;	
	}

	private void queryAssunto(StringBuilder sql) {
		sql.append("     AND A.NUMERO IN (                             ");
		sql.append("  SELECT P.NO_PROTOCOLO FROM SIACOL_PROTOCOLOS P   ");
		sql.append("   WHERE (P.FK_ASSUNTO_SIACOL IN (:idsAssunto)     ");
		sql.append("       OR P.FK_ASSUNTO_SIACOL IS NULL))            ");
	}

	
}
