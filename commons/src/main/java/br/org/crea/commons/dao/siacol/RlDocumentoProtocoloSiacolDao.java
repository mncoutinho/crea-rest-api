package br.org.crea.commons.dao.siacol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.Documento;
import br.org.crea.commons.models.commons.dtos.GenericDto;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.dtos.ApuracaoReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ConsultaProtocoloDto;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaItemPautaSiacolDto;
import br.org.crea.commons.models.siacol.dtos.ReuniaoSiacolDto;
import br.org.crea.commons.models.siacol.enuns.ResultadoVotacaoSiacolEnum;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RlDocumentoProtocoloSiacolDao extends GenericDao<RlDocumentoProtocoloSiacol, Serializable> {

	@Inject	HttpClientGoApi httpGoApi;
	@Inject ProtocoloSiacolDao protocoloSiacolDao;

	public RlDocumentoProtocoloSiacolDao() {
		super(RlDocumentoProtocoloSiacol.class);
	}

	public List<ProtocoloSiacol> getListProtocolos(Long idDocumento) {

		List<RlDocumentoProtocoloSiacol> listDocumentoProtocolo = new ArrayList<RlDocumentoProtocoloSiacol>();
		List<ProtocoloSiacol> listProtocolos = new ArrayList<ProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.documento.id = :idDocumento ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idDocumento", idDocumento);

			listDocumentoProtocolo = query.getResultList();

			for (RlDocumentoProtocoloSiacol rl : listDocumentoProtocolo) {
				listProtocolos.add(rl.getProtocolo());
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getListProtocolos", StringUtil.convertObjectToJson(idDocumento), e);
		}

		return listProtocolos;
	}

	public void cadastraProtocolosFechamentoPauta(List<ProtocoloSiacol> listProtocolos, Documento pauta) {

		try {
			int index=1;
			for (ProtocoloSiacol protocoloPautado : listProtocolos) {
				protocoloPautado.setUltimoStatus(protocoloPautado.getStatus());
				protocoloPautado.setStatus(StatusProtocoloSiacol.PAUTADO);
				protocoloPautado.setConselheiroRelator(protocoloPautado.getIdResponsavel());
				protocoloPautado.setIdResponsavel(new Long(0));
				protocoloSiacolDao.update(protocoloPautado);
				RlDocumentoProtocoloSiacol rlDocumentoProtocolo = new RlDocumentoProtocoloSiacol();
				rlDocumentoProtocolo.setDocumento(pauta);
				rlDocumentoProtocolo.setProtocolo(protocoloPautado);
				rlDocumentoProtocolo.setDescricaoItem("");
				rlDocumentoProtocolo.setItem("1."+index);
				create(rlDocumentoProtocolo);
				index++;
			}

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || cadastraProtocolosFechamentoPauta", StringUtil.convertObjectToJson(pauta), e);
		}
	}

	public void deletaItemPauta(Long idItem) {

		deletaAnexoPorIdItem(idItem);

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  SIACOL_DOCUMENTO_PROTOCOLO S ");
		sql.append("	 WHERE S.ID = :idItem ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idItem", idItem);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || deletaItemPauta", StringUtil.convertObjectToJson(idItem), e);
		}

	}

	public void deletaAnexoPorIdItem(Long idItem) {

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  SIACOL_ANEXO_ITEM_PAUTA S ");
		sql.append("	 WHERE S.FK_ITEM = :idAnexo ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idItem", idItem);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("AnexoItemPautaSiacolDao || deletaAnexoPorIdItem", StringUtil.convertObjectToJson(idItem), e);
		}

	}

	public List<RlDocumentoProtocoloSiacol> pesquisa(PesquisaItemPautaSiacolDto pesquisa) {

		List<RlDocumentoProtocoloSiacol> listModel = new ArrayList<RlDocumentoProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.documento.id IN (:idsPautas) ");
		
		if (pesquisa.isTemSolicitacaoVistas()) {
			sql.append("		AND RL.solicitacaoVista <> 0  AND RL.status.id <> 4 ");
		}
	
		if (pesquisa.isSomenteProtocolos()) {
			sql.append("		AND RL.protocolo.id IS NOT NULL ");
		}
		
		if (pesquisa.isTemEnquete()) {
			sql.append("		AND RL.temEnquete = 1           ");
		}
		
		if (pesquisa.isTemObservacao()) {
			sql.append("		AND RL.obsReuniao IS NOT NULL ");
		}
		
		if (pesquisa.getStatus() != 99) {
			if (pesquisa.isEmVotacao()) {
				sql.append("		AND RL.emVotacao = 1 ");
			} else if (pesquisa.getStatus().equals(2L)) {
				sql.append("		AND RL.status.id IN (2, 15) ");
			} else {
				sql.append("		AND RL.status.id = :idStatus ");
			}
		} else {
			sql.append("		AND RL.status.id NOT IN (1,5,6,7, 16) ");
		}

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idsPautas", pesquisa.getIdsPautas());
//			if (!pesquisa.estaEmVotacao() && pesquisa.getStatus() != 99 && !pesquisa.isSomenteProtocolos()) {
//				query.setParameter("idStatus", pesquisa.getStatus());
//			}
			if (pesquisa.getStatus() != 99) {
				if (!pesquisa.isEmVotacao() && !pesquisa.getStatus().equals(2L)) {
					query.setParameter("idStatus", pesquisa.getStatus());
				} 
			}

			listModel = query.getResultList();

		} catch (NoResultException e) {
			return listModel;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || pesquisa", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listModel;

	}
	
	public List<RlDocumentoProtocoloSiacol> pesquisaEncerramento(PesquisaItemPautaSiacolDto pesquisa) {

		List<RlDocumentoProtocoloSiacol> listModel = new ArrayList<RlDocumentoProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT RL FROM RlDocumentoProtocoloSiacol RL                   ");
		sql.append("  WHERE RL.documento.id IN (:idsPautas)                         ");
		
		if (pesquisa.isTemVistasConcedida() != null) {
			if (pesquisa.isTemVistasConcedida()) {
				sql.append("		AND RL.idPessoaVista IS NOT NULL                    ");
				sql.append("		AND RL.solicitacaoVista = 0                         ");
			} else {
				sql.append("		AND ((RL.idPessoaVista IS NOT NULL                  ");
				sql.append("		  AND RL.solicitacaoVista = 1)                      ");
				sql.append("		OR (RL.idPessoaVista IS NULL                        ");
				sql.append("		AND RL.solicitacaoVista = 0))                       ");
			}
		}
		if (pesquisa.isSomenteProtocolos()) {
			sql.append("		AND RL.protocolo.id IS NOT NULL                     ");
		}
		
		if (pesquisa.isSomenteItensSemProtocolos() != null) {
			if (pesquisa.isSomenteItensSemProtocolos()) {
				sql.append("		AND RL.protocolo.id IS NULL                         ");
			}
		}
		
		if (pesquisa.isTemEnquete()) {
			sql.append("		AND RL.temEnquete = 1                               ");
		}
		
		if (pesquisa.isTemObservacao()) {
			sql.append("		AND RL.obsReuniao IS NOT NULL                       ");
		}
		
		if (pesquisa.isTemUrgencia()) {
			sql.append("		AND RL.urgencia = 1                                 ");
		} else {
			sql.append("		AND (RL.urgencia = 0 OR RL.urgencia IS NULL)        ");
		}
		
		if (pesquisa.getStatus() != null) {
			if (pesquisa.getStatus() != 99) {
				if (pesquisa.isEmVotacao()) {
					sql.append("		AND RL.emVotacao = 1                            ");
				} else {
					sql.append("		AND RL.status.id = :idStatus                    ");
				}
			} else {
				sql.append("		AND RL.status.id NOT IN (1, 5, 6, 7, 16)                ");
			}
		}

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idsPautas", pesquisa.getIdsPautas());
			if (pesquisa.getStatus() != null) {
				if (pesquisa.getStatus() != 99) {
					if (!pesquisa.isEmVotacao()) {
						query.setParameter("idStatus", pesquisa.getStatus());
					} 
				}
			}
			listModel = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || pesquisa", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listModel;

	}

	public void alteraStatusItemPauta(Long id, Long status) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  SIACOL_DOCUMENTO_PROTOCOLO S ");
		sql.append("     SET S.FK_STATUS = :status ");
		sql.append("	 WHERE S.ID = :id ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", id);
			query.setParameter("status", status);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || alteraStatusItemPauta", StringUtil.convertObjectToJson(id), e);
		}

	}

	public List<RlDocumentoProtocoloSiacol> getItensEmVotacao(List<Long> idsPautas) {

		List<RlDocumentoProtocoloSiacol> listModel = new ArrayList<RlDocumentoProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.documento.id IN (:idsPautas) ");
		sql.append("		AND RL.emVotacao = 1 ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idsPautas", idsPautas);

			listModel = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getItensEmVotacao", StringUtil.convertObjectToJson(idsPautas), e);
		}

		return listModel;

	}

	public void retiraDeVotacao(List<Long> idsPauta) {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE  SIACOL_DOCUMENTO_PROTOCOLO S    ");
		sql.append("  SET S.EM_VOTACAO = 0                   ");
		sql.append("	 WHERE S.FK_DOCUMENTO IN (:idsPauta) ");
		sql.append("	   AND S.EM_VOTACAO = 1              ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsPauta", idsPauta);

			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || retiraTodosDeVotacao", StringUtil.convertObjectToJson("sem-parametro"), e);
		}

	}

	public void colocaEmVotacao(Long id) {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE  SIACOL_DOCUMENTO_PROTOCOLO S ");
		sql.append("    SET S.EM_VOTACAO = 1              ");
		sql.append("	    WHERE S.ID = :id              ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", id);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || colocaEmVotacao", StringUtil.convertObjectToJson(id), e);
		}

	}

	public void retiraDeVotacaoEAtualizaParaVotado(List<Long> idsPauta, ApuracaoReuniaoSiacolDto apuracao) {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_DOCUMENTO_PROTOCOLO S             ");
		sql.append("    SET S.EM_VOTACAO = 0,                        ");
		sql.append("        S.FK_STATUS = 1,                         ");
		sql.append("        S.TOTAL_VOTOS_SIM = :qtdSim,             ");
		sql.append("        S.TOTAL_VOTOS_NAO = :qtdNao,             ");
		sql.append("        S.TOTAL_VOTOS_ABSTENCAO = :qtdAbstencao  ");
		sql.append("  WHERE S.FK_DOCUMENTO IN (:idsPauta)            ");
		sql.append("    AND S.EM_VOTACAO = 1                         ");
		sql.append("    AND S.FK_STATUS <> 15                        ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsPauta", idsPauta);
			query.setParameter("qtdSim", apuracao.getQtdSim());
			query.setParameter("qtdNao", apuracao.getQtdNao());
			query.setParameter("qtdAbstencao", apuracao.getQtdAbstencao());

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || retiraDeVotacaoEAtualizaParaVotado", StringUtil.convertObjectToJson(idsPauta), e);
		}

	}
	
	public void retiraDeVotacaoEAtualizaParaVotadoHomologacaoProfissional(List<Long> idsPauta, ApuracaoReuniaoSiacolDto apuracao) {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_DOCUMENTO_PROTOCOLO S             ");
		sql.append("    SET S.EM_VOTACAO = 0,                        ");
		sql.append("        S.FK_STATUS = 16,                        ");
		sql.append("        S.TOTAL_VOTOS_SIM = :qtdSim,             ");
		sql.append("        S.TOTAL_VOTOS_NAO = :qtdNao,             ");
		sql.append("        S.TOTAL_VOTOS_ABSTENCAO = :qtdAbstencao  ");
		sql.append("  WHERE S.FK_DOCUMENTO IN (:idsPauta)            ");
		sql.append("    AND S.EM_VOTACAO = 1                         ");
		sql.append("    AND S.FK_STATUS = 15                         ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsPauta", idsPauta);
			query.setParameter("qtdSim", apuracao.getQtdSim());
			query.setParameter("qtdNao", apuracao.getQtdNao());
			query.setParameter("qtdAbstencao", apuracao.getQtdAbstencao());

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || retiraDeVotacaoEAtualizaParaVotado", StringUtil.convertObjectToJson(idsPauta), e);
		}

	}

	public List<RlDocumentoProtocoloSiacol> getItensByIdDocumento(Long idDocumento) {

		List<RlDocumentoProtocoloSiacol> listModel = new ArrayList<RlDocumentoProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.documento.id IN (:idDocumento) ");
		sql.append("	ORDER BY RL.item ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idDocumento", idDocumento);

			listModel = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getItensByIdDocumento", StringUtil.convertObjectToJson(idDocumento), e);
		}

		return listModel;
	}
	
	public String getUltimoItem(Long idDocumento) {

		List<RlDocumentoProtocoloSiacol> listModel = new ArrayList<RlDocumentoProtocoloSiacol>();
		String ultimoItem = new String();
		String proximoItem = new String();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.documento.id IN (:idDocumento) ");
		sql.append("	ORDER BY RL.item DESC ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idDocumento", idDocumento);

			listModel = query.getResultList();
			ultimoItem = listModel.get(0).getItem();
			String[] itemSeparado = ultimoItem.split("[.]");
			proximoItem = itemSeparado[0]+"."+Long.toString((Long.valueOf(itemSeparado[1]) + 1));
			
		} catch (NoResultException e){
			return "1";
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getItensByIdDocumento", StringUtil.convertObjectToJson(idDocumento), e);
		}

		return proximoItem;
	}

	public List<RlDocumentoProtocoloSiacol> buscaRevisaoDecisaoItens(ConsultaProtocoloDto dto) {
		List<RlDocumentoProtocoloSiacol> listModel = new ArrayList<RlDocumentoProtocoloSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL, ReuniaoSiacol RS ");
		sql.append("	WHERE RS.departamento.id = :idDepartamento ");
		sql.append("		AND ( RL.status = 1 OR RL.status = 9 OR RL.status = 16  ) ");
		sql.append("		AND RL.protocolo is null ");
		sql.append("		AND ( RS.pauta.id = RL.documento.id OR RS.extraPauta.id = RL.documento.id ) ");
		sql.append("	ORDER BY RL.item ASC ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idDepartamento", dto.getIdDepartamento());
			
			listModel = query.getResultList();
			
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || buscaRevisaoDecisaoItens", StringUtil.convertObjectToJson(dto), e);
		}

		return listModel;
	}
	
	public List<Long> buscaRevisaoDecisaoProtocolos(ConsultaProtocoloDto dto) {
		List<Long> listModel = new ArrayList<Long>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL.protocolo.id FROM RlDocumentoProtocoloSiacol RL, ReuniaoSiacol RS, ProtocoloSiacol PS ");
		sql.append("	WHERE RS.departamento.id = :idDepartamento ");
		sql.append("		AND ( RL.status = 1 OR RL.status = 9 OR RL.status = 16 ) ");
		sql.append("		AND RL.protocolo.id = PS.id ");
		for (GenericDto s : dto.getStatus()) {
			if (dto.getStatus().indexOf(s) == 0) {
				sql.append(" AND ( PS.status LIKE '"+ s.getTipo() + "' ");
			}else{
				sql.append("OR PS.status LIKE '"+ s.getTipo() + "' ");
			}
		}
		
		sql.append("	)	AND (RS.pauta.id = RL.documento.id OR RS.extraPauta.id = RL.documento.id) ");
		sql.append("	GROUP BY RL.protocolo.id  ");
//		sql.append("	ORDER BY RL.protocolo.numeroProtocolo ASC  ");

		try {

			TypedQuery<Long> query = em.createQuery(sql.toString(), Long.class);
			query.setParameter("idDepartamento", dto.getIdDepartamento());
			listModel = query.getResultList();
			
		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || buscaRevisaoDecisaoProtocolos", StringUtil.convertObjectToJson(dto), e);
		}

		return listModel;
	}



	public void excluirItensPauta(Long idPauta) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE  SIACOL_DOCUMENTO_PROTOCOLO S    ");
		sql.append("	 WHERE S.FK_DOCUMENTO = :idPauta  ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPauta", idPauta);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || excluirItensPauta", StringUtil.convertObjectToJson(idPauta), e);
		}

	}

	public List<RlDocumentoProtocoloSiacol> buscaItensParaAssinar(Long idDepartamento) {

			List<RlDocumentoProtocoloSiacol> listDocumentoProtocolo = new ArrayList<RlDocumentoProtocoloSiacol>();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
			sql.append("	WHERE RL.status.id = 9 ");
			sql.append("	AND RL.documento.departamento.id = :idDepartamento ");
			sql.append("	AND RL.protocolo is not null ");

			try {

				TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
				query.setParameter("idDepartamento", idDepartamento);

				listDocumentoProtocolo = query.getResultList();

			} catch (Throwable e) {
				httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || buscaItensParaAssinar", StringUtil.convertObjectToJson(idDepartamento), e);
			}

			return listDocumentoProtocolo;
		}

	public List<RlDocumentoProtocoloSiacol> buscaItensReuniao(ReuniaoSiacolDto dto) {
		
		List<RlDocumentoProtocoloSiacol> listDocumentoProtocolo = new ArrayList<RlDocumentoProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.documento.id in (:listIds) ");
		sql.append("	ORDER BY RL.item");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("listIds", dto.getIdsPautas());

			listDocumentoProtocolo = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || buscaItensReuniao", StringUtil.convertObjectToJson(dto), e);
		}

		return listDocumentoProtocolo;
		
	}

	public boolean observacaoCoordenador(RlDocumentoProtocoloSiacol rlDocumentoProtocoloSiacol, String obsCoordenador) {

		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE  SIACOL_DOCUMENTO_PROTOCOLO S ");
		sql.append("     SET S.OBS_COORDENADOR = :obsCoordenador ");
		sql.append("	 WHERE S.ID = :id ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("id", rlDocumentoProtocoloSiacol.getId());
			query.setParameter("obsCoordenador", obsCoordenador);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || observacaoCoordenador", StringUtil.convertObjectToJson(rlDocumentoProtocoloSiacol), e);
			return false;
		}
		
		return true;

	}

	public RlDocumentoProtocoloSiacol getUltimoItemByProtocolo(ItemPautaDto dto) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.protocolo.id = :idProtocolo ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idProtocolo", dto.getIdProtocolo());
			
			return query.getResultList().get(0);

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || buscaItensParaAssinar", StringUtil.convertObjectToJson(dto), e);
		}

		return null;
	}

	public RlDocumentoProtocoloSiacol getItemByPautaProtocolo(Long idProtocolo, Long idPauta) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.protocolo.id = :idProtocolo ");
		sql.append("	AND RL.documento.id = :idPauta ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idProtocolo", idProtocolo);
			query.setParameter("idPauta", idPauta);
			
			return query.getResultList().get(0);

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getItemByPautaProtocolo", StringUtil.convertObjectToJson(null), e);
		}

		return null;
	}
	
	public List<RlDocumentoProtocoloSiacol> getItensVistas(Long idDocumento) {
		
		List<RlDocumentoProtocoloSiacol> listDocumentoProtocolo = new ArrayList<RlDocumentoProtocoloSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.documento.id = :idDocumento ");
		sql.append("	WHERE RL.status.id = 4 ");
		sql.append("	ORDER BY RL.item");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idDocumento", idDocumento);

			listDocumentoProtocolo = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getItensVistas", StringUtil.convertObjectToJson(idDocumento), e);
		}

		return listDocumentoProtocolo;
		
	}

	public Long getIdPessoaVista(Long idProtocolo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.protocolo.id = :idProtocolo ");
		sql.append("	AND RL.idPessoaVista is not null ");
		sql.append("	ORDER BY RL.id DESC");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idProtocolo", idProtocolo);
			
			return query.getResultList().get(0).getIdPessoaVista();

		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getIdPessoaVista", StringUtil.convertObjectToJson(idProtocolo), e);
		}

		return null;
	}

	public RlDocumentoProtocoloSiacol getUltimoItemByIdProtocolo(Long idProtocolo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.protocolo.id = :idProtocolo ");
		sql.append("		AND ( RL.status = 1 OR RL.status = 9 OR RL.status = 16 ) ");
		sql.append("		AND ROWNUM <= 1 ");
		sql.append("		ORDER BY RL.id DESC ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idProtocolo", idProtocolo);
			
			return query.getSingleResult();

		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getUltimoItemByIdProtocolo", StringUtil.convertObjectToJson(idProtocolo), e);
		}

		return null;
	}
	
	public RlDocumentoProtocoloSiacol getItemByIdProtocolo(Long idProtocolo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("  WHERE RL.protocolo.id = :idProtocolo        ");
		sql.append("	AND ROWNUM <= 1                           ");
		sql.append("  ORDER BY RL.id DESC                         ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idProtocolo", idProtocolo);
			
			return query.getSingleResult();

		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getItemByIdProtocolo", StringUtil.convertObjectToJson(idProtocolo), e);
		}

		return null;
	}

	public void deletaTodosOsItens(List<Long> idsPautas) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE SIACOL_DOCUMENTO_PROTOCOLO Rl   ");
		sql.append("  WHERE Rl.FK_DOCUMENTO IN (:idsPautas) ");

		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsPautas", idsPautas);

			query.executeUpdate();
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || deletaTodosOsItens", StringUtil.convertObjectToJson(idsPautas), e);
		}
		
	}

	public RlDocumentoProtocoloSiacol retirarItem(Long numeroProtocolo) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("	WHERE RL.protocolo.numeroProtocolo = :numeroProtocolo ");
		sql.append("		AND ( RL.status != 1 OR RL.status != 9 ) ");
		sql.append("		AND ROWNUM <= 1 ");
		sql.append("		ORDER BY RL.id DESC ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("numeroProtocolo", numeroProtocolo);
			
			return query.getSingleResult();

		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || retirarItem", StringUtil.convertObjectToJson(numeroProtocolo), e);
		}

		return null;
		
	}

	public RlDocumentoProtocoloSiacol getItemDocumentoNumeroItem(ItemPautaDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT RL FROM RlDocumentoProtocoloSiacol RL ");
		sql.append("  WHERE RL.documento.id = :numeroDocumento    ");
		sql.append("	AND	RL.item = '" + dto.getItem() + "'     ");
		sql.append("	AND ROWNUM = 1                            ");
		sql.append("  ORDER BY RL.id DESC                         ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("numeroDocumento", new Long(dto.getIdDocumento()));
			
			return query.getSingleResult();

		} catch (NoResultException e){
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || getItemDocumentoNumeroItem", StringUtil.convertObjectToJson(dto), e);
		}

		return null;
	}
	
	public void atualizaResultadoDeItensComProtocoloHomologacao() {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_DOCUMENTO_PROTOCOLO RL                   ");
		sql.append("    SET RL.RESULTADO = :resultado                       ");
		sql.append("  WHERE RL.FK_STATUS = :statusHomologacaoProfissional   ");
		sql.append("    AND RL.FK_PROTOCOLO IS NULL                         ");
		sql.append("    AND RL.EM_VOTACAO = 1                               ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("resultado", ResultadoVotacaoSiacolEnum.HOMOLOGACAO_PROFISSIONAL.toString());
			query.setParameter("statusHomologacaoProfissional", 15L);
			
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || atualizaResultadoDeItensComProtocoloHomologacao", StringUtil.convertObjectToJson(""), e);
		}
	}

	public void atualizaResultadoDeItensSemProtocolo() {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_DOCUMENTO_PROTOCOLO RL                   ");
		sql.append("    SET RL.RESULTADO = :resultado                       ");
		sql.append("  WHERE RL.FK_STATUS = :statusVotado                    ");
		sql.append("    AND RL.FK_PROTOCOLO IS NULL                         ");
		sql.append("    AND RL.FK_STATUS <> :statusHomologacaoProfissional  ");
		sql.append("    AND RL.EM_VOTACAO = 1                               ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("resultado", ResultadoVotacaoSiacolEnum.ASSUNTO_VOTADO.toString());
			query.setParameter("statusVotado", 1L);
			query.setParameter("statusHomologacaoProfissional", 15L);
			
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("RlDocumentoProtocoloSiacolDao || atualizaResultadoDeItensSemProtocolo", StringUtil.convertObjectToJson(""), e);
		}
		
	}

	

}
