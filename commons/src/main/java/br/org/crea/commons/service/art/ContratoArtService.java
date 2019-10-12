package br.org.crea.commons.service.art;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.art.ContratoArtConverter;
import br.org.crea.commons.converter.cadastro.pessoa.EnderecoConverter;
import br.org.crea.commons.dao.art.ArtDao;
import br.org.crea.commons.dao.art.ArtQuantificacaoDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.cadastro.pessoa.EnderecoDao;
import br.org.crea.commons.models.art.Art;
import br.org.crea.commons.models.art.ArtQuantificacao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.dtos.ContratoArtDto;
import br.org.crea.commons.models.art.dtos.ContratoServicoDto;
import br.org.crea.commons.models.cadastro.UnidadeMedida;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.PesquisaGenericDto;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.LeigoDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.service.PessoaService;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.EnderecoUtil;

public class ContratoArtService {
	
	
	@Inject
	private ContratoArtDao dao;
	
	@Inject 
	private ContratoArtConverter converter;
	
	@Inject 
	private ArtQuantificacaoDao artQuantificacaoDao;
	
	@Inject
	private ArtDao artDao;
	
	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private EnderecoDao enderecoDao;
	
	@Inject
	private EnderecoConverter enderecoConverter;
	
	
	public List<ContratoArtDto> getContratosPor(PesquisaGenericDto pesquisa) {
		return converter.toListMinDto(dao.getContratos(pesquisa));
	}

	public int getTotalDeRegistrosDaPesquisa(PesquisaGenericDto pesquisa) {
		return dao.getTotalDeRegistrosDaPesquisa(pesquisa);
	}

	public void excluiContrato(String idContrato, UserFrontDto userDto) {
		
		String[] ids = idContrato.split("-");
		
		String numeroArt = ids[0];
		
		Long sequencial = Long.parseLong(ids[1]);
		
		dao.excluiContrato(idContrato);
		
		List<ContratoArt> contratosAReordenar =  dao.getContratosByArtAPartirDoSequencial(numeroArt, sequencial);
		
		for (ContratoArt contratoArt : contratosAReordenar) {
			
			List<Long> listaAtividades = dao.getListaDeCodigosDasAtividadesDoContratoPor(contratoArt.getId());
			List<Long> listaEspecificacoes = dao.getListaDeCodigosDasEspecificacoesDoContratoPor(contratoArt.getId());
			List<Long> listaComplementos = dao.getListaDeCodigosDosComplementosDoContratoPor(contratoArt.getId());
			
			dao.excluiAtividadeDoContrato(contratoArt.getId());
			dao.excluiEspecificacoesDoContrato(contratoArt.getId());
			dao.excluiComplementosDoContrato(contratoArt.getId());
			
			String novoCodigoDoContrato = numeroArt+"-"+sequencial;
			
			ArtQuantificacao artQuantificacao = artQuantificacaoDao.getByIdContrato(contratoArt.getId());
			
			if(artQuantificacao != null) {
				artQuantificacao.setContrato(null);
				artQuantificacaoDao.update(artQuantificacao);
			}
			
			dao.atualizaCodigoESequencial(contratoArt.getId(), novoCodigoDoContrato, sequencial);
			
			listaAtividades.forEach(atividade -> dao.atualizaAtividadePor(atividade, novoCodigoDoContrato));
			listaEspecificacoes.forEach(especificacao -> dao.atualizaEspecificacaoPor(especificacao, novoCodigoDoContrato));
			listaComplementos.forEach(complemento->dao.atualizaComplementoPor(complemento, novoCodigoDoContrato));
			
			if(artQuantificacao != null) {
				ContratoArt novoContratoArt = dao.getByIdString(novoCodigoDoContrato);
				artQuantificacao.setContrato(novoContratoArt);
				artQuantificacaoDao.update(artQuantificacao);
			}
			
			sequencial++;
		}
		
	}


	public ContratoArtDto getDetalhado(String idContrato) {
		ContratoArtDto contrato = new ContratoArtDto();
		contrato = converter.toDto(dao.getContratoPor(idContrato));
		return contrato;

	}
	
	public List<ContratoServicoDto> listaContratosPorArt(PesquisaGenericDto pesquisa) {
		return dao.getContratosPor(pesquisa);
	}

	public ContratoArtDto novoContrato(DomainGenericDto dto) {
		Art art = artDao.getBy(dto.getNumero());
		
		Long sequencial = dao.getUltimoSequencialContrato(art.getNumero());
		
		ContratoArt contrato = new ContratoArt();
		
		Long sequencialIncrementado = sequencial + 1;
		String sequencialString = "-" + (sequencialIncrementado);
		
		contrato.setId(art.getNumero() + sequencialString);
		contrato.setArt(art);
		contrato.setSequencial(sequencialIncrementado);
		contrato.setDataCadastro(new Date());
		contrato.setFuncionarioCadastro(new Long(99990));
		contrato.setBaixaArt(art.getBaixaArt());
		
		if(sequencialIncrementado.longValue() > 1) replicarDeclaracoes(contrato, art.getNumero());
		
		contrato = dao.create(contrato);
		
		if(sequencialIncrementado.longValue() > 1) replicarAtividadesEspecificacoesEComplementosDoPrimeiroContrato(art.getNumero(), contrato.getId());
		
			
		return converter.toDto(contrato);
	}

	private void replicarAtividadesEspecificacoesEComplementosDoPrimeiroContrato(String numeroArt, String numeroContrato) {
		List<Long> listaAtividades = dao.getListaDeCodigosDasAtividadesDoContratoPor(numeroArt+"-"+1);
		if(listaAtividades != null) listaAtividades.forEach(atividade->dao.atualizaAtividadePor(atividade,numeroContrato));
		
		List<Long> listaEspecificacoes = dao.getListaDeCodigosDasEspecificacoesDoContratoPor(numeroArt+"-"+1);
		if(listaEspecificacoes != null) listaEspecificacoes.forEach(especificacao->dao.atualizaEspecificacaoPor(especificacao,numeroContrato));
		
		List<Long> listaComplementos = dao.getListaDeCodigosDosComplementosDoContratoPor(numeroArt+"-"+1);
		if(listaComplementos != null) listaComplementos.forEach(complemento->dao.atualizaComplementoPor(complemento,numeroContrato));
		
	}

	private void replicarDeclaracoes(ContratoArt contrato, String numeroArt) {
		ContratoArt primeiroContrato = dao.getContratoPor(numeroArt+"-"+1);
		
		contrato.setAcessibilidade(primeiroContrato.getAcessibilidade());;
		contrato.setArbitragem(primeiroContrato.getArbitragem());
	}

	public boolean atualizaNumeroContrato(DomainGenericDto dto) {
		return dao.atualizaNumeroContrato(dto);
	}

	public boolean atualizaSalario(DomainGenericDto dto) {
		return dao.atualizaSalario(dto);
	}
	
	public DomainGenericDto atualizaProLabore(DomainGenericDto dto) {
		dao.atualizaProLabore(dto);
		return dto;
	}

	public boolean atualizaValorContrato(DomainGenericDto dto) {
		return dao.atualizaValorContrato(dto);
	}

	public DomainGenericDto atualizaNhhjt(DomainGenericDto dto) {
		dao.atualizaNhhjt(dto);
		return dto;
	}
	
	public boolean atualizaDataInicio(DomainGenericDto dto) {
		return dao.atualizaDataInicio(dto);
	}

	public boolean atualizaDataFim(DomainGenericDto dto) {
		if (dto.getDescricao() == null) {
			dao.atualizaPrazoDia(dto);
		}
		
		return dao.atualizaDataFim(dto);
	}
	
	public DomainGenericDto atualizaPrazoDeterminado(DomainGenericDto dto) {
		if(!dto.isChecked()) {
			dao.atualizaPrazoDia(dto);
			dao.atualizaPrazoMes(dto);
			dao.atualizaDataFim(dto);
		}
		dao.atualizaPrazoDeterminado(dto);
		return dto;
	}
	
	public boolean atualizaPrazoMes(DomainGenericDto dto) {
		return dao.atualizaPrazoMes(dto);
	}

	public boolean atualizaPrazoDia(DomainGenericDto dto) {
		return dao.atualizaPrazoDia(dto);
	}
	
	public DomainGenericDto atualizaRamo(DomainGenericDto dto) {
		dao.atualizaRamo(dto);
		return dto;
	}
	
	public DomainGenericDto atualizaAtividade(DomainGenericDto dto) {
		List<String> idsContrato =  dao.getListaCodigosContratosByArt(dto.getNumero());
		idsContrato.forEach(idContrato -> dao.atualizaAtividade(dto, idContrato));
		return dto;
	}

	public DomainGenericDto atualizaEspecificacao(DomainGenericDto dto) {
		List<String> idsContrato =  dao.getListaCodigosContratosByArt(dto.getNumero());
		idsContrato.forEach(idContrato -> dao.atualizaEspecificacao(dto, idContrato));
		return dto;
	}

	public DomainGenericDto atualizaComplemento(DomainGenericDto dto) {
		List<String> idsContrato =  dao.getListaCodigosContratosByArt(dto.getNumero());
		idsContrato.forEach(idContrato -> dao.atualizaComplemento(dto, idContrato));
		return dto;
	}
	
	public DomainGenericDto atualizaQuantificacao(DomainGenericDto dto) {
		ArtQuantificacao quantificacao = artQuantificacaoDao.getByIdContrato(dto.getNumero());
		
		if (quantificacao != null) {
			quantificacao.setValor(dto.getValor());
			artQuantificacaoDao.update(quantificacao);
			
		} else {
			
			quantificacao = new ArtQuantificacao();
			quantificacao.setValor(dto.getValor());
			
			ContratoArt contrato = new ContratoArt();
			contrato.setId(dto.getNumero());
			quantificacao.setContrato(contrato);
			
			artQuantificacaoDao.create(quantificacao);
		}		
		
		return dto;
	}
	
	public DomainGenericDto atualizaUnidadeDeMedida(DomainGenericDto dto) {
		ArtQuantificacao quantificacao = artQuantificacaoDao.getByIdContrato(dto.getNumero());
		UnidadeMedida unidade = new UnidadeMedida();
		
		if (quantificacao != null) {
			unidade.setCodigo(dto.getCodigo());
			quantificacao.setUnidadeMedida(unidade);
			artQuantificacaoDao.update(quantificacao);
			
		} else {
			quantificacao = new ArtQuantificacao();
			unidade.setCodigo(dto.getCodigo());
			quantificacao.setUnidadeMedida(unidade);

			ContratoArt contrato = new ContratoArt();
			contrato.setId(dto.getNumero());
			quantificacao.setContrato(contrato);
			
			artQuantificacaoDao.create(quantificacao);
		}		
		
		return dto;
	}
	
	public boolean atualizaNumeroPavimentos(DomainGenericDto dto) {
		return dao.atualizaNumeroPavimentos(dto);
	}

	public DomainGenericDto atualizaConvenioPublico(DomainGenericDto dto) {
		dao.atualizaConvenioPublico(dto);
		return dto;
	}
	
	public boolean atualizaDescricaoComplementar(DomainGenericDto dto) {
		return dao.atualizaDescricaoComplementar(dto);
	}

	public DomainGenericDto atualizaAcessibilidade(DomainGenericDto dto) {
		dao.atualizaAcessibilidade(dto);
		artDao.atualizaAcessibilidade(dto);
		return dto;
	}

	public void deletaAtividade(String numeroArt, String idAtividade) {
		dao.deletaAtividade(numeroArt, idAtividade);
	}

	public void deletaEspecificacao(String numeroArt, String idEspecificacao) {
		dao.deletaEspecificacao(numeroArt, idEspecificacao);
	}

	public void deletaComplemento(String numeroArt, String idComplemento) {
		dao.deletaComplemento(numeroArt, idComplemento);
	}

	public void atualizaTipoUnidadeAdministrativa(DomainGenericDto dto) {
		dao.atualizaTipoUnidadeAdministrativa(dto);
	}

	public void atualizaTipoAcaoInstitucional(DomainGenericDto dto) {
		dao.atualizaTipoAcaoInstitucional(dto);
	}

	public void atualizaTipoCargoFuncao(DomainGenericDto dto) {
		dao.atualizaTipoCargoFuncao(dto);
	}

	public void atualizaTipoFuncao(DomainGenericDto dto) {
		dao.atualizaTipoFuncao(dto);
	}
	
	public boolean atualizaDescricaoCargoFuncao(DomainGenericDto dto) {
		return dao.atualizaDescricaoCargoFuncao(dto);		
	}

	public void atualizaTipoVinculo(DomainGenericDto dto) {
		dao.atualizaTipoVinculo(dto);
	}

	public boolean atualizaDataCelebracao(DomainGenericDto dto) {
		return dao.atualizaDataCelebracao(dto);
	}

	public void atualizaTipoContratante(DomainGenericDto dto) {
		dao.atualizaTipoContratante(dto);
	}

	public void atualizaFinalidade(DomainGenericDto dto) {
		dao.atualizaFinalidade(dto);	
	}

	public void atualizaCodigoObraServico(DomainGenericDto dto) {
		dao.atualizaCodigoObraServico(dto);
	}

	public void atualizaArbitragem(DomainGenericDto dto) {
		dao.atualizaArbitragem(dto);		
	}

	public void atualizaContratante(DomainGenericDto dto) {
		
		if (!dto.isChecked()) {
			LeigoDto leigoDto = pessoaService.cadastrarLeigo(populaLeigoDto(dto));
			dto.setCodigo(leigoDto.getId());
		}			
		
		dao.atualizaContratante(dto);
	}


	private LeigoDto populaLeigoDto(DomainGenericDto dto) {
		LeigoDto leigoDto = new LeigoDto();
		
		if (dto.ehCpf()) {
			leigoDto.setTipoPessoa(TipoPessoa.LEIGOPF);
		} else {
			leigoDto.setTipoPessoa(TipoPessoa.LEIGOPJ);
		}
		
		leigoDto.setCpfOuCnpj(dto.getCpfOuCnpj());
		leigoDto.setNome(dto.getNome());
		
		
		return leigoDto;
	}


	public void atualizaProprietario(DomainGenericDto dto) {

		if (!dto.isChecked()) {
			LeigoDto leigoDto = pessoaService.cadastrarLeigo(populaLeigoDto(dto));
			dto.setCodigo(leigoDto.getId());
		}			
		
		dao.atualizaProprietario(dto);
		
	}


	public void atualizaEnderecoContrato(DomainGenericDto dto) {
		dao.atualizaEnderecoContrato(dto);
	}


	public void atualizaEnderecoContratante(DomainGenericDto dto) {
		dao.atualizaEnderecoContratante(dto);
	}

	/**
	 * Metodo responsável em calcular e definir a data fim para o contrato a partir da data de inicio mais o prazo mês e/ou dia 
	 * informado no cadastro do contrato.
	 * @param dto - com o número do contrato no campo numero
	 * @return boolean
	 */
	public boolean calculaEAtualizaDataFim(DomainGenericDto dto) {
		ContratoArt contratoArt = dao.getContratoPor(dto.getNumero());
		
		if (contratoArt.temDataFim()) {
			dto.setDescricao(String.valueOf(DateUtils.getDiferencaDiasEntreDatas(contratoArt.getDataFim(), contratoArt.getDataInicio())));
			dao.atualizaPrazoDia(dto);
			return true;
		}
		
		if (contratoArt.artEhDesempenhoDeCargoEFuncao()) {
			if(contratoArt.temPrazoIndeterminado()) {
				dao.limpaDataFimPrazoMesEDia(dto);
				return false;
			}
		}		
		
		if(contratoArt.temPrazoMes() && contratoArt.temPrazoDia()) {
			Date dataFim  = DateUtils.adicionaOrSubtraiDiasA(contratoArt.getDataInicio(), contratoArt.getPrazoDia().intValue());
			dataFim = DateUtils.adicionaOrSubtraiMesesA(dataFim, contratoArt.getPrazoMes().intValue());
			contratoArt.setDataFim(dataFim);
			dao.atualizaDataFim(contratoArt);
			return true;
		} 
		if(contratoArt.temPrazoMes()) {
			Date dataFim = DateUtils.adicionaOrSubtraiMesesA(contratoArt.getDataInicio(), contratoArt.getPrazoMes().intValue());
			contratoArt.setDataFim(dataFim);
			dao.atualizaDataFim(contratoArt);
			return true;
		} 
		if(contratoArt.temPrazoDia()) {
			Date dataFim = DateUtils.adicionaOrSubtraiDiasA(contratoArt.getDataInicio(), contratoArt.getPrazoDia().intValue());
			contratoArt.setDataFim(dataFim);
			dao.atualizaDataFim(contratoArt);
			return true;
		}
		return false;
	}


	public int getTotalDeRegistrosDaPesquisaGetAll(PesquisaGenericDto pesquisa) {
		return dao.getTotalDeRegistrosDaPesquisaGetAll(pesquisa);
	}


	public void atualizaEnderecoProprietario(DomainGenericDto dto) {
		dao.atualizaEnderecoProprietario(dto);
	}
	
	public DomainGenericDto setNumeroArtVinculadaAoContrato(DomainGenericDto dto) {
		dao.atualizaNumeroArtVinculadaAoContrato(dto);
		return dto;
	}
	
	public boolean numeroArtVinculadaAoContratoEhValido (DomainGenericDto dto) {
		if(dao.numeroArtVinculadaAoContratoEhValido(dto)) {
			if(enderecoDoContratoEhOMesmo(dto.getNumero(), dto.getNome()+"-1")) return true;
		}
		return false;
	}
	private Boolean enderecoDoContratoEhOMesmo(String numerocontratoArtprimario, String numerocontratoArtSecundario) {
		ContratoArt contratoArtPrimario = dao.getContratoPor(numerocontratoArtprimario);
		ContratoArt contratoArtSecundario = dao.getContratoPor(numerocontratoArtSecundario);		
		if(contratoArtPrimario.temEndereco() && contratoArtSecundario.temEndereco()) {
			Endereco enderecoArt = contratoArtPrimario.getEndereco().getClone();
			Endereco enderecoArtPrincipal = contratoArtSecundario.getEndereco().getClone();	
			return EnderecoUtil.enderecosSaoIguais(EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArt), EnderecoUtil.removerAcentosEespacoDoEndereco(enderecoArtPrincipal));
		} 
		return false;
	}

	public ContratoArtDto carregarDadosDaArtPrincipalComplementarParaOContratoAtual(DomainGenericDto dto) {
		
		ContratoArt contrato = dao.getPrimeiroContratoPor(dto.getNumero());
		
		if(contrato == null) {
			contrato = dao.getContratoPor(novoContrato(dto).getId());
		}
		
		ContratoArt contratoArtPrincipal = dao.getPrimeiroContratoPor(dto.getNome());
		
		//Copiando a contratante
		if(contratoArtPrincipal.temPessoa()) {
			contrato.setPessoa(contratoArtPrincipal.getPessoa());
			contrato.setNomeContratante(contratoArtPrincipal.getNomeContratante());
		}
		
		//Copiando endereco da contratante
		if(contratoArtPrincipal.temEnderecoContratante()) {
			Endereco enderecoContratante = new Endereco();
			enderecoContratante = contratoArtPrincipal.getEnderecoContratante().getClone();
			enderecoContratante.setId(null);
			enderecoDao.create(enderecoContratante);
			contrato.setEnderecoContratante(enderecoContratante);
		}
		
		//Copiando endereco da obra ou servico
		if(contratoArtPrincipal.temEndereco()) {
			Endereco endereco = new Endereco();
			endereco = contratoArtPrincipal.getEndereco().getClone();
			endereco.setId(null);
			enderecoDao.create(endereco);
			contrato.setEndereco(endereco);
		}
		
		dao.update(contrato);
		
		ContratoArtDto contratoArtDto  = converter.toDto(contrato);
		contratoArtDto.setNome(dto.getNome());
		
		return contratoArtDto;
	}

	public EnderecoDto atualizaEnderecoAPartirDoEnderecoContratante(DomainGenericDto dto) {
		Endereco endereco = enderecoDao.getBy(Long.parseLong(dto.getIdString()));
		endereco.setId(null);
		endereco = enderecoDao.create(endereco);
		dto.setCodigo(endereco.getId());
		if (dto.getDescricao().equals("PROPRIETARIO")) {
			dao.atualizaEnderecoProprietario(dto);
		}
		if (dto.getDescricao().equals("CONTRATO")) {
			dao.atualizaEnderecoContrato(dto);
		}
		
		return enderecoConverter.toDto(endereco);
	}
	


}
