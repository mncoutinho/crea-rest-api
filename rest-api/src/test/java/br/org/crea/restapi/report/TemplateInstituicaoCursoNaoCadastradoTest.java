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

public class TemplateInstituicaoCursoNaoCadastradoTest {
	
	private String template = "/home/monique/projects/java/crea_api/crea-rest-api/rest-api/src/main/webapp/WEB-INF/templates/protocolo/oficio-ie-curso-nao-cadastrado.jrxml";

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

			File file = new File("/opt/temp/cadastramento-curso-ie.pdf");
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
		stringBuilder.append("Levamos ao conhecimento de V.Sª que o (a) ex-concluinte do curso xxxxx ministrado na Unidade xxxx, concluído em xxx, requereu ao Crea-RJ o registro de profissional.  ");	
		stringBuilder.append("\n\n");		
		stringBuilder.append("Após análise da documentação apresentada foi constatado que o curso, nessa unidade, não possui cadastro no Crea-RJ até a presente data. ");	
		stringBuilder.append("\n\n");		
		stringBuilder.append("Dessa forma, o requerimento de registro do profissional não foi concedido pois aguarda a regularização do cadastramento institucional, com base na Resolução nº 1073, de 19 de abril de 2016. ");	
		stringBuilder.append("\n\n");		
		stringBuilder.append("Em face ao exposto, cientificamos essa instituição de ensino para que sejam tomadas as providências de requerer o cadastramento do curso/campi/unidade, a fim de permitir aos egressos a concessão de registro profissional. ");	
		stringBuilder.append("\n\n");		
		stringBuilder.append("Acesse o portal do Crea-RJ  http://servicos.crea-rj.org.br/#/app/servicos/instituicoes-ensino  onde também são encontrados os respectivos textos explicativos e a documentação pertinente. ");	
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
		dto.setAssunto("Informação Crea-RJ");
		dto.setReferencia("Processo: 201770000123(Crea-RJ)");
		dto.setTextoPrincipal(stringBuilder.toString());
		
		StringBuilder sb = new StringBuilder();
		sb.append("ROSIANE MOULIN ");
		sb.append("\n");
		sb.append("COORDENADORA DE REGISTRO, CADASTRO E ACERVO TÉCNICO");
		dto.setCargo(sb.toString());
		dto.setTemplate(TemplateReportEnum.OFICIO_IE_CURSO_NAO_CADASTRADO);
		
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
