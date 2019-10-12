package br.org.crea.commons.docflow.model.response;

import br.org.crea.commons.docflow.model.processo.DataAutuacaoProcessoDocflow;
import br.org.crea.commons.docflow.model.util.MessageDocflow;
import br.org.crea.commons.docflow.model.util.ServiceDocflow;

public class ResponseAutuacaoProcessoDocflow {

	private DataAutuacaoProcessoDocflow data;
	
	private MessageDocflow message;
	
	private ServiceDocflow service;

	public DataAutuacaoProcessoDocflow getData() {
		return data;
	}

	public void setData(DataAutuacaoProcessoDocflow data) {
		this.data = data;
	}

	public MessageDocflow getMessage() {
		return message;
	}

	public void setMessage(MessageDocflow messsage) {
		this.message = messsage;
	}

	public ServiceDocflow getService() {
		return service;
	}

	public void setService(ServiceDocflow service) {
		this.service = service;
	}
	
	public boolean hasError(){
		return this.getMessage().getType().equals("error") ? true : false;
	}
	
	
}
