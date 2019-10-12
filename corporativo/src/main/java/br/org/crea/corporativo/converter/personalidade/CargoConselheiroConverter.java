package br.org.crea.corporativo.converter.personalidade;

import java.util.ArrayList;
import java.util.List;
import br.org.crea.commons.models.corporativo.personalidade.dto.CargoConselheiroDto;
import br.org.crea.commons.models.corporativo.personalidade.entity.CargoConselheiro;
import br.org.crea.commons.util.DateUtils;

public class CargoConselheiroConverter {
	
	public CargoConselheiroDto toDto(CargoConselheiro model){
		
		CargoConselheiroDto dto = new CargoConselheiroDto();
			
			if(model != null){
				
				dto.setId(model.getId());
				dto.setCargo(model.getCargo());
				dto.setConselheiro(model.getConselheiro());
				dto.setDataDesligamentoCargo(model.getDataDesligamentoCargo() != null ? DateUtils.format(model.getDataDesligamentoCargo(), DateUtils.DD_MM_YYYY) : "-");
				dto.setDataFinalCargo(model.getDataFinalCargo() != null ? DateUtils.format(model.getDataFinalCargo(), DateUtils.DD_MM_YYYY) : "-");
				dto.setDataInicialCargo(model.getDataInicialCargo() != null ? DateUtils.format(model.getDataInicialCargo(), DateUtils.DD_MM_YYYY) : "-");
				dto.setDataPosseCargo(model.getDataPosseCargo() != null ? DateUtils.format(model.getDataPosseCargo(), DateUtils.DD_MM_YYYY) : "-");	
				dto.setCargoRaiz(model.getCargoRaiz() );
				dto.setDepartamento(model.getDepartamento());
				dto.setPersonalidade(model.getPersonalidade());
				dto.setPortariaDecisaoPlenaria(model.getPortariaDecisaoPlenaria());
				dto.setRemovido(model.getRemovido());
				
				
			}
			
			return dto;
		}
		
		
		public List<CargoConselheiroDto> toListDto(List<CargoConselheiro> listModel) {
			
			List<CargoConselheiroDto> listDto = new ArrayList<CargoConselheiroDto>();
			
			for(CargoConselheiro a : listModel){
				listDto.add(toDto(a));
			}
			
			return listDto;
		}
		
		public CargoConselheiro toModel(CargoConselheiroDto dto){
			
			CargoConselheiro model = new CargoConselheiro();

			if(dto.getId() != null){
				model.setId(dto.getId());
			}
			model.setId(dto.getId());
			model.setCargo(dto.getCargo());
			model.setConselheiro(dto.getConselheiro());
			model.setDataDesligamentoCargo(DateUtils.generateDate(dto.getDataDesligamentoCargo()));
			model.setDataFinalCargo(DateUtils.generateDate(dto.getDataFinalCargo()));
			model.setDataInicialCargo(DateUtils.generateDate(dto.getDataInicialCargo()));
			model.setDataPosseCargo(DateUtils.generateDate(dto.getDataPosseCargo()));
			model.setCargoRaiz(dto.getCargoRaiz());
			model.setDepartamento(dto.getDepartamento());
			model.setPersonalidade(dto.getPersonalidade());		
			model.setPortariaDecisaoPlenaria(dto.getPortariaDecisaoPlenaria());
			model.setRemovido(dto.getRemovido());
			
			return model;
			
		}
		
		public String to_String(Integer inteiro) {
		
			return inteiro.toString();
		
		}

}
