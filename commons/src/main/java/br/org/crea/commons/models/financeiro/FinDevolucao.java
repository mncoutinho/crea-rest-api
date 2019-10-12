package br.org.crea.commons.models.financeiro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIN_DEVOLUCAO")
public class FinDevolucao implements Serializable {
	
	private static final long serialVersionUID = -8070970610480927663L;

	@Id
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="FK_CODIGO_PROTOCOLO")
	private Long idProtocolo;
	
	@Column(name="FK_CODIGO_DIVIDA")
	private Long idDivida;
	
	@Column(name="FK_CODIGO_BOLETO")
	private Long idBoleto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProtocolo() {
		return idProtocolo;
	}

	public void setIdProtocolo(Long idProtocolo) {
		this.idProtocolo = idProtocolo;
	}

	public Long getIdDivida() {
		return idDivida;
	}

	public void setIdDivida(Long idDivida) {
		this.idDivida = idDivida;
	}

	public Long getIdBoleto() {
		return idBoleto;
	}

	public void setIdBoleto(Long idBoleto) {
		this.idBoleto = idBoleto;
	}
	
}
