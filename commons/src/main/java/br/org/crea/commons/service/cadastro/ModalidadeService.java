package br.org.crea.commons.service.cadastro;

import java.util.List;

import javax.inject.Inject;

import br.org.crea.commons.converter.cadastro.domains.ModalidadeConverter;
import br.org.crea.commons.dao.ModalidadeDao;
import br.org.crea.commons.dao.cadastro.RlModalidadeDepartamentoDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.models.cadastro.Departamento;
import br.org.crea.commons.models.cadastro.Modalidade;
import br.org.crea.commons.models.cadastro.RlModalidadeDepartamento;
import br.org.crea.commons.models.cadastro.dtos.ModalidadeDto;
import br.org.crea.commons.models.commons.dtos.RlModalidadeDepartamentoDto;

public class ModalidadeService {

	@Inject
	ModalidadeConverter converter;

	@Inject
	ModalidadeDao dao;
	
	@Inject
	DepartamentoDao departamentoDao;
	
	@Inject
	RlModalidadeDepartamentoDao rlModalidadeDepartamentoDao;

	public List<ModalidadeDto> getAll() {
		return converter.toListDto(dao.getAll());
	}

	public RlModalidadeDepartamento salvaRlModalidadeDepartamento(RlModalidadeDepartamentoDto dto) {
		RlModalidadeDepartamento rlModalidadeDepartamento = new RlModalidadeDepartamento();
		Departamento departamento = new Departamento();
		departamento = departamentoDao.getBy(dto.getDepartamento().getId());
		Modalidade modalidade = new Modalidade();
		modalidade = dao.getByCodigo(dto.getModalidade().getCodigo());
		
		
		rlModalidadeDepartamento.setId(dao.getIdRlModalidadeDepartamentoByIdDepartamento(dto.getDepartamento().getId()));
		rlModalidadeDepartamento.setDepartamento(departamento);
		rlModalidadeDepartamento.setModalidade(modalidade);
		
		if (rlModalidadeDepartamento.getId() == null) {
			return rlModalidadeDepartamentoDao.create(rlModalidadeDepartamento);
		}else {
			return rlModalidadeDepartamentoDao.update(rlModalidadeDepartamento);
		}
	}


}
