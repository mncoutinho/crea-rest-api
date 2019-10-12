package br.org.crea.restapi.siacol.relatorio.rel07;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import br.org.crea.commons.models.siacol.dtos.relatorios.ProtocolosRel07Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol07Dto;
import br.org.crea.commons.models.siacol.dtos.relatorios.StatusRel07;
import br.org.crea.commons.util.EstatisticaUtil;

public class Rel07_05ContagemEstatisticaTest {
//
//	@Test
//	public void distribuicaoPorOutroUsuarioTest() {
//		
//		try {
//			
//			List<RelSiacol07Dto> listaRelatorio = contagemEstatistica(populaListaRelatorio());
//			
//			for (RelSiacol07Dto relatorio : listaRelatorio) {
//				System.out.println(relatorio.getAssunto());
//				
//				System.out.println("Minímo | Máximo | Média | Moda | Desvio Padrão | Quantidade");
//				for (StatusRel07 linhaStatus : relatorio.getStatus()) {
//					System.out.println(linhaStatus.getMin() + " | " + linhaStatus.getMax() + " | " + linhaStatus.getMedia() + " | " + linhaStatus.getModal() + " | " + linhaStatus.getDesv() + " | " + linhaStatus.getQtd());
//				}
//			}
//			
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//	}
//
//	private List<RelSiacol07Dto> populaListaRelatorio() {
//		List<RelSiacol07Dto> listaRelatorio = new ArrayList<RelSiacol07Dto>();
//		
//		RelSiacol07Dto relatorio = new RelSiacol07Dto();
//		
//		relatorio.setAssunto("Assunto 1");
//		
//		List<StatusRel07> listaStatus = new ArrayList<StatusRel07>();
//		
//		StatusRel07 status = new StatusRel07();
//		status.setNome("Status 1");
//		
//		List<ProtocolosRel07Dto> listaProtocolos = new ArrayList<ProtocolosRel07Dto>();
//		ProtocolosRel07Dto protocolo = new ProtocolosRel07Dto();
//		protocolo.setNumeroProtocolo("2018700001234");
//		protocolo.setDiferencaEmDias(6);
//		listaProtocolos.add(protocolo);
//		
//		protocolo = new ProtocolosRel07Dto();
//		protocolo.setNumeroProtocolo("2018700001237");
//		protocolo.setDiferencaEmDias(5);
//		listaProtocolos.add(protocolo);
//		
//		status.setListaProtocolos(listaProtocolos);
//		
//		listaStatus.add(status);
//		
//		relatorio.setStatus(listaStatus);
//		
//		listaRelatorio.add(relatorio);
//		
//		return listaRelatorio;
//	}
//
//	private List<RelSiacol07Dto> contagemEstatistica(List<RelSiacol07Dto> listaRelatorio) {
//		
//		for (RelSiacol07Dto relatorio : listaRelatorio) {
//			
//			for (StatusRel07 status : relatorio.getStatus()) {
//				List<Integer> lista = status.getListaProtocolos().stream().map(p -> p.getDiferencaEmDias()).collect(Collectors.toList());
//					
//				status.setMin(EstatisticaUtil.valorMinimo(lista));
//				status.setMax(EstatisticaUtil.valorMaximo(lista));
//				Double media = EstatisticaUtil.mediaInt(lista);
//				status.setMedia(media.intValue());
//				status.setModal(0); // FIXME MODA
//				Double valorDesvioPadrao = EstatisticaUtil.desvioPadrao(lista);
//				status.setDesv(valorDesvioPadrao.intValue());
//				status.setQtd(status.getListaProtocolos().size());
//			}			
//		}		
//		
//		return listaRelatorio;
//	}

}
