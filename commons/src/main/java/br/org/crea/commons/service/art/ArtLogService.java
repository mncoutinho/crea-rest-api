package br.org.crea.commons.service.art;

import java.util.Date;

import javax.inject.Inject;

import br.org.crea.commons.dao.art.ArtLogDao;
import br.org.crea.commons.models.art.ArtLog;
import br.org.crea.commons.models.commons.dtos.UserFrontDto;

public class ArtLogService {

	@Inject ArtLogDao dao;
	
	private ArtLog log;
	
	public ArtLogService usuario(UserFrontDto usuario) {
		log = new ArtLog();
		log.setIp(usuario.getIp());
		return this;
	}
	
	public ArtLogService data(Date data) {
		log.setDataHora(data);
		return this;
	}

	public ArtLogService numero(String numeroArt) {
		log.setNumeroArt(numeroArt);
		return this;
	}

	public ArtLogService funcionario(Long idFuncionario) {
		log.setFuncionario(idFuncionario);
		return this;
	}
	
	public ArtLogService textoLog(String texto) {
		log.setDescricao(texto);
		return this;
	} 
	
	public ArtLogService acao(Long idAcao) {
		log.setTipoAcaoArt(idAcao);
		return this;
	}

	public void create() {
		dao.create(log);
	}
}
