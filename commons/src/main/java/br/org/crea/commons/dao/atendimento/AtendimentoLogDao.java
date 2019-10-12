package br.org.crea.commons.dao.atendimento;

import java.io.Serializable;
import javax.ejb.Stateless;
import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.portal.AtendimentoLog;

@Stateless
public class AtendimentoLogDao extends GenericDao<AtendimentoLog, Serializable> {
	
	public AtendimentoLogDao() {
		super(AtendimentoLog.class);
	}


}
