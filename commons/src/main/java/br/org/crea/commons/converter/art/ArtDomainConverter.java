package br.org.crea.commons.converter.art;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.art.ArtClasseProduto;
import br.org.crea.commons.models.art.ArtClasseToxicologica;
import br.org.crea.commons.models.art.ArtCultura;
import br.org.crea.commons.models.art.ArtDiagnostico;
import br.org.crea.commons.models.art.ArtGrupoQuimico;
import br.org.crea.commons.models.art.ArtIngredienteAtivo;
import br.org.crea.commons.models.art.BaixaArt;
import br.org.crea.commons.models.art.ComplementoArt;
import br.org.crea.commons.models.art.ContratoArtFinalidade;
import br.org.crea.commons.models.art.ContratoArtTipoAcaoInstitucional;
import br.org.crea.commons.models.art.ContratoArtTipoCargoFuncao;
import br.org.crea.commons.models.art.ContratoArtTipoContratante;
import br.org.crea.commons.models.art.ContratoArtTipoFuncao;
import br.org.crea.commons.models.art.ContratoArtTipoUnidadeAdministrativa;
import br.org.crea.commons.models.art.ContratoArtTipoVinculo;
import br.org.crea.commons.models.art.ConvenioPublico;
import br.org.crea.commons.models.art.FatoGeradorArt;
import br.org.crea.commons.models.art.ParticipacaoTecnicaArt;
import br.org.crea.commons.models.art.RamoArt;
import br.org.crea.commons.models.art.TipoArt;
import br.org.crea.commons.models.cadastro.Ramo;
import br.org.crea.commons.models.cadastro.UnidadeMedida;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.fiscalizacao.ContratoAtividade;

public class ArtDomainConverter {

	public List<GenericDto> toListArtClasseToxicacaoDto(List<ArtClasseToxicologica> lista){

		List<GenericDto> artToxicacoes = new ArrayList<GenericDto>();

		for(ArtClasseToxicologica artClasseToxicacao : lista){
			artToxicacoes.add(toArtClasseToxicacaoDto(artClasseToxicacao));
		}

		return artToxicacoes;
	}

	public GenericDto toArtClasseToxicacaoDto(ArtClasseToxicologica model){
		GenericDto dto = new GenericDto();

		dto.setCodigo(model.getCodigo());
		dto.setDescricao(model.getDescricao());

		return dto;
	}

	public List<GenericDto> toListArtIngredienteAtivoDto(List<ArtIngredienteAtivo> lista){

		List<GenericDto> artToxicacoes = new ArrayList<GenericDto>();

		for(ArtIngredienteAtivo artClasseToxicacao : lista){
			artToxicacoes.add(toArtIngredienteAtivoDto(artClasseToxicacao));
		}

		return artToxicacoes;
	}

	public GenericDto toArtIngredienteAtivoDto(ArtIngredienteAtivo model){
		GenericDto dto = new GenericDto();

		dto.setCodigo(String.valueOf(model.getCodigo()));
		dto.setDescricao(model.getDescricao());

		return dto;
	}



	public List<GenericDto> toListArtCulturaDto(List<ArtCultura> lista){

		List<GenericDto> artCulturas = new ArrayList<GenericDto>();

		for(ArtCultura artCultura : lista){
			artCulturas.add(toArtCulturaDto(artCultura));
		}

		return artCulturas;
	}

	public GenericDto toArtCulturaDto(ArtCultura model){
		GenericDto dto = new GenericDto();

		dto.setCodigo(String.valueOf(model.getCodigo()));
		dto.setDescricao(model.getDescricao());

		return dto;
	}



	public List<GenericDto> toListArtGrupoQuimicoDto(List<ArtGrupoQuimico> lista){

		List<GenericDto> artGruposQuimicos = new ArrayList<GenericDto>();

		for(ArtGrupoQuimico artGrupoQuimico : lista){
			artGruposQuimicos.add(toArtGrupoQuimicoDto(artGrupoQuimico));
		}

		return artGruposQuimicos;
	}

	public GenericDto toArtGrupoQuimicoDto(ArtGrupoQuimico model){
		GenericDto dto = new GenericDto();

		dto.setCodigo(String.valueOf(model.getCodigo()));
		dto.setDescricao(model.getDescricao());

		return dto;
	}





	public List<GenericDto> toListArtDiagnosticoDto(List<ArtDiagnostico> lista){

		List<GenericDto> artDiagnosticos = new ArrayList<GenericDto>();

		for(ArtDiagnostico artDiagnostico : lista){
			artDiagnosticos.add(toArtDiagnosticoDto(artDiagnostico));
		}

		return artDiagnosticos;
	}

	public GenericDto toArtDiagnosticoDto(ArtDiagnostico model){
		GenericDto dto = new GenericDto();

		dto.setCodigo(String.valueOf(model.getCodigo()));
		dto.setDescricao(model.getDescricao());

		return dto;
	}




	public List<GenericDto> toListArtClasseProdutoDto(List<ArtClasseProduto> lista){

		List<GenericDto> artToxicacoes = new ArrayList<GenericDto>();

		for(ArtClasseProduto artClasseProduto : lista){
			artToxicacoes.add(toArtClasseProdutoDto(artClasseProduto));
		}

		return artToxicacoes;
	}

	public GenericDto toArtClasseProdutoDto(ArtClasseProduto model){
		GenericDto dto = new GenericDto();

		dto.setCodigo(String.valueOf(model.getCodigo()));
		dto.setDescricao(model.getDescricao());

		return dto;
	}

	public List<GenericDto> toListUnidadeMedidaReceitaDto(List<UnidadeMedida> lista){
		List<GenericDto> unidadesMedida = new ArrayList<GenericDto>();
		
		for(UnidadeMedida unidade : lista){
			unidadesMedida.add(toUnidadeMedidaReceitaDto(unidade));
		}
		
		return unidadesMedida;
	}
	
	public GenericDto toUnidadeMedidaReceitaDto(UnidadeMedida model){
		GenericDto dto = new GenericDto();

		dto.setCodigo(String.valueOf(model.getCodigo()));
		dto.setDescricao(model.getDescricao());

		return dto;
	}
	
	public List<GenericDto> toListAllRamosDto(List<Ramo> lista){
		List<GenericDto> ramos = new ArrayList<GenericDto>();
		
		for(Ramo ramo : lista){
			ramos.add(toRamoDto(ramo));
		}
		
		return ramos;
	}

	public GenericDto toRamoDto(Ramo model) {
		GenericDto dto = new GenericDto();

		dto.setId(String.valueOf(model.getId()));
		dto.setDescricao(model.getDescricao());

		return dto;
	}

	public List<DomainGenericDto> toListComplementoArtDto(List<ComplementoArt> lista) {
		List<DomainGenericDto> complementos = new ArrayList<DomainGenericDto>();
		
		for(ComplementoArt c : lista){
			complementos.add(toComplementoArtDto(c));
		}
		
		return complementos;
	}

	public DomainGenericDto toComplementoArtDto(ComplementoArt c) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(c.getId());
		dto.setNome(c.getDescricao());
		
		return dto;
	}

	public List<GenericDto> toListAtividadesDto(List<ContratoAtividade> lista) {
		List<GenericDto> atividades = new ArrayList<GenericDto>();
		
		for(ContratoAtividade c : lista){
			atividades.add(toAtividadeDto(c));
		}
		
		return atividades;
	}

	public GenericDto toAtividadeDto(ContratoAtividade c) {
		GenericDto dto = new GenericDto();
		dto.setCodigo(String.valueOf(c.getCodigo()));
		dto.setDescricao(c.getDescricao());
		
		return dto;
	}
	

	public List<DomainGenericDto> toListFatoGeradorDto(List<FatoGeradorArt> lista) {
		List<DomainGenericDto> fatoGerador = new ArrayList<DomainGenericDto>();
		
		for(FatoGeradorArt f : lista){
			fatoGerador.add(toFatoGeradorDto(f));
		}
		
		return fatoGerador;
	}
	
	public DomainGenericDto toFatoGeradorDto(FatoGeradorArt f) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(f.getId());
		dto.setNome(f.getDescricao());
		
		return dto;
	}
	
	public List<DomainGenericDto> toListTipoDto(List<TipoArt> lista) {
		List<DomainGenericDto> tipoArt = new ArrayList<DomainGenericDto>();
		
		for(TipoArt t : lista){
			tipoArt.add(toTipoArtDto(t));
		}
		
		return tipoArt;
	}
	
	public DomainGenericDto toTipoArtDto(TipoArt t) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(t.getId());
		dto.setNome(t.getDescricao());
		
		return dto;
	}

	public List<DomainGenericDto> toListConvenioPublicoDto(List<ConvenioPublico> lista) {
		List<DomainGenericDto> convenioPublico = new ArrayList<DomainGenericDto>();
		
		for(ConvenioPublico c : lista){
			convenioPublico.add(toConvenioPublicoDto(c));
		}
		
		return convenioPublico;
	}
	
	public DomainGenericDto toConvenioPublicoDto(ConvenioPublico c) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(c.getId());
		dto.setNome(c.getDescricao());
		
		return dto;
	}

	public List<DomainGenericDto> toListUnidadeMedidaDto(List<UnidadeMedida> lista) {
		List<DomainGenericDto> unidadeMedida = new ArrayList<DomainGenericDto>();
		
		for(UnidadeMedida u : lista){
			unidadeMedida.add(toUnidadeMedidaDto(u));
		}
		
		return unidadeMedida;
	}
	
	public DomainGenericDto toUnidadeMedidaDto(UnidadeMedida u) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(u.getCodigo());
		dto.setNome(u.getDescricao());
		dto.setSigla(u.getAbreviatura());
		
		return dto;
	}
	
	public DomainGenericDto toTipoUnidadeAdministrativaDto(ContratoArtTipoUnidadeAdministrativa contratoArtTipoUnidadeAdministrativa) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(contratoArtTipoUnidadeAdministrativa.getId());
		dto.setDescricao(contratoArtTipoUnidadeAdministrativa.getDescricao());
		
		return dto;
	}
	
	public List<GenericDto> toListRamosDto(List<RamoArt> lista){
		List<GenericDto> ramos = new ArrayList<GenericDto>();
		
		for(RamoArt ramo : lista){
			ramos.add(toRamoDto(ramo));
		}
		
		return ramos;
	}

	public GenericDto toRamoDto(RamoArt model) {
		GenericDto dto = new GenericDto();

		dto.setId(String.valueOf(model.getId()));
		dto.setDescricao(model.getDescricao());

		return dto;
	}
	
	public List<DomainGenericDto> toListTiposUnidadesAdministrativaDto(List<ContratoArtTipoUnidadeAdministrativa> lista) {
		List<DomainGenericDto> tiposUnidadesAdministrativa = new ArrayList<DomainGenericDto>();
		
		for(ContratoArtTipoUnidadeAdministrativa u : lista){
			tiposUnidadesAdministrativa.add(toTipoUnidadeAdministrativaDto(u));
		}
		
		return tiposUnidadesAdministrativa;
	}

	public List<DomainGenericDto> toListTiposAcoesInstitucionaisDto(
			List<ContratoArtTipoAcaoInstitucional> lista) {
		
		List<DomainGenericDto> tiposAcoesInstitucionais = new ArrayList<DomainGenericDto>();
		
		for(ContratoArtTipoAcaoInstitucional u : lista){
			tiposAcoesInstitucionais.add(toTipoAcaoInstitucionalDto(u));
		}
		
		return tiposAcoesInstitucionais;
	}

	private DomainGenericDto toTipoAcaoInstitucionalDto(ContratoArtTipoAcaoInstitucional u) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(u.getId());
		dto.setDescricao(u.getDescricao());
		return dto;
	}

	public List<DomainGenericDto> toListTiposCargosFuncoesDto(List<ContratoArtTipoCargoFuncao> lista) {
		List<DomainGenericDto> tiposCargosFuncoes = new ArrayList<DomainGenericDto>();
		
		for(ContratoArtTipoCargoFuncao u : lista){
			tiposCargosFuncoes.add(toTipoCargoFuncaoDto(u));
		}
		
		return tiposCargosFuncoes;
	}

	private DomainGenericDto toTipoCargoFuncaoDto(ContratoArtTipoCargoFuncao u) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(u.getId());
		dto.setDescricao(u.getDescricao());
		return dto;
	}

	public List<DomainGenericDto> toListTiposFuncoesDto(List<ContratoArtTipoFuncao> lista) {
		
		List<DomainGenericDto> tiposFuncoes = new ArrayList<DomainGenericDto>();
		
		for(ContratoArtTipoFuncao u : lista){
			tiposFuncoes.add(toTipoFuncaoDto(u));
		}
		
		return tiposFuncoes;
	}

	private DomainGenericDto toTipoFuncaoDto(ContratoArtTipoFuncao u) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(u.getId());
		dto.setDescricao(u.getDescricao());
		return dto;
	}

	public List<DomainGenericDto> toListTiposVinculosDto(List<ContratoArtTipoVinculo> lista) {
		
		List<DomainGenericDto> tiposCargosFuncoes = new ArrayList<DomainGenericDto>();
		
		for(ContratoArtTipoVinculo u : lista){
			tiposCargosFuncoes.add(toTipoVinculoDto(u));
		}
		
		return tiposCargosFuncoes;
	}

	private DomainGenericDto toTipoVinculoDto(ContratoArtTipoVinculo u) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(u.getId());
		dto.setDescricao(u.getDescricao());
		return dto;
	}

	public List<DomainGenericDto> toListTiposContratantesDto(List<ContratoArtTipoContratante> lista) {
		
		List<DomainGenericDto> tiposContratantes = new ArrayList<DomainGenericDto>();
		
		for(ContratoArtTipoContratante u : lista){
			tiposContratantes.add(toTipoContratanteDto(u));
		}
		
		return tiposContratantes;
	}

	private DomainGenericDto toTipoContratanteDto(ContratoArtTipoContratante u) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(u.getId());
		dto.setDescricao(u.getDescricao());
		return dto;
	}

	public List<DomainGenericDto> toListFinalidadesDto(List<ContratoArtFinalidade> lista) {
		
		List<DomainGenericDto> finalidades = new ArrayList<DomainGenericDto>();
		
		for(ContratoArtFinalidade u : lista){
			finalidades.add(toFinalidadeDto(u));
		}
		
		return finalidades;
	}

	private DomainGenericDto toFinalidadeDto(ContratoArtFinalidade u) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(u.getId());
		dto.setDescricao(u.getDescricao());
		return dto;
	}
	
	public List<DomainGenericDto> toListTiposBaixaDto(List<BaixaArt> lista) {
		
		List<DomainGenericDto> tiposBaixa = new ArrayList<DomainGenericDto>();
		
		for(BaixaArt u : lista){
			tiposBaixa.add(toTipoBaixaDto(u));
		}
		
		return tiposBaixa;
	}
	
	private DomainGenericDto toTipoBaixaDto(BaixaArt u) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(u.getId());
		dto.setDescricao(u.getDescricao());
		return dto;
	}

	public List<DomainGenericDto> toListParticipacaoTecnicaDto(List<ParticipacaoTecnicaArt> lista) {
		List<DomainGenericDto> listParticipacaoTecnica = new ArrayList<DomainGenericDto>();
		
		for(ParticipacaoTecnicaArt u : lista){
			listParticipacaoTecnica.add(toParticipacaoTecnicaDto(u));
		}
		
		return listParticipacaoTecnica;
	}

	private DomainGenericDto toParticipacaoTecnicaDto(ParticipacaoTecnicaArt u) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(u.getId());
		dto.setDescricao(u.getDescricao());
		return dto;
	}

}
