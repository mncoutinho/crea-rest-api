package br.org.crea.commons.report;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class ReportCreaImpl {

	@Inject
	HttpClientGoApi httpGoApi;

	public byte[] pdf(ReportCrea relatorio) {
		byte[] pdf = null;
		try {
			JasperPrint jasperPrint = compilaJasper(relatorio);
			pdf = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			httpGoApi.geraLog("ReportCreaImpl || pdf", StringUtil.convertObjectToJson(relatorio), e);
		}
		return pdf;
	}
	
//	public String preview(ReportCrea relatorio) throws InvalidPasswordException, IOException, ParserConfigurationException {
//		byte[] pdf = null;
//		StringWriter outputStream = new StringWriter();
//		
//		try {
//			
//			JasperPrint jasperPrint = compilaJasper(relatorio);
//			pdf = JasperExportManager.exportReportToPdf(jasperPrint);
//			
//			PDFDomTree parser = new PDFDomTree();
//			PDDocument documentHtml = PDDocument.load(pdf);
//			
//			parser.writeText(documentHtml, outputStream);
//			
//			String result = outputStream.toString();			
//			outputStream.close();
//			return result;
//			
//		} catch (Throwable e) {
//			httpGoApi.geraLog("ReportCreaImpl || pdf", StringUtil.convertObjectToJson(relatorio), e);
//		}
//		return null;
//	}
	
	public String preview(ReportCrea relatorio, HttpServletRequest request) {
		try {
			ByteArrayOutputStream exported = new ByteArrayOutputStream();

			HtmlExporter exporter = new HtmlExporter();

			byte[] bytes = null;
			JasperPrint jasperPrint = compilaJasper(relatorio);

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

			SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(exported);
			output.setImageHandler(new WebHtmlResourceHandler("http://portalservicos.crea-rj.org.br/images/brazil.jpg"));
			exporter.setExporterOutput(output);

			exporter.exportReport();
			bytes = exported.toByteArray();

			return new String(bytes, "utf-8");
			
		} catch (JRException e) {
			httpGoApi.geraLog("ReportCreaImpl || preview", StringUtil.convertObjectToJson(relatorio), e);
		} catch (UnsupportedEncodingException e) {
			httpGoApi.geraLog("ReportCreaImpl || preview", StringUtil.convertObjectToJson(relatorio), e);
		} catch (Exception e) {
			httpGoApi.geraLog("ReportCreaImpl || preview", StringUtil.convertObjectToJson(relatorio), e);
		}

		return null;
	}

	public byte[] exportXls(ReportCrea relatorio) {

		byte[] xls = null;

		ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
		JRXlsExporter exporter = new JRXlsExporter();

		try {
			JasperPrint jasperPrint = compilaJasper(relatorio);

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));

			SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
			xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
			xlsReportConfiguration.setRemoveEmptySpaceBetweenColumns(Boolean.TRUE);
			xlsReportConfiguration.setWhitePageBackground(Boolean.TRUE);
			xlsReportConfiguration.setDetectCellType(Boolean.TRUE);
			xlsReportConfiguration.setOnePagePerSheet(Boolean.FALSE);

			exporter.setConfiguration(xlsReportConfiguration);
			exporter.exportReport();

			xls = xlsReport.toByteArray();

		} catch (JRException e) {
			httpGoApi.geraLog("ReportCreaImpl || exportXls", StringUtil.convertObjectToJson(relatorio), e);
		}
		return xls;
	}

	private JasperPrint compilaJasper(ReportCrea relatorio) {
		JasperPrint jasperPrint = null;
		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(relatorio.getTemplate());
			jasperPrint = JasperFillManager.fillReport(jasperReport, relatorio.getParams(), relatorio.getSource());
			return jasperPrint;
		} catch (Exception e) {
			httpGoApi.geraLog("ReportCreaImpl || compilaJasper", StringUtil.convertObjectToJson(relatorio), e);
		}
		return jasperPrint;
	}

}

