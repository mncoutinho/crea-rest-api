package br.org.crea.commons.models.commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAD_TIPOS_TELEFONES")
public class TipoTelefone {
	@Id
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="PESSOA_FISICA")
	private boolean pessoaFisica;
	
	@Column(name="PESSOA_JURIDICA")
	private boolean pessoaJuridica;

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

	public boolean isPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(boolean pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public boolean isPessoaJuridica() {
		return pessoaJuridica;
	}

	public void setPessoaJuridica(boolean pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
	
	
}
