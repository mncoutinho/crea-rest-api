package br.org.crea.commons.models.art;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.org.crea.commons.models.cadastro.UnidadeMedida;

@Entity
@Table(name="ART_RECEITA_PRODUTO")
public class ArtReceitaProduto {
	
	@EmbeddedId
	private IdReceitaProduto id;
	
	@Column(name="QUANTIDADE")
	private Long quantidade;
	
	@OneToOne
	@JoinColumn(name="FK_UNIDADE_MEDIDA_QUANTIDADE")
	private UnidadeMedida unidadeMedidaQuantidade;
	
	@Column(name="DOSE")
	private Long dose;
	
	@OneToOne
	@JoinColumn(name="FK_UNIDADE_MEDIDA_DOSE")
	private UnidadeMedida unidadeMedidaDose;
	
	@Column(name="INTERVALO_APLICACAO")
	private Long intervaloAplicacao;
	
	@Column(name="PERIODO_CARENCIA")
	private String periodoCarencia;

	@Column(name="PRESCRICAO")
	private String prescricao;

	public IdReceitaProduto getId() {
		return id;
	}

	public void setId(IdReceitaProduto id) {
		this.id = id;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public UnidadeMedida getUnidadeMedidaQuantidade() {
		return unidadeMedidaQuantidade;
	}

	public void setUnidadeMedidaQuantidade(UnidadeMedida unidadeMedidaQuantidade) {
		this.unidadeMedidaQuantidade = unidadeMedidaQuantidade;
	}

	public Long getDose() {
		return dose;
	}

	public void setDose(Long dose) {
		this.dose = dose;
	}

	public UnidadeMedida getUnidadeMedidaDose() {
		return unidadeMedidaDose;
	}

	public void setUnidadeMedidaDose(UnidadeMedida unidadeMedidaDose) {
		this.unidadeMedidaDose = unidadeMedidaDose;
	}

	public Long getIntervaloAplicacao() {
		return intervaloAplicacao;
	}

	public void setIntervaloAplicacao(Long intervaloAplicacao) {
		this.intervaloAplicacao = intervaloAplicacao;
	}

	public String getPeriodoCarencia() {
		return periodoCarencia;
	}

	public void setPeriodoCarencia(String periodoCarencia) {
		this.periodoCarencia = periodoCarencia;
	}

	public String getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(String prescricao) {
		this.prescricao = prescricao;
	}
}
