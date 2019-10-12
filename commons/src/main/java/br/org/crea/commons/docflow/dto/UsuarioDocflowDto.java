package br.org.crea.commons.docflow.dto;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDocflowDto {
	
	private String codigoUsuario;
	
	private String nome;
	
	private String assinante;
	
	private String codigoUnidadePadrao;
	
	private String nomeUnidadePadrao;
	
	private String codigoUnidadeLogada;
	
	private String NomeUnidadeLogada;
	
	private String messageError = null;
	
	public List<DepartamentoDocflowDto> unidades = new ArrayList<DepartamentoDocflowDto>();

	private List<Long> idsUnidadesUsuario = new ArrayList<Long>();
	
	public String getCodigoUsuario() {
		return codigoUsuario;
	}

	public String getNome() {
		return nome;
	}

	public String getAssinante() {
		return assinante;
	}

	public String getCodigoUnidadePadrao() {
		return codigoUnidadePadrao;
	}

	public String getNomeUnidadePadrao() {
		return nomeUnidadePadrao;
	}

	public String getCodigoUnidadeLogada() {
		return codigoUnidadeLogada;
	}

	public String getNomeUnidadeLogada() {
		return NomeUnidadeLogada;
	}

	public String getMessageError() {
		return messageError;
	}

	public List<DepartamentoDocflowDto> getUnidades() {
		return unidades;
	}

	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setAssinante(String assinante) {
		this.assinante = assinante;
	}

	public void setCodigoUnidadePadrao(String codigoUnidadePadrao) {
		this.codigoUnidadePadrao = codigoUnidadePadrao;
	}

	public void setNomeUnidadePadrao(String nomeUnidadePadrao) {
		this.nomeUnidadePadrao = nomeUnidadePadrao;
	}

	public void setCodigoUnidadeLogada(String codigoUnidadeLogada) {
		this.codigoUnidadeLogada = codigoUnidadeLogada;
	}

	public void setNomeUnidadeLogada(String nomeUnidadeLogada) {
		NomeUnidadeLogada = nomeUnidadeLogada;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public void setUnidades(List<DepartamentoDocflowDto> unidades) {
		this.unidades = unidades;
	}

	public List<Long> getIdsUnidadesUsuario() {
		return idsUnidadesUsuario;
	}

	public void setIdsUnidadesUsuario(List<Long> idsUnidadesUsuario) {
		this.idsUnidadesUsuario = idsUnidadesUsuario;
	}

}
