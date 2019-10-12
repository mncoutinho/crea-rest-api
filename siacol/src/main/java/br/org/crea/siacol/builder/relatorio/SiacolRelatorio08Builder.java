package br.org.crea.siacol.builder.relatorio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.siacol.relatorios.RelatorioSiacol08Dao;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol08Dto;

public class SiacolRelatorio08Builder {
	
	@Inject	RelatorioSiacol08Dao dao;
	
	public List<RelSiacol08Dto> detalhamentoDoHistoricoDeProducaoDaQuantidadeDeProtocolosPorAnalistaOuConselheiroEPorAssunto(PesquisaRelatorioSiacolDto pesquisa) {

		List<RelSiacol08Dto> listaRelatorio = new ArrayList<RelSiacol08Dto>();
		
		List<Long> idsProtocolos = new ArrayList<Long>();
		
		for (Long idProtocolo : idsProtocolos) {
			RelSiacol08Dto relatorio = new RelSiacol08Dto();
			
			populaAnalistaAReceber(relatorio, idProtocolo);
			
			populaAnalistaAnalise(relatorio, idProtocolo);
			
			populaConselheiroAReceber(relatorio, idProtocolo);
			
			populaConselheiroAssinatura(relatorio, idProtocolo);
			
			populaJulgamento(relatorio, idProtocolo);
			
			populaRevisaoDeDecisao(relatorio, idProtocolo);
			
			populaCoordenador(relatorio, idProtocolo);
			
			populaSaida(relatorio, idProtocolo);
			
			populaEfetivoAtendimento(relatorio, idProtocolo);
			
			populaTempoAtendimento(relatorio, idProtocolo);
			
			listaRelatorio.add(relatorio);
		}

		
		return listaRelatorio;
	}

	private void populaAnalistaAReceber(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}

	private void populaAnalistaAnalise(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}

	private void populaConselheiroAReceber(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}

	private void populaConselheiroAssinatura(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}

	private void populaJulgamento(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}

	private void populaRevisaoDeDecisao(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}

	private void populaCoordenador(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}

	private void populaSaida(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}

	private void populaEfetivoAtendimento(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}

	private void populaTempoAtendimento(RelSiacol08Dto relatorio, Long idProtocolo) {
		// TODO Auto-generated method stub
		
	}
	
}
