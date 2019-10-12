package br.org.crea.siacol.builder.relatorio;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol06Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacolCommonsDao;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol06Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol06Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol06PesoDto;
import br.org.crea.commons.util.EstatisticaUtil;
import br.org.crea.commons.util.ListUtils;

public class SiacolRelatorio06Builder {
	
	@Inject	RelatorioSiacol06Dao dao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	@Inject	RelatorioSiacolCommonsDao commonsDao;
	
	String[] mesesDoAno = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	List<RelDetalhadoSiacol06Dto> listaEntradaComPassivoMensalRel06 = new ArrayList<RelDetalhadoSiacol06Dto>();
	List<RelDetalhadoSiacol06Dto> listaEntradaSemDescontarSaida = null;
	
	public List<RelSiacol06Dto> quantidadeDeProtocolosPorAnalistaOuConselheiroPorMes(PesquisaRelatorioSiacolDto pesquisa) {
		
		if (pesquisa.isTodosAssuntos()) {
			pesquisa.setAssuntos(assuntoConverter.toListDtoSiacol(assuntoDao.getAll()));
		}
		List<Long> idsAssuntoSiacol = commonsDao.populaListaIdsAssuntos(pesquisa.getAssuntos());

		List<RelSiacol06Dto> listaRelatorio = new ArrayList<RelSiacol06Dto>();
		
		// FIXME rodar o loop só até o mês atual
		
		
		for (String mes : mesesDoAno) {
			RelSiacol06Dto linha = new RelSiacol06Dto();
			linha.setMes(mes);
			listaEntradaSemDescontarSaida = new ArrayList<RelDetalhadoSiacol06Dto>();
			
			if (pesquisa.temPerfilAnalista()) {
				populaPassivoAnalista(linha, pesquisa, mes, idsAssuntoSiacol);
				populaEntradaAnalista(linha, pesquisa, mes, idsAssuntoSiacol);
				populaSaidaAnalista(linha, pesquisa, mes, idsAssuntoSiacol);
				populaPausadoAnalista(linha, pesquisa, mes, idsAssuntoSiacol);
				populaRetornoAnalista(linha, pesquisa, mes, idsAssuntoSiacol);
			}
			
			if (pesquisa.temPerfilConselheiro()) {
				populaPassivoConselheiro(linha, pesquisa, mes, idsAssuntoSiacol);
				populaEntradaConselheiro(linha, pesquisa, mes, idsAssuntoSiacol);
				populaSaidaConselheiro(linha, pesquisa, mes, idsAssuntoSiacol);
			}
			
			linha.setProtocolosPassivo(listaEntradaComPassivoMensalRel06);
			linha.setPassivo(listaEntradaComPassivoMensalRel06.size()); // lista de passivo é atualizada após popula passivo
			linha.setPesoPassivo(somatorioPeso(listaEntradaComPassivoMensalRel06));
			
			populaPesoTotal(linha);
			populaPercentual(linha);
			
			listaRelatorio.add(linha);
		}
		
		linhaComTotais(listaRelatorio, pesquisa);

		return listaRelatorio;
	}

	///////////////////////////////////////////////////////////
	/////************** ANALISTA ***********************///////
	///////////////////////////////////////////////////////////
	private void populaPassivoAnalista(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		if (mes.equals("01")) {
			List<RelDetalhadoSiacol06Dto> listaPassivo = dao.getPassivoAnalistaAnosAnteriores(pesquisa, idsAssuntoSiacol);
			linha.setProtocolosPassivo(listaPassivo);
			linha.setPassivo(linha.getProtocolosPassivo().size());
			listaEntradaComPassivoMensalRel06.addAll(listaPassivo);
		} else {
			linha.setProtocolosPassivo(listaEntradaComPassivoMensalRel06);
		}
	}
	
	

	private void populaEntradaAnalista(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		linha.setProtocolosEntrada(dao.getEntradaAnalistaDistribuicaoOuTramitacao(pesquisa, mes, idsAssuntoSiacol));
		linha.setEntrada(linha.getProtocolosEntrada().size());
		linha.setPesoEntrada(somatorioPeso(linha.getProtocolosEntrada()));
		listaEntradaComPassivoMensalRel06.addAll(linha.getProtocolosEntrada());
		listaEntradaSemDescontarSaida = (List<RelDetalhadoSiacol06Dto>) ListUtils.copy(listaEntradaComPassivoMensalRel06);
	}
	
	private void populaSaidaAnalista(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		List<RelDetalhadoSiacol06Dto> listaSaida = new ArrayList<RelDetalhadoSiacol06Dto>();
		List<RelDetalhadoSiacol06Dto> listaPassivo = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		for (RelDetalhadoSiacol06Dto entrada : listaEntradaComPassivoMensalRel06) {
			RelDetalhadoSiacol06Dto saida = getSaidaAnalista(pesquisa, mes, entrada, idsAssuntoSiacol);
			if (saida != null) {
				listaSaida.add(preencheDadosAPartirDaEntrada(saida, entrada));
			} else {
				listaPassivo.add(entrada);
			}
		}
		listaEntradaComPassivoMensalRel06 = listaPassivo;
		linha.setProtocolosSaida(listaSaida);
		linha.setSaida(listaSaida.size());
		linha.setPesoSaida(somatorioPeso(linha.getProtocolosSaida()));
	}

	private RelDetalhadoSiacol06Dto getSaidaAnalista(PesquisaRelatorioSiacolDto pesquisa, String mes, RelDetalhadoSiacol06Dto entrada, List<Long> idsAssuntoSiacol) {
		RelDetalhadoSiacol06Dto saida = dao.getSaidaAnalistaDistribuicaoTramitacaoOuAdReferendumOuCriacaoOficio(pesquisa, mes, entrada, idsAssuntoSiacol);
		return saida;
	}

	private void populaPausadoAnalista(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		List<RelDetalhadoSiacol06Dto> listaPausado = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		for (RelDetalhadoSiacol06Dto entrada : listaEntradaSemDescontarSaida) {
			RelDetalhadoSiacol06Dto pausa = dao.getPausadoAnalista(pesquisa, mes, entrada, idsAssuntoSiacol);
			if (pausa != null) {
				listaPausado.add(preencheDadosAPartirDaEntrada(pausa, entrada));
			}
		}
		linha.setProtocolosPausado(listaPausado);
		linha.setPausado(listaPausado.size());
		linha.setPesoPausado(somatorioPeso(linha.getProtocolosPausado()));
	}
	


	private void populaRetornoAnalista(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		linha.setProtocolosRetorno(dao.getRetornoAnalista(pesquisa, mes, idsAssuntoSiacol));
		linha.setRetorno(linha.getProtocolosRetorno().size());
		linha.setPesoRetorno(somatorioPeso(linha.getProtocolosRetorno()));
	}
	
	///////////////////////////////////////////////////////////
	/////************** CONSELHEIRO ********************///////
	///////////////////////////////////////////////////////////
	private void populaEntradaConselheiro(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		linha.setProtocolosEntrada(dao.getEntradaConselheiroDistribuicao(pesquisa, mes, idsAssuntoSiacol));
		linha.setEntrada(linha.getProtocolosEntrada().size());
		linha.setPesoEntrada(somatorioPeso(linha.getProtocolosEntrada()));
		listaEntradaComPassivoMensalRel06.addAll(listaEntradaComPassivoMensalRel06);
		listaEntradaSemDescontarSaida = (List<RelDetalhadoSiacol06Dto>) ListUtils.copy(listaEntradaComPassivoMensalRel06);
	}

	private void populaSaidaConselheiro(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		List<RelDetalhadoSiacol06Dto> listaSaida = new ArrayList<RelDetalhadoSiacol06Dto>();
		List<RelDetalhadoSiacol06Dto> listaPassivo = new ArrayList<RelDetalhadoSiacol06Dto>();
		
		for (RelDetalhadoSiacol06Dto entrada : listaEntradaComPassivoMensalRel06) {
			RelDetalhadoSiacol06Dto saida = getSaidaConselheiro(pesquisa, mes, entrada, idsAssuntoSiacol);
			if (saida != null) {
				listaSaida.add(preencheDadosAPartirDaEntrada(saida, entrada));
			} else {
				listaPassivo.add(entrada);
			}
		}
		listaEntradaComPassivoMensalRel06 = listaPassivo;
		linha.setProtocolosSaida(listaSaida);
		linha.setSaida(listaSaida.size());
		linha.setPesoSaida(somatorioPeso(linha.getProtocolosSaida()));
	}
	
	private RelDetalhadoSiacol06Dto getSaidaConselheiro(PesquisaRelatorioSiacolDto pesquisa, String mes, RelDetalhadoSiacol06Dto entrada, List<Long> idsAssuntoSiacol) {
		RelDetalhadoSiacol06Dto saida = dao.getSaidaConselheiroImpedimentoOuAssinaturaRelatorioVoto(pesquisa, mes, entrada, idsAssuntoSiacol);
		if (saida != null) {
			preencheDadosAPartirDaEntrada(saida, entrada);
		}
		return saida;
	}

	private void populaPassivoConselheiro(RelSiacol06Dto linha, PesquisaRelatorioSiacolDto pesquisa, String mes, List<Long> idsAssuntoSiacol) {
		if (mes.equals("01")) {
			List<RelDetalhadoSiacol06Dto> listaPassivoEntrada = dao.getPassivoEntradaConselheiro(pesquisa, idsAssuntoSiacol);
			List<RelDetalhadoSiacol06Dto> listaPassivo = new ArrayList<RelDetalhadoSiacol06Dto>();
			
			for (RelDetalhadoSiacol06Dto entrada : listaPassivoEntrada) {
				RelDetalhadoSiacol06Dto saida = getPassivoSaidaConselheiro(pesquisa, mes, entrada, idsAssuntoSiacol);
				if (saida == null) {
					listaPassivo.add(entrada);
				}
			}
			linha.setProtocolosPassivo(listaPassivo);
			linha.setPassivo(linha.getProtocolosPassivo().size());
			linha.setPesoPassivo(somatorioPeso(linha.getProtocolosPassivo()));
			listaEntradaComPassivoMensalRel06.addAll(listaPassivo);
		} else {
			linha.setProtocolosPassivo((List<RelDetalhadoSiacol06Dto>) ListUtils.copy(listaEntradaComPassivoMensalRel06));
			linha.setPassivo(linha.getProtocolosPassivo().size());
			linha.setPesoPassivo(somatorioPeso(linha.getProtocolosPassivo()));
		}
		
	}

	private RelDetalhadoSiacol06Dto getPassivoSaidaConselheiro(PesquisaRelatorioSiacolDto pesquisa, String mes,	RelDetalhadoSiacol06Dto entrada, List<Long> idsAssuntoSiacol) {
		RelDetalhadoSiacol06Dto saida = dao.getSaidaConselheiroImpedimentoOuAssinaturaRelatorioVoto(pesquisa, mes, entrada, idsAssuntoSiacol);
		if (saida != null) {
			preencheDadosAPartirDaEntrada(saida, entrada);
		}
		return saida;
	}

	///////////////////////////////////////////////////////////
	/////******* PERCENTUAL E PESO TOTAL ***************///////
	///////////////////////////////////////////////////////////
	private void populaPesoTotal(RelSiacol06Dto linha) {
		RelSiacol06PesoDto pesoTotal = new RelSiacol06PesoDto();
		linha.setPesoPassivo(linha.getPesoPassivo());
		linha.setPesoEntrada(linha.getPesoEntrada());
		linha.setPesoSaida(linha.getPesoSaida());
		linha.setPesoPausado(linha.getPesoPausado());
		linha.setPesoRetorno(linha.getPesoRetorno());
		
		linha.setPesoTotal(pesoTotal);
	}
	
	private void populaPercentual(RelSiacol06Dto linha) {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.CEILING);
		double percentual;
		double entrada = linha.getEntrada() + linha.getPassivo();
		if (entrada == 0) { // evitar divisão por zero
			percentual = 0;
		} else {
			percentual = linha.getSaida() / entrada * 100.0;
		}
		linha.setPercentual(String.valueOf(df.format(percentual)) + " %");
	}
	
	private RelDetalhadoSiacol06Dto preencheDadosAPartirDaEntrada(RelDetalhadoSiacol06Dto saida, RelDetalhadoSiacol06Dto entrada) {
		saida.setCodigoAssunto(entrada.getCodigoAssunto());
		saida.setNomeAssunto(entrada.getNomeAssunto());
		saida.setIdDepartamento(entrada.getIdDepartamento());
		saida.setNomeDepartamento(entrada.getNomeDepartamento());
		saida.setPesoDto(entrada.getPesoDto());
		
		return saida;
	}
	
	private RelSiacol06PesoDto somatorioPeso(List<RelDetalhadoSiacol06Dto> protocolos) {
		RelSiacol06PesoDto somaPeso = new RelSiacol06PesoDto();

		if (!protocolos.isEmpty()) {
			somaPeso.setPesoInstrucao(EstatisticaUtil.somaDouble(protocolos.stream().map(RelDetalhadoSiacol06Dto::getPesoDto).map(RelSiacol06PesoDto::getPesoInstrucao).collect(Collectors.toList())));
			somaPeso.setPesoCamara(EstatisticaUtil.somaDouble(protocolos.stream().map(RelDetalhadoSiacol06Dto::getPesoDto).map(RelSiacol06PesoDto::getPesoCamara).collect(Collectors.toList())));
			somaPeso.setPesoComissao(EstatisticaUtil.somaDouble(protocolos.stream().map(RelDetalhadoSiacol06Dto::getPesoDto).map(RelSiacol06PesoDto::getPesoComissao).collect(Collectors.toList())));
			somaPeso.setPesoPlenaria(EstatisticaUtil.somaDouble(protocolos.stream().map(RelDetalhadoSiacol06Dto::getPesoDto).map(RelSiacol06PesoDto::getPesoPlenaria).collect(Collectors.toList())));
			somaPeso.setPesoValido(EstatisticaUtil.somaDouble(protocolos.stream().map(RelDetalhadoSiacol06Dto::getPesoDto).map(RelSiacol06PesoDto::getPesoValido).collect(Collectors.toList())));
		}
		
		return somaPeso;
	}
	
	///////////////////////////////////////////////////////////
	/////************** TOTAIS *************************///////
	///////////////////////////////////////////////////////////
	private void linhaComTotais(List<RelSiacol06Dto> listaRelatorio, PesquisaRelatorioSiacolDto pesquisa) {
		// obtenho totais antes de adicionar linha do primeiro total
		RelSiacol06Dto pesosTotais = getLinhaPorTipo(listaRelatorio, pesquisa);
		
		listaRelatorio.add(getTotal(listaRelatorio, "TOTAL", pesquisa.temPerfilAnalista()));
		
//		listaRelatorio.add(getPesoTotal(pesosTotais, "INSTRUCAO", pesquisa.temPerfilAnalista()));
//		listaRelatorio.add(getPesoTotal(pesosTotais, "CAMARA", pesquisa.temPerfilAnalista()));
//		listaRelatorio.add(getPesoTotal(pesosTotais, "COMISSAO", pesquisa.temPerfilAnalista()));
//		listaRelatorio.add(getPesoTotal(pesosTotais, "PLENARIA", pesquisa.temPerfilAnalista()));
		listaRelatorio.add(getPesoTotal(pesosTotais, "TOTAL RELATIVIZADO", pesquisa.temPerfilAnalista()));
		
	}

	private RelSiacol06Dto getTotal(List<RelSiacol06Dto> listaRelatorio, String tipo, boolean temPerfilAnalista) {
		RelSiacol06Dto relatorio = new RelSiacol06Dto();
		
		relatorio.setMes(tipo);
		
		int entrada = 0 , saida = 0, pausado = 0, retorno = 0;
		
		for (RelSiacol06Dto linha : listaRelatorio) {
			entrada += linha.getEntrada();
			saida += linha.getSaida();
			
			if (temPerfilAnalista) {
				pausado += linha.getPausado();
				retorno += linha.getRetorno();
			}
		}
		
		relatorio.setEntrada(entrada);
		relatorio.setSaida(saida);
		
		if (temPerfilAnalista) {
			relatorio.setPausado(pausado);
			relatorio.setRetorno(retorno);
		}
		
		return relatorio;
	}

	private RelSiacol06Dto getPesoTotal(RelSiacol06Dto pesosTotais, String tipo, boolean temPerfilAnalista) {
		RelSiacol06Dto relatorio = new RelSiacol06Dto();
		
		relatorio.setMes(tipo);
		
		if (tipo.equals("INSTRUCAO")) {
			relatorio.setEntrada(pesosTotais.getPesoEntrada().getPesoInstrucao());
			relatorio.setSaida(pesosTotais.getPesoSaida().getPesoInstrucao());
			
			if (temPerfilAnalista) {
				relatorio.setPausado(pesosTotais.getPesoPausado().getPesoInstrucao());
				relatorio.setRetorno(pesosTotais.getPesoRetorno().getPesoInstrucao());
			}
		}
		if (tipo.equals("CAMARA")) {
			relatorio.setEntrada(pesosTotais.getPesoEntrada().getPesoCamara());
			relatorio.setSaida(pesosTotais.getPesoSaida().getPesoCamara());
			
			if (temPerfilAnalista) {
				relatorio.setPausado(pesosTotais.getPesoPausado().getPesoCamara());
				relatorio.setRetorno(pesosTotais.getPesoRetorno().getPesoCamara());
			}
		}
		if (tipo.equals("COMISSAO")) {
			relatorio.setEntrada(pesosTotais.getPesoEntrada().getPesoComissao());
			relatorio.setSaida(pesosTotais.getPesoSaida().getPesoComissao());
			
			if (temPerfilAnalista) {
				relatorio.setPausado(pesosTotais.getPesoPausado().getPesoComissao());
				relatorio.setRetorno(pesosTotais.getPesoRetorno().getPesoComissao());
			}
		}
		if (tipo.equals("PLENARIA")) {
			relatorio.setEntrada(pesosTotais.getPesoEntrada().getPesoPlenaria());
			relatorio.setSaida(pesosTotais.getPesoSaida().getPesoPlenaria());
			
			if (temPerfilAnalista) {
				relatorio.setPausado(pesosTotais.getPesoPausado().getPesoPlenaria());
				relatorio.setRetorno(pesosTotais.getPesoRetorno().getPesoPlenaria());
			}
		}
		if (tipo.equals("TOTAL RELATIVIZADO")) {
			relatorio.setEntrada(pesosTotais.getPesoEntrada().getPesoValido());
			relatorio.setSaida(pesosTotais.getPesoSaida().getPesoValido());
			
			if (temPerfilAnalista) {
				relatorio.setPausado(pesosTotais.getPesoPausado().getPesoValido());
				relatorio.setRetorno(pesosTotais.getPesoRetorno().getPesoValido());
			}
		}
		
		return relatorio;
	}

	private RelSiacol06Dto getLinhaPorTipo(List<RelSiacol06Dto> listaRelatorio, PesquisaRelatorioSiacolDto pesquisa) {
		RelSiacol06Dto relatorio = new RelSiacol06Dto();
		
		RelSiacol06PesoDto somaPesoEntrada = new RelSiacol06PesoDto();
		RelSiacol06PesoDto somaPesoSaida = new RelSiacol06PesoDto();
		RelSiacol06PesoDto somaPesoPausado = new RelSiacol06PesoDto();
		RelSiacol06PesoDto somaPesoRetorno = new RelSiacol06PesoDto();
		RelSiacol06PesoDto somaPesoValido = new RelSiacol06PesoDto();
		
		for (RelSiacol06Dto linha : listaRelatorio) {
			somaPesoEntrada.setPesoInstrucao(somaPesoEntrada.getPesoInstrucao() + linha.getPesoEntrada().getPesoInstrucao());
			somaPesoEntrada.setPesoCamara(somaPesoEntrada.getPesoCamara() + linha.getPesoEntrada().getPesoCamara());
			somaPesoEntrada.setPesoComissao(somaPesoEntrada.getPesoComissao() + linha.getPesoEntrada().getPesoPlenaria());
			somaPesoEntrada.setPesoPlenaria(somaPesoEntrada.getPesoPlenaria() + linha.getPesoEntrada().getPesoPlenaria());
			somaPesoEntrada.setPesoValido(somaPesoEntrada.getPesoValido() + linha.getPesoEntrada().getPesoValido());
			
			somaPesoSaida.setPesoInstrucao(somaPesoSaida.getPesoInstrucao() + linha.getPesoSaida().getPesoInstrucao());
			somaPesoSaida.setPesoCamara(somaPesoSaida.getPesoCamara() + linha.getPesoSaida().getPesoCamara());
			somaPesoSaida.setPesoComissao(somaPesoSaida.getPesoComissao() + linha.getPesoSaida().getPesoPlenaria());
			somaPesoSaida.setPesoPlenaria(somaPesoSaida.getPesoPlenaria() + linha.getPesoSaida().getPesoPlenaria());
			somaPesoSaida.setPesoValido(somaPesoSaida.getPesoValido() + linha.getPesoSaida().getPesoValido());
			
			if (pesquisa.temPerfilAnalista()) {
				somaPesoPausado.setPesoInstrucao(somaPesoPausado.getPesoInstrucao() + linha.getPesoPausado().getPesoInstrucao());
				somaPesoPausado.setPesoCamara(somaPesoPausado.getPesoCamara() + linha.getPesoPausado().getPesoCamara());
				somaPesoPausado.setPesoComissao(somaPesoPausado.getPesoComissao() + linha.getPesoPausado().getPesoPlenaria());
				somaPesoPausado.setPesoPlenaria(somaPesoPausado.getPesoPlenaria() + linha.getPesoPausado().getPesoPlenaria());
				somaPesoPausado.setPesoValido(somaPesoPausado.getPesoValido() + linha.getPesoPausado().getPesoValido());
				
				somaPesoRetorno.setPesoInstrucao(somaPesoRetorno.getPesoInstrucao() + linha.getPesoRetorno().getPesoInstrucao());
				somaPesoRetorno.setPesoCamara(somaPesoRetorno.getPesoCamara() + linha.getPesoRetorno().getPesoCamara());
				somaPesoRetorno.setPesoComissao(somaPesoRetorno.getPesoComissao() + linha.getPesoRetorno().getPesoPlenaria());
				somaPesoRetorno.setPesoPlenaria(somaPesoRetorno.getPesoPlenaria() + linha.getPesoRetorno().getPesoPlenaria());
				somaPesoRetorno.setPesoValido(somaPesoRetorno.getPesoValido() + linha.getPesoRetorno().getPesoValido());
			}
		}
		
		relatorio.setPesoEntrada(somaPesoEntrada);
		relatorio.setPesoSaida(somaPesoSaida);
		relatorio.setPesoPausado(somaPesoPausado);
		relatorio.setPesoRetorno(somaPesoRetorno);
		relatorio.setPesoTotal(somaPesoValido);
		
		return relatorio;
	}
}
