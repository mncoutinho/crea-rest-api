package br.org.crea.commons.exception;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;

@Provider
public class DefaultExceptionHandler implements ExceptionMapper<Throwable> {

	@Context HttpServletRequest request;
	
	@Inject ResponseRestApi response;

	@Inject HttpClientGoApi httpGoApi;
	
	@Override
	public Response toResponse(Throwable e) {
		
		System.out.println(e);
		httpGoApi.geraLog("DefaultExceptionHandler", "sem parametro", e);
		return response.error().message("Houve um erro inesperado, por favor tente novamente. A inconsistência nos foi comunicada e já estamos atuando para solucionar o problema.").build();
	}
	

}