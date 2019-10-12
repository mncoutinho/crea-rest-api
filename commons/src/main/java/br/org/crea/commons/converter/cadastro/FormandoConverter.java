package br.org.crea.commons.converter.cadastro;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.org.crea.commons.models.cadastro.CampusCadastro;
import br.org.crea.commons.models.cadastro.CursoCadastro;
import br.org.crea.commons.models.cadastro.InstituicaoEnsino;
import br.org.crea.commons.models.cadastro.dtos.pessoa.FormandoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;
import br.org.crea.commons.models.corporativo.pessoa.Formando;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;

public class FormandoConverter {
	
	SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
	
	public Formando toModel(FormandoDto dto) {

		InstituicaoEnsino instituicaoEnsino = new InstituicaoEnsino();
		instituicaoEnsino.setId(dto.getInstituicao().getId());
		
		CursoCadastro cursoCadastro = new CursoCadastro();
		cursoCadastro.setId(dto.getCurso().getId());
		
		CampusCadastro campusCadastro = new CampusCadastro();
		campusCadastro.setId(dto.getCampus().getId());
		
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf(dto.getCpf());
		pessoaFisica.setNome(dto.getNome().toUpperCase());
		pessoaFisica.setTipoPessoa(TipoPessoa.FORMANDO);
		
		Formando formando = new Formando();
		formando.setPessoaFisica(pessoaFisica);
		formando.setInstituicaoEnsino(instituicaoEnsino);
		formando.setCurso(cursoCadastro);
		if(dto.temDataFormaturaPlanilha()) {
			try {
				formando.setDataFormatura(formatoData.parse(dto.getDataFormaturaPlanilha()));
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
		}else {
			formando.setDataFormatura(dto.getDataFormatura());
		}
		
		formando.setPrecisaoDataFormatura(dto.getPrecisaoDataFormatura());
		
		
		return formando;
		
	}
	
	public FormandoDto toDto(Formando formando) {
		FormandoDto formandoDto = new FormandoDto();
		formandoDto.setId(formando.getId());
		formandoDto.setCpf(formando.getCpfCnpj());
		formandoDto.setNome(formando.getNome());
		DomainGenericDto status = new DomainGenericDto();
		status.setId(1l);
		status.setDescricao("Ok");
		formandoDto.setStatus(status);
		
		return formandoDto;
	}

}
