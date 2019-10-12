package br.org.crea.commons.models.art.enuns;

public enum TipoExigenciaArtEnum {

	INFORMAR_REGISTRO_NOME_PROFISSIONAL(new Long(1), "Informar nr do registro/nome do prof"),
	INFORMAR_NOME_CONTRATANTE(new Long(2), "Informar o nome do contratante"),
	INFORMAR_ENDERECO_CONTRATANTE(new Long(3), "Informar o endereco do contratante"),
	INFORMAR_ATIVIDADE_TECNICA(new Long(4), "Informar Atividade Tecnica"),
	INFORMAR_ESPECIFICACAO_ATIVIDADE(new Long(5), "Informar Especificacao da Atividade"),
	INFORMAR_COMPLEMENTO(new Long(6), "Informar Complemento"),
	PROFISSIONAL_ANUIDADE_ATRASO(new Long(7), "Profissional com a anuidade em atraso"),
	TITULO_PROFISSIONAL_INCOMPATIVEL_OBRA_SERVICO(new Long(8), "Tit do prof incomp com a obra/servico"),
	TITULO_PROFISSIONAL_INCOMPATIVEL_ATIVIDADE_TECNICA(new Long(9), "Tit prof incomp com Atividade Tecnica"),
	EMPRESA_ANUIDADE_ATRASO(new Long(10), "Empresa com a anuidade em atraso"),
	COPIA_ART_COM_ASSINATURA_CONTRATADO(new Long(11), "Copia da ART com a assinatura do contratado"),
	INFORMAR_NUMERO_ART_PRINCIPAL(new Long(12), "Informar o Nr da ART principal"),
	COMPROVAR_VINCULO_PROFISSIONAL_EMPRESA(new Long(13), "Comprovar vinculo do prof com a empresa"),
	INFORMAR_HA_PROFISSIONAL_CO_RESPONSAVEL(new Long(14), "Informar se ha prof co-responsavel"),
	INFORMAR_HA_PROFISSIONAL_EMPRESA_VINCULADA(new Long(15), "Informar se ha prof/empresa vinculada"),
	PROFISSIONAL_NAO_CADASTRADO(new Long(16), "Profissional nao cadastrado"),
	EMPRESA_NAO_CADASTRADA(new Long(17), "Empresa nao cadastrada"),
	CAMPO_18_SUJEITO_NOVA_ANALISE(new Long(18), "Campo 18 sujeito a nova analise"),
	CAMPO_19_SUJEITO_NOVA_ANALISE(new Long(19), "Campo 19 sujeito a nova analise"),
	NAO_FOI_CADASTRADO_NENHUM_CONTRATANTE(new Long(20), "Nao foi cadastrado nenhum contratante"),
	ART_SEM_PAGAMENTO(new Long(21), "ART sem Pagamento"),
	ART_POSSUI_DIFERENCAS_A_PAGAR(new Long(22), "ART possui diferencas a pagar"),
	CAMPO_27_SUJEITO_NOVA_ANALISE(new Long(23), "Campo 27 sujeito a analise"),
	INFORMAR_QUANTIFICACAO_OBRA(new Long(24), "Informar Quantificacao da Obra"),
	INFORMAR_NUMERO_PAVIMENTOS(new Long(25), "Informar nr de pavimentos"),
	INFORMAR_DATA_INICIO_OBRA_SERVICO(new Long(26), "Informar data de inicio da obra/servico"),
	INFORMAR_PRAZO_CONTRATO(new Long(27), "Informar prazo do contrato"),
	INFORMAR_HOMEM_HORA_JORNADA_TRABALHO(new Long(28), "Informar homem hora/jornada de trabalho"),
	INFORMAR_VALOR_CONTRATO_HONORARIO(new Long(29), "Informar valor do contrato/honorario"),
	INFORMAR_SALARIO(new Long(30), "Informar o dado salario"),
	INFORMAR_ENDERECO_OBRA_SERVICO(new Long(31), "Informar o endereco da obra/servico"),
	COPIA_ART_COM_ASSINATURA_CONTRATANTE(new Long(32), "Copia da ART com ass. do contratante"),
	REGISTRO_PROFISSIONAL_POSTERIOR_DATA_INICIO(new Long(33), "Reg do prof posterior campo 22 da ART"),
	PROFISSIONAL_REGISTRO_CANCELADO_DATA_INICIO(new Long(34), "Prof com reg canc data da obra/servico"),
	REGISTRO_EMPRESA_POSTERIOR_DATA_INICIO(new Long(35), "Reg empresa posterior campo 22 da ART"),
	EMPRESA_REGISTRO_CANCELADO_DATA_INICIO(new Long(36), "Emp com o reg canc na data obra/servico"),
	PROFISSIONAL_NAO_PERTENCE_EMPRESA_DATA_INICIO(new Long(37), "Prof nao pertc emp conf campo 22 da ART"),
	PROF_LIM_ATO02_APRESENTAR_RELATORIO(new Long(38), "Prof lim ATO 02 apresentar relatorio"),
	ATIVIDADE_OUTRO_ESTADO(new Long(39), "Atividade nao pode ser em outro estado"),
	PROFISSIONAL_REGISTRO_CANCELADO_DECORRER_ATIVIDADE(new Long(40), "Prof reg canc no decorrer da atividade"),
	EMPRESA_REGISTRO_CANCELADO_DECORRER_ATIVIDADE(new Long(41), "Emp reg canc no decorrer da atividade"),
	PROFISSIONAL_DESVINCULADO_EMPRESA_DURANTE_ATIVIDADE(new Long(42), "Prof desvinc da emp durante atividade"),
	INFORMAR_TERMINO_OBRA_SERVICO(new Long(43), "Informar termino da obra/servico"),
	PROFISSIONAL_VISTO_PREVIO(new Long(44), "Prof esta em regime de visto previo"),
	PROFISSIONAL_ENQUADRADO_ATO02(new Long(45), "Prof esta enquadrado no Ato 2"),
	APRESENTAR_DECLARACAO_CONTRATANTE(new Long(46), "Apresentar Declaracao do Contratante"),
	ART_PRINCIPAL_NAO_CADASTRADA(new Long(47), "ART Principal nao cadastrada"),
	PAGAMENTO_TAXA_INCORPORACAO_ACERVO(new Long(50), "Pagamento da Taxa de Incorporacao de Acervo"),
	DIFERENCA_PAGAMENTO_TAXA_ART(new Long(51), "Diferença de Pagamento de Taxa de Art");
	
	private final Long id;
	
	private final String descricao;
	
	TipoExigenciaArtEnum(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
