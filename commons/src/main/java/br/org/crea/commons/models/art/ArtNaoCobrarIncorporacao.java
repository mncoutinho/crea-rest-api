package br.org.crea.commons.models.art;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ART_NAO_COBRAR_INCORPORACAO")
public class ArtNaoCobrarIncorporacao {

	@Id
	@Column(name = "FK_ART")
	private String numeroArt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INCLUSAO")
	private Date dataInclusao;

	@Column(name = "OBSERVACAO")
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	

}
