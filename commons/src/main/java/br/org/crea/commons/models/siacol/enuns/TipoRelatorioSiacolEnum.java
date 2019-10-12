package br.org.crea.commons.models.siacol.enuns;

public enum TipoRelatorioSiacolEnum {
	
	RELATORIO_DE_COORDENADORES(new Long(1), "Relatório de Coordenadores"),
	RELATORIO_INICIO_DE_QUORUM(new Long(2), "Relatório de Início de Quórum"),
	RELATORIO_FIM_DE_QUORUM(new Long(3), "Relatório de Fim de Quórum"),
	RELATORIO_INICIO_DA_REUNIAO(new Long(4), "Relatório de Início da Reunião"),
	RELATORIO_FIM_DA_REUNIAO(new Long(5), "Relatório de Fim da Reunião"),
	RELATORIO_80_PORCENTO(new Long(6), "Relatório de 80%"),
	RELATORIO_DE_COMPARECIMENTO(new Long(7), "Relatório de Comparecimento"),
	RELATORIO_DE_CANCELAMENTO_DA_REUNIAO(new Long(8), "Relatório de Cancelamento da Reunião"),
	RELATORIO_DE_PAUSA_DA_REUNIAO(new Long(9), "Relatório de Pausa da Reunião"),
	REL_01(new Long(10), "Relatório da quantidade de protocolos por departamento e ano"),
	REL_02(new Long(11), "Relatório da quantidade de protocolos julgados por departamento, assunto e classificação"),
	REL_03(new Long(12), "Relatório da quantidade de protocolos julgados por evento"),
	REL_04(new Long(13), "Relatório da quantidade de protocolos julgados e não julgados por assunto e departamento"),
	REL_05(new Long(14), "Relatório de detalhamento do saldo acumulado com indicação do status e mês"),
	REL_06(new Long(15), "Relatório de Produtividade - quantidade de protocolos por analista ou conselheiro X mês (Saída-Conclusão da análise/ Entrada para análise +Saldo acumulado)"),
	REL_07(new Long(16), "Relatório de detalhamento do histórico de produção da quantidade de protocolos por assunto siacol por analista / conselheiro com os totais dos tempos"),
	REL_08(new Long(17), "Relatório de quantidade, com seleção do tipo ou conjunto de tipos com indicação do tempo-Carta de serviço / por atores"),
	REL_09(new Long(18), "Relatório com relação detalhada por tipo pendente de análise"),
	REL_10(new Long(19), "Relatório analítico da produção do analista"),
	REL_11(new Long(20), "Relatório da vida do protocolo"),
	REL_12(new Long(21), "Relatório do somatório do total das decisões por departamento"),
	REL_13(new Long(22), "Relatório de assuntos de protocolo (corporativo) X Assuntos SIACOL "),
	REL_14(new Long(23), "Relatório da quantidade mensal de protocolos julgados na reuniões virtuais e na presencial por Departamento.");
	


	private Long id;
	private String nome;
	
	private TipoRelatorioSiacolEnum(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public static Long getIdBy(TipoRelatorioSiacolEnum tipo){
		
		for(TipoRelatorioSiacolEnum s : TipoRelatorioSiacolEnum.values()){
			if(s.equals(tipo)){
				return s.id;
			}
		}
		
		return null;
	}
	
	public static String getNomeBy(Long id){
		
		for(TipoRelatorioSiacolEnum s : TipoRelatorioSiacolEnum.values()){
			if(s.id.equals(id)){
				return s.nome;
			}
		}
		
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
