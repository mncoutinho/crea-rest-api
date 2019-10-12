package br.org.crea.commons.helper;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class PropertiesReader {
	
	@Inject
	private Properties properties; 
	
	private ResourceBundle resourceBundle;
	
	@PostConstruct
	public void init(){
		this.resourceBundle = ResourceBundle.getBundle("config");
	}
	
	public String getString(String key){
		
		if(properties.containsKey(key)){
			return properties.getProperty(key);
		}
		
		return resourceBundle.getString(key);
		
	}

}
