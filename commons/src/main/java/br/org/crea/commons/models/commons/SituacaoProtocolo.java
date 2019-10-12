package br.org.crea.commons.models.commons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PRT_SITUACOES")
@SequenceGenerator(name = "sqSituacaoProtocolo", sequenceName = "PRT_SITUACOES_SEQ",allocationSize = 1)
public class SituacaoProtocolo {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSituacaoProtocolo")
	private Long id;
	
	@Column(name = "CODIGO")
	private Long codigo;

	@Column(name = "DESABILITADO")
	private Long desabilitado;

	@Column(name = "SIACOL")
	private Boolean siacol;

	@Column(name = "DESCRICAO")
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

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Boolean getSiacol() {
		return siacol;
	}

	public void setSiacol(Boolean siacol) {
		this.siacol = siacol;
	}

	public Long getDesabilitado() {
		return desabilitado;
	}

	public void setDesabilitado(Long desabilitado) {
		this.desabilitado = desabilitado;
	}

}
