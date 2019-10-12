package br.org.crea.commons.converter.art;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ArtCultura;
import br.org.crea.commons.models.art.ArtDiagnostico;
import br.org.crea.commons.models.art.ArtProduto;
import br.org.crea.commons.models.art.ArtReceita;
import br.org.crea.commons.models.art.ArtReceitaProduto;
import br.org.crea.commons.models.art.IdReceitaProduto;
import br.org.crea.commons.models.art.dtos.ArtProdutoDto;
import br.org.crea.commons.models.art.dtos.ArtReceitaDto;
import br.org.crea.commons.models.art.dtos.ReceitaProdutoDto;
import br.org.crea.commons.models.cadastro.UnidadeMedida;
import br.org.crea.commons.models.commons.dtos.GenericDto;

public class ArtReceitaConverter {

	

	@Inject
	ContratoServicoConverter contratoConverter;

	public ArtReceita toModel(ArtReceitaDto dto) {

		ArtReceita receita = new ArtReceita();

		Art art = new Art();
		art.setNumero(dto.getNumeroArt());
		receita.setArt(art);

		ArtCultura cultura = new ArtCultura();
		cultura.setCodigo(Long.parseLong(dto.getCultura().getCodigo()));
		receita.setCultura(cultura);

		ArtDiagnostico diagnostico = new ArtDiagnostico();
		diagnostico.setCodigo(Long.parseLong(dto.getDiagnostico().getCodigo()));
		receita.setDiagnostico(diagnostico);

		receita.setInformacoesComplementares(dto.getInformacoesComplementares());

		receita.setAreaVolumePeso(dto.getAreaVolumePeso());

		UnidadeMedida unidadeMedida = new UnidadeMedida();
		unidadeMedida.setCodigo(Long.parseLong(dto.getUnidadeMedida().getCodigo()));

		receita.setUnidadeMedida(unidadeMedida);

		return receita;
	}

	public ArtReceitaProduto toModelArtReceitaProduto(ReceitaProdutoDto dto) {

		IdReceitaProduto id = new IdReceitaProduto();
		id.setProduto(toProdutoModel(dto.getProduto()));

		ArtReceitaProduto receitaProduto = new ArtReceitaProduto();

		receitaProduto.setQuantidade(dto.getQuantidade());

		UnidadeMedida unidadeMedidaQuantidade = new UnidadeMedida();
		unidadeMedidaQuantidade.setCodigo(Long.parseLong(dto.getUnidadeMedidaQuantidade().getCodigo()));
		receitaProduto.setUnidadeMedidaQuantidade(unidadeMedidaQuantidade);

		receitaProduto.setDose(dto.getDose());
		UnidadeMedida unidadeMedidaDose = new UnidadeMedida();
		unidadeMedidaDose.setCodigo(Long.parseLong(dto.getUnidadeMedidaDose().getCodigo()));
		receitaProduto.setUnidadeMedidaDose(unidadeMedidaDose);

		receitaProduto.setIntervaloAplicacao(dto.getIntervaloAplicacao());
		receitaProduto.setPeriodoCarencia(dto.getPeriodoCarencia());
		receitaProduto.setId(id);
		receitaProduto.setPrescricao(dto.getPrescricao());

		return receitaProduto;

	}

	public List<ArtReceitaDto> toListDto(List<ArtReceita> lista) {
		List<ArtReceitaDto> listaDto = new ArrayList<ArtReceitaDto>();

		for (ArtReceita receita : lista) {
			listaDto.add(toDto(receita));
		}

		return listaDto;
	}

	public ArtReceitaDto toDto(ArtReceita receita) {
		
		ArtReceitaDto dto = new ArtReceitaDto();

		GenericDto culturaDto = new GenericDto();
		culturaDto.setCodigo(String.valueOf(receita.getCultura().getCodigo()));
		culturaDto.setDescricao(receita.getCultura().getDescricao());

		GenericDto diagnosticoDto = new GenericDto();
		diagnosticoDto.setCodigo(String.valueOf(receita.getDiagnostico().getCodigo()));
		diagnosticoDto.setDescricao(receita.getDiagnostico().getDescricao());

		GenericDto unidadeMedidaDto = new GenericDto();
		unidadeMedidaDto.setCodigo(String.valueOf(receita.getUnidadeMedida().getCodigo()));
		unidadeMedidaDto.setDescricao(receita.getUnidadeMedida().getDescricao());
		unidadeMedidaDto.setSigla(receita.getUnidadeMedida().getAbreviatura());

		dto.setAreaVolumePeso(receita.getAreaVolumePeso());
		dto.setCultura(culturaDto);
		dto.setDiagnostico(diagnosticoDto);
		dto.setId(receita.getId());
		dto.setInformacoesComplementares(receita.getInformacoesComplementares());
		dto.setNumeroArt(receita.getArt().getNumero());
		dto.setProdutos(getProdutos(receita.getReceitaProdutos()));
		dto.setUnidadeMedida(unidadeMedidaDto);

		dto.setContrato(contratoConverter.toServicoDto(receita.getContrato()));

		return dto;
	}

	private List<ReceitaProdutoDto> getProdutos(List<ArtReceitaProduto> lista) {
		List<ReceitaProdutoDto> produtos = new ArrayList<ReceitaProdutoDto>();

		for (ArtReceitaProduto receitaProduto : lista) {
			ReceitaProdutoDto dto = new ReceitaProdutoDto();

			GenericDto dtoUnidadeDose = new GenericDto();
			dtoUnidadeDose.setCodigo(String.valueOf(receitaProduto.getUnidadeMedidaDose().getCodigo()));
			dtoUnidadeDose.setDescricao(receitaProduto.getUnidadeMedidaDose().getDescricao());
			dtoUnidadeDose.setSigla(receitaProduto.getUnidadeMedidaDose().getAbreviatura());

			GenericDto dtoUnidadeQuantidade = new GenericDto();
			dtoUnidadeQuantidade.setCodigo(String.valueOf(receitaProduto.getUnidadeMedidaQuantidade().getCodigo()));
			dtoUnidadeQuantidade.setDescricao(receitaProduto.getUnidadeMedidaQuantidade().getDescricao());
			dtoUnidadeQuantidade.setSigla(receitaProduto.getUnidadeMedidaQuantidade().getAbreviatura());

			dto.setDose(receitaProduto.getDose());
			dto.setUnidadeMedidaDose(dtoUnidadeDose);
			dto.setProduto(toArtProdutoDto(receitaProduto.getId().getProduto()));
			dto.setIdReceita(receitaProduto.getId().getReceita().getId());
			dto.setIntervaloAplicacao(receitaProduto.getIntervaloAplicacao());
			dto.setPeriodoCarencia(receitaProduto.getPeriodoCarencia());
			dto.setQuantidade(receitaProduto.getQuantidade());
			dto.setUnidadeMedidaQuantidade(dtoUnidadeQuantidade);
			dto.setPrescricao(receitaProduto.getPrescricao());

			produtos.add(dto);
		}

		return produtos;
	}
	
	
	public List<ArtProdutoDto> toListArtProdutoDto(List<ArtProduto> lista){
		
		List<ArtProdutoDto> artProdutos = new ArrayList<ArtProdutoDto>();
		
		for(ArtProduto artProduto : lista){
			artProdutos.add(toArtProdutoDto(artProduto));
		}
		
		return artProdutos;
	}
	
	public ArtProdutoDto toArtProdutoDto(ArtProduto model){
	
		ArtProdutoDto dto = new ArtProdutoDto();
		
		dto.setCodigo(model.getCodigo());
		dto.setDescricao(model.getDescricao());
		dto.setIngredienteAtivo(model.getIngredienteAtivo());
		dto.setConcentracao(model.getConcentracao());
		dto.setClasseUso(model.getClasseUso());
		dto.setClasseToxicologica(model.getClasseToxicologica());
		dto.setTitularCadastro(model.getTitularCadastro());
		dto.setCnpj(model.getCnpj());
		dto.setProcessoAdm(model.getProcessoAdm());
		
		return dto;
	}
	
	
	public List<ArtProduto> toListProdutoModel(List<ArtProdutoDto> listaDto){
		List<ArtProduto> lista = new ArrayList<ArtProduto>();
		
		for(ArtProdutoDto dto : listaDto){
			lista.add(toProdutoModel(dto));
		}
		
		return lista;
	}
	
	
	public ArtProduto toProdutoModel(ArtProdutoDto dto){
		ArtProduto produto = new ArtProduto();
		
		produto.setCodigo(dto.getCodigo());
				
		return produto;
		
	}
}
