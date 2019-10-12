package br.org.crea.commons.service.protocolo;

import javax.inject.Inject;

import br.org.crea.commons.dao.protocolo.EprocessoDao;
import br.org.crea.commons.models.commons.Eprocesso;

public class EprocessoService {

	@Inject EprocessoDao dao;
	
	public Eprocesso getEprocessoBy(Long id){
		return dao.getBy(id);
	}
}
