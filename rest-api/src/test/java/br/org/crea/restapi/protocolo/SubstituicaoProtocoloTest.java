package br.org.crea.restapi.protocolo;

import static com.jayway.restassured.RestAssured.given;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.org.crea.commons.models.commons.dtos.AuthenticationDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.protocolo.dtos.SubstituicaoProtocoloDto;
import br.org.crea.restapi.util.ResponseTest;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubstituicaoProtocoloTest {

	private String token;
	
	@Before
	public void getToken() {
		
		try {
			
			RestAssured.baseURI  = "http://dev-servicos.crea-rj.org.br/rest-api/crea/corporativo/auth/legado";
			Response response = 
					given()
						.contentType("application/json")
						.body(mockAuth())
					.expect()
						.statusCode(200)
					.when()
						.post("")
					.andReturn();
			
			token = response.getBody().asString();
			Assert.assertEquals(response.statusCode(), 200);
			
		} catch (Throwable e) {
			Assert.assertEquals(e.getStackTrace() != null, false);
		}
	}
	
	/**
	 *  Verifica apenas se não retornará um erro inesperado, cuja retorno seria uma message:
	 *  "Houve um erro inesperado, por favor tente novamente.
	 *   A inconsistência nos foi comunicada e já estamos atuando para solucionar o problema."
	 *  O que sugere erro não tratado.
	 * 
	 *  Nos demais casos a rotina pode retornar type success - > efetivou substituição ou 
	 *  type error -> erro tratado de regras de validação.
	 * 
	 *  Em ambos casos teríamos a lista de messages do response vazia
	 *  
	 * */
	@Test
	public void DeveVerificarDisponibilidadeServico() {
				
		try {
			
			Gson gson = new Gson();
			String substituicaoDto = gson.toJson(mockTeste1());
			
			RestAssured.baseURI = "http://dev-servicos.crea-rj.org.br/rest-api/crea/protocolo/substituir";
			
			Header accept = new Header("Accept", "application/json");
			Header authorization = new Header("Authorization", token);
			Headers headers = new Headers(accept, authorization);
			
			Response response = 
					given()
						.headers(headers)
						.contentType("application/json")
						.body(substituicaoDto)
					.when()
						.post("")
					.andReturn();
			
	        ResponseTest retorno = gson.fromJson(response.getBody().asString(), ResponseTest.class);
			Assert.assertEquals(retorno.getMessages().isEmpty(), true);
			
		} catch (Throwable e) {
			Assert.assertEquals(e.getStackTrace() != null, false);
		}
	}
	
	public SubstituicaoProtocoloDto mockTeste1() {
		
		ProtocoloDto substituido = new ProtocoloDto();
		substituido.setNumeroProtocolo(new Long(201870000581L));
		
		ProtocoloDto substituto = new ProtocoloDto();
		substituto.setNumeroProtocolo(new Long(201870000582L));
		
		SubstituicaoProtocoloDto dto = new SubstituicaoProtocoloDto();
		dto.setModuloSistema(ModuloSistema.PROTOCOLO);
		dto.setProtocoloSubstituido(substituido);
		dto.setProtocoloSubstituto(substituto);
		return dto; 
	}
	
	public String mockAuth() {
		
        AuthenticationDto auth = new AuthenticationDto();
        auth.setLogin("3945");
        auth.setSenha("crearj");
        auth.setTipo("MATRICULA");
        Gson gson = new Gson();
        return gson.toJson(auth);
	}
}
