package br.org.crea.siacol.builder.relatorio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol10Dao;
import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacolCommonsDao;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol10Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol10Dto;

public class SiacolRelatorio10Builder {
	
	@Inject	RelatorioSiacol10Dao dao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	@Inject	RelatorioSiacolCommonsDao commonsDao;
	
	public List<RelSiacol10Dto> detalhamentoDoHistoricoDeProducaoDaQuantidadeDeProtocolosPorAnalistaOuConselheiroEPorAssunto(PesquisaRelatorioSiacolDto pesquisa) {

		if (pesquisa.isTodosAssuntos()) {
			pesquisa.setAssuntos(assuntoConverter.toListDtoSiacol(assuntoDao.getAll()));
		}
		pesquisa.getAssuntos().sort(Comparator.comparing(AssuntoDto::getCodigo));
		
		List<RelSiacol10Dto> listaRelatorio = new ArrayList<RelSiacol10Dto>();

		for (AssuntoDto assunto : pesquisa.getAssuntos()) {
			RelSiacol10Dto relatorio = new RelSiacol10Dto();
			
			relatorio.setCodigoAssunto(assunto.getCodigo());
			relatorio.setDescricaoAssunto(assunto.getNome());
			relatorio.setPesoDto(dao.obtemPeso(String.valueOf(assunto.getCodigo())));
			
			populaInstrucao(relatorio, pesquisa, assunto.getId());
			
			populaComissao(relatorio, pesquisa, assunto.getId());
			
			populaCamara(relatorio, pesquisa, assunto.getId());
			
			populaPlenaria(relatorio, pesquisa, assunto.getId());
			
			populaRelativizado(relatorio, pesquisa, assunto.getId());
			
			listaRelatorio.add(relatorio);
		}
		
		populaTotais(listaRelatorio);
		
		return listaRelatorio;
	}

	private void populaInstrucao(RelSiacol10Dto relatorio, PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
		
		relatorio.setProtocolosEntradaInstrucao(dao.getEntradaAnalistaDistribuicaoOuTramitacao(pesquisa, idAssuntoSiacol, "INSTRUCAO"));

		List<RelDetalhadoSiacol10Dto> listaSaidas = new ArrayList<RelDetalhadoSiacol10Dto>();
		List<RelDetalhadoSiacol10Dto> listaAcumulado = new ArrayList<RelDetalhadoSiacol10Dto>();

		for (RelDetalhadoSiacol10Dto entrada : relatorio.getProtocolosEntradaInstrucao()) {
			RelDetalhadoSiacol10Dto saida = dao.getSaidaAnalistaDistribuicaoTramitacaoOuAdReferendumOuCriacaoOficio(pesquisa, entrada, idAssuntoSiacol);
			if (saida != null) {
				listaSaidas.add(saida);
			} else {
				listaAcumulado.add(entrada);
			}
		}
		
		relatorio.setProtocolosSaidaInstrucao(listaSaidas);
		relatorio.setProtocolosAcumuladoInstrucao(listaAcumulado);
		
		relatorio.setEntradaInstrucao(relatorio.getProtocolosEntradaInstrucao().size());
		relatorio.setSaidaInstrucao(relatorio.getProtocolosSaidaInstrucao().size());
		relatorio.setAcumuladoInstrucao(relatorio.getProtocolosAcumuladoInstrucao().size());
	}

	private void populaComissao(RelSiacol10Dto relatorio, PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
		relatorio.setProtocolosEntradaComissao(dao.getEntradaAnalistaDistribuicaoOuTramitacao(pesquisa, idAssuntoSiacol, "COMISSAO"));

		List<RelDetalhadoSiacol10Dto> listaSaidas = new ArrayList<RelDetalhadoSiacol10Dto>();
		List<RelDetalhadoSiacol10Dto> listaAcumulado = new ArrayList<RelDetalhadoSiacol10Dto>();

		for (RelDetalhadoSiacol10Dto entrada : relatorio.getProtocolosEntradaComissao()) {
			RelDetalhadoSiacol10Dto saida = dao.getSaidaAnalistaDistribuicaoTramitacaoOuAdReferendumOuCriacaoOficio(pesquisa, entrada, idAssuntoSiacol);
			if (saida != null) {
				listaSaidas.add(saida);
			} else {
				listaAcumulado.add(entrada);
			}
		}
		
		relatorio.setProtocolosSaidaComissao(listaSaidas);
		relatorio.setProtocolosAcumuladoComissao(listaAcumulado);
		
		relatorio.setEntradaComissao(relatorio.getProtocolosEntradaComissao().size());
		relatorio.setSaidaComissao(relatorio.getProtocolosSaidaComissao().size());
		relatorio.setAcumuladoComissao(relatorio.getProtocolosAcumuladoComissao().size());
	}

	private void populaCamara(RelSiacol10Dto relatorio, PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {

		relatorio.setProtocolosEntradaCamara(dao.getEntradaAnalistaDistribuicaoOuTramitacao(pesquisa, idAssuntoSiacol, "CAMARA"));

		List<RelDetalhadoSiacol10Dto> listaSaidas = new ArrayList<RelDetalhadoSiacol10Dto>();
		List<RelDetalhadoSiacol10Dto> listaAcumulado = new ArrayList<RelDetalhadoSiacol10Dto>();

		for (RelDetalhadoSiacol10Dto entrada : relatorio.getProtocolosEntradaCamara()) {
			RelDetalhadoSiacol10Dto saida = dao.getSaidaAnalistaDistribuicaoTramitacaoOuAdReferendumOuCriacaoOficio(pesquisa, entrada, idAssuntoSiacol);
			if (saida != null) {
				listaSaidas.add(saida);
			} else {
				listaAcumulado.add(entrada);
			}
		}
		
		relatorio.setProtocolosSaidaCamara(listaSaidas);
		relatorio.setProtocolosAcumuladoCamara(listaAcumulado);
		
		relatorio.setEntradaCamara(relatorio.getProtocolosEntradaCamara().size());
		relatorio.setSaidaCamara(relatorio.getProtocolosSaidaCamara().size());
		relatorio.setAcumuladoCamara(relatorio.getProtocolosAcumuladoCamara().size());
	}

	private void populaPlenaria(RelSiacol10Dto relatorio, PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {

		relatorio.setProtocolosEntradaPlenaria(dao.getEntradaAnalistaDistribuicaoOuTramitacao(pesquisa, idAssuntoSiacol, "PLENARIA"));

		List<RelDetalhadoSiacol10Dto> listaSaidas = new ArrayList<RelDetalhadoSiacol10Dto>();
		List<RelDetalhadoSiacol10Dto> listaAcumulado = new ArrayList<RelDetalhadoSiacol10Dto>();

		for (RelDetalhadoSiacol10Dto entrada : relatorio.getProtocolosEntradaPlenaria()) {
			RelDetalhadoSiacol10Dto saida = dao.getSaidaAnalistaDistribuicaoTramitacaoOuAdReferendumOuCriacaoOficio(pesquisa, entrada, idAssuntoSiacol);
			if (saida != null) {
				listaSaidas.add(saida);
			} else {
				listaAcumulado.add(entrada);
			}
		}
		
		relatorio.setProtocolosSaidaPlenaria(listaSaidas);
		relatorio.setProtocolosAcumuladoPlenaria(listaAcumulado);
		
		relatorio.setEntradaPlenaria(relatorio.getProtocolosEntradaPlenaria().size());
		relatorio.setSaidaPlenaria(relatorio.getProtocolosSaidaPlenaria().size());
		relatorio.setAcumuladoPlenaria(relatorio.getProtocolosAcumuladoPlenaria().size());
	}

	private void populaRelativizado(RelSiacol10Dto relatorio, PesquisaRelatorioSiacolDto pesquisa, Long idAssuntoSiacol) {
		relatorio.setTotalSaidaRelativoInstrucao(relatorio.getSaidaInstrucao() * relatorio.getPesoDto().getPesoInstrucao());
		relatorio.setTotalSaidaRelativoComissao(relatorio.getSaidaComissao() * relatorio.getPesoDto().getPesoComissao());
		relatorio.setTotalSaidaRelativoCamara(relatorio.getSaidaCamara() * relatorio.getPesoDto().getPesoCamara());
		relatorio.setTotalSaidaRelativoPlenaria(relatorio.getSaidaPlenaria() * relatorio.getPesoDto().getPesoPlenaria());
	}

	private void populaTotais(List<RelSiacol10Dto> listaRelatorio) {
		
		RelSiacol10Dto total = new RelSiacol10Dto();
		total.setDescricaoAssunto("Total");
		
		total.setEntradaInstrucao(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getEntradaInstrucao).sum());
		total.setSaidaInstrucao(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getSaidaInstrucao).sum());
		total.setAcumuladoInstrucao(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getAcumuladoInstrucao).sum());
		total.setEntradaComissao(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getEntradaComissao).sum());
		total.setSaidaComissao(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getSaidaComissao).sum());
		total.setAcumuladoComissao(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getAcumuladoComissao).sum());
		total.setEntradaCamara(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getEntradaCamara).sum());
		total.setSaidaCamara(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getSaidaCamara).sum());
		total.setAcumuladoCamara(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getAcumuladoCamara).sum());
		total.setEntradaPlenaria(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getEntradaPlenaria).sum());
		total.setSaidaPlenaria(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getSaidaPlenaria).sum());
		total.setAcumuladoPlenaria(listaRelatorio.stream().mapToInt(RelSiacol10Dto::getAcumuladoPlenaria).sum());
		
		total.setTotalSaidaRelativoInstrucao(listaRelatorio.stream().mapToDouble(RelSiacol10Dto::getTotalSaidaRelativoInstrucao).sum());
		total.setTotalSaidaRelativoComissao(listaRelatorio.stream().mapToDouble(RelSiacol10Dto::getTotalSaidaRelativoComissao).sum());
		total.setTotalSaidaRelativoCamara(listaRelatorio.stream().mapToDouble(RelSiacol10Dto::getTotalSaidaRelativoCamara).sum());
		total.setTotalSaidaRelativoPlenaria(listaRelatorio.stream().mapToDouble(RelSiacol10Dto::getTotalSaidaRelativoPlenaria).sum());
		  
		total.setTotalTrabalhadoRelativo(total.getTotalSaidaRelativoInstrucao() + total.getTotalSaidaRelativoComissao() + total.getTotalSaidaRelativoCamara() + total.getTotalSaidaRelativoPlenaria());
		total.setTotalSaldoRelativo(0d);
		
		listaRelatorio.add(total);
	}
	
}
