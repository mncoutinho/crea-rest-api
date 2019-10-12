package br.org.crea.restapi.resources.siacol;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.models.siacol.dtos.HabilidadePessoaDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.ResponseRestApi;
import br.org.crea.siacol.service.HabilidadePessoaService;


@Resource
@Path("siacol/habilidade")
public class HabilidadeResource {

	@Inject ResponseRestApi response;

	@Inject HabilidadePessoaService service;
	
	@Inject HttpClientGoApi httpClientGoApi;

	@GET
	@Path("{idPessoa}") @Publico
	public Response verificaAtivo(@PathParam("idPessoa") Long idPessoa){	
		return response.success().data(service.verificaAtivo(idPessoa)).build();
	}
	
	@GET
	@Path("departamento-habilitado/{idPessoa}") @Publico
	public Response listaDepartamentosHabilitado(@PathParam("idPessoa") Long idPessoa){	
		return response.success().data(service.listaDepartamentosHabilitado(idPessoa)).build();
	}
	
	@POST
	@Path("pessoa-habilidade/") @Publico
	public Response listaPessoaHabilidade(GenericDto dto){	
		return response.success().data(service.listaPessoaHabilidade(dto)).build();
	}
	
	@GET
	@Path("coordenador-coac/{idPessoa}") @Publico
	public Response ehCoordenadorCoac(@PathParam("idPessoa") Long idPessoa){	
		return response.success().data(service.ehCoordenadorCoac(idPessoa)).build();
	}
	
	@GET
	@Publico
	@Path("verifica-programacao/{idPessoa}") 
	public Response verificaProgramacao(@PathParam("idPessoa") Long idPessoa) {
		return response.success().data(service.verificaProgramacao(idPessoa)).build();
	}
	
	
	@POST
	@Path("programacao/cria") @Publico
	public Response programaHabilidadeCria(GenericSiacolDto dto) { 
		return response.success().data(service.programaHabilidadeCria(dto)).message("Programação feita com sucesso").build();
	}
	
	
	@POST
	@Path("programacao/remove") @Publico
	public Response programaHabilidadeRemove(GenericSiacolDto dto) { 
		return response.information().data(service.programaHabilidadeRemove(dto)).message("Programação removida com sucesso").build();
	}

	@POST
	@Path("pessoas-habilidade")
	public Response buscaAnalistasPorHabilidade(GenericSiacolDto dto, @HeaderParam("Authorization") String token) {
		return response.success().data(service.getPessoasByHabilidade(dto, httpClientGoApi.getUserDto(token))).build();
	}
	
	@POST
	@Path("coordenador")
	@Publico
	public Response buscaCoordenador(GenericSiacolDto dto) {
		PessoaDto pessoa = new PessoaDto();
		pessoa = service.buscaCoordenador(dto);
		if(pessoa != null){
			return response.success().data(pessoa).build();
		}else{
			return response.information().build();
		}
	}
	
	@POST
	@Path("conselheiro-disponivel")
	@Publico
	public Response buscaResponsavelAleatorioDistribuicao(GenericSiacolDto dto,  @HeaderParam("Authorization") String token) {
		return response.success().data(service.buscaResponsavelAleatoriaDistribuicao(dto, httpClientGoApi.getUserDto(token))).build();
	}
	
	
	@POST
	@Path("analista/atualiza-habilidade") @Publico
	public Response atualizaHabilidadesAnalista(HabilidadePessoaDto dto) {
		return response.success().data(service.atualizaHabilidadesAnalista(dto)).build();
	}
	
	@POST
	@Path("analista/ativa-todos") @Publico
	public Response ativaTodasHabilidadesAnalista(List<HabilidadePessoaDto> ListDto) { 
		return response.success().data(service.ativaTodasHabilidadesAnalista(ListDto)).build();
	}
	
	
	@POST
	@Publico	
	@Path("analista/desativa-todos") 
	public Response desativaTodasHabilidadesAnalista(GenericSiacolDto dto) {
		
		if (service.desativaTodasHabilidadesAnalista(dto)) {
			return response.success().message("Habilidades desabilitadas").build();
		}else {
			return response.information().message("Não foi possível desabilitar as habilidades desse funcionário").build();
		}
		
	}
	
	
	@POST
	@Path("conselheiro/atualiza-habilidade") @Publico
	public Response atualizaHabilidadesConselheiro(HabilidadePessoaDto dto) {
		return response.success().data(service.atualizaHabilidadesConselheiro(dto)).build();
	}
	
	@POST
	@Path("conselheiro/ativa-todos") @Publico
	public Response ativaTodasHabilidadesConselheiro(List<HabilidadePessoaDto> ListDto) { 
		return response.success().data(service.ativaTodasHabilidadesConselheiro(ListDto)).build();
	}	
	
	@POST
	@Publico	
	@Path("conselheiro/desativa-todos") 
	public Response desativaTodasHabilidadesConselheiro(GenericSiacolDto dto) {
		
		if (service.desativaTodasHabilidadesConselheiro(dto)) {
			return response.success().message("Habilidades desabilitadas").build();
		}else {
			return response.information().message("Não foi possível desabilitar as habilidades desse funcionário").build();
		}
		
	}
	
	
	@DELETE
	@Publico
	@Path("{id}")
	public Response ativaHabilidade(@PathParam("id") Long id) { 
		service.ativaHabilidade(id);
		return response.success().data("Deletado com sucesso").build();
	}
	
}
