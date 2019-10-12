package br.org.crea.commons.models.cadastro.enuns;

public enum TipoEventoAuditoria {
	
	TRAMITAR_PROTOCOLO(new Long(1), "Tramitar Protocolo"),
	DISTRIBUIR_PROTOCOLO(new Long(2), "Distribuir Protocolo"),
	CLASSIFICAR_PROTOCOLO(new Long(3), "Classificar Protocolo"),
	DOCUMENTO(new Long(4), "Documento"),
	RECEBER_PROTOCOLO(new Long(5), "Recebimento de protocolo"),
	ALTERA_SITUACAO_PROTOCOLO(new Long(6), "Classificar Protocolo"),
	CADASTRAR_DOCUMENTO(new Long(7), "Cadastrar documento"),
	ATUALIZAR_DOCUMENTO(new Long(8), "Atualizar documento"),
	EXCLUIR_DOCUMENTO(new Long(9), "Excluir documento"), 
	CADASTRAR_PROTOCOLO(new Long(10), "Cadastrar protocolo"), 
	ANEXAR_PROTOCOLO(new Long(11), "Anexar protocolo"),
	APENSAR_PROTOCOLO(new Long(12), "Apensar protocolo"),
	DESANEXAR_PROTOCOLO(new Long(13), "Desanexar protocolo"),
	DESAPENSAR_PROTOCOLO(new Long(14), "Desapensar protocolo"),
	SUBSTITUIR_PROTOCOLO(new Long(15), "Substituir protocolo"),
	ASSINATURA_DIGITAL_DOCUMENTO(new Long(16), "Assinatura digital de documento com guarda eletrônica (GED)"),
	AGENDAMENTO_EMISSAO_TAXA(new Long(17), "Agendamento Emissão de Taxa"),
	SIACOL_REUNIAO_INICIAR(new Long(18), "Iniciar Reunião"),
	SIACOL_REUNIAO_PAUSAR(new Long(19), "Pausar Reunião"),
	SIACOL_REUNIAO_CANCELAR(new Long(20), "Cancelar Reunião"),
	SIACOL_REUNIAO_ENCERRAR(new Long(21), "Encerrar Reunião"),
	SIACOL_PRESENCA_ENTRADA(new Long(22), "Registrar Entrada na Reunião"),
	SIACOL_PRESENCA_SAIDA(new Long(23), "Registrar Saída da Reunião"),
	SIACOL_PRESENCA_DELETA(new Long(24), "Deleta Registro de Presença na Reunião"),
	ALTERA_STATUS_PROTOCOLO(new Long(25), "Altera Status do Protocolo"),
	SIACOL_PRESENCA_PAUSA(new Long(26), "Registra Presença na Pausa de uma Reunião"),
	SIACOL_PROTOCOLO_ALTERACAO_STATUS(new Long(27), "Alterou o status do protocolo"),
	SIACOL_STATUS_DEVOLUCAO_COORDENADOR(new Long(28), "Alterou o status para devolucao do coordenador"),
	SIACOL_JUSTIFICATIVA_DEVOLUCAO(new Long(29), "Justificativa de devolucao de um protocolo"),
	SIACOL_CLASSIFICAR_PROTOCOLO(new Long(30), "Classificacao de protocolo"),
	SIACOL_DISTRIBUICAO_PROTOCOLO(new Long(31), "Distribuicao de protocolo"),
	ART_CADASTRO(new Long(32), "Cadastro ART"),
	CLASSIFICACAO_FINAL_PROTOCOLO(new Long(33), "Classificação Final Protocolo"),
	SIACOL_RECEBER_PROTOCOLO(new Long(34), "Receber Protocolo no Siacol"),
	SIACOL_DISTRIBUICAO_COORDENADOR_DEVOLUCAO(new Long(35), "Distribuição do Coordenador para contabilização de tempo de análise do protocolo"),
	SIACOL_DISTRIBUICAO_COORDENADOR_PARA_RECEBIMENTO(new Long(36), "Distribuição do Coordenador para contabilização do tempo de recebimento do protocolo"),
	CONSELHEIRO_IMPEDIDO(new Long(37), "Conselheiro se julgou impedido para analisar o protocolo"),
	PROTOCOLO_OFICIADO_ASSUMIDO(new Long(38), "Protocolo oficiado foi assumido por um analista"),
	SIACOL_PAUSAR_PROTOCOLO(new Long(39), "Pausar protocolo"),
	SIACOL_RETIRA_PAUSA_PROTOCOLO(new Long(40), "Retira pausa do protocolo"),
	DOCFLOW_CADASTRAR_DOCUMENTO(new Long(41), "Cadastrar documento Docflow"),
	TRAMITAR_PROTOCOLO_ANEXO(new Long(42), "Tramitar Protocolo Anexo"),
	RECEBER_PROTOCOLO_ANEXO(new Long(43), "Recebimento de protocolo Anexo"),
	TRAMITAR_PROTOCOLO_APENSO(new Long(44), "Tramitar Protocolo Apenso"),
	RECEBER_PROTOCOLO_APENSO(new Long(45), "Recebimento de protocolo Apenso"),
	SIACOL_DISTRIBUICAO_PROTOCOLO_ANEXO(new Long(46), "Distribuição de protocolo Anexo"),
	TRAMITAR_PROTOCOLO_DOCFLOW(new Long(47), "Tramitar Protocolo no Docflow"),
	RECEBER_PROTOCOLO_DOCFLOW(new Long(48), "Recebimento de protocolo no Docflow"),
	INATIVIDADE_ANEXO_APENSO(new Long(49), "Protocolo oculto por anexação ou apensação"),
	RETORNO_INATIVO_ANEXO_APENSO(new Long(50), "Protocolo anexado ou apensado ativo pelo usuario para analise"),
	ASSUMIR_PROTOCOLO(new Long(51), "Assumir protocolo com status A pautar ou Pautado e suas derivações"),
	INCLUSAO_PROTOCOLO_EXTRAPAUTA(new Long(52), "Inclusão de protocolo na extrapauta"),
	INCLUSAO_PROTOCOLO_EMERGENCIAL(new Long(53), "Inclusão de protocolo emergencial"),
	PEDIDO_DESTAQUE(new Long(54), "Pedido de destaque"),
	VISTA_CONCEDIDA_PROTOCOLO(new Long(55), "Vista concedida a protocolo"),
	JULGAMENTO_PROTOCOLO(new Long(56), "Julgamento de protocolo"),
	VOTO_OFFLINE(new Long(57), "Voto offline"),
	VOTO_ACLAMACAO(new Long(58), "Voto por aclamação"),
	DECLARACAO_VOTO(new Long(59), "Declaração de voto"),
	ART_CADASTRO_GERADO_MODELO(new Long(60), "ART Gerada a partir de ART Modelo"),
	ART_INICIADA(new Long(61), "ART Iniciada");

	private Long id;
	private String nome;
	
	private TipoEventoAuditoria(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public static Long getIdBy(TipoEventoAuditoria tipo){
		
		for(TipoEventoAuditoria s : TipoEventoAuditoria.values()){
			if(s.equals(tipo)){
				return s.id;
			}
		}
		return null;
	}
	
	public static String getNomeBy(Long long1){
		
		for(TipoEventoAuditoria s : TipoEventoAuditoria.values()){
			if(s.id.equals(long1)){
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
