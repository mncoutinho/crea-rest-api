package br.org.crea.art.service;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.art.ContratoServicoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.enuns.TipoEnderecoEnum;

public class ContratoServicoService {

	@Inject
	ContratoServicoConverter converter;

	@Inject
	ContratoArtDao dao;

	@Inject
	InteressadoDao interessadoDao;

	@Inject
	EnderecoConverter enderecoConverter;
	
	


	public ContratoArt salvar(ContratoArt contrato) {
		return dao.salvar(contrato);
	}

	public Long getUltimoSequencialContrato(String numeroArt) {
		return dao.getUltimoSequencialContrato(numeroArt);
	}

	public List<ContratoServicoDto> getContratoReceitaPor(String numeroArt){
		return converter.toListServicoDto(dao.getContratoReceitaPor(numeroArt));
	}

	public ContratoArt getModel(ContratoServicoDto contratoDto) {

		Long ultimoSequencialContrato = dao.getUltimoSequencialContrato(contratoDto.getNumeroArt());

		contratoDto.setSequencial(ultimoSequencialContrato);

		ContratoArt contrato = converter.toServicoModel(contratoDto);

		contrato.setNomeContratante(interessadoDao.getNomeContratanteBy(contratoDto.getIdPessoaContratante()));
		
		preparaEnderecosContrato(contratoDto, contrato);
				
		return contrato;
	} 
	
	private void preparaEnderecosContrato(ContratoServicoDto contratoDto, ContratoArt contrato){
		Endereco enderecoContratante = enderecoConverter.toModel(contratoDto.getEnderecoContratante());
		enderecoContratante.setValido(true);
		enderecoContratante.setPostal(false);
		enderecoContratante.setTipoEndereco(TipoEnderecoEnum.getTipoEndereco(TipoEnderecoEnum.CONTRATANTE));
		
		contrato.setEnderecoContratante(enderecoContratante);
		
		Endereco enderecoContrato = enderecoConverter.toModel(contratoDto.getEnderecoContrato());
		enderecoContrato.setValido(true);
		enderecoContrato.setPostal(false);
		enderecoContrato.setTipoEndereco(TipoEnderecoEnum.getTipoEndereco(TipoEnderecoEnum.OBRASERVICO));
		
		contrato.setEndereco(enderecoContrato);
	}
	
	public ContratoArt salvaContrato(ContratoArt contrato) {

		return dao.salvar(contrato);
	}
}
