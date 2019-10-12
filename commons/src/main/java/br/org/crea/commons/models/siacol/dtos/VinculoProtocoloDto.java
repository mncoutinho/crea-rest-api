package br.org.crea.commons.models.siacol.dtos;

import br.org.crea.commons.models.commons.dtos.ProtocoloDto;

public class VinculoProtocoloDto {

	private ProtocoloSiacolDto protocoloPai;
	
	private ProtocoloDto protocoloFilho;
	
	private Long idResponsavelVinculo;
	
	private boolean protocoloFilhoFoiImportado;

	public ProtocoloSiacolDto getProtocoloPai() {
		return protocoloPai;
	}

	public void setProtocoloPai(ProtocoloSiacolDto protocoloPai) {
		this.protocoloPai = protocoloPai;
	}

	public ProtocoloDto getProtocoloFilho() {
		return protocoloFilho;
	}

	public void setProtocoloFilho(ProtocoloDto protocoloFilho) {
		this.protocoloFilho = protocoloFilho;
	}

	public Long getIdResponsavelVinculo() {
		return idResponsavelVinculo;
	}

	public void setIdResponsavelVinculo(Long idResponsavelVinculo) {
		this.idResponsavelVinculo = idResponsavelVinculo;
	}

	public boolean protocoloFilhoFoiImportado() {
		return protocoloFilhoFoiImportado;
	}

	public void setProtocoloFilhoFoiImportado(boolean protocoloFilhoFoiImportado) {
		this.protocoloFilhoFoiImportado = protocoloFilhoFoiImportado;
	}
	
}
