package br.org.crea.siacol.relatorio;

import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;

import br.org.crea.commons.models.siacol.dtos.RelatorioReuniaoSiacolDto;

public class GeraConteudo {

	public PdfPTable tabelaCoordenadoresEAdjuntos (List<RelatorioReuniaoSiacolDto> lista) {
		
		PdfPTable tabelaRelatorio = new PdfPTable(5);
		
		try {			
			tabelaRelatorio.setWidths(new float[]{1, 1, 1, 3, 1});
			
			// Cabeçalho da Tabela:
			Font fonteCabecalhoCelula = FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD);
			tabelaRelatorio.addCell(new Phrase("SIGLA", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("CARGO", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("CRACHÁ", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("NOME", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("PRESENÇA", fonteCabecalhoCelula));
			tabelaRelatorio.setHeaderRows(1);
			
			Font fonteCelula = FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.NORMAL);
			lista.forEach(elemento -> {
				tabelaRelatorio.addCell(new Phrase(elemento.getDepartamento(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getCargo(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getCracha(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getNome(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getPresenca(), fonteCelula));
			});
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return tabelaRelatorio;
	}

	public PdfPTable tabelaPresentes(List<RelatorioReuniaoSiacolDto> relatorioPresentesNoMomento) {
		
		PdfPTable tabelaRelatorio = new PdfPTable(1);
	
		// Cabeçalho da Tabela:
		Font fonteCabecalhoCelula = FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD);
		tabelaRelatorio.addCell(new Phrase("NOME",fonteCabecalhoCelula));
		tabelaRelatorio.setHeaderRows(1);
					
		Font fonteCelula = FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.NORMAL);
		relatorioPresentesNoMomento.forEach(elemento -> {
			tabelaRelatorio.addCell(new Phrase(elemento.getNome(), fonteCelula));
		});
		
		
		return tabelaRelatorio;
	}

	public PdfPTable tabelaComparecimento(List<RelatorioReuniaoSiacolDto> relatorioDeComparecimento) {
		
		PdfPTable tabelaRelatorio = new PdfPTable(8);
		
		try {		
			// Cria elemento tabela com 5 colunas
			tabelaRelatorio.setWidths(new float[]{3, 1, 1, 1, 1, 1, 1, 1});
			
			// Cabeçalho da Tabela:
			Font fonteCabecalhoCelula = FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD);
			tabelaRelatorio.addCell(new Phrase("NOME", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("ENTRADA", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("SAÍDA", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("TEMPO PRESENTE", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("PROCESSOS TOTAL / VOTADOS", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("VALOR DIÁRIA", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("JETON", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("DIÁRIA + JETON", fonteCabecalhoCelula));
			tabelaRelatorio.setHeaderRows(1);
						
			Font fonteCelula = FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.NORMAL);
			relatorioDeComparecimento.forEach(elemento -> {
				tabelaRelatorio.addCell(new Phrase(elemento.getNome(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getEntrada(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getSaida(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getTempoPresente(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getQtdVotado(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(String.valueOf(elemento.getDiaria()), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(String.valueOf(elemento.getJeton()), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(String.valueOf(elemento.getSoma()), fonteCelula));
			});
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return tabelaRelatorio;
	}

	public List<PdfPTable> tabelaComparecimentoComPausa(RelatorioReuniaoSiacolDto relatorioDeComparecimentoComPausa) {
		
		List<PdfPTable> tabelas = new ArrayList<PdfPTable>();
		
		for (RelatorioReuniaoSiacolDto relatorioDeComparecimento : relatorioDeComparecimentoComPausa.getRelatorio()) {
			PdfPTable tabelaRelatorio;
			if (relatorioDeComparecimento.temParte()) {
				if (relatorioDeComparecimento.getParte().equals("0")) {
					
					tabelaRelatorio = new PdfPTable(5);
					
					try {		
						// Cria elemento tabela com 5 colunas
						tabelaRelatorio.setWidths(new float[]{3, 1, 1, 1, 1});
						
						// Cabeçalho da Tabela:
						Font fonteCabecalhoCelula = FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD);
						tabelaRelatorio.addCell(new Phrase("NOME", fonteCabecalhoCelula));
						tabelaRelatorio.addCell(new Phrase("PROCESSOS TOTAL / VOTADOS", fonteCabecalhoCelula));
						tabelaRelatorio.addCell(new Phrase("VALOR DIÁRIA", fonteCabecalhoCelula));
						tabelaRelatorio.addCell(new Phrase("JETON", fonteCabecalhoCelula));
						tabelaRelatorio.addCell(new Phrase("DIÁRIA + JETON", fonteCabecalhoCelula));
						tabelaRelatorio.setHeaderRows(1);
									
						Font fonteCelula = FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.NORMAL);
						for (RelatorioReuniaoSiacolDto elemento : relatorioDeComparecimento.getRelatorio()) {
							tabelaRelatorio.addCell(new Phrase(elemento.getNome(), fonteCelula));
							tabelaRelatorio.addCell(new Phrase(elemento.getQtdVotado(), fonteCelula));
							tabelaRelatorio.addCell(new Phrase(String.valueOf(elemento.getDiaria()), fonteCelula));
							tabelaRelatorio.addCell(new Phrase(String.valueOf(elemento.getJeton()), fonteCelula));
							tabelaRelatorio.addCell(new Phrase(String.valueOf(elemento.getSoma()), fonteCelula));
						};
						
					} catch (DocumentException e) {
						e.printStackTrace();
					}
					
				} else {
					tabelaRelatorio = new PdfPTable(4);
					
					try {		
						// Cria elemento tabela com 4 colunas
						tabelaRelatorio.setWidths(new float[]{3, 1, 1, 1});
						
						// Cabeçalho da Tabela:
						Font fonteCabecalhoCelula = FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD);
						// Parte
						relatorioDeComparecimento.getParte();
						tabelaRelatorio.addCell(new Phrase("NOME", fonteCabecalhoCelula));
						tabelaRelatorio.addCell(new Phrase("ENTRADA", fonteCabecalhoCelula));
						tabelaRelatorio.addCell(new Phrase("SAÍDA", fonteCabecalhoCelula));
						tabelaRelatorio.addCell(new Phrase("TEMPO PRESENTE", fonteCabecalhoCelula));
						tabelaRelatorio.setHeaderRows(1);
									
						Font fonteCelula = FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.NORMAL);
						for (RelatorioReuniaoSiacolDto elemento : relatorioDeComparecimento.getRelatorio()) {
							tabelaRelatorio.addCell(new Phrase(elemento.getNome(), fonteCelula));
							tabelaRelatorio.addCell(new Phrase(elemento.getEntrada(), fonteCelula));
							tabelaRelatorio.addCell(new Phrase(elemento.getSaida(), fonteCelula));
							tabelaRelatorio.addCell(new Phrase(elemento.getTempoPresente(), fonteCelula));
						};
						
					} catch (DocumentException e) {
						e.printStackTrace();
					}
				}
			
			tabelas.add(tabelaRelatorio);
			}
		}
		return tabelas;
	}
	
	public PdfPTable tabelaOitentaPorcento(List<RelatorioReuniaoSiacolDto> relatorioDeComparecimento) {
		
		PdfPTable tabelaRelatorio = new PdfPTable(5);
		
		try {
			// Cria elemento tabela com 5 colunas
			tabelaRelatorio.setWidths(new float[]{3, 1, 1, 1, 1});
			
			// Cabeçalho da Tabela:
			Font fonteCabecalhoCelula = FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD);
			tabelaRelatorio.addCell(new Phrase("NOME", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("ENTRADA", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("SAÍDA", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("TEMPO PRESENTE", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("HORA 80%", fonteCabecalhoCelula));
			tabelaRelatorio.setHeaderRows(1);
						
			Font fonteCelula = FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.NORMAL);
			relatorioDeComparecimento.forEach(elemento -> {
				tabelaRelatorio.addCell(new Phrase(elemento.getNome(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getEntrada(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getSaida(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getTempoPresente(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getHoraOitentaPorcento(), fonteCelula));
			});
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return tabelaRelatorio;
	}
}


