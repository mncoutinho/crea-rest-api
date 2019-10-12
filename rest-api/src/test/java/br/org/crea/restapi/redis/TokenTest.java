package br.org.crea.restapi.redis;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.org.crea.commons.models.commons.TokenUser;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.TokenUtil;

public class TokenTest {

	private TokenUser token;
	
	private UserFrontDto pessoaDto;
	
	private HttpClientGoApi api;

	@Before
	public void setup() throws Exception {
		
		pessoaDto = mockPessoaDto();
		token = TokenUtil.generateTokenWith(pessoaDto);
		api = new HttpClientGoApi();
		
	}

	
	
	@Test
	public void getToken(){
		UserFrontDto dto = new UserFrontDto();
		String token = "45069b3a10141aa5b151";
		dto = api.getToken(token);
		
		System.out.println("> > > > " + dto.getNome());
	}
	
	public void salvaNoGo() throws Exception{
		
		for (int i = 0; i < 10; i++) {
			token = TokenUtil.generateTokenWith(pessoaDto);
			api.salvaToken(token);
			
		}
		
	}
	
	public void deveRecuperarPessoa() {
		UserFrontDto dto = TokenUtil.recuperaDto(token);
		assertEquals("Teste", dto.getNome());
	}


	private UserFrontDto mockPessoaDto() {
		UserFrontDto dto = new UserFrontDto();

		dto.setEmail("ricardo Leite");
		dto.setNome("Teste");
		return dto;
	}
	
	
}
