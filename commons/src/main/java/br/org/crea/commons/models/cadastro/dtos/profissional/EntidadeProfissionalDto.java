package br.org.crea.commons.models.cadastro.dtos.profissional;

import java.io.Serializable;
import java.util.Date;

public class EntidadeProfissionalDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private boolean opcaoVoto;
	
	private Long idProfissional;
	
	private Date dataOpcao;
	
	private Date dataFiliacao;
	
	private String dataFiliacaoFormatada;
	
	private String dataOpcaoFormatada;
	
	private String codigoModalidade;
	
	private String codigoEspecialidade;
	
	private EntidadeClasseDto entidadeClasse;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isOpcaoVoto() {
		return opcaoVoto;
	}

	public void setOpcaoVoto(boolean opcaoVoto) {
		this.opcaoVoto = opcaoVoto;
	}

	public Date getDataOpcao() {
		return dataOpcao;
	}

	public void setDataOpcao(Date dataOpcao) {
		this.dataOpcao = dataOpcao;
	}

	public Date getDataFiliacao() {
		return dataFiliacao;
	}

	public void setDataFiliacao(Date dataFiliacao) {
		this.dataFiliacao = dataFiliacao;
	}

	public EntidadeClasseDto getEntidadeClasse() {
		return entidadeClasse;
	}

	public void setEntidadeClasse(EntidadeClasseDto entidadeClasse) {
		this.entidadeClasse = entidadeClasse;
	}

	public String getDataFiliacaoFormatada() {
		return dataFiliacaoFormatada;
	}

	public void setDataFiliacaoFormatada(String dataFiliacaoFormatada) {
		this.dataFiliacaoFormatada = dataFiliacaoFormatada;
	}

	public String getDataOpcaoFormatada() {
		return dataOpcaoFormatada;
	}

	public void setDataOpcaoFormatada(String dataOpcaoFormatada) {
		this.dataOpcaoFormatada = dataOpcaoFormatada;
	}

	public Long getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(Long idProfissional) {
		this.idProfissional = idProfissional;
	}

	public String getCodigoModalidade() {
		return codigoModalidade;
	}

	public void setCodigoModalidade(String codigoModalidade) {
		this.codigoModalidade = codigoModalidade;
	}

	public String getCodigoEspecialidade() {
		return codigoEspecialidade;
	}

	public void setCodigoEspecialidade(String codigoEspecialidade) {
		this.codigoEspecialidade = codigoEspecialidade;
	}
	
	public boolean temCodigoEspecialidade() {
		return this.codigoEspecialidade != null;
	}
	public boolean temCodigoModalidade() {
		return this.codigoModalidade != null;
	}

}

