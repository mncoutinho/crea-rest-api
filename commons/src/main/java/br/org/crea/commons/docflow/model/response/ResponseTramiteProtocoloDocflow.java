package br.org.crea.commons.docflow.model.response;

import br.org.crea.commons.docflow.model.tramite.DataTramiteProtocoloDocflow;
import br.org.crea.commons.docflow.model.util.MessageDocflow;
import br.org.crea.commons.docflow.model.util.ServiceDocflow;


public class ResponseTramiteProtocoloDocflow {
	

	private DataTramiteProtocoloDocflow data;
	
	private ServiceDocflow service;
	
	private MessageDocflow message;

	public DataTramiteProtocoloDocflow getData() {
		return data;
	}

	public ServiceDocflow getService() {
		return service;
	}

	public MessageDocflow getMessage() {
		return message;
	}

	public void setData(DataTramiteProtocoloDocflow data) {
		this.data = data;
	}

	public void setService(ServiceDocflow service) {
		this.service = service;
	}

	public void setMessage(MessageDocflow message) {
		this.message = message;
	}
	
	public boolean hasError() {
		return this.message != null && this.getMessage().getType().equals("error") ? true : false; 
	}
	
}
