package br.org.crea.commons.models.commons.dtos;

import java.util.Date;

import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

public class AuditoriaDto {
	
	
	private Long id;
	
	private String ip;
	
	private Long modulo;

	private Long evento;
	
	private PessoaDto pessoa;
	
	private Long idUsuario;
	
	private String registroUsuario;

	private String perfilUsuario;
	
	private String nomeUsuario;

	private Long idRemetente;
	
	private String perfilRemetente;
	
	private String registroRemetente;
	
	private String nomeRemetente;
	
	private Long idDestinatario;
	
	private String perfilDestinatario;
	
	private String registroDestinatario;
	
	private String nomeDestinatario;
	
	private String numero;

	private String textoAuditoria;
	
	private Object dtoAntigo;

	private Object dtoNovo;
	
	private String dtoClass;
	
	private Date dataCriacao;
	
	private String dataCriacaoFormatada;
	
	private String acao;

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

	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
		this.pessoa = pessoa;
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

	public Object getDtoAntigo() {
		return dtoAntigo;
	}

	public void setDtoAntigo(Object dtoAntigo) {
		this.dtoAntigo = dtoAntigo;
	}

	public Object getDtoNovo() {
		return dtoNovo;
	}

	public void setDtoNovo(Object dtoNovo) {
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

	public String getDataCriacaoFormatada() {
		return dataCriacaoFormatada;
	}

	public void setDataCriacaoFormatada(String dataCriacaoFormatada) {
		this.dataCriacaoFormatada = dataCriacaoFormatada;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}


	
	
	
	

}
