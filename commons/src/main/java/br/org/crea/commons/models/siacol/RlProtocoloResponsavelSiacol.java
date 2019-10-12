
package br.org.crea.commons.models.siacol;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.commons.Protocolo;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="SIACOL_PROTOCOLO_RESPONSAVEL")
@SequenceGenerator(name = "sqSiacolRlProtocoloResponsavel", sequenceName = "SQ_RL_PROTOCOLO_RESPONSAVEL", initialValue = 1, allocationSize = 1)
public class RlProtocoloResponsavelSiacol implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolRlProtocoloResponsavel")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_PROTOCOLO_PAI")
	private ProtocoloSiacol protocoloPai;
	
	@OneToOne
	@JoinColumn(name="FK_PROTOCOLO_FILHO")
	private Protocolo protocoloFilho;
	
	@OneToOne
	@JoinColumn(name = "FK_RESPONSAVEL")
	private Pessoa responsavel;

	@Column(name = "IMPORTADO")
	private Boolean foiImportadoCorporativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProtocoloSiacol getProtocoloPai() {
		return protocoloPai;
	}

	public void setProtocoloPai(ProtocoloSiacol protocoloPai) {
		this.protocoloPai = protocoloPai;
	}

	public Protocolo getProtocoloFilho() {
		return protocoloFilho;
	}

	public void setProtocoloFilho(Protocolo protocoloFilho) {
		this.protocoloFilho = protocoloFilho;
	}

	public Pessoa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoa pessoa) {
		this.responsavel = pessoa;
	}

	public Boolean foiImportadoCorporativo() {
		return foiImportadoCorporativo;
	}

	public void setFoiImportadoCorporativo(Boolean foiImportadoCorporativo) {
		this.foiImportadoCorporativo = foiImportadoCorporativo;
	}
	
}
