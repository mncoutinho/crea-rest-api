package br.org.crea.commons.exception;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ResteasyViolationException;

import br.org.crea.commons.util.ResponseRestApi;

@Provider
public class MethodConstraintViolationExceptionMapper implements ExceptionMapper<ResteasyViolationException> {

	@Inject
	ResponseRestApi response;

	@Override
	public Response toResponse(ResteasyViolationException exception) {
		
		ResponseRestApi responseApi = response.error();
		
		for (ResteasyConstraintViolation constraintViolation : exception.getParameterViolations()) {
			responseApi.message(constraintViolation.getMessage());
		}
		
		return responseApi.build();
	}
}