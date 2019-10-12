package br.org.crea.restapi.resources.siacol;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.siacol.dtos.AtualizaPainelPlenariaSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.ParticipanteReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaVotoReuniaoDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.VotoOfflineSiacolDto;
import br.org.crea.commons.models.siacol.dtos.VotoReuniaoSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.VotoReuniaoSiacolService;

@Resource
@Path("siacol/voto-reuniao-camara")
public class VotoReuniaoSiacolResource {

	@Inject
	ResponseRestApi response;
	
	@Inject HttpClientGoApi httpClientGoApi;

	@Inject
	private VotoReuniaoSiacolService service;
	
	@POST
	@Path("salvar")
	@Publico
	public Response enviar(VotoReuniaoSiacolDto dto) {
		
		VotoReuniaoSiacolDto votoReuniaoSiacolDto = service.salvar(dto);
		if (votoReuniaoSiacolDto != null) {
			return response.success().data(votoReuniaoSiacolDto).build();
		} else {
			return response.information().message("A reunião está fechada").build();
		}
		
	}

	@GET
	@Path("voto/{id}")
	@Publico
	public Response getVotoByIdVoto(@PathParam("id") Long id) {
		return response.success().data(service.getByIdVoto(id)).build();
	}
	
	@GET
	@Path("conselheiros/{idItemPauta}")
	@Publico
	public Response getConselheirosPorIdItemPautaVotado(@PathParam("idItemPauta") Long idItemPauta) {
		List<ParticipanteReuniaoSiacolDto> listParticipanteDto = new ArrayList<ParticipanteReuniaoSiacolDto>();
		listParticipanteDto = service.getConselheirosPorIdItemPautaVotado(idItemPauta);
		return response.success().data(listParticipanteDto).build();
	}

	@GET
	@Path("protocolo/{id}")
	@Publico
	public Response getVotosByIdProtocolo(@PathParam("id") Long id) {
		return response.success().data(service.getVotosByIdProtocolo(id)).build();
	}
	
	@POST
	@Path("contagem")
	@Publico
	public Response contagem(PesquisaVotoReuniaoDto pesquisa) {
		return response.success().data(service.contagem(pesquisa)).build();
	}
	
	@POST
	@Path("pesquisa")
	@Publico
	public Response pesquisa(PesquisaVotoReuniaoDto pesquisa) {
		return response.success().data(service.pesquisa(pesquisa)).build();
	}
	
	@POST
	@Path("verifica-destaque")
	@Publico
	public Response verificaDestaque(PesquisaVotoReuniaoDto pesquisa) {
		
		if(service.protocoloFoiDestacado(pesquisa)) {
			return response.information().message("Protocolo em destaque.").build();
		} else {
			return response.success().build();
		}
	}
	
	@POST
	@Path("salvar-voto-presencial")
	@Publico
	public Response enviarVotoPresencial(VotoReuniaoSiacolDto dto) {
		
		VotoReuniaoSiacolDto votoReuniaoSiacolDto = service.salvarVotoPresencial(dto);
		if (votoReuniaoSiacolDto != null) {
			return response.success().data(votoReuniaoSiacolDto).build();
		} else {
			return response.information().message("Estes itens não estão em votação").build();
		}
		
	}
	
	@POST
	@Path("salvar-voto-offline-aclamacao")
	@Publico
	public Response salvarVotoOfflineOuAclamacao(VotoOfflineSiacolDto dto, @HeaderParam("Authorization") String token) {
		service.salvarVotoOfflineOuAclamacao(dto, httpClientGoApi.getUserDto(token));
		return response.success().build();
	}
	
	@PUT
	@Path("atualiza-votacao/{tipo}") @Publico
	public Response atualizaVotacao(@PathParam("tipo") String tipo, AtualizaPainelPlenariaSiacolDto painel) { 
		return response.success().data(service.atualizaVotacao(tipo, painel)).build();		
	}
	
	@POST
	@Path("verifica-votacao") @Publico
	public Response verificaVotacao(ReuniaoSiacolDto dto) {
		if(service.houveVotosSuficientes(dto)) {
			if(service.houveEmpate(dto)) {
				return response.information().message("empate").build();
			} else {
				return response.success().build();
			}
		} else {
			return response.information().message("votos.insuficientes").build();
		}
		
	}
	
	@POST
	@Path("enquete-respostas-empatadas") @Publico
	public Response getRespostasEmpatadas(ItemPautaDto dto) {
		return response.success().data(service.getRespostasEmpatadas(dto)).build();
	}
	
}
