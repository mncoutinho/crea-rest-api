package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SIACOL_TAGS")
@SequenceGenerator(name="sqSiacolTags",sequenceName="SQ_SIACOL_TAGS", initialValue = 1, allocationSize = 1)
public class SiacolTags {
		
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolTags")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;

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
		
}
