package br.org.crea.commons.models.siacol.dtos;

import br.org.crea.commons.models.cadastro.dtos.empresa.ResponsavelTecnicoDto;

public class ProtocoloSiacolEmpresaDto {
	
	private ResponsavelTecnicoDto responsavelTecnico;
	
	private String nomeEmpresa;
	
	private String numeroArt;

	public ResponsavelTecnicoDto getResponsavelTecnico() {
		return responsavelTecnico;
	}

	public void setResponsavelTecnico(ResponsavelTecnicoDto responsavelTecnico) {
		this.responsavelTecnico = responsavelTecnico;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

}
