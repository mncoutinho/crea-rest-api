package br.org.crea.commons.dao.protocolo;

import java.io.Serializable;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.Eprocesso;

public class EprocessoDao extends GenericDao<Eprocesso, Serializable>{
	
	public EprocessoDao(){
		super(Eprocesso.class);
	}	
}
