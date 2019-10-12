package br.org.crea.commons.models.commons.dtos;

import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.cadastro.dtos.ModalidadeDto;

public class RlModalidadeDepartamentoDto {
	
	private Long id;

	private ModalidadeDto modalidade;

	private DepartamentoDto departamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ModalidadeDto getModalidade() {
		return modalidade;
	}

	public void setModalidade(ModalidadeDto modalidade) {
		this.modalidade = modalidade;
	}

	public DepartamentoDto getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoDto departamento) {
		this.departamento = departamento;
	}

}
