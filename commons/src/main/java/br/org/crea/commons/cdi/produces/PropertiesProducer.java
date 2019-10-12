package br.org.crea.commons.cdi.produces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.enterprise.inject.Produces;

public class PropertiesProducer {

	private static final String PROPERTIES = "rest-api.properties";

	@Produces
	public Properties get() throws FileNotFoundException, IOException {

		String propertyFile = System.getProperty(PROPERTIES);

		if(propertyFile == null){
			throw new FileNotFoundException("Arquivo de propriedades: " + PROPERTIES + " não encontrado no servidor!");
		}
		
		Properties properties = new Properties();
		FileInputStream file = new FileInputStream(new File(propertyFile));
		properties.load(file);

		HashMap<Object, Object> hashMap = new HashMap<>(properties);
		properties.putAll(hashMap);
		
		file.close();
		

		return properties;
	}

}
