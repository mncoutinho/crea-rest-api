package br.org.crea.commons.models.siacol;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.crea.commons.models.commons.Arquivo;

@Entity
@Table(name="SIACOL_PROTOCOLO_EXIGENCIA")
@SequenceGenerator(name="sqSiacolProtocoloExigencia",sequenceName="SQ_SIACOL_PROTOCOLO_EXIGENCIA",allocationSize = 1)
public class SiacolProtocoloExigencia {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolProtocoloExigencia")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_PROTOCOLO_SIACOL")
	private ProtocoloSiacol protocolo;
	
	@Column(name="MOTIVO")
	private String motivo;
	
	@Column(name="TIPO_CONTATO")
	private String tipoContato;
	
	@Column(name="PESSOA_CONTATO")
	private String pessoaContato;
	
	@Column(name="DT_CONTATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataContato;
	
	@Column(name="DT_INICIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Column(name="DT_FIM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
	
	@OneToOne
	@JoinColumn(name="FK_ARQUIVO")
	private Arquivo arquivo;
	
	@Column(name="DESCRICAO")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProtocoloSiacol getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(ProtocoloSiacol protocolo) {
		this.protocolo = protocolo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(String tipoContato) {
		this.tipoContato = tipoContato;
	}

	public String getPessoaContato() {
		return pessoaContato;
	}

	public void setPessoaContato(String pessoaContato) {
		this.pessoaContato = pessoaContato;
	}

	public Date getDataContato() {
		return dataContato;
	}

	public void setDataContato(Date dataContato) {
		this.dataContato = dataContato;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	
}


