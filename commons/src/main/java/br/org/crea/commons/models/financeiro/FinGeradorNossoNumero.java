package br.org.crea.commons.models.financeiro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "FIN_NOSSO_NUMERO_SEQUENCIAS")
@SequenceGenerator(name = "FIN_GERADOR_SEQUENCIAS_SEQUENCE", sequenceName = "FIN_GERADOR_NOSSO_NUMERO_SEQ", initialValue = 1, allocationSize = 1)
public class FinGeradorNossoNumero implements Serializable {
	
	private static final long serialVersionUID = -5789513623761319568L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_GERADOR_SEQUENCIAS_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="SEQUENCIAL")
	private Long sequencial;
	
	@Column(name="ANO")
	private String ano;
	
	@Version
	@Column(name="VERSION")
	private Long version;
	
	@Column(name="CONVENIO")
	private Long convenio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSequencial() {
		return sequencial;
	}

	public void setSequencial(Long sequencial) {
		this.sequencial = sequencial;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getConvenio() {
		return convenio;
	}

	public void setConvenio(Long convenio) {
		this.convenio = convenio;
	}

}
