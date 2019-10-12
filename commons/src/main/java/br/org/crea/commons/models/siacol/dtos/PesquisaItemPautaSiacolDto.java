package br.org.crea.commons.models.siacol.dtos;

import java.util.List;

public class PesquisaItemPautaSiacolDto {
	
    private List<Long> idsPautas;
	
    private Long protocolo;
	
	private Long idPessoaDestaque;
	
	private Long idPessoaVista;
	
	private String item;
	
	private Long status;
	
	private boolean emVotacao;
	
	private boolean somenteProtocolos;
	
	private Boolean somenteItensSemProtocolos;
	
	private boolean temSolicitacaoVistas;
	
	private Boolean temVistasConcedida;
	
	private boolean temObservacao;
	
	private boolean temEnquete;
	
	private boolean temUrgencia;

	public List<Long> getIdsPautas() {
		return idsPautas;
	}

	public void setIdsPautas(List<Long> idsPautas) {
		this.idsPautas = idsPautas;
	}

	public Long getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Long protocolo) {
		this.protocolo = protocolo;
	}

	public Long getIdPessoaDestaque() {
		return idPessoaDestaque;
	}

	public void setIdPessoaDestaque(Long idPessoaDestaque) {
		this.idPessoaDestaque = idPessoaDestaque;
	}

	public Long getIdPessoaVista() {
		return idPessoaVista;
	}

	public void setIdPessoaVista(Long idPessoaVista) {
		this.idPessoaVista = idPessoaVista;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public boolean isSomenteProtocolos() {
		return somenteProtocolos;
	}

	public void setSomenteProtocolos(boolean somenteProtocolos) {
		this.somenteProtocolos = somenteProtocolos;
	}

	public boolean isTemSolicitacaoVistas() {
		return temSolicitacaoVistas;
	}

	public void setTemSolicitacaoVistas(boolean temSolicitacaoVistas) {
		this.temSolicitacaoVistas = temSolicitacaoVistas;
	}

	public boolean isEmVotacao() {
		return emVotacao;
	}

	public void setEmVotacao(boolean emVotacao) {
		this.emVotacao = emVotacao;
	}

	public boolean isTemObservacao() {
		return temObservacao;
	}

	public void setTemObservacao(boolean temObservacao) {
		this.temObservacao = temObservacao;
	}

	public boolean isTemEnquete() {
		return temEnquete;
	}

	public void setTemEnquete(boolean temEnquete) {
		this.temEnquete = temEnquete;
	}

	public Boolean isTemVistasConcedida() {
		return temVistasConcedida;
	}

	public void setTemVistasConcedida(Boolean temVistasConcedida) {
		this.temVistasConcedida = temVistasConcedida;
	}

	public boolean isTemUrgencia() {
		return temUrgencia;
	}

	public void setTemUrgencia(boolean temUrgencia) {
		this.temUrgencia = temUrgencia;
	}

	public Boolean getTemVistasConcedida() {
		return temVistasConcedida;
	}

	public Boolean isSomenteItensSemProtocolos() {
		return somenteItensSemProtocolos;
	}

	public void setSomenteItensSemProtocolos(Boolean somenteItensSemProtocolos) {
		this.somenteItensSemProtocolos = somenteItensSemProtocolos;
	}
	
	

	


}
