package br.org.crea.commons.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.crea.commons.converter.ReportConverter;
import br.org.crea.commons.converter.cadastro.domains.DocumentoConverter;
import br.org.crea.commons.converter.commons.ArquivoConverter;
import br.org.crea.commons.dao.DocumentoDao;
import br.org.crea.commons.dao.commons.ArquivoDao;
import br.org.crea.commons.dao.siacol.RlDocumentoProtocoloSiacolDao;
import br.org.crea.commons.factory.commons.AuditaDocumentoFactory;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.models.commons.Arquivo;
import br.org.crea.commons.models.commons.dtos.DocumentoGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.report.ReportManager;
import br.org.crea.commons.report.TemplateReportEnum;
import br.org.crea.commons.util.FileUtils;
import br.org.crea.commons.util.StorageUtil;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.commons.validsigner.enums.TipoDocumentoAssinaturaEnum;

public class DocumentoService {
	
	@Inject private ArquivoDao arquivoDao;
	
	@Inject private ArquivoConverter arquivoConverter;

	@Inject private DocumentoConverter converter;

	@Inject private DocumentoDao dao;
		
	@Inject private Properties properties;

	@Inject private ReportConverter reportConverter;

	@Inject private ReportManager reportManager;
	
	@Inject private HttpClientGoApi httpGoApi;
	
	@Inject private NumeroDocumentoService numeroDocumentoService;
	
	@Inject private RlDocumentoProtocoloSiacolDao rlDocumentoProtocoloDao;
	
	@Inject private AuditaDocumentoFactory audita;
	
	@Context HttpServletRequest request;

	private String pathTemplate;
	
	
	@PostConstruct
	public void before() {
		this.pathTemplate = properties.getProperty("path.template");
	}

	@PreDestroy
	public void reset() {
		properties.clear();
	}

	public DocumentoGenericDto recuperaDocumento(Long idDocumento) {
		return converter.toDto(dao.getBy(idDocumento));
	}

	public List<DocumentoGenericDto> recuperaDocumentosByNumeroProtocolo(Long numeroProtocolo) {
		return converter.toListDto(dao.recuperaDocumentosByNumeroProtocolo(numeroProtocolo));
	}

	public List<DocumentoGenericDto> recuperaDocumentosByIdTipoDocumento(Long idTipoDocumento) {
		return converter.toListDto(dao.getDocumentosByIdTipoDocumento(idTipoDocumento));
	}

	public DocumentoGenericDto salvaDocumento(DocumentoGenericDto dto, UserFrontDto usuario) {

		if (dto.isAssinado() && dto.getNumeroDocumento() == null) {
			dto.setNumeroDocumento(numeroDocumentoService.getProximoNumeroDocumento(dto.getDepartamento().getId(), dto.getTipo().getId()));
		}
		
		Documento documento = converter.toModel(dto);
		DocumentoGenericDto responseDto = converter.toDto(dao.create(documento));
		
		dto = responseDto;
		audita.auditaCriacao(dto, usuario);		
		
		if (dto.isAssinado()) {
			audita.auditaAssinatura(dto, usuario);
		}
		
		return responseDto;
	}

	public DocumentoGenericDto atualizaDocumento(DocumentoGenericDto dto, UserFrontDto usuario) {
		
		DocumentoGenericDto dtoAntigo = converter.toDto(dao.getBy(dto.getId()));
		
		if (dto.isAssinado() && dto.getNumeroDocumento() == null) {
			dto.setNumeroDocumento(numeroDocumentoService.getProximoNumeroDocumento(dto.getDepartamento().getId(), dto.getTipo().getId()));
			
			audita.auditaAssinatura(dto, usuario);
		}
		Documento documento = converter.toModel(dto);
		
		dao.update(documento);
		DocumentoGenericDto dtoNovo = converter.toDto(dao.getBy(documento.getId()));
		
		if (dto.isAssinado() && dto.getTipo().getId() == 1106) {
			atualizarProtocolosVistas(dto);
		}
		
		audita.auditaAtualizacao(dtoNovo, dtoAntigo, usuario);	
		
		return dtoNovo;		
	}
	
	public DocumentoGenericDto atualizaNumeroDocumento(DocumentoGenericDto dto, UserFrontDto usuario) {
		
		dao.update(converter.toModel(dto));
		
		return dto;		
	}

	private void atualizarProtocolosVistas(DocumentoGenericDto dto) {
		
		List<RlDocumentoProtocoloSiacol> listDocumentoProtocolo = new ArrayList<RlDocumentoProtocoloSiacol>();
		listDocumentoProtocolo = rlDocumentoProtocoloDao.getItensVistas(dto.getId());
		
		for (RlDocumentoProtocoloSiacol item : listDocumentoProtocolo) {
			item.setIdPessoaVista(rlDocumentoProtocoloDao.getIdPessoaVista(item.getProtocolo().getId()));
			rlDocumentoProtocoloDao.update(item);
		}
		
	}

	public void excluiDocumento(Long idDocumento, UserFrontDto usuario) {
		
		DocumentoGenericDto dto = converter.toDto(dao.getBy(idDocumento));
		
		dao.excluiDocumento(idDocumento);
		
		audita.auditaExclusao(dto, usuario);	
		
	}

	public DocumentoGenericDto assinaEnviaDocflow(DocumentoGenericDto dto) {
		Documento documento = converter.toModel(dto);
		return converter.toDto(dao.update(documento));
	}

	public DocumentoGenericDto recuperaByProtocoloDocumento(Long idProtocolo, Long idTipoDocumento) {
		return converter.toDto(dao.recuperaByProtocoloDocumento(idProtocolo, idTipoDocumento));
	}

	public Response exportPdfPorIdDocumento(HttpServletRequest request, Long idDocumento) throws IllegalArgumentException, IllegalAccessException, IOException {

		return Response.ok(montaDocumentoPdf(request, idDocumento))
				.header("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString() + ".pdf")
				.header("Content-Type", "application/pdf").type(MediaType.APPLICATION_OCTET_STREAM).build();
	}
	
	public byte[] montaDocumentoPdf(HttpServletRequest request, Long idDocumento) {
		
		try {
			
			DocumentoGenericDto documentoGenericDto = recuperaDocumento(idDocumento);
			DocumentoDto documento = converter.toDocumentoDto(documentoGenericDto);

			List<Map<String, Object>> params = new ArrayList<>();
			params = reportConverter.toMapJrBeanCollection(documento);
			
			TemplateReportEnum template = TemplateReportEnum.getTemplatePorNome(documentoGenericDto.getTipo().getTemplate());
			return reportManager.objectDataSourceExportPdf(params, template.getTemplate(request));
			
		} catch (Throwable e) {
			httpGoApi.geraLog("DocumentoService || montaDocumentoPdf", StringUtil.convertObjectToJson(idDocumento), e);
		}
		return null;
	}
	
	public DocumentoGenericDto geraArquivo(HttpServletRequest request, DocumentoGenericDto dto,  UserFrontDto usuario) {

		byte[] pdfBytesTemp;
		DocumentoGenericDto documentoGenericDto = recuperaDocumento(dto.getId());
		DocumentoDto dadosDoDocumentoParaPreencherTemplate = converter.toDocumentoDto(documentoGenericDto);
		
		try {
			List<Map<String, Object>> dadosDocumentoMapeado = reportConverter.toMapJrBeanCollection(dadosDoDocumentoParaPreencherTemplate);
	
			TemplateReportEnum template = TemplateReportEnum.getTemplatePorNome(documentoGenericDto.getTipo().getTemplate());
			pdfBytesTemp = reportManager.exportPdfParaListDataSource(dadosDocumentoMapeado, template.getTemplate(request));
			
			Arquivo arquivo = salvaArquivo(documentoGenericDto, usuario.getIdPessoa());
			
			org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(StorageUtil.getCaminhoCompleto()), pdfBytesTemp);
			
			StorageUtil.setPrivado(false);
			File file = new File(StorageUtil.getRaizStorage() + arquivo.getCaminhoStorage());
			FileOutputStream fileStream = new FileOutputStream(file, false); // false to overwrite.
			                                                                
			fileStream.write(pdfBytesTemp);
			fileStream.close();
			
			dao.atualizaReferenciaDoArquivoNoDocumento(arquivo.getId(), dto.getId());
			
			documentoGenericDto.setArquivo(arquivoConverter.toDto(arquivo));
			
		} catch (Exception e) {
			FileUtils.delete(StorageUtil.getCaminhoCompleto()); 
			httpGoApi.geraLog("DocumentoService || geraArquivo", StringUtil.convertObjectToJson(dto + " -- " + usuario), e);
		}
		return documentoGenericDto;
	}
	
	public Arquivo geraArquivoJob(Documento dto,  UserFrontDto usuario) {
		
		byte[] pdfBytesTemp;
		DocumentoGenericDto documentoGenericDto = converter.toDto(dto);
		DocumentoDto dadosDoDocumentoParaPreencherTemplate = converter.toDocumentoDto(documentoGenericDto);
		Arquivo arquivo = new Arquivo();
		
		try {
			List<Map<String, Object>> dadosDocumentoMapeado = reportConverter.toMapJrBeanCollection(dadosDoDocumentoParaPreencherTemplate);
	
			TemplateReportEnum template = TemplateReportEnum.getTemplatePorNome(documentoGenericDto.getTipo().getTemplate());
			pdfBytesTemp = reportManager.exportPdfParaListDataSource(dadosDocumentoMapeado, template.getTemplatePath(pathTemplate));
			
			org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(StorageUtil.getCaminhoCompleto()), pdfBytesTemp);
			
			arquivo = salvaArquivo(documentoGenericDto, usuario.getIdPessoa());
			
			StorageUtil.setPrivado(false);
			File file = new File(StorageUtil.getRaizStorage() + arquivo.getCaminhoStorage());
			FileOutputStream fileStream = new FileOutputStream(file, false); // false to overwrite.
			                                                                
			fileStream.write(pdfBytesTemp);
			fileStream.close();
			
//			atualizaReferenciaDoArquivoNoDocumento(arquivo.getId(), dto.getId());
//			dao.atualizaReferenciaDoArquivoNoDocumento(arquivo.getId(), dto.getId());

		} catch (Exception e) {
			System.out.println("error >>> " + e);
			FileUtils.delete(StorageUtil.getCaminhoCompleto()); 
			httpGoApi.geraLog("DocumentoService || geraArquivo", StringUtil.convertObjectToJson(dto + " -- " + usuario), e);
		}
		return arquivo;
	}

	private void atualizaReferenciaDoArquivoNoDocumento(Long idArquivo, Long idDocumento) {
		Documento documento = dao.getBy(idDocumento);
		documento.setArquivo(arquivoDao.getBy(idArquivo));
		dao.update(documento);
	}

	private Arquivo salvaArquivo(DocumentoGenericDto dto, Long idPessoa) {
		Arquivo arquivo = new Arquivo();
		
		if (dto.temArquivo()) {
			arquivo = arquivoConverter.toModel(dto.getArquivo());
			StorageUtil.populaArquivo(ModuloSistema.SIACOL, dto.getArquivo().getNomeStorage(), ".pdf", false, idPessoa, "");
			return arquivoDao.update(arquivo);
		} else {
			arquivo = StorageUtil.populaArquivo(ModuloSistema.SIACOL, StringUtil.randomUUID(), ".pdf", false, idPessoa, "");
			return arquivoDao.create(arquivo);
		}		
	}
	
	/**
	 * Método útil para verificar a existência de um documento a partir de um id.
	 * O id informado pode ser tanto da pk de documento ou da fk de arquivo relacionada na entidade 'documento'.
	 * 
	 * Criado para apoiar o processo de assinatura.
	 * @author Monique Santos
	 * @return true/false
	 * */
	public boolean documentoParaAssinaturaExiste(Long id, TipoDocumentoAssinaturaEnum tipoDocumentoAssinatura) {
		return dao.documentoParaAssinaturaExiste(id, tipoDocumentoAssinatura);
	}
	
	/**
	 * Grava a chave do Redis para recuperar o selo de assinatura do documento
	 * @author Monique Santos     
	 **/
	public void atualizaChaveAssinaturaRedisNoDocumento(Long idDocumento, String chaveRedis) {
		dao.atualizaChaveAssinaturaRedisNoDocumento(idDocumento, chaveRedis);
	}
	
	/**
	 * Salva a referência (codigo e protocolo) do documento no Docflow.
	 * @param idDocumento - identificador do documento
	 * @param codigoDocflow - codigo do documento no Docflow
	 * @param protocoloDocflow - protocolo do documento no Docflow
	 * */
	public void atualizaReferenciaDoDocflowNoDocumento(Long idDocumento, String codigoDocflow, String protocoloDocflow) {
		dao.atualizaReferenciaDoDocflowNoDocumento(idDocumento, codigoDocflow, protocoloDocflow);
	}

	public boolean liberarDocumento(Long idDocumento, UserFrontDto userDto) {
		try {
			Documento documento = dao.getBy(idDocumento);
			documento.setResponsavel(null);
			dao.update(documento);
			
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
}
