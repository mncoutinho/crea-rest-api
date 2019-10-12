package br.org.crea.commons.service.commons;

import java.util.Calendar;

import javax.inject.Inject;

import br.org.crea.commons.dao.commons.LoginLogDao;
import br.org.crea.commons.models.commons.LoginLog;

public class LoginLogService {
	
	@Inject LoginLogDao dao;
	
	public void registra(String ip, Long registro, String descricao) {
		try {
			LoginLog loginLog = new LoginLog();
			loginLog.setIp(ip);
			loginLog.setLogin(registro);
			loginLog.setDescricao(descricao);
			loginLog.setData(Calendar.getInstance());
			dao.create(loginLog);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
