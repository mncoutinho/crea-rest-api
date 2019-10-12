package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

import br.org.crea.commons.models.siacol.dtos.ValueRelDto;

public class RelSiacol01DataSetDto {

	private String seriesname;
	
	private List<ValueRelDto> data;

	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}

	public List<ValueRelDto> getData() {
		return data;
	}

	public void setData(List<ValueRelDto> data) {
		this.data = data;
	}
	
}
