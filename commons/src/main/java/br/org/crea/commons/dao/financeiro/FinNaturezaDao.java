package br.org.crea.commons.dao.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.cadastro.Natureza;
import br.org.crea.commons.service.HttpClientGoApi;
import br.org.crea.commons.util.StringUtil;

@Stateless
public class FinNaturezaDao extends GenericDao<Natureza, Serializable> {

	@Inject
	private HttpClientGoApi httpGoApi;

	public FinNaturezaDao() {
		super(Natureza.class);
	}

	public List<Natureza> getNaturezaParaDevolucaoTransferenciaDeCredito() {

		List<Natureza> listFinNatureza = new ArrayList<Natureza>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT N FROM Natureza N ");
		sql.append("  WHERE N.devolucao = 1   ");

		try {
			TypedQuery<Natureza> query = em.createQuery(sql.toString(), Natureza.class);
			listFinNatureza = query.getResultList();
			
		} catch (Throwable e) {
			httpGoApi.geraLog("FinNaturezaDao || getNaturezaParaDevolucaoTransferenciaDeCredito", StringUtil.convertObjectToJson(""), e);
		}

		return listFinNatureza;

	}

}
