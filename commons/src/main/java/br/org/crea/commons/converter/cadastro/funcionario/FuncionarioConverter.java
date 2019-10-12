package br.org.crea.commons.converter.cadastro.funcionario;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.FuncionarioDto;
import br.org.crea.commons.models.corporativo.pessoa.Funcionario;
import br.org.crea.commons.service.HttpClientGoApi;

public class FuncionarioConverter {
	
	@Inject HttpClientGoApi httpGoApi;

	public List<FuncionarioDto> toListDto(List<Funcionario> listModel) {
		
		List<FuncionarioDto> listDto = new ArrayList<FuncionarioDto>();
		
		for(Funcionario f : listModel){
			listDto.add(toDto(f));
		}
		
		return listDto;
	}

	public FuncionarioDto toDto(Funcionario model) {
		
		FuncionarioDto dto = new FuncionarioDto();

		dto.setId(model.getId());
		dto.setMatricula(model.getMatricula());
		dto.setNome(model.getPessoaFisica().getNome());
		dto.setIdDepartamento(model.getDepartamento().getId());
		dto.setPerfil(model.getPessoaFisica().getDescricaoPerfil());
		
		
		return dto;
	}
	
	public FuncionarioDto toDto(UserFrontDto userDto) {
		
		FuncionarioDto dto = new FuncionarioDto();

		dto.setId(userDto.getIdFuncionario());
		dto.setIdPessoa(userDto.getIdPessoa());
		dto.setMatricula(userDto.getMatricula());
		dto.setNome(userDto.getNome());
		dto.setIdDepartamento(Long.parseLong(userDto.getDepartamento().getId()));
		
		return dto;
	}
	

}
