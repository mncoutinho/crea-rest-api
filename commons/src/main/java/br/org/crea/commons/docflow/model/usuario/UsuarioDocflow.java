package br.org.crea.commons.docflow.model.usuario;

import java.util.List;

import br.org.crea.commons.docflow.model.departamento.DepartamentoDocflow;

public class UsuarioDocflow {

	private List<MetadadoUsuarioDocflow> dadosUsuario;

	private List<DepartamentoDocflow> unidades;

	public List<MetadadoUsuarioDocflow> getDadosUsuario() {
		return dadosUsuario;
	}

	public List<DepartamentoDocflow> getUnidades() {
		return unidades;
	}

	public void setDadosUsuario(List<MetadadoUsuarioDocflow> dadosUsuario) {
		this.dadosUsuario = dadosUsuario;
	}

	public void setUnidades(List<DepartamentoDocflow> unidades) {
		this.unidades = unidades;
	}
	
}
