package br.org.crea.restapi.resources.commons;

import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.googleRecaptcha.service.GoogleReCaptchaService;
import br.org.crea.commons.models.commons.dtos.AuthenticationDto;
import br.org.crea.commons.models.commons.dtos.AuthenticationSemLoginDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.LeigoDto;
import br.org.crea.commons.service.CommonsService;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.corporativo.service.AuthService;

@Resource
@Path("/corporativo")
public class LoginResource {

	@Inject
	ResponseRestApi response;

	@Inject
	AuthService authService;

	@Inject
	CommonsService commonsService;
	
	@Inject
	GoogleReCaptchaService googleRecaptchaService;
	
	@Inject	private Properties properties;
	
	@POST
	@Path("auth")
	@Publico
	public Response auth(@Context HttpServletRequest request, AuthenticationDto auth) {
	
		UserFrontDto userFront = null;
		
		if(auth.temMatricula()){
			userFront = authService.loginPorMatricula(auth, request);
		}else {
			userFront = authService.loginPorRegistro(auth, request);
		}

		return userFront != null ? response.success().data(userFront).build() : response.error().message("usuario.invalido").build(); 
	
	}

	@POST
	@Path("auth/legado")
	@Publico
	public String authLegado(@Context HttpServletRequest request, AuthenticationDto auth) {

		UserFrontDto userFront = null;
		
		if(auth.temMatricula()){
			userFront = authService.loginPorMatricula(auth, request);
		}else {
			userFront = authService.loginPorRegistro(auth, request);
		}

		if (userFront != null) {
			return userFront.getToken();
		} else {
			return "ERROR";
		}

	}

	@POST
	@Path("auth/cpf-cnpj")
	@Publico
	public Response cpfOuCnpj(@Context HttpServletRequest request, AuthenticationSemLoginDto auth) {

		if(commonsService.validaFormatoCpfOuCnpj(auth.getCpfOuCnpj())){
			
			if(commonsService.cpfOuCnpjJaExisteNaBaseDoCrea(auth.getCpfOuCnpj())){
				
				UserFrontDto userDto = authService.autenticaPorCpfOuCnpj(auth, request);
				if (userDto != null) {
					return response.success().data(userDto).build();
				} else {
					return response.information().build();
				}					
				
			} else {					
				return response.information().build();					
			}	
		}else {
			return response.error().message("cpfOuCnpj.invalido").build();
		}
	}
	
	@POST
	@Path("login/cpf-cnpj")
	@Publico
	public Response loginCpfOuCnpj(@Context HttpServletRequest request, AuthenticationDto auth) {
		
		UserFrontDto userFront = null;

			if(commonsService.validaFormatoCpfOuCnpj(auth.getLogin())){
				
				if(commonsService.cpfOuCnpjJaExisteNaBaseDoCrea(auth.getLogin())) {
					userFront = authService.loginPorCpfOuCnpj(auth, request);
					return userFront != null ? response.success().data(userFront).build() : response.error().message("Acesso não autorizado!").build();
				} else {
					return response.information().build();
				}	
			}else {
				return response.error().message("CPF inválido").build();
			}
		
	}
	
	@POST
	@Path("login/cpf-registro-security")
	@Publico
	public Response loginCpfOuRegistroSecurity(@Context HttpServletRequest request, AuthenticationDto auth) {
		UserFrontDto userFront = null;
		String ambiente = properties.getProperty("ambiente");
		properties.clear();
		
		
		if(googleRecaptchaService.verify(auth.getRecaptchaToken())) {
			
			if ("DEV".equals(ambiente) || 
					(auth.getLogin().equals("2002201513") 
				  || auth.getLogin().equals("2010215554") 
			      || auth.getLogin().equals("03125979757") 
				  || auth.getLogin().equals("02203807750") 
				  || auth.getLogin().equals("05875206713")
				  || auth.getLogin().equals("04796917705")
				  || auth.getLogin().equals("07156662740")
				  || auth.getLogin().equals("08343317726"))) {
			
				if(commonsService.validaFormatoCpfOuRegistro(auth)){
					
					if(commonsService.cpfOuRegistroExisteNaBase(auth)) {
						
						userFront = authService.loginPorCpfOuRegistro(auth, request);
						if(userFront != null) {
							userFront = authService.verificarAutorizacao(userFront);
							if(userFront.getMensagem() != null) {
								return response.information().message(userFront.getMensagem()).build();
							}
							return response.success().data(userFront).build();
						} else {
							return response.error().message("Acesso não autorizado!").build();
						}
					} else {
						return response.information().message("Acesso não autorizado!").build();
					}	
				}else {
					return response.error().message("CPF / REGISTRO inválido").build();
				}
			} else {
				return response.error().message("Acesso não autorizado!").build();
			}
		} else {
			return response.error().message("Favor validar o captcha!").build();
		}		
	}

	@POST
	@Path("auth/pessoa-juridica")
	@Publico
	public Response cpfOuCnpjRestrito(@Context HttpServletRequest request, AuthenticationSemLoginDto auth) {

		String cnpj = auth.getCpfOuCnpj();

		if (!commonsService.cnpjHeValido(cnpj)) {
			return response.error().message("validator.cnpj.invalido").build();
		}

		if (!commonsService.validaCnpjNoCrea(cnpj)) {
			return response.information().build();
		} else {
			UserFrontDto user = authService.autenticaPorCnpjRestrito(auth, request);
			if (user != null) {
				return response.success().data(user).build();
			} else {
				return response.error().message("login.incorreto").build();
			}
		}

	}

	@POST
	@Path("auth/sem-login")
	@Publico
	public Response authSemLogin(@Context HttpServletRequest request, AuthenticationSemLoginDto auth) {
		
		LeigoDto leigoDto = commonsService.cadastrarLeigo(auth);
		
		if(auth.heAcessoWifi()){
			return response.success().data(auth).build();
		} else {
			return response.success().data(authService.autenticaTempAgendamento(leigoDto, request)).build();
		}
	}

	@DELETE
	@Path("logoff")
	public Response logoff(@HeaderParam("Authorization") String token) throws Exception {
		authService.logoff(token);
		return response.success().message("usuario.logoff").build();
	}

	@GET
	@Path("usuario-sessao")
	public Response verificaToken(@HeaderParam("Authorization") String token) throws Exception {
		return response.success().data(authService.verificaToken(token)).build();
	}
	
	@POST
	@Path("auth/gerar-senha")
	@Publico
	public Response gerarSenha(AuthenticationDto auth) {
		
		authService.gerarSenha(auth);
		
		return response.success().message("Sua senha foi gerada com sucesso").build();
	}
	
	@POST
	@Path("auth/alterar-senha")
	public Response alterarSenha(@HeaderParam("Authorization") String token, AuthenticationDto auth) {
		
		if (authService.alterarSenha(auth)) {
			return response.success().build();
		} else {
			return response.information().message("A senha inserida não corresponde a senha original, por favor tente novamente.").build();
		}
	}
}
