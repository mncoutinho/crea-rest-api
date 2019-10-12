package br.org.crea.commons.dao.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.ParticipantePremioTCT;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;
	
@Stateless	
public class ParticipantePremioTCTDao extends GenericDao<ParticipantePremioTCT, Serializable>  {

	@Inject HttpClientGoApi httpGoApi;
		
		
	public ParticipantePremioTCTDao() {
		super(ParticipantePremioTCT.class);
	}
	
	public List<ParticipantePremioTCT> getListParticipantes(Long idPremio){
		List<ParticipantePremioTCT> listParticipantes = new ArrayList<ParticipantePremioTCT>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("   SELECT P FROM ParticipantePremioTCT P ");
		sql.append("    WHERE P.premio.id = :idPremio        ");
		sql.append(" ORDER BY P.papel                        ");
		
		try {

			TypedQuery<ParticipantePremioTCT> query = em.createQuery(sql.toString(), ParticipantePremioTCT.class);
			query.setParameter("idPremio", idPremio);

			listParticipantes = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("ParticipantePremioTCTDao || getListParticipantes", StringUtil.convertObjectToJson(idPremio), e);
		}
		
		
		return listParticipantes;
	}
	
	public void deleteByIdPremio(Long idPremio){
		StringBuilder sql = new StringBuilder();
		
		sql.append("DELETE FROM ParticipantePremioTCT P ");
		sql.append(" 	  WHERE P.premio.id = :idPremio ");
		
		try {

			Query query = em.createQuery(sql.toString());
			query.setParameter("idPremio", idPremio);
			query.executeUpdate();

		} catch (Throwable e) {
			httpGoApi.geraLog("ParticipantePremioTCTDao || deleteByIdPremio", StringUtil.convertObjectToJson(idPremio), e);
		}
	}

		
}


