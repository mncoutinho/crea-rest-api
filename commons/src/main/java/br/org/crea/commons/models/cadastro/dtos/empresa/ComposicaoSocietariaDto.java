package br.org.crea.commons.models.cadastro.dtos.empresa;

import java.io.Serializable;

import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

public class ComposicaoSocietariaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 447436375542687314L;

	private Long id;

	private EmpresaDto empresa;
	
	private PessoaDto socio;
	
	private String percentual;

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

	public PessoaDto getSocio() {
		return socio;
	}

	public void setSocio(PessoaDto socio) {
		this.socio = socio;
	}

	public String getPercentual() {
		return percentual;
	}

	public void setPercentual(String percentual) {
		this.percentual = percentual;
	}

	
}
