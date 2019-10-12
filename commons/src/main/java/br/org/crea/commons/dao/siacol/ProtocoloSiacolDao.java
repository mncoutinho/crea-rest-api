package br.org.crea.commons.dao.siacol;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.dao.corporativo.Page;
import br.org.crea.commons.models.cadastro.dtos.DepartamentoDto;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.commons.dtos.SituacaoProtocoloDto;
import br.org.crea.commons.models.corporativo.Assunto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.models.corporativo.dtos.PessoaDto;
import br.org.crea.commons.models.siacol.AssuntoSiacol;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.ConsultaProtocoloDto;
import br.org.crea.commons.models.siacol.dtos.GenericSiacolDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class ProtocoloSiacolDao extends GenericDao<ProtocoloSiacol, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;
	
	List<Long> listIdsAssuntoSiacol = new ArrayList<Long>();

	public ProtocoloSiacolDao() {
		super(ProtocoloSiacol.class);
	}

	public List<ProtocoloSiacol> getAllProtocolos(ConsultaProtocoloDto consulta) {

		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS WHERE 1 = 1 ");

		queryConsultaProtocolo(consulta, sql);

		try {
			
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			Page page = new Page(consulta.getPage(), 50);
			page.paginate(query);

			parametrosConsultaProtocolo(consulta, query);

			listProtocoloSiacol = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || getAllProtocoloSiacol", StringUtil.convertObjectToJson(consulta), e);
		}

		return listProtocoloSiacol;
	}
	
	public List<ProtocoloSiacol> getAllProtocolosSemPaginacao(ConsultaProtocoloDto consulta) {

		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS WHERE 1 = 1 ");

		queryConsultaProtocolo(consulta, sql);

		try {
			
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);

			parametrosConsultaProtocolo(consulta, query);

			listProtocoloSiacol = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || getAllProtocolosSemPaginacao", StringUtil.convertObjectToJson(consulta), e);
		}

		return listProtocoloSiacol;
	}

	public int quantidadeConsultaProtocolos(ConsultaProtocoloDto consulta) {

		List<ProtocoloSiacol> listProtocoloSiacol = new ArrayList<ProtocoloSiacol>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PS FROM ProtocoloSiacol PS WHERE 1 = 1 ");

		queryConsultaProtocolo(consulta, sql);

		try {
			TypedQuery<ProtocoloSiacol> query = null;
			query = em.createQuery(sql.toString(), ProtocoloSiacol.class);

			parametrosConsultaProtocolo(consulta, query);

			listProtocoloSiacol = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || quantidadeConsultaProtocolos", StringUtil.convertObjectToJson(consulta), e);
		}

		return listProtocoloSiacol.size();

	}

	public void parametrosConsultaProtocolo(ConsultaProtocoloDto consulta, TypedQuery<ProtocoloSiacol> query) {
		if (!consulta.getSituacoes().isEmpty()) {
			List<Long> listIds = new ArrayList<Long>();
			for (SituacaoProtocoloDto s : consulta.getSituacoes()) {
				listIds.add(s.getId());
			}
			query.setParameter("idsSituacao", listIds);
		}

		if (consulta.getIdPessoa() != null) {
			query.setParameter("idResponsavel", consulta.getIdPessoa());
		}

		if (consulta.getInteressado() != null) {
			query.setParameter("idInteressado", consulta.getInteressado());
		}

		if (consulta.getNumeroProtocolo() != null) {
			query.setParameter("numeroProtocolo", consulta.getNumeroProtocolo());
		}

		if (consulta.getNumeroProcesso() != null) {
			query.setParameter("numeroProcesso", consulta.getNumeroProcesso());
		}

		if (consulta.getInicioData() != null) {
			query.setParameter("dataInicio", consulta.getInicioData());
		}

		if (consulta.getFimData() != null) {
			query.setParameter("dataFim", consulta.getFimData());
		}

		if (!consulta.getResponsaveis().isEmpty()) {
			List<Long> listIds = new ArrayList<Long>();
			for (PessoaDto s : consulta.getResponsaveis()) {
				listIds.add(s.getId());
			}
			query.setParameter("idsResponsavel", listIds);
		}

		if (!consulta.getDepartamentos().isEmpty()) {
			List<Long> listIds = new ArrayList<Long>();
			for (DepartamentoDto s : consulta.getDepartamentos()) {
				listIds.add(new Long(s.getId()));
			}
			query.setParameter("idsDepartamento", listIds);
		}

		if (!consulta.getAssuntos().isEmpty()) {
			query.setParameter("listIdsAssuntoSiacol", listIdsAssuntoSiacol);
		}

		if (!consulta.getAssuntosProtocolo().isEmpty()) {
			List<Long> listIds = new ArrayList<Long>();
			for (AssuntoDto s : consulta.getAssuntosProtocolo()) {
				listIds.add(s.getId());
			}
			query.setParameter("idsAssuntoProtocolo", listIds);
		}

	}

	public void queryConsultaProtocolo(ConsultaProtocoloDto consulta, StringBuilder sql) {
		if (consulta.getIdPessoa() != null) {
			sql.append("AND PS.idResponsavel = :idResponsavel ");
			if (!consulta.isInativo()) {
				sql.append("AND PS.ativo = 1 ");
			}
		}
		

		if (consulta.getInteressado() != null) {
			sql.append("AND PS.interessado = :idInteressado ");
		}

		if (consulta.getNumeroProtocolo() != null) {
			sql.append("AND PS.numeroProtocolo = :numeroProtocolo ");
		}

		if (consulta.getNumeroProcesso() != null) {
			sql.append("AND PS.numeroProcesso = :numeroProcesso ");
		}

		if (consulta.getInicioData() != null && consulta.getFimData() != null) {
			sql.append("AND TO_CHAR(PS.dataSiacol, 'mm/dd/yyyy') >= TO_CHAR(:dataInicio, 'mm/dd/yyyy') ");
			sql.append("AND TO_CHAR(PS.dataSiacol, 'mm/dd/yyyy') <= TO_CHAR(:dataFim, 'mm/dd/yyyy') ");

		}

		if (!consulta.getSituacoes().isEmpty()) {
			sql.append("AND PS.situacao.id IN (:idsSituacao) ");
		}
		if (!consulta.getResponsaveis().isEmpty()) {
			sql.append("AND PS.idResponsavel IN (:idsResponsavel) ");
			if (!consulta.isInativo()) {
				sql.append("AND PS.ativo = 1 ");
			}
		}
		if (!consulta.getDepartamentos().isEmpty()) {
			sql.append("AND PS.departamento.id IN (:idsDepartamento) ");
		}
		
		
		if (!consulta.getAssuntos().isEmpty()) {
			listIdsAssuntoSiacol = new ArrayList<Long>();
			for (AssuntoDto s : consulta.getAssuntos()) {
				listIdsAssuntoSiacol.add(s.getId());
			}
			if (listIdsAssuntoSiacol.contains(0L)) {
				sql.append("AND ( PS.assuntoSiacol.id IN (:listIdsAssuntoSiacol) OR PS.assuntoSiacol is null) ");

			} else {
				sql.append("AND PS.assuntoSiacol.id IN (:listIdsAssuntoSiacol) ");
			}
		}
		
		if (!consulta.getAssuntosProtocolo().isEmpty()) {
			sql.append("AND PS.idAssuntoCorportativo IN (:idsAssuntoProtocolo) ");
		}
		
		if (!consulta.getClassificacao().isEmpty()) {
			sql.append("AND ( ");
			for (GenericDto s : consulta.getClassificacao()) {
				if (consulta.getClassificacao().indexOf(s) == 0) {
					sql.append("PS.classificacao LIKE '"+ s.getTipo() + "' ");
				}else{
					sql.append("OR PS.classificacao LIKE '"+ s.getTipo() + "' ");
				}
			}
			sql.append(" )");
		}
		
		if (!consulta.getStatus().isEmpty()) {
			sql.append("AND ( ");
			for (GenericDto s : consulta.getStatus()) {
				if (consulta.getStatus().indexOf(s) == 0) {
					sql.append("PS.status LIKE '"+ s.getTipo() + "' ");
				}else{
					sql.append("OR PS.status LIKE '"+ s.getTipo() + "' ");
				}
			}
			sql.append(" )");
		}
		
		if (!consulta.isInativo()) {
			sql.append("AND PS.ativo = 1 ");
		}

		if (consulta.getOrdenacao() != null) {
			String ordenacao = consulta.getOrdenacao();
			sql.append("ORDER BY " + ordenacao);
		} else {
			sql.append("ORDER BY PS.dataSiacol");
		}

	}

	public Boolean temHabilidadeParaRedistribuicao(GenericSiacolDto dto) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT A FROM Assunto A ");
		sql.append("	WHERE A.id IN (:listaAssuntos) ");
		sql.append("		AND A.id NOT IN ( ");
		sql.append("			SELECT HS.assunto.id FROM HabilidadePessoaSiacol HS ");
		sql.append("       			WHERE HS.departamento.id = :idDepartamento ");
		sql.append("       	   			AND HS.ativo = 1  ");
		sql.append("       	   			AND HS.idPessoa = :idResponsavel ");
		sql.append("       	   			AND HS.assunto.id IN (:listaAssuntos) ) ");

		try {
			TypedQuery<Assunto> query = em.createQuery(sql.toString(), Assunto.class);
			query.setParameter("idDepartamento", dto.getIdDepartamento());
			query.setParameter("idResponsavel", dto.getIdFuncionario());
			query.setParameter("listaAssuntos", dto.getListaId());

			return query.getResultList().isEmpty() ? true : false;

		} catch (NoResultException e) {
			return true;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || temHabilidadeParaRedistribuicao", StringUtil.convertObjectToJson(dto), e);
		}

		return false;
	}
		
	public void liberaProtocolo(Long idProtocolo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  SIACOL_PROTOCOLOS S ");
		sql.append("     SET S.RECEBIDO = 0 ");
		sql.append("	 WHERE S.ID = :idProtocolo ");

		try {
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProtocolo", idProtocolo);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || liberaProtocolo", StringUtil.convertObjectToJson(idProtocolo), e);
		}
		
	}
	
	public boolean possuiDocumento(Long numeroProtocolo, Long idTipoDocumento) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(D) FROM Documento D ");
		sql.append("	WHERE D.protocolo = :numeroProtocolo ");
		sql.append("	AND   D.tipo.id = :idTipoDocumento ");

		try {
			Query query = em.createQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			query.setParameter("idTipoDocumento", idTipoDocumento);
			
			Long quantidadeDocumento = (Long) query.getSingleResult();
			return quantidadeDocumento > new Long(0) ? true : false;

		} catch (NoResultException e) {
			return false;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || possuiDocumento", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return false;
	}
	
	public boolean temRelatorioVoto(Long numeroProtocolo) {
		return possuiDocumento(numeroProtocolo, new Long(1101)) ? true : false;
	}
	
	public boolean temSugestaoRelatorioVoto(Long numeroProtocolo) {
		return possuiDocumento(numeroProtocolo, new Long(1103)) ? true : false;
	}
	
	public boolean temDecisao(Long numeroProtocolo) {
		return possuiDocumento(numeroProtocolo, new Long(1102)) ? true : false;
	}
	
	public boolean temVistas(Long numeroProtocolo) {
		return possuiDocumento(numeroProtocolo, new Long(1108)) ? true : false;
	}
	
	public boolean temDecisaoPai(Long numeroProtocolo) {
		return possuiDocumento(numeroProtocolo, new Long(1102)) ? true : false;
	}
	
	public boolean temDecisaoPrimeiraInstancia(Long numeroProtocolo) {
		return possuiDocumento(numeroProtocolo, new Long(1115)) ? true : false;
	}
	
	public void ativaProtocolos(Long idDepartamento) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE SIACOL_PROTOCOLOS ");
		sql.append("	SET ATIVO = 1  ");
		sql.append("	WHERE FK_DEPARTAMENTO = :idDepartamento ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			
			query.setParameter("idDepartamento", idDepartamento);
			
			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || ativaProtocolos", "SEM PARAMETRO", e);
		}
	}
	
	public ProtocoloSiacol getProtocoloBy(Long numero) {
		ProtocoloSiacol protocolo = new ProtocoloSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM ProtocoloSiacol P ");
		sql.append("	WHERE P.numeroProtocolo = :numero ");

		try {
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("numero", numero);
			
			protocolo = query.getSingleResult();
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || Get Protocolo By", StringUtil.convertObjectToJson(numero), e);
		}
		return protocolo;
	}
	
	public List<ProtocoloSiacol> buscaProtocoloOficio(String status, Long idDepartamento) {
		
		List<ProtocoloSiacol> listProtocolo = new ArrayList<ProtocoloSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT P FROM ProtocoloSiacol P ");
		sql.append("		WHERE P.departamento.id = :idDepartamento ");
		if (status.equals("TODOS")) {
			sql.append("		AND P.status in ('ASSINAR_OFICIO','OFICIO_ASSINADO','OFICIO_ENVIADO') ");
		}else {
			sql.append("		AND P.status LIKE '" +status+ "' ");
		}
		sql.append("		AND P.ativo = true ");
		
		try {
			TypedQuery<ProtocoloSiacol> query = em.createQuery(sql.toString(), ProtocoloSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			
			listProtocolo = query.getResultList();
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || Get Protocolo By", StringUtil.convertObjectToJson(status), e);
		}
		return listProtocolo;
	}
	
	public boolean protocoloJaEstaSiacol(Long numeroProtocolo) {
		return getProtocoloBy(numeroProtocolo) != null ? true : false;
	}

	public List<AssuntoSiacol> getAssuntosParaPauta(Long idDepartamento) {
		List<AssuntoSiacol> listAssunto = new ArrayList<AssuntoSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT PS.assuntoSiacol FROM ProtocoloSiacol PS ");
		sql.append("	WHERE PS.departamento.id = :idDepartamento ");
		sql.append("		AND (PS.status LIKE 'A_PAUTAR' ");
		sql.append("		OR PS.status LIKE 'A_PAUTAR_PRESENCIAL' ");
		sql.append("		OR PS.status LIKE 'A_PAUTAR_SEM_QUORUM' ");
		sql.append("		OR PS.status LIKE 'A_PAUTAR_VISTAS' ");
		sql.append("		OR PS.status LIKE 'A_PAUTAR_DESTAQUE' ) ");
		sql.append("		AND PS.classificacao != 'NAO_CLASSIFICADO' ");

		try {
			TypedQuery<AssuntoSiacol> query = em.createQuery(sql.toString(), AssuntoSiacol.class);
			query.setParameter("idDepartamento", idDepartamento);
			
			listAssunto = query.getResultList();
			
		}catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || getAssuntosParaPauta", StringUtil.convertObjectToJson(idDepartamento), e);
		}
		return listAssunto;
	}

	public void encaminharParaUltimoAnalista(Long idProtocolo, Long ultimoAnalista, String nome) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_PROTOCOLOS P                ");
		sql.append("  SET P.FK_RESPONSAVEL = :idResponsavel,   ");
		sql.append("      P.DS_RESPONSAVEL = :nomeResponsavel  ");
		sql.append("	   WHERE P.id = :idProtocolo           ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProtocolo", idProtocolo);
			query.setParameter("idResponsavel", ultimoAnalista);
			query.setParameter("nomeResponsavel", nome);
			
			query.executeUpdate();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || encaminharParaUltimoAnalista", StringUtil.convertObjectToJson(idProtocolo + " -- " + ultimoAnalista + " -- " + nome), e);
		}
		
	}

	public boolean temAnexoNoSiacol(Long numeroProtocoloPai) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT PRT.NUMERO FROM PRT_PROTOCOLOS PRT   ");
		sql.append("    WHERE PRT.FK_ID_PROTOCOLOS_ANEXO =         ");
		sql.append("    (SELECT ID FROM PRT_PROTOCOLOS             ");
		sql.append("     WHERE NUMERO = :numeroProtocoloPai)       ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocoloPai", numeroProtocoloPai);
			
			return query.getResultList().size() > 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || temAnexoNoSiacol", StringUtil.convertObjectToJson(numeroProtocoloPai), e);
		}
		return false;
	}
	
	public boolean estaAnexadoAProtocoloNoSiacol(Long numeroProtocolo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT PRT.NUMERO FROM PRT_PROTOCOLOS PRT       ");
		sql.append("    WHERE PRT.NUMERO = :numeroProtocolo            ");
		sql.append("      AND PRT.FK_ID_PROTOCOLOS_ANEXO IS NOT NULL   ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			
			return query.getResultList().size() > 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || estaAnexadoAProtocoloNoSiacol", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return false;
	}

	public Long getNumeroProtocoloPaiNoSiacol(Long numeroProtocolo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT A.NUMERO                                                  ");
		sql.append("     FROM PRT_PROTOCOLOS PRT                                        ");
		sql.append("     JOIN PRT_PROTOCOLOS A ON (PRT.FK_ID_PROTOCOLOS_ANEXO = A.ID)   ");
		sql.append("    WHERE PRT.NUMERO = :numeroProtocolo                             ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocolo", numeroProtocolo);
			
			BigDecimal resultado = (BigDecimal) query.getSingleResult();
			return resultado.setScale(0, BigDecimal.ROUND_UP).longValueExact();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || getProtocoloPaiNoSiacol", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}
		return null;
	}

	public List<ProtocoloSiacol> getListProtocolosAnexosNoSiacol(Long numeroProtocoloPai) {

		List<Long> listaNumerosProtocolos = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT P.NO_PROTOCOLO FROM SIACOL_PROTOCOLOS P         ");
		sql.append("  WHERE P.NO_PROTOCOLO IN (                             ");
		sql.append("   SELECT PRT.NUMERO FROM PRT_PROTOCOLOS PRT            ");
		sql.append("    WHERE PRT.FK_ID_PROTOCOLOS_ANEXO =                  ");
		sql.append("    (SELECT ID FROM PRT_PROTOCOLOS                      ");
		sql.append("      WHERE NUMERO = :numeroProtocoloPai)               ");
		sql.append("   )                                                    ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("numeroProtocoloPai", numeroProtocoloPai);
			
			for (Object rl : query.getResultList()) {
				BigDecimal resultado = (BigDecimal) rl;
				listaNumerosProtocolos.add(resultado.setScale(0, BigDecimal.ROUND_UP).longValueExact());
			}
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("ProtocoloSiacolDao || getListProtocolosAnexosNoSiacol", StringUtil.convertObjectToJson(numeroProtocoloPai), e);
		}
		
		List<ProtocoloSiacol> protocolos = new ArrayList<ProtocoloSiacol>();
		
		if (listaNumerosProtocolos.size() > 0) {
			
			StringBuilder sql2 = new StringBuilder();
			sql2.append(" SELECT P FROM ProtocoloSiacol P                   ");
			sql2.append("  WHERE P.numeroProtocolo IN (:listaNumerosProtocolos) ");

			try {
				TypedQuery<ProtocoloSiacol> query = em.createQuery(sql2.toString(), ProtocoloSiacol.class);
				query.setParameter("listaNumerosProtocolos", listaNumerosProtocolos);
				
				protocolos = query.getResultList();
				
			} catch (NoResultException e) {
				return null;
			} catch (Throwable e) {
				httpGoApi.geraLog("ProtocoloSiacolDao || getListProtocolosAnexosNoSiacol", StringUtil.convertObjectToJson(numeroProtocoloPai), e);
			}
		}
		
		return protocolos;
	}

}
