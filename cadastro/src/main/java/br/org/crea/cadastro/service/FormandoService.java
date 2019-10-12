package br.org.crea.cadastro.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;

import br.org.crea.commons.converter.cadastro.FormandoConverter;
import br.org.crea.commons.dao.cadastro.FormandoDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaFisicaDao;
import br.org.crea.commons.dao.corporativo.GeradorSequenciaDao;
import br.org.crea.commons.models.cadastro.dtos.pessoa.FormandoDto;
import br.org.crea.commons.models.commons.DestinatarioEmailDto;
import br.org.crea.commons.models.commons.dtos.ArquivoFormUploadDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Formando;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.EmailEvent;
import br.org.crea.commons.util.ExcelPoiUtil;
import br.org.crea.commons.util.ItextUtil;

public class FormandoService {
	
	@Inject FormandoDao dao;
	
	@Inject PessoaFisicaDao pessoaaFisicaDao;
	
	@Inject GeradorSequenciaDao geradorSequenciaDao;
	
	@Inject FormandoConverter converter;
	
	
	public byte[] gerarPDFComListaDeProcessados(FormandoDto dto) {
		
		ItextUtil.iniciarDocumentoParaDownload("FORMANDO(S) CADASTRADO(S)", ItextUtil.RETRATO);
		ItextUtil.adicionaCabecalhoPadrao();
		ItextUtil.adicionaRodapePadrao();
		ItextUtil.iniciarDocumento();
		ItextUtil.adicionaObjetoAoConteudoSemNomeDoArquivo(geraTitulo(dto));
		ItextUtil.adicionaObjetoAoConteudoSemNomeDoArquivo(geraPrimeiroParagrafo(dto));
		if(dto.temFormandosProcessadoComSucesso()) {
			ItextUtil.adicionaObjetoAoConteudoSemNomeDoArquivo(geraSegundoParagrafoProcessadosComSucesso(dto));
		}
		ItextUtil.adicionaObjetoAoConteudoSemNomeDoArquivo(geraTabelaProcessadosComSucessoConteudoItext(dto));
		if(dto.temFormandosProcessadoComErro()) {
			ItextUtil.adicionaObjetoAoConteudoSemNomeDoArquivo(geraTerceiroParagrafoProcessadosSemSucesso(dto));
		}
		ItextUtil.adicionaObjetoAoConteudoSemNomeDoArquivo(geraTabelaProcessadosSemSucessoConteudoItext(dto));
		ItextUtil.fecharDocumento();
		
		return ItextUtil.getPdfBytes();
	}
	
	private Object geraTitulo (FormandoDto dto) {
		Paragraph textoTopo = null;
		try {
			textoTopo = new Paragraph("\n\n Protocolo número "+dto.getProtocolo()+" referente ao cadastro de formandos.\n\n"
									);
			textoTopo.setFont(FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD));
			textoTopo.setAlignment(Element.ALIGN_LEFT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return textoTopo;
		
	}
	
	private Object geraPrimeiroParagrafo (FormandoDto dto) {
		Paragraph textoTopo = null;
		try {
			textoTopo = new Paragraph("Instituição: "+dto.getInstituicao().getNome()+"\n\n"
									+ "Campus: "+dto.getCampus().getNome()+"\n\n"
									+ "Curso: "+dto.getCurso().getNome()+".\n\n");
			textoTopo.setFont(FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.BOLD));
			textoTopo.setAlignment(Element.ALIGN_LEFT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return textoTopo;
		
	}
	
	private Object geraSegundoParagrafoProcessadosComSucesso (FormandoDto dto) {
		Paragraph textoTopo = null;
		try {
			textoTopo = new Paragraph("Formando(s) cadastro(s) com sucesso.\n");
			textoTopo.setFont(FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.BOLD));
			textoTopo.setAlignment(Element.ALIGN_LEFT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return textoTopo;
		
	}
	
	private Object geraTerceiroParagrafoProcessadosSemSucesso (FormandoDto dto) {
		Paragraph textoTopo = null;
		try {
			textoTopo = new Paragraph("\n Formando(s) não cadastro(s).\n");
			textoTopo.setFont(FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.BOLD));
			textoTopo.setAlignment(Element.ALIGN_LEFT);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return textoTopo;
		
	}
	
	private Object geraTabelaProcessadosComSucessoConteudoItext(FormandoDto dto) {

		PdfPTable tabelaRelatorio = new PdfPTable(4);
		
		try {
			tabelaRelatorio.setWidths(new float[]{2,3,3,3});
			
			Font fonteCabecalhoCelula = FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD);
			tabelaRelatorio.addCell(new Phrase("CPF", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("Nome", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("Data de colação de grau", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("Status", fonteCabecalhoCelula));
			tabelaRelatorio.setHeaderRows(1);
			tabelaRelatorio.setWidthPercentage(100);
				
			Font fonteCelula = FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.NORMAL);
			dto.getFormandosProcessadoComSucesso().forEach(formando ->{
				tabelaRelatorio.addCell(new Phrase(formando.getCpf(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(formando.getNome(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(formando.temDataFormaturaPlanilha() ? formando.getDataFormaturaPlanilha() : formando.getDataFormaturaFormatada(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(formando.getStatus().getDescricao(), fonteCelula));
			});
				
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return tabelaRelatorio;
	}
	
	private Object geraTabelaProcessadosSemSucessoConteudoItext(FormandoDto dto) {
		
		PdfPTable tabelaRelatorio = new PdfPTable(4);
		
		try {
			tabelaRelatorio.setWidths(new float[]{2,3,3,3});
			
			Font fonteCabecalhoCelula = FontFactory.getFont(FontFactory.COURIER, (float) 9.0, Font.BOLD);
			tabelaRelatorio.addCell(new Phrase("CPF", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("Nome", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("Data de formatura", fonteCabecalhoCelula));
			tabelaRelatorio.addCell(new Phrase("Status", fonteCabecalhoCelula));
			tabelaRelatorio.setHeaderRows(1);
			tabelaRelatorio.setWidthPercentage(100);
				
			Font fonteCelula = FontFactory.getFont(FontFactory.COURIER, (float) 8.0, Font.NORMAL);
			
			dto.getFormandosProcessadoComErro().forEach(formando ->{
				tabelaRelatorio.addCell(new Phrase(formando.getCpf(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(formando.getNome(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(formando.temDataFormaturaPlanilha() ? formando.getDataFormaturaPlanilha() : formando.getDataFormaturaFormatada(), fonteCelula));
				tabelaRelatorio.addCell(new Phrase(formando.getStatus().getDescricao(), fonteCelula));
			});
				
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return tabelaRelatorio;
	}
	
	public FormandoDto cadastra(FormandoDto formandoDto, UserFrontDto userFrontDto) {
		DomainGenericDto status = new DomainGenericDto();
		Formando formando = converter.toModel(formandoDto);
		formando.setMatriculaCadastro(userFrontDto.getIdPessoa());
		
		formando.setDataCadastro(new Date());
		formando.setCadastroAtivado(true);
		if (dao.getByCPFAndCurso(formando.getCpfCnpj(), formandoDto.getCurso().getId()) != null){
			status.setId(2l);
			status.setDescricao("Formando ja cadastrado para o curso!");
			formandoDto.setStatus(status);
			return formandoDto;
		}
		
		PessoaFisica pessoaFisicaCadastrada = pessoaaFisicaDao.getByCPF(formando.getCpfCnpj());
		
		if(pessoaFisicaCadastrada == null) {
			//IMPORTADO DO LEGADO COMO GERAR O ID PESSOA
			Long idPessoa = null;
			int i = 0;
			while(i == 0) {
				idPessoa = geradorSequenciaDao.getSequenciaWithFlush(TipoPessoa.PROFISSIONAL.getOrdem());
				if(0 == idPessoa) idPessoa = geradorSequenciaDao.getSequenciaWithFlush(TipoPessoa.PROFISSIONAL.getOrdem());
				if(pessoaaFisicaDao.buscaPessoaFisicaById(idPessoa) == null){
					i = 1;
				}
			}
			formando.getPessoaFisica().setId(idPessoa);
			pessoaaFisicaDao.createWithOutFlush(formando.getPessoaFisica());
		}else{
			formando.setPessoaFisica(pessoaFisicaCadastrada);
		}
							
		dao.createWithOutFlush(formando);
		status.setId(1l);
		status.setDescricao("Cadastrado com sucesso!");
		formandoDto.setId(formando.getId());
		formandoDto.setStatus(status);
		formandoDto.setIdPessoa(formando.getIdPessoa());
		formandoDto.setDataFormaturaFormatada(DateUtils.format(formandoDto.getDataFormatura(), DateUtils.DD_MM_YYYY));
		
		return formandoDto;
	}
	
	public void remove(Long id) {
		dao.deleta(id);
	}
	
	public List<FormandoDto> getAllByFormandoDtoForGrid(FormandoDto dto){
		List<FormandoDto> listFormandoDto = new ArrayList<FormandoDto>();
		List<Formando> listFormandos = dao.getALlByFormandoDtoForGrid(dto);
		listFormandos.forEach(formando ->{
			listFormandoDto.add(converter.toDto(formando));
		});
		return listFormandoDto;
		
	}
	
	public boolean verificaSeOFormandoJaEstaCadastradoNoCurso(FormandoDto formandoDto) {
		if (dao.getByCPFAndCurso(formandoDto.getCpf(), formandoDto.getCurso().getId()) != null) return true;
		return false;
	}
	
	public void enviaDocumentoProtocoloPorEmail(FormandoDto dto) {
		try {
			
			byte[] documento = this.gerarPDFComListaDeProcessados(dto);
			
			EmailEnvioDto emailDto = new EmailEnvioDto();
			
			emailDto.setAssunto("FORMANDO(S) CADASTRADO(S) - INSTITUIÇÃO - "+dto.getInstituicao().getNome());
			emailDto.setEmissor("ricardo.goncalves@crea-rj.org.br");
			
			List<DestinatarioEmailDto> destinatarios = new ArrayList<DestinatarioEmailDto>();
			DestinatarioEmailDto destino = new DestinatarioEmailDto();
			destino.setNome("Rodrigo Cunha");
			destino.setEmail("rodrigo.fonseca@crea-rj.org.br");
			destinatarios.add(destino);
			
			InputStream inputStream = new ByteArrayInputStream(documento); 
			ArquivoFormUploadDto arquivoFormUploadDto = new ArquivoFormUploadDto();
			arquivoFormUploadDto.setDescricao("Formandos cadastrados");
			arquivoFormUploadDto.setFileName("formandos-cadastrados-"+dto.getInstituicao().getNome()+".pdf");
			arquivoFormUploadDto.setFile(inputStream);
			
			emailDto.setAnexos(Arrays.asList(arquivoFormUploadDto));
			
			emailDto.setDestinatarios(destinatarios);
			emailDto.setMensagem("<p align='Justify'>MENSAGEM DO CORPO DO E-MAIL</p>");
			
			EmailEvent evento = new EmailEvent();
			evento.consumeEvent(emailDto);
		}catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public byte[] gerarPlanilhaModelo(FormandoDto dto) {
		try {
			ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
			excelPoiUtil.setCell("cpf");
			excelPoiUtil.setCell("nome");
			excelPoiUtil.setCell("dataFormaturaPlanilha");
			excelPoiUtil.setCell("curso");
			excelPoiUtil.newRow();
			excelPoiUtil.setCell("");
			excelPoiUtil.setCell("");
			excelPoiUtil.setCell("");
			excelPoiUtil.setCell(dto.getCurso().getNome());
			return excelPoiUtil.buildToStream();
		}catch (Exception e) {
			return null;
		}
	}
	
	public FormandoDto consultar (FormandoDto formandoDto) {
		return dao.consultar(formandoDto);
	}
	
	public int getTotalConsulta(FormandoDto formandoDto) {
		return dao.getTotalConsulta(formandoDto);
	}
	
	public byte[] consultaExportarPlanilha (FormandoDto dto) {
		
		dto = dao.consultarParaPlanilha(dto);
		
		ExcelPoiUtil excelPoiUtil = new ExcelPoiUtil();
		excelPoiUtil.setCell("PROTOCOLO");
		excelPoiUtil.setCell("CPF");
		excelPoiUtil.setCell("NOME");
		excelPoiUtil.setCell("DATA COLAÇÃO DE GRAU");
		excelPoiUtil.setCell("INSTITUIÇÃO DE ENSINO");
		excelPoiUtil.setCell("CAMPUS");
		excelPoiUtil.setCell("CURSO");
		
		for(FormandoDto formandoDto : dto.getFormandos()) {
			excelPoiUtil.newRow();
			excelPoiUtil.setCell(formandoDto.getProtocolo());
			excelPoiUtil.setCell(formandoDto.getCpf());
			excelPoiUtil.setCell(formandoDto.getNome());
			excelPoiUtil.setCell(formandoDto.getDataFormaturaFormatada());
			excelPoiUtil.setCell(dto.getInstituicao().getNome());
			excelPoiUtil.setCell(formandoDto.getCampus().getNome());
			excelPoiUtil.setCell(formandoDto.getCurso().getNome());
		}
		
		return excelPoiUtil.buildToStream();
		
	}
}
