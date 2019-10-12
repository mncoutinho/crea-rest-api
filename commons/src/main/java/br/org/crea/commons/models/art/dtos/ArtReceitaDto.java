package br.org.crea.commons.models.art.dtos;

import java.util.List;

import br.org.crea.commons.models.commons.dtos.GenericDto;

public class ArtReceitaDto {
	
	private Long id;
	
	private String numeroArt;
	
	private ContratoServicoDto contrato;
	
	private List<ReceitaProdutoDto> produtos;
	
	private String informacoesComplementares;
	
	private GenericDto cultura;
	
	private GenericDto diagnostico;
	
	private Long areaVolumePeso;
	
	private GenericDto unidadeMedida;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public ContratoServicoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoServicoDto contrato) {
		this.contrato = contrato;
	}

	public List<ReceitaProdutoDto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ReceitaProdutoDto> produtos) {
		this.produtos = produtos;
	}

	public String getInformacoesComplementares() {
		return informacoesComplementares;
	}

	public void setInformacoesComplementares(String informacoesComplementares) {
		this.informacoesComplementares = informacoesComplementares;
	}

	public GenericDto getCultura() {
		return cultura;
	}

	public void setCultura(GenericDto cultura) {
		this.cultura = cultura;
	}

	public GenericDto getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(GenericDto diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Long getAreaVolumePeso() {
		return areaVolumePeso;
	}

	public void setAreaVolumePeso(Long areaVolumePeso) {
		this.areaVolumePeso = areaVolumePeso;
	}

	public GenericDto getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(GenericDto unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}	
}
