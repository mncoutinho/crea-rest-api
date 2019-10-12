package br.org.crea.siacol.relatorio;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;

import br.org.crea.commons.dao.commons.ArquivoDao;
import br.org.crea.commons.dao.siacol.RelatorioReuniaoSiacolDao;
import br.org.crea.commons.dao.siacol.RlReuniaoRelatorioSiacolDao;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.siacol.ReuniaoSiacol;
import br.org.crea.commons.models.siacol.RlReuniaoRelatorioSiacol;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.TipoRelatorioSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.ItextUtil;
import br.org.crea.commons.util.StorageUtil;
import br.org.crea.commons.util.StringUtil;

public class GeraRelatorioSiacol {
	
	@Inject
	private ArquivoDao arquivoDao;	
	
	@Inject
	private GeraConteudo geraConteudo;
	
	@Inject
	private RelatorioReuniaoSiacolDao relatorioDao;
	
	@Inject
	private RlReuniaoRelatorioSiacolDao rlReuniaoRelatorioDao;
	
	@Inject
	private HttpClientGoApi httpGoApi;
	
	public void gera(ReuniaoSiacolDto reuniao, TipoRelatorioSiacolEnum tipo) {
		
		try {

			switch (tipo) {
			case RELATORIO_INICIO_DA_REUNIAO:
				gerarRelatorio(reuniao.getId(), tipo, ItextUtil.RETRATO, geraConteudo.tabelaPresentes(relatorioDao.relatorioPresentesNoMomento(reuniao.getId())));
				break;
			case RELATORIO_INICIO_DE_QUORUM:
				gerarRelatorio(reuniao.getId(), tipo, ItextUtil.RETRATO, geraConteudo.tabelaPresentes(relatorioDao.relatorioPresentesNoMomento(reuniao.getId())));
				break;
			case RELATORIO_DE_PAUSA_DA_REUNIAO:
				gerarRelatorio(reuniao.getId(), tipo, ItextUtil.RETRATO, geraConteudo.tabelaPresentes(relatorioDao.relatorioPresentesNoMomento(reuniao.getId())));
				break;
			case RELATORIO_DE_CANCELAMENTO_DA_REUNIAO:
				gerarRelatorio(reuniao.getId(), tipo, ItextUtil.RETRATO, geraConteudo.tabelaPresentes(relatorioDao.relatorioPresentesNoMomento(reuniao.getId())));
				break;
			case RELATORIO_FIM_DE_QUORUM:
				gerarRelatorio(reuniao.getId(), tipo, ItextUtil.RETRATO, geraConteudo.tabelaPresentes(relatorioDao.relatorioPresentesNoMomento(reuniao.getId())));
				break;
			case RELATORIO_FIM_DA_REUNIAO:
				gerarRelatorio(reuniao.getId(), tipo, ItextUtil.RETRATO, geraConteudo.tabelaPresentes(relatorioDao.relatorioPresentesNoMomento(reuniao.getId())));
				break;
			case RELATORIO_DE_COMPARECIMENTO:
				if (reuniao.isHouvePausa()) {
					gerarRelatorioComTabelas(reuniao.getId(), tipo, ItextUtil.RETRATO, geraConteudo.tabelaComparecimentoComPausa(relatorioDao.relatorioDeComparecimentoComPausa(reuniao.getId(), reuniao.getParte().intValue())));
				} else {
					gerarRelatorio(reuniao.getId(), tipo, ItextUtil.PAISAGEM, geraConteudo.tabelaComparecimento(relatorioDao.relatorioDeComparecimento(reuniao.getId())));
				}			
				break;
			case RELATORIO_80_PORCENTO:
				if (!rlReuniaoRelatorioDao.existeRelatorio(reuniao.getId(), TipoRelatorioSiacolEnum.RELATORIO_80_PORCENTO)) {
					gerarRelatorio(reuniao.getId(), TipoRelatorioSiacolEnum.RELATORIO_80_PORCENTO, ItextUtil.RETRATO, geraConteudo.tabelaOitentaPorcento(relatorioDao.relatorioDerelatorioDeOitentaPorcento(reuniao.getId())));
				} else {
					sobrescreveRelatorio(reuniao.getId(), TipoRelatorioSiacolEnum.RELATORIO_80_PORCENTO, ItextUtil.RETRATO, geraConteudo.tabelaOitentaPorcento(relatorioDao.relatorioDerelatorioDeOitentaPorcento(reuniao.getId())));
				}
				break;

			default:
				break;
			
			}
		} catch (Exception e) {
			httpGoApi.geraLog("GeraRelatorioSiacol || gera", StringUtil.convertObjectToJson(reuniao), e);
		}
		
	}

	public void gerarRelatorio(Long idReuniao, TipoRelatorioSiacolEnum tipo, String orientacao, PdfPTable tabela) {
		
		String nomeArquivo = idReuniao + " - " + tipo.getNome();
		Arquivo arquivo = StorageUtil.populaArquivo(ModuloSistema.SIACOL, nomeArquivo, StorageUtil.EXTENSAO_PDF, StorageUtil.PRIVADO, new Long(99990), "");
		
		ItextUtil.iniciarDocumentoParaArquivo(nomeArquivo, orientacao, StorageUtil.getCaminhoCompleto());
		ItextUtil.adicionaCabecalhoPadrao();
		ItextUtil.adicionaRodapePadrao();
		ItextUtil.iniciarDocumento();
		ItextUtil.adicionaObjetoAoConteudoEFecha(tabela);
		
		arquivo = arquivoDao.create(arquivo);
		
		// Criar linha referenciando cad arquivo na rlrelatorio
		RlReuniaoRelatorioSiacol relatorio = new RlReuniaoRelatorioSiacol();
		
		relatorio.setRelatorio(arquivo);
		
		ReuniaoSiacol reuniao = new ReuniaoSiacol();
		reuniao.setId(idReuniao);
		relatorio.setReuniao(reuniao);
		relatorio.setTipo(tipo);
		
		rlReuniaoRelatorioDao.create(relatorio);
	}
	
	public void sobrescreveRelatorio(Long idReuniao, TipoRelatorioSiacolEnum tipo, String orientacao, PdfPTable tabela) {
		
		String nomeArquivo = idReuniao + " - " + tipo;
		
		RlReuniaoRelatorioSiacol rlRelatorio = rlReuniaoRelatorioDao.getRelatorioPor(idReuniao, tipo);
		
		ItextUtil.iniciarDocumentoParaArquivo(nomeArquivo, orientacao, StorageUtil.getRaizStoragePrivada() + rlRelatorio.getRelatorio().getCaminhoStorage());
		ItextUtil.adicionaCabecalhoPadrao();
		ItextUtil.adicionaRodapePadrao();
		ItextUtil.iniciarDocumento();
		ItextUtil.adicionaObjetoAoConteudoEFecha(tabela);
		
	}
	
	public void gerarRelatorioComTabelas(Long idReuniao, TipoRelatorioSiacolEnum tipo, String orientacao, List<PdfPTable> tabelas) {
		
		String nomeArquivo = idReuniao + " - " + tipo.getNome();
		Arquivo arquivo = StorageUtil.populaArquivo(ModuloSistema.SIACOL, nomeArquivo, StorageUtil.EXTENSAO_PDF, StorageUtil.PRIVADO, new Long(99990), "");
		
		ItextUtil.iniciarDocumentoParaArquivo(nomeArquivo, orientacao, StorageUtil.getCaminhoCompleto());
		ItextUtil.adicionaCabecalhoPadrao();
		ItextUtil.adicionaRodapePadrao();
		ItextUtil.iniciarDocumento();
		
		Paragraph textoTopo = new Paragraph(nomeArquivo + " - " + DateUtils.format(new Date(), DateUtils.DD_MM_YYYY_HH_MM_SS) + "\n\n");
		textoTopo.setFont(FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD));
		textoTopo.setAlignment(Element.ALIGN_CENTER);
		
		ItextUtil.adicionaObjetoAoConteudo(textoTopo);
		int i = 0;
		for (PdfPTable tabela : tabelas) {
			if (i != 0) {
				textoTopo = new Paragraph("PARTE: "+i+"\n\n");
				textoTopo.setFont(FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD));
				textoTopo.setAlignment(Element.ALIGN_CENTER);
				
				ItextUtil.adicionaObjetoAoConteudo(textoTopo);
			}
			ItextUtil.adicionaObjetoAoConteudo(tabela);
			i++;
		}		
		ItextUtil.fecharDocumento();
		
		arquivo = arquivoDao.create(arquivo);
		
		// Criar linha referenciando cad arquivo na rlrelatorio
		RlReuniaoRelatorioSiacol relatorio = new RlReuniaoRelatorioSiacol();
		
		relatorio.setRelatorio(arquivo);
		
		ReuniaoSiacol reuniao = new ReuniaoSiacol();
		reuniao.setId(idReuniao);
		relatorio.setReuniao(reuniao);
		relatorio.setTipo(tipo);
		
		rlReuniaoRelatorioDao.create(relatorio);
	}


}


