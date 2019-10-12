package br.org.crea.commons.models.protocolo;

import java.io.Serializable;
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

import br.org.crea.commons.models.atendimento.OuvidoriaTipoDemanda;

@Entity
@Table(name="PRT_DEMANDA")
@SequenceGenerator(name="sqProtocoloDemanda",sequenceName="SQ_PRT_DEMANDA",allocationSize = 1)
public class ProtocoloDemanda implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqProtocoloDemanda")
	private Long id;

	@Column(name="FK_PROTOCOLO_FILHO")
	private Long protocoloFilho;
	
	@Column(name="FK_PROTOCOLO_PAI")
	private Long protocoloPai;
	
	@OneToOne
	@JoinColumn(name="FK_TIPO_DEMANDA")
	private OuvidoriaTipoDemanda tipoDemanda;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_REGISTRO")
	private Date dataRegistro;
	
	@Column(name="FK_USUARIO_ATENDIMENTO")
	private Long idUsuarioAtentimendo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProtocoloFilho() {
		return protocoloFilho;
	}

	public void setProtocoloFilho(Long protocoloFilho) {
		this.protocoloFilho = protocoloFilho;
	}

	public Long getProtocoloPai() {
		return protocoloPai;
	}

	public void setProtocoloPai(Long protocoloPai) {
		this.protocoloPai = protocoloPai;
	}

//	public TipoDemanda getTipoDemanda() {
//		return tipoDemanda;
//	}
//
//	public void setTipoDemanda(TipoDemanda tipoDemanda) {
//		this.tipoDemanda = tipoDemanda;
//	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Long getIdUsuarioAtentimendo() {
		return idUsuarioAtentimendo;
	}

	public void setIdUsuarioAtentimendo(Long idUsuarioAtentimendo) {
		this.idUsuarioAtentimendo = idUsuarioAtentimendo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
