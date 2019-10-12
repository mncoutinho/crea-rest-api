package br.org.crea.commons.models.siacol.enuns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum StatusProtocoloSiacol {
	
	AGUARDANDO_RECEBIMENTO(new Long(1), "A Receber", "AGUARDANDO_RECEBIMENTO"),
	ANALISE (new Long(2), "Em análise", "ANALISE"),
	DISTRIBUICAO (new Long(3), "Análise Conselheiro", "DISTRIBUICAO"),
	DISTRIBUICAO_ANALISTA (new Long(4), "Distribuido por um analista", "DISTRIBUICAO_ANALISTA"),
	PENDENTE_VINCULACAO (new Long(5), "Pendente vinculo", "PENDENTE_VINCULACAO"),
	VINCULADO (new Long(6), "Vinculado", "VINCULADO"),
	DEVOLUCAO (new Long(7), "Devolução Conselheiro", "DEVOLUCAO"),
	ENVIOCORDENADOR (new Long(8), "Devolução para Coordenador", "ENVIOCORDENADOR"),
	
	ASSINAR_PROVISORIO (new Long(9), "Para assinar provisório", "ASSINAR_PROVISORIO"),
	AGUARDANDO_PROVISORIO (new Long(10), "Aguardando provisório", "AGUARDANDO_PROVISORIO"),
	ANALISE_PROVISORIO_NEGADO (new Long(11), "Reanálise de provisório não cadastrado", "ANALISE_PROVISORIO_NEGADO"),
	
	A_PAUTAR (new Long(12), "A Pautar", "A_PAUTAR"),
	A_PAUTAR_PRESENCIAL(new Long(13), "A pautar presencial", "PROXIMA_REUNIAO_PRESENCIAL"), 
	A_PAUTAR_SEM_QUORUM(new Long(14), "A pautar sem quorum", "A_PAUTAR_SEM_QUORUM"),
	PAUTADO	(new Long(15), "Pautado", "PAUTADO"),
	
	A_PAUTAR_PROVISORIO (new Long(16), "Registro provisório à pautar", "A_PAUTAR_PROVISORIO"),
	PAUTADO_PROVISORIO (new Long(17), "Registro provisório pautato", "PAUTADO_PROVISORIO"),
	
	A_PAUTAR_VISTAS (new Long(18), "A pautar vistas", "A_PAUTAR_VISTAS"),
	VISTAS_AGUARDANDO_REUNIAO(new Long(19), "Vistas aguardando reunião", "VISTAS_AGUARDANDO_REUNIAO"),
	PAUTADO_VISTAS (new Long(20), "Pautado vistas", "PAUTADO_VISTAS"),

	A_PAUTAR_DESTAQUE(new Long(21), "A pautar destaque", "A_PAUTAR_DESTAQUE"),
	PAUTADO_DESTAQUE(new Long(22), "Pautado destaque", "PAUTADO_DESTAQUE"),

	
	PARA_ASSINAR_VIRTUAL (new Long(23), "Para assinar virtual", "PARA_ASSINAR_VIRTUAL"),

	PEDIDO_DE_VISTAS(new Long(24), "Pedido de vistas", "PEDIDO_DE_VISTAS"),
	ANALISE_VISTAS(new Long(25), "Análise vista conselheiro", "ANALISE_VISTAS"),

	DEFERIDO (new Long(26), "Deferido", "DEFERIDO"),
	FINALIZADO (new Long(27), "Finalizado", "FINALIZADO"),
	JULGADO	(new Long(28), "Julgado", "JULGADO"),
	REVISAO_DECISAO_VIRTUAL(new Long(29), "Revisão decisão virtual", "REVISAO_DECISAO_VIRTUAL"),
	PARA_ASSINAR_PRESENCIAL(new Long(30), "Para Assinar Presencial", "PARA_ASSINAR_PRESENCIAL"),	
	REVISAO_DECISAO_VISTAS(new Long(31), "Revisão decisão vistas", "REVISAO_DECISAO_VISTAS"),
	ANALISE_VISTAS_VOTADO(new Long(32), "Análise vistas votado", "ANALISE_VISTAS_VOTADO"),
	REVISAO_VISTAS_VOTADO(new Long(33), "Revisão vistas votado", "REVISAO_VISTAS_VOTADO"),
	ANALISE_VISTAS_A_PAUTAR(new Long(34), "Análise vistas a pautar", "ANALISE_VISTAS_A_PAUTAR"),
	RELACAO_VIRTUAL(new Long(35), "Relação virtual", "RELACAO_VIRTUAL"),
	ASSINAR_OFICIO(new Long(36), "Para assinar oficio", "ASSINAR_OFICIO"),
	A_RECEBER_PROVISORIO(new Long(37), "A receber provisorio", "A_RECEBER_PROVISORIO"),
	REVISAO_DECISAO_PRESENCIAL(new Long(38), "Revisão decisão presencial", "REVISAO_DECISAO_PRESENCIAL"),
	PROVISORIO_ASSINADO (new Long(39), "Provisório assinado", "PROVISORIO_ASSINADO"),
	OFICIO_ASSINADO (new Long(40), "Pendente Envio Ofício", "OFICIO_ASSINADO"),
	DEVOLUCAO_COORDENADOR_PARA_REVISAO(new Long(41), "Revisão decisão Coordenador", "DEVOLUCAO_COORDENADOR_PARA_REVISAO"),
	PARA_ASSINAR(new Long(42), "Assinatura revisão", "PARA_ASSINAR"),
	PARA_ASSINAR_VISTAS_VOTADO(new Long(43), "Assinatura revisão vistas", "PARA_ASSINAR_VISTAS_VOTADO"),
	REVISAO_ITENS_PROTOCOLADO(new Long(44), "Revisão de itens Protocolados", "REVISAO_ITENS_PROTOCOLADO"),

	ASSINATURA_AD_REFERENDUM(new Long(45), "Ad Referendum para assinar", "ASSINATURA_AD_REFERENDUM"),
	AD_REFERENDUM_ASSINADO(new Long(46), "Ad Referendum assinado", "AD_REFERENDUM_ASSINADO"),
	A_RECEBER_AD_REFERENDUM(new Long(47), "A receber Ad Referendum", "A_RECEBER_AD_REFERENDUM"),
	ANALISE_AD_REFERENDUM(new Long(48), "Analise Ad Referendum", "ANALISE_AD_REFERENDUM"),
	A_PAUTAR_AD_REFERENDUM(new Long(49), "A pautar Ad Referendum", "A_PAUTAR_AD_REFERENDUM"),
	PAUTADO_AD_REFERENDUM(new Long(50), "Ad Referendum pautado", "PAUTADO_AD_REFERENDUM"),
	ANALISE_CONSELHEIRO_AD_REFERENDUM(new Long(51), "Analise Conselheiro Ad Referendum", "ANALISE_CONSELHEIRO_AD_REFERENDUM"),
	
	ANALISE_CUMPRIMENTO_OFICIO(new Long(52), "Analise cumprimento de ofício", "ANALISE_CUMPRIMENTO_OFICIO"),
	DISTRIBUICAO_COORD_COAC(new Long(53), "Distribuido pelo coordenador da CACC", "DISTRIBUICAO_COORD_COAC"),
	PROTOCOLO_PAUSADO(new Long(54), "Protocolo em pausa", "PROTOCOLO_PAUSADO"),
	OFICIO_ENVIADO(new Long(55), "Oficiado", "OFICIO_ENVIADO"),
	
	DISTRIBUICAO_COORD_COAC_PARA_ANALISE(new Long(56), "Distribuído pelo coordenador CACC - Ajuste", "DISTRIBUICAO_COORD_COAC_PARA_ANALISE"),
	RETIRADO_DE_PAUTA(new Long(57), "Retirado de pauta", "RETIRADO_DE_PAUTA");
	
	private final Long id;
	
	private final String descricao;
	
	private final String tipo;
	
	private StatusProtocoloSiacol(Long id, String descricao, String tipo){
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public static Long getIdBy(StatusProtocoloSiacol tipo){
		
		for(StatusProtocoloSiacol s : StatusProtocoloSiacol.values()){
			if(s.equals(tipo)){
				return s.id;
			}
		}
		
		return null;
	}
	
	public static String getNomeBy(Long id){
		
		for(StatusProtocoloSiacol s : StatusProtocoloSiacol.values()){
			if(s.id.equals(id)){
				return s.descricao;
			}
		}
		
		return null;
	}
	
	public static StatusProtocoloSiacol getStatusNomeBy(String statusDescritivo){	
		for(StatusProtocoloSiacol s : StatusProtocoloSiacol.values()){
			if(s.tipo.equals(statusDescritivo)){
				return s;
			}
		}	
		return null;
	}
	
	public static String getTipoBy(Long id){
		
		for(StatusProtocoloSiacol s : StatusProtocoloSiacol.values()){
			if(s.id.equals(id)){
				return s.tipo;
			}
		}
		
		return null;
	}

	public static List<String> getAll () {
		StatusProtocoloSiacol[] vetor = StatusProtocoloSiacol.class.getEnumConstants();
		List<String> listaStatus = new ArrayList<String>();

		for (StatusProtocoloSiacol statusEnum : vetor) {
			listaStatus.add(statusEnum.toString());
		}
		Collections.sort(listaStatus);
		
		return listaStatus;
	}

}
