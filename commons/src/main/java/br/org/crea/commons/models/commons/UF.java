package br.org.crea.commons.models.commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CAD_UFS")
@SequenceGenerator(name = "UFS_SEQUENCE", sequenceName = "CAD_UFS_SEQ",allocationSize = 1)
public class UF {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UFS_SEQUENCE")
	@Column(name = "CODIGO")
	private Long id;

	@Column(name = "UF")
	private String sigla;

	@Column(name = "ATIVO")
	private Boolean ativo;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "BRASIL")
	private int brasil;

	@Column(name = "CEP_INICIAL")	
	private String cepInicial;

	@Column(name = "CEP_FINAL")
	private String cepFinal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBrasil() {
		return brasil;
	}

	public void setBrasil(int brasil) {
		this.brasil = brasil;
	}

	public String getCepInicial() {
		return cepInicial;
	}

	public void setCepInicial(String cepInicial) {
		this.cepInicial = cepInicial;
	}

	public String getCepFinal() {
		return cepFinal;
	}

	public void setCepFinal(String cepFinal) {
		this.cepFinal = cepFinal;
	}

	
}
