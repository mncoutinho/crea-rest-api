package br.org.crea.corporativo.dao.personalidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.personalidade.entity.CargoConselheiro;
import br.org.crea.commons.service.HttpClientGoApi;

public class CargoConselheiroDao  extends GenericDao<CargoConselheiro, Serializable>  {
	
	@Inject
	HttpClientGoApi httpGoApi;
	
	public CargoConselheiroDao() {
		super(CargoConselheiro.class);

	}
	
public List<CargoConselheiro> getByCargo(Long cargo){
		
		List<CargoConselheiro> cargos = new ArrayList<CargoConselheiro>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT C FROM CargoConselheiro C ");
		sql.append("	WHERE C.cargo.id = :cargo " );


		try {
			TypedQuery<CargoConselheiro> query = em.createQuery(sql.toString(), CargoConselheiro.class);
			query.setParameter("cargo", cargo);
			
		    cargos = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("CargoConselheiroDao || getAll", "sem parametro",  e);

		}
		
		return cargos;
	}

}
