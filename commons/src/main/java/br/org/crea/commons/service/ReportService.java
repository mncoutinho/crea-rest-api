package br.org.crea.commons.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import br.org.crea.commons.converter.ReportConverter;
import br.org.crea.commons.converter.cadastro.domains.DocumentoConverter;
import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.report.ReportManager;
import br.org.crea.commons.report.TemplateReportEnum;


public class ReportService {

	@Inject ReportConverter reportConverter;
	
	@Inject ReportManager reportManager ;
	
	@Inject DocumentoService documentoService;
	
	@Inject DocumentoConverter documentoConverter;
	
	@Inject HttpClientGoApi httpGoApi;
	
	public InputStream getDocumentoInputStream(HttpServletRequest request, Object objectDocumento) throws IllegalArgumentException, IllegalAccessException, IOException {

		List<Map<String, Object>> params = new ArrayList<>();
		byte[] bytesDocumento = null;
		InputStream input = null;

		params = reportConverter.toMapJrBeanCollection(objectDocumento);
		bytesDocumento = reportManager.objectDataSourceExportPdf(params, getModeloTemplate(objectDocumento).getTemplate(request));
		
		input = new ByteArrayInputStream(bytesDocumento);
		return input;
	}
	
	public TemplateReportEnum getModeloTemplate(Object object) throws IllegalArgumentException, IllegalAccessException {
		
		Field[] reportProperties = object.getClass().getDeclaredFields();
		for (Field propertie : reportProperties) {
			if (propertie.getName().equals("template")) {
				propertie.setAccessible(true);
				return (TemplateReportEnum) propertie.get(object);
			}
		}
		return null;
	}

	public Response exportPdf(HttpServletRequest request, DocumentoDto documentoDto) throws IllegalArgumentException, IllegalAccessException, IOException {
		
		List<Map<String, Object>> params = new ArrayList<>();
		params = reportConverter.toMapJrBeanCollection(documentoDto);

		return Response.ok(reportManager.objectDataSourceExportPdf(params, getModeloTemplate(documentoDto).getTemplate(request)))
				.header("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString() + ".pdf" )
				.header("Content-Type", "application/pdf").type(MediaType.APPLICATION_OCTET_STREAM).build();
	}

	public Response preview(HttpServletRequest request, DocumentoDto documentoDto) throws IllegalArgumentException, IllegalAccessException, IOException, ParserConfigurationException  {
		
		List<Map<String, Object>> params = new ArrayList<>();
		params = reportConverter.toMapJrBeanCollection(documentoDto);
		
		return Response.ok(reportManager.httpPreview(params, getModeloTemplate(documentoDto).getTemplate(request), request))
				       .header("Content-Type", "text/html").type(MediaType.TEXT_HTML).build();
	}
	
}
