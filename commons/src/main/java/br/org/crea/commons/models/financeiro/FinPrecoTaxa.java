package br.org.crea.commons.models.financeiro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FIN_PRECO_TAXA")
@SequenceGenerator(name = "PRECO_TAXA_SEQUENCE", sequenceName = "FIN_PRECO_TAXA_SEQ", initialValue = 1, allocationSize = 1)
public class FinPrecoTaxa implements Serializable {
	
	private static final long serialVersionUID = -2032679237662923359L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRECO_TAXA_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
