package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.RelatorioReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ValueRelDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.LabelRelDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol01Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol01DataSetDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol01Dto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.EstatisticaUtil;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol01Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	EventoDao eventoDao;
	@Inject	DepartamentoDao departamentoDao;
	@Inject	DepartamentoConverter departamentoConverter;
	@Inject	AssuntoDao assuntoCorporativoDao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	
	String[] mesesDoAno = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	
	public RelatorioSiacol01Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}

	public RelSiacol01Dto quantidadeDeProtocolosPorDepartamentosEPorAno(PesquisaRelatorioSiacolDto pesquisa) {
		List<Long> idsDepartamentos = new ArrayList<Long>();
		if (!pesquisa.isTodosDepartamentos()) {
			idsDepartamentos = populaListaIdsDepartamentos(pesquisa.getDepartamentos());
		}
		
		RelSiacol01Dto relatorio = new RelSiacol01Dto();
		relatorio.setLabels(populaLabels(pesquisa.getAno()));
		
		List<RelSiacol01DataSetDto> dataset = new ArrayList<RelSiacol01DataSetDto>();
		
		// fazer recursão até que retorno da query seja vazio
		dataset.add(getPassivoAnoAnterior(pesquisa, idsDepartamentos));
		
		int saldoAcumulado = Integer.parseInt(dataset.get(0).getData().get(0).getValue()); // passivoAnoAnterior
		
		RelSiacol01DataSetDto entradaDatasetItem = new RelSiacol01DataSetDto();
		RelSiacol01DataSetDto saidaDatasetItem = new RelSiacol01DataSetDto();
		RelSiacol01DataSetDto saldoDatasetItem = new RelSiacol01DataSetDto();
		
		entradaDatasetItem.setSeriesname("Entrada");
		saidaDatasetItem.setSeriesname("Saida");
		saldoDatasetItem.setSeriesname("Saldo");
		
		List<ValueRelDto> valoresEntrada = new ArrayList<ValueRelDto>();
		List<ValueRelDto> valoresSaida = new ArrayList<ValueRelDto>();
		List<ValueRelDto> valoresSaldo = new ArrayList<ValueRelDto>();
		
		for (String mes : mesesDoAno) {
			
			RelatorioReuniaoSiacolDto dto = new RelatorioReuniaoSiacolDto();
			ValueRelDto valorEntrada = new ValueRelDto();
			ValueRelDto valorSaida = new ValueRelDto();
			ValueRelDto valorSaldo = new ValueRelDto();
			
			dto.setMes(mes);
			List<RelDetalhadoSiacol01Dto> listaEntrada = getEntrada(pesquisa, idsDepartamentos, pesquisa.getAno(), mes);
			valorEntrada.setValue(String.valueOf(EstatisticaUtil.soma(listaEntrada.stream().map(RelDetalhadoSiacol01Dto::getQtd))));
			valorEntrada.setProtocolos(listaEntrada);
			
			List<RelDetalhadoSiacol01Dto> listaSaida = getSaida(pesquisa, idsDepartamentos, pesquisa.getAno(), mes);
			valorSaida.setValue(String.valueOf(EstatisticaUtil.soma(listaSaida.stream().map(RelDetalhadoSiacol01Dto::getQtd))));
			valorSaida.setProtocolos(listaSaida);
			
			saldoAcumulado = saldoAcumulado + Integer.parseInt(valorEntrada.getValue()) - Integer.parseInt(valorSaida.getValue());
			valorSaldo.setValue(String.valueOf(saldoAcumulado));
			
			valoresEntrada.add(valorEntrada);
			valoresSaida.add(valorSaida);
			valoresSaldo.add(valorSaldo);
		}
			
		entradaDatasetItem.setData(valoresEntrada);
		saidaDatasetItem.setData(valoresSaida);
		saldoDatasetItem.setData(valoresSaldo);
		
		dataset.add(entradaDatasetItem);
		dataset.add(saidaDatasetItem);
		dataset.add(saldoDatasetItem);
		
		relatorio.setDataset(dataset);
		
		return relatorio;
	}
	
	private RelSiacol01DataSetDto getPassivoAnoAnterior(PesquisaRelatorioSiacolDto pesquisa, List<Long> idsDepartamentos) {
		
		RelSiacol01DataSetDto datasetItem = new RelSiacol01DataSetDto();
		datasetItem.setSeriesname("Ano Anterior");
		
		List<ValueRelDto> valoresAnoAnterior = new ArrayList<ValueRelDto>();
		ValueRelDto valueAnoAnterior = new ValueRelDto();
		
//			List<RelDetalhadoSiacol01Dto> lista = obtemPassivoPorAno(pesquisa, idsDepartamentos, pesquisa.getAno());
//			
//			valueAnoAnterior.setValue(String.valueOf(EstatisticaUtil.soma(lista.stream().map(RelDetalhadoSiacol01Dto::getQtd))));
		
		valueAnoAnterior.setValue(String.valueOf(obtemPassivoPorAno(pesquisa, idsDepartamentos, pesquisa.getAno())));
		
		valoresAnoAnterior.add(valueAnoAnterior);
		datasetItem.setData(valoresAnoAnterior);
		
		return datasetItem;
	}
	
	public List<RelDetalhadoSiacol01Dto> getEntrada(PesquisaRelatorioSiacolDto pesquisa, List<Long> idsDepartamentos, String ano, String mes) {
		
		List<RelDetalhadoSiacol01Dto> lista = new ArrayList<RelDetalhadoSiacol01Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(A.ID_DEPARTAMENTO_DESTINO), A.NUMERO, D.SIGLA, A.DT_CREATE  ");
		sql.append("   FROM CAD_AUDITORIA A                                                   ");
		sql.append("   JOIN PRT_DEPARTAMENTOS D ON (A.ID_DEPARTAMENTO_DESTINO = D.ID)         ");
		sql.append("  WHERE A.MODULO = :modulo                                                ");
		sql.append("    AND A.EVENTO = :tipoEvento                                            ");
		sql.append("   AND (A.ACAO = 'C' OR ACAO = 'U')                                       ");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append(" 	  AND A.ID_DEPARTAMENTO_DESTINO IN (:idsDepartamentos)            ");
		}
		sql.append(" 		  AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                         ");
		sql.append(" 		  AND TO_CHAR(A.DT_CREATE, 'MM') = :mes                           ");
		sql.append("   GROUP BY A.NUMERO, D.SIGLA, A.DT_CREATE                                ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoEvento", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idsDepartamentos", idsDepartamentos);
			}
			query.setParameter("ano", ano);
			query.setParameter("mes", mes);
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol01Dto dto = new RelDetalhadoSiacol01Dto();
					
					dto.setQtd(Integer.parseInt(result[0].toString()));
					dto.setProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setDepartamento(result[2] != null ? result[2].toString() : null);
					dto.setData((Date) result[3]);
					dto.setDataFormatada(DateUtils.format(dto.getData(), DateUtils.DD_MM_YYYY_HH_MM_SS));
					
					lista.add(dto);
				}
			}

		} catch (NoResultException e) {
			return lista;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol01Dao || getEntrada", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;
	}
	
	public List<RelDetalhadoSiacol01Dto> getSaida(PesquisaRelatorioSiacolDto pesquisa, List<Long> idsDepartamentos, String ano, String mes) {
		
		List<RelDetalhadoSiacol01Dto> lista = new ArrayList<RelDetalhadoSiacol01Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(A.ID_DEPARTAMENTO_ORIGEM), A.NUMERO, D.SIGLA, A.DT_CREATE ");
		sql.append("   FROM CAD_AUDITORIA A                                                 ");
		sql.append("   JOIN PRT_DEPARTAMENTOS D ON (A.ID_DEPARTAMENTO_ORIGEM = D.ID)        ");
		sql.append("  WHERE A.MODULO = :modulo                                              ");
		sql.append("    AND A.EVENTO = :tipoEvento                                          ");
		sql.append("    AND A.ACAO IS NULL			                                        ");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append("    AND A.ID_DEPARTAMENTO_ORIGEM IN (:idsDepartamentos)             ");
		}
		sql.append("        AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                         ");
		sql.append("        AND TO_CHAR(A.DT_CREATE, 'MM') = :mes                           ");
		sql.append("   GROUP BY A.NUMERO, D.SIGLA, A.DT_CREATE                              ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoEvento", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idsDepartamentos", idsDepartamentos);
			}
			query.setParameter("ano", ano);
			query.setParameter("mes", mes);
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol01Dto dto = new RelDetalhadoSiacol01Dto();
					
					dto.setQtd(Integer.parseInt(result[0].toString()));
					dto.setProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setDepartamento(result[2] != null ? result[2].toString() : null);
					dto.setData((Date) result[3]);
					dto.setDataFormatada(DateUtils.format(dto.getData(), DateUtils.DD_MM_YYYY_HH_MM_SS));
					
					lista.add(dto);
				}
			}

		} catch (NoResultException e) {
			return lista;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol01Dao || getSaida", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;
	}
	
	public int obtemPassivoPorAno(PesquisaRelatorioSiacolDto pesquisa, List<Long> idsDepartamentos, String ano) {
		
		List<RelDetalhadoSiacol01Dto> lista = new ArrayList<RelDetalhadoSiacol01Dto>();
		
		List<RelDetalhadoSiacol01Dto> listaEntrada = obtemEntradaPassivoPorAno(pesquisa, idsDepartamentos, pesquisa.getAno());
		List<RelDetalhadoSiacol01Dto> listaSaida = obtemSaidaPassivoPorAno(pesquisa, idsDepartamentos, pesquisa.getAno());
		lista.addAll(listaEntrada);
		lista.addAll(listaSaida);
		return listaEntrada.size() - listaSaida.size();
//			return lista;
	}
	
	public List<RelDetalhadoSiacol01Dto> obtemEntradaPassivoPorAno(PesquisaRelatorioSiacolDto pesquisa, List<Long> idsDepartamentos, String ano) {
		
		List<RelDetalhadoSiacol01Dto> lista = new ArrayList<RelDetalhadoSiacol01Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT (A.ID_DEPARTAMENTO_DESTINO), A.NUMERO, D.SIGLA, A.DT_CREATE          ");
		sql.append("   FROM CAD_AUDITORIA A                                           ");
		sql.append("   JOIN PRT_DEPARTAMENTOS D ON (D.ID = A.ID_DEPARTAMENTO_DESTINO)  ");
		sql.append("  WHERE A.MODULO = :modulo                                        ");
		sql.append("    AND A.EVENTO = :tipoEvento                                    ");
		sql.append("   AND (A.ACAO = 'C' OR A.ACAO = 'U')                             ");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append("    AND A.ID_DEPARTAMENTO_DESTINO IN (:idsDepartamentos)      ");
		}		
		sql.append("    AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                       ");
		sql.append("   GROUP BY A.NUMERO, D.SIGLA, A.DT_CREATE                        ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoEvento", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idsDepartamentos", idsDepartamentos);
			}
			// String.valueOf(Integer.parseInt(pesquisa.getAno())-1)
			query.setParameter("ano", String.valueOf(Integer.parseInt(pesquisa.getAno())-1));
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol01Dto dto = new RelDetalhadoSiacol01Dto();
					
					dto.setQtd(Integer.parseInt(result[0].toString()));
					dto.setProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setDepartamento(result[2] != null ? result[2].toString() : null);
					dto.setData((Date) result[3]);
					dto.setDataFormatada(DateUtils.format(dto.getData(), DateUtils.DD_MM_YYYY_HH_MM_SS));
					
					lista.add(dto);
				}
			}

		} catch (NoResultException e) {
			return lista;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol01Dao || obtemEntradaPassivoPorAno", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;
	}

	public List<RelDetalhadoSiacol01Dto> obtemSaidaPassivoPorAno(PesquisaRelatorioSiacolDto pesquisa, List<Long> idsDepartamentos, String ano) {
		
		List<RelDetalhadoSiacol01Dto> lista = new ArrayList<RelDetalhadoSiacol01Dto>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT COUNT(A.ID_DEPARTAMENTO_ORIGEM), A.NUMERO, D.SIGLA, A.DT_CREATE          ");
		sql.append("    FROM CAD_AUDITORIA A                                          ");
		sql.append("    JOIN PRT_DEPARTAMENTOS D ON (D.ID = A.ID_DEPARTAMENTO_ORIGEM) ");
		sql.append("   WHERE A.MODULO = :modulo                                       ");
		sql.append("     AND A.EVENTO = :tipoEvento                                   ");
		sql.append("     AND A.ACAO IS NULL			                                  ");
		if (!pesquisa.isTodosDepartamentos()) {
			sql.append(" AND A.ID_DEPARTAMENTO_ORIGEM IN (:idsDepartamentos)          ");
		}
		sql.append("     AND TO_CHAR(A.DT_CREATE, 'YYYY') = :ano                      ");
		sql.append("   GROUP BY A.NUMERO, D.SIGLA, A.DT_CREATE                        ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoEvento", TipoEventoAuditoria.TRAMITAR_PROTOCOLO.getId());
			query.setParameter("modulo", ModuloSistema.SIACOL.getId());
			if (!pesquisa.isTodosDepartamentos()) {
				query.setParameter("idsDepartamentos", idsDepartamentos);
			}
			// String.valueOf(Integer.parseInt(pesquisa.getAno())-1)
			query.setParameter("ano", String.valueOf(Integer.parseInt(pesquisa.getAno())-1));
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					
					RelDetalhadoSiacol01Dto dto = new RelDetalhadoSiacol01Dto();
					
					dto.setQtd(Integer.parseInt(result[0].toString()));
					dto.setProtocolo(result[1] != null ? result[1].toString() : null);
					dto.setDepartamento(result[2] != null ? result[2].toString() : null);
					dto.setData((Date) result[3]);
					dto.setDataFormatada(DateUtils.format(dto.getData(), DateUtils.DD_MM_YYYY_HH_MM_SS));
					
					lista.add(dto);
				}
			}
	
		} catch (NoResultException e) {
			return lista;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol01Dao || obtemSaidaPassivoPorAno", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return lista;
	}
	
	private List<LabelRelDto> populaLabels(String ano) {
		List<LabelRelDto> labels = new ArrayList<LabelRelDto>();
		
		LabelRelDto label = new LabelRelDto();
		label.setLabel(String.valueOf(Integer.parseInt(ano)-1));
		labels.add(label);
		
		String[] meses = new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
		
		for (String mes : meses) {
			label = new LabelRelDto();
			label.setLabel(mes + "/" + ano);
			labels.add(label);
		}
			
		return labels;
	}
	
	private List<Long> populaListaIdsDepartamentos(List<DepartamentoDto> departamentos) {
		List<Long> listaIds = new ArrayList<Long>();
		
		for (DepartamentoDto departamento : departamentos) {
			listaIds.add(departamento.getId());
		}
		return listaIds;
	}
}
