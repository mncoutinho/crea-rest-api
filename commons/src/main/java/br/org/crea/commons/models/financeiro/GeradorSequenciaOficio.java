package br.org.crea.commons.models.financeiro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="CAD_GERADOR_SEQUENCIA_OFICIO")
@SequenceGenerator(name="GERADOR_SEQUENCIAS_OFICIO_SEQUENCE",sequenceName="CAD_GERADOR_SEQ_OFICIO_SEQ",allocationSize = 1)
public class GeradorSequenciaOficio {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GERADOR_SEQUENCIAS_OFICIO_SEQUENCE")
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="SIGLA")
	private String sigla;
	
	@Column(name="SEQUENCIAL")
	private long sequencial;
	
	@Column(name="ANO")
	private long ano;
	
	@Version
	@Column(name="VERSION")
	private long version;

	public long getAno() {
		return ano;
	}
	
	public void setAno(long ano) {
		this.ano = ano;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public long getSequencial() {
		return sequencial;
	}
	
	public void setSequencial(long sequencial) {
		this.sequencial = sequencial;
	}

	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public long getVersion() {
		return version;
	}
	
	public void setVersion(long version) {
		this.version = version;
	}

}
