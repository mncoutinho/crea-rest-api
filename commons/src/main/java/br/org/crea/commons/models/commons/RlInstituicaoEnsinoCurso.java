package br.org.crea.commons.models.commons;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "CAD_INSTI_ENSINOXCURSOS")
public class RlInstituicaoEnsinoCurso implements Serializable{

    private static final long serialVersionUID = 1L;

    @EmbeddedId
	private RlInstituicaoEnsinoCursoId id;

	public RlInstituicaoEnsinoCursoId getId() {
		return id;
	}

	public void setId(RlInstituicaoEnsinoCursoId id) {
		this.id = id;
	}
	
}
