package br.org.crea.commons.dao.commons;

import java.io.Serializable;

import javax.ejb.Stateless;

import br.org.crea.commons.dao.GenericDao;
import br.org.crea.commons.models.commons.LoginLog;
@Stateless
public class LoginLogDao extends GenericDao<LoginLog, Serializable> {

	public LoginLogDao() {
		super(LoginLog.class);
	}

}
