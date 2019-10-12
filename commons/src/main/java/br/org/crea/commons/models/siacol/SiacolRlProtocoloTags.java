package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SIACOL_RL_PROTOCOLO_TAGS")
@SequenceGenerator(name="sqSiacolRlTags",sequenceName="SQ_SIACOL_RL_PROTOCOLO_TAGS", initialValue = 1, allocationSize = 1)
public class SiacolRlProtocoloTags {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolRlTags")
	private Long id;
	
	@Column(name="NUMERO_PROTOCOLO")
	private Long numeroProtocolo;
	
	@ManyToOne
	@JoinColumn(name="FK_TAGS")
	private SiacolTags tag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public SiacolTags getTag() {
		return tag;
	}

	public void setTag(SiacolTags tag) {
		this.tag = tag;
	}
	
	
	
}
