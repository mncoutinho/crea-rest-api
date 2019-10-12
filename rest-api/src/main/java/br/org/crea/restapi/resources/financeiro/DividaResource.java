package br.org.crea.restapi.resources.financeiro;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.financeiro.dtos.DividaDto;
import br.org.crea.commons.models.financeiro.dtos.ValidaDevolucaoTransferenciaCreditoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.PessoaService;
import br.org.crea.commons.service.financeiro.FinDividaService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/financeiro")
public class DividaResource {

	@Inject
	ResponseRestApi response;

	@Inject
	FinDividaService service;

	@Inject
	PessoaService servicePessoa;

	@Inject
	HttpClientGoApi httpClientGoApi;
	
	@POST
	@Publico
	@Path("/emissao-guia")
	public Response getDividaPor(PesquisaGenericDto pesquisaDto) {

		List<PessoaDto> listPessoaDto = new ArrayList<PessoaDto>();

		listPessoaDto = servicePessoa.getPessoaPor(pesquisaDto);

		if (listPessoaDto.isEmpty()) {
			return response.error().message("divida.notExist").build();
		}

		if (listPessoaDto.size() > 1) {
			return response.error().message("divida.multipla").build();
		} else {

			return response.success().data(service.getDividaPor(listPessoaDto.get(0))).build();
		}

	}

	@POST
	@Publico
	@Path("/parcelas-a-vencer")
	public Response getParcelasAvencerPor(PesquisaGenericDto pesquisaDto) {

		return response.success().data(service.getParcelasAvencer(pesquisaDto.getIdPessoa(), pesquisaDto.getAnuidade())).build();

	}
	
	@POST
	@Publico
	@Path("/multas-a-vencer")
	public Response getMultasAvencer(PesquisaGenericDto pesquisaDto) {
		
		return response.success().data(service.getMultasAvencer(pesquisaDto.getIdPessoa(), pesquisaDto.getAnuidade())).build();
		
	}
	
	@GET
	@Path("anuidades/{idPessoa}")
	@Publico
	public Response buscaAnuidadesPorPessoa(@PathParam("idPessoa") Long idPessoa) {

		List<DividaDto> listDto = new ArrayList<DividaDto>();

		listDto = service.getAnuidadesDividaPorPessoa(idPessoa);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}

	@GET
	@Path("debitos/anuidade/{idPessoa}")
	@Publico
	public Response buscaDebitosAnuidadePorPessoa(@PathParam("idPessoa") Long idPessoa) {

		List<DividaDto> listDto = new ArrayList<DividaDto>();

		listDto = service.getDebitosAnuidadePorPessoa(idPessoa);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}
	
	@GET
	@Path("debitos/anuidade/quadro-tecnico/{idPessoa}")
	@Publico
	public Response buscaDebitosAnuidadeQuadroTecnicoPorPessoa(@PathParam("idPessoa") Long idPessoa) {

		List<DividaDto> listDto = new ArrayList<DividaDto>();

		listDto = service.getDebitosAnuidadeQuadroTecnicoPorPessoa(idPessoa);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}
	
	@GET
	@Path("debitos/art/{idPessoa}")
	@Publico
	public Response buscaDebitosArtPorPessoa(@PathParam("idPessoa") Long idPessoa) {

		List<DividaDto> listDto = new ArrayList<DividaDto>();

		listDto = service.getDebitosArtPorPessoa(idPessoa);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}
	
	@GET
	@Path("debitos/art/taxa-incorporacao/{idPessoa}")
	public Response buscaDebitosArtETaxaIncorporacaoPorPessoa(@PathParam("idPessoa") Long idPessoa) {

		List<DividaDto> listDto = new ArrayList<DividaDto>();

		listDto = service.getDebitosArtETaxaDeIncorporacaoPorPessoa(idPessoa);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}
	
	@GET
	@Path("debitos/numero-art/{numeroArt}")
	@Publico
	public Response buscaDebitosArtNumeroArt(@PathParam("numeroArt") String numeroArt) {

		List<DividaDto> listDto = new ArrayList<DividaDto>();

		listDto = service.getDebitosArtPorNumero(numeroArt);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}

	@GET
	@Path("debitos/taxas/{idPessoa}")
	@Publico
	public Response buscaDebitosTaxasPorPessoa(@PathParam("idPessoa") Long idPessoa) {

		List<DividaDto> listDto = new ArrayList<DividaDto>();

		listDto = service.getDebitosTaxasPorPessoa(idPessoa);

		if (!listDto.isEmpty()) {
			return response.success().data(listDto).build();
		} else {
			return response.information().build();
		}

	}
	
	@GET
	@Path("verifica-taxa-paga/{idAssunto}")
	@Publico
	public Response verificaTaxaPaga(@HeaderParam("Authorization") String token, @PathParam("idAssunto") Long idAssunto) {

		if (service.verificaTaxaPaga(httpClientGoApi.getUserDto(token), idAssunto)) {
			return response.success().build();
		} else {
			return response.information().build();
		}

	}
	
	@GET
	@Path("bancos")
	@Publico
	public Response getAllBancos() {
		return response.success().data(service.getAllBancos()).build();
	}
	
	@GET
	@Path("natureza-devolucao")
	@Publico
	public Response getNaturezaParaDevolucaoTransferenciaDeCredito() {
		return response.success().data(service.getNaturezaParaDevolucaoTransferenciaDeCredito()).build();
	}
	
	@POST
	@Path("valida-devolucao")
	@Publico
	public Response validaDevolucaoTransferenciaDeCredito(ValidaDevolucaoTransferenciaCreditoDto dto) {
		List<String> mensagens = service.validaDevolucaoTransferenciaDeCredito(dto);
		if (mensagens.size() > 0) {
			return response.error().messages(mensagens).build();
		} else {
			return response.success().build();
		}
	}
	
	
	
}
