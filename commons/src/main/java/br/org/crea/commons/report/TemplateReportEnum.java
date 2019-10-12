package br.org.crea.commons.report;

import javax.servlet.http.HttpServletRequest;

public enum TemplateReportEnum {
	

    DOCUMENTO_GENERICO("/WEB-INF/templates/siacol/documento-generico.jrxml"),
	ENTREGA_CARTEIRA("/WEB-INF/templates/modelo-entrega-carteira.jrxml"),
	ART_PREVIEW_CARGO_FUNCAO("/WEB-INF/templates/art/art.jrxml"),
	ART_PREVIEW_OBRA_SERVICO("/WEB-INF/templates/art/art-preview-obra-servico.jrxml"),
	DETALHE_ART("/WEB-INF/templates/detalhe-art.jrxml"),
	SIACOL_OFICIO("/WEB-INF/templates/siacol/oficio.jrxml"),
	SIACOL_OFICIO_COORD("/WEB-INF/templates/siacol/oficioCoordenador.jrxml"),
	SIACOL_OFICIO_PRESIDENTE("/WEB-INF/templates/siacol/oficioPresidente.jrxml"),
	SIACOL_PARECER("/WEB-INF/templates/siacol/parecer.jrxml"),
	SIACOL_RELATORIO_VOTO("/WEB-INF/templates/siacol/relatorio_voto.jrxml"),
	SIACOL_DESPACHO("/WEB-INF/templates/siacol/despacho.jrxml"),
	SIACOL_DESPACHO_COORD("/WEB-INF/templates/siacol/despachoCoordenador.jrxml"),
	SIACOL_ANALISE_PROTOCOLO("/WEB-INF/templates/siacol/analise_protocolo.jrxml"),
	SIACOL_PAUTA("/WEB-INF/templates/siacol/pauta.jrxml"),
	SIACOL_PAUTA_PRESENCIAL("/WEB-INF/templates/siacol/pauta-presencial.jrxml"),
	SIACOL_EXTRA_PAUTA("/WEB-INF/templates/siacol/extra-pauta.jrxml"),
	SIACOL_DECISAO("/WEB-INF/templates/siacol/decisao.jrxml"),
	SIACOL_DELIBERACAO("/WEB-INF/templates/siacol/deliberacao.jrxml"),
	SIACOL_COMUNICADO("/WEB-INF/templates/siacol/comunicado.jrxml"),
	SIACOL_DECISAO_ADREFERENDUM("/WEB-INF/templates/siacol/decisaoAdReferendum.jrxml"),
	SIACOL_DECISAO_PRIMEIRA_INSTANCIA("/WEB-INF/templates/siacol/decisao.jrxml"),
	OFICIO_REGIONAL_ENVIO_DOC_PROFISSIONAL("/WEB-INF/templates/protocolo/oficio-regional-envio-documento-profissional.jrxml"),
	OFICIO_PROFISSIONAL_CUMPRIMENTO_EXIGENCIA("/WEB-INF/templates/protocolo/oficio-profissional-cumprimento-exigencia.jrxml"),
	OFICIO_PROFISSIONAL_ESCOLA_EXTINTA("/WEB-INF/templates/protocolo/oficio-profissional-escola-extinta.jrxml"),
	OFICIO_PROFISSIONAL_ESCOLA_NAO_CADASTRADA("/WEB-INF/templates/protocolo/oficio-profissional-escola-nao-cadastrada.jrxml"),
	OFICIO_IE_CONFIRMACAO_DATA_CURSO_FORMANDO("/WEB-INF/templates/protocolo/oficio-ie-confirmacao-data-curso-formando.jrxml"),
	OFICIO_IE_CONFIRMACAO_FORMANDO_OUTRO_ESTADO("/WEB-INF/templates/protocolo/oficio-ie-confirmacao-formando-outro-estado.jrxml"),
	OFICIO_IE_CURSO_NAO_CADASTRADO("/WEB-INF/templates/protocolo/oficio-ie-curso-nao-cadastrado.jrxml"),
	OFICIO_IE_CONFIRMACAO_FORMANDO("/WEB-INF/templates/protocolo/oficio-ie-confirmacao-formando.jrxml"),
	OFICIO_IE_ALTERACAO_NOME_CURSO("/WEB-INF/templates/protocolo/oficio-ie-alteracao-nome-curso.jrxml"),
	OFICIO_IE_CONFIRMACAO_FORMANDO_DOC_FALSO("/WEB-INF/templates/protocolo/oficio-ie-confirmacao-formando-doc-falso.jrxml"),
	OFICIO_IE_DECISAO_CADASTRO_CURSO("/WEB-INF/templates/protocolo/oficio-ie-decisao-cadastramento-curso.jrxml"),
	OFICIO_IE_DIVERGENCIA_CURSO("/WEB-INF/templates/protocolo/oficio-ie-divergencia-curso.jrxml"),
    OFICIO_IE_ALUNO_ORIUNDO_ESCOLA_EXTINTA("/WEB-INF/templates/protocolo/oficio-ie-aluno-oriundo-escola-extinta.jrxml"),
    DOCUMENTO_BAIXA_QUADRO_TECNICO("/WEB-INF/templates/protocolo/documentoBaixaQuadroTecnicoProcessoDigital.jrxml"),
    DOCUMENTO_SEGUNDA_VIA_CARTEIRA("/WEB-INF/templates/protocolo/documentoSegundaViaCarteiraProcessoDigital.jrxml"),
    DOCUMENTO_EMPTY_DEFAULT("/WEB-INF/templates/commons/documento-empty-default.jrxml");
    
	private String descricao;

	private TemplateReportEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public String getTemplate(HttpServletRequest request){
		String contextoProjeto = request.getSession().getServletContext().getRealPath("/");
		System.out.println("Http --> " + contextoProjeto);
		return contextoProjeto +  descricao;
	}
	
	public String getTemplatePath(String path){
		System.out.println("PathTemplate --> " + path +  descricao);
		return path +  descricao;
	}
	
	public static TemplateReportEnum getTemplatePorNome(String template){
		
		for(TemplateReportEnum t : TemplateReportEnum.values()){
			if(t.name().equals(template)){
				return t;
			}
		}
		
		return null;
	}

}
