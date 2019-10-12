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
import br.org.crea.commons.dao.cadastro.pessoa.PessoaDao;
import br.org.crea.commons.dao.protocolo.ProtocoloDao;
import br.org.crea.commons.models.siacol.ProtocoloSiacol;
import br.org.crea.commons.models.siacol.RlProtocoloResponsavelSiacol;
import br.org.crea.commons.models.siacol.dtos.VinculoProtocoloDto;
import br.org.crea.commons.models.siacol.enuns.StatusProtocoloSiacol;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class RlProtocoloResponsavelSiacolDao extends GenericDao<RlProtocoloResponsavelSiacol, Serializable>{

	

	@Inject	HttpClientGoApi httpGoApi;
	
	@Inject	ProtocoloSiacolDao protocoloSiacolDao;
	
	@Inject	ProtocoloDao protocoloCorporativoDao;
	
	@Inject	PessoaDao pessoaDao;
		
	public RlProtocoloResponsavelSiacolDao () {
		super(RlProtocoloResponsavelSiacol.class);
	}
	
	public void vinculaProtocoloResponsavel(List<VinculoProtocoloDto> listDto) {
		
		RlProtocoloResponsavelSiacol rlProtocoloResponsavel = null;
		
		try {
			
			for (VinculoProtocoloDto vinculoProtocolo : listDto) {
				rlProtocoloResponsavel = new RlProtocoloResponsavelSiacol();
				
				ProtocoloSiacol protocoloPai = protocoloSiacolDao.getProtocoloBy(vinculoProtocolo.getProtocoloPai().getNumeroProtocolo());
				protocoloPai.setUltimoStatus(protocoloPai.getStatus());
				protocoloPai.setStatus(StatusProtocoloSiacol.PENDENTE_VINCULACAO);
				protocoloSiacolDao.update(protocoloPai);
				
				rlProtocoloResponsavel.setProtocoloPai(protocoloPai);
				rlProtocoloResponsavel.setProtocoloFilho(protocoloCorporativoDao.getProtocoloBy(vinculoProtocolo.getProtocoloFilho().getNumeroProtocolo()));
				rlProtocoloResponsavel.setResponsavel(pessoaDao.getPessoa(vinculoProtocolo.getIdResponsavelVinculo()));
				rlProtocoloResponsavel.setFoiImportadoCorporativo(protocoloVinculadoJaEstaSiacol(vinculoProtocolo.getProtocoloFilho().getNumeroProtocolo()));
				create(rlProtocoloResponsavel);
			}
			
		} catch (Throwable e) {
			httpGoApi.geraLog("RlProtocoloResponsavelSiacolDao || vinculaProtocoloResponsavel", StringUtil.convertObjectToJson("SEM PARAMETRO"), e);
		}
	}
	
	public void desfazVinculoProtocoloResponsavel(Long numeroProtocoloVinculado) {
		
		try {
			
			ProtocoloSiacol protocoloPai = getProtocoloVinculadoDoResponsavel(numeroProtocoloVinculado).getProtocoloPai();
			protocoloPai.setStatus(protocoloPai.getUltimoStatus());
			protocoloPai.setUltimoStatus(StatusProtocoloSiacol.PENDENTE_VINCULACAO);
			protocoloSiacolDao.update(protocoloPai);
			
			deleta(getProtocoloVinculadoDoResponsavel(numeroProtocoloVinculado).getId());
			
		} catch (Exception e) {
			httpGoApi.geraLog("RlProtocoloResponsavelSiacolDao || desfazVinculoProtocoloResponsavel", StringUtil.convertObjectToJson(numeroProtocoloVinculado), e);
		}
	}
	
	public void atualizaVinculoProtocoloNaImportacao(Long numeroProtocoloVinculado) {
		
		try {
			
			RlProtocoloResponsavelSiacol rlProtocoloResponsavel = getProtocoloVinculadoDoResponsavel(numeroProtocoloVinculado);
			rlProtocoloResponsavel.setFoiImportadoCorporativo(true);
			update(rlProtocoloResponsavel);
			
			if( todosProtocolosVinculadosForamImportados(rlProtocoloResponsavel.getProtocoloPai().getNumeroProtocolo()) ) {
				
				rlProtocoloResponsavel.getProtocoloPai().setStatus(StatusProtocoloSiacol.VINCULADO);
				protocoloSiacolDao.update(rlProtocoloResponsavel.getProtocoloPai());
			}
			
		} catch (Exception e) {
			httpGoApi.geraLog("RlProtocoloResponsavelSiacolDao || atualizaVinculoProtocoloNaImportacao", StringUtil.convertObjectToJson(numeroProtocoloVinculado), e);
		}
	}
	
	public RlProtocoloResponsavelSiacol getProtocoloVinculadoDoResponsavel(Long numeroProtocoloVinculado) {
		RlProtocoloResponsavelSiacol rlProtocoloResponsavel = new RlProtocoloResponsavelSiacol();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlProtocoloResponsavelSiacol RL ");
		sql.append("	WHERE RL.protocoloFilho.numeroProtocolo = :numeroProtocoloVinculado ");
		
		try {
			
			TypedQuery<RlProtocoloResponsavelSiacol> query = em.createQuery(sql.toString(), RlProtocoloResponsavelSiacol.class);
			query.setParameter("numeroProtocoloVinculado", numeroProtocoloVinculado);
			rlProtocoloResponsavel = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		} catch (Throwable e) {
			httpGoApi.geraLog("RlProtocoloResponsavelSiacolDao || getProtocoloVinculadoDoResponsavel", StringUtil.convertObjectToJson(numeroProtocoloVinculado), e);
		}
		
		return rlProtocoloResponsavel;
	}
	
	public List<RlProtocoloResponsavelSiacol> getListVinculosProtocoloResponsavelPor(Long numeroProtocoloPai) {
		List<RlProtocoloResponsavelSiacol> result = new ArrayList<RlProtocoloResponsavelSiacol>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RL FROM RlProtocoloResponsavelSiacol RL ");
		sql.append("	WHERE RL.protocoloPai.numeroProtocolo = :numeroProtocoloPai	");
		
		try {
			
			TypedQuery<RlProtocoloResponsavelSiacol> query = em.createQuery(sql.toString(), RlProtocoloResponsavelSiacol.class);
			query.setParameter("numeroProtocoloPai", numeroProtocoloPai);
			result  = query.getResultList();
			
		} catch (NoResultException e) {
			return result;
		} catch (Exception e) {
			httpGoApi.geraLog("RlProtocoloResponsavelSiacolDao || getListVinculosProtocoloResponsavelPor", StringUtil.convertObjectToJson(numeroProtocoloPai), e);
		}
		
		return result;
		
	}
	
	public boolean protocoloVinculadoJaEstaSiacol(Long numeroProtocoloVinculado) {
		return protocoloSiacolDao.getProtocoloBy(numeroProtocoloVinculado) != null ? true : false ;
	}
	
	public boolean protocoloJaEstaVinculado(Long numeroProtocolo) {
		return getProtocoloVinculadoDoResponsavel(numeroProtocolo) != null ? true : false ;
	}
	
	public boolean todosProtocolosVinculadosForamImportados(Long numeroProtocoloPai) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COUNT(RL) FROM RlProtocoloResponsavelSiacol RL ");
		sql.append("	WHERE RL.protocoloPai.numeroProtocolo = :numeroProtocoloPai ");
		sql.append("	AND   RL.foiImportadoCorporativo = false ");
		
		try {

			Query query = em.createQuery(sql.toString());
			query.setParameter("numeroProtocoloPai", numeroProtocoloPai);
			
			Long quantidadeVinculoNaoImportado = (Long) query.getSingleResult();
			return quantidadeVinculoNaoImportado > new Long(0) ? false : true;
			
		} catch (NoResultException e) {
			return false;
			
		} catch (Throwable e) {
			httpGoApi.geraLog("RlProtocoloResponsavelSiacolDao || todosProtocolosVinculadosForamImportados", StringUtil.convertObjectToJson(numeroProtocoloPai), e);
		}
		
		return false;
		
	}

}
