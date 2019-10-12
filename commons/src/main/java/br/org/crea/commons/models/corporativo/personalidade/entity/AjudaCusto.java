package br.org.crea.commons.models.corporativo.personalidade.entity;

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

import br.org.crea.commons.models.commons.Localidade;

@Entity
@Table(name="PER_AJUDA_CUSTO")
@SequenceGenerator(name="AJUDA_CUSTO_SEQUENCE", sequenceName="PER_AJUDA_CUSTO_SEQ",allocationSize = 1)
public class AjudaCusto {

	
	private Long id;
	private String descricao;
	private boolean removido;
	private Localidade localidade;
	private Integer distancia;
	private String valor;
	private BigDecimal diaria;
	private BigDecimal jeton;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AJUDA_CUSTO_SEQUENCE")
	@Column(name="CODIGO")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="DESCRICAO")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
			
	@Column(name="REMOVIDO")
	public boolean isRemovido() {
		return removido;
	}
	public void setRemovido(boolean removido) {
		this.removido = removido;
	}
	
	@OneToOne
	@JoinColumn(name="FK_CODIGO_LOCALIDADE")
	public Localidade getLocalidade() {
		return localidade;
	}
	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}
	
	@Column(name="DISTANCIA")
	public Integer getDistancia() {
		return distancia;
	}
	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}

	@Column(name="VALOR")
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Column(name="DIARIA")
	public BigDecimal getDiaria() {
		return diaria;
	}
	public void setDiaria(BigDecimal diaria) {
		this.diaria = diaria;
	}
	
	@Column(name="JETON")
	public BigDecimal getJeton() {
		return jeton;
	}
	public void setJeton(BigDecimal jeton) {
		this.jeton = jeton;
	}
		
}