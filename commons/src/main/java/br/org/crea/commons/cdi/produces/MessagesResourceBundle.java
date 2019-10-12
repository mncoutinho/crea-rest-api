package br.org.crea.commons.cdi.produces;

import java.util.ResourceBundle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class MessagesResourceBundle {

	@Produces
	public ResourceBundle get() {
		return ResourceBundle.getBundle("messages");
	}

}
