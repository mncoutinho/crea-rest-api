package br.org.crea.siacol.dao;

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
import br.org.crea.commons.models.siacol.RlDocumentoProtocoloSiacol;
import br.org.crea.commons.models.siacol.VotoReuniaoSiacol;
import br.org.crea.commons.models.siacol.dtos.ItemPautaDto;
import br.org.crea.commons.models.siacol.dtos.PesquisaVotoReuniaoDto;
import br.org.crea.commons.models.siacol.enuns.VotoReuniaoEnum;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class VotoReuniaoSiacolDao extends GenericDao<VotoReuniaoSiacol, Serializable> {

	@Inject
	HttpClientGoApi httpGoApi;

	public VotoReuniaoSiacolDao() {
		super(VotoReuniaoSiacol.class);
	}

	public List<VotoReuniaoSiacol> getByIdProtocolo(Long idProtocolo) {

		List<VotoReuniaoSiacol> listVotos = new ArrayList<VotoReuniaoSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT V FROM VotoReuniaoSiacol V ");
		sql.append("	WHERE V.protocolo.id = :idProtocolo ");
		sql.append("	ORDER BY V.id DESC ");


		try {
			
			TypedQuery<VotoReuniaoSiacol> query = em.createQuery(sql.toString(), VotoReuniaoSiacol.class);
			query.setParameter("idProtocolo", idProtocolo);

			listVotos = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getByIdProtocolo", StringUtil.convertObjectToJson(idProtocolo), e);

		}

		return listVotos;
	}

	public VotoReuniaoSiacol getVotoByIdPessoaByIdProtocolo(Long idPessoa, Long idProtocolo) {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT V FROM VotoReuniaoSiacol V ");
		sql.append("	WHERE V.protocolo.id = :idProtocolo ");
		sql.append("	AND V.pessoa.id = :idPessoa ");
		sql.append("	AND rownum = 1 ");
		sql.append("	ORDER BY V.id DESC ");

		try {

			TypedQuery<VotoReuniaoSiacol> query = em.createQuery(sql.toString(), VotoReuniaoSiacol.class);
			query.setParameter("idProtocolo", idProtocolo);
			query.setParameter("idPessoa", idPessoa);

			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getVotoByIdPessoaByIdProtocolo", StringUtil.convertObjectToJson(idProtocolo), e);
		}

		return null;

	}

	public int getQuantidadesVotosPor(Long idProtocolo, Long idReuniao, VotoReuniaoEnum votoEnum) {

		int result = 0;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(*) FROM SIACOL_VOTO_REUNIAO V ");
		sql.append("	WHERE V.FK_PROTOCOLO = :idProtocolo ");
		sql.append("	AND V.FK_REUNIAO = :idReuniao ");

		if (!votoEnum.toString().equals("D")) {
			sql.append("	AND V.VOTO = :voto ");
		} else {
			sql.append("	AND V.DESTAQUE = 1 ");
		}

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProtocolo", idProtocolo);
			query.setParameter("idReuniao", idReuniao);
			if (!votoEnum.toString().equals("D")) {
				query.setParameter("voto", votoEnum.toString());
			}

			result = Integer.parseInt(query.getSingleResult().toString());
			return result;

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getQuantidadesVotosPor", StringUtil.convertObjectToJson(idProtocolo + " -- " + idReuniao), e);
		}

		return result;
	}
	
	
	public List<VotoReuniaoSiacol> getVotosPorIdItemPauta(Long idItemPauta) {
		List<VotoReuniaoSiacol> listVotos = new ArrayList<VotoReuniaoSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT V FROM VotoReuniaoSiacol V ");
		sql.append("	WHERE 1 = 1 ");
	    sql.append(" AND V.itemPauta.id = :idItemPauta ");

		try {

			TypedQuery<VotoReuniaoSiacol> query = em.createQuery(sql.toString(), VotoReuniaoSiacol.class);
		    query.setParameter("idItemPauta", idItemPauta);

			listVotos = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getVotosPorIdItemPauta", StringUtil.convertObjectToJson(idItemPauta), e);
		}

		return listVotos;
		
	}

	public List<VotoReuniaoSiacol> pesquisa(PesquisaVotoReuniaoDto pesquisa) {

		List<VotoReuniaoSiacol> listVotos = new ArrayList<VotoReuniaoSiacol>();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT V FROM VotoReuniaoSiacol V ");
		sql.append("	WHERE 1 = 1 ");

		if (pesquisa.temProtocolo()) {
			sql.append(" AND V.protocolo.id = :idProtocolo ");
		}

		if (pesquisa.temPessoa()) {
			sql.append(" AND V.pessoa.id = :idPessoa ");
		}

		if (pesquisa.temVoto()) {
			sql.append(" AND V.voto = :voto ");
		}

		if (pesquisa.temReuniao()) {
			sql.append(" AND V.reuniao.id = :idReuniao ");
		}
		

		try {

			TypedQuery<VotoReuniaoSiacol> query = em.createQuery(sql.toString(), VotoReuniaoSiacol.class);
			if (pesquisa.temProtocolo()) {
				query.setParameter("idProtocolo", pesquisa.getIdProtocolo());
			}
			if (pesquisa.temPessoa()) {
				query.setParameter("idPessoa", pesquisa.getIdPessoa());
			}
			if (pesquisa.temVoto()) {
				query.setParameter("voto", pesquisa.getVoto().toString());
			}
			if (pesquisa.temReuniao()) {
				query.setParameter("idReuniao", pesquisa.getIdReuniao());
			}
		
			listVotos = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || pesquisa", StringUtil.convertObjectToJson(pesquisa), e);
		}

		return listVotos;
	}

	public VotoReuniaoSiacol getVotoCoordenadorOuAdjunto(Long idProtocolo, Long idReuniao, Long idPessoa) {
	
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT V FROM VotoReuniaoSiacol V ");
		sql.append("	WHERE V.protocolo.id = :idProtocolo ");
		sql.append("	AND V.pessoa.id = :idPessoa ");
		sql.append("	AND V.reuniao.id = :idReuniao ");

		try {

			TypedQuery<VotoReuniaoSiacol> query = em.createQuery(sql.toString(), VotoReuniaoSiacol.class);
			query.setParameter("idProtocolo", idProtocolo);
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("idReuniao", idReuniao);

			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getVotoCoordenadorOuAdjunto", StringUtil.convertObjectToJson(idProtocolo), e);
		}

		return null;
	}

	public VotoReuniaoSiacol getVotoByIdPessoaByIdItemPauta(Long idPessoa, Long idItemPauta) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT V FROM VotoReuniaoSiacol V ");
		sql.append("	WHERE V.itemPauta.id = :idItemPauta ");
		sql.append("	AND V.pessoa.id = :idPessoa ");

		try {

			TypedQuery<VotoReuniaoSiacol> query = em.createQuery(sql.toString(), VotoReuniaoSiacol.class);
			query.setParameter("idItemPauta", idItemPauta);
			query.setParameter("idPessoa", idPessoa);

			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getVotoByIdPessoaByIdItemPauta", StringUtil.convertObjectToJson(idItemPauta), e);
		}

		return null;
	}

	public int getQuantidadeVotosPresenciaisPor(Long idReuniao, Long idItemPauta, VotoReuniaoEnum votoEnum) {

			int result = 0;
			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT COUNT(*) FROM SIACOL_VOTO_REUNIAO V ");
			sql.append("	WHERE V.FK_REUNIAO = :idReuniao         ");
			sql.append("	  AND V.FK_ITEM_PAUTA = :idItemPauta    ");

			if (!votoEnum.ehDestaque()) {
				sql.append("	AND V.VOTO = :voto ");
			} else {
				sql.append("	AND V.DESTAQUE = 1 ");
			}

			try {

				Query query = em.createNativeQuery(sql.toString());
				query.setParameter("idReuniao", idReuniao);
				query.setParameter("idItemPauta", idItemPauta);
				
				if (!votoEnum.ehDestaque()) {
					query.setParameter("voto", votoEnum.toString());
				}
				
				return Integer.parseInt(query.getSingleResult().toString());

			} catch (Throwable e) {
				httpGoApi.geraLog("VotoReuniaoSiacolDao || getQuantidadesVotosPresenciaisPor", StringUtil.convertObjectToJson(idReuniao + " -- " + idItemPauta), e);
			}

			return result;
	}

	public int getQuantidadeItensVotaveisReuniao(List<Long> idsPautas) {
		int result = 0;
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT COUNT(*) FROM SIACOL_DOCUMENTO_PROTOCOLO D ");
		sql.append("	WHERE D.FK_DOCUMENTO IN (:idsPautas)           ");
		sql.append("	  AND D.FK_STATUS IN (1, 2, 3, 4, 6, 13, 15, 16)   ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsPautas", idsPautas);
			
			result = Integer.parseInt(query.getSingleResult().toString());

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getQuantidadeItensReuniao", StringUtil.convertObjectToJson(idsPautas), e);
		}

		return result;
	}

	public int getQuantidadeItensVotados(List<Long> idsPautas) {
		int result = 0;
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT COUNT(*) FROM SIACOL_DOCUMENTO_PROTOCOLO D  ");
		sql.append("	WHERE D.FK_DOCUMENTO IN (:idsPautas)            ");
		sql.append("      AND D.FK_STATUS = 1 OR D.FK_STATUS = 16       ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsPautas", idsPautas);
			
			result = Integer.parseInt(query.getSingleResult().toString());

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getQuantidadeItensVotados", StringUtil.convertObjectToJson(idsPautas), e);
		}

		return result;
	}

	public boolean itemEstaEmVotacao(Long idRlItemPauta) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT R FROM RlDocumentoProtocoloSiacol R ");
		sql.append("	WHERE R.id = :idRlItemPauta ");
		sql.append("	AND R.emVotacao = 1");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idRlItemPauta", idRlItemPauta);

			return query.getSingleResult() != null ? true : false;
		
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || itemEstaEmVotacao", StringUtil.convertObjectToJson(idRlItemPauta), e);
		}
		
		return false;
	}

	public void apagarVotos(List<Long> idsPauta) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE SIACOL_VOTO_REUNIAO                       ");
		sql.append("	WHERE FK_ITEM_PAUTA IN                        ");
		sql.append("      ( SELECT ID FROM SIACOL_DOCUMENTO_PROTOCOLO ");
		sql.append("          WHERE FK_DOCUMENTO IN (:idsPauta)       ");
		sql.append("                AND EM_VOTACAO = 1 )              ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idsPauta", idsPauta);

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || apagarVotos", StringUtil.convertObjectToJson(idsPauta), e);
		}
		
	}

	public Long getIdPrimeiroItemEmVotacao(List<Long> idsPautas) {
		
		RlDocumentoProtocoloSiacol primeiroItem = new RlDocumentoProtocoloSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT R FROM RlDocumentoProtocoloSiacol R ");
		sql.append("	WHERE R.documento.id IN (:idsPautas)    ");
		sql.append("	  AND R.emVotacao = 1                   ");
		sql.append("	  AND ROWNUM = 1                        ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idsPautas", idsPautas);

			primeiroItem = query.getSingleResult();
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getPrimeiroItemEmVotacao", StringUtil.convertObjectToJson(idsPautas), e);
		}
		
		return primeiroItem.getId();
	}

	public List<RlDocumentoProtocoloSiacol> getItensEmVotacao(List<Long> idsPautas) {
		List<RlDocumentoProtocoloSiacol> itens = new ArrayList<RlDocumentoProtocoloSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT R FROM RlDocumentoProtocoloSiacol R ");
		sql.append("	WHERE R.documento.id IN (:idsPautas)    ");
		sql.append("	  AND R.emVotacao = 1                   ");

		try {

			TypedQuery<RlDocumentoProtocoloSiacol> query = em.createQuery(sql.toString(), RlDocumentoProtocoloSiacol.class);
			query.setParameter("idsPautas", idsPautas);

			itens = query.getResultList();
		
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getItensEmVotacao", StringUtil.convertObjectToJson(idsPautas), e);
		}
		
		return itens;
	}

	public int getQuantidadeVotosSim(Long idProtocolo, Long idReuniao) {
		
		int result = 0;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(*) FROM SIACOL_VOTO_REUNIAO V ");
		sql.append("	WHERE V.FK_PROTOCOLO = :idProtocolo    ");
		sql.append("	  AND V.FK_REUNIAO = :idReuniao        ");
		sql.append("	  AND V.VOTO = 'S'                     ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProtocolo", idProtocolo);
			query.setParameter("idReuniao", idReuniao);

			result = Integer.parseInt(query.getSingleResult().toString());
			return result;

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getQuantidadeVotosSim", StringUtil.convertObjectToJson(idProtocolo + " -- " + idReuniao), e);
		}

		return result;
	}
	
	public int getQuantidadeVotosNao(Long idProtocolo, Long idReuniao) {
		
		int result = 0;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(*) FROM SIACOL_VOTO_REUNIAO V ");
		sql.append("	WHERE V.FK_PROTOCOLO = :idProtocolo    ");
		sql.append("	  AND V.FK_REUNIAO = :idReuniao        ");
		sql.append("	  AND V.VOTO = 'N'                     ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idProtocolo", idProtocolo);
			query.setParameter("idReuniao", idReuniao);

			result = Integer.parseInt(query.getSingleResult().toString());
			return result;

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getQuantidadeVotosNao", StringUtil.convertObjectToJson(idProtocolo + " -- " + idReuniao), e);
		}

		return result;
	}

	public void registraVotoMinervaNoItem(RlDocumentoProtocoloSiacol item, Long idMinerva) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_DOCUMENTO_PROTOCOLO P      ");
		sql.append("    SET P.FK_PESSOA_MINERVA = :idMinerva  ");
		sql.append("  WHERE P.ID = :id                        ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idMinerva", idMinerva);
			query.setParameter("id", item.getId());

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || registraVotoMinervaNoItem", StringUtil.convertObjectToJson(item + " - " + idMinerva), e);
		}
	}

	public boolean jaVotou(Long idReuniao, Long idParticipante) {
		
		int result = 0;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(*) FROM SIACOL_VOTO_REUNIAO V ");
		sql.append("	WHERE V.FK_PESSOA = :idParticipante    ");
		sql.append("	  AND V.FK_REUNIAO = :idReuniao        ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idParticipante", idParticipante);
			query.setParameter("idReuniao", idReuniao);

			result = Integer.parseInt(query.getSingleResult().toString());
			return result > 0;

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || jaVotou", StringUtil.convertObjectToJson(idParticipante + " -- " + idReuniao), e);
		}

		return false;
	}

	public int getQuantidadesTotalDeVotosPor(Long idReuniao, Long idItem) {
		int result = 0;
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) FROM SIACOL_VOTO_REUNIAO V ");
		sql.append("  WHERE V.FK_REUNIAO = :idReuniao           ");
		sql.append("    AND V.FK_ITEM_PAUTA = :idItem           ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			query.setParameter("idItem", idItem);

			result = Integer.parseInt(query.getSingleResult().toString());
			return result;

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getQuantidadesTotalDeVotosPor", StringUtil.convertObjectToJson(idItem + " -- " + idReuniao), e);
		}

		return result;
	}

	public void atualizaResultadoEnquete(ItemPautaDto dto) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIACOL_DOCUMENTO_PROTOCOLO D             ");
		sql.append("    SET D.RESULTADO_ENQUETE = :resultadoEnquete  ");
		sql.append("  WHERE D.ID = :idItem                           ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idItem", dto.getId());
			query.setParameter("resultadoEnquete", dto.getResultadoEnquete());

			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || atualizaResultadoEnquete", StringUtil.convertObjectToJson(dto), e);
		}

	}

	public int getTotalDeVotosEnquetePorId(Long idRlItemPauta, String idResposta) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("   SELECT COUNT(V.ID) FROM SIACOL_VOTO_REUNIAO V ");
		sql.append("    WHERE V.FK_ITEM_PAUTA = :idRlItemPauta       ");
		sql.append("      AND V.RESPOSTA_ENQUETE = :idResposta       ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idRlItemPauta", idRlItemPauta);
			query.setParameter("idResposta", idResposta);

			return Integer.parseInt(query.getSingleResult().toString());
			
		} catch (NoResultException e) {
			return 0;
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || getTotalDeVotosEnquete", StringUtil.convertObjectToJson(idResposta + " -- " + idRlItemPauta), e);
		}
		
		return 0;
	}

	public void apagarTodosOsVotos(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE SIACOL_VOTO_REUNIAO V     ");
		sql.append("  WHERE V.FK_REUNIAO = :idReuniao  ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);

			query.executeUpdate();
		
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || apagarTodosOsVotos", StringUtil.convertObjectToJson(idReuniao), e);
		}
	}

	public boolean reuniaoTeveAoMenosUmItemVotado(Long idReuniao) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(I.ID)                     ");
	    sql.append("   FROM SIACOL_DOCUMENTO_PROTOCOLO I    ");
	    sql.append("  WHERE I.STATUS = 1 OR I.STATUS = 16   "); 
		sql.append("    AND I.FK_DOCUMENTO = :idReuniao     ");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idReuniao", idReuniao);
			
			BigDecimal valor = (BigDecimal) query.getSingleResult(); 
			
			return valor.compareTo(new BigDecimal(0)) > 0;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("VotoReuniaoSiacolDao || reuniaoTeveAoMenosUmItemVotado", StringUtil.convertObjectToJson(idReuniao), e);
		}
		return false;
	}

}
