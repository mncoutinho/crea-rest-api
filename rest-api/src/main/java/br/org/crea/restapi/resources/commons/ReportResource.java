package br.org.crea.restapi.resources.commons;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.service.ReportService;
import br.org.crea.commons.util.ResponseRestApi;
@Resource
@Path("/commons/report")
public class ReportResource {

	@Inject ReportService reportService;
	
	@Inject	ResponseRestApi response;
	
	@POST
	@Path("pdf") 
	public Response pdf(@Context HttpServletRequest request, DocumentoDto documentoDto) throws IllegalArgumentException, IllegalAccessException, IOException {
		return reportService.exportPdf(request, documentoDto);
	}
	
	@POST
	@Path("preview") 
	@Publico
	public Response preview(@Context HttpServletRequest request, DocumentoDto dto) throws IllegalArgumentException, IllegalAccessException, IOException, ParserConfigurationException {
		return reportService.preview(request, dto);
	}
	
}
