package br.org.crea.commons.models.cadastro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.financeiro.NaturezaDivida;

@Entity
@Table(name="CAD_TIPO_SOLICITACAO_RPJ")
public class TipoSolicitacaoRPJ {

	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="SIGLA")
	private String sigla;
	
	@Column(name="HABILITADA")
	private Boolean habilitada;
	
	@Column(name="TIPO")
	private Long tipo;
	
	@OneToOne
	@JoinColumn(name="FK_NATUREZA")
	private NaturezaDivida natureza;
	
	@OneToOne
	@JoinColumn(name="FK_ASSUNTO")
	private Assunto assunto;

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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Boolean getHabilitada() {
		return habilitada;
	}

	public void setHabilitada(Boolean habilitada) {
		this.habilitada = habilitada;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public NaturezaDivida getNatureza() {
		return natureza;
	}

	public void setNatureza(NaturezaDivida natureza) {
		this.natureza = natureza;
	}

	public Assunto getAssunto() {
		return assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}
	
}