package br.org.crea.atendimento.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.atendimento.AssuntoMobile;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;
import br.org.crea.commons.models.corporativo.dtos.AssuntoDto;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class AssuntoMobileDao extends GenericDao<AssuntoMobile, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public AssuntoMobileDao() {
		super(AssuntoMobile.class);
	}

	public List<AssuntoMobile> getAssuntos(UserFrontDto pessoa) {
		
		
		List<AssuntoMobile> listAssunto = new ArrayList<AssuntoMobile>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A FROM AssuntoMobile A ");
		sql.append("    WHERE A.tipoPessoa = :tipoPessoa AND A.situacao.id = :idSituacao");
		
		try {
			TypedQuery<AssuntoMobile> query = em.createQuery(sql.toString(), AssuntoMobile.class);
			query.setParameter("tipoPessoa", pessoa.getTipoPessoa());
			query.setParameter("idSituacao", pessoa.getSituacao() != null ? pessoa.getSituacao().getId() : 0L);
			
			listAssunto = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoMobileDao || Get Assuntos", StringUtil.convertObjectToJson(pessoa), e);
		}
		
		return listAssunto;


	}

	@SuppressWarnings("unchecked")
	public List<AssuntoDto> getTodosAssuntos(String tipoPessoa) {
		
		List<AssuntoDto> listAssuntoDto = new ArrayList<AssuntoDto>();
	
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T.* FROM PRT_ASSUNTOS T ");
		sql.append("   WHERE T.id in (SELECT DISTINCT FK_ID_ASSUNTO FROM MOB_ASSUNTOS WHERE TIPOPESSOA = :tipoPessoa)");
		
		try {
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("tipoPessoa", tipoPessoa);
			Iterator<Object> it = query.getResultList().iterator();
			
			if (query.getResultList().size() > 0) {
				while (it.hasNext()) {

					Object[] result = (Object[]) it.next();
					AssuntoDto objeto = new AssuntoDto();

					BigDecimal idobjeto = (BigDecimal) result[0];
					objeto.setId(idobjeto.setScale(0, BigDecimal.ROUND_UP).longValueExact());
					objeto.setDescricao(result[1] == null ? "" : result[1].toString());
					listAssuntoDto.add(objeto);
				}
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoMobileDao || getTodosAssuntos", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
		
		return listAssuntoDto;
	}

	public boolean pagamentoRegistroEstaPago(Long idAssunto, Long idPessoa) {
	
		Long idNatureza = null;
		
		if(idAssunto.equals(new Long(2001))){
			idNatureza = new Long(404);
		}else {
			idNatureza = new Long(403);
		}
		
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT COUNT(*) FROM FIN_DIVIDA F ");
		sql.append("	WHERE F.FK_CODIGO_PESSOA = :idPessoa ");
		sql.append("	  AND F.FK_CODIGO_NATUREZA = :idNatureza ");
		sql.append("	  AND F.SERVICO_EXECUTADO = 0 ");
		sql.append("	  AND F.FK_CODIGO_STATUS_DIVIDA = 4 ");

		try {

			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("idPessoa", idPessoa);
			query.setParameter("idNatureza", idNatureza);
			
			return Integer.parseInt(query.getSingleResult().toString()) == 1;

		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoMobileDao || pagamentoRegistroEstaPago", StringUtil.convertObjectToJson(idAssunto + " -- " + idPessoa), e);
		}

		return false;
	}

}
