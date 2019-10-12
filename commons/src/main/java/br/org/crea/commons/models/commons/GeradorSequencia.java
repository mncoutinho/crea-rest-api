package br.org.crea.commons.models.commons;

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
@Table(name="PRT_GERADOR_SEQUENCIAS")
@SequenceGenerator(name="GERADOR_SEQUENCIAS_SEQUENCE_PRT",sequenceName="PRT_GERADORSEQUENCIA_SEQ",allocationSize = 1)
public class GeradorSequencia implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GERADOR_SEQUENCIAS_SEQUENCE_PRT")
	@Column(name="CODIGO")
	private Long codigo;
	
	@Column(name="ANO")
	private long ano;
	
	@Column(name="SEQUENCIAL")
	private long sequencial;
	
	@Version
	@Column(name="VERSION")
	private long version;
	
	@Column(name="ID")
	private long tipo;
	
	@Column(name="PARA_NOTIFICACAO")
	private boolean paraNotificacao = false;
	
	
	
	public long getTipo() {
		return tipo;
	}
	public void setTipo(long tipo) {
		this.tipo = tipo;
	}
	
	
	public long getAno() {
		return ano;
	}
	public void setAno(long ano) {
		this.ano = ano;
	}
	
	
	public long getSequencial() {
		return sequencial;
	}
	public void setSequencial(long sequecial) {
		this.sequencial = sequecial;
	}
	
	
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}

	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	

	public boolean isParaNotificacao() {
		return paraNotificacao;
	}
	public void setParaNotificacao(boolean paraNotificacao) {
		this.paraNotificacao = paraNotificacao;
	}

}

