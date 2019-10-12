package br.org.crea.commons.models.art;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name = "LOG_ART_SEQUENCE", sequenceName = "ART_LOG_SEQ", initialValue = 1, allocationSize = 1)
@Table(name="ART_LOG")
public class ArtLog implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4433529491055264918L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_ART_SEQUENCE")
	@Column(name="CODIGO")
	private Long id;
	
	@Column(name="FK_ART")
	private String numeroArt;
	
	@Column(name="FK_TIPO_ACAO")
	private Long tipoAcaoArt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_HORA")
	private Date dataHora;
	
	@Column(name="FK_FUNCIONARIO")
	private Long funcionario;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="IP")
	private String ip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public Long getTipoAcaoArt() {
		return tipoAcaoArt;
	}

	public void setTipoAcaoArt(Long tipoAcaoArt) {
		this.tipoAcaoArt = tipoAcaoArt;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Long getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Long funcionario) {
		this.funcionario = funcionario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


}
