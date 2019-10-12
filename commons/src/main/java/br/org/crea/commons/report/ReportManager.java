package br.org.crea.commons.report;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

public class ReportManager {

	@Inject
	ReportCreaImpl report;

	@Inject
	private Properties properties;

	private String pathTemp;

	@PostConstruct
	public void before() {
		this.pathTemp = properties.getProperty("file.temp");
	}

	@PreDestroy
	public void reset() {
		properties.clear();
	}
	
	public String httpPreview(List<Map<String, Object>> params, String template, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IOException, ParserConfigurationException {
		ReportCrea relatorio = new ReportCrea().pathTemp(pathTemp).objectDataSource(params).template(template).pdf();
		return report.preview(relatorio, request);
	}
	public String httpPreviewArt(List<Map<String, Object>> params, String template, HttpServletRequest request) throws IllegalArgumentException, IllegalAccessException, IOException, ParserConfigurationException {
		ReportCrea relatorio = new ReportCrea().pathTemp(pathTemp).objectDataSourceArt(params).template(template).pdf();
		return report.preview(relatorio, request);
	}

	@SuppressWarnings("unchecked")
	public byte[] exportPdfParaListDataSource(List<? extends Object> list, String template) {
		ReportCrea relatorio = new ReportCrea().listDataSource((List<Object>) list).template(template).pdf();
		return report.pdf(relatorio);
	}

	public byte[] objectDataSourceExportPdf(List<Map<String, Object>> params, String template) throws IllegalArgumentException, IllegalAccessException, IOException{
		ReportCrea relatorio = new ReportCrea().pathTemp(pathTemp).objectDataSource(params).template(template).pdf();
		
		byte[] pdfFile = report.pdf(relatorio);
		limpaArquivoTemporario(relatorio.getTempFile());
		
		return pdfFile;
	}

	@SuppressWarnings("unchecked")
	public byte[] exportXls(List<? extends Object> list, String template) {
		ReportCrea relatorio = new ReportCrea().listDataSource((List<Object>) list).template(template).xls();
		return report.exportXls(relatorio);
	}

	public void limpaArquivoTemporario(File fileTemp) {
		
		if(fileTemp != null) {
			fileTemp.delete();
		}
	}

}
