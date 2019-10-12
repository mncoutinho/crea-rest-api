package br.org.crea.restapi.resources.siacol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.protocolo.dtos.JuntadaProtocoloDto;
import br.org.crea.commons.models.siacol.dtos.ConsultaProtocoloDto;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ProtocoloSiacolEmpresaDto;
import br.org.crea.commons.models.siacol.dtos.SiacolProtocoloExigenciaDto;
import br.org.crea.commons.models.siacol.dtos.VinculoProtocoloDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.protocolo.service.TramiteProtocoloService;
import br.org.crea.siacol.service.ProtocoloSiacolService;

@Resource
@Path("/siacol/protocolo")
public class ProtocoloSiacolResource {

	@Inject ResponseRestApi response;

	@Inject ProtocoloSiacolService service;
	
	@Inject HttpClientGoApi httpClientGoApi;

	@Inject TramiteProtocoloService tramiteService;
	
	@GET
	@Path("busca-protocolo-upar-documento-docflow/{numeroProtocolo}") @Publico
	public Response getProtocoloParaUparDocumento(@PathParam("numeroProtocolo") Long numeroProtocolo, @HeaderParam("Authorization") String token) {	
		return response.success().data(service.getProtocoloParaUparDocumento(numeroProtocolo)).build();
	}
	
	@GET
	@Path("busca-protocolo/{idProtocolo}") @Publico
	public Response buscaProtocolo(@PathParam("idProtocolo") Long idProtocolo, @HeaderParam("Authorization") String token) {	
		return response.success().data(service.buscaProtocolo(idProtocolo)).build();
	}
	
	@GET
	@Path("busca-protocolo-numero/{numeroProtocolo}") @Publico
	public Response getProtocoloBy(@PathParam("numeroProtocolo") Long numeroProtocolo, @HeaderParam("Authorization") String token) {	
		return response.success().data(service.getProtocoloBy(numeroProtocolo)).build();
	}
	
	@GET
	@Path("busca-protocolo-oficio/{status}/{idDepartamento}") @Publico
	public Response buscaProtocoloOficio(@PathParam("status") String status, 
			@PathParam("idDepartamento") Long idDepartamento,
			@HeaderParam("Authorization") String token) {	
		return response.success().data(service.buscaProtocoloOficio(status, idDepartamento)).build();
	}
	
	@POST
	@Path("cadastrar")
	public Response cadastrar(ProtocoloSiacolDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("Protocolo cadastrado").data(service.cadastrar(dto, httpClientGoApi.getUserDto(token))).build();
	}

	@POST
	@Path("consulta") @Publico //FIXME remover @publico, usado para o painel plenaria, enquanto não é passado token
	public Response consultaProtocolos(ConsultaProtocoloDto consulta, @HeaderParam("Authorization") String token) {

		List<ProtocoloSiacolDto> listDto = new ArrayList<ProtocoloSiacolDto>();

		listDto = service.getAllProtocolos(consulta);

		if (!listDto.isEmpty()) {
			if (consulta.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getQuantidadeConsultaProtocolo(consulta)).data(listDto).build();
			} else {
				return response.success().data(listDto).build();
			}

		} else {
			return response.information().message("protocolo.notExist").build();
		}

	}
	
	@POST
	@Path("consulta/xls") @Publico
	public Response geraRelatorioXls(ConsultaProtocoloDto dto) {
		return Response.ok(service.geraRelatorioXls(dto))
				.header("Content-Disposition", "attachment; filename=" + "planilha-protocolos.xls" )
				.header("Content-Type", "application/vnd.ms-excel").type(MediaType.APPLICATION_OCTET_STREAM).build();
	}

	@POST
	@Path("redistribuicao/verifica-habilidade")
	@Publico
	public Response verificaHabilidadeParaRedistribuicao(GenericSiacolDto dto) {
		if (service.temHabilidadeParaRedistribuicao(dto)) {
			return response.success().build();
		} else {
			return response.information().build();
		}
	}


	@POST
	@Path("distribuicao")
	public Response distribuiProtocolo(GenericSiacolDto dto, @HeaderParam("Authorization") String token) {

		GenericSiacolDto dtoDistribuicao = service.distribuiProtocolo(dto, httpClientGoApi.getUserDto(token));
		if (dtoDistribuicao == null) {
			return response.information().message("Protocolo(s) não foi disbribuido por falta de pessoas para recebimento").build();
		} else {
			return response.success().message("Protocolo(s) redistribuido(s)").data(dtoDistribuicao).build();
		}
		
	}

	@POST
	@Path("justificativa-devolucao")
	@Publico
	public Response justificativaDevolucao(GenericSiacolDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("Protocolo(s) justificado(s)").data(service.justificativaDevolucao(dto, httpClientGoApi.getUserDto(token))).build();
	}

	@POST
	@Path("altera-situacao")
	public Response alteraSituacao(GenericSiacolDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("Protocolo(s) atualizado(s)").data(service.alteraSituacoes(dto, httpClientGoApi.getUserDto(token))).build();
	}

	@POST
	@Path("receber")
	public Response receberProtocolo(GenericSiacolDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("Protocolo(s) recebido(s)").data(service.receberProtocolo(dto, httpClientGoApi.getUserDto(token))).build();
	}

	@POST
	@Path("classificar")
	public Response classificar(GenericSiacolDto dto, @HeaderParam("Authorization") String token) {
		return response.success().message("Protocolo classificado").data(service.classificar(dto, httpClientGoApi.getUserDto(token))).build();
	}

	@POST
	@Path("ativar/{idDepartamento}")
	public Response ativarProtocoloPorIdDepartamento(@PathParam("idDepartamento") Long idDepartamento) {
		service.ativarProtocoloPorIdDepartamento(idDepartamento);
		return response.success().build();
	}
	
	@POST
	@Path("cria-vinculo")
	public Response vincularProtocolos(List<VinculoProtocoloDto> listVinculoProtocolo, @HeaderParam("Authorization") String token) {
		service.vinculaProtocolosResponsavel(listVinculoProtocolo);
		return response.success().build();
	}
	
	@GET
	@Path("desfaz-vinculo/{numeroProtocolo}")
	public Response desvinculaProtocolo(@PathParam("numeroProtocolo") Long numeroProtocolo, @HeaderParam("Authorization") String token) {
		service.desfazVinculoProtocoloResponsavel(numeroProtocolo);
		return response.success().build();
	}
	
	@GET
	@Path("verifica-vinculo/{numeroProtocolo}") 
	public Response verificaVinculo(@PathParam("numeroProtocolo") Long numeroProtocolo, @HeaderParam("Authorization") String token) {
		
		if ( service.protocoloJaEstaVinculadoAoResponsavel(numeroProtocolo) ){
			return response.information().data("O protocolo já possui vínculo e não poderá ser vinculado novamente.").build();
		} else{
			return response.success().build();
		}
	}
	
	@GET
	@Path("busca-protocolo-vinculado/{numeroProtocolo}")
	public Response buscaProtocoloVinculado(@PathParam("numeroProtocolo") Long numeroProtocolo, @HeaderParam("Authorization") String token) {
		return response.success().data(service.getProtocoloVinculado(numeroProtocolo)).build();
	}
	
	@GET
	@Path("busca-vinculos-protocolo/{numeroProtocoloPai}")
	public Response buscaVinculosDoProtocolo(@PathParam("numeroProtocoloPai") Long numeroProtocoloPai, @HeaderParam("Authorization") String token) {
		return response.success().data(service.getVinculosProtocoloPor(numeroProtocoloPai)).build();
	}

	@GET
	@Path("busca-exigencia/{idProtocolo}")
	public Response buscaExigenciaByIdProtocolo(@PathParam("idProtocolo") Long idProtocolo) {
			return response.success().data(service.buscaExigenciaByIdProtocolo(idProtocolo)).build();
		
	}
	
	@POST
	@Path("cria-exigencia")
	public Response criarExigencia(SiacolProtocoloExigenciaDto exigencia, @HeaderParam("Authorization") String token) {
		service.criarExigencia(exigencia, httpClientGoApi.getUserDto(token));
		return response.success().build();
	}
	
	@POST
	@Path("exclui-exigencia")
	public Response excluirExigencia(SiacolProtocoloExigenciaDto exigencia, @HeaderParam("Authorization") String token) {
		service.excluirExigencia(exigencia, httpClientGoApi.getUserDto(token));
		return response.success().build();
	}

	@GET
	@Publico
	@Path("busca-informacoes-requerimento/{numeroProtocolo}")
	public Response buscaDadosTextoProtocoloEmpresa(@PathParam("numeroProtocolo") Long numeroProtocolo) {
		
		ProtocoloSiacolEmpresaDto result = service.buscaDadosTextoProtocoloEmpresa(numeroProtocolo);
		return result != null ? response.success().data(result).build() : response.error().message("Protocolo não localizado").build();
	}
	
	@POST
	@Path("apensar")
	public Response apensarProtocolo(JuntadaProtocoloDto dto) {
		return response.success().data(service.apensarProtocolo(dto)).build(); 
	}
	
	@POST
	@Path("desapensar")
	public Response desapensarProtocolo(JuntadaProtocoloDto dto) {
		return response.success().data(service.desapensarProtocolo(dto)).build(); 
	}
	
	@PUT
	@Path("ocultar/{numeroProtocolo}")
	public Response ocultarProtocolo(@PathParam("numeroProtocolo") Long numeroProtocolo, @HeaderParam("Authorization") String token) {
		return response.success().data(service.ocultarProtocolo(numeroProtocolo, httpClientGoApi.getUserDto(token))).build(); 
	}
	
	@PUT
	@Path("mostrar/{numeroProtocolo}")
	public Response mostrarProtocolo(@PathParam("numeroProtocolo") Long numeroProtocolo, @HeaderParam("Authorization") String token) {
		return response.success().data(service.mostrarProtocolo(numeroProtocolo, httpClientGoApi.getUserDto(token))).build(); 
	}
		
	@GET
	@Publico
	@Path("marcar-ad-referendum/{idProtocolo}")
	public Response marcarComoAdReferendum(@PathParam("idProtocolo") Long idProtocolo) {
		return response.success().data(service.marcarComoAdReferendum(idProtocolo)).build(); 
	}

	@POST
	@Path("salvar-observacao")
	public Response salvarObservacaoMovimento(GenericSiacolDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.salvarObservacaoMovimento(dto, httpClientGoApi.getUserDto(token))).build();
	}
	
	@GET
	@Path("teste")
	public Response teste(@HeaderParam("Authorization") String token) {
		UserFrontDto user = httpClientGoApi.getUserDto(token);
//		LinkedHashMap<String,String> map = user.getPerfil();
		
		
		ObjectMapper oMapper = new ObjectMapper();


        // object -> Map
        Map<?, ?> map = oMapper.convertValue(user.getPerfil(), Map.class);
        System.out.println(map.get("perfil"));
        
        
		return response.success().data(user).build(); 
	}
	

}
