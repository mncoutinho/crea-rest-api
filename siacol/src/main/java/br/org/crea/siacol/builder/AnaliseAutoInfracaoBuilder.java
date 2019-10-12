package br.org.crea.siacol.builder;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.empresa.EmpresaConverter;
import br.org.crea.commons.converter.cadastro.empresa.ResponsavelTecnicoConverter;
import br.org.crea.commons.converter.financeiro.DividaConverter;
import br.org.crea.commons.converter.protocolo.ProtocoloConverter;
import br.org.crea.commons.dao.cadastro.empresa.EmpresaDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.dao.fiscalizacao.FiscalizacaoDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.commons.dtos.ProtocoloDto;
import br.org.crea.commons.models.corporativo.pessoa.Empresa;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.models.siacol.dtos.AnaliseProtocoloSiacolDto;


public class AnaliseAutoInfracaoBuilder {
	
	@Inject FiscalizacaoDao fiscalizacaoDao;
	
	@Inject ProtocoloDao protocoloDao;
	
	@Inject ProfissionalDao profissionalDao;
	
	@Inject EmpresaDao empresaDao;
	
	@Inject FinDividaDao dividaDao;
	
	@Inject DividaConverter dividaConverter;
	
	@Inject ProtocoloConverter protocoloConverter;
	
	@Inject EmpresaConverter empresaConverter;
	
	@Inject ResponsavelTecnicoConverter responsavelTecnicoConverter;
	
	private ProtocoloDto protocoloDto;

	private AnaliseProtocoloSiacolDto analiseDto;
	
	public AnaliseAutoInfracaoBuilder constroiAnalise(ProtocoloDto dto) {
		
		protocoloDto = dto;
		analiseDto = new AnaliseProtocoloSiacolDto();
		
		getAutoInfracaoEmAnalise().getAutosNaMesmaCapitulacao().
		getProtocolosProcessoInfracao().getDividaInfracao().getInformacoesInfrator();
		return this;
		
	}
	
	public AnaliseAutoInfracaoBuilder getAutoInfracaoEmAnalise() {
		
		analiseDto.setAutoInfracao(fiscalizacaoDao.getAutoInfracaoPor(protocoloDto.getNumeroProtocolo()));
		analiseDto.getAutoInfracao().setNumeroDoAuto(protocoloDto.getNumeroProtocolo().toString());
		return this;
	}
	
	public AnaliseAutoInfracaoBuilder getAutosNaMesmaCapitulacao() {
		
		analiseDto.getAutoInfracao().setAutosMesmaCapitulacao(
				   fiscalizacaoDao.getListAutoInfracaoMesmaCapitulacaoPor(protocoloDto.getNumeroProtocolo()));
		
		return this;
	}

	public AnaliseAutoInfracaoBuilder getEmpresaAutuada() {
		
		Empresa empresa = empresaDao.getEmpresaPor(protocoloDto.getInteressado().getId());
		analiseDto.getAutoInfracao().setEmpresaAutuada(empresaConverter.toDto(empresa)); 
		
		return this;
	}
	
	public AnaliseAutoInfracaoBuilder getProfissionalAutuado() {
		
        Profissional profissional = profissionalDao.buscaProfissionalPor(protocoloDto.getInteressado().getId().toString());
        analiseDto.getAutoInfracao().setProfissionalAutuado(responsavelTecnicoConverter.toDto(profissional));
		
		return this;
	}
	
	public AnaliseAutoInfracaoBuilder getInformacoesInfrator() {
		
		if(pessoaAutuadaEhProfissional()) {
			
			getProfissionalAutuado();
			
		} else if (pessoaAutuadaEhEmpresa()) {
			
			getEmpresaAutuada();
		}
		return this;
	}
	
	public AnaliseAutoInfracaoBuilder getProtocolosProcessoInfracao() {
		
		List<ProtocoloDto> listProtocolos = protocoloConverter.toListDto(protocoloDao.getProtocoloByProcesso(protocoloDto.getNumeroProcesso()));
		analiseDto.getAutoInfracao().setProtocolosVinculadosProcessoAutoInfracao(listProtocolos);
		return this;
	}
	
	public AnaliseAutoInfracaoBuilder getDividaInfracao() {
	
		analiseDto.getAutoInfracao().setDividaInfracao(dividaConverter.toDto(dividaDao.getDividaPor(protocoloDto.getNumeroProtocolo().toString())));
		return this;
	}
	
	public AnaliseProtocoloSiacolDto build() {
		return analiseDto; 
	}
	
	public boolean pessoaAutuadaEhProfissional() {
		return String.valueOf(protocoloDto.getInteressado().getId()).charAt(4) == '1' ? true : false;
	}
	
	public boolean pessoaAutuadaEhEmpresa() {
		return String.valueOf(protocoloDto.getInteressado().getId()).charAt(4) == '2' ? true : false;
	}
	
}
