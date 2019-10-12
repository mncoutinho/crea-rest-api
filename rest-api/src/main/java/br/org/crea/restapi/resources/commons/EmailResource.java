package br.org.crea.restapi.resources.commons;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.commons.dtos.EmailEnvioDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.service.EmailService;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.commons.EmailEnvioService;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.commons.util.StringUtil;
import br.org.crea.siacol.service.RlEmailReuniaoSiacolService;

@Resource
@Path("/commons/email")
public class EmailResource {
	@Inject
	HttpClientGoApi httpClientGoApi;

	@Inject	ResponseRestApi response;
	
	@Inject EmailService service;
	
	@Inject EmailEnvioService emailEnvioService;
	
	@Inject RlEmailReuniaoSiacolService rlEmailReuniaoSiacolService;
	
	@GET
	@Path("template-envio/{idEmail}") @Publico
	public Response getBy(@PathParam("idEmail") Long idEmail){
		return response.success().data(emailEnvioService.getBy(idEmail)).build();
	}
	
	@POST
	@Path("template-envio-reuniao/") @Publico
	public Response getTemplateReuniaoBy(GenericDto dto){
		return response.success().data(rlEmailReuniaoSiacolService.getTemplateReuniaoBy(dto)).build();
	}
	
	@POST
	@Path("template-envio") @Publico
	public Response salva(EmailEnvioDto dto){
		return response.success().data(emailEnvioService.salva(dto)).build();
	}
	
	@POST
	@Path("salva-rl-template-reuniao") @Publico
	public Response salvaRlTemplateReuniao(GenericDto dto){
		return response.success().data(rlEmailReuniaoSiacolService.salvaRlTemplateReuniao(dto)).build();
	}
	
	@PUT
	@Path("template-envio") @Publico
	public Response atualiza(EmailEnvioDto dto){
		return response.success().data(emailEnvioService.atualiza(dto)).build();
	}
	
	@DELETE
	@Path("template-envio/{idEmail}") @Publico
	public Response deleta(@PathParam("idEmail") Long idEmail){
		emailEnvioService.deleta(idEmail);
		return response.success().build();
	}
		
	@GET
	@Path("pessoa/{idPessoa}") @Publico
	public Response getEnderecoDeEmailBy(@PathParam("idPessoa") Long idPessoa){
		return response.success().data(service.getListEnderecoDeEmailPor(idPessoa)).build();
	}
	

	@POST
	@Path("envia")
	public Response envia(EmailEnvioDto dto, @HeaderParam("Authorization") String token){
		return response.success().data(service.envia(dto)).build();
	}

	
	@PUT
	public Response atualizar(EmailDto dto, @HeaderParam("Authorization") String token){
		
		if (service.existeEmailCadastrado(dto)) {
			return response.information().message("Este e-mail já está em uso.").build();
		} else {
			if (StringUtil.ehEmail(dto.getDescricao())){
				return response.success().data(service.atualizarEmail(dto, httpClientGoApi.getUserDto(token))).build();
			}else {
				return response.information().message("Este e-mail não possui um formato válido").build();
			}
		}
	}
	
}


