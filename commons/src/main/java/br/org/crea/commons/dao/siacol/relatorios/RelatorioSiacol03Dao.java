package br.org.crea.commons.dao.siacol.relatorios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.org.crea.commons.converter.cadastro.domains.DepartamentoConverter;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.cadastro.EventoDao;
import br.org.crea.commons.dao.cadastro.domains.DepartamentoDao;
import br.org.crea.commons.models.cadastro.Evento;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.enuns.ModuloSistema;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.PesquisaRelatorioSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelDepartamentosSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol03Dto;
import br.org.crea.commons.models.siacol.enuns.TipoDocumentoSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol03Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	EventoDao eventoDao;
	@Inject	DepartamentoDao departamentoDao;
	@Inject	DepartamentoConverter departamentoConverter;
	
	public RelatorioSiacol03Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public List<RelSiacol03Dto> quantidadeDeProtocolosJulgadosPorEventoDepartamentosEPorMesAno(PesquisaRelatorioSiacolDto pesquisa) {
		
		List<Evento> eventos = eventoDao.getAll();	
		eventos.sort(Comparator.comparing(Evento::getId));
		
		List<DepartamentoDto> departamentos = pesquisa.getDepartamentos();
		if (pesquisa.isTodosDepartamentos()) {
			departamentos = departamentoConverter.toListDto(departamentoDao.getAllDepartamentos(ModuloSistema.SIACOL.getNome().toUpperCase()));
		}
		
		List<RelSiacol03Dto> listaRelatorio = new ArrayList<RelSiacol03Dto>();
		
		for (Evento evento : eventos) {
			
			RelSiacol03Dto linhaEvento = new RelSiacol03Dto();
			
			linhaEvento.setEvento(evento.getId() + " - " + evento.getDescricao());
			
			List<RelDepartamentosSiacolDto> valoresDaLinhaEvento = new ArrayList<RelDepartamentosSiacolDto>();
			for (DepartamentoDto departamento : departamentos) {
				RelDepartamentosSiacolDto valorDaCelulaDepartamentoxEvento = new RelDepartamentosSiacolDto();
				
				valorDaCelulaDepartamentoxEvento.setNome(departamento.getSigla());
				
				List<String> protocolos = getProtocolosPorDepartamentoEPorEvento(pesquisa, departamento.getId(), evento.getId());
				
				if(!protocolos.isEmpty()) {
					valorDaCelulaDepartamentoxEvento.setProtocolos(protocolos);
				}
				
				valorDaCelulaDepartamentoxEvento.setQtd(protocolos.size());
				
				valoresDaLinhaEvento.add(valorDaCelulaDepartamentoxEvento);
			}
			linhaEvento.setDepartamentos(valoresDaLinhaEvento);
			listaRelatorio.add(linhaEvento);
		}
		
		return listaRelatorio;
	}
	
	public List<String> getProtocolosPorDepartamentoEPorEvento(PesquisaRelatorioSiacolDto pesquisa, Long idDepartamento, Long idEvento) {
		
		List<String> protocolos = new ArrayList<String>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.NO_PROTOCOLO FROM SIACOL_PROTOCOLOS P                         ");    
		sql.append("   LEFT JOIN CAD_DOCUMENTO C ON (C.NUMERO_PROTOCOLO = P.NO_PROTOCOLO)   ");   
		sql.append("  WHERE P.FK_EVENTO = :idEvento                                         ");
		sql.append("    AND C.FK_DEPARTAMENTO = :idDepartamento                             ");
		sql.append("    AND C.FK_TIPO_DOCUMENTO = :documentoDecisao                         ");
		sql.append("    AND C.NUMERO_DOCUMENTO IS NOT NULL                                  ");
		
		sql.append("    AND P.ID IN(                                                                ");
		sql.append("	 SELECT RL.FK_PROTOCOLO FROM SIACOL_DOCUMENTO_PROTOCOLO RL                  ");
		sql.append("	  WHERE RL.FK_DOCUMENTO IN (SELECT R.FK_DOCUMENTO FROM SIACOL_REUNIAO R     ");
		sql.append("	  WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                    ");
		if (pesquisa.temMeses()) {
			sql.append("	    AND TO_CHAR(R.HR_FIM, 'MM') = :mes                                  ");
		}
		sql.append("	   )                                                                        ");
		sql.append("	     OR RL.FK_DOCUMENTO IN (SELECT R.FK_EXTRA_PAUTA FROM SIACOL_REUNIAO R   ");
		sql.append("      WHERE TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                    ");
		if (pesquisa.temMeses()) {
			sql.append("		AND TO_CHAR(R.HR_FIM, 'MM') = :mes                                  ");
		}
		sql.append("		))                                                                      ");
				 
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			query.setParameter("idDepartamento", idDepartamento);
			query.setParameter("idEvento", idEvento);
			query.setParameter("ano", pesquisa.getAno());
			if (pesquisa.temMeses()) {
				query.setParameter("meses", pesquisa.getMeses());
			}
			
			Iterator<?> it = query.getResultList().iterator();

			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object result = (Object) it.next();
					
					protocolos.add(result != null ? result.toString() : "");
				}
			}
			
		} catch (NoResultException e) {
			return protocolos;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol03Dao || getProtocolosPorDepartamentoEPorEvento", StringUtil.convertObjectToJson(idDepartamento + " - " + idEvento + " - " + pesquisa), e);
		}
	 	return protocolos;
	}
}
