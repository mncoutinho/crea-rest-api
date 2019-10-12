package br.org.crea.commons.converter.cadastro.profissional;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.cadastro.profissional.ProfissionalDao;
import br.org.crea.commons.models.cadastro.EntidadeProfissional;
import br.org.crea.commons.models.cadastro.dtos.empresa.EmpresaDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.EntidadeClasseDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.EntidadeProfissionalDto;
import br.org.crea.commons.models.cadastro.dtos.profissional.ValidaEntidadeClasseDto;
import br.org.crea.commons.util.DateUtils;

public class EntidadeProfissionalConverter {
	
	@Inject ProfissionalDao profissionalDao;

	public List<EntidadeProfissionalDto> toListDto(List<EntidadeProfissional> lista) {
		List<EntidadeProfissionalDto> listDto = new ArrayList<EntidadeProfissionalDto>();
		for (EntidadeProfissional model : lista) {
			listDto.add(toDto(model));
		}
		return listDto;
	}

	public EntidadeProfissionalDto toDto(EntidadeProfissional model) {
		
		EntidadeProfissionalDto dto = new EntidadeProfissionalDto();
		dto.setDataFiliacaoFormatada(model.getDataFiliacao() != null ? DateUtils.format(model.getDataFiliacao(), DateUtils.DD_MM_YYYY) : "-");
		dto.setDataOpcaoFormatada(model.getDataOpcao() != null ? DateUtils.format(model.getDataOpcao(), DateUtils.DD_MM_YYYY) : "-");
		dto.setOpcaoVoto(model.isOpcaoVoto());
		dto.setId(model.getId());
		dto.setIdProfissional(model.getIdProfissional());
		
		if (model.getEntidade() != null && model.getEntidade().getPessoaJuridica() != null) {
			EmpresaDto empresa = new EmpresaDto();
			empresa.setNomeFantasia(model.getEntidade().getPessoaJuridica().getNomeFantasia());
		
			
			
			EntidadeClasseDto entidadeClasse = new EntidadeClasseDto();
			entidadeClasse.setEmpresa(empresa);
			entidadeClasse.setId(model.getEntidade().getId());
			
			dto.setEntidadeClasse(entidadeClasse);
		}
		

		return dto;
	}
	
	public ValidaEntidadeClasseDto toListValidadaDto(List<EntidadeProfissional> lista) {
		ValidaEntidadeClasseDto listaValidade = null;
		
		if (!lista.isEmpty()) {
			listaValidade = new ValidaEntidadeClasseDto();
			
			if (profissionalDao.existeTituloHabilitadoNoAnoAtual(lista.get(0).getIdProfissional())) {
				listaValidade.setPodeHabilitarEntidadesClasse(false);
			}else {
				listaValidade.setPodeHabilitarEntidadesClasse(true);
			}
			
			List<EntidadeProfissionalDto> listDto = new ArrayList<EntidadeProfissionalDto>();
			for (EntidadeProfissional model : lista) {
				listDto.add(toDto(model));
			}
			listaValidade.setListaEntidade(listDto);
		}
		return listaValidade;
	}

}
