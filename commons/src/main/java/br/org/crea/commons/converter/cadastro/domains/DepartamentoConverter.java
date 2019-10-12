package br.org.crea.commons.converter.cadastro.domains;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.dao.ModalidadeDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Modalidade;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.DomainGenericDto;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

public class DepartamentoConverter {
	
	@Inject ModalidadeDao modalidadeDao;
	
	@Inject ModalidadeConverter modalidadeConverter;

	public List<DepartamentoDto> toListDto(List<Departamento> listModel) {

		List<DepartamentoDto> listDto = new ArrayList<DepartamentoDto>();

		for (Departamento d : listModel) {
			listDto.add(toDto(d));
		}

		return listDto;
	}

	public DepartamentoDto toDto(Departamento model) {

		DepartamentoDto dto = new DepartamentoDto();
		Modalidade modalidade = new Modalidade();
		modalidade = modalidadeDao.getByIdDepartamento(model.getId());

		dto.setId(model.getId());
		dto.setCodigo(model.getCodigo());
		dto.setNome(model.getNome());
		dto.setNomeExibicao(model.getNomeExibicao());
		dto.setAtendimento(model.getAtendimento());
		dto.setSigla(model.getSigla());
		dto.setAtendimento(model.getAtendimento());
		dto.setDividaAtiva(model.isDividaAtiva());
		dto.setEmailCoordenacao(model.getEmailCoordenacao());
		dto.setExecutaJulgamentoRevelia(model.getEnviaParaJulgamentoRevelia());
		dto.setEnviaParaJulgamentoRevelia(model.getEnviaParaJulgamentoRevelia());
		dto.setImportacaoSiacol(model.getImportacaoSiacol());
		dto.setRemovido(model.getRemovido());
		dto.setModulo(model.getModulo());

		DepartamentoDto departamento = new DepartamentoDto();
		if (model.temDepartamentoPai()) {
			departamento.setId(model.getDepartamentoPai().getId());
			departamento.setNome(model.getDepartamentoPai().getNome());
			dto.setDepartamentoPai(departamento);
		}else {
			departamento.setId(model.getId());
			departamento.setNome(model.getNome());
			dto.setDepartamentoPai(departamento);
		}

		if (model.temCoordenador()) {
			dto.setCoordenador(toPessoaDto(model.getCoordenador()));
		}

		if (model.temAdjunto()) {
			dto.setAdjunto(toPessoaDto(model.getAdjunto()));
		}
		if (modalidade != null) {
			dto.setModalidade(modalidadeConverter.toDto(modalidade));
		}

		return dto;
	}

	public Departamento toModel(DepartamentoDto dto) {

		Departamento model = new Departamento();

		if (dto.getId() != null) {
			model.setId(new Long(dto.getId()));
		}
		model.setCodigo(dto.getCodigo());
		model.setNome(dto.getNome());
		model.setNomeExibicao(dto.getNomeExibicao());
		model.setAtendimento(dto.getAtendimento());
		model.setSigla(dto.getSigla());
		model.setAtendimento(dto.getAtendimento());
		model.setDividaAtiva(dto.isDividaAtiva());
		model.setEmailCoordenacao(dto.getEmailCoordenacao());
		model.setExecutaJulgamentoRevelia(dto.getEnviaParaJulgamentoRevelia());
		model.setEnviaParaJulgamentoRevelia(dto.getEnviaParaJulgamentoRevelia());
		model.setImportacaoSiacol(dto.getImportacaoSiacol());
		model.setRemovido(dto.getRemovido());
		model.setModulo(dto.getModulo());

		Departamento departamento = new Departamento();
		if (dto.temDepartamentoPai()) {
			departamento.setId(dto.getDepartamentoPai().getId());
			departamento.setNome(dto.getDepartamentoPai().getNome());
			model.setDepartamentoPai(departamento);
		}else {
			departamento.setId(dto.getId());
			departamento.setNome(dto.getNome());
			model.setDepartamentoPai(departamento);
		}

		if (dto.temCoordenador()) {
			model.setCoordenador(toPessoaModel(dto.getCoordenador()));
		}

		if (dto.temAdjunto()) {
			model.setAdjunto(toPessoaModel(dto.getAdjunto()));
		}

		return model;
	}

	private Pessoa toPessoaModel(DomainGenericDto dto) {
		Pessoa model = new Pessoa();
		model.setId(dto.getId());
		model.setNome(dto.getNome());
		return model;
	}

	private DomainGenericDto toPessoaDto(Pessoa pessoa) {
		DomainGenericDto dto = new DomainGenericDto();
		dto.setId(pessoa.getId());
		dto.setNome(pessoa.getNome());
		return dto;
	}

}
