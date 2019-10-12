package br.org.crea.commons.models.cadastro;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FIN_PRECO_TAXA")
public class Taxa {

	@Id
	@Column(name="CODIGO")
	private Long id;

	@ManyToOne
	@JoinColumn(name="FK_CODIGO_NATUREZA")
	private Natureza natureza;

	@Column(name="EXERCICIO")
	private String exercicio;
	
	@Column(name="VALOR")
	private BigDecimal valor;
	
	@Column(name="ATIVO")
	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Natureza getNatureza() {
		return natureza;
	}

	public void setNatureza(Natureza natureza) {
		this.natureza = natureza;
	}

	public String getExercicio() {
		return exercicio;
	}

	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	


	

}
