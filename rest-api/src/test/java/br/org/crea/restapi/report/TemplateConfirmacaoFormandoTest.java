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

public class TemplateConfirmacaoFormandoTest {
	private String template = "/home/monique/projects/java/api-crea/crea-rest-api/rest-api/src/main/webapp/WEB-INF/templates/protocolo/oficio-ie-confirmacao-formando.jrxml";

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
			
			reportCrea = new ReportCrea().pathTemp("/opt/temp/").objectDataSource(params).template("").pdf();
			bytes = gera.pdf(reportCrea);

			File file = new File("/opt/temp/confirma-formando.pdf");
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
		stringBuilder.append("A fim de darmos prosseguimento ao processo de registro em tramitação neste Crea-RJ, solicitamos a V.Sª que nos seja confirmado se MONIQUE SANTOS e JOAO JOSE DA SILVA concluiu(ram) o curso de SISTEMAS DE INFORMACAO em 2017. ");
		stringBuilder.append("\n\n");		
		stringBuilder.append("Solicitamos ainda, por medida de segurança, que a resposta seja encaminhada pelos Correios e ou por e-mail e que faça referência ao nosso ofício (apoio.corc@crea-rj.org.br). ");		
		stringBuilder.append("\n\n");		
		stringBuilder.append("Na oportunidade, solicitamos a colaboração de V.Sª no sentido de enviar, ao término de cada período letivo, listagem contendo o nome dos concludentes de cursos cadastrados neste Crea-RJ com a respectiva data de conclusão, título profissional e número do CPF. ");
		stringBuilder.append("\n\n");		
		stringBuilder.append("Em face ao exposto, cientificamos essa instituição de ensino para que sejam tomadas as providências de requerer o cadastramento do curso/campi/unidade, a fim de permitir aos egressos a concessão de  registro profissional. ");	
		stringBuilder.append("\n\n");		
		stringBuilder.append("Atenciosamente, ");		

		System.out.println(stringBuilder.toString());
		
		dto.setNumeroDocumento("0001/2017/CREA-RJ");
		dto.setDataDocumento("Rio de Janeiro, 20 de Agosto de 2017.");
		dto.setOficiado("UNIVERSIDADE ESTÁCIO DE SÁ");
		dto.setEnderecoDescritivo("RUA OTAVIO CARNEIRO - 134 " + "\n"
									  + "APTO - 302 " 				 + "\n"
									  + "ICARAI "                    + "\n"  
									  + "NITEROI-RJ " 	             + "\n"
									  + "24230191 ");
		
		dto.setAssunto("Solicita informação sobre egresso dessa instituição de ensino");
		dto.setReferencia("Processo: 201770000123(Crea-RJ)");
		dto.setTextoPrincipal(stringBuilder.toString());
		
		StringBuilder sb = new StringBuilder();
		sb.append("ROSIANE MOULIN ");
		sb.append("\n");
		sb.append("COORDENADORA DE REGISTRO, CADASTRO E ACERVO TÉCNICO");
		dto.setCargo(sb.toString());
		dto.setTemplate(TemplateReportEnum.OFICIO_IE_CONFIRMACAO_FORMANDO);
		
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
