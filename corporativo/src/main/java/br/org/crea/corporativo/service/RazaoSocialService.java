package br.org.crea.corporativo.service;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.corporativo.dtos.RazaoSocialDto;
import br.org.crea.corporativo.converter.RazaoSocialConverter;

public class RazaoSocialService {

	
	@Inject RazaoSocialConverter converter;
	
	@Inject InteressadoDao dao;
	
	public RazaoSocialDto getRazaoSocialBy(Long idPessoaJuridica){
//		return converter.toDto(dao.buscaDescricaoRazaoSocial(idPessoaJuridica));
		return null;
	}
	
}
