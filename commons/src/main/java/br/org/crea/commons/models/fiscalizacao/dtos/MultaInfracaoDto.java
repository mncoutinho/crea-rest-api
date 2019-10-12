package br.org.crea.commons.models.fiscalizacao.dtos;

import java.math.BigDecimal;

public class MultaInfracaoDto {
	
	private Long codigo;
	
	private String letraFundamento;
	
	private BigDecimal valor;
	
	private BigDecimal valorReincidencia;
	
	private BigDecimal valorMinimo;
	
	private BigDecimal valorMaximo;
	
	private String exercicio;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getLetraFundamento() {
		return letraFundamento;
	}

	public void setLetraFundamento(String letraFundamento) {
		this.letraFundamento = letraFundamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorReincidencia() {
		return valorReincidencia;
	}

	public void setValorReincidencia(BigDecimal valorReincidencia) {
		this.valorReincidencia = valorReincidencia;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public String getExercicio() {
		return exercicio;
	}

	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
	}

	

}
