package br.org.crea.commons.models.siacol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="SIACOL_PASSIVO")
//@SequenceGenerator(name="sqSiacolProtocolo",sequenceName="SQ_SIACOL_PROTOCOLOS",allocationSize = 1)
public class SiacolPassivo {
	
	@Id
	@Column(name="ID")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqSiacolProtocolo")
	private Long id;
	
	@Column(name="ANO")
	private Long ano;
	
	@Column(name="MES")
	private Long mes;
	
	@Column(name="FK_PROTOCOLO_SIACOL")
	private Long protocoloSiacol;
	
	@Column(name="FK_ASSUNTO_SIACOL")
	private Long assuntoSiacol;
	
	@Column(name="FK_USUARIO")
	private Long usuario;

	@Column(name="ANO_SAIDA")
	private Long anoSaida;
	
	@Column(name="MES_SAIDA")
	private Long mesSaida;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAno() {
		return ano;
	}

	public void setAno(Long ano) {
		this.ano = ano;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public Long getProtocoloSiacol() {
		return protocoloSiacol;
	}

	public void setProtocoloSiacol(Long protocoloSiacol) {
		this.protocoloSiacol = protocoloSiacol;
	}

	public Long getAssuntoSiacol() {
		return assuntoSiacol;
	}

	public void setAssuntoSiacol(Long assuntoSiacol) {
		this.assuntoSiacol = assuntoSiacol;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Long getAnoSaida() {
		return anoSaida;
	}

	public void setAnoSaida(Long anoSaida) {
		this.anoSaida = anoSaida;
	}

	public Long getMesSaida() {
		return mesSaida;
	}

	public void setMesSaida(Long mesSaida) {
		this.mesSaida = mesSaida;
	}

}


