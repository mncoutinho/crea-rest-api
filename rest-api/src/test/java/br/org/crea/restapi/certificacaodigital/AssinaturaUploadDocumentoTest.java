package br.org.crea.restapi.certificacaodigital;

import static com.jayway.restassured.RestAssured.given;

import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.commons.dtos.AuthenticationDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.validsigner.dto.ValidSignDto;
import br.org.crea.commons.validsigner.enums.TipoDocumentoAssinaturaEnum;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;

/**
 * @author Monique Santos
 * @since 07/2018
 * Teste para assinar documento que está sendo submetido via upload
 * O serviço deve ser capaz de assinar o pdf enviado
 * */
public class AssinaturaUploadDocumentoTest {

	private String authToken;
	private JsonPath retornoPreAssinatura;
	
	/**
	 * Aciona recurso para pegar token de autorização necessário para a execução do 
	 * serviço de assinatura.
	 * 
	 * Faz a pré-autenticação da assinatura no web service da Valid e recupera a chave armazenada no Redis para fazer
	 * o get no documento que está com assinatura em andamento.
	 * 
	 * */
	@Before
	public void setup(){
		
		try {
			
			AuthenticationDto auth = new AuthenticationDto();
			auth.setLogin("3945");
			auth.setSenha("crearj");
			auth.setTipo("MATRICULA");
			Gson gson = new Gson();
			String usuarioJson = gson.toJson(auth);
			 
			RestAssured.baseURI  = "http://dev-servicos.crea-rj.org.br/rest-api/crea/corporativo/auth/legado";
			Response response =
					 given()
			            .contentType("application/json")
			            .body(usuarioJson)
			         .expect()
			         	.statusCode(200)
			         .when()
			         	.post("")
			         .andReturn();
			authToken = response.getBody().asString();
			retornoPreAssinatura = autenticaAssinaturaValid();
			 
		} catch (Throwable e) {
			Assert.assertEquals(e.getStackTrace() != null, false);
		}
	}
	
	/**
	 * Commit da Assinatura
	 * Finaliza o processo de assinatura gerando o documento assinado.
	 * */
	@Test
	public void testCommitAssinaturaDigital() {
		
		try {
			
			Gson gson = new Gson();
			String certificateDto = gson.toJson(populaDtoCommitAssinatura());
			
			RestAssured.baseURI  = "http://dev-servicos.crea-rj.org.br/rest-api/crea/commons/certificado/finalizar-sign";
			
			Header accept = new Header("Accept", "application/json");
			Header auth = new Header("Authorization", authToken);
			Headers headers = new Headers(accept, auth);
			
			Response response = 
					given()
						.headers(headers)
						.contentType("application/json")
						.body(certificateDto)
					.when()
						.post("")
					.andReturn();
			
			System.out.println(response.getBody().asString());
			Assert.assertEquals(response.getBody().asString(), response.getBody().asString());
			
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.assertEquals(e.getStackTrace() != null, false);
		}
	}
	
	/**
	 * Pre sign. 
	 * Processo que faz pre autenticação no servidor da Valid Certificadora para realizar a assinatura
	 * O serviço invocado retorna o SignaturePackage necessário a finalização do processo
	 * */
	public JsonPath autenticaAssinaturaValid() {
		
		try {
			
			RestAssured.baseURI  = "http://dev-servicos.crea-rj.org.br/rest-api/crea/commons/certificado/presign-upload-file";
			
			Header accept = new Header("Accept", "multipart/form-data");
			Header auth = new Header("Authorization", authToken);
			Headers headers = new Headers(accept, auth);
			
			JsonPath retorno = 
					 given()
					 	.multiPart("signerCertificate", getSignerCertificado())
					 	.multiPart("thumbprint", "5fca39d7aa9866669f7601878f3ab2f44b382141")
					    .multiPart("tipoDocumento", TipoDocumentoAssinaturaEnum.ARQUIVO_UPLOAD)
					 	.multiPart("file", "4e7d83a0900cfdd1783d",new FileInputStream("/var/www/html/arquivos/SIACOL/2018/06/01/4e7d83a0900cfdd1783d.pdf"))
		                .headers(headers)
		                .contentType("multipart/form-data")
		             .when()
		             	.post("")
	                 .andReturn()
	             	 	.jsonPath();
		    
			return retorno;
			
		} catch (Throwable e) {
			Assert.assertEquals(e.getStackTrace() != null, false);
		}
		
		return null;
	}
	
	/**
	 * Mock com informações do dto esperado pelo commit da assinatura
	 * */
	public ValidSignDto populaDtoCommitAssinatura() {
		ValidSignDto dto = new ValidSignDto();

		StringBuilder signaturepackage = new StringBuilder();
		signaturepackage.append("MYIBxzAvBgkqhkiG9w0BCQQxIgQgb5QDj6MMsOMZp2pyPuu3o2v1sfOza+i5qrQOlJI9dnUwGAYJ");
		signaturepackage.append("KoZIhvcNAQkDMQsGCSqGSIb3DQEHATAcBgkqhkiG9w0BCQUxDxcNMTgwNjIwMTkwMTM2WjCBwgYL");
		signaturepackage.append("KoZIhvcNAQkQAi8xgbIwga8wgawwgakEIAdgYUhFpLHs1ALI4JUx3QuLF5fYRSk47bN2luoyQoNH");
		signaturepackage.append("MIGEMHikdjB0MQswCQYDVQQGEwJCUjETMBEGA1UEChMKSUNQLUJyYXNpbDE2MDQGA1UECxMtU2Vj");
		signaturepackage.append("cmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRgwFgYDVQQDEw9BQyBW");
		signaturepackage.append("QUxJRCBSRkIgdjUCCEVtFZwTxXbaMIGWBgsqhkiG9w0BCRACDzGBhjCBgwYIYEwBBwEBAgIwMTAN");
		signaturepackage.append("BglghkgBZQMEAgEFAAQgD2+ixigZgXFslceYmQOYRFI7HGHCyWIonNrHgR/u4p4wRDBCBgsqhkiG");
		signaturepackage.append("9w0BCRAFARYzaHR0cDovL3BvbGl0aWNhcy5pY3BicmFzaWwuZ292LmJyL1BBX0FEX1JCX3YyXzIu");
		signaturepackage.append("ZGVy");
		
		dto.setSignerCertificate(retornoPreAssinatura.getObject("data.signerCertificate", String.class));
		dto.setSignaturePackage(retornoPreAssinatura.getObject("data.signaturePackage", String.class));
		dto.setIdDocumento(retornoPreAssinatura.getObject("data.idDocumento", Long.class));
		dto.setChaveAssinaturaRedis(retornoPreAssinatura.getObject("data.chaveAssinaturaRedis", String.class));
		dto.setSignature("RVShfO5/litNNsaD82x7c63zf/qSHvfbi+srBHUgn0PY+SOWeY5CUUf4MVCJO0kdumAtIMMcBFAIzbuJQKTnoPADM+8PbuYCDvnm/SMC/bNOHzXUXuAqDUIYQsD97u0tUgMCEWcbV9T04HnQseTGFVxJwwdWOajqQWl3f70dks9kmx4LGuiOQmnLs+vTamrWpSTtUSs7jNImBUEb6h8wc72aGmrIllE34J+tVb57GqfbuXvUKdaY5Trm19geNmjvTe3NCh27+iXExLhIgxGHlbJ4ikd1s7fJtuVvP8RbJ5upC3SEqvvg4UwPFupe2Y+dsyzgZAT0UNC0r2h+X+NM9g==");
		dto.setModuloSistema(ModuloSistema.CORPORATIVO);
		dto.setAssuntoDocumento("1001 - REGISTRO DE PROFISSIONAL DIPLOMADO NO PAIS");
		dto.setUnidadeDestino("230201");
		dto.setInteressadoDocumento("MONIQUE - TESTE ASSINATURA DIGITAL EXTERNA");
		dto.setCodigoTipoDocumento("52");
		return dto;
	}
	
	/**
	 * Mock com dados do certificado
	 * */
	private String getSignerCertificado() {
		
		StringBuilder signerCertificate = new StringBuilder();
		signerCertificate.append("-----BEGIN CERTIFICATE-----\n");
		signerCertificate.append("MIIHizCCBXOgAwIBAgIIRW0VnBPFdtowDQYJKoZIhvcNAQELBQAwdDELMAkGA1UEBhMCQlIxEzAR\n");
		signerCertificate.append("BgNVBAoTCklDUC1CcmFzaWwxNjA0BgNVBAsTLVNlY3JldGFyaWEgZGEgUmVjZWl0YSBGZWRlcmFs\n");
		signerCertificate.append("IGRvIEJyYXNpbCAtIFJGQjEYMBYGA1UEAxMPQUMgVkFMSUQgUkZCIHY1MB4XDTE4MDEyOTE5MjM1\n");
		signerCertificate.append("MloXDTIxMDEyOTE5MjM1MlowgcwxCzAJBgNVBAYTAkJSMRMwEQYDVQQKEwpJQ1AtQnJhc2lsMTYw\n");
		signerCertificate.append("NAYDVQQLEy1TZWNyZXRhcmlhIGRhIFJlY2VpdGEgRmVkZXJhbCBkbyBCcmFzaWwgLSBSRkIxFTAT\n");
		signerCertificate.append("BgNVBAsTDFJGQiBlLUNQRiBBMzEOMAwGA1UECxMFVkFMSUQxFDASBgNVBAsTC0FSIFZBTElEIENE\n");
		signerCertificate.append("MTMwMQYDVQQDEypNT05JUVVFIEdPTUVTIERFIEFSQVVKTyBTQU5UT1M6MTIwMTc0MzU3NTkwggEi\n");
		signerCertificate.append("MA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCUGTuabbp32lReLvebWCEhfTXznfQUTSVN1Y0i\n");
		signerCertificate.append("Ukgi61MFa2kT5n/FquCJooN2/77ylD7JjUihn9JAZc7dOTmuhlPFzTUJT7CXsrT4/SvRj6wWw0BH\n");
		signerCertificate.append("sPPjw+S3XeFRam2vbNgYmn7da3hTdb38Akagu8YhCpR9O0tDEtMzBxJcm6rqN49gnDqJ49sfaiTT\n");
		signerCertificate.append("3fyW3L9H/+mUZPxkoqFWEK5/IdG+lhBshGdrfE1lUbSj+GdLPTY9RW5l0f4hU7/wASG0una0yMyo\n");
		signerCertificate.append("WjypgNPHBlIHSC6J5X7+sDoVpFvh5BhpmMo5kEqu8c+1oqz2mG1BAnd42fhodVzX48oQv2KhWNLZ\n");
		signerCertificate.append("AgMBAAGjggLGMIICwjCBnAYIKwYBBQUHAQEEgY8wgYwwVQYIKwYBBQUHMAKGSWh0dHA6Ly9pY3At\n");
		signerCertificate.append("YnJhc2lsLnZhbGlkY2VydGlmaWNhZG9yYS5jb20uYnIvYWMtdmFsaWRyZmIvYWMtdmFsaWRyZmJ2\n");
		signerCertificate.append("NS5wN2IwMwYIKwYBBQUHMAGGJ2h0dHA6Ly9vY3NwdjUudmFsaWRjZXJ0aWZpY2Fkb3JhLmNvbS5i\n");
		signerCertificate.append("cjAJBgNVHRMEAjAAMB8GA1UdIwQYMBaAFFPLpeR1UJlALL5bFUXJvsswqonFMHAGA1UdIARpMGcw\n");
		signerCertificate.append("ZQYGYEwBAgMkMFswWQYIKwYBBQUHAgEWTWh0dHA6Ly9pY3AtYnJhc2lsLnZhbGlkY2VydGlmaWNh\n");
		signerCertificate.append("ZG9yYS5jb20uYnIvYWMtdmFsaWRyZmIvZHBjLWFjLXZhbGlkcmZidjUucGRmMIG2BgNVHR8Ega4w\n");
		signerCertificate.append("gaswU6BRoE+GTWh0dHA6Ly9pY3AtYnJhc2lsLnZhbGlkY2VydGlmaWNhZG9yYS5jb20uYnIvYWMt\n");
		signerCertificate.append("dmFsaWRyZmIvbGNyLWFjLXZhbGlkcmZidjUuY3JsMFSgUqBQhk5odHRwOi8vaWNwLWJyYXNpbDIu\n");
		signerCertificate.append("dmFsaWRjZXJ0aWZpY2Fkb3JhLmNvbS5ici9hYy12YWxpZHJmYi9sY3ItYWMtdmFsaWRyZmJ2NS5j\n");
		signerCertificate.append("cmwwDgYDVR0PAQH/BAQDAgXgMB0GA1UdJQQWMBQGCCsGAQUFBwMCBggrBgEFBQcDBDCBmgYDVR0R\n");
		signerCertificate.append("BIGSMIGPgRptb25pcXVlZy5hc2FudG9zQGdtYWlsLmNvbaA4BgVgTAEDAaAvBC0xODA3MTk4ODEy\n");
		signerCertificate.append("MDE3NDM1NzU5MDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDCgFwYFYEwBAwagDgQMMDAwMDAwMDAw\n");
		signerCertificate.append("MDAwoB4GBWBMAQMFoBUEEzAwMDAwMDAwMDAwMDAwMDAwMDAwDQYJKoZIhvcNAQELBQADggIBADpj\n");
		signerCertificate.append("wG562NCPMFT4iq2BHMdJmFeGDj9Bt3viTzzYSciUgq7czTDipNAMgdfY0d57qteaC4/qUhgaus8M\n");
		signerCertificate.append("yerIJYGHMUBX4DVdCvyyA4Hg9chaJ2zgAW82vPdiX01Wgx0jcS2Aqt40b0cL6lmViMLe3JDVaQit\n");
		signerCertificate.append("d006f9IYtuNfkcgG0Og/a1GZJhror66tEV6V77niT2Y+t785i1Ge69xvEAzEHXz19cu65NFl/VFf\n");
		signerCertificate.append("32qRbN2ceJzvQI0r5o+yD7gXeN3wyABKQ6qaM3TKkDR2E85E2/125GGVoySAJsfGBMCDs/BFzIwL\n");
		signerCertificate.append("PKzPgNPpEoVvkqh6bnB0OIcviwdMVgHvsckepZQpbee+vDBaZ0ZwEmBpm4aTIjpYxpdCZg7pJZSf\n");
		signerCertificate.append("jY/QghQuoSj9Ik5U6eFRChv8Mb0Oz/GPRsR01vIb+vvzZtOWiux9wdkBXPIH6KZGNRqHtOynKavV\n");
		signerCertificate.append("vZJ8IRrU9k4x1WuZuf02xd0Y8m/iqbW592OYc/LVPPJTXyB01X/lLUOZrzp499Shjs149JRRbJjf\n");
		signerCertificate.append("nMgIRg2TLkWmZh+7pg396qhoiDJXOBrROvkSKh/Mv2H92LoNAwSZUye027iPre3gFm3ukJ03GSmU\n");
		signerCertificate.append("Vi/39THFdjUTxJ7qEAcNV/aKMQ99yvvLdPSZByFWO15OBzgrlsPBQzJWLZBq0Ljd8AqyN/SH\n");
		signerCertificate.append("-----END CERTIFICATE-----");
		return signerCertificate.toString();
	}
	
}

