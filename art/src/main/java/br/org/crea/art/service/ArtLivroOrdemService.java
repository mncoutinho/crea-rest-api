package br.org.crea.art.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;

import br.org.crea.commons.converter.art.ArtLivroOrdemConverter;
import br.org.crea.commons.dao.art.ArtLivroOrdemDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.models.art.ArtLivroOrdem;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.dtos.ArtLivroDeOrdemDto;
import br.org.crea.commons.models.art.dtos.PesquisaArtDto;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.ItextUtil;
import br.org.crea.commons.util.PDFUtils;
import br.org.crea.commons.util.StorageUtil;

public class ArtLivroOrdemService {

	@Inject ArtLivroOrdemConverter converter;
	
	@Inject ArtLivroOrdemDao dao;
	
	@Inject ContratoArtDao contratoArtDao;
	
	@Inject	private Properties properties;
	
	private String url;
	
	Font regular = FontFactory.getFont(FontFactory.COURIER, (float) 12,	Font.NORMAL);
	Font bold = FontFactory.getFont(FontFactory.COURIER, (float) 12,Font.BOLD);
	
	
	@PostConstruct
	public void before () {
	 this.url = properties.getProperty("corporativo.url");
	}
	
	@PreDestroy
	public void reset () {
		properties.clear();
	}

	public List<ArtLivroDeOrdemDto> getByArt(String numeroArt) {
		return converter.toListDto(dao.getByART(numeroArt));
	}
	
	public List<ArtLivroDeOrdemDto> getByArtPaginado(PesquisaArtDto pesquisa) {
		return converter.toListDto(dao.getByArtPaginado(pesquisa));
	}

	public int getTotalDeRegistrosDaPesquisa(PesquisaArtDto pesquisa) {
		return dao.getTotalDeRegistrosDaPesquisa(pesquisa);
	}
	
	public List<String> cadastrar(ArtLivroDeOrdemDto dto) {
		List<String> mensagens = new ArrayList<>();
		try {
			ArtLivroOrdem artLivroOrdem = converter.toModel(dto); 
			mensagens = comparaDatasDoLivroDeOrdemComOContrato(artLivroOrdem);
			if(mensagens.isEmpty()) dao.create(artLivroOrdem);
		} catch (Exception e) {
			mensagens.add("Erro ao cadastrar o livro de ordem. Por favor tente novamente.");
			return mensagens;
		}
		return mensagens;
	}
	
	public List<String> atualizar(ArtLivroDeOrdemDto dto) {
		List<String> mensagens = new ArrayList<>();
		try {
			ArtLivroOrdem artLivroOrdem = converter.toModel(dto);
			mensagens = comparaDatasDoLivroDeOrdemComOContrato(artLivroOrdem);
			if(mensagens.isEmpty()) dao.update(artLivroOrdem);
		}catch (Exception e) {
			mensagens.add("Erro ao atualizar o livro de ordem. Por favor tente novamente.");
			return mensagens;
		}
		return mensagens;
	}
	
	private List<String> comparaDatasDoLivroDeOrdemComOContrato(ArtLivroOrdem artLivroOrdem){
		List<String> mensagens = new ArrayList<>();
		ContratoArt contratoArt = contratoArtDao.getPrimeiroContratoPor(artLivroOrdem.getArt().getNumero());
		if(DateUtils.primeiraDataeMenorQueSegunda(artLivroOrdem.getDataInicioDaEtapa(), contratoArt.getDataInicio())) 
			mensagens.add("A data de início da etapa não pode ser inferior a data de ínicio do contrato de obra ou serviço.");
		if(DateUtils.primeiraDataeMaiorQueSegunda(artLivroOrdem.getDataInicioDaEtapa(), contratoArt.getDataFim())) 
			mensagens.add("A data de início da etapa não pode ser superior a data de término do contrato de obra ou serviço.");
		
		if (artLivroOrdem.temDataConclusao()) {
			if(DateUtils.primeiraDataeMenorQueSegunda(artLivroOrdem.getDataConclusao(), contratoArt.getDataInicio())) 
				mensagens.add("A data de conclusão da etapa não pode ser inferior a data de início do contrato de obra ou serviço.");
			if(DateUtils.primeiraDataeMaiorQueSegunda(artLivroOrdem.getDataConclusao(), contratoArt.getDataFim())) 
				mensagens.add("A data de conclusão da etapa não pode ser superior a data de prevista de término do contrato de obra ou serviço.");
		}
		
		return mensagens;
	}
	
	public boolean deletar(String codigo) {
		try {
			dao.deleta(Long.parseLong(codigo));
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	public boolean deletarArquivo(String idArquivo) {
		try {
			return dao.deletaArquivo(Long.parseLong(idArquivo));
		} catch (Exception e) {
			return false;
		}
	}

	public byte[] download(String numeroArt, String registro) {
		try {
			StorageUtil.setPrivado(true);
			List<ArtLivroDeOrdemDto> listaLivroDeOrdem = converter.toListDto(dao.getByART(numeroArt));			
			PDFUtils pdfUtil = new PDFUtils();
			pdfUtil.adicionar(gerarByteArrayPDFdaArtDoLegado(numeroArt,registro));
			listaLivroDeOrdem.forEach(livro ->{
				pdfUtil.adicionar(gerarDocumento(livro));
				if(livro.temArquivo()) {
					try {
						byte[] anexo = IOUtils.toByteArray(new FileInputStream(StorageUtil.getRaizStorage() + livro.getArquivo().getCaminhoStorage()));
						pdfUtil.adicionar(anexo);
					}catch (Exception e) {
					}
				}
			});
			return pdfUtil.concatenar().buildFile();
		}catch (Exception e) {
			return null;
		}
	}
	
	private byte[] gerarDocumento(ArtLivroDeOrdemDto livro) {
		ItextUtil.iniciarDocumentoParaDownload("LIVRO DE ORDEM - ART", ItextUtil.RETRATO);
		ItextUtil.adicionaCabecalhoPadrao();
		ItextUtil.adicionaRodapePadrao();
		ItextUtil.iniciarDocumento();
		ItextUtil.adicionaParagrafoAoConteudoSemNomeDoArquivo(preencherDocumento(livro));
		ItextUtil.fecharDocumento();
		return ItextUtil.getPdfBytes();
	}
			
	private Paragraph preencherDocumento(ArtLivroDeOrdemDto artLivroOrdem) {
		try {
			Paragraph texto = new Paragraph();
			PdfPTable tabelaDatas = new PdfPTable(2);
			tabelaDatas.setWidths(new float[]{3,3});
			tabelaDatas.addCell(new Phrase("Data início:", bold));
			tabelaDatas.addCell(new Phrase("Data conclusão", bold));
			tabelaDatas.setHeaderRows(1);
			tabelaDatas.setWidthPercentage(100);
			tabelaDatas.addCell(new Phrase(artLivroOrdem.getDataInicioDaEtapaFormatada() == null ? "-" : artLivroOrdem.getDataInicioDaEtapaFormatada() ,regular));
			tabelaDatas.addCell(new Phrase(artLivroOrdem.getDataConclusaoFormatada() == null ? "-" : artLivroOrdem.getDataConclusaoFormatada(),regular));
			texto.add(tabelaDatas);
			PdfPTable tabelaRelato = new PdfPTable(1);
			tabelaRelato.setWidths(new float[] {3});
			tabelaRelato.addCell(new Phrase("Relato visita:",bold));
			tabelaRelato.setHeaderRows(1);
			tabelaRelato.setWidthPercentage(100);
			tabelaRelato.addCell(new Phrase(artLivroOrdem.getRelatoVisitaResponsavelTecnico() == null ? "-" : artLivroOrdem.getRelatoVisitaResponsavelTecnico(),regular));
			texto.add(tabelaRelato);
			PdfPTable tabelaOrientacao = new PdfPTable(1);
			tabelaOrientacao.setWidths(new float[] {3});
			tabelaOrientacao.addCell(new Phrase("Orientação:",bold));
			tabelaOrientacao.setHeaderRows(1);
			tabelaOrientacao.setWidthPercentage(100);
			tabelaOrientacao.addCell(new Phrase(artLivroOrdem.getOrientacao() == null ? "-" : artLivroOrdem.getOrientacao(),regular));
			texto.add(tabelaOrientacao);
			PdfPTable tabelaAcidentes = new PdfPTable(1);
			tabelaAcidentes.setWidths(new float[] {3});
			tabelaAcidentes.addCell(new Phrase("Acidentes:",bold));
			tabelaAcidentes.setHeaderRows(1);
			tabelaAcidentes.setWidthPercentage(100);
			tabelaAcidentes.addCell(new Phrase(artLivroOrdem.getAcidentesDanosMateriais() == null ? "-" : artLivroOrdem.getAcidentesDanosMateriais(),regular));
			texto.add(tabelaAcidentes);
			PdfPTable tabelaContratado = new PdfPTable(1);
			tabelaContratado.setWidths(new float[] {3});
			tabelaContratado.addCell(new Phrase("Contratado ou SubContratado:",bold));
			tabelaContratado.setHeaderRows(1);
			tabelaContratado.setWidthPercentage(100);
			tabelaContratado.addCell(new Phrase(artLivroOrdem.getNomeContratado() == null ? "-" : artLivroOrdem.getNomeContratado(),regular));
			texto.add(tabelaContratado);
			PdfPTable tabelaInterrupcoes = new PdfPTable(1);
			tabelaInterrupcoes.setWidths(new float[] {3});
			tabelaInterrupcoes.addCell(new Phrase("Interrupções:",bold));
			tabelaInterrupcoes.setHeaderRows(1);
			tabelaInterrupcoes.setWidthPercentage(100);
			tabelaInterrupcoes.addCell(new Phrase(artLivroOrdem.getPeriodosInterrupcaoEMotivos() == null ? "-" : artLivroOrdem.getPeriodosInterrupcaoEMotivos(),regular));
			texto.add(tabelaInterrupcoes);
			PdfPTable tabelaObservacoes = new PdfPTable(1);
			tabelaObservacoes.setWidths(new float[] {3});
			tabelaObservacoes.addCell(new Phrase("Observações:",bold));
			tabelaObservacoes.setHeaderRows(1);
			tabelaObservacoes.setWidthPercentage(100);
			tabelaObservacoes.addCell(new Phrase(artLivroOrdem.getOutrosFatosEObservacoes() == null ? "-" : artLivroOrdem.getOutrosFatosEObservacoes(),regular));
			texto.add(tabelaObservacoes);
			return texto;	
		}catch (Exception e) {
			return null;
		}
	}
	
	public byte[] gerarByteArrayPDFdaArtDoLegado(String numeroArt, String registroProfissional) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			HttpGet httpGet = new HttpGet(url+"/creaOnLine/home/realizarAtendimentoPublico.do?funcao=imprimirARTpdfPortal&art="+numeroArt+"&id="+registroProfissional);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity e = response.getEntity();
			if (e != null) {
				e.writeTo(baos);
				return baos.toByteArray();
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return null;
	}
	
}
