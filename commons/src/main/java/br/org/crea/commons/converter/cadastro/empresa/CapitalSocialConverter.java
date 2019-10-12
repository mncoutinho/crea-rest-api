package br.org.crea.commons.converter.cadastro.empresa;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.dtos.empresa.CapitalSocialDto;
import br.org.crea.commons.models.corporativo.pessoa.CapitalSocial;
import br.org.crea.commons.util.DateUtils;
import br.org.crea.commons.util.NumericUtils;

public class CapitalSocialConverter {

	public List<CapitalSocialDto> toListDto(List<CapitalSocial> listaModel) {
		List<CapitalSocialDto> listaDto = new ArrayList<CapitalSocialDto>();
		for (CapitalSocial objetoSocial : listaModel) {
			CapitalSocialDto dto = this.toDto(objetoSocial);
			listaDto.add(dto);
		}
		return listaDto;
	}

	public CapitalSocialDto toDto(CapitalSocial model) {
		CapitalSocialDto dto = new CapitalSocialDto();
		
		dto.setCodigo(model.getCodigo().toString());
		dto.setTipoCapitalSocial(model.getTipoCapitalSocial().getDescricao());
		dto.setDataInclusao(DateUtils.format(model.getDataInclusao(), DateUtils.DD_MM_YYYY));
		dto.setDataAlteracao(DateUtils.format(model.getDataAlteracao(), DateUtils.DD_MM_YYYY));
		dto.setDataIntegralizacao(DateUtils.format(model.getDataIntegralizacao(), DateUtils.DD_MM_YYYY));
		dto.setDataJuntaComercial(DateUtils.format(model.getDataJuntaComercial(), DateUtils.DD_MM_YYYY));
		dto.setDataReceitaFederal(DateUtils.format(model.getDataReceitaFederal(), DateUtils.DD_MM_YYYY));
		dto.setFaixaCapital(model.getFaixaCapital().toString());
		dto.setValorCapital(NumericUtils.currencyFormat(model.getValorCapital()));
		dto.setValorCapitalAIntegralizar(NumericUtils.currencyFormat(model.getValorCapitalAIntegralizar()));
		dto.setValorCapitalIntegralizado(NumericUtils.currencyFormat(model.getValorCapitalIntegralizado()));
		dto.setValorCapitalMoedaCorrente(NumericUtils.currencyFormat(model.getValorCapitalMoedaCorrente()));

		return dto;
	}

}
