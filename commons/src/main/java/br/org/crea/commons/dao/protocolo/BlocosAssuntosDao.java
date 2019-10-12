package br.org.crea.commons.dao.protocolo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.BlocosAssuntos;
import br.org.crea.commons.service.HttpClientGoApi;


public class BlocosAssuntosDao extends GenericDao<BlocosAssuntos, Serializable> {
	
	@Inject HttpClientGoApi httpGoApi;

	public BlocosAssuntosDao() {
		super(BlocosAssuntos.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<BlocosAssuntos> getblocos(){
		
		List<BlocosAssuntos> blocosAssuntos = new ArrayList<BlocosAssuntos>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM PRT_BLOCOSASSUNTOS " );
		sql.append("	WHERE ID IN (1000, 2000, 3000, 5000, 6000, 7000, 14000, 17000 ) " );
		sql.append("	ORDER BY ID " );

		try {
			Query query = em.createNativeQuery(sql.toString(), BlocosAssuntos.class);

			
			blocosAssuntos = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("AssuntoDao || getblocos", "sem parametro",  e);

		}
		
		return blocosAssuntos;
	}
	
}
