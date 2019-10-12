package br.org.crea.restapi.resources.siacol;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDetalhadoSiacol04Dto;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.RelatorioSiacolService;

@Resource
@Path("/siacol/relatorios")
public class RelatoriosSiacolResource {

	@Inject ResponseRestApi response;

	@Inject RelatorioSiacolService service;
	
	@POST
	@Publico
	public Response geraRelatorio(PesquisaRelatorioSiacolDto dto) {
		return response.success().data(service.geraRelatorio(dto)).build(); 
	}
	
	@POST
	@Publico
	@Path("xls")
	public Response geraRelatorioXls(PesquisaRelatorioSiacolDto dto) {
		return Response.ok(service.geraRelatorioXls(dto))
				.header("Content-Disposition", "attachment; filename=" + "planilha-"+dto.getTipo()+".xls" )
				.header("Content-Type", "application/vnd.ms-excel").type(MediaType.APPLICATION_OCTET_STREAM).build();
	}
	
	@POST
	@Publico
	@Path("xls-detalhe-rel04")
	public Response geraRelatorioXlsRel04Detalhe(List<RelDetalhadoSiacol04Dto> dto) {
		return Response.ok(service.geraRelatorioXlsDetalheRel04(dto))
				.header("Content-Disposition", "attachment; filename=" + "planilha.xls" )
				.header("Content-Type", "application/vnd.ms-excel").type(MediaType.APPLICATION_OCTET_STREAM).build();
	}
	
}

