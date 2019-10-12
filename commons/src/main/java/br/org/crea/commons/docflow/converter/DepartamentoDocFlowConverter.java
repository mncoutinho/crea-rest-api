package br.org.crea.commons.docflow.converter;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.docflow.dto.DepartamentoDocflowDto;
import br.org.crea.commons.docflow.helper.ConstantDocflow;
import br.org.crea.commons.docflow.model.departamento.DepartamentoDocflow;
import br.org.crea.commons.docflow.model.departamento.MetadadoDepartamentoDocflow;

public class DepartamentoDocFlowConverter {
	
	public DepartamentoDocflowDto toDto(List<MetadadoDepartamentoDocflow> listDataDepartamento){
		
		DepartamentoDocflowDto dto = populaDadosUnidade(listDataDepartamento);
		return dto;
	}
	
	public DepartamentoDocflowDto populaDadosUnidade(List<MetadadoDepartamentoDocflow> listDataDepartamento) {
		DepartamentoDocflowDto dto = new DepartamentoDocflowDto();
		
	for (MetadadoDepartamentoDocflow dataDepartamento : listDataDepartamento) {
			
			switch (dataDepartamento.getName()) {
			case ConstantDocflow.CODIGO_UNIDADE:
				dto.setCodigoUnidade(dataDepartamento.getValue());
			case ConstantDocflow.SIGLA_UNIDADE:
				dto.setSigla(dataDepartamento.getValue());
			case ConstantDocflow.NOME_UNIDADE:
				dto.setNome(dataDepartamento.getValue());
			}
		}
		return dto;
	}
	
	public List<DepartamentoDocflowDto > toListDto(List<DepartamentoDocflow> listMetadadosUnidades) {
		List<DepartamentoDocflowDto > listUnidades = new ArrayList<DepartamentoDocflowDto>();
		
		for (DepartamentoDocflow unidade : listMetadadosUnidades) {

			List<MetadadoDepartamentoDocflow> listDadosUnidade = unidade.getDadosUnidade();
			listUnidades.add(toDto(listDadosUnidade));
		}
		return listUnidades;
	}
	
}
