package br.org.crea.restapi.resources.commons;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.service.protocolo.ProtocoloService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/processo")
public class ProcessoResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject ProtocoloService service;
	
	
	@GET
	@Path("numero/{numero}") @Publico
	public Response getProtocoloByProcesso(@PathParam("numero") Long numero){
		
		List<ProtocoloDto> listDto = new ArrayList<ProtocoloDto>();
		listDto = service.getProtocoloByProcesso(numero);
		
		if(!listDto.isEmpty()){
			return response.success().data(listDto).build();
		}else{
			return response.error().message("processo.notExist").build();
		}
	}
	
	@GET
	@Path("pessoa/{idPessoa}") @Publico
	public Response getProtocoloByPessoa(@PathParam("idPessoa") Long idPessoa){
		
		List<ProtocoloDto> listDto = new ArrayList<ProtocoloDto>();
		listDto = service.getProtocolosByPessoa(idPessoa);
		
		if(listDto != null){
			return response.success().data(listDto).build();
		}else{
			return response.error().message("protocolo.notExist").build();
		}
	}

	@POST
	@Path("pessoa") @Publico
	public Response getProtocoloPaginadoByPessoa(PesquisaGenericDto pesquisa){
		
		List<ProtocoloDto> listDto = new ArrayList<ProtocoloDto>();
		listDto = service.getProtocolosPaginadosByPessoa(pesquisa);
		
		if(listDto != null){
			if (pesquisa.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalDeRegistrosDaPesquisa(pesquisa)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			}
		} else{
			return response.error().message("protocolo.notExist").build();
		}
	}
}
