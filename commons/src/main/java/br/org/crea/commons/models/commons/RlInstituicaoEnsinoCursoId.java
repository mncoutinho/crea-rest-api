package br.org.crea.commons.models.commons;

import java.io.Serializable;

import javax.persistence.JoinColumn;

public class RlInstituicaoEnsinoCursoId implements Serializable {

	private static final long serialVersionUID = 1L;

	@JoinColumn(name="FK_CODIGO_INSTITUICAO")
	private InstituicaoEnsinoAtu instituicaoEnsino;
	
	@JoinColumn(name="FK_CODIGO_CURSO")
	private Curso curso;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((instituicaoEnsino == null) ? 0 : instituicaoEnsino.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RlInstituicaoEnsinoCursoId other = (RlInstituicaoEnsinoCursoId) obj;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (instituicaoEnsino == null) {
			if (other.instituicaoEnsino != null)
				return false;
		} else if (!instituicaoEnsino.equals(other.instituicaoEnsino))
			return false;
		return true;
	}
	
}
