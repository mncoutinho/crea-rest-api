package br.org.crea.commons.docflow.exception;

import javax.inject.Inject;

import br.org.crea.commons.docflow.model.util.MessageDocflow;

public class DefaultExceptionDocflow {
	
	@Inject MessageDocflow messageError;

	public DefaultExceptionDocflow() {
		
		this.messageError = new MessageDocflow();
		messageError.setType("error");
		messageError.setValue("Houve um erro inesperado durante a comunicação com o servidor do Docflow. Por favor contate o administrador do sistema.");
	}

	public MessageDocflow getMessageError() {
		return messageError;
	}

}



