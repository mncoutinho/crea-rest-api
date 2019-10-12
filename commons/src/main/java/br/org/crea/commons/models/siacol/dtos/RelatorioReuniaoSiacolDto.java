package br.org.crea.commons.models.siacol.dtos;

import java.math.BigDecimal;
import java.util.List;

public class RelatorioReuniaoSiacolDto {

	private String departamento;
	
	private String cargo;
	
	private String cracha;
	
	private String nome;
	
	private String presenca;
	
	private String parte;
	
	private String uri;
	
	private String tipo;
	
	private String entrada;
	
	private String saida;
	
	private String tempoPresente;
	
	private String entidade;
	
	private String qtdVotado;
	
	private String horaOitentaPorcento;
	
	private String nomeArquivoOriginal;
	
	private String nomeArquivo;
	
	private String mes;
	
	private String assunto;
	
	private String status;
	
	private String evento;
	
	private String data;
	
	private String destinatario;
	
	private String departamentoOrigem;
	
	private String departamentoDestino;
	
	private String erro;
	
	private String texto;
	
	private String quantidadeProvisorioConcedido;
	
	private String quantidadeProvisorioAprovado;
	
	private String quantidadeAdReferendumConcedido;
	
	private String quantidadeAdReferendumAprovado;
	
	private String quantidadeReuniaoPresencial;
	
	private String quantidadeReuniaoVirtual;
	
	private String passivoAnoAnterior;
	
	private String saldoAcumulado;
	
	private String quantidade;
	
	private String total;
	
	private BigDecimal diaria = BigDecimal.ZERO;
	
	private BigDecimal jeton = BigDecimal.ZERO;
	
	private BigDecimal soma = BigDecimal.ZERO;
	
	private List<RelatorioReuniaoSiacolDto> relatorio;
	
	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCracha() {
		return cracha;
	}

	public void setCracha(String cracha) {
		this.cracha = cracha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPresenca() {
		return presenca;
	}

	public void setPresenca(String presenca) {
		this.presenca = presenca;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Departamento: " + departamento + ", Cracha: " + cracha + ", Nome: " + nome + ", Presença: " + presenca;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public String getTempoPresente() {
		return tempoPresente;
	}

	public void setTempoPresente(String tempoPresente) {
		this.tempoPresente = tempoPresente;
	}

	public String getEntidade() {
		return entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}

	public String getQtdVotado() {
		return qtdVotado;
	}

	public void setQtdVotado(String qtdVotado) {
		this.qtdVotado = qtdVotado;
	}

	public String getNomeArquivoOriginal() {
		return nomeArquivoOriginal;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public void setNomeArquivoOriginal(String nomeArquivoOriginal) {
		this.nomeArquivoOriginal = nomeArquivoOriginal;
	}

	public String getHoraOitentaPorcento() {
		return horaOitentaPorcento;
	}

	public void setHoraOitentaPorcento(String horaOitentaPorcento) {
		this.horaOitentaPorcento = horaOitentaPorcento;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getQuantidadeReuniaoPresencial() {
		return quantidadeReuniaoPresencial;
	}

	public void setQuantidadeReuniaoPresencial(String quantidadeReuniaoPresencial) {
		this.quantidadeReuniaoPresencial = quantidadeReuniaoPresencial;
	}

	public String getQuantidadeReuniaoVirtual() {
		return quantidadeReuniaoVirtual;
	}

	public void setQuantidadeReuniaoVirtual(String quantidadeReuniaoVirtual) {
		this.quantidadeReuniaoVirtual = quantidadeReuniaoVirtual;
	}

	public List<RelatorioReuniaoSiacolDto> getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(List<RelatorioReuniaoSiacolDto> relatorio) {
		this.relatorio = relatorio;
	}

	public String getPassivoAnoAnterior() {
		return passivoAnoAnterior;
	}

	public void setPassivoAnoAnterior(String passivoAnoAnterior) {
		this.passivoAnoAnterior = passivoAnoAnterior;
	}

	public String getSaldoAcumulado() {
		return saldoAcumulado;
	}

	public void setSaldoAcumulado(String saldoAcumulado) {
		this.saldoAcumulado = saldoAcumulado;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getQuantidadeProvisorioConcedido() {
		return quantidadeProvisorioConcedido;
	}

	public void setQuantidadeProvisorioConcedido(String quantidadeProvisorioConcedido) {
		this.quantidadeProvisorioConcedido = quantidadeProvisorioConcedido;
	}

	public String getQuantidadeProvisorioAprovado() {
		return quantidadeProvisorioAprovado;
	}

	public void setQuantidadeProvisorioAprovado(String quantidadeProvisorioAprovado) {
		this.quantidadeProvisorioAprovado = quantidadeProvisorioAprovado;
	}

	public String getQuantidadeAdReferendumConcedido() {
		return quantidadeAdReferendumConcedido;
	}

	public void setQuantidadeAdReferendumConcedido(String quantidadeAdReferendumConcedido) {
		this.quantidadeAdReferendumConcedido = quantidadeAdReferendumConcedido;
	}

	public String getQuantidadeAdReferendumAprovado() {
		return quantidadeAdReferendumAprovado;
	}

	public void setQuantidadeAdReferendumAprovado(String quantidadeAdReferendumAprovado) {
		this.quantidadeAdReferendumAprovado = quantidadeAdReferendumAprovado;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getDepartamentoOrigem() {
		return departamentoOrigem;
	}

	public void setDepartamentoOrigem(String departamentoOrigem) {
		this.departamentoOrigem = departamentoOrigem;
	}

	public String getDepartamentoDestino() {
		return departamentoDestino;
	}

	public void setDepartamentoDestino(String departamentoDestino) {
		this.departamentoDestino = departamentoDestino;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public BigDecimal getDiaria() {
		return diaria;
	}

	public void setDiaria(BigDecimal diaria) {
		this.diaria = this.diaria.add(diaria);
	}

	public BigDecimal getJeton() {
		return jeton;
	}

	public void setJeton(BigDecimal jeton) {
		this.jeton = this.jeton.add(jeton);
	}

	public BigDecimal getSoma() {
		return soma;
	}

	public void setSoma(BigDecimal soma) {
		this.soma = this.soma.add(soma);
	}

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

	public boolean temParte() {
		return this.parte != null;
	}

}
