package br.org.crea.commons.models.siacol.dtos.documento;

import java.util.List;

public class RelatorioVotoDto {
	
	private List<String> listaIdsTexto;
	
	private List<String> listaIdsTextoNegativa;
	
	private Long dataCriacao;
	
	//private Long dataAtualizacao;
	
	//private String dataDocumento;
	
	private String texto;
	
	private String textoVoto;
	
	private String assunto;
	
	private String origem;
	
	private String local;
	
	private String relator;

	private String numeroDocumento;

	private String nomeOrigem;
	
	//private Long matricula;
	
	private String camaraEspecializada;
	
	private String comissaoPermanente;
	
	private String comissaoEspecial;
	
	private String plenario;

	private String outrosOrgaoDeOrigem;
	
	private String diretoria;

	public List<String> getListaIdsTexto() {
		return listaIdsTexto;
	}

	public void setListaIdsTexto(List<String> listaIdsTexto) {
		this.listaIdsTexto = listaIdsTexto;
	}

	public List<String> getListaIdsTextoNegativa() {
		return listaIdsTextoNegativa;
	}

	public void setListaIdsTextoNegativa(List<String> listaIdsTextoNegativa) {
		this.listaIdsTextoNegativa = listaIdsTextoNegativa;
	}

	public Long getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Long dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTextoVoto() {
		return textoVoto;
	}

	public void setTextoVoto(String textoVoto) {
		this.textoVoto = textoVoto;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getRelator() {
		return relator;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNomeOrigem() {
		return nomeOrigem;
	}

	public void setNomeOrigem(String nomeOrigem) {
		this.nomeOrigem = nomeOrigem;
	}

	public void setRelator(String relator) {
		this.relator = relator;
	}

	public String getCamaraEspecializada() {
		return camaraEspecializada;
	}

	public void setCamaraEspecializada(String camaraEspecializada) {
		this.camaraEspecializada = camaraEspecializada;
	}

	public String getComissaoPermanente() {
		return comissaoPermanente;
	}

	public void setComissaoPermanente(String comissaoPermanente) {
		this.comissaoPermanente = comissaoPermanente;
	}

	public String getComissaoEspecial() {
		return comissaoEspecial;
	}

	public void setComissaoEspecial(String comissaoEspecial) {
		this.comissaoEspecial = comissaoEspecial;
	}

	public String getPlenario() {
		return plenario;
	}

	public void setPlenario(String plenario) {
		this.plenario = plenario;
	}

	public String getOutrosOrgaoDeOrigem() {
		return outrosOrgaoDeOrigem;
	}

	public void setOutrosOrgaoDeOrigem(String outrosOrgaoDeOrigem) {
		this.outrosOrgaoDeOrigem = outrosOrgaoDeOrigem;
	}

	public String getDiretoria() {
		return diretoria;
	}

	public void setDiretoria(String diretoria) {
		this.diretoria = diretoria;
	}
	

}
