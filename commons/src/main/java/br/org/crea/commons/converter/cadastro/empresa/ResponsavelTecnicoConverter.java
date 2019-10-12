
package br.org.crea.commons.converter.cadastro.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.converter.cadastro.pessoa.SituacaoPessoaConverter;
import br.org.crea.commons.converter.cadastro.pessoa.TelefoneConverter;
import br.org.crea.commons.converter.cadastro.profissional.TituloProfissionalConverter;
import br.org.crea.commons.converter.financeiro.DividaConverter;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.pessoa.TelefoneDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalEspecialidadeDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.models.cadastro.ResponsavelTecnico;
import br.org.crea.commons.models.cadastro.dtos.empresa.ResponsavelTecnicoDto;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.Telefone;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.util.DateUtils;

public class ResponsavelTecnicoConverter {
	
	@Inject InteressadoDao interessadoDao;
	
	@Inject ProfissionalEspecialidadeDao profissionalEspecialidadeDao;
	
	@Inject EnderecoDao enderecoDao;
	
	@Inject TelefoneDao telefoneDao;
	
	@Inject FinDividaDao finDividaDao;
	
	@Inject PessoaConverter pessoaConverter;
	
	@Inject SituacaoPessoaConverter situacaoConverter;
	
	@Inject TituloProfissionalConverter tituloConverter;
	
	@Inject EnderecoConverter enderecoConverter;
	
	@Inject TelefoneConverter telefoneConverter;
	
	@Inject DividaConverter dividaConverter;
	
	@Inject ProfissionalDao profissionalDao;

	public List<ResponsavelTecnicoDto> toListDto(List<ResponsavelTecnico> listModel){
		
		List<ResponsavelTecnicoDto> listDto = new ArrayList<ResponsavelTecnicoDto>();
		
		for(ResponsavelTecnico q : listModel){
			listDto.add(toDto(q));
		}
		return listDto;
	}
	
	public ResponsavelTecnicoDto toDto(ResponsavelTecnico model) {
		
		ResponsavelTecnicoDto dto = new ResponsavelTecnicoDto();
		
		dto.setId(model.getId());		
		dto.setNomeEmpresa(interessadoDao.buscaDescricaoRazaoSocial(model.getQuadro().getEmpresa().getId()));
		dto.setNomeProfissional(model.getQuadro().getProfissional().getNome());
		dto.setRegistro(model.getQuadro().getProfissional().getRegistro());
		dto.setAtividade(model.getRamoAtividade().getAtividade().getDescricao()) ;	
		dto.setRamo(model.getRamoAtividade().getRamo().getDescricao());
		dto.setDataInicioRT(DateUtils.format(model.getDataInicio(), DateUtils.DD_MM_YYYY));
		dto.setDataFimRT(DateUtils.format(model.getDataFim(), DateUtils.DD_MM_YYYY));
		dto.setAnuidade(dividaConverter.toDto(finDividaDao.getUltimaAnuidadePagaPor(model.getQuadro().getProfissional().getId())));
		dto.setDataInicioQuadroTecnico(DateUtils.format(model.getQuadro().getDataInicio(), DateUtils.DD_MM_YYYY));
		dto.setTitulos(tituloConverter.toListTituloProfissionalDto(model.getQuadro().getProfissional()));
		
		return dto;
	}
	
	public ResponsavelTecnicoDto toDto(Profissional model) {
		ResponsavelTecnicoDto dto = new ResponsavelTecnicoDto();
		
		dto.setId(model.getId());
		
		dto.setNumeroRnp(model.getNumeroRNP());
		dto.setRegistro(model.getRegistro());
		dto.setNomeProfissional(model.getNome());
		dto.setCpf(model.getCpfCnpj());
		dto.setSituacao(situacaoConverter.toDto(model.getSituacao()));
		dto.setTitulos(tituloConverter.toListTituloProfissionalDto(model));
		dto.setAnuidade(dividaConverter.toDividaAnuidadeDto(model.getId()));
		dto.setDataVisto(model.getDataVisto() != null ? model.getDataVisto() : null);
		dto.setDataVistoFormatada(dto.getDataVisto() != null ? DateUtils.format(model.getDataVisto(), DateUtils.DD_MM_YYYY) : "");
		dto.setAnotacoesEspeciais(model.getAnotacoesEspeciais() == null ? "Não Possui" : model.getAnotacoesEspeciais());
		dto.setRessalvas(model.getObservacoesEspeciais() == null ? "Não Possui" : model.getObservacoesEspeciais());
		
		if(profissionalDao.possuiVistoOutosCreas(model.getId())){
			dto.setVistosRegionais(profissionalDao.getListVistosOutrosCreas(model.getId()));
		}
		
		Endereco enderecoResidencial = enderecoDao.getEnderecoResidencialPessoaPor(model.getPessoaFisica().getId());
		dto.setEnderecoResidencial(enderecoConverter.toDto(enderecoResidencial));
		
		List<Endereco> enderecosValidos = enderecoDao.getListEnderecosValidosPor(model.getPessoaFisica().getId());
		dto.setEnderecosValidos(enderecoConverter.toListDto(enderecosValidos));
		
		List<Telefone> listTelefones = telefoneDao.getListTelefoneByPessoa(model.getPessoaFisica().getId());
		dto.setListTelefones(telefoneConverter.toListDto(listTelefones));
		
		return dto;
	}
	
	/**
	 * Retorna um um responsavelTecnicoDto "simples", com informações
	 * sucintas somente para substituição de texto de pareceres ou sugestão 
	 * de relatório e voto do Siacol.
	 * @param model
	 * @return dto 
	 * */
	public ResponsavelTecnicoDto toResponsavelTecnicoRequerimentoDto(Profissional model) {
		ResponsavelTecnicoDto dto = new ResponsavelTecnicoDto();
		
		dto.setId(model.getId());
		dto.setNumeroRnp(model.getNumeroRNP());
		dto.setRegistro(model.getRegistro());
		dto.setNomeProfissional(model.getNome());
		dto.setCpf(model.getCpfCnpj());
		dto.setTitulos(tituloConverter.toListTituloProfissionalDto(model));
		dto.setAnotacoesEspeciais(model.getAnotacoesEspeciais() == null ? "Não Possui" : model.getAnotacoesEspeciais());
		dto.setRessalvas(model.getObservacoesEspeciais() == null ? "Não Possui" : model.getObservacoesEspeciais());
		return dto;
	}

}
