package br.org.crea.restapi.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.specimpl.BuiltResponse;

import br.org.crea.commons.annotations.EhAutorDaArt;
import br.org.crea.commons.annotations.Funcionario;
import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.commons.util.StringUtil;

@Provider
public class RestApiInterceptor implements javax.ws.rs.container.ContainerRequestFilter, javax.ws.rs.container.ContainerResponseFilter  
{
	private static final String AUTHORIZATION_PROPERTY = "authorization";
	private static final ServerResponse EXPIRED  = new ServerResponse((BuiltResponse) new ResponseRestApi().unauthorized().message("Sua sessão expirou. Por favor, efetue o login novamente.").build());
	private static final ServerResponse UNAUTHORIZED  = new ServerResponse((BuiltResponse) new ResponseRestApi().unauthorized().message("Acesso não autorizado para este recurso.").build());
	private static final ServerResponse ERROR  = new ServerResponse((BuiltResponse) new ResponseRestApi().error().message("Ocorreu um erro imprevisto. A equipe de Desenvolvimento  já foi notificada por email. Qualquer dúvida entre em contato com desenv@crea-rj.org.br").build());

	@Inject HttpClientGoApi httpClientGoApi;
	
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException 
	{			
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        
        String token = requestContext.getHeaderString(AUTHORIZATION_PROPERTY);
        
        if ( !method.isAnnotationPresent(Publico.class) ){
        	if ( StringUtil.isValidAndNotEmpty(token) ){
				try {
					if ( !httpClientGoApi.estaLogado(token) ){						
						requestContext.abortWith(EXPIRED);        			
						return;
					} else if ( method.isAnnotationPresent(Funcionario.class) && !httpClientGoApi.temIdFuncionario(token) ) {
						requestContext.abortWith(EXPIRED);        			
						return;
					} else if( method.isAnnotationPresent(EhAutorDaArt.class)) {
						return;
					}

				} catch (Exception e) {
					requestContext.abortWith(ERROR);        			
					return;
				}   			
        	} else {
        		requestContext.abortWith(UNAUTHORIZED);
        		return;
        	}
        }
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException
	{	
		if ( ! requestContext.getUriInfo().getPath().equals("/corporativo/auth") ){			
			//UserSessionService.atualizaAtividade(requestContext.getHeaderString(AUTHORIZATION_PROPERTY));			
		}
	}
}