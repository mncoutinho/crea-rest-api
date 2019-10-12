package br.org.crea.commons.models.commons;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_INSTI_ENSINOXCURSOS")
public class Campus implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_INSTITUICAO")
	private InstituicaoEnsinoAtu instituicao;
	
	@Id
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_CURSO")
	private Curso curso;

	public InstituicaoEnsinoAtu getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(InstituicaoEnsinoAtu instituicao) {
		this.instituicao = instituicao;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((instituicao == null) ? 0 : instituicao.hashCode());
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
		Campus other = (Campus) obj;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (instituicao == null) {
			if (other.instituicao != null)
				return false;
		} else if (!instituicao.equals(other.instituicao))
			return false;
		return true;
	}
	
	
	
}
