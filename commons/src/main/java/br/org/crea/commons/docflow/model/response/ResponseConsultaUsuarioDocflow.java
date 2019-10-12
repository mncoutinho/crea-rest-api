package br.org.crea.commons.docflow.model.response;

import br.org.crea.commons.docflow.model.usuario.InfoLotacaoDocflow;
import br.org.crea.commons.docflow.model.util.MessageDocflow;
import br.org.crea.commons.docflow.model.util.ServiceDocflow;

public class ResponseConsultaUsuarioDocflow {

	private InfoLotacaoDocflow info;
	
	private ServiceDocflow service;
	
	private MessageDocflow message;

	public InfoLotacaoDocflow getInfo() {
		return info;
	}

	public ServiceDocflow getService() {
		return service;
	}

	public MessageDocflow getMessage() {
		return message;
	}

	public void setInfo(InfoLotacaoDocflow info) {
		this.info = info;
	}

	public void setService(ServiceDocflow service) {
		this.service = service;
	}

	public void setMessage(MessageDocflow message) {
		this.message = message;
	}
	
	public boolean hasError(){
		return this.getMessage().getType().equals("error") ? true : false;
	}
}
