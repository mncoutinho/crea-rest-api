package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_RL_MODALIDADE_DEPARTAMENTO")
@SequenceGenerator(name="sqRlModalidadeDepartamento",sequenceName="SQ_RL_MODALIDADE_DEPARTAMTNO",allocationSize = 1)
public class RlModalidadeDepartamento{

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqRlModalidadeDepartamento")
	private Long id;

	@OneToOne
	@JoinColumn(name="FK_MODALIDADE")
	private Modalidade modalidade;
	
	@OneToOne
	@JoinColumn(name="FK_DEPARTAMENTO")
	private Departamento departamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	
	
}
