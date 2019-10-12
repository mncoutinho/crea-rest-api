package br.org.crea.commons.converter.financeiro;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.financeiro.FinDividaDao;
import br.org.crea.commons.models.cadastro.Natureza;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.financeiro.Banco;
import br.org.crea.commons.models.financeiro.FinDivida;
import br.org.crea.commons.models.financeiro.dtos.DividaDto;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.NumericUtils;

public class DividaConverter {

	@Inject
	FinDividaDao finDividaDao;

	public List<DividaDto> toListDto(List<FinDivida> listModel) {

		List<DividaDto> listDto = new ArrayList<DividaDto>();

		for (FinDivida d : listModel) {
			listDto.add(toDto(d));
		}

		return listDto;
	}

	public DividaDto toDto(FinDivida m) {

		if (m == null) {
			return null;
		}

		DividaDto dto = new DividaDto();

		dto.setId(String.valueOf(m.getId()));
		dto.setIdentificadorDivida(m.getIdentificadorDivida());
		if (NumericUtils.isNumericValue(m.getIdentificadorDivida())) {
			dto.setAnoDivida(m.getIdentificadorDivida());
		} 
		dto.setDataVencimento(m.getDataVencimento());
		dto.setDataVencimentoFormatada(DateUtils.format(m.getDataVencimento(), DateUtils.DD_MM_YYYY));
		dto.setStatusDivida(m.getStatus().getId());
		dto.setDescricao(m.getStatus().getDescricao().trim());
		dto.setParcela(m.getParcela());
		dto.setTipo(m.getNatureza().getDescricao());
		dto.setValorAtual(m.getValorAtual());
		dto.setValorOriginal(m.getValorOriginal());
		dto.setValorPago(m.getValorPago());
		dto.setJuros(m.getJuros());
		dto.setMulta(m.getMulta());
		dto.setIdPessoa(m.getPessoa().getId());
		dto.setDataPagamento(m.getDataQuitacao() != null ? m.getDataQuitacao() : null);
		dto.setDataPagamentoFormatada(m.getDataQuitacao() != null ? DateUtils.format(m.getDataQuitacao(), DateUtils.DD_MM_YYYY) : "-");
		dto.setUfPagamento(m.getLocalPgto() != null ? m.getLocalPgto().getUf().getSigla() : "");
		dto.setServicoExecutado(m.getServicoExecutado() != null ? m.getServicoExecutado() : false);
		dto.setObservacao(m.getObservacao() != null ? m.getObservacao() : "");

		return dto;

	}

	public DividaDto toDividaAnuidadeDto(Long idPessoa) {
		DividaDto dto = new DividaDto();

		FinDivida anuidade = finDividaDao.pessoaPossuiDividaAnuidadeParcelada(idPessoa) ? finDividaDao.getUltimaParcelaAnuidadeVencidaPagaPor(idPessoa) : finDividaDao
				.getUltimaAnuidadePagaPor(idPessoa);

		if (anuidade != null) {

			dto.setId(String.valueOf(anuidade.getId()));
			dto.setAnoDivida(anuidade.getIdentificadorDivida());
			dto.setEstaEmParcelamento(anuidade.getParcela() > 0 ? true : false);
			dto.setDataPagamentoFormatada(anuidade.getDataQuitacao() != null ? DateUtils.format(anuidade.getDataQuitacao(), DateUtils.DD_MM_YYYY) : "-");
			dto.setDataPagamento(anuidade.getDataQuitacao() != null ? anuidade.getDataQuitacao() : null);
			dto.setDataQuitacaoUltimaParcelaVencida(anuidade.getParcela() > 0 ? anuidade.getDataQuitacao() : null);
			dto.setDataQuitacaoUltimaParcelaVencidaFormatada(anuidade.getParcela() > 0 ? DateUtils.format(anuidade.getDataQuitacao(), DateUtils.DD_MM_YYYY) : "-");
			dto.setParcela(anuidade.getParcela());

		} else {
			dto.setDataPagamentoFormatada("-");
		}
		return dto;
	}
	
	public List<DomainGenericDto> toListBancoDto(List<Banco> listModel) {

		List<DomainGenericDto> listDto = new ArrayList<DomainGenericDto>();

		for (Banco d : listModel) {
			listDto.add(toBancoDto(d));
		}

		return listDto;
	}

	public DomainGenericDto toBancoDto(Banco m) {

		if (m == null) {
			return null;
		}

		DomainGenericDto dto = new DomainGenericDto();

		dto.setId(m.getId());
		dto.setCodigo(m.getCodigoBanco());
		dto.setNome(m.getNome());

		return dto;

	}

	public List<DomainGenericDto> toListNaturezaDto(List<Natureza> listModel) {
		List<DomainGenericDto> listDto = new ArrayList<DomainGenericDto>();

		for (Natureza d : listModel) {
			listDto.add(toNaturezaDto(d));
		}

		return listDto;
	}
	
	public DomainGenericDto toNaturezaDto(Natureza m) {

		if (m == null) {
			return null;
		}

		DomainGenericDto dto = new DomainGenericDto();

		dto.setId(m.getId());
		dto.setDescricao(m.getDescricao());

		return dto;

	}

}
