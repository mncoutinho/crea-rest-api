package br.org.crea.commons.models.cadastro.dtos;

import br.org.crea.commons.models.commons.dtos.TipoDocumentoDto;

public class NumeroDocumentoDto {

	private Long id;

	private TipoDocumentoDto tipo;
	
	private Long numero;
	
	private String ano;
	
	private boolean tem_ano;

	private DepartamentoDto departamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoDocumentoDto getTipo() {
		return tipo;
	}

	public void setTipo(TipoDocumentoDto tipo) {
		this.tipo = tipo;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public boolean isTem_ano() {
		return tem_ano;
	}

	public void setTem_ano(boolean tem_ano) {
		this.tem_ano = tem_ano;
	}

	public DepartamentoDto getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoDto departamento) {
		this.departamento = departamento;
	}

	public boolean temId() {
		return this.id != null;

	}

		
	
}
