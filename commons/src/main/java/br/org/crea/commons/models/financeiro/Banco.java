package br.org.crea.commons.models.financeiro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIN_BANCO")
public class Banco implements Serializable {
	
	private static final long serialVersionUID = 2373689793862443052L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="CODIGO_BANCO")
	private Long codigoBanco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(Long codigoBanco) {
		this.codigoBanco = codigoBanco;
	}


}
