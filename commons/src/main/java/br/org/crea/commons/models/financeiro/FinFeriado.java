package br.org.crea.commons.models.financeiro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="FIN_FERIADO")
public class FinFeriado implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7483933590232594727L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA")
	private Date data;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INCLUSAO")
	private Date dataInclusao;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_ALTERACAO")
	private Date dataAlteracao;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="HONORARIOS")
	private Boolean ativo;
	
	@Column(name="PERIODICIDADE")
	private Long periodicidade;
	
	@Column(name="FK_CODIGO_USUARIO_INCLUSAO")
	private Long usuarioInclusao;
	
	@Column(name="FK_CODIGO_USUARIO_ALTERACAO")
	private Long usuarioAlteracao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
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

	public Long getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(Long periodicidade) {
		this.periodicidade = periodicidade;
	}

	public Long getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(Long usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	public Long getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Long usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
		
}
