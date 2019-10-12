package br.org.crea.restapi.report;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import br.org.crea.commons.converter.ReportConverter;
import br.org.crea.commons.models.cadastro.dtos.DocumentoDto;
import br.org.crea.commons.report.ReportCrea;
import br.org.crea.commons.report.ReportCreaImpl;
import br.org.crea.commons.report.TemplateReportEnum;

public class TemplateProfissionalCumpExigenciaTest {

	
	private String template = "/home/monique/projects/java/crea_api/crea-rest-api/rest-api/src/main/webapp/WEB-INF/templates/protocolo/oficio-profissional-cumprimento-exigencia.jrxml";

	private ReportCrea reportCrea;
	private ReportConverter converter;
	
	@Test
	public void geraPDF() throws IOException {
		
		List<Map<String, Object>> params = new ArrayList<>();
		converter = new ReportConverter();
		
		ReportCreaImpl gera = new ReportCreaImpl();
		byte[] bytes = null;
		
		try {
			
			DocumentoDto oficio = populaOficio();
			params = converter.toMapJrBeanCollection(oficio);
			
			reportCrea = new ReportCrea().pathTemp("/opt/temp/").objectDataSource(params).template(template).pdf();
			bytes = gera.pdf(reportCrea);

			File file = new File("/opt/temp/prof-cumprimento-exigencia.pdf");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(bytes);
			bos.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private DocumentoDto populaOficio() throws FileNotFoundException{
		
		DocumentoDto dto = new DocumentoDto();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Prezado (a) Senhor(a), ");
		stringBuilder.append("\n\n");
		stringBuilder.append("A fim de darmos prosseguimento ao seu requerimento protocolado neste Conselho, vimos solicitar a V.S.a a apresentação da documentação abaixo relacionada no prazo de 30 (trinta) dias, a contar do recebimento deste, na Rua Buenos Aires, 40–Térreo, no horário de 9 h às 17h30 min. ou pela EBCT para o endereço constante doimpresso.");
		stringBuilder.append("\n\n");		
		stringBuilder.append("» Apresentar original e cópia do Histórico Escolar do curso de Engenharia Mecânica.");		
		stringBuilder.append("\n\n");		
		stringBuilder.append("Atenciosamente, ");		

		System.out.println(stringBuilder.toString());
		
		dto.setNumeroDocumento("0001/2017/CREA-RJ");
		dto.setDataDocumento("Rio de Janeiro, 20 de Agosto de 2017.");
		dto.setOficiado("FREDERICO ALBERTO BOTELHO");
		dto.setEnderecoDescritivo("RUA OTAVIO CARNEIRO - 134 " + "\n"
									  + "APTO - 302 " 				 + "\n"
									  + "ICARAI "                    + "\n"  
									  + "NITEROI-RJ " 	             + "\n"
									  + "24230191 ");
		dto.setAssunto("Solicitação");
		dto.setReferencia("Processo no 2013131307");
		dto.setTextoPrincipal(stringBuilder.toString());
		
		StringBuilder sb = new StringBuilder();
		sb.append("ROSIANE MOULIN ");
		sb.append("\n");
		sb.append("COORDENADORA DE REGISTRO, CADASTRO E ACERVO TÉCNICO");
		dto.setCargo(sb.toString());
		dto.setTemplate(TemplateReportEnum.OFICIO_PROFISSIONAL_CUMPRIMENTO_EXIGENCIA);
		
		InputStream myInputStream = null;
		byte[] dataAssinatura = null;
		
		try {
			File initialFile = new File("/opt/images/ASS.jpg");
			myInputStream = new FileInputStream(initialFile);
			dataAssinatura = IOUtils.toByteArray(myInputStream);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		dto.setAssinatura(Base64.encodeBase64String(dataAssinatura));
		return dto;
	}
}
