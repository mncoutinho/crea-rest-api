package br.org.crea.atendimento.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.atendimento.converter.AgendamentoConverter;
import br.org.crea.atendimento.dao.GuicheDao;
import br.org.crea.commons.models.atendimento.AgendamentoMobile;
import br.org.crea.commons.models.atendimento.dtos.AgendamentoDto;
import br.org.crea.commons.models.atendimento.dtos.PainelAtendimentoDto;
import br.org.crea.commons.models.atendimento.dtos.PesquisaAtendimentoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.HttpFirebaseApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.ExcelPoiUtil;
import br.org.crea.commons.util.ItextUtil;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;

public class GuicheService {

	@Inject
	GuicheDao dao;

	@Inject
	AgendamentoConverter converter;

	@Inject
	ExcelPoiUtil excel;

	@Inject
	HttpClientGoApi httpGoApi;
	
	@Inject
	HttpFirebaseApi httpFirebaseApi;

	public List<AgendamentoDto> getFilaDoDia(PesquisaGenericDto pesquisa) {
		return converter.toListDto(dao.getFilaDoDia(pesquisa));
	}

	public List<AgendamentoDto> getFilaEmAndamento(PesquisaGenericDto pesquisa, Long idFuncionario) {
		pesquisa.setIdFuncionario(idFuncionario);
		return converter.toListDto(dao.getFilaEmAndamento(pesquisa));
	}

	public List<AgendamentoDto> getListaAtendidosDoDia(PesquisaGenericDto pesquisa, Long idFuncionario) {
		return converter.toListDto(dao.getListaAtendidosDoDia(pesquisa, idFuncionario));
	}

	public AgendamentoDto confirmarPresenca(AgendamentoDto dto, Long idFuncionario) {
		dao.confirmarPresenca(dto, idFuncionario);
		return dto;
	}

	public AgendamentoDto chamarCliente(AgendamentoDto dto, Long idFuncionario) {
		dao.chamarCliente(dto, idFuncionario);
		return dto;
	}

	public AgendamentoDto iniciarAtendimento(AgendamentoDto dto, Long idFuncionario) {
		dto.setIdFuncionario(idFuncionario);
		dao.iniciarAtendimento(dto);
		return dto;
	}

	public AgendamentoDto liberarChamada(AgendamentoDto dto, Long idFuncionario) {
		dto.setIdFuncionario(idFuncionario);
		dao.liberarChamada(dto);
		return dto;
	}

	public AgendamentoDto finalizarAtendimento(AgendamentoDto dto, Long idFuncionario) {
		dao.finalizarAtendimento(dto, idFuncionario);
		return dto;
	}

	public AgendamentoDto cancela(AgendamentoDto dto, Long idFuncionario) {
		dao.cancela(dto, idFuncionario);
		return dto;
	}

	public AgendamentoDto marcarAusencia(AgendamentoDto dto, Long idFuncionario) {
		dto.setIdFuncionario(idFuncionario);
		dao.marcarAusencia(dto);
		return dto;
	}

	public boolean clienteFoiCapturado(AgendamentoDto dto) {
		return dao.clienteFoiCapturado(dto);
	}

	public boolean clienteFoiChamado(AgendamentoDto dto) {
		return dao.clienteFoiChamado(dto);
	}

	public List<AgendamentoDto> pesquisaFiltroIndicadores(PesquisaGenericDto pesquisa) {
		return converter.toListDto(dao.filtroPesquisa(pesquisa));
	}

	public int pesquisaTotalFiltroIndicadores(PesquisaGenericDto pesquisa) {
		return dao.pesquisaTotalFiltroIndicadores(pesquisa);
	}

	public List<AgendamentoDto>  pesquisaPresenca(PesquisaAtendimentoDto pesquisa) {
		return converter.toListDto(dao.filtroPesquisaRecepcao(pesquisa));
	}

	public boolean temAtendimentoAindaEmAberto(Long idFuncionario) {
		return dao.temAtendimentoAindaEmAberto(idFuncionario);
	}

	public byte[] relatorioIndicadores(PesquisaGenericDto pesquisa) {
		ItextUtil.iniciarDocumentoParaDownload("RELATÓRIO DE AGENDAMENTO", ItextUtil.PAISAGEM);
		ItextUtil.iniciarDocumento();
		ItextUtil.adicionaObjetoAoConteudoEFecha(geraConteudoItext(dao.filtroPesquisa(pesquisa)));
		
		return ItextUtil.getPdfBytes();
	}
	
	private Object geraConteudoItext(List<AgendamentoMobile> listaAgendamentos) {
		// Cria elemento tabela com 6 colunas
		PdfPTable tabelaRelatorio = new PdfPTable(6);
		
		try {
			tabelaRelatorio.setWidths(new float[]{2, 1, 2, 4, 4, 4});
			
			// Cabeçalho da Tabela:
			Font fonteCabecalhoCelula = FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD);
			tabelaRelatorio.addCell(new Phrase("DT. AGENDAMENTO", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("HORÁRIO", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("CPF/CNPJ", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("NOME", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("ASSUNTO", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("ATENDENTE", fonteCabecalhoCelula));
			tabelaRelatorio.setHeaderRows(1);
			tabelaRelatorio.setWidthPercentage(100);
				
	//		FontFactory.getFont(FontFactory.COURIER, (float) 12.0, Font.NORMAL, new Color(255, 0, 0))
			Font fonteCelula = FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.NORMAL);
			listaAgendamentos.forEach(elemento -> {
				
				tabelaRelatorio.addCell(new Phrase(DateUtils.format(elemento.getDataAgendamento(), DateUtils.DD_MM_YYYY), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(DateUtils.format(elemento.getDataAgendamento(), DateUtils.HH_MM), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getCpfOuCnpj(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getNome(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getAssunto().getDescricao(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(elemento.getFuncionario().getNome(), fonteCelula));
			});
		
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return tabelaRelatorio;
	}

	public PainelAtendimentoDto chamarClientePainel(PainelAtendimentoDto dto) {
		httpFirebaseApi.salvarPainelAtendimento(dto);
		return dto;
	}

}
