package br.org.crea.commons.models.cadastro.dtos;

public class VistoDto {

	private String dataVisto;
	
	private boolean estaCancelado;
	
	private String dataCancelamento;
	
	private String regionaldoVisto;

	public String getDataVisto() {
		return dataVisto;
	}

	public void setDataVisto(String dataVisto) {
		this.dataVisto = dataVisto;
	}

	public boolean isEstaCancelado() {
		return estaCancelado;
	}

	public void setEstaCancelado(boolean estaCancelado) {
		this.estaCancelado = estaCancelado;
	}

	public String getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(String dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getRegionaldoVisto() {
		return regionaldoVisto;
	}

	public void setRegionaldoVisto(String regionaldoVisto) {
		this.regionaldoVisto = regionaldoVisto;
	}
	
}
