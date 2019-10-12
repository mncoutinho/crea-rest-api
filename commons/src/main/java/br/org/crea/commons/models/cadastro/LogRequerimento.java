package br.org.crea.commons.models.cadastro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CAD_LOG_REQUERIMENTOS")
@SequenceGenerator(name="CAD_LOG_REQUERIMENTOS_SEQUENCE",sequenceName="CAD_LOG_REQUERIMENTOS_SEQ",allocationSize = 1)
public class LogRequerimento  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CAD_LOG_REQUERIMENTOS_SEQUENCE")
	@Column(name="ID")
	private Long id;
	
	@Column(name="IP")
	private String ip;
	
	@Column(name="FK_PESSOA")
	private Long idPessoa;
	
	@Column(name="DATA")
	private Date data;
	
	@Column(name="FK_TIPO_SOLICITACAO")
	private Long idTipoSolicitacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getIdTipoSolicitacao() {
		return idTipoSolicitacao;
	}

	public void setIdTipoSolicitacao(Long idTipoSolicitacao) {
		this.idTipoSolicitacao = idTipoSolicitacao;
	}

}
	
