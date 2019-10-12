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
import br.org.crea.commons.models.siacol.dtos.relatorios.Rel14DepartamentosSiacolDto;
import br.org.crea.commons.models.siacol.dtos.relatorios.RelSiacol14Dto;
import br.org.crea.commons.models.siacol.enuns.TipoDocumentoSiacolEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

public class RelatorioSiacol14Dao extends GenericDao<ProtocoloSiacol, Serializable> {
	
	@Inject	HttpClientGoApi httpGoApi;
	@Inject	EventoDao eventoDao;
	@Inject	DepartamentoDao departamentoDao;
	@Inject	DepartamentoConverter departamentoConverter;
	@Inject	AssuntoDao assuntoCorporativoDao;
	@Inject	AssuntoSiacolDao assuntoDao;
	@Inject	AssuntoConverter assuntoConverter;
	
	String[] mesesDoAno = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	
	public RelatorioSiacol14Dao() {
		super(ProtocoloSiacol.class); // FIXME
	}
	
	public List<RelSiacol14Dto> quantidadeMensalDeProtocolosJulgadosNasReunioesPresenciaisEVirtuaisPorDepartamento(PesquisaRelatorioSiacolDto pesquisa) {

		List<DepartamentoDto> departamentos = pesquisa.getDepartamentos();
		if (pesquisa.isTodosDepartamentos()) {
			departamentos = departamentoConverter.toListDto(departamentoDao.getAllDepartamentos(ModuloSistema.SIACOL.getNome().toUpperCase()));
		}
		
		List<RelSiacol14Dto> listaRelatorio = new ArrayList<RelSiacol14Dto>();
		
		for (String mes : mesesDoAno) {
			RelSiacol14Dto relatorio = new RelSiacol14Dto();
			relatorio.setMes(mes + "/" + pesquisa.getAno());
			
			List<Rel14DepartamentosSiacolDto> depts = new ArrayList<Rel14DepartamentosSiacolDto>();
			
			for (DepartamentoDto departamento : departamentos) {
				Rel14DepartamentosSiacolDto dept = new Rel14DepartamentosSiacolDto();
				dept.setNome(departamento.getSigla());
				dept.setQtdReuniaoPresencial(qtdReuniao(pesquisa, departamento.getId(), mes, false));
				dept.setQtdReuniaoVirtual(qtdReuniao(pesquisa, departamento.getId(), mes, true));
				depts.add(dept);
			}
			
			relatorio.setDepartamentos(depts);
			listaRelatorio.add(relatorio);
		}
		
		listaRelatorio.add(linhaComTotais(departamentos, listaRelatorio));
		

		return listaRelatorio;
	}

	public int qtdReuniao(PesquisaRelatorioSiacolDto pesquisa, Long idDepartamento, String mes, boolean ehVirtual) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT COUNT(P.ID) AS QTD_REUNIAO                                           ");
		sql.append("		FROM SIACOL_PROTOCOLOS P                                                ");
		sql.append("	LEFT JOIN CAD_DOCUMENTO C ON (C.NUMERO_PROTOCOLO = P.NO_PROTOCOLO)          ");
		sql.append("	WHERE P.ID IN(                                                              ");
		sql.append("	  SELECT RL.FK_PROTOCOLO FROM SIACOL_DOCUMENTO_PROTOCOLO RL                 ");
		sql.append("       WHERE RL.FK_DOCUMENTO IN (SELECT R.FK_DOCUMENTO FROM SIACOL_REUNIAO R    ");
		sql.append("        WHERE R.VIRTUAL = :ehVirtual                                            ");
		sql.append("          AND TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                  ");
		sql.append("          AND TO_CHAR(R.HR_FIM, 'MM') = :mes)                                   ");
		sql.append("		  OR RL.FK_DOCUMENTO IN (SELECT R.FK_EXTRA_PAUTA FROM SIACOL_REUNIAO R  ");
		sql.append("		WHERE R.VIRTUAL = :ehVirtual                                            ");
		sql.append("		  AND TO_CHAR(R.HR_FIM, 'YYYY') = :ano                                  ");
		sql.append("		  AND TO_CHAR(R.HR_FIM, 'MM') = :mes))                                  ");
		sql.append("	AND C.FK_DEPARTAMENTO = :idDepartamento                                     ");
		sql.append("	AND C.NUMERO_DOCUMENTO IS NOT NULL                                          ");
		sql.append("	AND C.FK_TIPO_DOCUMENTO = :documentoDecisao                                 ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idDepartamento", idDepartamento);
			query.setParameter("ano", pesquisa.getAno());
			query.setParameter("mes", mes);
			query.setParameter("ehVirtual", ehVirtual);
			query.setParameter("documentoDecisao", TipoDocumentoSiacolEnum.DECISAO_DELIBERACAO.getId());
			
			String resultado = query.getSingleResult().toString();
			return Integer.parseInt(resultado);

		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("RelatorioSiacol14Dao || qtdReuniao", StringUtil.convertObjectToJson(pesquisa), e);
		}
		return 0;
	}

	private RelSiacol14Dto linhaComTotais(List<DepartamentoDto> departamentos, List<RelSiacol14Dto> listaRelatorio) {
		// POPULA TOTAL, necessário alterar o for primeiro dept, depois meses, pois ficará mais fácil fazer a soma
		RelSiacol14Dto relatorio = new RelSiacol14Dto();
		relatorio.setMes("TOTAL");
		List<Rel14DepartamentosSiacolDto> depts = new ArrayList<Rel14DepartamentosSiacolDto>();
		for (DepartamentoDto departamento : departamentos) {
			Rel14DepartamentosSiacolDto dept = new Rel14DepartamentosSiacolDto();
			dept.setNome(departamento.getSigla());
			
			int totalPresencial = 0;
			int totalVirtual = 0;
			
			for (RelSiacol14Dto rel : listaRelatorio) {
				for (Rel14DepartamentosSiacolDto depart : rel.getDepartamentos()) {
					if (depart.getNome().equals(departamento.getSigla())) {
						totalPresencial += depart.getQtdReuniaoPresencial();
						totalVirtual += depart.getQtdReuniaoVirtual();
					}
				}
			}
			
			dept.setQtdReuniaoPresencial(totalPresencial);
			dept.setQtdReuniaoVirtual(totalVirtual);
			
			depts.add(dept);			
		}
		relatorio.setDepartamentos(depts);
		return relatorio;
	}
}
