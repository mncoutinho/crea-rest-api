package br.org.crea.commons.models.commons;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="LOGIN_LOG_SEQUENCE",sequenceName="CAD_LOGIN_LOG_SEQ", allocationSize=1)
@Table(name="CAD_LOGIN_LOG")

public class LoginLog  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1068087153960130460L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOGIN_LOG_SEQUENCE")
	@Column(name="ID")
	private Long id;
	
	@Column(name="LOGIN")
	private Long login;
	
	@Column(name="IP")
	private String ip;
	
	@Column(name="DATA")
	private Calendar data;
	
	@Column(name="DESCRICAO")
	private String descricao;
	
	@Column(name="SUCESSO")
	private Boolean sucesso;
	
	@Column(name="CERTIFICACAO")
	private Boolean certificado;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="CPFCNPJ")
	private String cnpjcpf;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getLogin() {
		return login;
	}
	public void setLogin(Long login) {
		this.login = login;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Boolean getSucesso() {
		return sucesso;
	}
	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}
	
	public Boolean getCertificado() {
		return certificado;
	}
	
	public void setCertificado(Boolean certificado) {
		this.certificado = certificado;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCnpjcpf() {
		return cnpjcpf;
	}
	
	public void setCnpjcpf(String cnpjcpf) {
		this.cnpjcpf = cnpjcpf;
	}

}
