package br.org.crea.commons.models.cadastro.dtos.empresa;

import java.util.Date;

public class RequerimentoPJDto {
	
	private Long id; 
	
	private Long idEmpresa;
	
	private String razaoSocialEmpresa;
	
	private Long numeroProtocolo;
	
	private ResponsavelTecnicoDto responsavelTecnico;
	
	private Date dataSolicitacao;
	
	private String dataSolicitacaoFormatada;
	
	private String numeroArt;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Long getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(Long numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public ResponsavelTecnicoDto getResponsavel() {
		return responsavelTecnico;
	}

	public void setResponsavel(ResponsavelTecnicoDto responsavelTecnico) {
		this.responsavelTecnico = responsavelTecnico;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public String getDataSolicitacaoFormatada() {
		return dataSolicitacaoFormatada;
	}

	public void setDataSolicitacaoFormatada(String dataSolicitacaoFormatada) {
		this.dataSolicitacaoFormatada = dataSolicitacaoFormatada;
	}

	public String getNumeroArt() {
		return numeroArt;
	}

	public void setNumeroArt(String numeroArt) {
		this.numeroArt = numeroArt;
	}

	public String getRazaoSocialEmpresa() {
		return razaoSocialEmpresa;
	}

	public void setRazaoSocialEmpresa(String razaoSocialEmpresa) {
		this.razaoSocialEmpresa = razaoSocialEmpresa;
	}
	
}
