package br.org.crea.siacol.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
import br.org.crea.commons.models.siacol.HabilidadePessoaSiacol;
import br.org.crea.commons.models.siacol.dtos.HabilidadePessoaDto;

public class HabilidadePessoaConverter {

	public List<HabilidadePessoaDto> toListDto(List<HabilidadePessoaSiacol> listModel) {
		
		List<HabilidadePessoaDto> listDto = new ArrayList<HabilidadePessoaDto>();
		
		for(HabilidadePessoaSiacol s : listModel){
			listDto.add(toDto(s));
		}
		
		return listDto;
		
	}
	

	public HabilidadePessoaDto toDto(HabilidadePessoaSiacol model) {
		
		HabilidadePessoaDto dto = new HabilidadePessoaDto();
		
		dto.setId(model.getId());
		if(model.getAssunto() != null){
			dto.setIdAssunto(model.getAssunto().getId());
		}if(model.getAssuntoSiacol() != null){
			dto.setIdAssunto(model.getAssuntoSiacol().getId());
		}
		dto.setAtivo(model.getAtivo());
		dto.setIdDepartamento(model.getDepartamento().getId());
		dto.setIdPessoa(model.getPessoa().getId());
		dto.setLiberadoParaDistribuicao(model.pessoaLiberadaParaReceber());
				
		return dto;
	}
	
	
	public HabilidadePessoaSiacol toModelAnalista(HabilidadePessoaDto dto){
		
		HabilidadePessoaSiacol model = new HabilidadePessoaSiacol();

		Departamento departamento = new Departamento();
		Assunto assunto = new Assunto();
					
		departamento.setId(dto.getIdDepartamento());
		assunto.setId(new Long(dto.getIdAssunto()));
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(dto.getIdPessoa());
		model.setPessoa(pessoa);
		model.setAtivo(true);
		model.setDepartamento(departamento);
		model.setAssunto(assunto);
		
		return model;
		
	}
	
	public HabilidadePessoaSiacol toModelConselheiro(HabilidadePessoaDto dto){
		
		HabilidadePessoaSiacol model = new HabilidadePessoaSiacol();

		Departamento departamento = new Departamento();
		AssuntoSiacol assuntoSiacol = new AssuntoSiacol();
					
		departamento.setId(dto.getIdDepartamento());
		assuntoSiacol.setId(new Long(dto.getIdAssunto()));
		
		Pessoa pessoa = new Pessoa();
		pessoa.setId(dto.getIdPessoa());
		model.setPessoa(pessoa);
		model.setAtivo(true);
		model.setDepartamento(departamento);
		model.setAssuntoSiacol(assuntoSiacol);
		
		return model;
		
	}


	public List<HabilidadePessoaSiacol> toListModel(List<HabilidadePessoaSiacol> listaPessoaHabilidade) {
		List<HabilidadePessoaSiacol> listaHabilidadePessoaSiacol = new ArrayList<HabilidadePessoaSiacol>();
		for (HabilidadePessoaSiacol habilidadePessoaSiacol : listaPessoaHabilidade) {
			HabilidadePessoaSiacol habilidadePessoa = new HabilidadePessoaSiacol();
			habilidadePessoa.setId(habilidadePessoaSiacol.getId());
			habilidadePessoa.setPessoa(habilidadePessoaSiacol.getPessoa());
			habilidadePessoa.setAssunto(habilidadePessoaSiacol.getAssunto());
			habilidadePessoa.setAssuntoSiacol(habilidadePessoaSiacol.getAssuntoSiacol());
//			habilidadePessoa.setDepartamento(habilidadePessoaSiacol.getDepartamento());

			
			
			listaHabilidadePessoaSiacol.add(habilidadePessoa);
			
		}
		return listaHabilidadePessoaSiacol;
	}
	
}
