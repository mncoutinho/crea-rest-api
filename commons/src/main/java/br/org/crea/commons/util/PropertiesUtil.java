package br.org.crea.commons.util;


import java.util.ResourceBundle;

/**
 * @author Ricardo Leite
 * classe util para carregar properties do sistema
 */
public abstract class PropertiesUtil {
	public static ResourceBundle rbMessage = ResourceBundle.getBundle("messages");
	public static ResourceBundle rbApi = ResourceBundle.getBundle("api");
}
