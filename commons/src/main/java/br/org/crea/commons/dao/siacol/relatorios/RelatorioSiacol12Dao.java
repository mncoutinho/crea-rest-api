package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.converter.protocolo.AssuntoConverter;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.cadastro.EventoDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol12Dto;
import br.org.crea.commons.models.siacol.enuns.TipoDocumentoSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.EstatisticaUtil;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol12Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	EventoDao eventoDao;
	@Inject	DepartamentoDao departamentoDao;
	@Inject	DepartamentoConverter departamentoConverter;
	@Inject	AssuntoDao assuntoCorporativoDao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	@Inject	RelatorioSiacolCommonsDao commonsDao;
	
	String[] mesesDoAno = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	
	public RelatorioSiacol12Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public List<RelSiacol12Dto> somatorioDoTotalDeDecisoesPorDepartamento(PesquisaRelatorioSiacolDto pesquisa) {
		boolean ehFavoravel = true;
		
		List<RelSiacol12Dto> listaRelatorio = new ArrayList<RelSiacol12Dto>();
		if (pesquisa.isTodosDepartamentos()) {
			List<DepartamentoDto> listDepartamentosDto = departamentoConverter.toListDto(departamentoDao.getAllDepartamentos(ModuloSistema.SIACOL.toString()));
			pesquisa.setDepartamentos(listDepartamentosDto);
		}
		
		for (DepartamentoDto departamento : pesquisa.getDepartamentos()) {
			RelSiacol12Dto rel = new RelSiacol12Dto();
			rel.setDepartamento(departamento.getSigla());
			pesquisa.setDepartamento(departamento);
			rel.setQtdDecisoesFavoraveis(qtdDecisoes(pesquisa, ehFavoravel));
			rel.setQtdDecisoesDesfavoraveis(qtdDecisoes(pesquisa, !ehFavoravel));
			rel.setQtdDecisoesHomologacao(qtdDecisoesHomologacao(pesquisa));
			rel.setQtdDecisoesAssunto(qtdDecisoesAssunto(pesquisa));
			rel.setQtdNaoClassificados(commonsDao.qtdNaoClassificado(pesquisa));
			
			int total = rel.getQtdDecisoesFavoraveis() + rel.getQtdDecisoesDesfavoraveis();
			rel.setQtdTotalDepartamento(total);
			listaRelatorio.add(rel);
		}
		
		listaRelatorio.add(linhaComTotais(listaRelatorio));
		
		return listaRelatorio;
	}
	
	public int qtdDecisoes(PesquisaRelatorioSiacolDto pesquisa, boolean ehFavoravel) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT COUNT(P.NO_PROTOCOLO)                                             ");
		sql.append("    FROM SIACOL_PROTOCOLOS P                                               ");
		sql.append("    LEFT JOIN CAD_DOCUMENTO D ON (D.NUMERO_PROTOCOLO = P.NO_PROTOCOLO)     ");
		sql.append("   WHERE 1 = 1                                                             ");
		if (ehFavoravel) {
			sql.append("     AND P.CLASSIFICACAO_FINAL = 'FAVORAVEL'                           ");
		} else {
			sql.append("     AND P.CLASSIFICACAO_FINAL = 'DESFAVORAVEL'                        ");
		}
		sql.append("     AND D.FK_DEPARTAMENTO = :idDepartamento                               ");
		sql.append("     AND D.NUMERO_DOCUMENTO IS NOT NULL                                    ");
		sql.append("     AND D.FK_TIPO_DOCUMENTO = :documentoDecisao                           ");
		
		sql.append("    AND P.ID IN(                                                                ");
		sql.append("	 SELECT RL.FK_PROTOCOLO FROM SIACOL_DOCUMENTO_PROTOCOLO RL                  ");
		sql.append("	  WHERE RL.FK_DOCUMENTO IN (SELECT R.FK_DOCUMENTO FROM SIACOL_REUNIAO R     ");
		sql.append("	  WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano)                                   ");
		sql.append("	     OR RL.FK_DOCUMENTO IN (SELECT R.FK_EXTRA_PAUTA FROM SIACOL_REUNIAO R   ");
		sql.append("      WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano))                                  ");
			
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDepartamento", pesquisa.getDepartamento().getId());
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			query.setParameter("ano", pesquisa.getAno());
			
			return Integer.parseInt(query.getSingleResult().toString());
			
		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol12Dao || qtdDecisoesFavoraveis", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return 0;
	}

	public int qtdDecisoesHomologacao(PesquisaRelatorioSiacolDto pesquisa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(P.NO_PROTOCOLO)                                             ");
		sql.append("   FROM SIACOL_PROTOCOLOS P                                               ");
		sql.append("   LEFT JOIN CAD_DOCUMENTO D ON (D.NUMERO_PROTOCOLO = P.NO_PROTOCOLO)     ");
		sql.append("  WHERE D.FK_DEPARTAMENTO = :idDepartamento                               ");
		sql.append("    AND D.FK_TIPO_DOCUMENTO = :documentoDecisao                           ");
		sql.append("    AND D.NUMERO_DOCUMENTO IS NOT NULL                                    ");
		
		sql.append("   AND ((P.AD_REFERENDUM = 1                                              ");
		sql.append("     OR P.PROVISORIO = 1                                                  ");
		sql.append("     OR P.HOMOLOGACAO_PF = 1)                                             ");
		sql.append("     OR (P.NO_PROTOCOLO IN (SELECT H.NO_PROTOCOLO       "); 
		sql.append("         	     FROM SIACOL_PROTOCOLO_HIST_TRAMITE H        "); 
		sql.append("       	     WHERE H.AD_REFERENDUM = 1 OR H.PROVISORIO = 1 OR H.HOMOLOGACAO_PF = 1)))  ");		
		sql.append("    AND P.ID IN(                                                                ");
		sql.append("	 SELECT RL.FK_PROTOCOLO FROM SIACOL_DOCUMENTO_PROTOCOLO RL                  ");
		sql.append("	  WHERE RL.FK_DOCUMENTO IN (SELECT R.FK_DOCUMENTO FROM SIACOL_REUNIAO R     ");
		sql.append("	  WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano)                                   ");
		sql.append("	     OR RL.FK_DOCUMENTO IN (SELECT R.FK_EXTRA_PAUTA FROM SIACOL_REUNIAO R   ");
		sql.append("      WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano))                                  ");
	
			
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDepartamento", pesquisa.getDepartamento().getId());
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			query.setParameter("ano", pesquisa.getAno());
			
			return Integer.parseInt(query.getSingleResult().toString());
			
		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol12Dao || qtdDecisoesHomologacao", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return 0;
	}
	
	public int qtdDecisoesAssunto(PesquisaRelatorioSiacolDto pesquisa) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT COUNT(D.ID) FROM CAD_DOCUMENTO D                   ");
		sql.append("    WHERE D.FK_TIPO_DOCUMENTO = :documentoDecisao            ");
		sql.append("     AND D.FK_DEPARTAMENTO = :idDepartamento                 ");
		sql.append("      AND D.NUMERO_DOCUMENTO IN                              ");
		sql.append("      (SELECT RL.NUMERO_DOCUMENTO                            ");
		sql.append("         FROM SIACOL_DOCUMENTO_PROTOCOLO RL                  ");
		sql.append("        WHERE RL.RESULTADO = 'ASSUNTO_VOTADO'                ");       
		sql.append("          AND (RL.FK_DOCUMENTO IN                            ");
		sql.append("           (SELECT FK_DOCUMENTO FROM SIACOL_REUNIAO          ");
		sql.append("             WHERE FK_DEPARTAMENTO = :idDepartamento         ");
		sql.append("               AND TO_CHAR(DATA_REUNIAO, 'YYYY') = :ano)     ");
		sql.append("          OR RL.FK_DOCUMENTO IN                              ");
		sql.append("           (SELECT FK_EXTRA_PAUTA FROM SIACOL_REUNIAO        ");
		sql.append("             WHERE FK_DEPARTAMENTO = :idDepartamento         ");
		sql.append("               AND TO_CHAR(DATA_REUNIAO, 'YYYY') = :ano) )   ");
		sql.append("      )                                                      ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDepartamento", pesquisa.getDepartamento().getId());
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			query.setParameter("ano", pesquisa.getAno());
			
			return Integer.parseInt(query.getSingleResult().toString());
			
		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol12Dao || qtdDecisoesAssunto", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return 0;
	}
	
	private RelSiacol12Dto linhaComTotais(List<RelSiacol12Dto> listaRelatorio) {
		RelSiacol12Dto rel = new RelSiacol12Dto();
		rel.setDepartamento("TOTAL");
		rel.setQtdDecisoesFavoraveis(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol12Dto::getQtdDecisoesFavoraveis)));
		rel.setQtdDecisoesDesfavoraveis(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol12Dto::getQtdDecisoesDesfavoraveis)));
		rel.setQtdDecisoesHomologacao(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol12Dto::getQtdDecisoesHomologacao)));
		rel.setQtdDecisoesAssunto(EstatisticaUtil.soma(listaRelatorio.stream().map(RelSiacol12Dto::getQtdDecisoesAssunto)));
		
		int total = rel.getQtdDecisoesFavoraveis() + rel.getQtdDecisoesDesfavoraveis();
		rel.setQtdTotalDepartamento(total);
		return rel;
	}
	
}
