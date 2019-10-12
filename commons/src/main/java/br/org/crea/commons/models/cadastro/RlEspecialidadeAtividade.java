package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAD_RAMOATIVIDADE_ESPEC")
public class RlEspecialidadeAtividade {

	@Id
	@Column(name="FK_CODIGO_ESPECIALIDADE")
	private Especialidade especialidade;
	
	@Column(name="FK_ATIVIDADE")
	private Long atividade;
	
	@Column(name="STATUS")
	private Long status;
	
	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Long getAtividade() {
		return atividade;
	}

	public void setAtividade(Long atividade) {
		this.atividade = atividade;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	
	
}
