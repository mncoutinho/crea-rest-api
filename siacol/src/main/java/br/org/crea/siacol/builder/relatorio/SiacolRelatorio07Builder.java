package br.org.crea.siacol.builder.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol07Dao;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07RecebimentoDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol07Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.StatusRel07;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.EstatisticaUtil;
import br.org.crea.commons.util.ListUtils;

public class SiacolRelatorio07Builder {
	
	@Inject	RelatorioSiacol07Dao dao;
	List<ProtocolosRel07RecebimentoDto> listaRecebimentoRel07 = null;
	Date hoje = new Date();
	
	public List<RelSiacol07Dto> detalhamentoDoHistoricoDeProducaoDaQuantidadeDeProtocolosPorAnalistaOuConselheiroEPorAssunto(PesquisaRelatorioSiacolDto pesquisa) {
		List<RelSiacol07Dto> listaRelatorio = new ArrayList<RelSiacol07Dto>();
		
		for (AssuntoDto assunto : pesquisa.getAssuntos()) { // FIXME fazer querys para o caso de não passar assunto
			RelSiacol07Dto linhaAssunto = new RelSiacol07Dto();
			linhaAssunto.setAssunto(assunto.getDescricao());
			
			List<StatusRel07> listaStatus = new ArrayList<StatusRel07>();
			
			if (pesquisa.temPerfilAnalista()) {
				listaStatus.add(populaProtocolos(pesquisa, assunto.getId(), "RECEBER", "ANALISTA"));
				listaStatus.add(populaProtocolos(pesquisa, assunto.getId(), "ANALISE", "ANALISTA"));
				listaStatus.add(populaProtocolos(pesquisa, assunto.getId(), "PAUSADO", "ANALISTA"));
				listaStatus.add(populaProtocolos(pesquisa, assunto.getId(), "VINCULO", "ANALISTA"));
			}
			
			if (pesquisa.temPerfilConselheiro()) {
				listaStatus.add(populaProtocolos(pesquisa, assunto.getId(), "RECEBER", "CONSELHEIRO"));
				listaStatus.add(populaProtocolos(pesquisa, assunto.getId(), "ANALISE", "CONSELHEIRO"));
			}
			
			linhaAssunto.setStatus(listaStatus);
			listaRelatorio.add(linhaAssunto);
		}
		
		return contagemEstatistica(listaRelatorio);
	}
	
	private StatusRel07 populaProtocolos(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol, String tipo, String perfil) {
		StatusRel07 status = new StatusRel07();
		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
		status.setNome(tipo);
		
		if (perfil.equals("ANALISTA")) {
			if (tipo.equals("RECEBER")) {
				List<ProtocolosRel07RecebimentoDto> listaRecebimentos = obtemSaidaRecebidosAnalista(pesquisa, idAssuntoSiacol);
				for (ProtocolosRel07RecebimentoDto recebimento : listaRecebimentos) {
					obtemDiferencaDeDatasInicioEFimGeneric(recebimento, pesquisa, listaProtocolos, obtemEntradaRecebidoAnalista(pesquisa,recebimento));
				}
				listaRecebimentoRel07 = listaRecebimentos;
			}
			if (tipo.equals("ANALISE")) {
				List<String> listaProtocolosRecebimento = (List<String>) ListUtils.removerDuplicidade(populaListaProtocolos(listaRecebimentoRel07));
				
				List<ProtocolosRel07RecebimentoDto> listaEntradaAnalise = dao.getEntradaAnaliseAnalista(pesquisa, listaProtocolosRecebimento);
				
				for (ProtocolosRel07RecebimentoDto recebimentoAnalise : listaEntradaAnalise) {
					obtemDiferencaDeDatasInicioEFimGeneric(dao.getSaidaAnaliseAnalista(pesquisa, idAssuntoSiacol, recebimentoAnalise), pesquisa, listaProtocolos, recebimentoAnalise);
				}
				
				// FIXME somar o tempo de análise geral para cada protocolo, sem duplicidade, REMOVER DUPLICIDADE SOMANDO OS TEMPOS DE ANALISE DE CADA REPETICAO
			}
			if (tipo.equals("PAUSADO")) {
				for (ProtocolosRel07RecebimentoDto entradaPausado : dao.getEntradaPausadosAnalista(pesquisa, populaListaProtocolos(listaRecebimentoRel07))) {
					obtemDiferencaDeDatasInicioEFimGeneric(entradaPausado, pesquisa, listaProtocolos, dao.getSaidaPausadosAnalista(pesquisa, entradaPausado));
				}
			}	
			if (tipo.equals("VINCULO")) {
				for (ProtocolosRel07RecebimentoDto entradaVinculo : dao.getEntradaVinculoAnalista(pesquisa, idAssuntoSiacol)) {
					obtemDiferencaDeDatasInicioEFimGeneric(entradaVinculo, pesquisa, listaProtocolos, dao.getSaidaVinculoAnalista(pesquisa, entradaVinculo.getIdAuditoria()));
				}
			}	
		}
		
		if (perfil.equals("CONSELHEIRO")) {
			if (tipo.equals("RECEBER")) {
				List<ProtocolosRel07RecebimentoDto> listaRecebimentos = dao.getRecebimentoSiacol(pesquisa, idAssuntoSiacol);
				for (ProtocolosRel07RecebimentoDto recebimento : listaRecebimentos) {
					obtemDiferencaDeDatasInicioEFimGeneric(dao.getDistribuicaoConselheiroOuImpedimento(pesquisa, idAssuntoSiacol, recebimento.getIdAuditoria()), pesquisa, listaProtocolos, recebimento);
				}
				listaRecebimentoRel07 = listaRecebimentos;
			}
			
			if (tipo.equals("ANALISE")) {
				for (ProtocolosRel07RecebimentoDto recebimentoAnalise : dao.getRecebimentoSiacol(pesquisa, idAssuntoSiacol)) {
					obtemDiferencaDeDatasInicioEFimGeneric(recebimentoAnalise, pesquisa, listaProtocolos, getSaidaAnaliseConselheiro(pesquisa, idAssuntoSiacol));
				}
			}
		}
		
		status.setListaProtocolos(listaProtocolos);
		
		return status;
	}

	///////////////////////////////////////////////////////////
	/////************** ANALISTA ***********************///////
	///////////////////////////////////////////////////////////
	private List<ProtocolosRel07RecebimentoDto> obtemSaidaRecebidosAnalista(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
		List<ProtocolosRel07RecebimentoDto> listaRecebimentos = new ArrayList<ProtocolosRel07RecebimentoDto>();
		listaRecebimentos.addAll(dao.getDistribuicaoPorOutroUsuario(pesquisa, idAssuntoSiacol));
		listaRecebimentos.addAll(dao.getRecebimentoSiacol(pesquisa, idAssuntoSiacol));
		
		return listaRecebimentos;
	}
	
	private ProtocolosRel07RecebimentoDto obtemEntradaRecebidoAnalista(PesquisaRelatorioSiacolDto pesquisa, ProtocolosRel07RecebimentoDto recebimento) {
		ProtocolosRel07RecebimentoDto entradaRecebidoAnalista = dao.getDistribuicaoAnalista(pesquisa, recebimento.getNumeroProtocolo(), recebimento.getIdAuditoria());
		if (entradaRecebidoAnalista == null) {
			entradaRecebidoAnalista = dao.getTramiteEnvioAnalista(pesquisa, recebimento.getNumeroProtocolo(), recebimento.getIdAuditoria());
		}
		return entradaRecebidoAnalista;
	}

	///////////////////////////////////////////////////////////
	/////************** CONSELHEIRO ********************///////
	///////////////////////////////////////////////////////////
	private ProtocolosRel07RecebimentoDto getSaidaAnaliseConselheiro(PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
		
		ProtocolosRel07RecebimentoDto saidaAnaliseConselheiro = dao.getSaidaAnaliseAssinaEPautaConselheiro(pesquisa, idAssuntoSiacol);
		if (saidaAnaliseConselheiro == null) {
			saidaAnaliseConselheiro = dao.getSaidaAnaliseImpedimentoConselheiro(pesquisa, idAssuntoSiacol);
			if (saidaAnaliseConselheiro == null) {
				saidaAnaliseConselheiro = dao.getSaidaAnaliseDistribuicao(pesquisa, idAssuntoSiacol);
				if (saidaAnaliseConselheiro == null) {
					List<ProtocolosRel07RecebimentoDto> lista = dao.getDistribuicaoPorOutroUsuario(pesquisa, idAssuntoSiacol);
					if (lista != null) {
						if (lista.size() > 0) {
							saidaAnaliseConselheiro = lista.get(0);
						}
					}
				}
			}
		}
		return saidaAnaliseConselheiro;
	}

	///////////////////////////////////////////////////////////
	/////************** DEMAIS MÉTODOS *****************///////
	///////////////////////////////////////////////////////////
	private void obtemDiferencaDeDatasInicioEFimGeneric(ProtocolosRel07RecebimentoDto fim, PesquisaRelatorioSiacolDto pesquisa, List<ProtocolosRel07Dto> listaProtocolos, ProtocolosRel07RecebimentoDto inicio) {
		ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
		
		protocolo.setNumeroProtocolo(fim.getNumeroProtocolo() != null ? fim.getNumeroProtocolo() : inicio.getNumeroProtocolo());
		Long diferenca = DateUtils.getDiferencaDiasEntreDatas(inicio != null ? inicio.getData() : hoje, fim != null ? fim.getData() : hoje);
		protocolo.setDiferencaEmDias(diferenca.intValue());
		
		listaProtocolos.add(protocolo);
	}
	
	private List<String> populaListaProtocolos(List<ProtocolosRel07RecebimentoDto> listaRecebimento) {
		return listaRecebimento.stream().map(ProtocolosRel07RecebimentoDto::getNumeroProtocolo).collect(Collectors.toList());
	}
		
	///////////////////////////////////////////////////////////
	/////************** ESTATÍSTICA ********************///////
	///////////////////////////////////////////////////////////
	private List<RelSiacol07Dto> contagemEstatistica(List<RelSiacol07Dto> listaRelatorio) {
		
		for (RelSiacol07Dto relatorio : listaRelatorio) {
			
			for (StatusRel07 status : relatorio.getStatus()) {
				List<Integer> lista = status.getListaProtocolos().stream().map(p -> p.getDiferencaEmDias()).collect(Collectors.toList());
					
				status.setMin(EstatisticaUtil.valorMinimo(lista));
				status.setMax(EstatisticaUtil.valorMaximo(lista));
				status.setMedia(EstatisticaUtil.mediaInt(lista));
				status.setModal(EstatisticaUtil.modaUnica(lista));
				Double valorDesvioPadrao = EstatisticaUtil.desvioPadrao(lista);
				status.setDesv(valorDesvioPadrao.intValue());
				status.setQtd(status.getListaProtocolos().size());
			}			
		}		
		
		return listaRelatorio;
	}
}
