package br.org.crea.commons.docflow.model.response;

import br.org.crea.commons.docflow.model.processo.DataSubtituicaoProtocoloDocflow;
import br.org.crea.commons.docflow.model.util.MessageDocflow;

public class ResponseSubstituicaoProtocoloDocflow {

	private String data;
	
	private DataSubtituicaoProtocoloDocflow service;
	
	private MessageDocflow message;
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public DataSubtituicaoProtocoloDocflow getService() {
		return service;
	}
	
	public void setService(DataSubtituicaoProtocoloDocflow service) {
		this.service = service;
	}
	
	public MessageDocflow getMessage() {
		return message;
	}
	
	public void setMessage(MessageDocflow message) {
		this.message = message;
	}
	
	public boolean hasError() {
		return this.message != null && this.getMessage().getType().equals("error") ? true : false; 
	}
	
}
