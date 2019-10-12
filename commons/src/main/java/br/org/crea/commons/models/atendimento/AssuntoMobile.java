package br.org.crea.commons.models.atendimento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.SituacaoRegistro;
import br.org.crea.commons.models.corporativo.enuns.TipoPessoa;


@Entity
@Table(name = "MOB_ASSUNTOS")
public class AssuntoMobile {
	
	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPOPESSOA")
	private TipoPessoa tipoPessoa;
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_SITUACAO_REGISTRO")
	private SituacaoRegistro situacao;
	
	@OneToOne
	@JoinColumn(name="FK_ID_ASSUNTO")
	private Assunto assunto;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public SituacaoRegistro getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoRegistro situacao) {
		this.situacao = situacao;
	}

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}

	@Override
	public String toString() {
		return "AssuntoMobile [id=" + id + ", tipoPessoa=" + tipoPessoa + ", situacao=" + situacao + ", assunto=" + assunto + "]";
	}
	
	
	
	

}
