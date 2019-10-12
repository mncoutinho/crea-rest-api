package br.org.crea.commons.models.commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "ATE_TIPO_ATENDIMENTO")
public class TipoAtendimento {


	@Id
	@Column(name = "CODIGO") 
	private Long codigo;
	
	@Column(name = "DESCRICAO")
	private String descricao;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	
}
