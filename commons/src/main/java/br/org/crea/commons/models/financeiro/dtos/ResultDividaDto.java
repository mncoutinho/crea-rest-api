package br.org.crea.commons.models.financeiro.dtos;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

public class ResultDividaDto {
	
	
	private PessoaDto pessoa;
	
	private boolean liberaParcelamentoAnuidadeCorrente;
	
	private List<DividaDto> dividas = new ArrayList<DividaDto>();
	

	public boolean isLiberaParcelamentoAnuidadeCorrente() {
		return liberaParcelamentoAnuidadeCorrente;
	}

	public void setLiberaParcelamentoAnuidadeCorrente(boolean liberaParcelamentoAnuidadeCorrente) {
		this.liberaParcelamentoAnuidadeCorrente = liberaParcelamentoAnuidadeCorrente;
	}

	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
		this.pessoa = pessoa;
	}

	public List<DividaDto> getDividas() {
		return dividas;
	}

	public void setDividas(List<DividaDto> dividas) {
		this.dividas = dividas;
	}
	
	
	

}
