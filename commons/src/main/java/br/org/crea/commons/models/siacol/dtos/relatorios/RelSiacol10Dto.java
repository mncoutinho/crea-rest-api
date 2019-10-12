package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelSiacol10Dto {
	
	private Long codigoAssunto;
	private String descricaoAssunto;
	
	private RelSiacol10PesoDto pesoDto;

	private int entradaInstrucao;
	private List<RelDetalhadoSiacol10Dto> protocolosEntradaInstrucao;

	private int saidaInstrucao;
	private List<RelDetalhadoSiacol10Dto> protocolosSaidaInstrucao;

	private int acumuladoInstrucao;
	private List<RelDetalhadoSiacol10Dto> protocolosAcumuladoInstrucao;

	private int entradaComissao;
	private List<RelDetalhadoSiacol10Dto> protocolosEntradaComissao;

	private int saidaComissao;
	private List<RelDetalhadoSiacol10Dto> protocolosSaidaComissao;

	private int acumuladoComissao;
	private List<RelDetalhadoSiacol10Dto> protocolosAcumuladoComissao;

	private int entradaCamara;
	private List<RelDetalhadoSiacol10Dto> protocolosEntradaCamara;

	private int saidaCamara;
	private List<RelDetalhadoSiacol10Dto> protocolosSaidaCamara;

	private int acumuladoCamara;
	private List<RelDetalhadoSiacol10Dto> protocolosAcumuladoCamara;

	private int entradaPlenaria;
	private List<RelDetalhadoSiacol10Dto> protocolosEntradaPlenaria;

	private int saidaPlenaria;
	private List<RelDetalhadoSiacol10Dto> protocolosSaidaPlenaria;

	private int acumuladoPlenaria;
	private List<RelDetalhadoSiacol10Dto> protocolosAcumuladoPlenaria;

	/* RELATIVIZADOS */
	private Double totalSaidaRelativoInstrucao;

	private Double totalSaidaRelativoComissao;

	private Double totalSaidaRelativoCamara;

	private Double totalSaidaRelativoPlenaria;
	
	/* TOTAIS */
	private Double totalTrabalhadoRelativo;
	private Double totalSaldoRelativo;
	
	public Long getCodigoAssunto() {
		return codigoAssunto;
	}
	public void setCodigoAssunto(Long codigoAssunto) {
		this.codigoAssunto = codigoAssunto;
	}
	public String getDescricaoAssunto() {
		return descricaoAssunto;
	}
	public void setDescricaoAssunto(String descricaoAssunto) {
		this.descricaoAssunto = descricaoAssunto;
	}
	public int getEntradaInstrucao() {
		return entradaInstrucao;
	}
	public void setEntradaInstrucao(int entradaInstrucao) {
		this.entradaInstrucao = entradaInstrucao;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosEntradaInstrucao() {
		return protocolosEntradaInstrucao;
	}
	public void setProtocolosEntradaInstrucao(List<RelDetalhadoSiacol10Dto> protocolosEntradaInstrucao) {
		this.protocolosEntradaInstrucao = protocolosEntradaInstrucao;
	}
	public int getSaidaInstrucao() {
		return saidaInstrucao;
	}
	public void setSaidaInstrucao(int saidaInstrucao) {
		this.saidaInstrucao = saidaInstrucao;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosSaidaInstrucao() {
		return protocolosSaidaInstrucao;
	}
	public void setProtocolosSaidaInstrucao(List<RelDetalhadoSiacol10Dto> protocolosSaidaInstrucao) {
		this.protocolosSaidaInstrucao = protocolosSaidaInstrucao;
	}
	public int getAcumuladoInstrucao() {
		return acumuladoInstrucao;
	}
	public void setAcumuladoInstrucao(int acumuladoInstrucao) {
		this.acumuladoInstrucao = acumuladoInstrucao;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosAcumuladoInstrucao() {
		return protocolosAcumuladoInstrucao;
	}
	public void setProtocolosAcumuladoInstrucao(List<RelDetalhadoSiacol10Dto> protocolosAcumuladoInstrucao) {
		this.protocolosAcumuladoInstrucao = protocolosAcumuladoInstrucao;
	}
	public int getEntradaComissao() {
		return entradaComissao;
	}
	public void setEntradaComissao(int entradaComissao) {
		this.entradaComissao = entradaComissao;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosEntradaComissao() {
		return protocolosEntradaComissao;
	}
	public void setProtocolosEntradaComissao(List<RelDetalhadoSiacol10Dto> protocolosEntradaComissao) {
		this.protocolosEntradaComissao = protocolosEntradaComissao;
	}
	public int getSaidaComissao() {
		return saidaComissao;
	}
	public void setSaidaComissao(int saidaComissao) {
		this.saidaComissao = saidaComissao;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosSaidaComissao() {
		return protocolosSaidaComissao;
	}
	public void setProtocolosSaidaComissao(List<RelDetalhadoSiacol10Dto> protocolosSaidaComissao) {
		this.protocolosSaidaComissao = protocolosSaidaComissao;
	}
	public int getAcumuladoComissao() {
		return acumuladoComissao;
	}
	public void setAcumuladoComissao(int acumuladoComissao) {
		this.acumuladoComissao = acumuladoComissao;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosAcumuladoComissao() {
		return protocolosAcumuladoComissao;
	}
	public void setProtocolosAcumuladoComissao(List<RelDetalhadoSiacol10Dto> protocolosAcumuladoComissao) {
		this.protocolosAcumuladoComissao = protocolosAcumuladoComissao;
	}
	public int getEntradaCamara() {
		return entradaCamara;
	}
	public void setEntradaCamara(int entradaCamara) {
		this.entradaCamara = entradaCamara;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosEntradaCamara() {
		return protocolosEntradaCamara;
	}
	public void setProtocolosEntradaCamara(List<RelDetalhadoSiacol10Dto> protocolosEntradaCamara) {
		this.protocolosEntradaCamara = protocolosEntradaCamara;
	}
	public int getSaidaCamara() {
		return saidaCamara;
	}
	public void setSaidaCamara(int saidaCamara) {
		this.saidaCamara = saidaCamara;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosSaidaCamara() {
		return protocolosSaidaCamara;
	}
	public void setProtocolosSaidaCamara(List<RelDetalhadoSiacol10Dto> protocolosSaidaCamara) {
		this.protocolosSaidaCamara = protocolosSaidaCamara;
	}
	public int getAcumuladoCamara() {
		return acumuladoCamara;
	}
	public void setAcumuladoCamara(int acumuladoCamara) {
		this.acumuladoCamara = acumuladoCamara;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosAcumuladoCamara() {
		return protocolosAcumuladoCamara;
	}
	public void setProtocolosAcumuladoCamara(List<RelDetalhadoSiacol10Dto> protocolosAcumuladoCamara) {
		this.protocolosAcumuladoCamara = protocolosAcumuladoCamara;
	}
	public int getEntradaPlenaria() {
		return entradaPlenaria;
	}
	public void setEntradaPlenaria(int entradaPlenaria) {
		this.entradaPlenaria = entradaPlenaria;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosEntradaPlenaria() {
		return protocolosEntradaPlenaria;
	}
	public void setProtocolosEntradaPlenaria(List<RelDetalhadoSiacol10Dto> protocolosEntradaPlenaria) {
		this.protocolosEntradaPlenaria = protocolosEntradaPlenaria;
	}
	public int getSaidaPlenaria() {
		return saidaPlenaria;
	}
	public void setSaidaPlenaria(int saidaPlenaria) {
		this.saidaPlenaria = saidaPlenaria;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosSaidaPlenaria() {
		return protocolosSaidaPlenaria;
	}
	public void setProtocolosSaidaPlenaria(List<RelDetalhadoSiacol10Dto> protocolosSaidaPlenaria) {
		this.protocolosSaidaPlenaria = protocolosSaidaPlenaria;
	}
	public int getAcumuladoPlenaria() {
		return acumuladoPlenaria;
	}
	public void setAcumuladoPlenaria(int acumuladoPlenaria) {
		this.acumuladoPlenaria = acumuladoPlenaria;
	}
	public List<RelDetalhadoSiacol10Dto> getProtocolosAcumuladoPlenaria() {
		return protocolosAcumuladoPlenaria;
	}
	public void setProtocolosAcumuladoPlenaria(List<RelDetalhadoSiacol10Dto> protocolosAcumuladoPlenaria) {
		this.protocolosAcumuladoPlenaria = protocolosAcumuladoPlenaria;
	}
	public Double getTotalSaidaRelativoInstrucao() {
		return totalSaidaRelativoInstrucao;
	}
	public void setTotalSaidaRelativoInstrucao(Double totalSaidaRelativoInstrucao) {
		this.totalSaidaRelativoInstrucao = totalSaidaRelativoInstrucao;
	}
	public Double getTotalSaidaRelativoComissao() {
		return totalSaidaRelativoComissao;
	}
	public void setTotalSaidaRelativoComissao(Double totalSaidaRelativoComissao) {
		this.totalSaidaRelativoComissao = totalSaidaRelativoComissao;
	}
	public Double getTotalSaidaRelativoCamara() {
		return totalSaidaRelativoCamara;
	}
	public void setTotalSaidaRelativoCamara(Double totalSaidaRelativoCamara) {
		this.totalSaidaRelativoCamara = totalSaidaRelativoCamara;
	}
	public Double getTotalSaidaRelativoPlenaria() {
		return totalSaidaRelativoPlenaria;
	}
	public void setTotalSaidaRelativoPlenaria(Double totalSaidaRelativoPlenaria) {
		this.totalSaidaRelativoPlenaria = totalSaidaRelativoPlenaria;
	}
	public Double getTotalTrabalhadoRelativo() {
		return totalTrabalhadoRelativo;
	}
	public void setTotalTrabalhadoRelativo(Double totalTrabalhadoRelativo) {
		this.totalTrabalhadoRelativo = totalTrabalhadoRelativo;
	}
	public Double getTotalSaldoRelativo() {
		return totalSaldoRelativo;
	}
	public void setTotalSaldoRelativo(Double totalSaldoRelativo) {
		this.totalSaldoRelativo = totalSaldoRelativo;
	}
	public RelSiacol10PesoDto getPesoDto() {
		return pesoDto;
	}
	public void setPesoDto(RelSiacol10PesoDto pesoDto) {
		this.pesoDto = pesoDto;
	}
}
