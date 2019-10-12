package br.org.crea.commons.cdi.produces;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@ApplicationScoped
public class GsonProducer {

	@Produces
	public Gson get() {
		return new GsonBuilder().create();
	}
	
}
