package br.org.crea.commons.models.siacol.dtos.relatorios;

import java.util.List;

public class StatusRel07 {

	private Long id;

	private String nome;

	private int min;

	private int max;

	private int media;
	
	private int modal;

	private int desv;

	private int qtd;
	
	private List<ProtocolosRel07Dto> listaProtocolos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getModal() {
		return modal;
	}

	public void setModal(int modal) {
		this.modal = modal;
	}

	public int getDesv() {
		return desv;
	}

	public void setDesv(int desv) {
		this.desv = desv;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public List<ProtocolosRel07Dto> getListaProtocolos() {
		return listaProtocolos;
	}

	public void setListaProtocolos(List<ProtocolosRel07Dto> listaProtocolos) {
		this.listaProtocolos = listaProtocolos;
	}

	public int getMedia() {
		return media;
	}

	public void setMedia(int media) {
		this.media = media;
	}
	
}
