package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelSiacol01Dto {

	private List<LabelRelDto> labels;
	
	private List<RelSiacol01DataSetDto> dataset;

	public List<LabelRelDto> getLabels() {
		return labels;
	}

	public void setLabels(List<LabelRelDto> labels) {
		this.labels = labels;
	}

	public List<RelSiacol01DataSetDto> getDataset() {
		return dataset;
	}

	public void setDataset(List<RelSiacol01DataSetDto> dataset) {
		this.dataset = dataset;
	}
	
	
}
