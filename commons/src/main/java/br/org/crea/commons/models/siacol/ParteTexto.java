package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SIACOL_PARTE_TEXTO")
@SequenceGenerator(name = "sqParteTexto", sequenceName = "SQ_SIACOL_PARTE_TEXTO",allocationSize = 1)
public class ParteTexto {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqParteTexto")
	private Long id;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "ATIVO")
	private Boolean ativo;

	@Column(name = "ORDEM")
	private Long ordem;

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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Long getOrdem() {
		return ordem;
	}

	public void setOrdem(Long ordem) {
		this.ordem = ordem;
	}

}