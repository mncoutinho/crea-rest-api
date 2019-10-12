package br.org.crea.commons.models.siacol.dtos.documento;

import java.util.List;

import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;

public class PautaDto {

	private List<GenericSiacolDto> ordenacaoPauta;
	
	private List<String> listDigitoExclusaoProtocolo;
	
	private GenericDto departamento;
	
	private boolean pautaVirtual;
	
	private boolean incluiProtocoloDesfavoravel;

	public List<GenericSiacolDto> getOrdenacaoPauta() {
		return ordenacaoPauta;
	}

	public void setOrdenacaoPauta(List<GenericSiacolDto> ordenacaoPauta) {
		this.ordenacaoPauta = ordenacaoPauta;
	}

	public List<String> getListDigitoExclusaoProtocolo() {
		return listDigitoExclusaoProtocolo;
	}

	public void setListDigitoExclusaoProtocolo(List<String> listDigitoExclusaoProtocolo) {
		this.listDigitoExclusaoProtocolo = listDigitoExclusaoProtocolo;
	}

	public GenericDto getDepartamento() {
		return departamento;
	}

	public void setDepartamento(GenericDto departamento) {
		this.departamento = departamento;
	}

	public boolean isPautaVirtual() {
		return pautaVirtual;
	}

	public void setPautaVirtual(boolean pautaVirtual) {
		this.pautaVirtual = pautaVirtual;
	}

	public boolean incluiProtocoloDesfavoravel() {
		return incluiProtocoloDesfavoravel;
	}

	public void setIncluiProtocoloDesfavoravel(boolean incluiProtocoloDesfavoravel) {
		this.incluiProtocoloDesfavoravel = incluiProtocoloDesfavoravel;
	}
	
}
