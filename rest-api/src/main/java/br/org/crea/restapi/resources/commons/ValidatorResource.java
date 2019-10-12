package br.org.crea.restapi.resources.commons;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.service.CommonsService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/validator")
public class ValidatorResource {
	
	
	@Inject	ResponseRestApi response;
	
	@Inject CommonsService service;
	
	
	@GET @Publico
	@Path("cpf/{cpf}")
	public Response validaCpf(@PathParam("cpf") String cpf){
		
		if(!service.cpfHeValido(cpf)){
			return response.error().message("validator.cpf.invalido").build();
		}
		
		if(!service.existeCpjOuCnpjNaBaseDoCrea(cpf)){
			
			return response.information().data("validator.cpf.validoENaoCadastrado").build();
		
		}else{
			
			return response.success().build();
		
		}
		
	}
	
	@GET @Publico
	@Path("cnpj/{cnpj}")
	public Response validaCnpj(@PathParam("cnpj") String cnpj){
		
		if(!service.cnpjHeValido(cnpj)){
			return response.error().message("validator.cnpj.invalido").build();
		}
		
		if(!service.validaCnpjNoCrea(cnpj)){
			return response.success().data("validator.cnpj.validoENaoCadastrado").build();
		}else{
			return response.success().build();
		}
		
	}
	
	@GET @Publico
	@Path("rnp/{rnp}")
	public Response validaRnp(@PathParam("rnp") String rnp){
		
		if(!service.validaRnp(rnp)){
			return response.error().message("validator.rnp.invalido").build();
		}else{
			return response.success().build();
		}
		
	}
	
	@GET @Publico
	@Path("registro/{registro}")
	public Response validaRegistro(@PathParam("registro") String registro){
		
		if(!service.validaRegistro(registro)){
			return response.error().message("validator.registro.invalido").build();
		}else{
			return response.success().build();
		}
		
	}
	

}
