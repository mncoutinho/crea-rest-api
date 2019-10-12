package br.org.crea.commons.models.cadastro.dtos.profissional;

import java.io.Serializable;

import br.org.crea.commons.models.cadastro.dtos.empresa.EmpresaDto;

public class EntidadeClasseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private EmpresaDto empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmpresaDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDto empresa) {
		this.empresa = empresa;
	}

}

