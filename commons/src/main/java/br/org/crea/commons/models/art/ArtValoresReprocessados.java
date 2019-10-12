package br.org.crea.commons.models.art;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="ART_VALORES_REPROCESSADOS")
public class ArtValoresReprocessados implements Serializable {
   
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="FK_ART")
    private String numeroArt ;
   
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATA_INCLUSAO")
    private Date dataInclusao;

    @Column(name="VALOR_ANTIGO")
    private BigDecimal valorAntigo;
   
    @Column(name="VALOR_NOVO")
    private BigDecimal valorNovo;
   
    @Column(name="INTERVALO")
    private String intervalo;
   
    @Column(name="OBS")
    private String observacao;

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public BigDecimal getValorAntigo() {
		return valorAntigo;
	}

	public void setValorAntigo(BigDecimal valorAntigo) {
		this.valorAntigo = valorAntigo;
	}

	public BigDecimal getValorNovo() {
		return valorNovo;
	}

	public void setValorNovo(BigDecimal valorNovo) {
		this.valorNovo = valorNovo;
	}

	public String getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(String intervalo) {
		this.intervalo = intervalo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
    
    
    
}