package br.org.crea.art.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.art.ArtReceitaConverter;
import br.org.crea.commons.dao.art.ArtDomainDao;
import br.org.crea.commons.dao.art.ArtReceitaDao;
import br.org.crea.commons.dao.art.ArtReceitaProdutoDao;
import br.org.crea.commons.models.art.ArtReceita;
import br.org.crea.commons.models.art.ArtReceitaProduto;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.dtos.ArtProdutoDto;
import br.org.crea.commons.models.art.dtos.ArtQuantidadeReceitaDto;
import br.org.crea.commons.models.art.dtos.ArtReceitaDto;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.models.art.dtos.ReceitaProdutoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class ArtReceitaService {

	@Inject
	ArtReceitaConverter converter;

	@Inject
	ArtReceitaDao dao;

	@Inject
	ArtReceitaProdutoDao receitaProdutoDao;

	@Inject
	HttpClientGoApi httpGoApi;
	
	@Inject
	ArtDomainDao domainDao;
	
	@Inject 
	ContratoServicoService contratoService;

	public ArtReceitaDto salvaReceita(ArtReceitaDto dto) {

		try {

			ArtReceita receita = converter.toModel(dto);
			receita.setReceitaProdutos(montarListaProdutos(dto.getProdutos(), receita));
			
			ContratoServicoDto contratoDto = dto.getContrato();
			contratoDto.setNumeroArt(dto.getNumeroArt());
			ContratoArt contrato = contratoService.getModel(contratoDto);
			contrato.setReceita(receita);
			
			contrato = contratoService.salvaContrato(contrato);

		} catch (Exception e) {
			httpGoApi.geraLog(" ArtReceitaService || salvarReceita ", StringUtil.convertObjectToJson(dto), e);
		}

		return dto;
	}



	private List<ArtReceitaProduto> montarListaProdutos(List<ReceitaProdutoDto> produtos, ArtReceita receita) {

		List<ArtReceitaProduto> lista = new ArrayList<ArtReceitaProduto>();
		
		for (ReceitaProdutoDto dto : produtos) {

			ArtReceitaProduto receitaProduto = converter.toModelArtReceitaProduto(dto);
			receitaProduto.getId().setReceita(receita);

			lista.add(receitaProduto);
		}
		
		return lista;
	}
	

	public ArtReceitaDto recuperarReceitasEProdutos(Long idReceita) {
		return converter.toDto(dao.recuperarReceitaEProdutos(idReceita));
	}

	public List<ArtQuantidadeReceitaDto> getArtsDisponiveis(String registro) {
		return dao.getArtsDisponiveis(registro);
	}
	
	public List<ArtProdutoDto> getAllArtProdutos() {
		return converter.toListArtProdutoDto(domainDao.getAllArtProduto());
	}
}
