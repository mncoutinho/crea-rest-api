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
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.dao.protocolo.AssuntoDao;
import br.org.crea.commons.dao.siacol.AssuntoSiacolDao;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDepartamentosSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol05Dto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.models.siacol.enuns.TipoDocumentoSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol05Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	DepartamentoDao departamentoDao;
	@Inject	DepartamentoConverter departamentoConverter;
	@Inject	AssuntoDao assuntoCorporativoDao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	@Inject	RelatorioSiacolCommonsDao commonsDao;
	
	String[] mesesDoAno = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	
	public RelatorioSiacol05Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public List<RelSiacol05Dto> detalhamentoDoSaldoAcumuladoPorDepartamentoMesEAssunto(PesquisaRelatorioSiacolDto pesquisa) {

		List<DepartamentoDto> departamentos = pesquisa.getDepartamentos();
		List<AssuntoDto> assuntos = pesquisa.getAssuntos();
		
		if (pesquisa.isTodosDepartamentos()) {
			departamentos = departamentoConverter.toListDto(departamentoDao.getAllDepartamentos("SIACOL")); 
		}
		
		if (pesquisa.isTodosAssuntos()) {
			assuntos = assuntoConverter.toListDtoSiacol(assuntoDao.getAllAssuntos());
		}
		
		List<Long> idsAssuntoSiacol = commonsDao.populaListaIdsAssuntos(assuntos);
		StatusProtocoloSiacol[] listaStatus = StatusProtocoloSiacol.class.getEnumConstants();
		
		List<RelSiacol05Dto> listaRelatorio = new ArrayList<RelSiacol05Dto>();
		
		for (StatusProtocoloSiacol status : listaStatus) {
		
			RelSiacol05Dto relatorio = new RelSiacol05Dto();
			relatorio.setStatus(status.getDescricao());
			List<RelDepartamentosSiacolDto> relDepartamentos = new ArrayList<RelDepartamentosSiacolDto>();
			
			for (DepartamentoDto departamento : departamentos) {
				RelDepartamentosSiacolDto relDepartamento = new RelDepartamentosSiacolDto();
				relDepartamento.setNome(departamento.getSigla());
				relDepartamento.setQtd(qtdPorStatusDepartamentoEAssuntos(departamento.getId(), status.getTipo(), idsAssuntoSiacol));
				
				relDepartamentos.add(relDepartamento);
			}
			
			relatorio.setDepartamentos(relDepartamentos);
			
			int total = 0;
			
			for (RelDepartamentosSiacolDto dept : relDepartamentos) {
				total += dept.getQtd();
			}
			
			relatorio.setTotal(total);
			
			listaRelatorio.add(relatorio);
		}
		
		listaRelatorio.add(linhaComTotais(listaRelatorio, departamentos, idsAssuntoSiacol));
		
		return listaRelatorio;
	}

	public int qtdPorStatusDepartamentoEAssuntos(Long idDepartamento, String status, List<Long> idsAssuntoSiacol) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT COUNT(P.STATUS) AS QTD                              ");
		sql.append("    FROM SIACOL_PROTOCOLOS P                                 ");
		sql.append("   WHERE (P.FK_ASSUNTO_SIACOL IN (:idsAssuntoSiacol)         ");
		sql.append("       OR P.FK_ASSUNTO_SIACOL IS NULL  )                     ");
		sql.append("      AND P.FK_DEPARTAMENTO = :idDepartamento                ");
		sql.append("      AND P.STATUS = :status                                 ");
		sql.append("      AND P.ATIVO = 1                                 		 ");
//			sql.append("	 AND P.NO_PROTOCOLO NOT IN                               ");
//			sql.append("	  (SELECT D.NUMERO_PROTOCOLO                             ");
//			sql.append("	     FROM CAD_DOCUMENTO D                                ");
//			sql.append("		WHERE D.FK_TIPO_DOCUMENTO = :documentoDecisao        ");
//			sql.append("          AND D.FK_DEPARTAMENTO = :idDepartamento            ");
//			sql.append("		  AND D.NUMERO_DOCUMENTO IS NOT NULL)                ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDepartamento", idDepartamento);
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			query.setParameter("status", status);
			query.setParameter("idsAssuntoSiacol", idsAssuntoSiacol);
			
			return Integer.parseInt(query.getSingleResult().toString());
			
		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog(
					"RelatorioSiacol05Dao || qtdPorStatusDepartamentoEAssuntos",
					StringUtil.convertObjectToJson(idDepartamento), e);
		}
		return 0;		
	}
	
	
	private RelSiacol05Dto linhaComTotais(List<RelSiacol05Dto> listaRelatorio, List<DepartamentoDto> departamentos, List<Long> idsAssuntoSiacol) {
		RelSiacol05Dto relatorio = new RelSiacol05Dto();
		relatorio.setStatus("TOTAL");
		List<RelDepartamentosSiacolDto> relDepartamentos = new ArrayList<RelDepartamentosSiacolDto>();
		
		for (DepartamentoDto departamento : departamentos) {
			RelDepartamentosSiacolDto relDepartamento = new RelDepartamentosSiacolDto();
			relDepartamento.setNome(departamento.getSigla());
			
			int total = 0;
			for (RelSiacol05Dto rel : listaRelatorio) {
				for (RelDepartamentosSiacolDto depto : rel.getDepartamentos()) {
					if (depto.getNome().equals(departamento.getNome())) {
						total += depto.getQtd();
					}
				}
			}
			relDepartamento.setQtd(total);
			
			relDepartamentos.add(relDepartamento);
		}
		
		relatorio.setDepartamentos(relDepartamentos);
		
		int total = 0;
		
		for (RelDepartamentosSiacolDto dept : relDepartamentos) {
			total += dept.getQtd();
		}
		
		relatorio.setTotal(total);
		
		return relatorio;
	}
}
