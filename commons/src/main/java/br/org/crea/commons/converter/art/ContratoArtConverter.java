package br.org.crea.commons.converter.art;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.art.ArtQuantificacaoDao;
import br.org.crea.commons.dao.art.ContratoArtDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.models.art.ArtQuantificacao;
import br.org.crea.commons.models.art.ContratoArt;
import br.org.crea.commons.models.art.dtos.ContratoArtDto;
import br.org.crea.commons.models.commons.Endereco;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.commons.dtos.EnderecoDto;
import br.org.crea.commons.models.commons.dtos.LocalidadeDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.service.PessoaService;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.StringUtil;

public class ContratoArtConverter {

	@Inject
	ContratoArtDao dao;

	@Inject
	PessoaDao pessoaDao;

	@Inject
	PessoaService pessoaService;

	@Inject
	ArtQuantificacaoDao quantificacaoDao;

	public List<ContratoArtDto> toListMinDto(List<ContratoArt> lista) {

		List<ContratoArtDto> resultado = new ArrayList<ContratoArtDto>();

		for (ContratoArt model : lista) {
			resultado.add(toMinDto(model));
		}

		return resultado;
	}

	public ContratoArtDto toMinDto(ContratoArt model) {

		ContratoArtDto dto = new ContratoArtDto();

		dto.setId(String.valueOf(model.getId()));
		dto.setNumero(model.getNumeroContrato());

		if (model.temPessoa()) {
			PessoaDto contratante = pessoaService.getPessoaBy(model.getPessoa().getId());
			dto.setNomeContratante(model.getNomeContratante());
			dto.setIdPessoaContratante(contratante.getId());
		}

		dto.setDataInicioFormatada(convertDateFormat(model.getDataInicio()));
		dto.setDataFimFormatada(convertDateFormat(model.getDataFim()));
		dto.setValorContrato(model.getValorContrato());
		dto.setValorContratoFormatado(model.temValorContrato() ? StringUtil.convertBigDecimalParaReal(model.getValorContrato()) : "");
		dto.setSequencial(model.getSequencial());
		dto.setFinalizado(model.getFinalizado());

		return dto;

	}

	public ContratoArtDto toDto(ContratoArt model) {
		ContratoArtDto dto = new ContratoArtDto();

		if (model != null) {

			dto.setId(String.valueOf(model.getId()));
			dto.setNumero(model.getNumeroContrato());
			dto.setNumeroArt(model.getArt().getNumero());
//			dto.setIdReceita(model.getReceita().getId());

			dto.setDataCadastro(model.getDataCadastro());
			dto.setDataCadastroFormatada(convertDateFormat(model.getDataCadastro()));
			dto.setProlabore(model.getProlabore());
			dto.setAssinaturaContratante(model.getAssContratante());
			dto.setValorContrato(model.getValorContrato());

			if (model.getValorContrato() != null) {
				dto.setValorContratoFormatado(StringUtil.convertBigDecimalParaReal(model.getValorContrato()));
			}

			// Valor Inserido Manualmente
			dto.setValorPago(model.getValorPago());
			dto.setValorReceber(model.getValorReceber());
			// Valor Calculado
			dto.setSalario(model.getSalario());
			dto.setJornadaDeTrabalho(model.getNHHJT());
			dto.setDescricaoComplementares(model.getDescricaoComplementares());
			dto.setDataInicio(model.getDataInicio());
			dto.setDataInicioFormatada(convertDataFormatada(model.getDataInicio()));
			dto.setDataInicioFormatadaYYYYMMDD(convertDateFormat(model.getDataInicio()));
			dto.setDataFim(model.getDataFim());
			dto.setDataFimFormatada(convertDataFormatada(model.getDataFim()));
			dto.setDataFimFormatadaYYYYMMDD(convertDateFormat(model.getDataFim()));
			dto.setDataBaixa(model.getDataBaixa());
			dto.setDataBaixaFormatada(convertDateFormat(model.getDataBaixa()));
			// Data Contrato
			// Data Contrato formatada
			dto.setMotivoBaixaOutros(model.getMotivoBaixaOutros());
			dto.setSequencial(model.getSequencial());
			dto.setPrazoMes(model.getPrazoMes());
			dto.setPrazoDia(model.getPrazoDia());
			dto.setPrazoDeterminado(model.getPrazoDeterminado());
			dto.setNumeroDePavimentos(model.getNumeroPavtos());
			dto.setAcessibilidade(model.getAcessibilidade());
			dto.setDataCelebracao(model.getDataCelebracao());
			dto.setDataCelebracaoFormatada(convertDateFormat(model.getDataCelebracao()));
			dto.setCodigoObraServico(model.getCodigoObraServico());
			
			dto.setArbitragem(model.getArbitragem());
			
			dto.setDescricaoCargoFuncao(model.getDescricaoCargoFuncao());

			// quantificação e unidade de medida
			dto.setQuantificacao(toQuantificacaoDto(quantificacaoDao.getByIdContrato(model.getId())));

			dto.setListAtividades(dao.getAtividadesDoContratoPor(model.getId()));
			dto.setListEspecificacoes(dao.getEspecificacoesDoContratoPor(model.getId()));
			dto.setListComplementos(dao.getComplementosDoContratoPor(model.getId()));

			if (model.temPessoa()) {
				PessoaDto contratante = pessoaService.getPessoaBy(model.getPessoa().getId());
				dto.setNomeContratante(model.getNomeContratante());
				dto.setIdPessoaContratante(contratante.getId());
				dto.setCpfOuCnpjContratante(contratante.getCpfOuCnpj());
				dto.setRegistroContratante(contratante.getRegistro());
				dto.setTipoPessoaContratante(contratante.getTipo().toString());
			}

			if (model.temProprietario()) {
				PessoaDto proprietario = pessoaService.getPessoaBy(model.getProprietario().getId());
				dto.setNomeProprietario(proprietario.getNome());
				dto.setIdPessoaProprietario(proprietario.getId());
				dto.setCpfOuCnpjProprietario(proprietario.getCpfOuCnpj());
			}

			if (model.temEndereco()) {
				dto.setEnderecoContrato(populaEndereco(model.getEndereco()));
			}

			if (model.temEnderecoContratante()) {
				dto.setEnderecoContratante(populaEndereco(model.getEnderecoContratante()));
			}
			
			if (model.temEnderecoProprietario()) {
				dto.setEnderecoProprietario(populaEndereco(model.getEnderecoProprietario()));
			}

			if (model.temRamoArt()) {
				DomainGenericDto ramoArt = new DomainGenericDto();
				ramoArt.setId(model.getRamoart().getId());
				ramoArt.setDescricao(model.getRamoart().getDescricao());

				dto.setRamoART(ramoArt);
			}

			if (model.temBaixaArt()) {
				DomainGenericDto baixaArt = new DomainGenericDto();
				baixaArt.setId(model.getBaixaArt().getId());
				baixaArt.setDescricao(model.getBaixaArt().getDescricao());

				dto.setBaixa(baixaArt);
			}

			if (model.temTipoUnidadeAdministrativa()) {
				DomainGenericDto tipoUnidadeAdministrativa = new DomainGenericDto();
				tipoUnidadeAdministrativa.setId(model.getTipoUnidadeAdministrativa().getId());
				tipoUnidadeAdministrativa.setDescricao(model.getTipoUnidadeAdministrativa().getDescricao());

				dto.setTipoUnidadeAdministrativa(tipoUnidadeAdministrativa);
			}

			if (model.temTipoVinculo()) {
				DomainGenericDto tipoVinculo = new DomainGenericDto();
				tipoVinculo.setId(model.getTipoVinculo().getId());
				tipoVinculo.setDescricao(model.getTipoVinculo().getDescricao());

				dto.setTipoVinculo(tipoVinculo);
			}

			if (model.temTipoContratante()) {
				DomainGenericDto tipoContratante = new DomainGenericDto();
				tipoContratante.setId(model.getTipoContratante().getId());
				tipoContratante.setDescricao(model.getTipoContratante().getDescricao());

				dto.setTipoContratante(tipoContratante);
			}

			if (model.temTipoAcaoInstitucional()) {
				DomainGenericDto tipoAcaoInstitucional = new DomainGenericDto();
				tipoAcaoInstitucional.setId(model.getTipoAcaoInstitucional().getId());
				tipoAcaoInstitucional.setDescricao(model.getTipoAcaoInstitucional().getDescricao());

				dto.setTipoAcaoInstitucional(tipoAcaoInstitucional);
			}

			if (model.temTipoCargoFuncao()) {
				DomainGenericDto tipoCargoFuncao = new DomainGenericDto();
				tipoCargoFuncao.setId(model.getTipoCargoFuncao().getId());
				tipoCargoFuncao.setDescricao(model.getTipoCargoFuncao().getDescricao());

				dto.setTipoCargoFuncao(tipoCargoFuncao);
			}

			if (model.temTipoFuncao()) {
				DomainGenericDto tipoFuncao = new DomainGenericDto();
				tipoFuncao.setId(model.getTipoFuncao().getId());
				tipoFuncao.setDescricao(model.getTipoFuncao().getDescricao());

				dto.setTipoFuncao(tipoFuncao);
			}

			if (model.temFinalidade()) {
				DomainGenericDto finalidade = new DomainGenericDto();
				finalidade.setId(model.getFinalidade().getId());
				finalidade.setDescricao(model.getFinalidade().getDescricao());

				dto.setFinalidade(finalidade);
			}

			if (model.temConvenioPublico()) {
				DomainGenericDto convenio = new DomainGenericDto();
				convenio.setId(model.getConvenioPublico().getId());
				convenio.setDescricao(model.getConvenioPublico().getDescricao());

				dto.setConvenioPublico(convenio);
			}
			
			dto.setFinalizado(model.getFinalizado());
			dto.setNumeroArtVinculadaAoContrato(model.getNumeroArtVinculadaAoContrato());
			
		}

		return dto;

	}

	private EnderecoDto populaEndereco(Endereco model) {
		EnderecoDto enderecoDto = new EnderecoDto();
		enderecoDto.setId(model.getId());
		enderecoDto.setIdString(model.getId() != null ?  String.valueOf(model.getId()) : "");
		enderecoDto.setNumero(model.getNumero());
		enderecoDto.setPostal(model.isPostal() ? "SIM" : "NAO");
		enderecoDto.setLogradouro(model.getLogradouro());
		enderecoDto.setComplemento(model.getComplemento());
		enderecoDto.setBairro(model.getBairro());
		enderecoDto.setCep(model.getCep());
		enderecoDto.setDataInclusao(convertDateFormat(model.getDataEndereco()));
		enderecoDto.setComplemento(model.getComplemento());
		enderecoDto.setLatitude(model.getLatitude());
		enderecoDto.setLongitude(model.getLongitude());
		enderecoDto.setAproximado(model.isAproximado());

		DomainGenericDto uf = new DomainGenericDto();
		uf.setId(model.getUf().getId());
		uf.setSigla(model.getUf().getSigla());
		enderecoDto.setUf(uf);

		DomainGenericDto tipoLogradouro = new DomainGenericDto();
		tipoLogradouro.setId(model.getTipoLogradouro().getId());
		tipoLogradouro.setDescricao(model.getTipoLogradouro().getDescricao());
		enderecoDto.setTipoLogradouro(tipoLogradouro);

		DomainGenericDto tipoEndereco = new DomainGenericDto();
		tipoEndereco.setId(model.getTipoEndereco().getId());
		tipoEndereco.setDescricao(model.getTipoEndereco().getDescricao());
		enderecoDto.setTipoEndereco(tipoEndereco);

		LocalidadeDto localidade = new LocalidadeDto();
		localidade.setId(model.getLocalidade().getId());
		localidade.setDescricao(model.getLocalidade().getDescricao());
		uf.setId(model.getUf().getId());
		uf.setSigla(model.getUf().getSigla());
		localidade.setUf(uf);
		enderecoDto.setLocalidade(localidade);
		return enderecoDto;
	}

	private DomainGenericDto toQuantificacaoDto(ArtQuantificacao quantificacao) {
		DomainGenericDto dto = new DomainGenericDto();

		if (quantificacao != null) {
			dto.setId(quantificacao.getId());
			dto.setNumero(quantificacao.getContrato().getId());
			dto.setValor(quantificacao.getValor());
			dto.setCodigo(quantificacao.getUnidadeMedida() != null ? quantificacao.getUnidadeMedida().getCodigo() : null);
			dto.setDescricao(quantificacao.getUnidadeMedida() != null ? quantificacao.getUnidadeMedida().getDescricao() : null);
			dto.setSigla(quantificacao.getUnidadeMedida() != null ? quantificacao.getUnidadeMedida().getAbreviatura() : null);
		}

		return dto;
	}
	
	private String convertDateFormat (Date data) {
		return data != null ? DateUtils.format(data, DateUtils.YYYY_MM_DD_COM_TRACOS) : "-";
	}
	private String convertDataFormatada (Date data) {
		return data != null ? DateUtils.format(data, DateUtils.DD_MM_YYYY): "-";
	}

}
