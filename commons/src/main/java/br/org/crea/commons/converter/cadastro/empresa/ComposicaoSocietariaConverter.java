package br.org.crea.commons.converter.cadastro.empresa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.pessoa.InteressadoDao;
import br.org.crea.commons.models.cadastro.dtos.empresa.ComposicaoSocietariaDto;
import br.org.crea.commons.models.cadastro.dtos.empresa.EmpresaDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.corporativo.pessoa.ComposicaoSocietaria;

public class ComposicaoSocietariaConverter {
	
	@Inject InteressadoDao interessadoDao;

	public List<ComposicaoSocietariaDto> toListDto(List<ComposicaoSocietaria> listaModel) {
		List<ComposicaoSocietariaDto> listaDto = new ArrayList<ComposicaoSocietariaDto>();
		for (ComposicaoSocietaria composicao : listaModel) {
			ComposicaoSocietariaDto dto = this.toDto(composicao);
			listaDto.add(dto);
		}
		return listaDto;
	}

	public ComposicaoSocietariaDto toDto(ComposicaoSocietaria model) {
		ComposicaoSocietariaDto dto = new ComposicaoSocietariaDto();
		
		dto.setId(model.getId());
		if (model.getEmpresa() != null) {
			EmpresaDto empresa = new EmpresaDto();
			empresa.setId(model.getEmpresa().getId());
			empresa.setNome(interessadoDao.buscaDescricaoRazaoSocial(model.getEmpresa().getId()));
			dto.setEmpresa(empresa);
		}
		if (model.getSocio() != null) {
			PessoaDto pessoa = new PessoaDto();
			pessoa.setId(model.getSocio().getId());
			pessoa.setNome(model.getSocio().getNome());
			pessoa.setTipo(model.getSocio().getTipoPessoa());
			dto.setSocio(pessoa);
		}
		
		dto.setPercentual(String.valueOf(model.getPercentual()));

		return dto;
	}

}
