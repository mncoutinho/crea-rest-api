package br.org.crea.commons.models.cadastro.dtos.profissional;

import java.util.List;

public class ValidaEntidadeClasseDto {

	
	private boolean podeHabilitarEntidadesClasse;
	
	private List<EntidadeProfissionalDto> listaEntidade;

	public boolean isPodeHabilitarEntidadesClasse() {
		return podeHabilitarEntidadesClasse;
	}

	public void setPodeHabilitarEntidadesClasse(boolean podeHabilitarEntidadesClasse) {
		this.podeHabilitarEntidadesClasse = podeHabilitarEntidadesClasse;
	}

	public List<EntidadeProfissionalDto> getListaEntidade() {
		return listaEntidade;
	}

	public void setListaEntidade(List<EntidadeProfissionalDto> listaEntidade) {
		this.listaEntidade = listaEntidade;
	}
}
