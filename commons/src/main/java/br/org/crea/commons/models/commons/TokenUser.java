package br.org.crea.commons.models.commons;


public class TokenUser {
	
	private String id;
	
	private String publicKey;

	private String privateKey;
	
	private int timeLife;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	public String getPrivateKey() {
		return privateKey;
	}
	
	public void setPrivateKey(String token) {
		this.privateKey = token;
	}
	
	public String getChavePublica() {
		return publicKey;
	}
	
	public void setChavePublica(String chavePublica) {
		this.publicKey = chavePublica;
	}

	public int getTimeLife() {
		return timeLife;
	}

	public void setTimeLife(int timeLife) {
		this.timeLife = timeLife;
	}
	
	
	

}
