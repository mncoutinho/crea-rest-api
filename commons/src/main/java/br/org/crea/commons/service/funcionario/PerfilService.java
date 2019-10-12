package br.org.crea.commons.service.funcionario;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.service.HttpCouchApi;

public class PerfilService {

	
	@Inject PessoaDao pessoaDao;
	
	@Inject HttpCouchApi couchApi;


	public Object novoPerfil(Object dto) {
		couchApi.salvaPerfil(dto);
		return dto;
	}

	public Object alteraPerfil(String id, Object obj) {
		couchApi.alteraPerfil(id, obj);
		return obj;
	}

	public GenericDto salvaPerfil(GenericDto dto) {
		Pessoa pessoa = new Pessoa();
		
		pessoa = pessoaDao.getPessoa(new Long(dto.getId()));
		pessoa.setPerfil(dto.getIdPerfil());
		pessoa.setDescricaoPerfil(dto.getDescricaoPerfil());
		
		
		try {
			
			pessoaDao.update(pessoa);
			pessoaDao.atualizaDescricaoPerfil(pessoa.getPerfil(), pessoa.getDescricaoPerfil());
			return dto;	
		}catch (Throwable e) {
			return null;
		}
		
	}

	public Object getPerfilByIdPessoa(Long idPessoa) {
		
		Pessoa pessoa = pessoaDao.getPessoa(idPessoa);
		
		return pessoa != null ? couchApi.getPerfil(pessoa.getPerfil()) : null;
		
	}




}






