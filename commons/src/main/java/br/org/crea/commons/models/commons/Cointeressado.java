package br.org.crea.commons.models.commons;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.crea.commons.models.commons.enuns.TipoCointeressado;
import br.org.crea.commons.models.corporativo.pessoa.Pessoa;

@Entity
@Table(name="PRT_COINTERESSADOS")
@SequenceGenerator(name="COINTERESSADOS_SEQUENCE",sequenceName="PRT_COINTERESSADOS_SEQ",allocationSize = 1)
public class Cointeressado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COINTERESSADOS_SEQUENCE")
	@Column(name="ID")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_ID_PESSOAS")
	private Pessoa pessoa;
	
	@OneToOne
	@JoinColumn(name="FK_ID_PROTOCOLOS")
	private Protocolo protocolo;
	
	@Enumerated(EnumType.ORDINAL)
	private TipoPessoa tipoPessoa;
	
	@Column(name="REQUERENTE")
	private boolean requerente;
	
	@Column(name="MOTIVO")
	private String motivo;
	
	@Column(name="TIPOCOINTERESSADO")
	@Enumerated(EnumType.STRING)
	private TipoCointeressado tipoCointeressado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public boolean isRequerente() {
		return requerente;
	}

	public void setRequerente(boolean requerente) {
		this.requerente = requerente;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public TipoCointeressado getTipoCointeressado() {
		return tipoCointeressado;
	}

	public void setTipoCointeressado(TipoCointeressado tipoCointeressado) {
		this.tipoCointeressado = tipoCointeressado;
	}
	
}
