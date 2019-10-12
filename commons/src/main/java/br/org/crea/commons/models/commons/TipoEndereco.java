package br.org.crea.commons.models.commons;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CAD_TIPOS_ENDERECOS")
@SequenceGenerator(name="TIPOS_ENDERECOS_SEQUENCE",sequenceName="CAD_TIPOS_ENDERECOS_SEQ",allocationSize = 1)
public class TipoEndereco implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOS_ENDERECOS_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="TIPO_CONFEA")
	private String tipoConfea;
	
	@Column(name="VISIVEL")
	private Boolean visivel;
	
	@Column(name="PESSOA_FISICA")
	private Boolean pessoaFisica;
	
	@Column(name="PESSOA_JURIDICA")
	private Boolean pessoaJuridica;
	
	@Column(name="RF")
	private Boolean rf;
	
	


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

	public String getTipoConfea() {
		return tipoConfea;
	}

	public void setTipoConfea(String tipoConfea) {
		this.tipoConfea = tipoConfea;
	}

	public Boolean getVisivel() {
		return visivel;
	}

	public void setVisivel(Boolean visivel) {
		this.visivel = visivel;
	}

	public Boolean getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(Boolean pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public Boolean getPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(Boolean pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	public Boolean getRf() {
		return rf;
	}

	public void setRf(Boolean rf) {
		this.rf = rf;
	}

	@Override
	public String toString() {
		return "TipoEndereco [id=" + id + ", descricao=" + descricao + "]";
	}


	
	

	
	
}