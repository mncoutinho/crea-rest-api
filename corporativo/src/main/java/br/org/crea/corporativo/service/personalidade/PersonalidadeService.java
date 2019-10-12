package br.org.crea.corporativo.service.personalidade;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.pessoa.PessoaConverter;
import br.org.crea.commons.dao.cadastro.pessoa.PessoaFisicaDao;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.personalidade.dto.PersonalidadeDto;
import br.org.crea.commons.models.corporativo.personalidade.entity.Personalidade;
import br.org.crea.commons.models.corporativo.pessoa.PessoaFisica;
import br.org.crea.corporativo.converter.personalidade.PersonalidadeConverter;
import br.org.crea.corporativo.dao.personalidade.PersonalidadeDao;


public class PersonalidadeService {
	
    @Inject PersonalidadeConverter converter;
	
	@Inject PersonalidadeDao dao;
	
	@Inject PessoaFisicaDao pessoaFisicaDao;
	
	@Inject PessoaConverter pessoaConverter;
	
	public PersonalidadeDto getById(Long id) {		
		return converter.toDto(dao.getBy(id));	
	}
	
	public List<PersonalidadeDto> getAll() {
		return converter.toListDto(dao.getAll());
	}
	
	public List<PersonalidadeDto> getByNomes() {
		return dao.getByNomes();		
	}
	
	public List<PersonalidadeDto> getConselheirosByNomes() {
		return dao.getConselheirosByNomes();		
	}
	
	public List<PersonalidadeDto> getPresidentesByNomes() {
		return dao.getPresidentesByNomes();		
		
		
	}
	
    public PersonalidadeDto atualizar(PersonalidadeDto dto) {
		
		try {	
	    dao.update(converter.toModel(dto));
		return dto;
		} catch (Exception e) {
			return null;
		}
	}
    
	public PessoaDto getPessoaById(Long id) {		
		PessoaFisica pessoaFisica = pessoaFisicaDao.getBy(id);
		return pessoaFisica != null ? pessoaConverter.toDto(pessoaFisica) : null;	
	}
	
    public PersonalidadeDto create(PersonalidadeDto dto) {
		
		try {	
			Personalidade personalidade = converter.toModel(dto);
			personalidade.setDataAlteracao(new Date() );
			personalidade.setDataCadastro(new Date() );
	    dao.create(personalidade);
		return dto;
		} catch (Exception e) {
			return null;
		}
	}
	
}
