package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="CAD_TIPOS_CERTIDOES")
public class TipoCertidao{

	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="PARA_ART")
	private Boolean paraART;
	
	
	public Boolean getParaART() {
		return paraART;
	}
	public void setParaART(Boolean paraART) {
		this.paraART = paraART;
	}

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
