package br.org.crea.commons.service;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.PessoaFisicaDao;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;

public class PessoaFisicaService {
	
	@Inject PessoaFisicaDao dao;

	public DomainGenericDto getPisPasep(Long idPessoa) {
		return dao.getPisPasep(idPessoa);
	}

	public DomainGenericDto salvaPisPasep(DomainGenericDto dto) {
		dao.salvaPisPasep(dto);
		return dto;
	}

	public String getAssinatura(Long idPessoa) {
		PessoaFisica pessoa = dao.getBy(idPessoa);
		
		return pessoa.getAssinaturaBase64();
	}
	

}
