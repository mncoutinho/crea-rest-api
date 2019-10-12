package br.org.crea.restapi.resources.art;

import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.org.crea.art.service.FinalizaContratoArtService;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.art.ContratoArtService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/art/contratos/finaliza")
public class FinalizaContratoArtResource {

	
	@Inject
	ResponseRestApi response;
	
	@Inject
	ContratoArtService service;
	
	@Inject
	HttpClientGoApi httpClientGoApi;
	
	@Inject
	FinalizaContratoArtService finalizaService;
	
	/**
	 * Método finaliza contrato 
	 * 
	 */
	@PUT
	public Response finaliza(DomainGenericDto dto) {
		
		if(finalizaService.finaliza(dto)) {
			return response.data(dto).success().build();
		}else {
			return response.error().build();
		}
	}
	
	/**
	 * Método finaliza contrato e calcula
	 * 
	 * 
	 * @param dto
	 *            - contém uma lista de um ou mais protocolos, funcionário que
	 *            tramitou, departamento de destino e departamento pai em
	 *            relação ao de destino (útil para validação de algumas regras).
	 * @param token
	 * @return lista de TramiteDto dos protocolos passados no dto com suas
	 *         respectivas mensagens de sucesso ou de erro conforme o caso.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@PUT
	@Path("obra-servico")
	public Response obraServico(DomainGenericDto dto) {
		
		if(finalizaService.obraServico(dto)) {
			return response.data(dto).success().build();
		}else {
			return response.error().build();
		}
		
	}
	
	/**
	 * Método finaliza contrato e calcula
	 * 
	 * 
	 * @param dto
	 *            - contém uma lista de um ou mais protocolos, funcionário que
	 *            tramitou, departamento de destino e departamento pai em
	 *            relação ao de destino (útil para validação de algumas regras).
	 * @param token
	 * @return lista de TramiteDto dos protocolos passados no dto com suas
	 *         respectivas mensagens de sucesso ou de erro conforme o caso.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@PUT
	@Path("desempenho-cargo-funcao")
	public Response desempenhoCargoFuncao(DomainGenericDto dto) {
		
		if(finalizaService.desempenhoCargoFuncao(dto)) {
			return response.data(dto).success().build();
		}else {
			return response.error().build();
		}
		
	}
	
	/**
	 * Método finaliza contrato e calcula
	 * 
	 * 
	 * @param dto
	 *            - contém uma lista de um ou mais protocolos, funcionário que
	 *            tramitou, departamento de destino e departamento pai em
	 *            relação ao de destino (útil para validação de algumas regras).
	 * @param token
	 * @return lista de TramiteDto dos protocolos passados no dto com suas
	 *         respectivas mensagens de sucesso ou de erro conforme o caso.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@PUT
	@Path("receituario-agronomico")
	public Response receituarioAgronomico(DomainGenericDto dto) {
		
		if(finalizaService.receituarioAgronomico(dto)) {
			return response.data(dto).success().build();
		}else {
			return response.error().build();
		}
		
	}
	
	/**
	 * Método finaliza contrato e calcula
	 * 
	 * 
	 * @param dto
	 *            - contém uma lista de um ou mais protocolos, funcionário que
	 *            tramitou, departamento de destino e departamento pai em
	 *            relação ao de destino (útil para validação de algumas regras).
	 * @param token
	 * @return lista de TramiteDto dos protocolos passados no dto com suas
	 *         respectivas mensagens de sucesso ou de erro conforme o caso.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@PUT
	@Path("multipla")
	public Response finalizaContratoMultipla(DomainGenericDto dto) {
		
		if(finalizaService.multipla(dto)) {
			return response.data(dto).success().build();
		}else {
			return response.error().build();
		}
		
	}
}
