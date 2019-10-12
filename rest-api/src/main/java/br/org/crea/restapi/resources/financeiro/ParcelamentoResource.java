package br.org.crea.restapi.resources.financeiro;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.ParcelamentoRequestDTO;
import br.org.crea.commons.models.commons.dtos.ParcelamentoResponseDTO;
import br.org.crea.commons.service.HttpClientLegado;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/financeiro/parcelamento")
public class ParcelamentoResource {
	
	@Inject
	ResponseRestApi response;
	
	@Inject
	HttpClientLegado httpClientLegado;
	
	@POST
	@Path("/dividas-a-parcelar")
	public Response getDividasAParcelar(ParcelamentoRequestDTO  parcelamentoRequestDTO) {
		ParcelamentoResponseDTO parcelamentoResponseDTO = null;
		try {
			parcelamentoResponseDTO = httpClientLegado.buscaDividasParcelamentoAnuidadeProfissional(parcelamentoRequestDTO);
			if(parcelamentoResponseDTO.getMensagem() != null) return response.information().message(parcelamentoResponseDTO.getMensagem()).build();
		}catch (Exception e) {
			return response.error().message("Erro ao processar a requisição. Pedimos desculpas pelo transtorno. Favor tentar novamente.").build(); 
		}
		return response.success().data(parcelamentoResponseDTO).build();
	}
	
	@POST
	@Path("/valida-tipo-parcelamento")
	public Response validaTipoParcelamento(ParcelamentoRequestDTO  parcelamentoRequestDTO) {
		ParcelamentoResponseDTO parcelamentoResponseDTO = null;
		try {
			parcelamentoResponseDTO = httpClientLegado.verificaTipoParcelamentoAnuidadeProfissional(parcelamentoRequestDTO);
			if(parcelamentoResponseDTO.getMensagem() != null) return response.success().data(parcelamentoResponseDTO).build();
		}catch (Exception e) {
			return response.error().message("Erro ao processar a requisição").build(); 
		}
		return response.success().data(parcelamentoResponseDTO).build();
	}
	
	@POST
	@Path("/concluir-parcelamento")
	public Response concluirParcelamento(ParcelamentoRequestDTO  parcelamentoRequestDTO) {
		ParcelamentoResponseDTO parcelamentoResponseDTO = null;
		try {
			parcelamentoResponseDTO = httpClientLegado.calcularValorTotalAnuidadesProfissional(parcelamentoRequestDTO);
			if(parcelamentoResponseDTO.getMensagem() != null) return response.information().message(parcelamentoResponseDTO.getMensagem()).build();
		}catch (Exception e) {
			return response.error().message("Erro ao processar a requisição. Pedimos desculpas pelo transtorno. Favor tentar novamente.").build(); 
		}
		return response.success().data(parcelamentoResponseDTO).build();
	}
	
	@POST
	@Path("/gerar-termo-parcelamento")
	public Response gerarTermoParcelamento(ParcelamentoRequestDTO  parcelamentoRequestDTO) {
		try {
			byte[] retorno = httpClientLegado.gerarTermoEBoletoParcelamento(parcelamentoRequestDTO);
			if(retorno == null) return response.error().message("Erro ao processar a requisição. Pedimos desculpas pelo transtorno. Favor tentar novamente.").build();
			return Response.ok(retorno).type(MediaType.APPLICATION_OCTET_STREAM).build();
		}catch (Exception e) {
			return response.error().message("Erro ao processar a requisição. Pedimos desculpas pelo transtorno. Favor tentar novamente.").build();
		}
	}
	
	@POST
	@Path("/listar-termo-parcelamento")
	public Response listarTermoParcelamento(ParcelamentoRequestDTO  parcelamentoRequestDTO) {
		ParcelamentoResponseDTO parcelamentoResponseDTO = null;
		try {
			parcelamentoResponseDTO = httpClientLegado.buscaTermosInscricaoParcelamento(parcelamentoRequestDTO);
			if(parcelamentoResponseDTO.getMensagem() != null) return response.information().message(parcelamentoResponseDTO.getMensagem()).build();
		}catch (Exception e) {
			return response.error().message("Erro ao processar a requisição. Pedimos desculpas pelo transtorno. Favor tentar novamente.").build(); 
		}
		return response.success().data(parcelamentoResponseDTO).build();
	}
	
	@POST
	@Path("/listar-boleto-parcelamento")
	public Response listarBoletoParcelamento(ParcelamentoRequestDTO  parcelamentoRequestDTO) {
		ParcelamentoResponseDTO parcelamentoResponseDTO = null;
		try {
			parcelamentoResponseDTO = httpClientLegado.listaBoletosParcelamentos(parcelamentoRequestDTO);
			if(parcelamentoResponseDTO.getMensagem() != null) return response.information().message(parcelamentoResponseDTO.getMensagem()).build();
		}catch (Exception e) {
			return response.error().message("Erro ao processar a requisição. Pedimos desculpas pelo transtorno. Favor tentar novamente.").build(); 
		}
		return response.success().data(parcelamentoResponseDTO).build();
	}
	
	@POST
	@Path("/gerar-boleto")
	public Response gerarBoleto(ParcelamentoRequestDTO  parcelamentoRequestDTO) {
		try {
			byte[] retorno = httpClientLegado.gerarBoleto(parcelamentoRequestDTO);
			if(retorno == null) return response.error().message("Erro ao processar a requisição. Pedimos desculpas pelo transtorno. Favor tentar novamente.").build();
			return Response.ok(retorno).type(MediaType.APPLICATION_OCTET_STREAM).build();
		}catch (Exception e) {
			return response.error().message("Erro ao processar a requisição. Pedimos desculpas pelo transtorno. Favor tentar novamente.").build();
		}
	}
	
}
