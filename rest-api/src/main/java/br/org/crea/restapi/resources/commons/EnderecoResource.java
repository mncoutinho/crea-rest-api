package br.org.crea.restapi.resources.commons;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.cadastro.EnderecoService;
import br.org.crea.commons.util.ResponseRestApi;


@Resource
@Path("/endereco")
public class EnderecoResource {
	
	@Inject	ResponseRestApi response;
	
	@Inject EnderecoService service;
	
	@Inject	HttpClientGoApi httpClientGoApi;
	
	@GET
	@Path("tipo-endereco") @Publico
	public Response getTipoEndereco(){
		return response.success().data(service.getTipoEndereco()).build();
	}
	
	@GET
	@Path("tipo-logradouro") @Publico
	public Response getTipoLogradouro(){
		return response.success().data(service.getTipoLogradouro()).build();
	}
	
	@GET
	@Path("lista-endereco/{idPessoa}") @Publico
	public Response getListEndereco(@PathParam("idPessoa") Long idPessoa){
		return response.success().data(service.getListEnderecoPorIdPessoa(idPessoa)).build();
	}

	
	@GET
	@Path("tipo-logradouro/{descricao}") @Publico
	public Response getTipoLogradouroByDescricao(@PathParam("descricao") String descricao){
		return response.success().data(service.getTipoLogradouroByDescricao(descricao)).build();
	}
	
	@GET
	@Path("uf") @Publico
	public Response getUf(){
		return response.success().data(service.getUf()).build();
	}
	
	@GET
	@Path("uf-paises") @Publico
	public Response getUfPaises(){
		return response.success().data(service.getUfPaises()).build();
	}
	
	@GET
	@Path("municipio/{uf}") @Publico
	public Response getMunicipioBySigla(@PathParam("uf") String uf){
		return response.success().data(service.getMunicipioBySigla(uf)).build();
	}

	@GET
	@Path("{idUf}/municipio") @Publico
	public Response getMunicipioByIdUf(@PathParam("idUf") Long idUf){
		return response.success().data(service.getMunicipioByIdUf(idUf)).build();
	}
	
	@GET
	@Path("{cep}") @Publico
	public Response getEnderecoByCep(@PathParam("cep") String cep){
		EnderecoDto endereco = new EnderecoDto();
		endereco = service.getEnderecoByCep(cep);
		
		if (endereco == null ){
			return response.error().message("endereco.not.exists").build();
		}else{
			return response.success().data(endereco).build();
		}
	}
	
	@GET
	@Path("pessoa/{id}")
	public Response getEnderecoPessoa(@PathParam("id") Long id){
		EnderecoDto endereco = service.getEnderecoPessoaById(id);
		
		if(endereco != null){
			return response.success().data(endereco).build();
		}else{
			return response.information().build();
		}
	}
	
	@GET
	@Path("valido/pessoa/{id}") @Publico
	public Response getEnderecoValidoPessoa(@PathParam("id") Long id){
		EnderecoDto endereco = service.getEnderecoValidoPessoaById(id);
		
		if(endereco != null){
			return response.success().data(endereco).build();
		}else{
			return response.information().build();
		}
	}
	
	@POST
	@Path("busca-cep") @Publico
	public Response getCepPorLogradouro(EnderecoDto enderecoDto){
		return response.success().data(service.listarCepPorLogradouro(enderecoDto)).build();
	}
	
	@GET
	@Path("busca-localidade/{descricao}") @Publico
	public Response getLocalidadePorDescricao(@PathParam("descricao") String descricao){
		return response.success().data(service.getLocalidadePorDescricao(descricao)).build();
	}

	@GET
	@Path("busca-localidade") @Publico
	public Response getLocalidadePorDescricaoEdescricaoUF(@QueryParam("descricaoLocalidade") String descricaoLocalidade, @QueryParam("descricaoUF") String descricaoUF){
		return response.success().data(service.getLocalidadePorDescricaoEdescricaoUF(descricaoLocalidade, descricaoUF)).build();
	}
	
	@GET
	@Path("busca-uf/{sigla}") @Publico
	public Response getUfPorSigla(@PathParam("sigla") String sigla){
		return response.success().data(service.getUfPorSigla(sigla)).build();
	}
	
	@GET
	@Path("busca-endereco/{idEndereco}") @Publico
	public Response getEnderecoById(@PathParam("idEndereco") Long idEndereco){
		return response.success().data(service.getEnderecoById(idEndereco)).build();
	}
	
	@PUT
	public Response atualizar(EnderecoDto dto, @HeaderParam("Authorization") String token){
		return response.success().data(service.atualizarEndereco(dto)).build();
	}
	
	@PUT
	@Path("exclui-endereco/{idEndereco}") @Publico
	public Response excluiEnderecoById(@PathParam("idEndereco") Long idEndereco){
		return response.success().data(service.excluiEnderecoById(idEndereco)).build();
	}
	
	@POST
	public Response adiciona(EnderecoDto dto, @HeaderParam("Authorization") String token){
		return response.success().data(service.adicionaEndereco(dto)).build();
	}
	
	@POST
	@Path("pessoa")
	public Response adicionaPessoa(EnderecoDto dto, @HeaderParam("Authorization") String token){
		return response.success().data(service.adicionaEnderecoPessoa(dto)).build();
	}
	
	@PUT
	@Path("art")
	public Response atualizarEnderecoArt(EnderecoDto dto, @HeaderParam("Authorization") String token){
		return response.success().data(service.atualizarEnderecoArt(dto)).build();
	}
	
	@PUT
	@Publico
	public Response atualizar(EnderecoDto dto){
		if (service.atualizarEndereco(dto)) {
			return response.information().message("Deve se ter pelo pelo menos um endereço postal.").build();
		} else {
			return response.success().build();		}
	}

	
}