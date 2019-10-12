package br.org.crea.commons.models.commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CAD_LOGRADOUROS")
public class Logradouro{
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_TIPOS_LOGRADOURO")
	private TipoLogradouro tipoLogradouro;
	
	@ManyToOne
	@JoinColumn(name="FK_CODIGO_LOCALIDADES")
	private Localidade localidade;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_UFS")
	private UF uf;
	
	@Column(name="CEP")
	private String cep;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_BAIRROS")
	private Bairro bairro;
	
	@Column(name="NUM_INICIAL")
	private String numInicio;
	
	@Column(name="NUM_FINAL")
	private String numFinal;
	
	@Column(name="PAR")
	private boolean par;
	
	@Column(name="IMPAR")
	private boolean impar;

	public Long getId() {
		return id;
	}

	public TipoLogradouro getTipoLogradouro() {
		return tipoLogradouro;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public UF getUf() {
		return uf;
	}

	public String getCep() {
		return cep;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public String getNumInicio() {
		return numInicio;
	}

	public String getNumFinal() {
		return numFinal;
	}

	public boolean isPar() {
		return par;
	}

	public boolean isImpar() {
		return impar;
	}

	public boolean temBairro() {
		return this.bairro != null;
	}
}