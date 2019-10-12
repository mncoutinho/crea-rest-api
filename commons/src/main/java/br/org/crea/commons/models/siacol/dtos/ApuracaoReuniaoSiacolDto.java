package br.org.crea.commons.models.siacol.dtos;

import java.util.List;

public class ApuracaoReuniaoSiacolDto {
	
	private int qtdItensReuniao;
	
	private int qtdItensVotados;
	
	private int qtdOutrosItensVotaveis;
	
	private String percentualItensVotados;
	
	private int qtdSim;

	private int qtdNao;

	private int qtdAbstencao;
	
	private int qtdDestaque;
	
	private String status;
	
	private int qtdPresentes;
	
	private int quorum;
	
	private List<RespostaEnqueteDto> enquete;
	
	private int votadoAntesDaApuracao;
	
	private int votadoDepoisDaApuracao;

	public int getQtdItensReuniao() {
		return qtdItensReuniao;
	}

	public void setQtdItensReuniao(int qtdItensReuniao) {
		this.qtdItensReuniao = qtdItensReuniao;
	}

	public int getQtdItensVotados() {
		return qtdItensVotados;
	}

	public void setQtdItensVotados(int qtdItensVotados) {
		this.qtdItensVotados = qtdItensVotados;
	}

	public String getPercentualItensVotados() {
		return percentualItensVotados;
	}

	public void setPercentualItensVotados(String percentualItensVotados) {
		this.percentualItensVotados = percentualItensVotados;
	}

	public int getQtdSim() {
		return qtdSim;
	}

	public void setQtdSim(int qtdSim) {
		this.qtdSim = qtdSim;
	}

	public int getQtdNao() {
		return qtdNao;
	}

	public void setQtdNao(int qtdNao) {
		this.qtdNao = qtdNao;
	}

	public int getQtdAbstencao() {
		return qtdAbstencao;
	}

	public void setQtdAbstencao(int qtdAbstencao) {
		this.qtdAbstencao = qtdAbstencao;
	}

	public int getQtdDestaque() {
		return qtdDestaque;
	}

	public void setQtdDestaque(int qtdDestaque) {
		this.qtdDestaque = qtdDestaque;
	}

	public int getQtdPresentes() {
		return qtdPresentes;
	}

	public void setQtdPresentes(int qtdPresentes) {
		this.qtdPresentes = qtdPresentes;
	}

	public int getQuorum() {
		return quorum;
	}

	public void setQuorum(int quorum) {
		this.quorum = quorum;
	}
	
	public int calculaRazaolItensVotadosPorItensDaReuniao() {
		return Math.round( ( (float) this.qtdItensVotados / this.qtdItensReuniao ) * 100);
	}

	public String calculaPercentualItensVotados() {
		return Integer.toString( Math.round( ( (float) this.qtdItensVotados / this.qtdItensReuniao ) * 100) ) + "%";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getQtdOutrosItensVotaveis() {
		return qtdOutrosItensVotaveis;
	}

	public void setQtdOutrosItensVotaveis(int qtdOutrosItensVotaveis) {
		this.qtdOutrosItensVotaveis = qtdOutrosItensVotaveis;
	}

	public List<RespostaEnqueteDto> getEnquete() {
		return enquete;
	}

	public void setEnquete(List<RespostaEnqueteDto> enquete) {
		this.enquete = enquete;
	}

	public int getVotadoAntesDaApuracao() {
		return votadoAntesDaApuracao;
	}

	public void setVotadoAntesDaApuracao(int votadoAntesDaApuracao) {
		this.votadoAntesDaApuracao = votadoAntesDaApuracao;
	}

	public int getVotadoDepoisDaApuracao() {
		return votadoDepoisDaApuracao;
	}

	public void setVotadoDepoisDaApuracao(int votadoDepoisDaApuracao) {
		this.votadoDepoisDaApuracao = votadoDepoisDaApuracao;
	}

	public boolean atingiuOitentaPorcento() {
		return this.votadoAntesDaApuracao < 80 && this.votadoDepoisDaApuracao >= 80;
	}

}
