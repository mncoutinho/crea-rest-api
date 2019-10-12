package br.org.crea.corporativo.dao.personalidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.corporativo.personalidade.entity.CargoPersonalidade;
import br.org.crea.commons.service.HttpClientGoApi;

@Stateless
public class CargoPersonalidadeDao extends GenericDao<CargoPersonalidade, Serializable>  {

	@Inject
	HttpClientGoApi httpGoApi;
	
	public CargoPersonalidadeDao() {
		super(CargoPersonalidade.class);

	}
	
public List<CargoPersonalidade> getAll(){
		
		List<CargoPersonalidade> cargos = new ArrayList<CargoPersonalidade>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT C FROM CargoPersonalidade C ");
		sql.append("	WHERE C.cargoRaiz IS NULL ORDER BY descricao " );


		try {
			TypedQuery<CargoPersonalidade> query = em.createQuery(sql.toString(), CargoPersonalidade.class);
			
		    cargos = query.getResultList();

		} catch (Throwable e) {
			httpGoApi.geraLog("CargoPersonallidadeDao || getAll", "sem parametro",  e);

		}
		
		return cargos;
	}

}
