package br.org.crea.commons.models.art;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.cadastro.UnidadeMedida;

@Entity
@Table(name="ART_QUANTIFICACAO")
@SequenceGenerator(name = "sqArtQuantificacao", sequenceName = "ART_QUANTIFICACAO_SEQ", initialValue = 1, allocationSize = 1)
public class ArtQuantificacao implements Serializable {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqArtQuantificacao")
	private Long id;
	
	@Column(name="VALOR")
	private BigDecimal valor;
	
	@OneToOne
	@JoinColumn(name="FK_UNIDADE_MEDIDA")
	private UnidadeMedida unidadeMedida;
	
	@OneToOne
	@JoinColumn(name="FK_CONTRATO_ART")
	private ContratoArt contrato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public ContratoArt getContrato() {
		return contrato;
	}

	public void setContrato(ContratoArt contrato) {
		this.contrato = contrato;
	}

}
