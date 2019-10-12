package br.org.crea.commons.models.art.enuns;

public enum TipoAtividadeContratoArt {
	
	ANALISE(new Long(1)),
	ASSESSORIA(new Long(3)),
	AVALIACAO(new Long(5)),
	CONSULTORIA(new Long(12)),
	DESEMPENHO_CARGO_TECNICO(new Long(15)),
	DESEMPENHO_FUNCAO_TECNICA(new Long(16)),
	ELABORACAO_DE_ORCAMENTO(new Long(20)),
	ESPECIFICACAO(new Long(23)),
	ESTUDO(new Long(24)),
	ESTUDO_DE_VIABILIDADE_TECNICO_ECONOMICA(new Long(25)),
	EXECUCAO_DE_DESENHO_TECNICO(new Long(26)),
	EXECUCAO_SERVICO_TECNICO(new Long(31)),
	LAUDO_TECNICO(new Long(36)),
	MANUTENCAO_DE_EQUIPAMENTO(new Long(37)),
	MANUTENCAO_DE_INSTALACAO(new Long(38)),
	OPERACAO_DE_EQUIPAMENTO(new Long(40)),
	OPERACAO_DE_INSTALACAO(new Long(41)),
	ORIENTACAO_TECNICA(new Long(42)),
	PADRONIZACAO(new Long(43)),
	PARECER_TECNICO(new Long(44)),
	PESQUISA(new Long(46)),
	PLANEJAMENTO(new Long(47)),
	PROJETO(new Long(49)),
	RESPONSAVEL_TECNICO_POR_EMPRESA(new Long(68)),
	QUADRO_TECNICO_DA_EMPRESA(new Long(69));
	
	private final Long id;
	
	TipoAtividadeContratoArt(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
