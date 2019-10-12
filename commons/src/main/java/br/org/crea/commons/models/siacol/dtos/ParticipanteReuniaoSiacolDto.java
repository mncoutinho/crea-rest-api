package br.org.crea.commons.models.siacol.dtos;

import br.org.crea.commons.models.siacol.enuns.StatusPresencaReuniaoEnum;
import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;

public class ParticipanteReuniaoSiacolDto {
	
	private int __index;
	
	private Long id;
	
	private String nome;
	
	private String nomeGuerra;
	
	private Long cracha;
	
	private Long idCargo;
	
	private String descricaoCargo;
	
	private Long idReuniao;
	
	private String base64;
	
	private VotoReuniaoEnum voto;
	
	private StatusPresencaReuniaoEnum statusPresenca;
	
	private String papel;
	
	private Boolean ehSuplente;
	
	private Boolean ehSuplenteDoLicenciado;
	
	private Boolean votoMinerva;
	
	private Long idSuplente;
	
	private String nomeSuplente;
	
	private StatusPresencaReuniaoEnum statusPresencaEfetivoOuSuplente;
	
	private Long senha;
	
	private String titulos;
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getNomeGuerra() {
		return nomeGuerra;
	}

	public Long getCracha() {
		return cracha;
	}

	public Long getIdCargo() {
		return idCargo;
	}

	public Long getIdReuniao() {
		return idReuniao;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setNomeGuerra(String nomeGuerra) {
		this.nomeGuerra = nomeGuerra;
	}

	public VotoReuniaoEnum getVoto() {
		return voto;
	}

	public void setVoto(VotoReuniaoEnum voto) {
		this.voto = voto;
	}

	public StatusPresencaReuniaoEnum getStatusPresenca() {
		return statusPresenca;
	}

	public void setStatusPresenca(StatusPresencaReuniaoEnum statusPresenca) {
		this.statusPresenca = statusPresenca;
	}

	public void setCracha(Long cracha) {
		this.cracha = cracha;
	}

	public void setIdCargo(Long idCargo) {
		this.idCargo = idCargo;
	}

	public void setIdReuniao(Long idReuniao) {
		this.idReuniao = idReuniao;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public Boolean getEhSuplente() {
		return ehSuplente;
	}

	public void setEhSuplente(Boolean ehSuplente) {
		this.ehSuplente = ehSuplente;
	}

	public Boolean getEhSuplenteDoLicenciado() {
		return ehSuplenteDoLicenciado;
	}

	public void setEhSuplenteDoLicenciado(Boolean ehSuplenteDoLicenciado) {
		this.ehSuplenteDoLicenciado = ehSuplenteDoLicenciado;
	}

	public Long getIdSuplente() {
		return idSuplente;
	}

	public void setIdSuplente(Long idSuplente) {
		this.idSuplente = idSuplente;
	}

	public StatusPresencaReuniaoEnum getStatusPresencaEfetivoOuSuplente() {
		return statusPresencaEfetivoOuSuplente;
	}

	public void setStatusPresencaEfetivoOuSuplente(
			StatusPresencaReuniaoEnum statusPresencaEfetivoOuSuplente) {
		this.statusPresencaEfetivoOuSuplente = statusPresencaEfetivoOuSuplente;
	}

	public String getDescricaoCargo() {
		return descricaoCargo;
	}

	public void setDescricaoCargo(String descricaoCargo) {
		this.descricaoCargo = descricaoCargo;
	}

	public Long getSenha() {
		return senha;
	}

	public void setSenha(Long senha) {
		this.senha = senha;
	}

	public int get__index() {
		return __index;
	}

	public void set__index(int __index) {
		this.__index = __index;
	}

	public Boolean getVotoMinerva() {
		return votoMinerva;
	}

	public void setVotoMinerva(Boolean votoMinerva) {
		this.votoMinerva = votoMinerva;
	}

	public String getNomeSuplente() {
		return nomeSuplente;
	}

	public void setNomeSuplente(String nomeSuplente) {
		this.nomeSuplente = nomeSuplente;
	}

	public String getTitulos() {
		return titulos;
	}

	public void setTitulos(String titulos) {
		this.titulos = titulos;
	}

	public boolean jaVotou() {
		return this.voto.equals(VotoReuniaoEnum.S) || this.voto.equals(VotoReuniaoEnum.N) || this.voto.equals(VotoReuniaoEnum.A);
	}
}
