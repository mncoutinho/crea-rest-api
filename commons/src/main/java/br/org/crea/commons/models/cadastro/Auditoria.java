package br.org.crea.commons.models.cadastro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "CAD_AUDITORIA")
@SequenceGenerator(name = "sqAuditoria", sequenceName = "SQ_CAD_AUDITORIA", initialValue = 1, allocationSize = 1)
public class Auditoria {
	

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sqAuditoria")
	private Long id;
	
	@Column(name="IP")
	private String ip;
	
	@Column(name="MODULO")
	private Long modulo;

	@Column(name="EVENTO")
	private Long evento;
	
	/** USUARIO LOGADO NO SISTEMA **/
	@Column(name="ID_USUARIO")
	private Long idUsuario;
	
	@Column(name="REGISTRO_USUARIO")
	private String registroUsuario;

	@Column(name="PERFIL_USUARIO")
	private String perfilUsuario;
	
	@Column(name="NOME_USUARIO")
	private String nomeUsuario;
	
	
	/** USUARIO QUE SOFRE A ACAO **/
	@Column(name="ID_PESSOA_ACAO")
	private Long idRemetente;
	
	@Column(name="PERFIL_PESSOA_ACAO")
	private String perfilRemetente;
	
	@Column(name="REGISTRO_PESSOA_ACAO")
	private String registroRemetente;
	
	@Column(name="NOME_PESSOA_ACAO")
	private String nomeRemetente;
	
	
	/** USUARIO QUE FOI DESTINADO **/
	@Column(name="ID_USUARIO_DESTINATARIO")
	private Long idDestinatario;
	
	@Column(name="PERFIL_USUARIO_DESTINATARIO")
	private String perfilDestinatario;
	
	@Column(name="REGISTRO_USUARIO_DESTINATARIO")
	private String registroDestinatario;
	
	@Column(name="NOME_USUARIO_DESTINATARIO")
	private String nomeDestinatario;
	
	@Column(name="ID_DEPARTAMENTO_DESTINO")
	private Long idDepartamentoDestino;
	
	@Column(name="ID_DEPARTAMENTO_ORIGEM")
	private Long idDepartamentoOrigem;
	
	@Column(name="NUMERO")
	private String numero;

	@Column(name="TEXTO_AUDITORIA")
	private String textoAuditoria;
	
	@Lob
	@Column(name="DTO_ANTIGO")
	private String dtoAntigo;

	@Lob
	@Column(name="DTO_NOVO")
	private String dtoNovo;
	
	@Column(name="DTO_CLASS")
	private String dtoClass;
	
	@Column(name="DT_CREATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;
	
	@Column(name="ACAO")
	private String acao;
	
	@Column(name="IS_ERROR")
	private boolean isError;
	
	@Column(name="STATUS_ANTIGO")
	private String statusAntigo;

	@Column(name="STATUS_NOVO")
	private String statusNovo;
	
	@Column(name="NUMERO_REFERENCIA")
	private String numeroReferencia;
	

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

	public Long getModulo() {
		return modulo;
	}

	public void setModulo(Long modulo) {
		this.modulo = modulo;
	}

	public Long getEvento() {
		return evento;
	}

	public void setEvento(Long evento) {
		this.evento = evento;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getRegistroUsuario() {
		return registroUsuario;
	}

	public void setRegistroUsuario(String registroUsuario) {
		this.registroUsuario = registroUsuario;
	}

	public String getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(String perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Long getIdRemetente() {
		return idRemetente;
	}

	public void setIdRemetente(Long idRemetente) {
		this.idRemetente = idRemetente;
	}

	public String getPerfilRemetente() {
		return perfilRemetente;
	}

	public void setPerfilRemetente(String perfilRemetente) {
		this.perfilRemetente = perfilRemetente;
	}

	public String getRegistroRemetente() {
		return registroRemetente;
	}

	public void setRegistroRemetente(String registroRemetente) {
		this.registroRemetente = registroRemetente;
	}

	public String getNomeRemetente() {
		return nomeRemetente;
	}

	public void setNomeRemetente(String nomeRemetente) {
		this.nomeRemetente = nomeRemetente;
	}

	public Long getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(Long idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getPerfilDestinatario() {
		return perfilDestinatario;
	}

	public void setPerfilDestinatario(String perfilDestinatario) {
		this.perfilDestinatario = perfilDestinatario;
	}

	public String getRegistroDestinatario() {
		return registroDestinatario;
	}

	public void setRegistroDestinatario(String registroDestinatario) {
		this.registroDestinatario = registroDestinatario;
	}

	public String getNomeDestinatario() {
		return nomeDestinatario;
	}

	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTextoAuditoria() {
		return textoAuditoria;
	}

	public void setTextoAuditoria(String textoAuditoria) {
		this.textoAuditoria = textoAuditoria;
	}

	public String getDtoAntigo() {
		return dtoAntigo;
	}

	public void setDtoAntigo(String dtoAntigo) {
		this.dtoAntigo = dtoAntigo;
	}

	public String getDtoNovo() {
		return dtoNovo;
	}

	public void setDtoNovo(String dtoNovo) {
		this.dtoNovo = dtoNovo;
	}

	public String getDtoClass() {
		return dtoClass;
	}

	public void setDtoClass(String dtoClass) {
		this.dtoClass = dtoClass;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public boolean temDtoNovo() {
		return this.dtoNovo != null ? true : false;
	}

	public boolean temDtoAntigo() {
		return this.dtoAntigo != null ? true : false;
	}

	public Long getIdDepartamentoDestino() {
		return idDepartamentoDestino;
	}

	public void setIdDepartamentoDestino(Long idDepartamento) {
		this.idDepartamentoDestino = idDepartamento;
	}

	public Long getIdDepartamentoOrigem() {
		return idDepartamentoOrigem;
	}

	public void setIdDepartamentoOrigem(Long idDepartamentoOrigem) {
		this.idDepartamentoOrigem = idDepartamentoOrigem;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getStatusAntigo() {
		return statusAntigo;
	}

	public void setStatusAntigo(String statusAntigo) {
		this.statusAntigo = statusAntigo;
	}

	public String getStatusNovo() {
		return statusNovo;
	}

	public void setStatusNovo(String statusNovo) {
		this.statusNovo = statusNovo;
	}

	public String getNumeroReferencia() {
		return numeroReferencia;
	}

	public void setNumeroReferencia(String numeroReferencia) {
		this.numeroReferencia = numeroReferencia;
	}


}