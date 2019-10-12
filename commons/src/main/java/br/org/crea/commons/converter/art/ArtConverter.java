package br.org.crea.commons.converter.art;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.cadastro.empresa.RazaoSocialDao;
import br.org.crea.commons.dao.cadastro.profissional.ProfissionalEspecialidadeDao;
import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.dtos.ArtDto;
import br.org.crea.commons.models.art.dtos.ArtMinDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.Profissional;
import br.org.crea.commons.util.DateUtils;

public class ArtConverter {

	@Inject ProfissionalEspecialidadeDao profissionalEspecialidadeDao;

	@Inject RazaoSocialDao razaoSocialDao;

	@Inject ArtDao artDao;
	
	@Inject FinDividaDao finDividaDao;
	
	@Inject ContratoArtDao contratoDao;
	
	@Inject ContratoArtConverter contratoConverter;
	
	public ArtDto toDto(Art model) {

		ArtDto dto = new ArtDto();
		dto.setNumero(model.getNumero());
		dto.setDescricaoFatoGerador(model.getDescricaoFatoGerador());
		dto.setCancelada(model.getCancelada());
		dto.setHaEmpresaVinculada(model.getHaEmpresaVinculada());
		dto.setHaProfissionalCoResponsavel(model.getHaProfissionalCoResponsavel());
		dto.setBaixada(model.getBaixada());
		dto.setMotivoBaixaOutros(model.getMotivoBaixaOutros());
		dto.setDataBaixa(model.getDataBaixa());
		dto.setDataCadastro(model.getDataCadastro());
		dto.setDataCadastroFormatada(model.getDataCadastro() != null ? DateUtils.format(model.getDataCadastro(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataPagamento(model.getDataPagamento());
		dto.setDataPagamentoFormatada(model.getDataPagamento() != null ? DateUtils.format(model.getDataPagamento(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataUltimaAlteracao(model.getDataUltimaAlteracao());
		dto.setAcessibilidade(model.getAcessibilidade());
		dto.setAssinaturaContratado(model.getAssinaturaContratado());
		dto.setFinalizada(model.getFinalizada());
		dto.setIsAcaoOrdinaria(model.getIsAcaoOrdinaria());
		dto.setIsTermoAditivo(model.getIsTermoAditivo());
		dto.setValorPago(model.getValorPago());
		dto.setValorReceber(model.getValorReceber());
		dto.setNumeroArtPrincipal(model.getNumeroARTPrincipal());
		dto.setUsuarioCadastro(model.getUsuarioCadastro());
		dto.setIsCertificada(model.getIsCertificada());
		dto.setQuantidadeContratos(artDao.getQuantidadeContratoBy(model.getNumero()));
		dto.setEstaQuitada(finDividaDao.dividaArtEstaQuitada(model.getNumero()));
		dto.setFuncionarioCadastro(model.getFuncionarioCadastro());
		dto.setFuncionarioAlteracao(model.getFuncionarioAlteracao());
		dto.setPossuiContratoCadastrado(contratoDao.getTotalDeContratosDaArt(model.getNumero()) > 0  ? true : false);
		dto.setExigencia(model.getExigencia());
		
		if (model.temBaixaArt()) {
	
			dto.setBaixaArt(populaBaixa(model));
		}
		
		if (model.temNaturezaArt()) {
			dto.setNatureza(populaNatureza(model));
		}

		if (model.temTipoArt()) {
			DomainGenericDto tipoArtDto = new DomainGenericDto();
			tipoArtDto.setId(model.getTipoArt().getId());
			tipoArtDto.setNome(model.getTipoArt().getDescricao());
			tipoArtDto.setNumero(model.getNumero());
			dto.setTipo(tipoArtDto);
		}

		if (model.temFatoGerador()) {
			DomainGenericDto fatoGeradorArtDto = new DomainGenericDto();
			fatoGeradorArtDto.setId(model.getFatoGeradorArt().getId());
			fatoGeradorArtDto.setNome(model.getFatoGeradorArt().getDescricao());
			fatoGeradorArtDto.setNumero(model.getNumero());
			dto.setFatoGerador(fatoGeradorArtDto);
		}

		if (model.temEntidadeClasse()) {
			DomainGenericDto entidadeClasseDto = new DomainGenericDto();
			entidadeClasseDto.setId(model.getEntidadeClasse().getId());
			entidadeClasseDto.setDescricao(model.getEntidadeClasse().getPessoaJuridica().getNomeFantasia());
			entidadeClasseDto.setSigla(model.getEntidadeClasse().getPessoaJuridica().getAbreviatura());
			entidadeClasseDto.setNumero(model.getNumero());
			dto.setEntidadeClasse(entidadeClasseDto);
		}
		
		if (model.temProfissional()) {
			
			Profissional profissional = model.getProfissional();
			PessoaDto profissionalDto = new PessoaDto();
			profissionalDto.setId(profissional.getId());
			profissionalDto.setCpf(profissional.getCpfCnpj());
			profissionalDto.setNome(profissional.getPessoaFisica().getNome());
			profissionalDto.setRegistro(profissional.getRegistro());
			profissionalDto.setNumeroRNP(profissional.getNumeroRNP());
			profissionalDto.setTitulo(profissionalEspecialidadeDao.getTituloProfissional(profissional));
			dto.setProfissional(profissionalDto);

		}
		
		if (model.temEmpresa()) {
			PessoaDto empresaDto = new PessoaDto();
			empresaDto.setId(model.getEmpresa().getId());
			empresaDto.setCnpj(model.getEmpresa().getCpfCnpj());
			empresaDto.setCnpj(model.getEmpresa().getRegistro());
			empresaDto.setNome(razaoSocialDao.buscaDescricaoRazaoSocial(model.getEmpresa().getId()));
			dto.setEmpresa(empresaDto);
		}
		
		if (model.temParticipacaoTecnica()) {
			DomainGenericDto participacaoTecnicaDto = new DomainGenericDto();
			participacaoTecnicaDto.setId(model.getParticipacaoTecnica().getId());
			participacaoTecnicaDto.setNome(model.getParticipacaoTecnica().getDescricao());
			participacaoTecnicaDto.setNumero(model.getNumero());
			dto.setParticipacaoTecnica(participacaoTecnicaDto);
		}
		
		dto.setModelo(model.getModelo());
		dto.setDescricaoModelo(model.getDescricaoModelo());
		
		dto.setContratoArt(contratoConverter.toDto(contratoDao.getPrimeiroContratoPor(model.getNumero())));
		
		dto.setPrimeiraParticipacaoTecnica(model.getPrimeiraParticipacaoTecnica());

		return dto;
	}
	
	public ArtDto toDtoFormulario(Art model) {

		ArtDto dto = new ArtDto();
		dto.setNumero(model.getNumero());
		dto.setDescricaoFatoGerador(model.getDescricaoFatoGerador());
		dto.setCancelada(model.getCancelada());
		dto.setHaEmpresaVinculada(model.getHaEmpresaVinculada());
		dto.setHaProfissionalCoResponsavel(model.getHaProfissionalCoResponsavel());
		dto.setBaixada(model.getBaixada());
		dto.setMotivoBaixaOutros(model.getMotivoBaixaOutros());
		dto.setDataBaixa(model.getDataBaixa());
		dto.setDataCadastro(model.getDataCadastro());
		dto.setDataCadastroFormatada(model.getDataCadastro() != null ? DateUtils.format(model.getDataCadastro(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataPagamento(model.getDataPagamento());
		dto.setDataPagamentoFormatada(model.getDataPagamento() != null ? DateUtils.format(model.getDataPagamento(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataUltimaAlteracao(model.getDataUltimaAlteracao());
		dto.setAcessibilidade(model.getAcessibilidade());
		dto.setAssinaturaContratado(model.getAssinaturaContratado());
		dto.setFinalizada(model.getFinalizada());
		dto.setIsAcaoOrdinaria(model.getIsAcaoOrdinaria());
		dto.setIsTermoAditivo(model.getIsTermoAditivo());
		dto.setValorPago(model.getValorPago());
		dto.setValorReceber(model.getValorReceber());
		dto.setNumeroArtPrincipal(model.getNumeroARTPrincipal());
		dto.setNumeroArtParticipacaoTecnica(model.getNumeroARTParticipacaoTecnica());
		dto.setUsuarioCadastro(model.getUsuarioCadastro());
		dto.setIsCertificada(model.getIsCertificada());
		dto.setQuantidadeContratos(artDao.getQuantidadeContratoBy(model.getNumero()));
		dto.setEstaQuitada(finDividaDao.dividaArtEstaQuitada(model.getNumero()));
		dto.setFuncionarioCadastro(model.getFuncionarioCadastro());
		dto.setFuncionarioAlteracao(model.getFuncionarioAlteracao());
		dto.setPossuiContratoCadastrado(contratoDao.getTotalDeContratosDaArt(model.getNumero()) > 0  ? true : false);
		
		if (model.temBaixaArt()) {
	
			dto.setBaixaArt(populaBaixa(model));
		}
		
		if (model.temNaturezaArt()) {
			dto.setNatureza(populaNatureza(model));
		}

		if (model.temTipoArt()) {
			DomainGenericDto tipoArtDto = new DomainGenericDto();
			tipoArtDto.setId(model.getTipoArt().getId());
			tipoArtDto.setNome(model.getTipoArt().getDescricao());
			tipoArtDto.setNumero(model.getNumero());
			dto.setTipo(tipoArtDto);
		}
		
		if (model.temParticipacaoTecnica()) {
			DomainGenericDto participacaoTecnicaDto = new DomainGenericDto();
			participacaoTecnicaDto.setId(model.getParticipacaoTecnica().getId());
			participacaoTecnicaDto.setNome(model.getParticipacaoTecnica().getDescricao());
			participacaoTecnicaDto.setNumero(model.getNumero());
			dto.setParticipacaoTecnica(participacaoTecnicaDto);
		}

		if (model.temFatoGerador()) {
			DomainGenericDto fatoGeradorArtDto = new DomainGenericDto();
			fatoGeradorArtDto.setId(model.getFatoGeradorArt().getId());
			fatoGeradorArtDto.setNome(model.getFatoGeradorArt().getDescricao());
			fatoGeradorArtDto.setNumero(model.getNumero());
			dto.setFatoGerador(fatoGeradorArtDto);
		}

		if (model.temEntidadeClasse()) {
			DomainGenericDto entidadeClasseDto = new DomainGenericDto();
			entidadeClasseDto.setId(model.getEntidadeClasse().getId());
			entidadeClasseDto.setSigla(model.getEntidadeClasse().getPessoaJuridica().getAbreviatura());
			entidadeClasseDto.setNumero(model.getNumero());
			dto.setEntidadeClasse(entidadeClasseDto);
		}
		
		if (model.temProfissional()) {
			
			Profissional profissional = model.getProfissional();
			PessoaDto profissionalDto = new PessoaDto();
			profissionalDto.setId(profissional.getId());
			profissionalDto.setCpf(profissional.getCpfCnpj());
			profissionalDto.setNome(profissional.getPessoaFisica().getNome());
			profissionalDto.setTitulo(profissionalEspecialidadeDao.getTituloProfissional(profissional));
			dto.setProfissional(profissionalDto);

		}
		
		if (model.temEmpresaInformada()) {
			PessoaDto empresaDto = new PessoaDto();
			empresaDto.setId(model.getEmpresa().getId());
			empresaDto.setCnpj(model.getEmpresa().getCpfCnpj());
			empresaDto.setNome(razaoSocialDao.buscaDescricaoRazaoSocial(model.getEmpresa().getId()));
			dto.setEmpresa(empresaDto);
		}
		
		dto.setModelo(model.getModelo());
		dto.setDescricaoModelo(model.getDescricaoModelo());
		dto.setPrimeiraParticipacaoTecnica(model.getPrimeiraParticipacaoTecnica());
		
		return dto;
	}

	private DomainGenericDto populaBaixa(Art model) {
		
		DomainGenericDto baixaArtDto = new DomainGenericDto();
		baixaArtDto.setId(model.getBaixaArt().getId());
		baixaArtDto.setNome(model.getBaixaArt().getDescricao());
		baixaArtDto.setNumero(model.getNumero());
		
		return baixaArtDto;
		
	}

	private DomainGenericDto populaNatureza(Art model) {
		
		DomainGenericDto naturezaArtDto = new DomainGenericDto();
		naturezaArtDto.setId(model.getNaturezaArt().getId());
		naturezaArtDto.setNome(model.getNaturezaArt().getDescricao());
		naturezaArtDto.setNumero(model.getNumero());
		return naturezaArtDto;
		
	}

	public List<ArtDto> toListDto(List<Art> listModel) {

		List<ArtDto> listDto = new ArrayList<ArtDto>();
		
		for(Art a : listModel){
			listDto.add(toDto(a));
		}
		
		return listDto;
	}

	public List<ArtMinDto> toListMinDto(List<Art> arts) {
		
		List<ArtMinDto> listDto = new ArrayList<ArtMinDto>();
		
		for(Art a : arts){
			listDto.add(toMinArtDto(a));
		}
		
		return listDto;
	}

	private ArtMinDto toMinArtDto(Art art) {
		
		ArtMinDto dto = new ArtMinDto();
		
		dto.setNumero(art.getNumero());
		dto.setDataCadastro(DateUtils.format(art.getDataCadastro(), DateUtils.DD_MM_YYYY_HH_MM));
		if(art.temNaturezaArt()){
			dto.setNatureza(art.getNaturezaArt().getDescricao());
		}
		if(art.temBaixaArt()){
			dto.setBaixa(art.getBaixaArt().getDescricao());
		}
		dto.setExigencia(art.getExigencia());
		
		return dto;
		
	}

}
