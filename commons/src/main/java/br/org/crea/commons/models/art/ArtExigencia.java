package br.org.crea.commons.models.art;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ART_EXIGENCIA")
public class ArtExigencia {
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="EMITE_OFICIO")
	private Boolean emiteOficio;

	@Column(name="EMP")
	private Boolean emp;
	
	@Column(name="OBRIGATORIO")
	private Boolean obrigatorio;
	
	@Column(name="PROF")
	private Boolean prof;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getEmiteOficio() {
		return emiteOficio;
	}

	public void setEmiteOficio(Boolean emiteOficio) {
		this.emiteOficio = emiteOficio;
	}

	public Boolean getEmp() {
		return emp;
	}

	public void setEmp(Boolean emp) {
		this.emp = emp;
	}

	public Boolean getObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(Boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public Boolean getProf() {
		return prof;
	}

	public void setProf(Boolean prof) {
		this.prof = prof;
	}

}
