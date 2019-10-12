package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.AssuntoConfea;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol02Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol02Dto;
import br.org.crea.commons.models.siacol.enuns.TipoDocumentoSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.EstatisticaUtil;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol02Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	DepartamentoDao departamentoDao;
	@Inject	DepartamentoConverter departamentoConverter;
	@Inject	AssuntoDao assuntoCorporativoDao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	@Inject	RelatorioSiacolCommonsDao commonsDao;
	
	public RelatorioSiacol02Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}

	public List<RelSiacol02Dto> quantidadeDeProtocolosJulgadosPorDepartamentoAssuntoEClassificacao(PesquisaRelatorioSiacolDto pesquisa) {
		boolean ehVirtual = true;
		List<Long> idsDepartamentos = commonsDao.populaListaIdsDepartamentos(pesquisa.getDepartamentos());
		
		List<RelSiacol02Dto> listaRelatorio = new ArrayList<RelSiacol02Dto>();
		
		if (pesquisa.isTodosAssuntos()) {
			pesquisa.setAssuntos(assuntoConverter.toListDtoSiacol(assuntoDao.getAll()));
		}
		
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		assuntos.sort(Comparator.comparing(AssuntoDto::getCodigo));
		
		for (AssuntoDto assunto : assuntos) {
			RelSiacol02Dto linhaAssunto = new RelSiacol02Dto();
			linhaAssunto.setAssunto(assunto.getCodigo() + " - " + assunto.getNome());
			linhaAssunto.setCodigoAssunto(assunto.getCodigo());
			linhaAssunto.setDescricaoAssunto(assunto.getNome());
			
			AssuntoConfea assuntoConfea = commonsDao.populaAssuntoConfeaPeloAssuntoSiacol(assunto.getCodigo());
			if (assuntoConfea.getCodigo() != null) linhaAssunto.setAssuntoConfea(assuntoConfea.getCodigo() + " - " + assuntoConfea.getNome());
			linhaAssunto.setCodigoAssuntoConfea(assuntoConfea.getCodigo());
			linhaAssunto.setDescricaoAssuntoConfea(assuntoConfea.getNome());
			
			qtdConcedido(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, TipoDocumentoSiacolEnum.DESPACHO_PROVISORIO.getId());
			qtdAprovado(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, TipoDocumentoSiacolEnum.DESPACHO_PROVISORIO.getId());
			
			qtdConcedido(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, TipoDocumentoSiacolEnum.DECISAO_AD_REFERENDUM.getId());
			qtdAprovado(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, TipoDocumentoSiacolEnum.DECISAO_AD_REFERENDUM.getId());
			linhaAssunto.setQtdTotalADeRP(linhaAssunto.getQtdAprovadoRegistroProvisorio() + linhaAssunto.getQtdAprovadoAdReferendum());
			qtdReuniao(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, ehVirtual);
			qtdReuniao(linhaAssunto, pesquisa, assunto.getId(), idsDepartamentos, !ehVirtual);
			linhaAssunto.setQtdNaoClassificado(commonsDao.qtdNaoClassificado(pesquisa));
			linhaAssunto.setQtdTotalReunioes(linhaAssunto.getQtdReuniaoPresencial() + linhaAssunto.getQtdReuniaoVirtual());
			
			listaRelatorio.add(linhaAssunto);
		}
		
		listaRelatorio.add(linhaComTotais(listaRelatorio));
		
		return listaRelatorio;
	}

	public void qtdConcedido(RelSiacol02Dto linhaAssunto, PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, List<Long> idsDepartamentos, Long tipoDocumento) {
		
		List<RelDetalhadoSiacol02Dto> lista = new ArrayList<RelDetalhadoSiacol02Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT COUNT(P.ID), P.NO_PROTOCOLO, D.SIGLA, P.FK_ASSUNTO, A.DESCRICAO   ");
		sql.append(" 	FROM SIACOL_PROTOCOLOS P                                               ");
		sql.append("    LEFT JOIN CAD_DOCUMENTO C ON (C.NUMERO_PROTOCOLO = P.NO_PROTOCOLO)     ");
		sql.append("    LEFT JOIN PRT_DEPARTAMENTOS D ON (D.ID = P.FK_DEPARTAMENTO)            ");
		sql.append("    LEFT JOIN PRT_ASSUNTOS A ON (A.ID = P.FK_ASSUNTO)                      ");
		sql.append("   WHERE TO_CHAR(C.DT_UPDATE, 'YYYY') = :ano                               ");
		sql.append(" 	 AND P.FK_ASSUNTO_SIACOL = :idAssuntoSiacol                            ");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append(" 	  AND P.FK_DEPARTAMENTO IN (:idsDepartamentos)                     ");
		}
		if (pesquisa.temMeses()) {
			sql.append("  AND TO_CHAR(C.DT_UPDATE, 'MM') IN (:meses)                           ");
		}
		sql.append("      AND C.FK_TIPO_DOCUMENTO = :tipoDocumento                             ");
		sql.append("      AND C.ASSINADO = 1                                                   ");
		sql.append("    GROUP BY P.NO_PROTOCOLO, D.SIGLA, P.FK_ASSUNTO, A.DESCRICAO            ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoDocumento", tipoDocumento);
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idsDepartamentos", idsDepartamentos);
			}
			query.setParameter("idAssuntoSiacol", idAssuntoSiacol);
			query.setParameter("ano", pesquisa.getAno());
			if (pesquisa.temMeses()) {
				query.setParameter("meses", pesquisa.getMeses());
			}
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol02Dto dto = new RelDetalhadoSiacol02Dto();
					
					dto.setQtd(Integer.parseInt(result[0].toString()));
					dto.setProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setDepartamento(result[2] != null ? result[2].toString() : null);
					dto.setCodigoAssuntoCorporativo(result[3] != null ? result[3].toString() : null);
					dto.setAssuntoCorporativo(result[4] != null ? result[4].toString() : null);
					
					lista.add(dto);
				}
			}
			
			int soma = EstatisticaUtil.soma(lista.stream().map(RelDetalhadoSiacol02Dto::getQtd));
			if (tipoDocumento.equals(1110L)) {
				linhaAssunto.setQtdConcedidoRegistroProvisorio(soma);
				linhaAssunto.setProtocolosConcedidoProvisorio(lista);
			} 
			if (tipoDocumento.equals(1112L)) {
				linhaAssunto.setQtdConcedidoAdReferendum(soma);
				linhaAssunto.setProtocolosConcedidoAdReferendum(lista);
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol02Dao || getConcedido", StringUtil.convertObjectToJson(pesquisa), e);
		}
	}
	
	public void qtdAprovado(RelSiacol02Dto linhaAssunto, PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, List<Long> idsDepartamentos, Long tipoDocumento) {
		
		List<RelDetalhadoSiacol02Dto> lista = new ArrayList<RelDetalhadoSiacol02Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.ID), P.NO_PROTOCOLO, D.SIGLA, P.FK_ASSUNTO, A.DESCRICAO    ");
		sql.append("   FROM SIACOL_PROTOCOLOS P                                                ");
		sql.append("   LEFT JOIN CAD_DOCUMENTO C ON (C.NUMERO_PROTOCOLO = P.NO_PROTOCOLO)      ");
		sql.append("   LEFT JOIN PRT_DEPARTAMENTOS D ON (D.ID = C.FK_DEPARTAMENTO)             ");
		sql.append("   LEFT JOIN PRT_ASSUNTOS A ON (A.ID = P.FK_ASSUNTO)                       ");
		sql.append("  WHERE C.FK_TIPO_DOCUMENTO = :documentoDecisao                            ");
		sql.append("    AND C.NUMERO_DOCUMENTO IS NOT NULL                                     ");
		queryFiltraDataJulgamento(sql, pesquisa);
		sql.append("    AND P.ID IN (SELECT P.ID                                               ");
		sql.append("   FROM SIACOL_PROTOCOLOS P                                                ");
		sql.append("   LEFT JOIN CAD_DOCUMENTO C ON (C.NUMERO_PROTOCOLO = P.NO_PROTOCOLO)      ");
		sql.append("  WHERE P.FK_ASSUNTO_SIACOL = :idAssuntoSiacol                             ");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append(" AND C.FK_DEPARTAMENTO IN (:idsDepartamentos)                          ");
		}
		sql.append("       AND C.FK_TIPO_DOCUMENTO = :tipoDocumento                            ");
		sql.append("       AND C.ASSINADO = 1)                                                 ");
		sql.append("    GROUP BY P.NO_PROTOCOLO, D.SIGLA, P.FK_ASSUNTO, A.DESCRICAO            ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			query.setParameter("tipoDocumento", tipoDocumento);
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idsDepartamentos", idsDepartamentos);
			}
			query.setParameter("idAssuntoSiacol", idAssuntoSiacol);
			query.setParameter("ano", pesquisa.getAno());
			if (pesquisa.temMeses()) {
				query.setParameter("meses", pesquisa.getMeses());
			}
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol02Dto dto = new RelDetalhadoSiacol02Dto();
					
					dto.setQtd(Integer.parseInt(result[0].toString()));
					dto.setProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setDepartamento(result[2] != null ? result[2].toString() : null);
					dto.setCodigoAssuntoCorporativo(result[3] != null ? result[3].toString() : null);
					dto.setAssuntoCorporativo(result[4] != null ? result[4].toString() : null);
					
					lista.add(dto);
				}
			}
			
			int soma = EstatisticaUtil.soma(lista.stream().map(RelDetalhadoSiacol02Dto::getQtd));
			if (tipoDocumento.equals(1110L)) {
				linhaAssunto.setQtdAprovadoRegistroProvisorio(soma);
				linhaAssunto.setProtocolosAprovadoProvisorio(lista);
			} 
			if (tipoDocumento.equals(1112L)) {
				linhaAssunto.setQtdAprovadoAdReferendum(soma);
				linhaAssunto.setProtocolosAprovadoAdReferendum(lista);
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol02Dao || getAprovado", StringUtil.convertObjectToJson(pesquisa), e);
		}
	}
	
	public void qtdReuniao(RelSiacol02Dto linhaAssunto, PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, List<Long> idsDepartamentos, boolean ehVirtual) {
		
		List<RelDetalhadoSiacol02Dto> lista = new ArrayList<RelDetalhadoSiacol02Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.ID), P.NO_PROTOCOLO, D.SIGLA, P.FK_ASSUNTO, A.DESCRICAO                        ");
		sql.append("   FROM SIACOL_PROTOCOLOS P                                                                    ");
		sql.append("   LEFT JOIN CAD_DOCUMENTO C ON (C.NUMERO_PROTOCOLO = P.NO_PROTOCOLO)                          ");
		sql.append("   LEFT JOIN PRT_DEPARTAMENTOS D ON (D.ID = C.FK_DEPARTAMENTO)                                 ");
		sql.append("   LEFT JOIN PRT_ASSUNTOS A ON (A.ID = P.FK_ASSUNTO)                                           ");
		sql.append(" 	WHERE P.ID IN(                                                                             ");
		sql.append(" 		SELECT RL.FK_PROTOCOLO FROM SIACOL_DOCUMENTO_PROTOCOLO RL WHERE RL.FK_DOCUMENTO IN(    ");
		sql.append(" 		SELECT R.FK_DOCUMENTO FROM SIACOL_REUNIAO R                                            ");
		sql.append(" 	     WHERE R.VIRTUAL = :ehVirtual                                                          ");
		sql.append("           AND TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                                ");
		if (pesquisa.temMeses()) {
			sql.append("  AND TO_CHAR(R.HR_FIM, 'MM') IN :meses                          					   ");
		}
		sql.append(" 	)                                                                                         ");
		sql.append(" 	OR RL.FK_DOCUMENTO IN (SELECT R.FK_EXTRA_PAUTA FROM SIACOL_REUNIAO R                       ");
		sql.append("         WHERE R.VIRTUAL = :ehVirtual                                                          ");
		sql.append("           AND TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                                ");
		if (pesquisa.temMeses()) {
			sql.append("  AND TO_CHAR(R.HR_FIM, 'MM') IN :meses                         					   ");
		}
		sql.append(" 	))                                                                                         ");
		sql.append(" 	AND P.FK_ASSUNTO_SIACOL IN (:idAssuntoSiacol)                                              ");
		sql.append("    AND C.NUMERO_DOCUMENTO IS NOT NULL                                                         ");
		sql.append("    AND C.FK_TIPO_DOCUMENTO = :documentoDecisao                                                ");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append(" 	  AND C.FK_DEPARTAMENTO IN (:idsDepartamentos)                                         ");
		}
		queryFiltro(sql, pesquisa);
		sql.append("    GROUP BY P.NO_PROTOCOLO, D.SIGLA, P.FK_ASSUNTO, A.DESCRICAO                                ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("ehVirtual", ehVirtual);
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idsDepartamentos", idsDepartamentos);
			}
			query.setParameter("idAssuntoSiacol", idAssuntoSiacol);
			query.setParameter("ano", pesquisa.getAno());
			if (pesquisa.temMeses()) {
				query.setParameter("meses", pesquisa.getMeses());
			}
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol02Dto dto = new RelDetalhadoSiacol02Dto();
					
					dto.setQtd(Integer.parseInt(result[0].toString()));
					dto.setProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setDepartamento(result[2] != null ? result[2].toString() : null);
					dto.setCodigoAssuntoCorporativo(result[3] != null ? result[3].toString() : null);
					dto.setAssuntoCorporativo(result[4] != null ? result[4].toString() : null);
					
					lista.add(dto);
				}
			}
			
			if (ehVirtual) {
				linhaAssunto.setQtdReuniaoVirtual(EstatisticaUtil.soma(lista.stream().map(RelDetalhadoSiacol02Dto::getQtd)));
				linhaAssunto.setProtocolosReuniaoVirtual(lista);
			} else {
				linhaAssunto.setQtdReuniaoPresencial(EstatisticaUtil.soma(lista.stream().map(RelDetalhadoSiacol02Dto::getQtd)));
				linhaAssunto.setProtocolosReuniaoPresencial(lista);
			}

		} catch (NoResultException e) {
			System.out.println("sem resultado");
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol02Dao || rel02Reuniao", StringUtil.convertObjectToJson(pesquisa), e);
		}
	}
	
	public void queryFiltro(StringBuilder sql, PesquisaRelatorioSiacolDto pesquisa) {
		
		if (pesquisa.isTodasClassificacoes()) {
			sql.append(" AND ( ");
			sql.append("      (P.CLASSIFICACAO = 'FAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'FAVORAVEL')        ");
			sql.append("   OR (P.CLASSIFICACAO = 'DESFAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'DESFAVORAVEL')  ");
			sql.append("   OR (P.CLASSIFICACAO = 'FAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'DESFAVORAVEL')     ");
			sql.append("   OR (P.CLASSIFICACAO = 'DESFAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'FAVORAVEL')     ");
			sql.append("     ) ");
		} else {
			List<GenericDto> filtros = pesquisa.getClassificacao();
			if (!filtros.isEmpty()) {
				sql.append("AND ( ");
				for (GenericDto s : filtros) {
					if (filtros.indexOf(s) == 0) {
						if (s.getTipo().equals("FAVORAVEL_APROVADO")) {
							sql.append("   (P.CLASSIFICACAO = 'FAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'FAVORAVEL')        ");
						} else if (s.getTipo().equals("DESFAVORAVEL_APROVADO")) {
							sql.append("   (P.CLASSIFICACAO = 'DESFAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'DESFAVORAVEL')  ");
						} else if (s.getTipo().equals("FAVORAVEL_REPROVADO")) {
							sql.append("   (P.CLASSIFICACAO = 'FAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'DESFAVORAVEL')     ");
						} else if (s.getTipo().equals("DESFAVORAVEL_REPROVADO")) {
							sql.append("   (P.CLASSIFICACAO = 'DESFAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'FAVORAVEL')     ");
						}
					} else{
						if (s.getTipo().equals("FAVORAVEL_APROVADO")) {
							sql.append("   OR (P.CLASSIFICACAO = 'FAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'FAVORAVEL')        ");
						} else if (s.getTipo().equals("DESFAVORAVEL_APROVADO")) {
							sql.append("   OR (P.CLASSIFICACAO = 'DESFAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'DESFAVORAVEL')  ");
						} else if (s.getTipo().equals("FAVORAVEL_REPROVADO")) {
							sql.append("   OR (P.CLASSIFICACAO = 'FAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'DESFAVORAVEL')     ");
						} else if (s.getTipo().equals("DESFAVORAVEL_REPROVADO")) {
							sql.append("   OR (P.CLASSIFICACAO = 'DESFAVORAVEL' AND P.CLASSIFICACAO_FINAL = 'FAVORAVEL')     ");
						}
					}
				}
				sql.append(" )");
			}
		}
		
	}
	
	private void queryFiltraDataJulgamento(StringBuilder sql, PesquisaRelatorioSiacolDto pesquisa) {
		sql.append("    AND P.ID IN(                                                                ");
		sql.append("	 SELECT RL.FK_PROTOCOLO FROM SIACOL_DOCUMENTO_PROTOCOLO RL                  ");
		sql.append("	  WHERE RL.FK_DOCUMENTO IN (SELECT R.FK_DOCUMENTO FROM SIACOL_REUNIAO R     ");
		sql.append("	  WHERE R.VIRTUAL = 0                                                       ");
		sql.append("	    AND TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                    ");
		if (pesquisa.temMeses()) {
			sql.append("	    AND TO_CHAR(R.HR_FIM, 'MM') = :mes                                  ");
		}
		sql.append("	   )                                                                        ");
		sql.append("	     OR RL.FK_DOCUMENTO IN (SELECT R.FK_EXTRA_PAUTA FROM SIACOL_REUNIAO R   ");
		sql.append("      WHERE R.VIRTUAL = 0                                                       ");
		sql.append("	    AND TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                    ");
		if (pesquisa.temMeses()) {
			sql.append("		AND TO_CHAR(R.HR_FIM, 'MM') = :mes                                  ");
		}
		sql.append("		))                                                                      ");
	}

	

	private RelSiacol02Dto linhaComTotais(List<RelSiacol02Dto> listaRelatorio) {
		RelSiacol02Dto linhaTotais = new RelSiacol02Dto();
		linhaTotais.setAssunto("TOTAL");
		linhaTotais.setQtdConcedidoRegistroProvisorio(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol02Dto::getQtdConcedidoRegistroProvisorio)));
		linhaTotais.setQtdAprovadoRegistroProvisorio(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol02Dto::getQtdAprovadoRegistroProvisorio)));
		linhaTotais.setQtdConcedidoAdReferendum(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol02Dto::getQtdConcedidoAdReferendum)));
		linhaTotais.setQtdAprovadoAdReferendum(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol02Dto::getQtdAprovadoAdReferendum)));
		linhaTotais.setQtdTotalADeRP(linhaTotais.getQtdAprovadoRegistroProvisorio() + linhaTotais.getQtdAprovadoAdReferendum());
		linhaTotais.setQtdReuniaoPresencial(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol02Dto::getQtdReuniaoPresencial)));
		linhaTotais.setQtdReuniaoVirtual(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol02Dto::getQtdReuniaoVirtual)));
		linhaTotais.setQtdTotalReunioes(linhaTotais.getQtdReuniaoPresencial() + linhaTotais.getQtdReuniaoVirtual());
		
		return linhaTotais;
	}

}
