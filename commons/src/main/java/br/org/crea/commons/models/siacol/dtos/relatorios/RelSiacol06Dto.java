package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelSiacol06Dto {
	
	private String mes;
	
	private double entrada;
	
	private List<RelDetalhadoSiacol06Dto> protocolosEntrada;
	
	private RelSiacol06PesoDto pesoEntrada;
	
	private double saida;
	
	private List<RelDetalhadoSiacol06Dto> protocolosSaida;
	
	private RelSiacol06PesoDto pesoSaida;
	
	private double passivo;
	
	private List<RelDetalhadoSiacol06Dto> protocolosPassivo;
	
	private RelSiacol06PesoDto pesoPassivo;
	
	private String percentual;
	
	private double pausado;
	
	private List<RelDetalhadoSiacol06Dto> protocolosPausado;
	
	private RelSiacol06PesoDto pesoPausado;
	
	private double retorno;
	
	private List<RelDetalhadoSiacol06Dto> protocolosRetorno;
	
	private RelSiacol06PesoDto pesoRetorno;

	private double total;
	
	private RelSiacol06PesoDto pesoTotal;

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<RelDetalhadoSiacol06Dto> getProtocolosEntrada() {
		return protocolosEntrada;
	}

	public void setProtocolosEntrada(List<RelDetalhadoSiacol06Dto> protocolosEntrada) {
		this.protocolosEntrada = protocolosEntrada;
	}

	public RelSiacol06PesoDto getPesoEntrada() {
		return pesoEntrada;
	}

	public void setPesoEntrada(RelSiacol06PesoDto pesoEntrada) {
		this.pesoEntrada = pesoEntrada;
	}

	public List<RelDetalhadoSiacol06Dto> getProtocolosSaida() {
		return protocolosSaida;
	}

	public void setProtocolosSaida(List<RelDetalhadoSiacol06Dto> protocolosSaida) {
		this.protocolosSaida = protocolosSaida;
	}

	public RelSiacol06PesoDto getPesoSaida() {
		return pesoSaida;
	}

	public void setPesoSaida(RelSiacol06PesoDto pesoSaida) {
		this.pesoSaida = pesoSaida;
	}

	public List<RelDetalhadoSiacol06Dto> getProtocolosPassivo() {
		return protocolosPassivo;
	}

	public void setProtocolosPassivo(List<RelDetalhadoSiacol06Dto> protocolosPassivo) {
		this.protocolosPassivo = protocolosPassivo;
	}

	public RelSiacol06PesoDto getPesoPassivo() {
		return pesoPassivo;
	}

	public void setPesoPassivo(RelSiacol06PesoDto pesoPassivo) {
		this.pesoPassivo = pesoPassivo;
	}

	public String getPercentual() {
		return percentual;
	}

	public void setPercentual(String percentual) {
		this.percentual = percentual;
	}

	public List<RelDetalhadoSiacol06Dto> getProtocolosPausado() {
		return protocolosPausado;
	}

	public void setProtocolosPausado(List<RelDetalhadoSiacol06Dto> protocolosPausado) {
		this.protocolosPausado = protocolosPausado;
	}

	public RelSiacol06PesoDto getPesoPausado() {
		return pesoPausado;
	}

	public void setPesoPausado(RelSiacol06PesoDto pesoPausado) {
		this.pesoPausado = pesoPausado;
	}

	public List<RelDetalhadoSiacol06Dto> getProtocolosRetorno() {
		return protocolosRetorno;
	}

	public void setProtocolosRetorno(List<RelDetalhadoSiacol06Dto> protocolosRetorno) {
		this.protocolosRetorno = protocolosRetorno;
	}

	public RelSiacol06PesoDto getPesoRetorno() {
		return pesoRetorno;
	}

	public void setPesoRetorno(RelSiacol06PesoDto pesoRetorno) {
		this.pesoRetorno = pesoRetorno;
	}

	public RelSiacol06PesoDto getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(RelSiacol06PesoDto pesoTotal) {
		this.pesoTotal = pesoTotal;
	}

	public double getEntrada() {
		return entrada;
	}

	public void setEntrada(double entrada) {
		this.entrada = entrada;
	}

	public double getSaida() {
		return saida;
	}

	public void setSaida(double saida) {
		this.saida = saida;
	}

	public double getPassivo() {
		return passivo;
	}

	public void setPassivo(double passivo) {
		this.passivo = passivo;
	}

	public double getPausado() {
		return pausado;
	}

	public void setPausado(double pausado) {
		this.pausado = pausado;
	}

	public double getRetorno() {
		return retorno;
	}

	public void setRetorno(double retorno) {
		this.retorno = retorno;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}



