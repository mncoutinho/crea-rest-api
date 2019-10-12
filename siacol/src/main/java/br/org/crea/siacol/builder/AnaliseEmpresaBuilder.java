package br.org.crea.siacol.builder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.art.ArtConverter;
import br.org.crea.commons.converter.cadastro.empresa.EmpresaConverter;
import br.org.crea.commons.converter.cadastro.empresa.RequerimentoPJConverter;
import br.org.crea.commons.converter.cadastro.empresa.ResponsavelTecnicoConverter;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.dao.cadastro.empresa.RequerimentoPJDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.RequerimentoPJDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.ResponsavelTecnicoDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.siacol.dtos.AnaliseProtocoloSiacolDto;

public class AnaliseEmpresaBuilder {
	
	@Inject ProfissionalDao profissionalDao;
	
	@Inject EmpresaDao empresaDao;

	@Inject ArtDao artDao;
	
	@Inject PessoaDao pessoaDao;

	@Inject RequerimentoPJDao requerimentoDao;
	
	@Inject ResponsavelTecnicoConverter responsavelTecnicoConverter;
	
	@Inject ArtConverter artConverter;
	
	@Inject EmpresaConverter empresaConverter;
	
	@Inject RequerimentoPJConverter requerimentoConverter;
	
	@Inject ContratoArtDao contratoDao;
	
	private ProtocoloDto protocoloDto;
	
	private AnaliseProtocoloSiacolDto analiseDto;
	
	public AnaliseEmpresaBuilder constroiAnalise(ProtocoloDto dto) {
		
		protocoloDto = dto;
		analiseDto = new AnaliseProtocoloSiacolDto();
		
		getResponsaveisProtocoloComArtCargoFuncao().getInformacoesEmpresaEmAnalise();
		return this;
	}
	
	public AnaliseEmpresaBuilder getResponsaveisProtocoloComArtCargoFuncao() {
		List<RequerimentoPJDto> listRequerimentoProtocolo = requerimentoConverter.toListDto(requerimentoDao.buscaRequerimentosPor(protocoloDto.getNumeroProtocolo()));
		
		if(!listRequerimentoProtocolo.isEmpty()) {
			analiseDto.setListResponsaveisTecnicos(getResponsaveisTecnicosRequerimento(listRequerimentoProtocolo));
		} else {
			analiseDto.setListResponsaveisTecnicos(getResponsaveisTecnicosProtocolo(protocoloDto.getListRegistroRtsComArt()));
		}
		
		return this; 
	}
	
	public AnaliseEmpresaBuilder getInformacoesEmpresaEmAnalise() {
		
		Empresa empresa = empresaDao.getEmpresaPor(protocoloDto.getInteressado().getId());
		analiseDto.setEmpresa(empresaConverter.toDto(empresa));
		
		return this;
	}
	
	
	public List<ResponsavelTecnicoDto> getResponsaveisTecnicosRequerimento(List<RequerimentoPJDto> listDto) {
		List<ResponsavelTecnicoDto> listRts = new ArrayList<ResponsavelTecnicoDto>();
		
		for (RequerimentoPJDto dto : listDto) {
			listRts.add(dto.getResponsavel());
		}
		return listRts;
	}
	
	public List<ResponsavelTecnicoDto> getResponsaveisTecnicosProtocolo(List<GenericDto> listDto) {
		List<ResponsavelTecnicoDto> listRts = new ArrayList<ResponsavelTecnicoDto>();
		
		for (GenericDto rt : listDto) {
			
			ResponsavelTecnicoDto responsavel = responsavelTecnicoConverter.toDto(profissionalDao.buscaProfissionalPor(rt.getRegistro()));
			
			ArtDto artDto = artConverter.toDto(artDao.getArtPor(rt.getNumeroArt()));
			artDto.setDadosContratoAnalise(contratoDao.getContratoPrimeiroSequencialPor(artDto.getNumero()));
			
			responsavel.setArtCargoFuncao(artDto);
			responsavel.setVinculoEmpresas(empresaDao.getEmpresasOndeProfissionalEhResponsavelPor(Long.parseLong(rt.getRegistro())));
			
			listRts.add(responsavel);
		}
		return listRts;
	}
	
	public AnaliseProtocoloSiacolDto build() {
		return analiseDto; 
	}
}
