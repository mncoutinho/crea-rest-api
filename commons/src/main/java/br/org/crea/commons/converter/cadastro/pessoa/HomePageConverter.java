package br.org.crea.commons.converter.cadastro.pessoa;

import java.util.ArrayList;
import java.util.List;

import br.org.crea.commons.models.cadastro.HomePage;
import br.org.crea.commons.models.cadastro.dtos.pessoa.HomePageDto;

public class HomePageConverter {
	
	public List<HomePageDto> toListDto(List<HomePage> lista){
		List<HomePageDto> resultado = new ArrayList<HomePageDto>();
		
		for(HomePage homepage : lista){
			resultado.add(toDto(homepage));
		}
		
		return resultado;
	}

	private HomePageDto toDto(HomePage homepage) {
		HomePageDto homePageDto = new HomePageDto();
		homePageDto.setId(String.valueOf(homepage.getId()));
		homePageDto.setUrl(homepage.getUrl());
		return homePageDto;
	}
	

}
