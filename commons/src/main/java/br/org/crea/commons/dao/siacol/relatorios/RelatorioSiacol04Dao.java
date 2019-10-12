package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
import java.math.BigDecimal;
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
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.AssuntoConfea;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDepartamentosSiacol04Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol04Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol04Dto;
import br.org.crea.commons.models.siacol.enuns.TipoDocumentoSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.EstatisticaUtil;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol04Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	DepartamentoDao departamentoDao;
	@Inject	DepartamentoConverter departamentoConverter;
	@Inject	AssuntoDao assuntoCorporativoDao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	@Inject	RelatorioSiacolCommonsDao commonsDao;
	
	public RelatorioSiacol04Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public List<RelSiacol04Dto> quantidadeDeProtocolosJulgadosENaoJulgadosPorAssuntoEDepartamentosEPorMesAno(PesquisaRelatorioSiacolDto pesquisa) {

		List<DepartamentoDto> departamentos = pesquisa.getDepartamentos();
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		
		if (pesquisa.isTodosDepartamentos()) {
			departamentos = departamentoConverter.toListDto(departamentoDao.getAllDepartamentos("SIACOL")); 
		}
		
		if (pesquisa.isTodosAssuntos()) {
			assuntos = assuntoConverter.toListDtoSiacol(assuntoDao.getAll());
		}
		
		List<RelSiacol04Dto> listaRelatorio = new ArrayList<RelSiacol04Dto>();
		AssuntoDto semAssunto = new AssuntoDto();
		semAssunto.setId(0L);
		semAssunto.setCodigo(0L);
		semAssunto.setNome("SEM ASSUNTO SIACOL");
		assuntos.add(0, semAssunto);
		
		assuntos.sort(Comparator.comparing(AssuntoDto::getCodigo));
		
		for (AssuntoDto assunto : assuntos) {
			RelSiacol04Dto linhaAssunto = new RelSiacol04Dto();
			linhaAssunto.setAssunto(assunto.getCodigo() + " - " + assunto.getNome());
			linhaAssunto.setCodigoAssunto(assunto.getCodigo());
			linhaAssunto.setDescricaoAssunto(assunto.getNome());
			
			AssuntoConfea assuntoConfea = populaAssuntoConfeaPeloAssuntoSiacol(assunto.getCodigo());
			if (assuntoConfea.getCodigo() != null) linhaAssunto.setAssuntoConfea(assuntoConfea.getCodigo() + " - " + assuntoConfea.getNome());
			linhaAssunto.setCodigoAssuntoConfea(assuntoConfea.getCodigo());
			linhaAssunto.setDescricaoAssuntoConfea(assuntoConfea.getNome());
			
			List<RelDepartamentosSiacol04Dto> valoresDaLinhaAssunto = new ArrayList<RelDepartamentosSiacol04Dto>();
			for (DepartamentoDto departamento : departamentos) {
				RelDepartamentosSiacol04Dto valorDaCelulaDepartamentoxAssunto = new RelDepartamentosSiacol04Dto();
				valorDaCelulaDepartamentoxAssunto.setNome(departamento.getSigla());
				String total = "";
				
				if (pesquisa.filtroEhJulgados()) {
					
					List<RelDetalhadoSiacol04Dto> listaJulgados = getJulgadosPorDepartamentoEPorAssunto(pesquisa, departamento, assunto.getId());
					total = String.valueOf(EstatisticaUtil.soma(listaJulgados.stream().map(RelDetalhadoSiacol04Dto::getQtd)));
					valorDaCelulaDepartamentoxAssunto.setProtocolos(listaJulgados);
					valorDaCelulaDepartamentoxAssunto.setQtdJulgados(total);
					valorDaCelulaDepartamentoxAssunto.setQtd("0");
					valorDaCelulaDepartamentoxAssunto.setQtdNaoJulgados("0");
					
				} else if (pesquisa.filtroEhNaoJulgados()) {
					
					List<RelDetalhadoSiacol04Dto> listaNaoJulgados = getNaoJulgadosPorDepartamentoEPorAssunto(pesquisa, departamento, assunto.getId());
					total = String.valueOf(EstatisticaUtil.soma(listaNaoJulgados.stream().map(RelDetalhadoSiacol04Dto::getQtd)));
					valorDaCelulaDepartamentoxAssunto.setProtocolos(listaNaoJulgados);
					valorDaCelulaDepartamentoxAssunto.setQtdNaoJulgados(total);
					valorDaCelulaDepartamentoxAssunto.setQtdJulgados("0");
					valorDaCelulaDepartamentoxAssunto.setQtd("0");
					
				} else if (pesquisa.isMesclado()) {
					List<RelDetalhadoSiacol04Dto> lista = new ArrayList<RelDetalhadoSiacol04Dto>();
					List<RelDetalhadoSiacol04Dto> listaJulgados = getJulgadosPorDepartamentoEPorAssunto(pesquisa, departamento, assunto.getId());
					lista.addAll(listaJulgados);
					List<RelDetalhadoSiacol04Dto> listaNaoJulgados = getNaoJulgadosPorDepartamentoEPorAssunto(pesquisa, departamento, assunto.getId());
					lista.addAll(listaNaoJulgados);
					int qtdJulgado = EstatisticaUtil.soma(listaJulgados.stream().map(RelDetalhadoSiacol04Dto::getQtd));
					int qtdNaoJulgado = EstatisticaUtil.soma(listaNaoJulgados.stream().map(RelDetalhadoSiacol04Dto::getQtd));
					valorDaCelulaDepartamentoxAssunto.setQtdJulgados(String.valueOf(qtdJulgado));
					valorDaCelulaDepartamentoxAssunto.setQtdNaoJulgados(String.valueOf(qtdNaoJulgado));
					total = String.valueOf(qtdJulgado) + "/" + String.valueOf(qtdNaoJulgado);
					valorDaCelulaDepartamentoxAssunto.setProtocolos(lista);
				}
				
				valorDaCelulaDepartamentoxAssunto.setQtd(total);
				
				valoresDaLinhaAssunto.add(valorDaCelulaDepartamentoxAssunto);
			}
			linhaAssunto.setDepartamentos(valoresDaLinhaAssunto);
			
			linhaAssunto.setQtdNaoClassificado(commonsDao.qtdNaoClassificado(pesquisa));
			
			listaRelatorio.add(linhaAssunto);
		}
			
		listaRelatorio.add(linhaComTotais(listaRelatorio, departamentos, pesquisa));		
		
		return listaRelatorio;
	}
	
	private RelSiacol04Dto linhaComTotais(List<RelSiacol04Dto> listaRelatorio, List<DepartamentoDto> departamentos, PesquisaRelatorioSiacolDto pesquisa) {
		RelSiacol04Dto linhaAssunto = new RelSiacol04Dto();
		linhaAssunto.setAssunto("TOTAL");
		
		List<RelDepartamentosSiacol04Dto> valoresDaLinhaAssunto = new ArrayList<RelDepartamentosSiacol04Dto>();
		for (DepartamentoDto departamento : departamentos) {
			int totalJulgado = 0;
			int totalNaoJulgado = 0;
			int total = 0;
			RelDepartamentosSiacol04Dto valorDaCelulaDepartamentoxAssunto = new RelDepartamentosSiacol04Dto();
			valorDaCelulaDepartamentoxAssunto.setNome(departamento.getSigla());
			
			for (RelSiacol04Dto rel : listaRelatorio) {
				for (RelDepartamentosSiacol04Dto dept : rel.getDepartamentos()) {
					if (dept.getNome().equals(departamento.getSigla())) {
						if (pesquisa.isMesclado()) {
							totalJulgado += Integer.parseInt(dept.getQtdJulgados());
							totalNaoJulgado += Integer.parseInt(dept.getQtdNaoJulgados());
						} else {
							total += Integer.parseInt(dept.getQtd());
						}
					}
				}
			}
			if (pesquisa.isMesclado()) {
			    valorDaCelulaDepartamentoxAssunto.setQtd(String.valueOf(totalJulgado) + "/" + String.valueOf(totalNaoJulgado));
			} else {
				valorDaCelulaDepartamentoxAssunto.setQtd(String.valueOf(total));
			}
			valorDaCelulaDepartamentoxAssunto.setQtdJulgados(String.valueOf(totalJulgado));
			valorDaCelulaDepartamentoxAssunto.setQtdNaoJulgados(String.valueOf(totalNaoJulgado));
			valoresDaLinhaAssunto.add(valorDaCelulaDepartamentoxAssunto);
			
		}
		
		
		linhaAssunto.setDepartamentos(valoresDaLinhaAssunto);
		return linhaAssunto;
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
			httpGoApi.geraLog("RelatorioSiacol04Dao || populaAssuntoConfeaPeloAssuntoSiacol", StringUtil.convertObjectToJson(codigoAssunto), e);
		}
		return assuntoConfea;
	}
	
	public List<RelDetalhadoSiacol04Dto> getJulgadosPorDepartamentoEPorAssunto(PesquisaRelatorioSiacolDto pesquisa, DepartamentoDto departamento, Long idAssunto) {
		
		List<RelDetalhadoSiacol04Dto> lista = new ArrayList<RelDetalhadoSiacol04Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT COUNT(P.ID) as qtd, P.NO_PROTOCOLO,                               ");
		sql.append("           D.SIGLA, A.CODIGO AS COD_ASS_SIACOL, A.NOME AS ASSUNTO_SIACOL,    ");
		sql.append("           C.CODIGO AS COD_ASS_CONFEA, C.NOME AS ASSUNTO_CONFEA,             ");
		sql.append("           PA.CODIGO AS COD_ASS_CORP, PA.DESCRICAO AS ASSUNTO_CORPORATIVO    ");
		sql.append("      FROM SIACOL_PROTOCOLOS P                                               ");
		sql.append("      LEFT JOIN SIACOL_ASSUNTO A ON (A.ID = P.FK_ASSUNTO_SIACOL)             ");
		sql.append("      LEFT JOIN PRT_DEPARTAMENTOS D ON (D.ID = P.FK_DEPARTAMENTO)            "); // FIXME PEGAR DEPT DA CAD_DOCUMENTO
		sql.append("      LEFT JOIN SIACOL_ASSUNTO_CONFEA C ON (C.ID = A.FK_ASSUNTO_CONFEA)      ");
		sql.append("      LEFT JOIN PRT_ASSUNTOS PA ON (PA.ID = P.FK_ASSUNTO)                    ");
		sql.append("  WHERE 1 = 1                                                ");
		if (!idAssunto.equals(0L)) {
			sql.append("    AND P.FK_ASSUNTO_SIACOL = :idAssunto                 ");
		} else {
			sql.append("    AND P.FK_ASSUNTO_SIACOL IS NULL                      ");
		}
		
		sql.append("    AND P.NO_PROTOCOLO IN (                                  ");
		sql.append("         SELECT D.NUMERO_PROTOCOLO                           ");
		sql.append("           FROM CAD_DOCUMENTO D                              "); 
		sql.append("          WHERE D.FK_TIPO_DOCUMENTO = :documentoDecisao      ");
		sql.append("            AND D.FK_DEPARTAMENTO = :idDepartamento          ");
		sql.append("            AND D.NUMERO_DOCUMENTO IS NOT NULL               ");
		// Data do julgamento
		sql.append(" 	AND P.ID IN(                                                                             ");
		sql.append(" 		SELECT RL.FK_PROTOCOLO FROM SIACOL_DOCUMENTO_PROTOCOLO RL WHERE RL.FK_DOCUMENTO IN(    ");
		sql.append(" 		SELECT R.FK_DOCUMENTO FROM SIACOL_REUNIAO R                                            ");
		sql.append(" 	     WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                                ");
		if (pesquisa.temMeses()) {
		sql.append("           AND TO_CHAR(R.HR_FIM, 'MM') = :mes                                                 ");
		}
		sql.append(" )	OR RL.FK_DOCUMENTO IN (SELECT R.FK_EXTRA_PAUTA FROM SIACOL_REUNIAO R                       ");
		sql.append("         WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                                ");
		if (pesquisa.temMeses()) {
		sql.append("           AND TO_CHAR(R.HR_FIM, 'MM') = :mes                                                 ");
		}
		sql.append(" 	              ))                                                                            ");
		
		sql.append("          )                                                  ");
		sql.append("     GROUP BY  P.NO_PROTOCOLO, D.SIGLA, A.CODIGO, A.NOME,    ");
		sql.append("               C.CODIGO, C.NOME, PA.CODIGO, PA.DESCRICAO     ");
		sql.append("     ORDER BY A.CODIGO                                       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			query.setParameter("idDepartamento", departamento.getId());
			if (!idAssunto.equals(0L)) {
				query.setParameter("idAssunto", idAssunto);
			}
			query.setParameter("ano", pesquisa.getAno());
			if (pesquisa.temMeses()) {
				query.setParameter("meses", pesquisa.getMeses());
			}
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol04Dto dto = new RelDetalhadoSiacol04Dto();
					
					dto.setQtd(Integer.parseInt(result[0].toString()));
					dto.setQtdJulgados(dto.getQtd());
					dto.setProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setDepartamento(departamento.getSigla());
					dto.setCodigoAssuntoSiacol(result[3] != null ? result[3].toString() : null);
					dto.setAssuntoSiacol(result[4] != null ? result[4].toString() : null);
					dto.setCodigoAssuntoConfea(result[5] != null ? result[5].toString() : null);
					dto.setAssuntoConfea(result[6] != null ? result[6].toString() : null);
					dto.setCodigoAssuntoCorporativo(result[7] != null ? result[7].toString() : null);
					dto.setAssuntoCorporativo(result[8] != null ? result[8].toString() : null);
					
					lista.add(dto);
				}
			}
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol04Dao || getJulgadosPorDepartamentoEPorAssunto", StringUtil.convertObjectToJson(departamento + " - " + pesquisa), e);
		}
		return lista;
	}
	
	public List<RelDetalhadoSiacol04Dto> getNaoJulgadosPorDepartamentoEPorAssunto(PesquisaRelatorioSiacolDto pesquisa, DepartamentoDto departamento, Long idAssunto) {
		
		List<RelDetalhadoSiacol04Dto> lista = new ArrayList<RelDetalhadoSiacol04Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT COUNT(P.ID) as qtd, P.NO_PROTOCOLO,                                ");
		sql.append("           D.SIGLA, A.CODIGO AS COD_ASS_SIACOL, A.NOME AS ASSUNTO_SIACOL,     ");
		sql.append("           C.CODIGO AS COD_ASS_CONFEA, C.NOME AS ASSUNTO_CONFEA,              ");
		sql.append("           PA.CODIGO AS COD_ASS_CORP, PA.DESCRICAO AS ASSUNTO_CORPORATIVO     ");
		sql.append("       FROM SIACOL_PROTOCOLOS P                                               ");
		sql.append("       LEFT JOIN SIACOL_ASSUNTO A ON (A.ID = P.FK_ASSUNTO_SIACOL)             ");
		sql.append("       LEFT JOIN PRT_DEPARTAMENTOS D ON (D.ID = P.FK_DEPARTAMENTO)            ");
		sql.append("       LEFT JOIN SIACOL_ASSUNTO_CONFEA C ON (C.ID = A.FK_ASSUNTO_CONFEA)      ");
		sql.append("       LEFT JOIN PRT_ASSUNTOS PA ON (PA.ID = P.FK_ASSUNTO)                    ");
		sql.append("      WHERE 1 = 1                                                             ");
		if (!idAssunto.equals(0L)) {
			sql.append("    AND P.FK_ASSUNTO_SIACOL = :idAssunto                                  ");
		} else {
			sql.append("    AND P.FK_ASSUNTO_SIACOL IS NULL                                       ");
		}
		sql.append("        AND P.FK_DEPARTAMENTO = :idDepartamento                               "); 
		sql.append("        AND P.NO_PROTOCOLO NOT IN (                                           ");
		sql.append("    	   SELECT D.NUMERO_PROTOCOLO                                          ");       
		sql.append("    	     FROM CAD_DOCUMENTO D                                             ");        
		sql.append("    	    WHERE D.FK_TIPO_DOCUMENTO = :documentoDecisao                     ");   
		sql.append("              AND D.FK_DEPARTAMENTO = :idDepartamento                         ");
		sql.append("              AND D.NUMERO_DOCUMENTO IS NOT NULL                              ");
		sql.append("              AND D.NUMERO_PROTOCOLO = P.NO_PROTOCOLO)                        ");
		sql.append("     GROUP BY  P.NO_PROTOCOLO, D.SIGLA, A.CODIGO, A.NOME,                     ");
		sql.append("               C.CODIGO, C.NOME, PA.CODIGO, PA.DESCRICAO                      ");
		sql.append("     ORDER BY A.CODIGO                                                        ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			query.setParameter("idDepartamento", departamento.getId());
			if (!idAssunto.equals(0L)) {
				query.setParameter("idAssunto", idAssunto);
			}
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol04Dto dto = new RelDetalhadoSiacol04Dto();
					
					dto.setQtd(Integer.parseInt(result[0].toString()));
					dto.setQtdNaoJulgados(dto.getQtd());
					dto.setProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setDepartamento(departamento.getSigla());
					dto.setCodigoAssuntoSiacol(result[3] != null ? result[3].toString() : null);
					dto.setAssuntoSiacol(result[4] != null ? result[4].toString() : null);
					dto.setCodigoAssuntoConfea(result[5] != null ? result[5].toString() : null);
					dto.setAssuntoConfea(result[6] != null ? result[6].toString() : null);
					dto.setCodigoAssuntoCorporativo(result[7] != null ? result[7].toString() : null);
					dto.setAssuntoCorporativo(result[8] != null ? result[8].toString() : null);
					
					lista.add(dto);
				}
			}			
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol04Dao || getNaoJulgadosPorDepartamentoEPorAssunto", StringUtil.convertObjectToJson(departamento + " - " + pesquisa), e);
		}
		return lista;
	}

}
