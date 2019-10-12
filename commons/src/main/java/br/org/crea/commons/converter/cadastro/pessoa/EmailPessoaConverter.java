package br.org.crea.commons.converter.cadastro.pessoa;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.funcionario.FuncionarioDao;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.models.cadastro.EmailPessoa;
import br.org.crea.commons.models.cadastro.dtos.EmailDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

public class EmailPessoaConverter {
	
	@Inject FuncionarioDao funcionarioDao;
	
	@Inject PessoaDao pessoaDao;
	
	public EmailPessoa toModel(String email, Long idPessoa, Long idFuncionario) {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(idPessoa);
		
		EmailPessoa emailPessoa = new EmailPessoa();
		emailPessoa.setDescricao(email);
		emailPessoa.setPessoa(pessoa);
		emailPessoa.setDataAtualizacao(new Date());
		emailPessoa.setIdFuncionario(idFuncionario == null ? new Long(99990) : idFuncionario);
		
		return emailPessoa;
	}
	
	public EmailPessoa toModel(EmailDto dto) {
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(dto.getIdPessoa());
		
		EmailPessoa emailPessoa = new EmailPessoa();
		emailPessoa.setDescricao(dto.getDescricao());
		emailPessoa.setPessoa(pessoa);
		emailPessoa.setDataAtualizacao(new Date());
		emailPessoa.setIdFuncionario(dto.getIdFuncionario() == null ? new Long(99990) : dto.getIdFuncionario());
		
		return emailPessoa;
	}

}
