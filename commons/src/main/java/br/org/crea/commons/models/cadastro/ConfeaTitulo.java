package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAD_CONFEA_TITULOS")
public class ConfeaTitulo  {
	
	@Id
	@Column(name="CODIGO")
	private Long id;

	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="DESCRICAO_FEMININO")
	private String descricaoFeminino;
	
	@Column(name="ABREVIATURA")
	private String abreviatura;
	
	@Column(name="ATIVO")
	private int ativo;

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

	public String getDescricaoFeminino() {
		return descricaoFeminino;
	}

	public void setDescricaoFeminino(String descricaoFeminino) {
		this.descricaoFeminino = descricaoFeminino;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	
	

}
