package br.org.crea.commons.models.corporativo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="CAD_SITUACAO_REGISTRO")
public class SituacaoRegistro {
	
	
	@Id
	@Column(name="CODIGO")
	private Long   id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="DESCRICAO_PUBLICA")
	private String descricaoPublica;
	
	@Column(name="REGISTRO_ATIVO")
	private Boolean registroAtivo;

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

	public String getDescricaoPublica() {
		return descricaoPublica;
	}

	public void setDescricaoPublica(String descricaoPublica) {
		this.descricaoPublica = descricaoPublica;
	}

	public Boolean getRegistroAtivo() {
		return registroAtivo;
	}

	public void setRegistroAtivo(Boolean registroAtivo) {
		this.registroAtivo = registroAtivo;
	}
	
}
