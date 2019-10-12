package br.org.crea.commons.converter.cadastro.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.art.ArtConverter;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.cadastro.RequerimentoPJ;
import br.org.crea.commons.models.cadastro.dtos.empresa.RequerimentoPJDto;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.util.DateUtils;

public class RequerimentoPJConverter {
	
	@Inject ProfissionalDao profissionalDao;
	
	@Inject ArtDao artDao;
	
	@Inject EmpresaDao empresaDao;
	
	@Inject InteressadoDao interessadoDao; 
	
	@Inject ResponsavelTecnicoConverter responsavelTecnicoConverter;
	
	@Inject ArtConverter artConverter;
	
	@Inject ContratoArtDao contratoDao;
	
	public RequerimentoPJDto toDto(RequerimentoPJ model) {
		RequerimentoPJDto dto = new RequerimentoPJDto();
		
		if( model != null ) {
			
			dto.setId(model.getId());
			dto.setDataSolicitacao(model.getDataCadastro());
			dto.setDataSolicitacaoFormatada(DateUtils.format(model.getDataCadastro(), DateUtils.DD_MM_YYYY));
			dto.setIdEmpresa(model.getEmpresaRequerente().getId());
			dto.setNumeroProtocolo(model.getProtocolo().getNumeroProtocolo());
			dto.setNumeroArt(model.getNumeroArt());
			
			Profissional profissional = profissionalDao.buscaProfissionalPor(model.getProfissionalResponsavel().getId().toString());
			dto.setResponsavel(responsavelTecnicoConverter.toDto(profissional));
			
			ArtDto artDto = artConverter.toDto(artDao.getArtPor(dto.getNumeroArt()));
			artDto.setDadosContratoAnalise(contratoDao.getContratoPrimeiroSequencialPor(artDto.getNumero()));
			dto.getResponsavel().setArtCargoFuncao(artDto);
			
			dto.getResponsavel().setVinculoEmpresas(empresaDao.getEmpresasOndeProfissionalEhResponsavelPor(dto.getResponsavel().getId()));
			return dto;
		}
		return null;
	}
	
	public List<RequerimentoPJDto> toListDto(List<RequerimentoPJ> listModel) {
		List<RequerimentoPJDto> listDto = new ArrayList<RequerimentoPJDto>();
		
		listModel.forEach(r -> listDto.add(toDto(r)));
		return listDto;
	}
	
	/**
	 * Retorna informações simplificadas do requerimento somente para atender a 
	 * substituição de texto dos pareceres e relatório e voto do Siacol.
	 * @param model
	 * @return dto
	 * */
	public RequerimentoPJDto toDtoTextoProtocoloEmpresa(RequerimentoPJ model) {
		RequerimentoPJDto dto = new RequerimentoPJDto();

		if( model != null ) {
			
			dto.setIdEmpresa(model.getEmpresaRequerente().getId());
			dto.setRazaoSocialEmpresa(model.getEmpresaRequerente() != null ? interessadoDao.buscaDescricaoRazaoSocial(dto.getIdEmpresa()) : "");
			dto.setNumeroProtocolo(model.getProtocolo().getNumeroProtocolo());
			dto.setNumeroArt(model.getNumeroArt());
			
			Profissional profissional = profissionalDao.buscaProfissionalPor(model.getProfissionalResponsavel().getId().toString());
			dto.setResponsavel(responsavelTecnicoConverter.toResponsavelTecnicoRequerimentoDto(profissional));
			return dto;
		}
		return null;
	}

}
