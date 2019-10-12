package br.org.crea.restapi.resources.cadastro;

import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.crea.cadastro.service.FormandoService;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.cadastro.dtos.pessoa.FormandoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.protocolo.ProtocoloService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/cadastro/formandos")
public class FormandoResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject FormandoService service;
	
	@Inject ProtocoloService protocoloService;
	
	@Inject	HttpClientGoApi httpClientGoApi;
	
	
	@POST
	public Response cadastra(FormandoDto dto, @HeaderParam("Authorization") String token) {
		service.cadastra(dto,httpClientGoApi.getUserDto(token));
		return response.success().data(dto).build();
	}
	
	@POST
	@Path("gerar-pdf-cadastrados")
	public Response relatorio(FormandoDto dto) {
				
		return Response.ok(service.gerarPDFComListaDeProcessados(dto))
				.header("Content-Disposition", "attachment; filename=" + "relatorio-de-formandos-cadastrados" + ".pdf" )
				.header("Content-Type", "application/pdf").type(MediaType.APPLICATION_OCTET_STREAM).build();
	}
	
	@POST
	@Path("gerar-planilha-modelo")
	public Response gerarPlanilhaModelo(FormandoDto dto) {
				
		return Response.ok(service.gerarPlanilhaModelo(dto))
				.header("Content-Disposition", "attachment; filename=" + "formandos-planilha-modelo-"+dto.getCurso().getDescricao()+".xls" )
				.header("Content-Type", "application/vnd.ms-excel").type(MediaType.APPLICATION_OCTET_STREAM).build();
	}
	
	@POST
	@Path("pesquisa")
	public Response pesquisa(FormandoDto dto) {
		return response.success().data(service.getAllByFormandoDtoForGrid(dto)).build();
	}
	
	@POST
	@Path("valida")
	public Response valida(FormandoDto dto) {
		if(service.verificaSeOFormandoJaEstaCadastradoNoCurso(dto)) return response.error().message("Formando "+dto.getNome()+" já está cadastrado para o curso").build();
		return response.success().build();
	}
	
	@POST
	@Path("cadastra-protocolo-externo")
	public Response geraProtocolo(FormandoDto dto) {
		try {
			dto = protocoloService.cadastrarProtocoloParaCadastroDeFormandosExterno(dto);
			if(dto.isProtocoloGerado()) service.enviaDocumentoProtocoloPorEmail(dto);
			return response.success().data(dto).build();
		} catch (Exception e) {
			return response.error().message("Ocorreu um erro durante sua requisão.").build();
		}		
	}
	
	@POST
	@Path("consulta")
	public Response consulta(FormandoDto dto) {
		dto = service.consultar(dto);
		if (!dto.getFormandos().isEmpty()) {
			if (dto.ehPrimeiraConsulta()) {
				return response.success().totalCount(service.getTotalConsulta(dto)).data(dto).build();
			} else {
				return response.success().data(dto).build();
			}
		} else {
			return response.success().data(dto).build();
		}
	}
	
	@POST
	@Path("consulta-exportar-planilha")
	public Response consultaExportarPlanilha(FormandoDto dto) {
		return Response.ok(service.consultaExportarPlanilha(dto))
				.header("Content-Disposition", "attachment; filename=" + "formandos-"+dto.getInstituicao().getNome()+".xls" )
				.header("Content-Type", "application/vnd.ms-excel").type(MediaType.APPLICATION_OCTET_STREAM).build();
	}

}