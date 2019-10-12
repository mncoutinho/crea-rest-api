package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class RelSiacol02Dto {
	
	private String assunto;
	
	private String assuntoConfea;
	
	private Long codigoAssunto;
	
	private Long codigoAssuntoConfea;
	
	private String descricaoAssunto;
	
	private String descricaoAssuntoConfea;
	
	private int qtdConcedidoRegistroProvisorio;
	
	private int qtdAprovadoRegistroProvisorio;

	private int qtdConcedidoAdReferendum;
	
	private int qtdAprovadoAdReferendum;

	private int qtdTotalADeRP;
	
	private int qtdReuniaoVirtual;
	
	private int qtdReuniaoPresencial;
	
	private int qtdNaoClassificado;

	private int qtdTotalReunioes;
	
	private List<RelDetalhadoSiacol02Dto> protocolosConcedidoProvisorio;
	
	private List<RelDetalhadoSiacol02Dto> protocolosAprovadoProvisorio;
	
	private List<RelDetalhadoSiacol02Dto> protocolosConcedidoAdReferendum;
	
	private List<RelDetalhadoSiacol02Dto> protocolosAprovadoAdReferendum;
	
	private List<RelDetalhadoSiacol02Dto> protocolosReuniaoVirtual;
	
	private List<RelDetalhadoSiacol02Dto> protocolosReuniaoPresencial;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public int getQtdConcedidoRegistroProvisorio() {
		return qtdConcedidoRegistroProvisorio;
	}

	public void setQtdConcedidoRegistroProvisorio(int qtdConcedidoRegistroProvisorio) {
		this.qtdConcedidoRegistroProvisorio = qtdConcedidoRegistroProvisorio;
	}

	public int getQtdAprovadoRegistroProvisorio() {
		return qtdAprovadoRegistroProvisorio;
	}

	public void setQtdAprovadoRegistroProvisorio(int qtdAprovadoRegistroProvisorio) {
		this.qtdAprovadoRegistroProvisorio = qtdAprovadoRegistroProvisorio;
	}

	public int getQtdConcedidoAdReferendum() {
		return qtdConcedidoAdReferendum;
	}

	public void setQtdConcedidoAdReferendum(int qtdConcedidoAdReferendum) {
		this.qtdConcedidoAdReferendum = qtdConcedidoAdReferendum;
	}

	public int getQtdAprovadoAdReferendum() {
		return qtdAprovadoAdReferendum;
	}

	public void setQtdAprovadoAdReferendum(int qtdAprovadoAdReferendum) {
		this.qtdAprovadoAdReferendum = qtdAprovadoAdReferendum;
	}

	public int getQtdTotalADeRP() {
		return qtdTotalADeRP;
	}

	public void setQtdTotalADeRP(int qtdTotalADeRP) {
		this.qtdTotalADeRP = qtdTotalADeRP;
	}

	public int getQtdReuniaoVirtual() {
		return qtdReuniaoVirtual;
	}

	public void setQtdReuniaoVirtual(int qtdReuniaoVirtual) {
		this.qtdReuniaoVirtual = qtdReuniaoVirtual;
	}

	public int getQtdReuniaoPresencial() {
		return qtdReuniaoPresencial;
	}

	public void setQtdReuniaoPresencial(int qtdReuniaoPresencial) {
		this.qtdReuniaoPresencial = qtdReuniaoPresencial;
	}

	public int getQtdTotalReunioes() {
		return qtdTotalReunioes;
	}

	public void setQtdTotalReunioes(int qtdTotalReunioes) {
		this.qtdTotalReunioes = qtdTotalReunioes;
	}

	public List<RelDetalhadoSiacol02Dto> getProtocolosConcedidoProvisorio() {
		return protocolosConcedidoProvisorio;
	}

	public void setProtocolosConcedidoProvisorio(List<RelDetalhadoSiacol02Dto> protocolosConcedidoProvisorio) {
		this.protocolosConcedidoProvisorio = protocolosConcedidoProvisorio;
	}

	public List<RelDetalhadoSiacol02Dto> getProtocolosAprovadoProvisorio() {
		return protocolosAprovadoProvisorio;
	}

	public void setProtocolosAprovadoProvisorio(List<RelDetalhadoSiacol02Dto> protocolosAprovadoProvisorio) {
		this.protocolosAprovadoProvisorio = protocolosAprovadoProvisorio;
	}

	public List<RelDetalhadoSiacol02Dto> getProtocolosConcedidoAdReferendum() {
		return protocolosConcedidoAdReferendum;
	}

	public void setProtocolosConcedidoAdReferendum(List<RelDetalhadoSiacol02Dto> protocolosConcedidoAdReferendum) {
		this.protocolosConcedidoAdReferendum = protocolosConcedidoAdReferendum;
	}

	public List<RelDetalhadoSiacol02Dto> getProtocolosAprovadoAdReferendum() {
		return protocolosAprovadoAdReferendum;
	}

	public void setProtocolosAprovadoAdReferendum(List<RelDetalhadoSiacol02Dto> protocolosAprovadoAdReferendum) {
		this.protocolosAprovadoAdReferendum = protocolosAprovadoAdReferendum;
	}

	public List<RelDetalhadoSiacol02Dto> getProtocolosReuniaoVirtual() {
		return protocolosReuniaoVirtual;
	}

	public void setProtocolosReuniaoVirtual(List<RelDetalhadoSiacol02Dto> protocolosReuniaoVirtual) {
		this.protocolosReuniaoVirtual = protocolosReuniaoVirtual;
	}

	public List<RelDetalhadoSiacol02Dto> getProtocolosReuniaoPresencial() {
		return protocolosReuniaoPresencial;
	}

	public void setProtocolosReuniaoPresencial(List<RelDetalhadoSiacol02Dto> protocolosReuniaoPresencial) {
		this.protocolosReuniaoPresencial = protocolosReuniaoPresencial;
	}

	public String getAssuntoConfea() {
		return assuntoConfea;
	}

	public void setAssuntoConfea(String assuntoConfea) {
		this.assuntoConfea = assuntoConfea;
	}

	public Long getCodigoAssunto() {
		return codigoAssunto;
	}

	public void setCodigoAssunto(Long codigoAssunto) {
		this.codigoAssunto = codigoAssunto;
	}

	public Long getCodigoAssuntoConfea() {
		return codigoAssuntoConfea;
	}

	public void setCodigoAssuntoConfea(Long codigoAssuntoConfea) {
		this.codigoAssuntoConfea = codigoAssuntoConfea;
	}

	public String getDescricaoAssunto() {
		return descricaoAssunto;
	}

	public void setDescricaoAssunto(String descricaoAssunto) {
		this.descricaoAssunto = descricaoAssunto;
	}

	public String getDescricaoAssuntoConfea() {
		return descricaoAssuntoConfea;
	}

	public void setDescricaoAssuntoConfea(String descricaoAssuntoConfea) {
		this.descricaoAssuntoConfea = descricaoAssuntoConfea;
	}

	public int getQtdNaoClassificado() {
		return qtdNaoClassificado;
	}

	public void setQtdNaoClassificado(int qtdNaoClassificado) {
		this.qtdNaoClassificado = qtdNaoClassificado;
	}

}
