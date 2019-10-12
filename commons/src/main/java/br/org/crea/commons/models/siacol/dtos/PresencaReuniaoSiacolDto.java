package br.org.crea.commons.models.siacol.dtos;

import java.util.Date;

import br.org.crea.commons.models.corporativo.dtos.PessoaDto;

public class PresencaReuniaoSiacolDto {
	
	private Long id;
		
	private PessoaDto pessoa;
	
	private ReuniaoSiacolDto reuniao;
	
	private Date horaEntregaCracha;
	
	private Date horaDevolucaoCracha;
	
	private String papel;
	
	private String tipo;
	
	private Boolean votoMinerva;
	
	private Boolean atingiu80;
	
	private Date hora80;
	
	private String acao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PessoaDto getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDto pessoa) {
		this.pessoa = pessoa;
	}

	public ReuniaoSiacolDto getReuniao() {
		return reuniao;
	}

	public void setReuniao(ReuniaoSiacolDto reuniao) {
		this.reuniao = reuniao;
	}

	public Date getHoraEntregaCracha() {
		return horaEntregaCracha;
	}

	public void setHoraEntregaCracha(Date horaEntregaCracha) {
		this.horaEntregaCracha = horaEntregaCracha;
	}

	public Date getHoraDevolucaoCracha() {
		return horaDevolucaoCracha;
	}

	public void setHoraDevolucaoCracha(Date horaDevolucaoCracha) {
		this.horaDevolucaoCracha = horaDevolucaoCracha;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Boolean getVotoMinerva() {
		return votoMinerva;
	}

	public void setVotoMinerva(Boolean votoMinerva) {
		this.votoMinerva = votoMinerva;
	}

	public Boolean getAtingiu80() {
		return atingiu80;
	}

	public void setAtingiu80(Boolean atingiu80) {
		this.atingiu80 = atingiu80;
	}

	public Date getHora80() {
		return hora80;
	}

	public void setHora80(Date hora80) {
		this.hora80 = hora80;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public boolean ehEntrada() {
		return this.acao.equals("ENTRADA");
	}
	
	public boolean ehSaida() {
		return this.acao.equals("SAIDA");
	}
	
	public boolean ehDeletaPresenca() {
		return this.acao.equals("DELETA");
	}

	public boolean temReuniao() {
		return this.reuniao != null;
	}
	
	public boolean temPessoa() {
		return this.pessoa != null;
	}
}
