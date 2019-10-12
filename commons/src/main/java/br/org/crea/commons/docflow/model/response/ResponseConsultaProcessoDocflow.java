package br.org.crea.commons.docflow.model.response;

import br.org.crea.commons.docflow.model.processo.DataProcessoDocflow;
import br.org.crea.commons.docflow.model.util.MessageDocflow;
import br.org.crea.commons.docflow.model.util.ServiceDocflow;

public class ResponseConsultaProcessoDocflow {
	
	private DataProcessoDocflow data; 

	private ServiceDocflow service;

	private MessageDocflow message;

	public DataProcessoDocflow getData() {
		return data;
	}

	public ServiceDocflow getService() {
		return service;
	}

	public MessageDocflow getMessage() {
		return message;
	}

	public void setData(DataProcessoDocflow data) {
		this.data = data;
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
