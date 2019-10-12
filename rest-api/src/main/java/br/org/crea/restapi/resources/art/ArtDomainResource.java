package br.org.crea.restapi.resources.art;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.crea.art.service.ArtReceitaService;
import br.org.crea.commons.annotations.Publico;
import br.org.crea.commons.cdi.stereotype.Resource;
import br.org.crea.commons.models.art.dtos.NaturezaDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.service.art.ArtDomainService;
import br.org.crea.commons.util.ResponseRestApi;

@Resource
@Path("/art")
public class ArtDomainResource {

	@Inject
	ResponseRestApi response;

	@Inject
	ArtDomainService service;

	@Inject
	ArtReceitaService receitaService;

	@Inject
	HttpClientGoApi httpClientGoApi;

	@GET
	@Path("culturas")
	@Publico
	public Response getArtCulturas() {
		return response.success().data(service.getAllArtCulturas()).build();
	}

	@GET
	@Path("natureza")
	public Response getNatureza(@HeaderParam("Authorization") String token) {
		return response.success().data(service.getAllNatureza(httpClientGoApi.getUserDto(token))).build();
	}

	@GET
	@Path("entidades-classe")
	@Publico
	public Response getEntidadesClasse() {
		return response.success().data(service.getEntidadesClasse()).build();
	}

	@GET
	@Path("diagnosticos")
	@Publico
	public Response getArtDiagnosticos() {
		return response.success().data(service.getAllArtDiagnosticos()).build();
	}

	@GET
	@Path("produtos")
	@Publico
	public Response getArtProdutos() {
		return response.success().data(receitaService.getAllArtProdutos()).build();
	}

	@GET
	@Path("unidades-medida-receita")
	@Publico
	public Response getUnidadesMedidaReceita() {
		return response.success().data(service.getAllUnidadesMedidaArtReceita()).build();
	}

	@GET
	@Path("ramos-agronomia")
	@Publico
	public Response getRamos() {
		return response.success().data(service.getAllRamosAgronomia()).build();
	}

	@GET
	@Path("ramos/{idProfissional}")
	public Response getRamosByIdProfissional(@PathParam("idProfissional") Long idProfissional) {
		return response.success().data(service.getRamosByIdProfissional(idProfissional)).build();
	}
	
	@GET
	@Path("complemento-art")
	@Publico
	public Response getComplementosArt() {
		return response.success().data(service.getAllComplementosArt()).build();
	}

	@GET
	@Path("atividades")
	@Publico
	public Response getAtividades() {
		return response.success().data(service.getAllAtividades()).build();
	}

	@POST
	@Path("atividades-tecnicas/{idProfissional}")
	public Response getAtividadesTecnicasByIdProfissional(@PathParam("idProfissional") Long idProfissional, NaturezaDto dto) {
		return response.success().data(service.getAllAtividadesTecnicasByIdProfissional(idProfissional, dto)).build();
	}

	@POST
	@Path("especificacao-atividade/{idProfissional}")
	public Response getEspecificacaoAtividadeByIdProfissional(@PathParam("idProfissional") Long idProfissional, NaturezaDto dto) {
		return response.success().data(service.getAllEspecificacaoAtividadeByIdProfissional(idProfissional, dto)).build();
	}
	
	@POST
	@Path("complemento/{idProfissional}")
	public Response getComplementosArtByIdProfissional(@PathParam("idProfissional") Long idProfissional, NaturezaDto dto) {
		return response.success().data(service.getAllComplementosArtByIdProfissional(idProfissional, dto)).build();
	}

	@GET
	@Path("fato-gerador")
	@Publico
	public Response getFatoGerador() {
		return response.success().data(service.getAllFatoGerador()).build();
	}

	@GET
	@Path("tipo")
	@Publico
	public Response getTipo() {
		return response.success().data(service.getAllTipo()).build();
	}
	
	@GET
	@Path("participacao-tecnica")
	@Publico
	public Response getParticipacaoTecnica() {
		return response.success().data(service.getAllParticipacaoTecnica()).build();
	}

	@GET
	@Path("convenio-publico")
	@Publico
	public Response getConvenioPublico() {
		return response.success().data(service.getAllConvenioPublico()).build();
	}

	@GET
	@Path("unidade-medida")
	@Publico
	public Response getUnidadeMedida() {
		return response.success().data(service.getAllUnidadeMedida()).build();
	}
	
	@GET
	@Path("tipos-unidades-administrativas")
	@Publico
	public Response getTiposUnidadesAdministrativas() {
		return response.success().data(service.getTiposUnidadesAdministrativasAtivas()).build();
	}
	
	@GET
	@Path("tipos-acoes-institucionais")
	@Publico
	public Response getTiposAcoesInstitucionais() {
		return response.success().data(service.getTiposAcoesInstitucionaisAtivas()).build();
	}
	
	@GET
	@Path("tipos-cargos-funcoes")
	@Publico
	public Response getTiposCargosFuncoes() {
		return response.success().data(service.getTiposCargosFuncoesAtivas()).build();
	}
	
	@GET
	@Path("tipos-funcoes")
	@Publico
	public Response getTiposFuncoes() {
		return response.success().data(service.getTiposFuncoesAtivas()).build();
	}
	
	@GET
	@Path("tipos-vinculos")
	@Publico
	public Response getTiposVinculos() {
		return response.success().data(service.getTiposVinculosAtivos()).build();
	}
	
	@GET
	@Path("tipos-contratantes")
	@Publico
	public Response getTiposContratantes() {
		return response.success().data(service.getTiposContratantesAtivos()).build();
	}
	
	@GET
	@Path("tipos-baixa")
	@Publico
	public Response getTiposBaixa() {
		return response.success().data(service.getTiposBaixa()).build();
	}
	
	@GET
	@Path("finalidades")
	@Publico
	public Response getFinalidades() {
		return response.success().data(service.getFinalidadesAtivas()).build();
	}

}
