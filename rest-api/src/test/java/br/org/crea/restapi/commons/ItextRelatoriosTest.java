package br.org.crea.restapi.commons;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.atendimento.dao.GuicheDao;
import br.org.crea.commons.models.atendimento.AgendamentoMobile;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.ItextUtil;

public class ItextRelatoriosTest {

	static GuicheDao dao;
	private static EntityManager em = null;
	private List<AgendamentoMobile> listaAgendamento;

	@Before
	public void setup() {
		dao = new GuicheDao();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dsCreaTest");
		em = factory.createEntityManager();
		Assert.assertTrue("entity manager iniciado", em != null);
		listaAgendamento = populaAgendamentos();
	}
	
	@After
	public void fim() {
		em.close();
		Assert.assertFalse("entity manager finalizado", em.isOpen() );
	}
	
	private List<AgendamentoMobile> populaAgendamentos() {
		dao.setEntityManager(em);
		PesquisaGenericDto pesquisa = new PesquisaGenericDto();
		pesquisa.setUnidadeAtendimento(new Long(23020804));
		pesquisa.setStatus(new Long(99));
		pesquisa.setDataInicio(DateUtils.generateDate("01/06/2018"));
		pesquisa.setDataFim(DateUtils.generateDate("30/06/2018"));
		pesquisa.setRows(1000);
		pesquisa.setPage(0);
		
		listaAgendamento = dao.filtroPesquisa(pesquisa);
		
		return listaAgendamento;
	}


	@Test
	public void relatorioItext() {

//		ItextUtil.iniciarDocumento("RELATÓRIO DE AGENDAMENTO");
//		ItextUtil.adicionaCabecalhoPadrao();
//		ItextUtil.adicionaRodapePadrao();
//		ItextUtil.adicionaTabelaAgendamentoAoConteudo(listaAgendamento);
		

//		ItextUtil.iniciarDocumento("relatorio");
//		Document documento = ItextUtil.getDocumento();

//		File arquivo = new File("/opt/arquivos/" + nomeArquivo + ".pdf");
//		Document documento = new Document(PageSize.A4, 65, 28, 40, 26);
//		PdfWriter writer = null;
//		PdfContentByte pdfByte = null;

//		try {
//			writer = PdfWriter.getInstance(documento, new FileOutputStream(ItextUtil.getArquivo()));

			// Metadados
//			ItextUtil.adicionaMetadados("ok");
//			documento.addTitle("Crea-RJ - Sistema Corporativo");
//			documento.addSubject("Crea-RJ - Sistema Corporativo");
//			documento.addAuthor("Crea-RJ - Sistema Corporativo");

			// Cabeçalho

			// Cabeçalho - Imagem do Brasão
//			Image imagem = Image.getInstance("/opt/arquivos/brasao.png");
//			imagem.scalePercent(5);
//			imagem.setAlignment(Element.ALIGN_CENTER);
//
//			Table table = new Table(1);
//			table.setAlignment(Table.ALIGN_CENTER);
//			table.setBorder(Table.NO_BORDER);
//
//			Cell cell = new Cell();
//			cell.setBorder(Cell.NO_BORDER);
//			cell.add(imagem);
//			table.addCell(cell);
//
//			// Font - cabeçalho
////			Font negrito = new Font(Font.FONT_SANS_SERIF, 12, Font.CAPTION);
//			
//			// Cabeçalho - Texto
//			Phrase conteudoCabecalho = new Phrase();
//			conteudoCabecalho.add(table);
//
//			Phrase texto = new Phrase("SERVIÇO PÚBLICO FEDERAL" + quebra + quebra + "CONSELHO REGIONAL DE ENGENHARIA E AGRONOMIA DO RIO DE JANEIRO" + quebra + "CREA-RJ");
//			
//			conteudoCabecalho.add(texto);
//
//			HeaderFooter cabecalho = new HeaderFooter(conteudoCabecalho, false);
//			cabecalho.setAlignment(HeaderFooter.ALIGN_CENTER);
//			cabecalho.setBorder(Rectangle.NO_BORDER);
//			documento.setHeader(cabecalho);
//			ItextUtil.adicionaCabecalhoPadrao();

			// Rodape
//			Phrase texto = new Phrase();
//			texto.add("Rua Buenos Aires, nº 40 - Centro - 20.070-022 - Rio de Janeiro - RJ" + quebra + "crea-rj@crea-rj.org.br - www.crea-rj.org.br");
//
//			HeaderFooter rodape = new HeaderFooter(texto, false);
//			rodape.setAlignment(HeaderFooter.ALIGN_CENTER);
//			rodape.setBorder(Rectangle.NO_BORDER);
//			documento.setFooter(rodape);
//			ItextUtil.adicionaRodapePadrao();
//
////			// Conteúdo
//			documento.open();
//
//			documento.add(new Phrase("Hello World!"));
//			
//			// Cria elemento tabela com 2 colunas
//			PdfPTable tabelaRelatorio = new PdfPTable(2);
//			tabelaRelatorio.setWidths(new float[]{1, 3});
//
//			
//			listaAgendamento.forEach(elemento -> {
//				// Preenche células de uma linha 
//				PdfPCell celula = new PdfPCell();
//				celula.addElement(new Phrase(DateUtils.format(elemento.getDataAgendamento(), DateUtils.DD_MM_YYYY)));
//				tabelaRelatorio.addCell(celula);
//				
//				celula = new PdfPCell();
//				celula.addElement(new Phrase(elemento.getNome()));
//				tabelaRelatorio.addCell(celula);
//			});
			
			
			// Adiciona tabela ao documento
//			Phrase linha = new Phrase();
//			linha.add(tabelaRelatorio);
//			documento.add(linha);
////			ItextUtil.adicionaTabelaAoConteudo(listaAgendamento);
			
//			String data = DateUtils.format(new Date(), DateUtils.DD_MM_YYYY);

//			pdfByte = ItextUtil.getPdfByte();
			
			// Fechar documento
//			documento.close();

//		} catch (DocumentException de) {
//			de.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}
}
